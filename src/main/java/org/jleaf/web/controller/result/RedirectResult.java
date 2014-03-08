package org.jleaf.web.controller.result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class RedirectResult extends Result {

    private String url;

    public RedirectResult(String url) {
        super();
        this.url = url;
    }


    @Override
    public void render(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        resp.sendRedirect(url);
        resp.getOutputStream().close();
    }

}
