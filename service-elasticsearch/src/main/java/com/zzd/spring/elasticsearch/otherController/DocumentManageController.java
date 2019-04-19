package com.zzd.spring.elasticsearch.otherController;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.zzd.spring.elasticsearch.config.ElasticSearchConfig;
import com.zzd.spring.elasticsearch.model.User;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateRequestBuilder;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.elasticsearch.script.Script;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * Created by zhangzhende on 2018/4/26.
 */
@RestController
public class DocumentManageController {
    @Autowired
    private ElasticSearchConfig elasticSearchConfig;
    @Autowired
    private BulkProcessor bulkProcessor;

    /**
     * 四种方式索引文档
     *
     * @return
     */
    @RequestMapping(value = "/createDoc")
    public Object createDoc() throws IOException {
        Map<String, Object> map = new HashMap<>();
        TransportClient client = elasticSearchConfig.getInstance();
        //1.JSON格式的string
        String doc1 = "{\"user\":\"kimchy\",\"postDate\":\"2013-01-30\",\"message\":\"trying out Elasticsearch\"}";
        IndexResponse indexResponse = client.prepareIndex("twitter", "tweet", "1").setSource(doc1).get();
        map.put("doc1", indexResponse.status());
        //2.使用map
        Map<String, Object> doc2 = new HashMap<>();
        doc2.put("user", "kimchy");
        doc2.put("postDate", "2013-01-30");
        doc2.put("message", "trying out Elasticsearch2");
        IndexResponse indexResponse1 = client.prepareIndex("twitter", "tweet", "2").setSource(doc2).get();
        map.put("doc2", indexResponse1.status());
        //3.使用ES的帮助类XContentFactory的sonBuilder方法
        XContentBuilder doc3 = jsonBuilder().startObject()
                .field("user", "kimchy")
                .field("postDate", "2013-01-30")
                .field("message", "trying out Elasticsearch3")
                .endObject();
        IndexResponse indexResponse2 = client.prepareIndex("twitter", "tweet", "3")
                .setSource(doc3).get();
        map.put("doc3", indexResponse2.status());
        //使用Jackson序列化javabean，需要添加依赖pom文件，创建Javabean的set,get,有参无参方法
        User user = new User("张三", new Date(2013 - 1900, 1 - 1, 30), "tryingg out Elasticsearch4");
        ObjectMapper mapper = new ObjectMapper();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        mapper.setDateFormat(format);
        byte[] doc4 = mapper.writeValueAsBytes(user);
        IndexResponse indexResponse3 = client.prepareIndex("twitter", "tweet", "4").setSource(doc4).execute().actionGet();
        map.put("doc4", indexResponse3.status());
        return map;
    }

    /**
     * 获取文档，以及Getresponse各方法的返回内容
     *
     * @param id
     * @return
     * @throws UnknownHostException
     */
    @RequestMapping(value = "/getDoc")
    public Object getDoc(@RequestParam(value = "id") String id) throws UnknownHostException {
        Map<String, Object> map = new HashMap<>();
        TransportClient client = elasticSearchConfig.getInstance();
        GetResponse response = client.prepareGet("twitter", "tweet", "1").get();
        map.put("content", response.getSourceAsString());
        map.put("isExists", response.isExists());
        map.put("getIndex", response.getIndex());
        map.put("getType", response.getType());
        map.put("getId", response.getId());
        map.put("getVersion", response.getVersion());
        map.put("getSourceAsBytes", response.getSourceAsBytes());
        map.put("getSourceAsMap", response.getSourceAsMap());
        map.put("isSourceEmpty", response.isSourceEmpty());
        return map;
    }

    /**
     * 删除指定ID的文档
     *
     * @param id
     * @return
     * @throws UnknownHostException
     */
    @RequestMapping(value = "/deleteDoc")
    public Object deleteDoc(@RequestParam(value = "id", required = true) String id) throws UnknownHostException {
        Map<String, Object> map = new HashMap<>();
        TransportClient client = elasticSearchConfig.getInstance();
        DeleteResponse deleteResponse = client.prepareDelete("twitter", "tweet", "1").get();
        map.put("status", deleteResponse.status());
        map.put("type", deleteResponse.getType());
        map.put("id", deleteResponse.getId());
        map.put("version", deleteResponse.getVersion());
        return map;
    }

    /**
     * 更新文档
     *
     * @param id
     * @return
     * @throws IOException
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @RequestMapping(value = "/updateDoc")
    public Object updateDoc(@RequestParam(value = "id", required = false) String id) throws IOException, ExecutionException, InterruptedException {
        Map<String, Object> map = new HashMap<>();
        TransportClient client = elasticSearchConfig.getInstance();
        //jsonbuilder 方式
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index("twitter");
        updateRequest.type("tweet");
        updateRequest.id("1");
        updateRequest.doc(jsonBuilder()
                .startObject()
                .field("gender1", "male").endObject());
        client.update(updateRequest).get();
//script 方式
        UpdateRequest updateRequest1 = new UpdateRequest("twitter", "tweet", "1")
                .script(new Script("ctx._source.gender2=\"male\""));
        client.update(updateRequest1).get();
//prepareUpdate方式
        client.prepareUpdate("twitter", "tweet", "1")
                .setScript(new Script("ctx._source.gender3=\"male\""))
                .get();

        client.prepareUpdate("twitter", "tweet", "1")
                .setDoc(jsonBuilder().startObject()
                        .field("gender4", "male").endObject()).get();
        //upsert方式，有则更新无则插入
        IndexRequest indexRequest = new IndexRequest("twitter", "tweet", "2")
                .source(jsonBuilder()
                        .startObject()
                        .field("name", "Joe Smith")
                        .field("gender", "male")
                        .endObject());
        UpdateRequest updateRequest2 = new UpdateRequest("twitter", "tweet", "2")
                .doc(jsonBuilder().startObject().field("gender", "male").endObject()).upsert(indexRequest);
        return map;
    }

    /**
     * 查询删除
     *
     * @param id
     * @return
     * @throws UnknownHostException
     */
    @RequestMapping(value = "/deleteByQuery")
    public Object deleteByQuery(@RequestParam(value = "id", required = false) String id) throws UnknownHostException {
        Map<String, Object> map = new HashMap<>();
        TransportClient client = elasticSearchConfig.getInstance();
        BulkByScrollResponse bulkByScrollResponse = DeleteByQueryAction.INSTANCE.newRequestBuilder(client)
                .filter(QueryBuilders.matchQuery("title", "java")).source("books").get();
        long deleted = bulkByScrollResponse.getDeleted();
        map.put("deleted num", deleted);
        return map;
    }

    /**
     * 批量獲取
     *
     * @param id
     * @return
     * @throws UnknownHostException
     */
    @RequestMapping(value = "/multiGet")
    public Object multiGet(@RequestParam(value = "id", required = false) String id) throws UnknownHostException {
        Map<String, Object> map = new HashMap<>();
        TransportClient client = elasticSearchConfig.getInstance();
        MultiGetResponse multiGetItemResponses = client.prepareMultiGet()
                .add("twitter", "tweet", "1")
                .add("twitter", "tweet", "2", "3", "4")
                .add("books", "IT", "3")
                .get();
        for (MultiGetItemResponse itemResponse : multiGetItemResponses) {//遍历
            GetResponse response = itemResponse.getResponse();
            if (response != null && response.isExists()) {//检验文档是否存在
                String json = response.getSourceAsString();
                map.put(response.getId(), json);
            }
        }
        return map;
    }

    /**
     * 批量请求
     *
     * @param id
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/bulkRequest")
    public Object bulkRequest(@RequestParam(value = "id", required = false) String id) throws IOException {
        Map<String, Object> map = new HashMap<>();
        TransportClient client = elasticSearchConfig.getInstance();

        BulkRequestBuilder bulkRequest = client.prepareBulk();
        //索引文档
        IndexRequestBuilder indexRequestBuilder = client
                .prepareIndex("twitter", "tweet", "1")
                .setSource(jsonBuilder().startObject().field("user", "tom")
                        .field("postDate", new Date()).field("message", "another post").endObject());
        DeleteRequestBuilder deleteRequestBuilder = client.prepareDelete("twitter", "tweet", "1");
        UpdateRequestBuilder updateRequestBuilder = client.prepareUpdate("twiteer", "tweet", "1")
                .setDoc(jsonBuilder().startObject().field("message", "update").endObject());
        bulkRequest.add(indexRequestBuilder)
                .add(deleteRequestBuilder)
                .add(updateRequestBuilder)
                .execute().actionGet();
        return map;
    }

    public Object bulkPro() throws IOException {
        TransportClient client = elasticSearchConfig.getInstance();
        IndexRequest indexRequest = new IndexRequest("user", "student", "1").source(jsonBuilder().startObject().field("user", "tom")
                .field("postDate", new Date()).field("message", "another post").endObject());
        bulkProcessor.add(indexRequest);
        return "OK";

    }

}




































