package com.yws.plane.web;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yws.plane.entity.News;
import com.yws.plane.entity.PageEntity;
import com.yws.plane.service.NewsService;
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
 * @since 2019-12-23
 */
@RestController
@RequestMapping("/api/admin/news")
public class NewsController {
    @Autowired
    private NewsService newsService;

    @PostMapping("/all")
    @CrossOrigin
    public String show(@RequestBody PageEntity page) {
        Page<News> page1 = new Page<>(page.getCurrentPage(), page.getPageSize());
        if(page.getKey()!=null){
            QueryWrapper<News> wrapper = new QueryWrapper<>();
            wrapper.like("title", page.getKey()).or()
                    .like("plan_start_time", page.getKey()).or()
                    .like("plan_end_time", page.getKey()).or()
                    .like("notify_fight", page.getKey()).or()
                    .like("status", page.getKey());
            return JSONData.toJsonString(200, "", newsService.page(page1, wrapper));
        }
        return JSONData.toJsonString(200, "", newsService.page(page1));
    }

    @GetMapping("/all")
    @CrossOrigin
    public String all() {
        return JSONData.toJsonString(200, "", newsService.list(null));
    }

    @PostMapping("/del")
    @CrossOrigin
    public String del(@RequestBody String news) {
        JSONObject com = JSONObject.parseObject(news);
        Object value = com.get("news");
        JSONArray array = JSONArray.parseArray(value.toString());
        List<News> news1 = array.toJavaList(News.class);
        boolean flag = newsService.removeByIds(TypeUtil.newsIds(news1));
        if (flag) {
            return JSONData.toJsonString(200, "删除成功", null);
        }
        return JSONData.toJsonString(500, "删除失败", null);
    }

    @PostMapping("/update")
    @CrossOrigin
    public String update(@RequestBody News news) {
        boolean flag = newsService.updateById(news);
        if (flag) {
            return JSONData.toJsonString(200, "修改成功", null);
        }
        return JSONData.toJsonString(500, "修改失败", null);
    }

    @PostMapping("/add")
    @CrossOrigin
    public String add(@RequestBody News news) {
        boolean flag = newsService.save(news);
        if (flag) {
            return JSONData.toJsonString(200, "添加成功", null);
        }
        return JSONData.toJsonString(500, "添加失败", null);
    }
}

