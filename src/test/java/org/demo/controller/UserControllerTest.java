package org.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jleaf.test.JleafJunit;
import org.jleaf.utils.LogFactory;
import org.jleaf.web.annotation.HttpMethod;
import org.jleaf.web.controller.result.Result;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * leaf
 * 14-3-7 下午11:21.
 */
public class UserControllerTest extends JleafJunit {

    private static Logger log = LogFactory.getLogger(UserControllerTest.class);

    @BeforeClass
    public static void beforeClass() {
        JleafJunit.setPath("F:/Java/project/jleaf/jleaf/src/demo/webapp");
        Map<String, String> config = new HashMap<String, String>();
        config.put("package", "org.demo.*");
        setBootConfig(config);
    }

    @Test
    public void testJson() throws Exception {
        Result result = this.action("user/json", HttpMethod.GET);
        Assert.assertNotNull(result);
    }

    @Test
    public void testIndex() throws Exception {
        Result result = this.action("user/index", HttpMethod.GET);
        Assert.assertNotNull(result);
    }

}
