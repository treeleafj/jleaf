package org.jleaf.web.controller.result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jleaf.format.json.JsonUtils;

/**
 * 返回Json结果
 * @author leaf
 * @date 2014-1-3 下午6:13:15
 */
public class JsonResult extends Result{
	
	private Object obj;
	
	public JsonResult(Object obj){
		this.obj = obj;
	}

	@Override
	public void render(HttpServletRequest req, HttpServletResponse resp) throws Exception{
		if(obj == null){
			resp.getOutputStream().print("{}");
		}else{
			String json = JsonUtils.toJSON(obj);
			resp.getOutputStream().print(json);
			resp.getOutputStream().close();
		}
	}

}