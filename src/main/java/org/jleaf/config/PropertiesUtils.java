package org.jleaf.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.jleaf.utils.LogFactory;

/**
 * leaf
 * 14-3-1 下午3:39.
 */
@SuppressWarnings("unchecked")
public class PropertiesUtils {

    private final static Logger log = LogFactory.getLogger(PropertiesUtils.class);

    /**
     * 加载配置文件
     *
     * @param path
     * @throws IOException
     */
    public static Map<String, String> load(String path) {
        return load(new File(path));
    }

    /**
     * 加载配置文件
     *
     * @param file
     */
    public static Map<String, String> load(File file) {
        try {
            if(file.exists()){
                return load(new FileInputStream(file));
            }else{
                log.warn("cant not found the file:" + file.getAbsolutePath());
            }
        } catch (FileNotFoundException e) {
            throw new Error(e);
        }
        return new HashMap<String, String>();
    }

    /**
     * 加载配置文件
     *
     * @param in
     */
	public static Map<String, String> load(InputStream in) {
        if(in != null){
            Properties p = new Properties();
            try {
                p.load(in);
                Map<String, String> config = new HashMap<String, String>();
                Enumeration<String> enumeration = (Enumeration<String>) p.propertyNames();
                while (enumeration.hasMoreElements()) {
                    String key = enumeration.nextElement();
                    config.put(key, p.getProperty(key));
                }
                return config;
            } catch (IOException e) {
                e.printStackTrace();
            }
            IOUtils.closeQuietly(in);
        }
        log.warn("in is null");
        return new HashMap<String, String>();
    }


}
