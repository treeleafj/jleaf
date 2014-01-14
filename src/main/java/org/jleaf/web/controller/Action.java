package org.jleaf.web.controller;

import java.util.Map;

public class Action {

	private AnalyzeResult analyzeResult;

	private Map<String, Object> param;
	private Map<String, Object> session;

	public Action(AnalyzeResult analyzeResult, Map<String, Object> param,
			Map<String, Object> session) {
		super();
		this.analyzeResult = analyzeResult;
		this.param = param;
		this.session = session;
	}

	public AnalyzeResult getAnalyzeResult() {
		return analyzeResult;
	}

	public Map<String, Object> getParam() {
		return param;
	}

	public Map<String, Object> getSession() {
		return session;
	}

}
