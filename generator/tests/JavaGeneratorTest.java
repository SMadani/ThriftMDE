package org.apache.thrift.generator.tests;

import static java.util.Arrays.asList;
import java.util.List;
import org.junit.Test;

public class JavaGeneratorTest extends BaseGeneratorTest {
	public JavaGeneratorTest() throws Exception {
		super("java", "SimpleService.thrift");
	}
	
	String
		javaFile = "SimpleService",
		constants = javaFile+"Constants";
	List<String>
		services = asList("Calculator"),
		unions = asList("myOnion"),
		structs = asList("myStruct", "simpleStruct"),
		enums = asList("myEnum");
	
	@Test
	public void testEnums() {
		enums.forEach(this::compareContents);
	}
	
	@Test
	public void testStructs() {
		structs.forEach(this::compareContents);
	}
	
	@Test
	public void testUnions() {
		unions.forEach(this::compareContents);
	}
	
	@Test
	public void testConsts() {
		compareSizes(constants);
	}
	
	@Test
	public void testServices() {
		services.forEach(this::compareContents);
	}
}
