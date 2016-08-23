package org.apache.thrift.generator.tests;

import org.apache.thrift.ThriftCompiler;

public class CompilerPerfBench {
	public static void main(String[] args) throws Exception {
		if (args.length != 2) return;
		String lang = args[0], testFile = args[1],
			cppArgs = "thrift --gen "+lang+' '+'"'+testFile+'"';
		long before, after;
		Runtime runtime = Runtime.getRuntime();
		before = System.currentTimeMillis();
		runtime.exec(cppArgs).waitFor();
		after = System.currentTimeMillis();
		System.out.println("C++ compiler: "+(after-before)+" ms");
		before = System.currentTimeMillis();
		ThriftCompiler.main(lang, testFile);
		after = System.currentTimeMillis();
		System.out.println("MDE compiler: "+(after-before)+" ms");
	}
}
