package org.jleaf.web;

import org.demo.controller.MsgController;
import org.jleaf.search.ClassData;
import org.jleaf.search.ClassFilter;
import org.jleaf.search.ClassSearcher;
import org.junit.Test;

public class ClassSearchText {

//    @Test
    public void scan() throws Exception {
        ClassSearcher classSearcher = new ClassSearcher();
        classSearcher.setBasePath("F:\\Java\\idea\\jleaf\\src\\demo\\webapp\\WEB-INF\\classes");
        classSearcher.search(new ClassFilter() {
            @Override
            public boolean filter(ClassData classData) {
                System.out.println(classData);
                return false;
            }
        });
    }

    @Test
    public void testPath(){
        System.out.println(MsgController.class.getResource(""));
        System.out.println(MsgController.class.getClassLoader().getResource(""));
        System.out.println(Thread.currentThread().getContextClassLoader().getResource(""));

    }

}
