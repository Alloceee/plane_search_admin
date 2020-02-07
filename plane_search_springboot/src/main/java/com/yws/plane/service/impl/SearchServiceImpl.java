package com.yws.plane.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yws.plane.util.RedisUtil;
import com.yws.plane.vo.FightCompanyPlaneVO;
import com.yws.plane.entity.PageEntity;
import com.yws.plane.service.AbroadFightService;
import com.yws.plane.service.ChinaFightService;
import com.yws.plane.service.SearchService;
import com.yws.plane.util.TimeUtils;
import io.searchbox.client.JestClient;
import io.searchbox.core.Bulk;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: yewenshu https://github.com/Alloceee
 * @Date: 2020/1/5 20:30
 * @Project: plane
 */
@Service
public class SearchServiceImpl implements SearchService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyServiceImpl.class);

    @Autowired
    private JestClient jestClient;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void importEs(List<FightCompanyPlaneVO> fightCompanyPlaneVOS) {
        Bulk.Builder bulk = new Bulk.Builder();
        for (FightCompanyPlaneVO fightCompanyPlaneVO : fightCompanyPlaneVOS) {
            Index index = new Index.Builder(fightCompanyPlaneVO)
                    .index(FightCompanyPlaneVO.INDEX).type(FightCompanyPlaneVO.ORDER_TYPE).build();
            bulk.addAction(index);
        }
        try {
            jestClient.execute(bulk.build());
            LOGGER.info("ES 导入完成");
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
        }
    }

    /**
     * boolQueryBuilder.must()表示AND
     * boolQueryBuilder.mustNot()表示NOT
     * boolQueryBuilder.should()表示OR
     *
     * @param pageEntity
     * @return
     */
    @Override
    public JSONObject searchEntity(PageEntity pageEntity) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        String key = pageEntity.getKey();
        if (key != null) {
            String startCity = pageEntity.getStartCity();
            String endCity = pageEntity.getEndCity();
            Date startTime = pageEntity.getStartTime();
            Date endTime = pageEntity.getEndTime();
            boolQueryBuilder.must(QueryBuilders.commonTermsQuery("startCity", startCity));
            if (startTime != null && endTime != null && endCity != null) {
                boolQueryBuilder.must(QueryBuilders.rangeQuery("startTime").from(startTime).to(endTime))
                        .must(QueryBuilders.rangeQuery("endTime").from(startTime).to(endTime))
                        .must(QueryBuilders.commonTermsQuery("endCity", pageEntity.getEndCity()));
                redisUtil.incr(endCity,1);
            } else {
                System.out.println(TimeUtils.getStartTime(new Date()));
                boolQueryBuilder.must(QueryBuilders.rangeQuery("startTime")
                        .from(TimeUtils.getStartTime(new Date())).to(TimeUtils.getEndTime(new Date())))
                        .should(QueryBuilders.fuzzyQuery("endCity", key))
                        .should(QueryBuilders.fuzzyQuery("endAirport", key))
                        .should(QueryBuilders.fuzzyQuery("startAirport", key))
                        .should(QueryBuilders.fuzzyQuery("name", key))
                        .should(QueryBuilders.fuzzyQuery("number", key));
            }
        }
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //需要检索的数据条数，这里可以设计为分页
        searchSourceBuilder
                .query(boolQueryBuilder)
                .size(pageEntity.getPageSize())
                .from((pageEntity.getCurrentPage() - 1) * pageEntity.getPageSize());
        if (pageEntity.getOrderByField() != null) {
            searchSourceBuilder.sort(pageEntity.getOrderByField(), SortOrder.DESC);
        }
        Search search = new Search.Builder(searchSourceBuilder.toString())
                .addIndex(FightCompanyPlaneVO.INDEX).addType(FightCompanyPlaneVO.ORDER_TYPE).build();
        try {
            SearchResult result = jestClient.execute(search);
            List<SearchResult.Hit<FightCompanyPlaneVO, Void>> hits = result.getHits(FightCompanyPlaneVO.class);
            List<FightCompanyPlaneVO> fightCompanyPlaneVOS = new ArrayList<FightCompanyPlaneVO>();
            for (SearchResult.Hit<FightCompanyPlaneVO, Void> hit : hits) {
                FightCompanyPlaneVO source = hit.source;
                FightCompanyPlaneVO fightCompanyPlaneVO = new FightCompanyPlaneVO();
                fightCompanyPlaneVO.setFid(source.getFid());
                fightCompanyPlaneVO.setName(source.getName());
                fightCompanyPlaneVO.setStartAirport(source.getStartAirport());
                fightCompanyPlaneVO.setEndAirport(source.getEndAirport());
                fightCompanyPlaneVO.setStartCity(source.getStartCity());
                fightCompanyPlaneVO.setEndCity(source.getEndCity());
                fightCompanyPlaneVO.setPrice(source.getPrice());
                fightCompanyPlaneVO.setNumber(source.getNumber());
                fightCompanyPlaneVO.setModel(source.getModel());
                fightCompanyPlaneVO.setType(source.getType());
                fightCompanyPlaneVO.setStartTime(TimeUtils.stringToDate(source.getStartTime(), TimeUtils.PATTERN1));
                fightCompanyPlaneVO.setEndTime(TimeUtils.stringToDate(source.getEndTime(), TimeUtils.PATTERN1));
//                fightCompanyPlaneVO.setStartTime(source.getStartTime());
//                fightCompanyPlaneVO.setEndTime(source.getEndTime());
                fightCompanyPlaneVO.setDescription(source.getDescription());
                fightCompanyPlaneVO.setIcon(source.getIcon());
                fightCompanyPlaneVOS.add(fightCompanyPlaneVO);
            }
            Integer total = result.getJsonObject().get("hits").getAsJsonObject().get("total").getAsJsonObject().get("value").getAsInt();
            JSONObject object = new JSONObject();
            object.put("records", fightCompanyPlaneVOS);
            object.put("total", total);
            return object;
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

}
