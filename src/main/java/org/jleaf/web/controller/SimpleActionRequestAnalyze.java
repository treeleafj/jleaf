package org.jleaf.web.controller;

import org.jleaf.utils.WebUtils;

/**
 * 简单实现Action请求解析
 * 
 * @author leaf
 * @date 2014-1-2 下午9:27:00
 */
public class SimpleActionRequestAnalyze extends AbstractActionRequestAnalyze {

	private final static String DEFAULTPostfix = "";
	
	private String uri;
	private String httpMethod;

	public SimpleActionRequestAnalyze(String uri, String httpMethod) {
		super();
		this.uri = uri;
		this.httpMethod = httpMethod;
	}

	@Override
	public AnalyzeResult analyze() {
		HttpMethod hm = WebUtils.analyzeHttpMehotd(httpMethod);
		
		// 先去掉开头的 '/'
		if (uri.charAt(0) == '/') {
			uri = uri.substring(1);
		}

		String[] array = uri.split("/");
		
		switch(array.length){
		case 0 : 
			{
				return new AnalyzeResult("/", "index",DEFAULTPostfix, hm);
			}
		case 1 :
			{
				int index = array[0].lastIndexOf('.');
				if (index > 0) {
					return new AnalyzeResult(array[0].substring(0, index), "index", array[0].substring(index), hm);
				}else{
					return new AnalyzeResult(array[0], "index", DEFAULTPostfix, hm);
				}
			}
		case 2 :
			{
				int index = array[1].lastIndexOf('.');
				if (index > 0) {
					return new AnalyzeResult(array[0], array[1].substring(0, index), array[1].substring(index), hm);
				}else{
					return new AnalyzeResult(array[0], array[1], DEFAULTPostfix, hm);
				}
			}
		default : 
			{
				StringBuilder controllerUri = new StringBuilder();
				int len = array.length - 1;
				for (int i = 0; i < len; i++) {
					controllerUri.append(array[i]);
					controllerUri.append('/');
				}
				controllerUri.deleteCharAt(controllerUri.length() - 1);
				String last = array[array.length - 1];
				
				int index = last.lastIndexOf('.');
				if (index > 0) {
					return new AnalyzeResult(controllerUri.toString(),last.substring(0, index), last.substring(index), hm);
				}else{
					return new AnalyzeResult(controllerUri.toString(), last, DEFAULTPostfix, hm);
				}
			}
		}
	}
}
