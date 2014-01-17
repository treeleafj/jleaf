package org.search;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.jleaf.web.controller.HttpMethod;
import org.jleaf.web.controller.annotation.Controller;

public class ClassSearcher {

	private String basePath;
	
	public ClassSearcher() {
		URL url = Thread.currentThread().getContextClassLoader()
				.getResource("");
		this.basePath = url.getPath();
		System.out.println(url);
	}

	public ClassSearcher(String basePath) {
		this.basePath = basePath;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Class> search(Class classz) throws Exception {
		
		List<Class> result = new ArrayList<Class>();
		
		Queue<File> fileQueue = new LinkedList<File>();
		
		File file = new File(basePath);
		
		List<ClassData> classDataList = new ArrayList<ClassData>();
		
		fileQueue.add(file);
		while(!fileQueue.isEmpty()){
			File f = fileQueue.poll();
			if(f.isDirectory()){
				File [] fs = f.listFiles();
				for(File temp : fs){
					fileQueue.add(temp);
				}
			}else if(f.getName().endsWith(".class")){
				classDataList.add(ClassData.load(f));
			}
		}
		
		
		if(classz.isAnnotation()){
			for (ClassData classData : classDataList) {
				Class<?> c = Class.forName(classData.getClassName());
				if(c.getAnnotation(classz) != null){
					result.add(c);
				}
			}
		}else{
			for (ClassData classData : classDataList) {
				Class<?> c = Class.forName(classData.getClassName());
				if(classz.isAssignableFrom(c) && classz != c){
					result.add(c);
				}
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

	public static void main(String[] args) throws Exception {
		List result = new ClassSearcher().search(Error.class);
		System.out.println(result);
	}

}
