package org.demo.query;

import org.jleaf.format.query.Operator;
import org.jleaf.format.query.QueryObject;

public class MsgQuery extends QueryObject {

    private Long id;

    private String title;

    private String msg;

    @Override
    public void init() {
        if (id != null) {
            this.addCondition("id", id, Operator.EQUAL);
        }

        if (title != null) {
            this.addCondition("title", VAGUE + title + VAGUE, Operator.LIKE);
        }

        if (msg != null) {
            this.addCondition("msg", VAGUE + msg + VAGUE, Operator.LIKE);
        }

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
