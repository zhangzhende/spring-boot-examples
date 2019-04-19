package com.zzd.spring.elasticsearch.otherController;

import com.carrotsearch.hppc.cursors.ObjectObjectCursor;
import com.zzd.spring.elasticsearch.config.ElasticSearchConfig;
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesResponse;
import org.elasticsearch.action.admin.indices.alias.get.GetAliasesResponse;
import org.elasticsearch.action.admin.indices.close.CloseIndexResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.admin.indices.exists.types.TypesExistsResponse;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsResponse;
import org.elasticsearch.action.admin.indices.open.OpenIndexResponse;
import org.elasticsearch.action.admin.indices.refresh.RefreshResponse;
import org.elasticsearch.action.admin.indices.settings.get.GetSettingsResponse;
import org.elasticsearch.action.admin.indices.settings.put.UpdateSettingsResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.collect.ImmutableOpenMap;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.jboss.logging.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangzhende on 2018/4/24.
 */
@RestController
public class IndexManagerController {
    @Autowired
    private ElasticSearchConfig elasticSearchConfig;

    /**
     * 简单测试好不好用
     *
     * @param id
     * @return
     * @throws UnknownHostException
     */
    @RequestMapping(value = "/getDoc", method = RequestMethod.GET)
    public Object getDocs(@RequestParam("id") String id) throws UnknownHostException {
        GetResponse redpo = elasticSearchConfig.getInstance().prepareGet("books", "IT", "1").get();
        return redpo.getSourceAsString();
    }

    /**
     * 索引管理通过IndicesAdminClient对象发送各种操作请求实现的
     *
     * @return
     * @throws UnknownHostException
     */
    public IndicesAdminClient getIndicesAdminClient() throws UnknownHostException {
        TransportClient client = elasticSearchConfig.getInstance();
        //获取IndicesAdminClient对象
        IndicesAdminClient indicesAdminClient = client.admin().indices();
        return indicesAdminClient;
    }

    /**
     * 判断索引是否存在
     *
     * @param index
     * @return
     * @throws UnknownHostException
     */
    @RequestMapping(value = "/isIndex", method = RequestMethod.GET)
    public Object isIndex(@RequestParam("index") String index) throws UnknownHostException {
        index = StringUtils.isEmpty(index) ? "index" : index;
        Map<String, Object> map = new HashMap<>();
        //获取IndicesAdminClient对象
        IndicesAdminClient indicesAdminClient = getIndicesAdminClient();
        //判断索引是否存在
        IndicesExistsResponse existsResponse = indicesAdminClient.prepareExists(index).get();
        map.put("index是否存在:", existsResponse.isExists());
        return map;
    }

    /**
     * 判断type是否存在
     *
     * @param index
     * @param type
     * @return
     * @throws UnknownHostException
     */
    @RequestMapping(value = "/isType", method = RequestMethod.GET)
    public Object isType(@RequestParam(value = "index") String index, @RequestParam("type") String type) throws UnknownHostException {
        index = StringUtils.isEmpty(index) ? "index" : index;
        type = StringUtils.isEmpty(type) ? "type" : type;
        Map<String, Object> map = new HashMap<>();
        //获取IndicesAdminClient对象
        IndicesAdminClient indicesAdminClient = getIndicesAdminClient();
        TypesExistsResponse typesExistsResponse = indicesAdminClient.prepareTypesExists(index).setTypes(type, "program").get();
        map.put("type是否存在:", typesExistsResponse.isExists());
        return map;
    }

    /**
     * 创建索引
     *
     * @param index
     * @return
     * @throws UnknownHostException
     */
    @RequestMapping(value = "/createIndex", method = RequestMethod.GET)
    public Object createIndex(@RequestParam(value = "index", required = true) String index) throws UnknownHostException {
        Map<String, Object> map = new HashMap<>();
        //索引名必须小写
        index = index.toLowerCase();
        //获取IndicesAdminClient对象
        IndicesAdminClient indicesAdminClient = getIndicesAdminClient();
        CreateIndexResponse createIndexResponse = indicesAdminClient.prepareCreate(index).get();
        map.put(index + "索引是否创建成功:", createIndexResponse.isAcknowledged());
        return map;
    }

    /**
     * 创建索引并设置settings
     *
     * @param index
     * @param shards
     * @param replicas
     * @return
     * @throws UnknownHostException
     */
    @RequestMapping(value = "/createIndexWithSettings", method = RequestMethod.GET)
    public Object createIndexWithSettings(@RequestParam(value = "index", required = true) String index,
                                          @RequestParam(value = "shards") int shards,
                                          @RequestParam(value = "replicas") int replicas) throws UnknownHostException {
        Map<String, Object> map = new HashMap<>();
        //索引名必须小写
        index = index.toLowerCase();
        //获取IndicesAdminClient对象
        IndicesAdminClient indicesAdminClient = getIndicesAdminClient();
        CreateIndexResponse createIndexResponse = indicesAdminClient.prepareCreate(index)
                .setSettings(Settings.builder()
                        .put("index.number_of_shards", shards == 0 ? 1 : shards)
                        .put("index.number_of_replicas", replicas == 0 ? 1 : replicas)
                ).get();
        map.put(index + "索引是否创建成功:", createIndexResponse.isAcknowledged());
        return map;
    }

    /**
     * 修改副本
     *
     * @param index
     * @param replicas
     * @return
     * @throws UnknownHostException
     */
    @RequestMapping(value = "/updateReplicas", method = RequestMethod.GET)
    public Object updateReplicas(@RequestParam(value = "index", required = true) String index,
                                 @RequestParam(value = "replicas") int replicas) throws UnknownHostException {
        Map<String, Object> map = new HashMap<>();
        //索引名必须小写
        index = index.toLowerCase();
        //获取IndicesAdminClient对象
        IndicesAdminClient indicesAdminClient = getIndicesAdminClient();
        UpdateSettingsResponse updateSettingsResponse = indicesAdminClient
                .prepareUpdateSettings(index)
                .setSettings(Settings.builder()
                        .put("index.number_of_replicas", replicas == 0 ? 1 : replicas)).get();
        map.put(index + "副本修改是否成功:", updateSettingsResponse.isAcknowledged());
        return map;
    }

    /**
     * 获取settings
     *
     * @param index
     * @param type
     * @return
     * @throws UnknownHostException
     */
    @RequestMapping(value = "/getSettings", method = RequestMethod.GET)
    public Object getSettings(@RequestParam(value = "index", required = true) String index,
                              @RequestParam(value = "type", required = true) String type) throws UnknownHostException {
        Map<String, Object> map = new HashMap<>();
        //索引名必须小写
        index = index.toLowerCase();
        //获取IndicesAdminClient对象
        IndicesAdminClient indicesAdminClient = getIndicesAdminClient();
        GetSettingsResponse getSettingsResponse = indicesAdminClient.prepareGetSettings(index, type).get();
        for (ObjectObjectCursor<String, Settings> cursor : getSettingsResponse.getIndexToSettings()) {
            String indexKey = cursor.key;
            Settings settings = cursor.value;
            Integer shards = settings.getAsInt("index.number_of_shards", null);
            Integer replicas = settings.getAsInt("index.number_of_replicas", null);
            map.put("index", indexKey);
            map.put(index + "index.number_of_shards:", shards);
            map.put(index + "index.number_of_replicas:", replicas);
        }
        return map;
    }

    /**
     * 设置Mappings
     * 假设index/type的索引如下
     * {
     * "properties":{
     * "name":{"type":"keyword"}
     * }
     * }
     *
     * @param index
     * @param type
     * @return
     * @throws UnknownHostException
     */
    @SuppressWarnings("deprecation")
	@RequestMapping(value = "/setMappings", method = RequestMethod.GET)
    public Object setMappings(@RequestParam(value = "index", required = true) String index,
                              @RequestParam(value = "type", required = true) String type) throws IOException {
        Map<String, Object> map = new HashMap<>();
        //索引名必须小写
        index = index.toLowerCase();
        //获取IndicesAdminClient对象
        IndicesAdminClient indicesAdminClient = getIndicesAdminClient();
        //1.使用javaAPI构造
        indicesAdminClient.preparePutMapping("index")
                .setType(type)
                .setSource("{\n" +
                        " \"properties\":{\n" +
                        "\"name\":{\n" +
                        "\"type\":\"keyword\"\n" +
                        "}\n" +
                        "}\n" +
                        "}").get();
        //2.使用XContentFactory构造
        CreateIndexResponse createIndexResponse = indicesAdminClient.prepareCreate(index)
                .addMapping(type, XContentFactory.jsonBuilder()
                        .startObject()
                        .startObject("properties")
                        .startObject("bookname")
                        .field("type", "keyword")
                        .endObject()
                        .endObject()
                        .endObject()).get();
        return map;
    }

    /**
     * 获取Mappings
     * @param index
     * @param type
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/getMappings", method = RequestMethod.GET)
    public Object getMappings(@RequestParam(value = "index", required = true) String index,
                              @RequestParam(value = "type", required = true) String type) throws IOException {
        //索引名必须小写
        index = index.toLowerCase();
        //获取IndicesAdminClient对象
        IndicesAdminClient indicesAdminClient = getIndicesAdminClient();
        GetMappingsResponse getMappingsResponse=indicesAdminClient.prepareGetMappings(index).get();
        ImmutableOpenMap<String,MappingMetaData> immutableOpenMap=getMappingsResponse.getMappings().get(index);
        MappingMetaData mappingMetaData=immutableOpenMap.get(type);
        return mappingMetaData.getSourceAsMap();
    }

    /**
     * 删除索引
     * @param index
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/deleteIndex", method = RequestMethod.GET)
    public Object deleteIndex(@RequestParam(value = "index", required = true) String index) throws IOException {
        Map<String, Object> map = new HashMap<>();
        //索引名必须小写
        index = index.toLowerCase();
        //获取IndicesAdminClient对象
        IndicesAdminClient indicesAdminClient = getIndicesAdminClient();
        DeleteIndexResponse deleteIndexResponse=indicesAdminClient.prepareDelete(index).get();
        map.put("删除索引是否成功", deleteIndexResponse.isAcknowledged());
        return map;
    }

    /**
     * 刷新
     * @param index
     * @param type
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/refresh", method = RequestMethod.GET)
    public Object refresh(@RequestParam(value = "index") String index,
                          @RequestParam(value = "type") String type) throws IOException {
        Map<String, Object> map = new HashMap<>();
        //索引名必须小写
        index = index.toLowerCase();
        //获取IndicesAdminClient对象
        IndicesAdminClient indicesAdminClient = getIndicesAdminClient();
        RefreshResponse refreshResponse=indicesAdminClient.prepareRefresh().get();
        map.put("刷新成功分片数", refreshResponse.getSuccessfulShards());
        map.put("刷新失败分片数", refreshResponse.getFailedShards());
        if(!StringUtils.isEmpty(index)){
            RefreshResponse refreshResponse1=indicesAdminClient.prepareRefresh(index).get();
            map.put(index+"刷新失败分片数", refreshResponse1.getFailedShards());
            map.put(index+"刷新成功分片数", refreshResponse1.getSuccessfulShards());
            if(!StringUtils.isEmpty(type)){
                RefreshResponse refreshResponse2=indicesAdminClient.prepareRefresh(index,type).get();
                map.put(type+"刷新失败分片数", refreshResponse2.getFailedShards());
                map.put(type+"刷新成功分片数", refreshResponse2.getSuccessfulShards());
            }
        }
        return map;
    }

    /**
     * 关闭索引
     * @param index
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/closeIndex", method = RequestMethod.GET)
    public Object closeIndex(@RequestParam(value = "index") String index) throws IOException {
        Map<String, Object> map = new HashMap<>();
        //索引名必须小写
        index = index.toLowerCase();
        //获取IndicesAdminClient对象
        IndicesAdminClient indicesAdminClient = getIndicesAdminClient();
        CloseIndexResponse closeIndexResponse=indicesAdminClient.prepareClose(index).get();
        map.put(index+"关闭是否成功", closeIndexResponse.isAcknowledged());
        return map;
    }

    /**
     * 打开索引
     * @param index
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/openIndex", method = RequestMethod.GET)
    public Object openIndex(@RequestParam(value = "index") String index) throws IOException {
        Map<String, Object> map = new HashMap<>();
        //索引名必须小写
        index = index.toLowerCase();
        //获取IndicesAdminClient对象
        IndicesAdminClient indicesAdminClient = getIndicesAdminClient();
        OpenIndexResponse openIndexResponse=indicesAdminClient.prepareOpen(index).get();
        map.put(index+"打开是否成功", openIndexResponse.isAcknowledged());
        return map;
    }

    /**
     * 设置别名
     * @param index
     * @param alias
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/setAlias", method = RequestMethod.GET)
    public Object setAlias(@RequestParam(value = "index") String index,
                           @RequestParam(value = "alias") String alias) throws IOException {
        Map<String, Object> map = new HashMap<>();
        //索引名必须小写
        index = index.toLowerCase();
        //获取IndicesAdminClient对象
        IndicesAdminClient indicesAdminClient = getIndicesAdminClient();
        IndicesAliasesResponse indicesAliasesResponse=indicesAdminClient.prepareAliases()
                .addAlias(index,alias).get();
        map.put(index+"别名设置是否成功", indicesAliasesResponse.isAcknowledged());
        return map;
    }

    /**
     * 获取别名
     * @param index
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/getAlias", method = RequestMethod.GET)
    public Object getAlias(@RequestParam(value = "index") String index) throws IOException {
        Map<String, Object> map = new HashMap<>();
        //索引名必须小写
        index = index.toLowerCase();
        //获取IndicesAdminClient对象
        IndicesAdminClient indicesAdminClient = getIndicesAdminClient();
        GetAliasesResponse getAliasesResponse=indicesAdminClient.prepareGetAliases(index).get();
        map.put(index+"别名", getAliasesResponse.getAliases());
        return map;
    }
}
