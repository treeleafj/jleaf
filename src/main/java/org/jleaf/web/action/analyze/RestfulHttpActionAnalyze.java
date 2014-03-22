package org.jleaf.web.action.analyze;

import java.util.HashMap;
import java.util.Map;

import org.jleaf.web.annotation.HttpMethod;
import org.jleaf.web.utils.WebUtils;

/**
 * Restful的解析器
 * leaf
 * 14-3-7 上午1:04.
 */
@SuppressWarnings("serial")
public class RestfulHttpActionAnalyze extends HttpActionAnalyze {
	
	private final static String METHOD_NAME = "_method";

    public RestfulHttpActionAnalyze(AnalyzeParam analyzeParam) {
        super(analyzeParam);
    }

    @Override
    public AnalyzeResult analyze() {
    	String _method = getAnalyzeParam().getRequest().getParameter(METHOD_NAME);
    	HttpMethod hm;
    	if(_method != null){
    		hm = WebUtils.analyzeHttpMehotd(_method);
    	}else{
    		hm = WebUtils.analyzeHttpMehotd(getAnalyzeParam().getHttpMethod());
    	}

        String uri = this.getAnalyzeParam().getUri();
        String postfix = DEFAULTPostfix;

        // 先去掉开头的 '/'
        if (uri.charAt(0) == '/') {
            uri = uri.substring(1);
        }
        //如果uri的最后包含'/',且长度大于1
        if(uri.endsWith("/") && uri.length() > 1){
        	uri = uri.substring(0,uri.length() - 1);
        }
        //截取最后的.***后缀
        int dotIndex = uri.lastIndexOf(".");
        if (dotIndex >= 0) {
            postfix = uri.substring(dotIndex + 1);
            uri = uri.substring(0, dotIndex);
        }

        Map<String,Object> params = new HashMap<String,Object>(this.getAnalyzeParam().getRequest().getParameterMap());

        switch (hm) {
            case GET:
	            {
	            	if(uri.endsWith("/edit")){//edit
	            		String temp = uri.substring(0,uri.lastIndexOf('/'));
	            		int index = temp.lastIndexOf('/');
	            		String id = temp.substring(index + 1);
	            		params.put("id",id);
	            		uri = temp.substring(0,index) + "/edit";
	            	}else if(uri.endsWith("/new")){//create
	            		int index = uri.lastIndexOf("/new");
	            		uri = uri.substring(0,index) + "/create";
	            	}else{//index或者get
		                int index = uri.lastIndexOf('/');
		                if(index > 0){
			                String id = uri.substring(index + 1);
			                uri = uri.substring(0,index) + "/get";
			                params.put("id",id);
		                }else{
		                	uri += "/index";
		                }
	            	}
	            }
                break;
            case POST:
	            {
	            	int index = uri.lastIndexOf('/'); 
	            	if(index > 0 && index +1 <= uri.length()){
	            		String id = uri.substring(index + 1);
	            		if(params.get("id") ==  null){
	            			params.put("id", id);
	            		}
	            		uri = uri.substring(0,index);
	            	}
	                uri += "/update";
	            }
                break;
            case PUT:
	            {
	                uri += "/save";
	            }
                break;
            case DELETE:
	            {
	            	int index = uri.lastIndexOf('/'); 
	            	if(index > 0 && index +1 <= uri.length()){
	            		String id = uri.substring(index + 1);
	            		if(params.get("id") ==  null){
	            			params.put("id", id);
	            		}
	            		uri = uri.substring(0,index);
	            	}
	                uri += "/delete";
	            }
                break;
        }

        return new AnalyzeResult(uri, postfix, hm, params);
    }
}
