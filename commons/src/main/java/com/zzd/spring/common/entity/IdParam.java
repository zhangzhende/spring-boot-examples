/*
 * 文件名：IdParam.java 版权：Copyright by www.newlixon.com/ 描述： 修改人：Administrator 修改时间：2018年11月6日 跟踪单号：
 * 修改单号： 修改内容：
 */

package com.zzd.spring.common.entity;


import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;


/**
 * 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author zhangzhende
 * @version 2018年11月6日
 * @see IdParam
 * @since
 */

public class IdParam implements Serializable {

    /**
     * 意义，目的和功能，以及被用到的地方<br>
     */
    private static final long serialVersionUID = -7033655786808642029L;

    /**
     * 标识
     */
    @ApiModelProperty(value = "主键编号，必填", name = "id", example = "1")
    @NotNull(message = "主键编号")
    private Long id;

    /**
     * 索引名
     */
    @ApiModelProperty(value = "索引名，必填", name = "index", example = "mytest")
    @NotBlank(message = "索引名不能为空")
    private String index;

    /**
     * type名
     */
    @ApiModelProperty(value = "type名，可选", name = "type", example = "ncqLandPublish")
    private String type;

    /**
     * 返回字段
     */
    @ApiModelProperty(value = "返回字段，可选", name = "resultFields", example = "['ncqLandPublish']")
    private String[] resultFields;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String[] getResultFields() {
        return resultFields;
    }

    public void setResultFields(String[] resultFields) {
        this.resultFields = resultFields;
    }

}
