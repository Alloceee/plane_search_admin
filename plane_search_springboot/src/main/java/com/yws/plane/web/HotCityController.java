package com.yws.plane.web;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yws.plane.entity.*;
import com.yws.plane.service.HotCityLogService;
import com.yws.plane.service.HotCityService;
import com.yws.plane.service.RoleService;
import com.yws.plane.util.JSONData;
import com.yws.plane.util.TypeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yewenshu123
 * @since 2020-01-06
 */
@RestController
@RequestMapping("/api/admin/city")
public class HotCityController {
    @Autowired
    private HotCityService hotCityService;
    @Autowired
    private HotCityLogService hotCityLogService;

    @PostMapping("/all")
    @CrossOrigin
    public String show(@RequestBody PageEntity page) {
        Page<HotCity> page1 = new Page<>(page.getCurrentPage(), page.getPageSize());
        QueryWrapper<HotCity> wrapper = new QueryWrapper<>();
        if(page.getKey()==null){
            page.setKey("");
        }
        wrapper.like("city", page.getKey());
        return JSONData.toJsonString(200, "", hotCityService.page(page1, wrapper));
    }

    @GetMapping("/all")
    @CrossOrigin
    public String all(String type) {
        QueryWrapper<HotCity> wrapper = new QueryWrapper<>();
        wrapper.eq("type",type);
        return JSONData.toJsonString(200, "", hotCityService.list(wrapper));
    }

    @PostMapping("/del")
    @CrossOrigin
    public String del(@RequestBody String hots) {
        JSONObject com = JSONObject.parseObject(hots);
        Object value = com.get("hots");
        JSONArray array = JSONArray.parseArray(value.toString());
        List<HotCity> hotCities = array.toJavaList(HotCity.class);
        boolean flag = hotCityService.removeByIds(TypeUtil.hotCitiesIds(hotCities));
        if (flag) {
            return JSONData.toJsonString(200, "删除成功", null);
        }
        return JSONData.toJsonString(500, "删除失败", null);
    }

    @PostMapping("/update")
    @CrossOrigin
    public String update(@RequestBody HotCity city) {
        boolean flag = hotCityService.updateById(city);
        if (flag) {
            return JSONData.toJsonString(200, "修改成功", null);
        }
        return JSONData.toJsonString(500, "修改失败", null);
    }

    @PostMapping("/add")
    @CrossOrigin
    public String add(@RequestBody HotCity city) {
        System.out.println(city);
        boolean flag = hotCityService.save(city);
        if (flag) {
            return JSONData.toJsonString(200, "添加成功", null);
        }
        return JSONData.toJsonString(500, "添加失败", null);
    }

    @GetMapping("/log")
    @CrossOrigin
    public String log(@RequestBody HotCityLog hotCityLog){
        QueryWrapper<HotCityLog> wrapper = new QueryWrapper<>();
        wrapper.setEntity(hotCityLog);
        return JSONData.toJsonString(200, "查询成功", hotCityLogService.list(wrapper));
    }
}

