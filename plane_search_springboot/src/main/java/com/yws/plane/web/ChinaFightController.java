package com.yws.plane.web;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yws.plane.entity.ChinaFight;
import com.yws.plane.entity.PageEntity;
import com.yws.plane.entity.Plane;
import com.yws.plane.service.ChinaFightService;
import com.yws.plane.service.SearchService;
import com.yws.plane.util.ExcelUtil;
import com.yws.plane.util.JSONData;
import com.yws.plane.util.RedisUtil;
import com.yws.plane.util.TypeUtil;
import com.yws.plane.vo.FightCompanyPlaneVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
@RequestMapping("/api/admin/chinaFight")
public class ChinaFightController {
    @Autowired
    private ChinaFightService chinaFightService;
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
        return JSONData.toJsonString(200, "", chinaFightService.page(page1, wrapper));
    }

    @GetMapping("/all")
    @CrossOrigin
    public String all() {
        return JSONData.toJsonString(200, "", chinaFightService.list());
    }

    @PostMapping("/add")
    @CrossOrigin
    @Transactional
    public String add(@RequestBody ChinaFight chinaFight) {
        List<FightCompanyPlaneVO> fightCompanyPlaneVOS = new ArrayList<>();
        if (chinaFightService.save(chinaFight)) {
            QueryWrapper<ChinaFight> wrapper = new QueryWrapper<>();
            wrapper.setEntity(chinaFight);
            ChinaFight chinaFight1 = chinaFightService.getOne(wrapper);
            fightCompanyPlaneVOS.add(chinaFightService.getChinaFightCompanyPlane(chinaFight1.getFid()));
        }
        System.out.println(fightCompanyPlaneVOS);
        searchService.importEs(fightCompanyPlaneVOS);
        redisUtil.del(chinaFight.getStartCity() + "_" + chinaFight.getEndCity());
        return JSONData.toJsonString(200, "添加成功", null);
    }

    @PostMapping("/file")
    @CrossOrigin
    @Transactional
    public String file(@RequestBody MultipartFile file) {
        List<ChinaFight> chinaFights = ExcelUtil.importExcel(file, 0, 1, ChinaFight.class);
        boolean flag = chinaFightService.saveBatch(chinaFights);
        if (flag) {
            List<FightCompanyPlaneVO> fightCompanyPlaneVOS = new ArrayList<>();
            for (ChinaFight chinaFight : chinaFights) {
                QueryWrapper<ChinaFight> wrapper = new QueryWrapper<>();
                wrapper.setEntity(chinaFight);
                ChinaFight chinaFight1 = chinaFightService.getOne(wrapper);
                fightCompanyPlaneVOS.add(chinaFightService.getChinaFightCompanyPlane(chinaFight1.getFid()));
            }
            searchService.importEs(fightCompanyPlaneVOS);
            return JSONData.toJsonString(200, "批量文件导入成功", null);
        }
        return JSONData.toJsonString(500, "批量文件导入失败", null);
    }

    @PostMapping("/del")
    @CrossOrigin
    public String del(@RequestBody String fights) {
        JSONObject com = JSONObject.parseObject(fights);
        Object value = com.get("fights");
        JSONArray array = JSONArray.parseArray(value.toString());
        List<ChinaFight> fights1 = array.toJavaList(ChinaFight.class);
        boolean flag = chinaFightService.removeByIds(TypeUtil.chinaFightIds(fights1));
        if (flag) {
            return JSONData.toJsonString(200, "删除成功", null);
        }
        return JSONData.toJsonString(500, "删除失败", null);
    }

    @PostMapping("/update")
    @CrossOrigin
    public String update(@RequestBody ChinaFight fight) {
        boolean flag = chinaFightService.updateById(fight);
        if (flag) {
            return JSONData.toJsonString(200, "修改成功", null);
        }
        return JSONData.toJsonString(500, "修改失败", null);
    }

}

