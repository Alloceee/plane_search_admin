package com.yws.plane.service;

import com.alibaba.fastjson.JSONObject;
import com.yws.plane.entity.PageEntity;
import com.yws.plane.vo.FightCompanyPlaneVO;

import java.util.List;

/**
 * @Author: yewenshu https://github.com/Alloceee
 * @Date: 2020/1/5 20:25
 * @Project: plane
 */
public interface SearchService {
    void importEs(List<FightCompanyPlaneVO> fightCompanyPlaneVOS);

    JSONObject searchEntity(PageEntity pageEntity);
}
