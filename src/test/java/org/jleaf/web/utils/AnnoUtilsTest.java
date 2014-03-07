package org.jleaf.web.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.demo.controller.MsgController;
import org.demo.interceptor.BaseInitInterceptor;
import org.demo.interceptor.Msg2Interceptor;
import org.demo.interceptor.MsgInterceptor;
import org.jleaf.db.interceptor.EntityTranscationInterceptor;
import org.jleaf.web.annotation.ClearInterceptor;
import org.jleaf.web.intercept.Interceptor;
import org.junit.Test;

/**
 * leaf
 * 14-3-5 下午10:44.
 */
public class AnnoUtilsTest {
    @Test
    public void testGetInterceptorsByClearInterceptor() throws Exception {
        ClearInterceptor clearInterceptor = MsgController.class.getAnnotation(ClearInterceptor.class);
        System.out.println("clear:" + Arrays.toString(clearInterceptor.value()));
        List<Interceptor> list = new ArrayList<Interceptor>();
        list.add(new BaseInitInterceptor());
        list.add(new Msg2Interceptor());
        list.add(new MsgInterceptor());
        list.add(new EntityTranscationInterceptor());
        list = AnnoUtils.getInterceptorsByClearInterceptor(clearInterceptor, list);
        System.out.println(list);
    }
}
