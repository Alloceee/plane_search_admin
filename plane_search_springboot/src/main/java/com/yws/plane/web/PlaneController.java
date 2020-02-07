package com.yws.plane.web;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yws.plane.entity.PageEntity;
import com.yws.plane.entity.Plane;
import com.yws.plane.service.PlaneService;
import com.yws.plane.util.JSONData;
import com.yws.plane.util.TypeUtil;
import com.yws.plane.vo.PlaneCompanyVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/api/admin/plane")
public class PlaneController {
    @Autowired
    private PlaneService planeService;

    @PostMapping("/all")
    @CrossOrigin
    public String show(@RequestBody PageEntity page) {
        Page<PlaneCompanyVO> page1 = new Page<>(page.getCurrentPage(), page.getPageSize());
        if (page.getKey() == null) page.setKey("");
        QueryWrapper<PlaneCompanyVO> wrapper = new QueryWrapper<>();
        wrapper.like("name", page.getKey()).or()
                .like("number", page.getKey()).or()
                .like("model", page.getKey()).or()
                .like("type", page.getKey());
        return JSONData.toJsonString(200, "", planeService.selectAll(page1, wrapper));
    }

    @GetMapping("/all")
    @CrossOrigin
    public String all() {
        return JSONData.toJsonString(200, "", planeService.list(null));
    }

    @PostMapping("/del")
    @CrossOrigin
    public String del(@RequestBody String planes) {
        JSONObject com = JSONObject.parseObject(planes);
        Object value = com.get("planes");
        JSONArray array = JSONArray.parseArray(value.toString());
        List<Plane> planes1 = array.toJavaList(Plane.class);
        boolean flag = planeService.removeByIds(TypeUtil.planeIds(planes1));
        if (flag) {
            return JSONData.toJsonString(200, "删除成功", null);
        }
        return JSONData.toJsonString(500, "删除失败", null);
    }

    @PostMapping("/update")
    @CrossOrigin
    public String update(@RequestBody Plane plane) {
        boolean flag = planeService.updateById(plane);
        if (flag) {
            return JSONData.toJsonString(200, "修改成功", null);
        }
        return JSONData.toJsonString(500, "修改失败", null);
    }

    @PostMapping("/add")
    @CrossOrigin
    public String add(@RequestBody Plane plane) {
        boolean flag = planeService.save(plane);
        if (flag) {
            return JSONData.toJsonString(200, "添加成功", null);
        }
        return JSONData.toJsonString(500, "添加失败", null);
    }

    @PostMapping("/check")
    @CrossOrigin
    public String check(@RequestBody Plane plane) {
        QueryWrapper<Plane> wrapper = new QueryWrapper<>();
        wrapper.eq("number", plane.getNumber())
                .eq("model", plane.getModel())
                .eq("company_id", plane.getCompanyId())
                .eq("type", plane.getType());
        Plane plane1 = planeService.getOne(wrapper);
        if (plane1 != null) {
            return JSONData.toJsonString(500, "该航班已经存在", null);
        }
        return JSONData.toJsonString(200, "true", null);
    }
}

