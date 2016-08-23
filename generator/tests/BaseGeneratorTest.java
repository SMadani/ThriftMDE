package org.apache.thrift.generator.tests;

import static org.junit.Assert.assertEquals;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.apache.thrift.ThriftCompiler;
import org.junit.Before;

public abstract class BaseGeneratorTest {
	
	protected final String lang, extension, rootPath, cppPath, mdePath, testFile;
	static boolean isSetUp = false;
	
	public BaseGeneratorTest(String language, String thriftFile) {
		this.lang = language;
		extension = '.'+lang;
		rootPath = "src/org/apache/thrift/";
		mdePath = rootPath+"/generator/"+lang+"/output/";
		cppPath = rootPath+"gen-"+lang+'/';
		this.testFile = rootPath+thriftFile;
	}
	
	@Before
	public void setUp() throws Exception {
		if (isSetUp) return;
		ThriftCompiler.main(lang, testFile);
		Runtime.getRuntime().exec("thrift --gen "+lang+' '+'"'+testFile+'"');
		isSetUp = true;
	}
	
	protected void compareSizes(String fileName) {
		long cppImpl = new File(cppPath+fileName+extension).length(),
		     mdeImpl = new File(mdePath+fileName+extension).length();
		assertEquals(cppImpl, mdeImpl);
	}
	
	protected void compareContents(String fileName) {
		String cppImpl = readFile(cppPath+fileName+extension);
		String mdeImpl = readFile(mdePath+fileName+extension);
		assertEquals(cppImpl, mdeImpl);
	}
	
	public static String readFile(String filePath) {
		Charset charset = Charset.forName("UTF-8");
		String ln = System.lineSeparator();
		StringBuilder sb = new StringBuilder();
		try (BufferedReader reader = Files.newBufferedReader(Paths.get(filePath), charset)) {
		    String line = null;
		    while ((line = reader.readLine()) != null) {
		        sb.append(line+ln);
		    }
		}
		catch (IOException ioe) {
		    System.err.println("Failed to read file: "+ioe.getMessage());
		}
		return sb.toString();
	}
}
