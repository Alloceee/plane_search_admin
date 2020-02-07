package com.yws.plane.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yws.plane.entity.Company;
import com.yws.plane.entity.PageEntity;
import com.yws.plane.mapper.CompanyMapper;
import com.yws.plane.service.CompanyService;
import io.searchbox.client.JestClient;
import io.searchbox.core.Bulk;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yewenshu123
 * @since 2019-12-21
 */
@Service
public class CompanyServiceImpl extends ServiceImpl<CompanyMapper, Company> implements CompanyService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyServiceImpl.class);

    @Autowired
    private JestClient jestClient;

    @Override
    public void importEs(List<Company> companies) {
        Bulk.Builder bulk = new Bulk.Builder();
        for (Company company : companies) {
            Index index = new Index.Builder(company).index(Company.INDEX).type(Company.ORDER_TYPE).build();
            bulk.addAction(index);
        }
        try {
            jestClient.execute(bulk.build());
            LOGGER.info("ES 插入完成");
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
        }
    }

    @Override
    public JSONObject searchEntity(PageEntity pageEntity) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        String key = pageEntity.getKey();
        if (key != null) {
            boolQueryBuilder.should(QueryBuilders.commonTermsQuery("name", key));
            boolQueryBuilder.should(QueryBuilders.commonTermsQuery("description", key));
        }
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //需要检索的数据条数，这里可以设计为分页
        searchSourceBuilder.query(boolQueryBuilder).size(pageEntity.getPageSize())
                .from((pageEntity.getCurrentPage() - 1) * pageEntity.getPageSize());
        Search search = new Search.Builder(searchSourceBuilder.toString())
                .addIndex(Company.INDEX).addType(Company.ORDER_TYPE).build();
        try {
            SearchResult result = jestClient.execute(search);
            List<SearchResult.Hit<Company, Void>> hits = result.getHits(Company.class);
            List<Company> companies = new ArrayList<Company>();
            for (SearchResult.Hit<Company, Void> hit : hits) {
                Company source = hit.source;
                Company company = new Company();
                company.setCid(source.getCid());
                company.setName(source.getName());
                company.setDescription(source.getDescription());
                company.setIcon(source.getIcon());
                companies.add(company);
            }
            Integer total = result.getJsonObject().get("hits").getAsJsonObject().get("total").getAsJsonObject().get("value").getAsInt();
            JSONObject object = new JSONObject();
            object.put("records", companies);
            object.put("total", total);
            return object;
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
