

package com.zzd.restclient.service.impl;


import com.zzd.restclient.config.ElasticSearchConfig;
import com.zzd.restclient.config.EsValueConfig;
import com.zzd.restclient.utils.ConstantsES;
import com.zzd.restclient.utils.Utils;
import com.zzd.spring.common.entity.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.*;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


/**
 * 〈一句话功能简述〉 〈功能详细描述〉
 *
 * @author zhangzhende
 * @version 2018年11月6日
 * @see CommonService
 * @since
 */
@Service
public class CommonService {
    /**
     *
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CommonService.class);

    @Autowired
    private EsValueConfig esValueConfig;
    /**
     *
     */
    @Autowired
    private ElasticSearchConfig elasticSearchConfig;

    /**
     * 批量插入数据，公用方法，有则覆盖，无则插入
     *
     * @param index 索引
     * @param type  类型
     * @param map   数据列表，id为key，object为value
     * @throws UnknownHostException s
     * @see
     */
    public void upsertData(String index, String type, Map<String, Object> map)
            throws IOException {
        RestHighLevelClient client = elasticSearchConfig.getRestHighLevelClient();
        BulkRequest bulkRequest = new BulkRequest();
        // 构建批量查询
        for (Entry<String, Object> entry : map.entrySet()) {
            bulkRequest.add(new IndexRequest(index, type).id(entry.getKey()).source(Utils.parseToMap(entry.getValue())));
        }
        BulkResponse response = client.bulk(bulkRequest, RequestOptions.DEFAULT);
        LOGGER.info("CommonService-upsertData:用时{}", response.getTook());
    }

    /**
     * 更新数据，更新部分字段，有则更新，无则插入
     *
     * @param index
     * @param type
     * @param map
     * @throws UnknownHostException
     * @see
     */
    public void updateData(String index, String type, Map<String, Object> map)
            throws IOException {
        RestHighLevelClient restHighLevelClient = elasticSearchConfig.getRestHighLevelClient();
        BulkRequest bulkRequest = new BulkRequest();
        // 构建批量更新
        for (Entry<String, Object> entry : map.entrySet()) {
            bulkRequest.add(new UpdateRequest(index, type, entry.getKey()).doc());
            IndexRequest indexRequest = new IndexRequest(index, type, entry.getKey()).source(
                    Utils.parseToMap(entry.getValue()));
//            upsert的作用就是当不存在待更新的数据时，就插入这条数据
            UpdateRequest updateRequest = new UpdateRequest(index, type, entry.getKey()).doc(indexRequest).upsert(
                    indexRequest);
            bulkRequest.add(updateRequest);
        }
        BulkResponse response = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        LOGGER.info("CommonService-updateData:用时{}", response.getTook());
    }

    /**
     * 通過主鍵查詢，rest low level client来查询
     *
     * @param param
     * @return Object
     * @throws IOException s
     * @see
     */
    public Object serachLowById(IdParam param)
            throws IOException {
        RestClient restClient = elasticSearchConfig.getRestClient();
        Request request = new Request("POST", new StringBuilder("/index/type/").append(param.getId().toString()).append("_search").toString());
        Response response = restClient.performRequest(request);
        String hits = EntityUtils.toString(response.getEntity());
        return hits;
    }

    /**
     * 通过主键查询，rest high level client
     *
     * @param param
     * @return
     * @throws IOException
     */
    public Object serachHighById(IdParam param)
            throws IOException {
        Object result = new Object();
        RestHighLevelClient restHighLevelClient = elasticSearchConfig.getRestHighLevelClient();
        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        // 构建查询
        QueryBuilder queryBuilder = QueryBuilders.idsQuery(param.getType()).addIds(
                String.valueOf(param.getId()));
        sourceBuilder.query(queryBuilder);
        if (param.getResultFields() != null && param.getResultFields().length > 0) {
            sourceBuilder.fetchSource(param.getResultFields(), null);
        }
        searchRequest.source(sourceBuilder);
        searchRequest.indices(param.getIndex());
        SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = response.getHits();
//        id查询，讲道理只会有一条
        for (SearchHit searchHit : hits) {
            result = searchHit.getSourceAsMap();
        }
        return result;
    }

    /**
     * 查询数据
     *
     * @param params
     * @return PageResult
     * @throws UnknownHostException s
     * @see
     */
    @SuppressWarnings("rawtypes")
    public PageResult searchData(SearchParam params)
            throws IOException {
        PageResult result = new PageResult();
        SearchRequest searchRequest = new SearchRequest();
        RestHighLevelClient restHighLevelClient = elasticSearchConfig.getRestHighLevelClient();
        BoolQueryBuilder boolQueryAll = QueryBuilders.boolQuery();
        // 组装字段查询条件
        if (null != params.getModelList() && params.getModelList().size() > 0) {
            boolQueryAll = buildSearchFields(boolQueryAll, params);
        }
        // 添加范围查询
        boolQueryAll = buildRangeQuery(boolQueryAll, params);
        // type
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        searchRequest.indices(params.getIndex());
        if (params.getTypes() != null && params.getTypes().length > 0) {
            searchRequest.types(params.getTypes());
        }
        // 返回字段
        sourceBuilder.query(boolQueryAll);
        if (params.getResultFields() != null && params.getResultFields().length > 0) {
            sourceBuilder.fetchSource(params.getResultFields(), null);
        }
        // 添加排序
        if (StringUtils.isNotBlank(params.getSortField())) {
            SortBuilder sortBuilder = SortBuilders.fieldSort(params.getSortField()).order(
                    getSort(params.getSortType())).missing("_last").unmappedType("string");
            sourceBuilder.sort(sortBuilder);
        }
        // 分页数据
        sourceBuilder.from((params.getPageNum() - 1) * params.getPageSize()).size(params.getPageSize());
        // 高亮
        if (params.getHighLightFields() != null && params.getHighLightFields().size() > 0) {
            params.setIsHighlight(true);
            HighlightBuilder highlightBuilder = new HighlightBuilder();
            highlightBuilder.preTags(ConstantsES.HIGHT_LIGHT_PRE).postTags(
                    ConstantsES.HIGHT_LIGHT_POST).requireFieldMatch(true);
            for (String field : params.getHighLightFields()) {
                highlightBuilder.field(field);
            }
            sourceBuilder.highlighter(highlightBuilder);
        } else {
            params.setIsHighlight(false);
        }
        SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = response.getHits();
        List<Object> list = new ArrayList<Object>();
        result.setTotal(hits.getTotalHits());

        for (SearchHit searchHit : hits) {
            Map<String, Object> map = searchHit.getSourceAsMap();
            if (params.getIsHighlight()) {
                Map<String, String> hiMap = getHighLightFileds(searchHit);
                map.put(ConstantsES.KEY_HIGHLIGHTFIELDS, hiMap);
                for (Entry<String, String> entry : hiMap.entrySet()) {
//                    覆盖高亮部分
                    map.put(entry.getKey(), entry.getValue());
                }
            }
            list.add(map);
        }
        result.setRows(list);
        return result;
    }

    /**
     * 组装，范围查询
     *
     * @param boolQueryAll
     * @param params
     * @return BoolQueryBuilder
     * @see
     */
    public BoolQueryBuilder buildRangeQuery(BoolQueryBuilder boolQueryAll, SearchParam params) {
        if (null != params.getRangeList() && params.getRangeList().size() > 0) {
            for (RangeModel model : params.getRangeList()) {
                if (StringUtils.isNotBlank(model.getField())) {
                    RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery(model.getField());
                    if (null != model.getGte()) {
                        rangeQuery.gte(model.getGte());
                    }
                    if (null != model.getLte()) {
                        rangeQuery.lte(model.getLte());
                    }
                    boolQueryAll.must(rangeQuery);
                }
            }
        }
        return boolQueryAll;
    }

    /**
     * getModelList组装查询条件，not，and，or,
     *
     * @param boolQueryAll
     * @param params
     * @return BoolQueryBuilder
     * @see
     */
    public BoolQueryBuilder buildSearchFields(BoolQueryBuilder boolQueryAll, SearchParam params) {
        for (SearchModel model : params.getModelList()) {
            BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
            // 查询字段建并关系，即同时都满足
            if (model.getSearchType().equalsIgnoreCase(ConstantsES.SearchType.AND)) {
                // 如果没有指定字段，那么就认为是全文查询
                if (null != model.getSearchFields() && model.getSearchFields().size() == 0) {
                    QueryBuilder matchQuerybool = QueryBuilders.matchQuery("_all",
                            model.getContent());
                    boolQuery.must(matchQuerybool);
                } else {
                    for (String field : model.getSearchFields()) {
                        QueryBuilder matchQuerybool = QueryBuilders.matchQuery(field,
                                model.getContent());
                        //.operator(Operator.AND);
                        boolQuery.must(matchQuerybool);
                    }
                }
            }
            // 查询字段间或关系，即至少一个满足
            else if (model.getSearchType().equalsIgnoreCase(ConstantsES.SearchType.OR)) {
                // 如果没有指定字段，那么就认为是全文查询
                if (null != model.getSearchFields() && model.getSearchFields().size() == 0) {
                    QueryBuilder matchQuerybool = QueryBuilders.matchQuery("_all",
                            model.getContent());
                    //.operator(Operator.AND);
                    boolQuery.should(matchQuerybool);
                } else {
                    for (String field : model.getSearchFields()) {
                        QueryBuilder matchQuerybool = QueryBuilders.matchQuery(field,
                                model.getContent());
                        boolQuery.should(matchQuerybool);
                    }
                }
            }
            // 查询字段间非关系，即同时不满足
            else if (model.getSearchType().equalsIgnoreCase(ConstantsES.SearchType.NOT)) {
                // 如果没有指定字段，那么就认为是全文查询
                if (null != model.getSearchFields() && model.getSearchFields().size() == 0) {
                    QueryBuilder matchQuerybool = QueryBuilders.matchQuery("_all",
                            model.getContent());
                    //.operator(Operator.AND);
                    boolQuery.mustNot(matchQuerybool);
                } else {
                    for (String field : model.getSearchFields()) {
                        QueryBuilder matchQuerybool = QueryBuilders.matchQuery(field,
                                model.getContent());
                        boolQuery.mustNot(matchQuerybool);
                    }
                }
            }
            //查询字段必须为空
            else {
                if (null != model.getSearchFields() && model.getSearchFields().size() > 0) {
                    for (String field : model.getSearchFields()) {
                        QueryBuilder matchQuerybool = QueryBuilders.existsQuery(field);
                        boolQuery.mustNot(matchQuerybool);
                    }
                }
            }
            boolQueryAll.must(boolQuery);
        }
        return boolQueryAll;
    }

    /**
     * 按照ids删除数据，公用方法，
     *
     * @param index 索引
     * @param type  类型
     * @param list  id列表
     * @throws IOException s
     * @see
     */
    public void deleteDataByIds(String index, String type, List<Long> list)
            throws IOException {
        RestHighLevelClient restHighLevelClient = elasticSearchConfig.getRestHighLevelClient();
        BulkRequest bulkRequest = new BulkRequest();
        for (Long id : list) {
            DeleteRequest request = new DeleteRequest();
            request.id(String.valueOf(id));
            request.index(index);
            request.type(type);
            // 构建批量删除

//            bulkRequest.add(new DeleteRequest(index,type,String.valueOf(id)));
            bulkRequest.add(request);
        }
        BulkResponse response = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        LOGGER.info("CommonService-deleteDataByIds:用时{}", response.getTook());
    }

    /**
     * 获取高亮字段
     *
     * @param searchHit
     * @return Map<String                                                               ,                                                                                                                               Object>
     * @see
     */
    public Map<String, String> getHighLightFileds(SearchHit searchHit) {
        Map<String, String> hiMap = new HashMap<String, String>();
        Map<String, HighlightField> highMap = searchHit.getHighlightFields();
        if (highMap != null) {
            for (Entry<String, HighlightField> entry : highMap.entrySet()) {
                if (entry.getValue() != null && entry.getValue().getFragments() != null) {
                    Text[] text = entry.getValue().getFragments();
                    String content = getTextToString(text);
                    hiMap.put(entry.getKey(), content);
                }
            }
        }
        return hiMap;
    }

    /**
     * parse text[] to String
     *
     * @param text
     * @return String
     * @see
     */
    public String getTextToString(Text[] text) {
        StringBuilder builder = new StringBuilder();
        if (text != null) {
            for (Text str : text) {
                builder.append(str);
            }
        }
        return builder.toString();
    }

    /**
     * 获取排序方式
     *
     * @param sortType
     * @return SortOrder
     * @see
     */
    public SortOrder getSort(String sortType) {
        if (StringUtils.isNotBlank(sortType)) {
            if (sortType.equalsIgnoreCase(ConstantsES.SortType.DESC)) {
                return SortOrder.DESC;
            } else if (sortType.equalsIgnoreCase(ConstantsES.SortType.ASC)) {
                return SortOrder.ASC;
            } else {
                return SortOrder.DESC;
            }
        } else {
            return SortOrder.DESC;
        }
    }
}
