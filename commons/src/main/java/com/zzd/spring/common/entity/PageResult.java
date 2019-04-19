package com.zzd.spring.common.entity;


import java.io.Serializable;
import java.util.List;


/**
 * 
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author zhangzhende
 * @version 2018年11月6日
 * @see PageResult
 * @since
 */
public class PageResult implements Serializable {

    /**
     * 意义，目的和功能，以及被用到的地方<br>
     */
    private static final long serialVersionUID = 8397846139874758463L;

    /**
     * 
     */
    private long total;

    /**
     * 
     */
    private List<Object> rows;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<Object> getRows() {
        return rows;
    }

    public void setRows(List<Object> rows) {
        this.rows = rows;
    }

}