/*
 * 文件名：SearchParam.java 版权：Copyright by www.newlixon.com/ 描述： 修改人：Administrator 修改时间：2018年11月6日
 * 跟踪单号： 修改单号： 修改内容：
 */

package com.zzd.spring.common.entity;


import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.Max;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;


/**
 * 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author zhangzhende
 * @version 2018年11月6日
 * @see SearchParam
 * @since
 */

public class SearchParam implements Serializable {

    /**
     * 意义，目的和功能，以及被用到的地方<br>
     */
    private static final long serialVersionUID = 3039030142623774758L;

    /**
     * 索引名
     */
    @ApiModelProperty(value = "索引名,必填项", name = "index", example = "mytest")
    @NotBlank(message = "索引名不能为空")
    private String index;

    /**
     * type名
     */
    @ApiModelProperty(value = "type名列表，可选", name = "types")
    private String[] types;

    /**
     * 查询条件
     */
    @ApiModelProperty(value = "查询条件，这一级条件之间是并关系，可选", name = "modelList")
    private List<SearchModel> modelList;

    /**
     * 范围查询的条件
     */
    @ApiModelProperty(value = "范围查询的条件，可选", name = "rangeList")
    private List<RangeModel> rangeList;
    /**
     * 返回字段
     */
    @ApiModelProperty(value = "返回字段，非必填", name = "resultFields")
    private String[] resultFields;

    /**
     * 页码
     */
    @ApiModelProperty(value = "页码，可选", name = "pageNum", example = "1")
    private Integer pageNum;

    /**
     * 页面大小
     */
    @ApiModelProperty(value = "页面大小，可选", name = "pageSize", example = "10")
    @Max(100)
    private Integer pageSize;

    /**
     * 是否高亮展示
     */
    @ApiModelProperty(value = "是否高亮展示，可选,高亮方式为<span style=\"color:red\"></span>", name = "isHighlight", example = "true", hidden = true)
    private Boolean isHighlight;

    /**
     * 高亮内容
     */
    @ApiModelProperty(value = "返回高亮字段，非必填", name = "highLightFields")
    private List<String> highLightFields;

    /**
     * 排序字段
     */
    @ApiModelProperty(value = "排序字段，非必填", name = "sortField", example = "createTime")
    private String sortField;

    /**
     * 排序方式，desc,asc,排序字段未填无效，非必填
     */
    @ApiModelProperty(value = "排序方式，desc,asc,排序字段未填无效，非必填", name = "sortField", example = "desc")
    private String sortType;

    public Boolean getIsHighlight() {
        return isHighlight;
    }

    public void setIsHighlight(Boolean isHighlight) {
        this.isHighlight = isHighlight;
    }

    public List<String> getHighLightFields() {
        return highLightFields;
    }

    public void setHighLightFields(List<String> highLightFields) {
        this.highLightFields = highLightFields;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String[] getTypes() {
        return types;
    }

    public void setTypes(String[] types) {
        this.types = types;
    }

    public String[] getResultFields() {
        return resultFields;
    }

    public void setResultFields(String[] resultFields) {
        this.resultFields = resultFields;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public List<SearchModel> getModelList() {
        return modelList;
    }

    public void setModelList(List<SearchModel> modelList) {
        this.modelList = modelList;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    public List<RangeModel> getRangeList() {
        return rangeList;
    }

    public void setRangeList(List<RangeModel> rangeList) {
        this.rangeList = rangeList;
    }

}
