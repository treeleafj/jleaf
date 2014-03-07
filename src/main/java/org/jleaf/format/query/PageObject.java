package org.jleaf.format.query;

/**
 * 分页对象
 *
 * @author leaf
 * @date 2014-2-1 下午3:08:33
 */
public class PageObject {

    /**
     * 起始行
     */
    private int start = 0;

    /**
     * 条数
     */
    private int limit = 10;

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

}
