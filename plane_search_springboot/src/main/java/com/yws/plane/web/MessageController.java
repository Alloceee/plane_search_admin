package com.yws.plane.web;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yws.plane.entity.Message;
import com.yws.plane.entity.PageEntity;
import com.yws.plane.service.MessageService;
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
 * @since 2020-01-10
 */
@RestController
@RequestMapping("/api/admin/message")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @PostMapping("/all")
    @CrossOrigin
    public String show(@RequestBody PageEntity page) {
        Page<Message> page1 = new Page<>(page.getCurrentPage(), page.getPageSize());
        if(page.getKey()!=null){
            QueryWrapper<Message> wrapper = new QueryWrapper<>();
            wrapper.like("uid", page.getKey()).or()
                    .like("number", page.getKey()).or()
                    .like("type", page.getKey()).or()
                    .like("status", page.getKey());
            return JSONData.toJsonString(200, "", messageService.page(page1, wrapper));
        }
        return JSONData.toJsonString(200, "", messageService.page(page1));
    }

    @GetMapping("/all")
    @CrossOrigin
    public String all() {
        return JSONData.toJsonString(200, "", messageService.list(null));
    }

    @PostMapping("/del")
    @CrossOrigin
    public String del(@RequestBody String messages) {
        JSONObject com = JSONObject.parseObject(messages);
        Object value = com.get("messages");
        JSONArray array = JSONArray.parseArray(value.toString());
        List<Message> messages1 = array.toJavaList(Message.class);
        boolean flag = messageService.removeByIds(TypeUtil.messageIds(messages1));
        if (flag) {
            return JSONData.toJsonString(200, "删除成功", null);
        }
        return JSONData.toJsonString(500, "删除失败", null);
    }

    @PostMapping("/update")
    @CrossOrigin
    public String update(@RequestBody Message message) {
        boolean flag = messageService.updateById(message);
        if (flag) {
            return JSONData.toJsonString(200, "修改成功", null);
        }
        return JSONData.toJsonString(500, "修改失败", null);
    }

    @PostMapping("/add")
    @CrossOrigin
    public String add(@RequestBody Message message) {
        boolean flag = messageService.save(message);
        if (flag) {
            return JSONData.toJsonString(200, "添加成功", null);
        }
        return JSONData.toJsonString(500, "添加失败", null);
    }
}

