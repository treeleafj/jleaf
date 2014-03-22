package org.jleaf.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.demo.entity.User;
import org.junit.Test;

public class FastBeanUtilsTest {
	
	
	public void populate() throws IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException{
//		int i = 100;
//		while(i-- > 0){
			long t = System.currentTimeMillis();
			Map map = new HashMap();
			map.put("id", "_id");
			map.put("username", "_username");
			map.put("email", "_email");
			map.put("sex", "10");
			map.put("loged", "true");
			map.put("logined", "true");
			User u = new User();
			FastBeanUtils.populate(u, map);
			System.out.println(u + ", 	time:" + (System.currentTimeMillis() - t));
//		}
	}
	
	@Test
	public void mPopulate(){
		List<Thread> threads = new ArrayList<Thread>();
		
		for(int i = 0; i < 1000; i++){
			
			Thread t = new Thread(){
				@Override
				public void run() {
					try {
						populate();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};
			
			threads.add(t);
		}
		
		try {
			populate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		for(Thread t : threads){
			t.start();
		}
	}
	
	public static void main(String[] args) {
		new FastBeanUtilsTest().mPopulate();
	}
	
}
