package org.jleaf.web.controller.result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jleaf.format.json.JsonUtils;
import org.jleaf.web.utils.HttpHeadUtils;

/**
 * 返回Json结果
 *
 * @author leaf
 * @date 2014-1-3 下午6:13:15
 */
public class JsonResult extends Result {

    private Object obj;

    public JsonResult(Object obj) {
        this.obj = obj;
    }

    @Override
    public void render(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        resp.setContentType(HttpHeadUtils.CONTENT_TYPE_JSON);
        resp.setCharacterEncoding(HttpHeadUtils.DEFAULT_CHART_SET);
        if (obj == null) {
            resp.getWriter().print("{}");
        } else {
            String json = JsonUtils.toJSON(obj);
            resp.getWriter().print(json);
            resp.getWriter().close();
        }
    }

}
