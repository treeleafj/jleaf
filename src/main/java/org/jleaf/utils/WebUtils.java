package org.jleaf.utils;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jleaf.web.controller.HttpMethod;

/**
 * Web工具
 * @author leaf
 * @date 2014-1-2 下午2:25:04
 */
public abstract class WebUtils {
	
	/**
	 * 静态文件的格式
	 */
	private static String [] staticResource = {
			".gif",".jpge",".jpg",".png",".icon",".ico",
			".jsp",".html",".htm",
			".js",".css",".swf"
	};
	
	/**
	 * 静态文件的格式的集合
	 */
	private static Set<String> staticResourceSet = new HashSet<String>();
	
	static{
		for(String s : staticResource){
			staticResourceSet.add(s);
		}
	}

	/**
	 * 将字符串(GET,POST,DELETE等) 解析成 ReqMethod对象<br/>
	 * 如果解析失败,则默认返回GET
	 * @param method
	 */
	public static HttpMethod analyzeHttpMehotd(String method) {
		try {
			return Enum.valueOf(HttpMethod.class, method.toUpperCase());
		} catch (Exception e) {
			return HttpMethod.GET;
		}

	}

	/**
	 * 判断是请求地址是否Action请求
	 * @param uri
	 */
	public static boolean isStaticResource(String uri) {
		int index = uri.lastIndexOf('.');
		if(index >= 0){
			String key = uri.substring(index);
			return staticResourceSet.contains(key);
		}
		return false;
	}
	
	/**
	 * 将Session转为Map
	 * @param ses
	 */
	public static Map<String,Object> ses2Map(HttpSession ses){
		Map<String,Object> sesMap = new HashMap<String,Object>();
		Enumeration<String> names = ses.getAttributeNames();
		while(names.hasMoreElements()){
			String name = names.nextElement();
			sesMap.put(name,ses.getAttribute(name));
		}
		return sesMap;
	}
	
	public static HttpServletRequest getRequest(){
		return HttpServletRequestInvoke.getRequest();
	}
	
	public static HttpServletResponse getResponse(){
		return HttpServletResponseInvoke.getResponse();
	}
	
	public static ServletContext getServletContext(){
		return getRequest().getServletContext();
	}
	
	public static HttpSession getSession(){
		return getRequest().getSession();
	}
	
	public static HttpSession getSession(boolean create){
		return getRequest().getSession(create);
	}
	
}
