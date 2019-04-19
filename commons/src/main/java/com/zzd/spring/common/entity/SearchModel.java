/*
 * 文件名：SearchModel.java
 * 版权：Copyright by www.newlixon.com/
 * 描述：
 * 修改人：Administrator
 * 修改时间：2018年11月7日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.zzd.spring.common.entity;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;

/**
 * 查询范围 内容
 * @author zhangzhende
 * @version 2018年11月7日
 * @see SearchModel
 * @since
 */

public class SearchModel implements Serializable{

    /**
     * 意义，目的和功能，以及被用到的地方<br>
     */
    private static final long serialVersionUID = 1370725525387243344L;

    /**
     * 查询范围
     */
    @ApiModelProperty(value = "查询范围", name = "searchFields")
    private List<String> searchFields;

    /**
     * 
     */
    @ApiModelProperty(value = "查询内容", name = "content",example="哈哈")
    private String content;


    /**
     * 查询类型，not ,and ,or
     */
    @ApiModelProperty(value = "查询类型，not ,and ,or,notexist ", name = "searchType",example="and")
    private String searchType;


    public List<String> getSearchFields() {
        return searchFields;
    }


    public void setSearchFields(List<String> searchFields) {
        this.searchFields = searchFields;
    }


    public String getContent() {
        return content;
    }


    public void setContent(String content) {
        this.content = content;
    }


    public String getSearchType() {
        return searchType;
    }


    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

}
