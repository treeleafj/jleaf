package org.jleaf.web.controller.result;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * IOStream结果,用于流传送
 *
 * @author leaf
 * @date 2014-1-4 上午1:19:59
 */
@SuppressWarnings("serial")
public class IOResult extends Result {

    private InputStream in;

    /**
     * 文件名
     */
    private String filename;

    private IOCallBack ioCallBack;

    public IOResult(InputStream in) {
        super();
        this.in = in;
    }

    public IOResult(IOCallBack ioCallBack) {
        super();
        this.ioCallBack = ioCallBack;
    }

    public IOResult(String filename, InputStream in) {
        super();
        this.in = in;
        this.filename = filename;
    }

    public IOResult(String filename, File file) {
        super();
        try {
            this.in = new FileInputStream(file);
            this.filename = filename;
        } catch (FileNotFoundException e) {
            throw new Error(e.getCause());
        }
    }

    public IOResult(String filename, String file) {
        super();
        try {
            this.in = new FileInputStream(file);
            this.filename = filename;
        } catch (FileNotFoundException e) {
            throw new Error(e.getCause());
        }
    }

    public IOResult(String filename, IOCallBack ioCallBack) {
        super();
        this.filename = filename;
        this.ioCallBack = ioCallBack;
    }

    @Override
    public void render(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        OutputStream out = resp.getOutputStream();

        if (filename != null) {
            resp.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(filename, "UTF-8"));
        }

        if (ioCallBack != null) {
            try {
                ioCallBack.callback(out);
            } catch (Exception e) {
                close(out);
                throw e;
            }
        } else if (in != null) {
            try {
                byte[] buffer = new byte[1024 * 4];
                int n = 0;
                while (-1 != (n = in.read(buffer))) {
                    out.write(buffer, 0, n);
                }
            } catch (Exception e) {
                throw e;
            } finally {
                close(out);
                close(in);
            }
        }
    }

    private boolean close(InputStream in) {
        if (in != null) {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    private boolean close(OutputStream out) {
        if (out != null) {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    public InputStream getIn() {
        return in;
    }

    public void setIn(InputStream in) {
        this.in = in;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public IOCallBack getIoCallBack() {
        return ioCallBack;
    }

    public void setIoCallBack(IOCallBack ioCallBack) {
        this.ioCallBack = ioCallBack;
    }

}
