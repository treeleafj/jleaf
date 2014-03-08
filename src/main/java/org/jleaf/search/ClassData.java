package org.jleaf.search;

import java.io.File;
import java.io.Serializable;

/**
 * 存放类型的一些基本数据
 *
 * @author leaf
 * @date 2014-1-19 下午4:58:52
 */
@SuppressWarnings("serial")
public class ClassData implements Serializable {

    /**
     * 类名
     */
    private String className;

    /**
     * 类的简单名字
     */
    private String simpleClassName;

    public ClassData() {
    }

    public static ClassData load(String rootPath, File file) throws Exception {
        return load(rootPath, file.getAbsolutePath());
    }

    public static ClassData load(String rootPath, String filePath) throws Exception {
        rootPath = new File(rootPath).getAbsolutePath();
        int index = filePath.indexOf(rootPath);
        if (index >= 0) {
            String s = filePath.substring(index + rootPath.length());
            s = s.replaceAll("/", ".").replaceAll("\\\\", ".");

            //匿名类不需要
            if (s.contains("$")) {
                return null;
            }

            if (s.charAt(0) == '.') {
                s = s.substring(1);
            }
            if (s.endsWith(".class")) {
                s = s.substring(0, s.lastIndexOf(".class"));
            }
            if (s.endsWith(".java")) {
                s = s.substring(0, s.lastIndexOf(".java"));
            }

            ClassData data = new ClassData();
            data.setClassName(s);

            int lastIndex = s.lastIndexOf('.');
            if (lastIndex >= 0) {
                String simpleName = s.substring(lastIndex + 1);
                data.setSimpleClassName(simpleName);
            } else {
                data.setSimpleClassName(s);
            }
            return data;
        }
        return null;
    }

    public String getClassName() {
        return className;
    }

    public String getSimpleClassName() {
        return simpleClassName;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setSimpleClassName(String simpleClassName) {
        this.simpleClassName = simpleClassName;
    }

    @Override
    public String toString() {
        return this.className + "[" + this.simpleClassName + "]";
    }

}