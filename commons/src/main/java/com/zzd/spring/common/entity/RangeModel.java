/*
 * 文件名：SearchModel.java 版权：Copyright by www.newlixon.com/ 描述： 修改人：Administrator 修改时间：2018年11月7日
 * 跟踪单号： 修改单号： 修改内容：
 */

package com.zzd.spring.common.entity;


import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;


/**
 * 范围查询的模型
 * 
 * @author zhangzhende
 * @version 2018年11月7日
 * @see RangeModel
 * @since
 */

public class RangeModel implements Serializable {

    /**
     * 意义，目的和功能，以及被用到的地方<br>
     */
    private static final long serialVersionUID = 1370725525387243344L;

    /**
     * 查询范围
     */
    @ApiModelProperty(value = "需要范围查询的字段", name = "field", example = "createTime")
    private String field;

    /**
     * 下限
     */
    @ApiModelProperty(value = "下限，大于等于", name = "gte", example = "2018-11-15 12:23:23")
    private Object gte;

    /**
     * 上限
     */
    @ApiModelProperty(value = "上限，小于等于 ", name = "lte", example = "2018-11-17 12:23:23")
    private Object lte;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Object getGte() {
        return gte;
    }

    public void setGte(Object gte) {
        this.gte = gte;
    }

    public Object getLte() {
        return lte;
    }

    public void setLte(Object lte) {
        this.lte = lte;
    }

}
