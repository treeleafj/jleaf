package org.jleaf.utils;

import java.util.List;

import org.junit.Test;
import org.search.ClassData;
import org.search.ClassFilter;
import org.search.ClassSearcher;

public class ClassSearcherTest {

//	@Test
	public void test() throws Exception {
		
		ClassSearcher classSearcher = new ClassSearcher("F:/Java/project/jleaf/jleaf");
		List<?> result = classSearcher.search(new ClassFilter() {

			public boolean filter(ClassData classData) {
				try {
					if (ClassSearcher.wildcardMatch("org.jleaf.*", classData.getClassName())) {
						Class<?> classz = Class.forName(classData.getClassName());
						if (Error.class.isAssignableFrom(classz)) {
							return true;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				return false;
			}
		});
		System.out.println("size:" + result.size() + ":" + result);
	}
	
	@Test
	public void path() throws Exception{
		String path = ClassSearcher.class.getClassLoader().getResource("").getPath();
		System.out.println(path);
	}

}
