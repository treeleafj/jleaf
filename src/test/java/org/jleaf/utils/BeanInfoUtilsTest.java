package org.jleaf.utils;

import java.lang.reflect.Method;

import org.demo.entity.User;
import org.junit.Assert;
import org.junit.Test;

public class BeanInfoUtilsTest {
	
	
	@Test
	public void isPrivate() throws SecurityException, NoSuchMethodException{
		Method m = User.class.getMethod("getId");
		Assert.assertTrue(!BeanInfoUtils.isPrivate(m));
	}
	
	@Test
	public void isProtected() throws SecurityException, NoSuchMethodException{
		Method m = User.class.getMethod("getId");
		Assert.assertTrue(!BeanInfoUtils.isProtected(m));
	}

	@Test
	public void isPublic() throws SecurityException, NoSuchMethodException{
		Method m = User.class.getMethod("getId");
		Assert.assertTrue(BeanInfoUtils.isPublic(m));
	}
	
	@Test
	public void isStatic() throws SecurityException, NoSuchMethodException{
		Method m = BeanInfoUtils.class.getMethod("isStatic",Method.class);
		Assert.assertTrue(BeanInfoUtils.isStatic(m));
	}
	
	@Test
	public void isAbstract() throws SecurityException, NoSuchMethodException{
		Method m = BeanInfoUtils.class.getMethod("isStatic",Method.class);
		Assert.assertTrue(!BeanInfoUtils.isAbstract(m));
	}
	
	@Test
	public void isFinal() throws SecurityException, NoSuchMethodException{
		Method m = BeanInfoUtils.class.getMethod("isStatic",Method.class);
		Assert.assertTrue(!BeanInfoUtils.isFinal(m));
	}
	
	@Test
	public void isSynchronized() throws SecurityException, NoSuchMethodException{
		Method m = User.class.getMethod("getId");
		Assert.assertTrue(!BeanInfoUtils.isSynchronized(m));
	}
	
	@Test
	public void isSet() throws SecurityException, NoSuchMethodException{
		Method m = User.class.getMethod("setId",String.class);
		Assert.assertTrue(BeanInfoUtils.isSet(m));
	}
	
	@Test
	public void isGet() throws SecurityException, NoSuchMethodException{
		Method m = User.class.getMethod("getId");
		Assert.assertTrue(BeanInfoUtils.isGet(m));
	}
	
	@Test
	public void getParamLength() throws SecurityException, NoSuchMethodException{
		Method m = User.class.getMethod("setId",String.class);
		Assert.assertTrue(BeanInfoUtils.getParamLength(m) == 1);
	}
	
	@Test
	public void isReturnVoid() throws SecurityException, NoSuchMethodException{
		Method m = User.class.getMethod("setId",String.class);
		System.out.println(m.getReturnType());
		Assert.assertTrue(BeanInfoUtils.isReturnVoid(m));
	}


}
