//package com.yws.plane.util;
//
//
//import com.alibaba.fastjson.JSONObject;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
//import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
//import org.elasticsearch.action.index.IndexRequest;
//import org.elasticsearch.action.index.IndexResponse;
//import org.elasticsearch.client.Response;
//import org.elasticsearch.client.RestClient;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.elasticsearch.common.xcontent.XContentType;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import javax.annotation.Resource;
//import java.io.IOException;
//
///**
// * @Title: ElasticsearchUtil
// * @Description: 工具类
// * @author chy
// * @date 2018/4/24 15:40
// */
//@Component
//public class ElasticsearchUtil {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(ElasticsearchUtil.class);
//
//    @Resource(name="restHighLevelClient")
//    private RestHighLevelClient restHighLevelClient;
//
//    private static RestHighLevelClient client;
//
//    private static ObjectMapper mapper = new ObjectMapper();
//
//    /**
//     * @PostContruct是spring框架的注解
//     * spring容器初始化的时候执行该方法
//     */
//    @PostConstruct
//    public void init() {
//        client = this.restHighLevelClient;
//    }
//
//    /**
//     * 创建索引
//     *
//     * @param index
//     * @return
//     */
//    public static boolean createIndex(String index) {
//        //index名必须全小写，否则报错
//        CreateIndexRequest request = new CreateIndexRequest(index);
//        try {
//            CreateIndexResponse indexResponse = client.indices().create(request);
//            if (indexResponse.isAcknowledged()) {
//                LOGGER.info("创建索引成功");
//            } else {
//                LOGGER.info("创建索引失败");
//            }
//            return indexResponse.isAcknowledged();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return false;
//    }
//
//    /**
//     * 插入数据
//     * @param index
//     * @param type
//     * @param object
//     * @return
//     */
//    public static String addData(String index,String type,String id,JSONObject object) {
//        IndexRequest indexRequest = new IndexRequest(index, type, id);
//        try {
//            indexRequest.source(mapper.writeValueAsString(object), XContentType.JSON);
//            IndexResponse indexResponse = client.index(indexRequest);
//            return indexResponse.getId();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    /**
//     * 检查索引
//     * @param index
//     * @return
//     * @throws IOException
//     */
//    public static boolean checkIndexExist(String index) {
//        try {
//            Response response = client.getLowLevelClient().performRequest("HEAD", index);
//            boolean exist = response.getStatusLine().getReasonPhrase().equals("OK");
//            return exist;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//
//    /**
//     * 获取低水平客户端
//     * @return
//     */
//    public static RestClient getLowLevelClient() {
//        return client.getLowLevelClient();
//    }
//}
