package org.jleaf.web.controller.result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 代表空结果,什么都不操作
 *
 * @author leaf
 * @date 2014-1-12 下午11:59:54
 */
public class NullResult extends Result {

    @Override
    public void render(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        resp.getOutputStream().close();
    }

}
