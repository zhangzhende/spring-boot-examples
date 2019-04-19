/*
 * 文件名：IndexModel.java 版权：Copyright by www.newlixon.com/ 描述： 修改人：Administrator 修改时间：2018年11月8日
 * 跟踪单号： 修改单号： 修改内容：
 */

package com.zzd.spring.common.entity;


import java.io.Serializable;
import java.util.Map;



import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


/**
 * 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author zhangzhende
 * @version 2018年11月8日
 * @see IndexModel
 * @since
 */

public class IndexModel implements Serializable {

    /**
     * 意义，目的和功能，以及被用到的地方<br>
     */
    private static final long serialVersionUID = -7238832261556641326L;

    /**
     * 
     */
    @ApiModelProperty(value = "索引，必填", name = "index", example = "books")
    @NotBlank(message = "index不能为空")
    private String index;

    /**
     * 
     */
    @ApiModelProperty(value = "类型，必填", name = "type", example = "it")
    @NotBlank(message = "type不能为空")
    private String type;

    /**
     * 
     */
    @ApiModelProperty(value = "文档集合，key为id,value为内容，必填", name = "contentMap")
    @NotNull(message = "contentMap不能为空")
    private Map<String, Object> contentMap;

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

    public Map<String, Object> getContentMap() {
        return contentMap;
    }

    public void setContentMap(Map<String, Object> contentMap) {
        this.contentMap = contentMap;
    }

}
