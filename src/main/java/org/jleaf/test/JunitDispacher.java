package org.jleaf.test;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jleaf.config.BootConfig;
import org.jleaf.config.GlobalConfig;
import org.jleaf.config.PropertiesUtils;
import org.jleaf.config.WebApplicationInfo;
import org.jleaf.utils.LogFactory;
import org.jleaf.web.core.HttpMvcDispatcher;
import org.jleaf.web.core.MvcDispatcher;

/**
 * leaf
 * 14-3-8 上午12:02.
 */
public class JunitDispacher {

    private static Logger log = LogFactory.getLogger(JleafJunit.class);

    private static MvcDispatcher dispatcher;

    private static String path = "";

    private static BootConfig bootConfig = new BootConfig(new HashMap<String, String>());

    public synchronized static MvcDispatcher getInstance(){
        if (dispatcher == null){
            path = path.replaceAll("\\\\","/");
            if(path.endsWith("/")){
                path = path.substring(0,path.length() - 1);
            }
            WebApplicationInfo.setPath(path);
            //初始化全局配置
            Map<String, String> config = PropertiesUtils.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("jleaf.properties"));

            Map<String, String> mainConfig = GlobalConfig.getLocalConfigMap();
            for (Map.Entry<String, String> entry : config.entrySet()) {
                mainConfig.put(entry.getKey(), entry.getValue());
            }

            log.debug("jleaf.properties:" + mainConfig);

            GlobalConfig.setConfig(mainConfig);

            //初始化启动配置
            dispatcher = new HttpMvcDispatcher(bootConfig);
            return dispatcher;
        }
        return dispatcher;
    }

    public static String getPath() {
        return path;
    }

    public static void setPath(String path) {
        JunitDispacher.path = path;
    }

    public static BootConfig getBootConfig() {
        return bootConfig;
    }

    public static void setBootConfig(BootConfig bootConfig) {
        JunitDispacher.bootConfig = bootConfig;
    }

    public static void setBootConfig(Map<String,String> config) {
        JunitDispacher.bootConfig = new BootConfig(config);
    }
}
