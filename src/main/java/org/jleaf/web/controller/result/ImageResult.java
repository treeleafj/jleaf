package org.jleaf.web.controller.result;

import java.io.File;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jleaf.web.utils.HttpHeadUtils;

/**
 * 图片专用Result(可用于一些动态生成的比如验证码)
 *
 * @author leaf
 * @date 2014-1-4 上午1:51:39
 */
public class ImageResult extends IOResult {

    public ImageResult(InputStream in) {
        super(in);
    }

    public ImageResult(IOCallBack ioCallBack) {
        super(ioCallBack);
    }

    public ImageResult(String filename, InputStream in) {
        super(filename, in);
    }

    public ImageResult(String filename, IOCallBack ioCallBack) {
        super(filename, ioCallBack);
    }

    public ImageResult(String filename, File file) {
        super(filename, file);
    }

    public ImageResult(String filename, String file) {
        super(filename, file);
    }

    @Override
    public void render(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        resp.setContentType(HttpHeadUtils.CONTENT_TYPE_JPEG);// 设定response的内容类型为图片
        resp.setHeader("Cache-Control", "no-store");
        resp.setHeader("Pragrma", "no-cache");// 设定response的缓存机制
        resp.setDateHeader("Expires", 0);
        super.render(req, resp);
    }

}
