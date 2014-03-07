package org.jleaf.web.controller.result;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 一次Action返回的结果
 *
 * @author leaf
 */
public abstract class Result implements Serializable {

    /**
     * 渲染结果
     *
     * @param req
     * @param resp
     */
    public abstract void render(HttpServletRequest req, HttpServletResponse resp) throws Exception;

}
