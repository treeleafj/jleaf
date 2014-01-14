package org.jleaf.web.controller;

import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

/**
 * 一次Request请求
 * 
 * @author leaf
 * @date 2014-1-2 下午9:41:40
 */
@SuppressWarnings("unchecked")
public class ActionRequest {

	private AnalyzeResult analyzeResult;

	private Map<String, String[]> params;

	private Map<String, Object> session;

	public ActionRequest(AnalyzeResult analyzeResult,Map<String, String[]> params, Map<String, Object> session) {
		this.analyzeResult = analyzeResult;
		this.params = params;
		this.session = session;
	}

	public <T> T toObj(Class<T> classz) {
		try {
			Object obj = classz.newInstance();
			BeanUtils.populate(obj, params);
			return (T) obj;
		} catch (Exception e) {
			throw new Error(e.getCause());
		}
	}

	public AnalyzeResult getAnalyzeResult() {
		return analyzeResult;
	}

	public Map<String, String[]> getParams() {
		return params;
	}

	public Map<String, Object> getSession() {
		return session;
	}
	
	public Object getSesAttr(String name){
		return session.get(name);
	}
	
	public Object removeSesAttr(String name){
		return session.remove(name);
	}
	
	public String getParam(String name){
		String [] p = params.get(name);
		if(p != null){
			return p[0];
		}
		return null;
	}
	
	public String[] getParamByValues(String name){
		return params.get(name);
	}
}
