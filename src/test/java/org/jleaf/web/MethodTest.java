package org.jleaf.web;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.junit.Test;

/**
 * leaf
 * 14-3-2 下午10:36.
 */
public class MethodTest {

    public final void t1() {

    }


    @Test
    public void test() throws NoSuchMethodException {
        Method m = MethodTest.class.getMethod("t1");
        System.out.println(m.getModifiers() & Modifier.STATIC);
    }

}
