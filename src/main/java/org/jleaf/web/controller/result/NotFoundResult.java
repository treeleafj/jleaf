package org.jleaf.web.controller.result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * leaf
 * 14-3-1 下午7:17.
 */
@SuppressWarnings("serial")
public class NotFoundResult extends Result {

    @Override
    public void render(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
    }

}
