package com.yws.plane.web;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yws.plane.entity.AbroadFight;
import com.yws.plane.entity.ChinaFight;
import com.yws.plane.entity.PageEntity;
import com.yws.plane.service.AbroadFightService;
import com.yws.plane.service.SearchService;
import com.yws.plane.util.JSONData;
import com.yws.plane.util.RedisUtil;
import com.yws.plane.util.TypeUtil;
import com.yws.plane.vo.FightCompanyPlaneVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author yewenshu123
 * @since 2019-12-23
 */
@RestController
@RequestMapping("/api/admin/abroadFight")
public class AbroadFightController {
    @Autowired
    private AbroadFightService abroadFightService;
    @Autowired
    private SearchService searchService;
    @Autowired
    private RedisUtil redisUtil;

    @PostMapping("/all")
    @CrossOrigin
    public String show(@RequestBody PageEntity page) {
        Page<FightCompanyPlaneVO> page1 = new Page<>(page.getCurrentPage(), page.getPageSize());
        if (page.getKey() == null) page.setKey("");
        QueryWrapper<FightCompanyPlaneVO> wrapper = new QueryWrapper<>();
        wrapper.like("start_city", page.getKey()).or()
                .like("end_city", page.getKey()).or()
                .like("start_airport", page.getKey()).or()
                .like("end_airport", page.getKey()).or()
                .like("price", page.getKey()).or()
                .like("number", page.getKey()).or()
                .like("model", page.getKey()).or();
        return JSONData.toJsonString(200, "", abroadFightService.page(page1, wrapper));
    }

    @GetMapping("/all")
    @CrossOrigin
    public String all() {
        return JSONData.toJsonString(200, "", abroadFightService.allOptions());
    }

    @PostMapping("/add")
    @CrossOrigin
    public String add(@RequestBody AbroadFight abroadFight) {
        List<FightCompanyPlaneVO> fightCompanyPlaneVOS = new ArrayList<>();
        if(abroadFightService.save(abroadFight)){
//            QueryWrapper<AbroadFight> wrapper = new QueryWrapper<>();
//            wrapper.setEntity(abroadFight);
//            AbroadFight abroadFight1 = abroadFightService.getOne(wrapper);
//            fightCompanyPlaneVOS.add(abroadFightService.getAbroadFightCompanyPlane(abroadFight1.getFid()));
//            searchService.importEs(fightCompanyPlaneVOS);
//            redisUtil.del(abroadFight.getStartCity()+"_"+abroadFight.getEndCity());
            return JSONData.toJsonString(200, "添加成功", abroadFightService.save(abroadFight));
        }
        return JSONData.toJsonString(500, "添加失败", abroadFightService.save(abroadFight));
    }

    @PostMapping("/del")
    @CrossOrigin
    public String del(@RequestBody String fights) {
        JSONObject com = JSONObject.parseObject(fights);
        Object value = com.get("fights");
        JSONArray array = JSONArray.parseArray(value.toString());
        List<AbroadFight> fights1 = array.toJavaList(AbroadFight.class);
        boolean flag = abroadFightService.removeByIds(TypeUtil.abroadFightIds(fights1));
        if (flag) {
            return JSONData.toJsonString(200, "删除成功", null);
        }
        return JSONData.toJsonString(500, "删除失败", null);
    }

    @PostMapping("/update")
    @CrossOrigin
    public String update(@RequestBody AbroadFight fight) {
        boolean flag = abroadFightService.updateById(fight);
        if (flag) {
            return JSONData.toJsonString(200, "修改成功", null);
        }
        return JSONData.toJsonString(500, "修改失败", null);
    }
}

