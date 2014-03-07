package org.jleaf.config;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * 启动参数配置
 * 14-3-1 下午3:04.
 */
public class BootConfig implements Serializable {

    public final static String SCAN = "scan";

    public final static String SCAN_LIB = "scan-lib";

    public final static String PACKAGE = "package";

    private Map<String, String> config = new HashMap<String, String>();

    public BootConfig(Map<String, String> config) {
        this.config = config;
    }

    /**
     * @return 是否需要启用扫描功能, 默认是true
     */
    public boolean isScan() {
        return !"false".equals(get(SCAN));
    }

    /**
     * @return 是否需要扫描lib目录, 默认是false
     */
    public boolean isScanLib() {
        return "true".equals(get(SCAN_LIB));
    }

    /**
     * @return 得到要扫描的包路径
     */
    public String[] getPackages() {
        String s = get(PACKAGE);
        return StringUtils.isBlank(s) ? new String[]{"*"} : s.split(",");
    }

    /**
     * 得到指定key的值
     */
    public String get(String key) {
        if (config == null) {
            throw new Error("未初始化配置!");
        }
        return config.get(key);
    }
}
