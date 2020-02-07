package com.yws.plane.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yws.plane.entity.Company;
import com.yws.plane.entity.PageEntity;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author yewenshu123
 * @since 2019-12-21
 */
public interface CompanyService extends IService<Company> {
    //从数据库导入数据到elasticsearch
    void importEs(List<Company> companies);
    //根据输入的值搜索复合的内容
    JSONObject searchEntity(PageEntity pageEntity);
}
