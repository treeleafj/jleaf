package org.jleaf.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.shop.domain.User;

public class FastBeanUtilsTest {
	
	@Test
	public void populate() throws IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException{
		int i = 10;
		while(i-- > 0){
			long t = System.currentTimeMillis();
			Map map = new HashMap();
			map.put("id", "_id");
			map.put("username", "_username");
			map.put("email", "_email");
			map.put("sex", "10");
			map.put("loged", "true");
			map.put("logined", "true");
			
			System.out.println(FastBeanUtils.fastPopulate(User.class, map) + ", 	time:" + (System.currentTimeMillis() - t));
		}
	}

}
