/*
 * 文件名：IdParam.java 版权：Copyright by www.newlixon.com/ 描述： 修改人：Administrator 修改时间：2018年11月6日 跟踪单号：
 * 修改单号： 修改内容：
 */

package com.zzd.spring.common.entity;


import java.io.Serializable;
import java.util.List;


import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;


/**
 * 〈一句话功能简述〉 〈功能详细描述〉
 *
 * @author zhangzhende
 * @version 2018年11月6日
 * @see DeleteModel
 */

public class DeleteModel implements Serializable {

    /**
     * 意义，目的和功能，以及被用到的地方<br>
     */
    private static final long serialVersionUID = 2983966291184467676L;

    /**
     * 标识
     */
    @ApiModelProperty(value = "主键编号，不能为空，必填", name = "ids", example = "[1]")
    @NotEmpty(message = "主键编号")
    private List<Long> ids;

    /**
     * 索引名
     */
    @ApiModelProperty(value = "索引名，必填", name = "index", example = "mytest")
    @javax.validation.constraints.NotEmpty
    private String index;

    /**
     * type名
     */
    @ApiModelProperty(value = "type名", name = "type", example = "ncqLandPublish")
    @NotBlank(message = "type名不能为空")
    private String type;

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

    public void setType(String type) {
        this.type = type;
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

}
