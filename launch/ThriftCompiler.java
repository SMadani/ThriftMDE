package org.apache.thrift;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import org.apache.thrift.IDLStandaloneSetup;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.epsilon.egl.EglFileGeneratingTemplateFactory;
import org.eclipse.epsilon.egl.EgxModule;
import org.eclipse.epsilon.egl.execute.context.EgxContext;
import org.eclipse.epsilon.egl.execute.context.IEgxContext;
import org.eclipse.epsilon.emc.emf.InMemoryEmfModel;
import org.eclipse.epsilon.eol.IEolModule;
import org.eclipse.epsilon.eol.execute.context.FrameStack;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.evl.EvlModule;
import org.eclipse.epsilon.evl.IEvlModule;
import org.eclipse.epsilon.evl.execute.UnsatisfiedConstraint;
import org.eclipse.epsilon.evl.execute.context.IEvlContext;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import com.google.inject.Injector;

public class ThriftCompiler {
	public static void main(String... args) throws Exception {
		//Parse the command-line arguments
		String root = "src/org/apache/thrift/";
		if (args.length != 2) {
			System.out.println("Incorrect arguments: first arg must be the language, second must be the Thrift file.");
			return;
		}
		switch(args[0].toLowerCase()) {
			case "java":
			case "rb": break;
			default: {
				System.out.println("Language not supported. Exiting.");
				return;
			}
		}
		
		//Parsing the input file into an EMF model
		Injector injector = (Injector) new IDLStandaloneSetup().createInjectorAndDoEMFRegistration();
		XtextResourceSet resourceSet = injector.getInstance(XtextResourceSet.class);
		resourceSet.addLoadOption(XtextResource.OPTION_RESOLVE_ALL, true);
		File thriftFile = new File(args[1]);
		String fileName = thriftFile.getName();
		fileName = fileName.substring(0, fileName.lastIndexOf('.'));
		String fileLocation = thriftFile.getAbsolutePath();
		Resource resource = resourceSet.getResource(URI.createURI("file:///"+fileLocation), true);
		IModel model = new InMemoryEmfModel(resource);
	    model.load();
	    
	    //Semantic validation using Epsilon Validation Language
	    IEvlModule evl = new EvlModule();
	    evl.parse(new File(root+"validation/ThriftValidator.evl"));
	    if (!evl.getParseProblems().isEmpty()) {
	    	System.err.println("Syntax errors found in validation logic. Exiting.");
	    	return;
	    }
	    evl.getContext().getModelRepository().addModel(model);
	    evl.execute();
	    Collection<UnsatisfiedConstraint> failedConstraints = evl.getContext().getUnsatisfiedConstraints();
	    if (!failedConstraints.isEmpty()) {
	    	System.err.println("Semantic validation failed:");
	    	failedConstraints.forEach(constraint -> System.out.println(constraint.getMessage()));
	    	return;
	    }
	    
	    //Code generation using Epsilon Generation Lanaguage
		IEolModule egx = new EgxModule(new EglFileGeneratingTemplateFactory());
	    egx.parse(new File(root+"generator/"+args[0]+"/thrift-"+args[0]+".egx"));
	    if (!egx.getParseProblems().isEmpty()) {
	    	System.err.println("Syntax errors found in code generation template. Exiting.");
	    	return;
	    }
	    egx.getContext().getModelRepository().addModel(model);
	    egx.getContext().getFrameStack().put(
	    	Variable.createReadOnlyVariable("fileName", fileName),
	    	Variable.createReadOnlyVariable("date", new SimpleDateFormat("yyyy-MM-dd").format(new Date()))
	    );
	    egx.execute();
	}
}
