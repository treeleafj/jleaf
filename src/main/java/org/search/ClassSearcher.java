package org.search;

import java.io.File;
import java.net.URL;
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
 * @author leaf
 * @date 2014-1-19 下午4:58:02
 */
public class ClassSearcher {

	private static final Logger log = Logger.getLogger(ClassSearcher.class);

	private String basePath;

	public ClassSearcher() {
		URL url = Thread.currentThread().getContextClassLoader()
				.getResource("");
		this.basePath = url.getPath();
	}

	public ClassSearcher(String basePath) {
		if(basePath != null){
			this.basePath = basePath;
		}else{
			URL url = Thread.currentThread().getContextClassLoader()
					.getResource("");
			this.basePath = url.getPath();
		}
	}

	/**
	 * 如果classz是注解类,则返回路径下所有标有该注解的类 如果classz是普通类或者接口,则返回路径下所有该类的子类
	 */
	public List<ClassData> search(ClassFilter filter) throws Exception {

		log.debug("开始搜索,basePath=" + this.basePath);

		File file = new File(basePath);

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
		log.debug("结束搜索,共搜索" + classDataList.size() + "个文件,符合条件的有"
				+ result.size() + "个");

		return result;
	}

	private static List<ClassData> searchJar(JarFile jarFile) throws Exception {
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
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	private static List<ClassData> searchJar(File file) throws Exception {
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
	 * 
	 * @param file
	 */
	private static List<ClassData> searchDir(File file) throws Exception {

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
				classDataList.add(ClassData.load(f));
			}
		}
		return classDataList;
	}

	/**
	 * 过滤class
	 */
	private static List<ClassData> filterClass(List<ClassData> classDataList,
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

	/**
	 * 通配符匹配
	 * 
	 * @param pattern
	 *            通配符模式
	 * @param str
	 *            待匹配的字符串
	 * @return 匹配成功则返回true，否则返回false
	 */
	public static boolean wildcardMatch(String[] patterns, String str) {
		for (String p : patterns) {
			if (wildcardMatch(p, str)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 通配符匹配
	 * 
	 * @param pattern
	 *            通配符模式
	 * @param str
	 *            待匹配的字符串
	 * @return 匹配成功则返回true，否则返回false
	 */
	public static boolean wildcardMatch(String pattern, String str) {
		int patternLength = pattern.length();
		int strLength = str.length();
		int strIndex = 0;
		char ch;
		for (int patternIndex = 0; patternIndex < patternLength; patternIndex++) {
			ch = pattern.charAt(patternIndex);
			if (ch == '*') {
				// 通配符星号*表示可以匹配任意多个字符
				while (strIndex < strLength) {
					if (wildcardMatch(pattern.substring(patternIndex + 1),
							str.substring(strIndex))) {
						return true;
					}
					strIndex++;
				}
			} else if (ch == '?') {
				// 通配符问号?表示匹配任意一个字符
				strIndex++;
				if (strIndex > strLength) {
					// 表示str中已经没有字符匹配?了。
					return false;
				}
			} else {
				if ((strIndex >= strLength) || (ch != str.charAt(strIndex))) {
					return false;
				}
				strIndex++;
			}
		}
		return strIndex == strLength;
	}

	public String getBasePath() {
		return basePath;
	}

	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

}
