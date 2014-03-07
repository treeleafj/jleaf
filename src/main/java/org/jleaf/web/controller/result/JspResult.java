package org.jleaf.web.controller.result;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class JspResult extends Result {

    private String jsp;

    private Object obj;

    /**
     * @param jsp jsp地址
     */
    public JspResult(String jsp) {
        this.jsp = jsp;
    }

    /**
     * @param jsp jsp地址
     * @param obj 存进Request Attribute的对象,key为"obj"
     */
    public JspResult(String jsp, Object obj) {
        this.jsp = jsp;
        this.obj = obj;
    }

    @Override
    public void render(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        req.setAttribute("obj", obj);
        RequestDispatcher rd = req.getRequestDispatcher(jsp);
        rd.forward(req, resp);
    }

    /**
     * 将对象的类名首字母小写
     *
     * @param obj
     * @return
     */
    public String getName(Object obj) {
        if (obj != null) {
            String name = obj.getClass().getSimpleName();
            return name.substring(0, 1).toLowerCase() + name.substring(1);
        }
        return "obj";
    }

}
