package org.jleaf.search;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.log4j.Logger;

/**
 * 类搜索工具
 *
 * @author leaf
 * @date 2014-1-19 下午4:58:02
 */
public class ClassSearcher {

    private static final Logger log = Logger.getLogger(ClassSearcher.class);

    private String basePath;

    public ClassSearcher() {
        this(Thread.currentThread().getContextClassLoader().getResource("").getPath());
    }

    /**
     * @param basePath 资源路径,可以是文件夹或者文件
     */
    public ClassSearcher(String basePath) {
        if (basePath == null) {
            this.basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        } else {
            this.basePath = basePath;
        }
    }

    /**
     * 如果classz是注解类,则返回路径下所有标有该注解的类 如果classz是普通类或者接口,则返回路径下所有该类的子类
     */
    public List<ClassData> search(ClassFilter filter) throws Exception {

        log.debug("begin search,basePath=" + this.basePath);

        File file = new File(this.basePath);

        Queue<File> fileQueue = new LinkedList<File>();
        fileQueue.add(file);

        List<ClassData> classDataList = new ArrayList<ClassData>();

        while (!fileQueue.isEmpty()) {
            File f = fileQueue.poll();
            if (f.isDirectory()) {
                for (File temp : f.listFiles()) {
                    fileQueue.add(temp);
                }
            } else if (f.getName().endsWith(".class")) {
                classDataList.addAll(searchDir(f));
            } else if (f.getName().endsWith(".jar")) {
                classDataList.addAll(searchJar(f));
            }
        }
        List<ClassData> result = filterClass(classDataList, filter);
        log.debug("stop search,total:" + classDataList.size() + ",suitable:"
                + result.size() + "");

        return result;
    }

    /**
     * 搜索Jar文件
     */
    private List<ClassData> searchJar(JarFile jarFile) throws Exception {
        List<ClassData> classDataList = new ArrayList<ClassData>();

        Enumeration<JarEntry> enumeration = jarFile.entries();

        while (enumeration.hasMoreElements()) {
            JarEntry entry = enumeration.nextElement();
            if (!entry.isDirectory()) {
                if (entry.getName().endsWith(".class")) {
                    try {
                        ClassData data = new ClassData();
                        String name = entry.getName().replaceAll("/", ".")
                                .replaceAll("\\\\", ".")
                                .replaceAll("file:", "");
                        int index = name.lastIndexOf(".");
                        name = name.substring(0, index);
                        data.setClassName(name);
                        index = name.lastIndexOf(".");
                        if (index >= 0) {
                            data.setSimpleClassName(name.substring(index + 1));
                        } else {
                            data.setSimpleClassName(name);
                        }
                        classDataList.add(data);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return classDataList;
    }

    /**
     * 搜索指定路径或指定jar包下所有class文件
     */
    private List<ClassData> searchJar(File file) throws Exception {
        List<ClassData> classDataList = new ArrayList<ClassData>();
        Queue<File> fileQueue = new LinkedList<File>();
        fileQueue.add(file);
        while (!fileQueue.isEmpty()) {
            File f = fileQueue.poll();
            if (f.isDirectory()) {
                for (File temp : f.listFiles()) {
                    fileQueue.add(temp);
                }
            } else if (f.getName().endsWith(".jar")) {
                JarFile jarFile = new JarFile(f);
                classDataList.addAll(searchJar(jarFile));
            }
        }
        return classDataList;
    }

    /**
     * 搜索某路径下所有class文件
     */
    private List<ClassData> searchDir(File file) throws Exception {

        List<ClassData> classDataList = new ArrayList<ClassData>();

        Queue<File> fileQueue = new LinkedList<File>();
        fileQueue.add(file);

        while (!fileQueue.isEmpty()) {
            File f = fileQueue.poll();
            if (f.isDirectory()) {
                File[] fs = f.listFiles();
                for (File temp : fs) {
                    fileQueue.add(temp);
                }
            } else if (f.getName().endsWith(".class")) {
                try {
                    ClassData data = ClassData.load(this.basePath, f);
                    if (data != null) {
                        classDataList.add(data);
                    }
                } catch (Exception e) {
                    log.error("扫描文件:" + f + "出现错误");
                    throw new Error(e);
                }

            }
        }
        return classDataList;
    }

    /**
     * 过滤class
     */
    private List<ClassData> filterClass(List<ClassData> classDataList,
                                        ClassFilter filter) {
        List<ClassData> result = new ArrayList<ClassData>();

        for (ClassData classData : classDataList) {
            boolean b = filter.filter(classData);
            if (b) {
                result.add(classData);
            }
        }
        return result;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

}
