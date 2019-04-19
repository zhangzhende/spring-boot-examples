package com.zzd.spring.elasticsearch.otherController;

import com.zzd.spring.elasticsearch.config.ElasticSearchConfig;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangzhende on 2018/5/3.
 */
@RestController
public class QueryController {
    @Autowired
    ElasticSearchConfig elasticSearchConfig;

    /**
     * 测试QueryBuilders构建查询语句查询
     *
     * @param id
     * @return
     * @throws UnknownHostException
     */
    @RequestMapping(value = "/getQuery")
    public Object getQuery(@RequestParam(value = "id", required = false) String id) throws UnknownHostException {
        Map<String, Object> map = new HashMap<>();
        TransportClient client = elasticSearchConfig.getInstance();
        QueryBuilder queryBuilder = QueryBuilders.matchQuery("title", "python").operator(Operator.AND);
        HighlightBuilder highlightBuilder = new HighlightBuilder().field("title").preTags("<span style=\"color:red\"").preTags("</span>");
        SearchResponse response = client.prepareSearch("books").setQuery(queryBuilder).highlighter(highlightBuilder).setSize(100).get();
        SearchHits hits = response.getHits();
        for (SearchHit hit : hits) {
            map.put(hit.getId(), hit.getSourceAsString());
            Text[] text = hit.getHighlightFields().get("title").getFragments();//高亮字段的高亮内容
            if (text != null) {
                for (Text str : text) {
                    System.out.println();
                }
            }
        }
        return map;
    }

    /**
     * 全文查询
     *
     * @param id
     * @return
     */
    @RequestMapping("/showQuerys")
    public Object showQuerys(@RequestParam(value = "id", required = false) String id) {
        Map<String, Object> map = new HashMap<>();
        //全文检索
        QueryBuilder matchAllQuery = QueryBuilders.matchAllQuery();
        //match_phrase
        QueryBuilder matchPhraseQuery = QueryBuilders.matchPhraseQuery("title", "java");

        QueryBuilder matchPhrasePrefixQuery = QueryBuilders.matchPhrasePrefixQuery("title", "ja");

        QueryBuilder multiMatchQuery = QueryBuilders.multiMatchQuery("java", "title", "content");

        QueryBuilder commomTermsQuery = QueryBuilders.commonTermsQuery("title", "java");

        QueryBuilder queryStringQuery = QueryBuilders.queryStringQuery("+python -java");

        QueryBuilder simpleQueryString = QueryBuilders.simpleQueryStringQuery("+python -java");

        return map;
    }

    /**
     * 词项查询
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/termsQuery", method = RequestMethod.GET)
    public Object termsQuery(@RequestParam(value = "id", required = false) String id) {
        Map<String, Object> map = new HashMap<>();

        QueryBuilder termQuery = QueryBuilders.termQuery("title", "java");

        QueryBuilder termsQuery = QueryBuilders.termsQuery("title", "java", "python");

        QueryBuilder rangeQuery = QueryBuilders.rangeQuery("price").from(50).to(70).includeLower(true).includeUpper(false);

        QueryBuilder existsQuery = QueryBuilders.existsQuery("language");

        QueryBuilder prefixQuery = QueryBuilders.prefixQuery("description", "win");

        QueryBuilder wildCardQuery = QueryBuilders.wildcardQuery("author", "张*");

        QueryBuilder regexpQuery = QueryBuilders.regexpQuery("author", "Br.*");

        QueryBuilder fuzzyQuery = QueryBuilders.fuzzyQuery("title", "javascritp");

        QueryBuilder idsQuery = QueryBuilders.idsQuery().addIds("1", "2");
        return map;
    }

    /**
     * 复合查询
     * @param id
     * @return
     */
    @RequestMapping(value = "/fuheQuery",method = RequestMethod.GET)
    public Object fuheQuery(@RequestParam(value = "id",required = false)String id){
        Map<String ,Object> map =new HashMap<>();
        QueryBuilder constantScoreQuery=QueryBuilders.constantScoreQuery(QueryBuilders.termQuery("title","java")).boost(2.0F);

        QueryBuilder disMaxQuery=QueryBuilders.disMaxQuery().
                add(QueryBuilders.termQuery("title","java"))
                .add(QueryBuilders.termQuery("title","python"))
                .boost(1.2F)
                .tieBreaker(0.7F);

        QueryBuilder matchQuery1bool=QueryBuilders.matchQuery("title","Java");
        QueryBuilder matchQuery2bool=QueryBuilders.matchQuery("description","虚拟机");
        QueryBuilder rangeQuerybool=QueryBuilders.rangeQuery("price").gte(70);
        QueryBuilder boolQuery=QueryBuilders.boolQuery().must(matchQuery1bool).should(matchQuery2bool).mustNot(rangeQuerybool);

        IndicesQueryBuilder indicesQuery =QueryBuilders.indicesQuery(matchQuery1bool,"books","books2")
                .noMatchQuery(matchQuery2bool);

        QueryBuilder matchQueryboosting=QueryBuilders.matchQuery("title","python");
        QueryBuilder rangeQueryboosting=QueryBuilders.rangeQuery("publish_time").lte("2015-01-01");
        QueryBuilder boostingQuery=QueryBuilders.boostingQuery(matchQueryboosting,rangeQueryboosting).negativeBoost(0.2F);
        return map;
    }

    /**
     * 嵌套查询，但是似乎有问题
     * @return
     */
    @RequestMapping(value = "/qiantaoQuery",method = RequestMethod.GET)
    public Object qiantaoQuery(){
        Map<String ,Object> map =new HashMap<>();

        //nestedQuery
        //---
        QueryBuilder rangeQuery=QueryBuilders.rangeQuery("dob").gte("1980-01-01");
        return map;
    }
}
