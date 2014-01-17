package org.jleaf.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class ClassSearcherTest {

	@Test
	public void test() {
		
		List<String> jars = new ArrayList<String>();
		jars.add("commons-beanutils-core-1.8.3.jar");
		
		ClassSearcher2 cs = ClassSearcher2.of(Serializable.class)
				.injars(jars)
				.includeAllJarsInLib(true)
				.classpath("F:/Java/project");
		
		List<Class<?>> result = cs.search();
		
		System.out.println(result);
	}

}
