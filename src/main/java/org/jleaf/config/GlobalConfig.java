package org.jleaf.config;

import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jleaf.utils.LogFactory;
import org.jleaf.web.action.analyze.ActionAnalyze;

/**
 * 全局基本配置类
 */
@SuppressWarnings({"serial","unchecked"})
public class GlobalConfig implements Serializable {

    private final static Logger log = LogFactory.getLogger(GlobalConfig.class);

    /**
     * 日志工厂
     */
    public final static String LOG_FACTORY = "jlaef.logFactory";

    /**
     * 默认的Dao实现
     */
    public final static String DEFAULT_DAO = "jleaf.defaultDaoImpl";

    /**
     * 静态文件后缀
     */
    public final static String STATIC_RESOURCE = "jleaf.staticResource";

    /**
     * 默认的请求后缀
     */
    public final static String DEFAULT_POSTFIX = "jleaf.defaultPostfix";

    /**
     * 默认ActionAnalyze实现类
     */
    public final static String DEFAULT_ACTION_ANALYZE_CLASS = "jleaf.defaultActionAnalyzeClass";

    /**
     * 配置
     */
    private static Map<String, String> config = null;

    /**
     * @return 获取默认的Dao实现类
     */
    public static String getDefaultDao() {
        String defaultDaoImpl = get(DEFAULT_DAO);
        if (StringUtils.isBlank(defaultDaoImpl)) {
            return "org.jleaf.db.dao.impl.MongoDBDaoImpl";
        }
        return defaultDaoImpl;
    }

    /**
     * @return 获得静态文件后缀
     */
    public static String[] getStaticResouces() {
        String sr = get(STATIC_RESOURCE);
        if (StringUtils.isBlank(sr)) {
            return new String[]{};
        }
        return sr.split(",");
    }

    /**
     * @return 获取默认的请求后缀
     */
    public static String getDefaultPostfix() {
        String dp = get(DEFAULT_POSTFIX);
        if (StringUtils.isBlank(dp)) {
            return "";
        }
        return dp;
    }

    /**
     * 获得默认的ActionAnalyze实现类
     * @return
     */
	public static Class<? extends ActionAnalyze> getDefaultActionAnalyzeClass() {
        String classz = get(DEFAULT_ACTION_ANALYZE_CLASS);
        try {
            if (StringUtils.isBlank(classz)) {
                return (Class<? extends ActionAnalyze>) Class.forName("org.jleaf.web.action.analyze.HttpActionAnalyze");
            }
            return (Class<? extends ActionAnalyze>) Class.forName(classz);
        } catch (Exception e) {
            throw new Error(e);
        }
    }

    /**
     * 获取配置里指定的值
     *
     * @param key 指定的key
     */
    protected static String get(String key) {
        if (config == null) {
            throw new Error("未加载配置文件");
        }
        return config.get(key);
    }

    /**
     * 设置配置
     */
    public static void setConfig(Map<String, String> config) {
        GlobalConfig.config = config;
    }


    /**
     * 获取本地配置
     * @return
     */
    public static Map<String,String> getLocalConfigMap(){
        InputStream in = GlobalConfig.class.getResourceAsStream("jleaf.properties");
        if(in != null){
            return PropertiesUtils.load(in);
        }
        log.warn("cont not found the jleaf.properties in jleaf framework");;
        return new HashMap<String, String>();
    }

}
