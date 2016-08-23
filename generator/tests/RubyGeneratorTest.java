package org.apache.thrift.generator.tests;

import java.util.List;
import org.junit.Test;
import static java.util.Arrays.asList;

public class RubyGeneratorTest extends BaseGeneratorTest {
	public RubyGeneratorTest() {
		super("rb", "SimpleService.thrift");
	}

	String
		rbFile = "simple_service",
		constants = rbFile+"_constants",
		types = rbFile+"_types";
	List<String> services = asList("Calculator");
	
	@Test
	public void testTypes() {
		compareContents(types);
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
