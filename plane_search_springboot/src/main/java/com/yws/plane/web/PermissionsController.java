package com.yws.plane.web;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yws.plane.entity.News;
import com.yws.plane.entity.PageEntity;
import com.yws.plane.entity.Permissions;
import com.yws.plane.service.PermissionsService;
import com.yws.plane.util.JSONData;
import com.yws.plane.util.TypeUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/api/admin/permissions")
public class PermissionsController {
    @Autowired
    private PermissionsService permissionsService;

    @PostMapping("/all")
    @CrossOrigin
    public String show(@RequestBody PageEntity page) {
        Page<Permissions> page1 = new Page<>(page.getCurrentPage(), page.getPageSize());
        QueryWrapper<Permissions> wrapper = new QueryWrapper<>();
        if(page.getKey()==null) {
            page.setKey("");
        }
        wrapper.like("permission_name", page.getKey())
               .like("permission_url",page.getKey());
        return JSONData.toJsonString(200, "", permissionsService.page(page1, wrapper));
    }

    @GetMapping("/all")
    @CrossOrigin
    public String all() {
        return JSONData.toJsonString(200, "", permissionsService.list(null));
    }

    @PostMapping("/del")
    @CrossOrigin
    public String del(@RequestBody String permissions) {
        JSONObject com = JSONObject.parseObject(permissions);
        Object value = com.get("news");
        JSONArray array = JSONArray.parseArray(value.toString());
        List<News> news1 = array.toJavaList(News.class);
        boolean flag = permissionsService.removeByIds(TypeUtil.newsIds(news1));
        if (flag) {
            return JSONData.toJsonString(200, "删除成功", null);
        }
        return JSONData.toJsonString(500, "删除失败", null);
    }

    @PostMapping("/update")
    @CrossOrigin
    public String update(@RequestBody Permissions permissions) {
        boolean flag = permissionsService.updateById(permissions);
        if (flag) {
            return JSONData.toJsonString(200, "修改成功", null);
        }
        return JSONData.toJsonString(500, "修改失败", null);
    }

    @PostMapping("/add")
    @CrossOrigin
    public String add(@RequestBody Permissions permissions) {
        boolean flag = permissionsService.save(permissions);
        if (flag) {
            return JSONData.toJsonString(200, "添加成功", null);
        }
        return JSONData.toJsonString(500, "添加失败", null);
    }
}

