package com.yws.plane.web;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yws.plane.annotation.UserLoginToken;
import com.yws.plane.entity.AbroadFight;
import com.yws.plane.entity.ChinaFight;
import com.yws.plane.entity.PageEntity;
import com.yws.plane.service.AbroadFightService;
import com.yws.plane.service.ChinaFightService;
import com.yws.plane.service.SearchService;
import com.yws.plane.util.JSONData;
import com.yws.plane.util.RedisUtil;
import com.yws.plane.vo.FightCompanyPlaneVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Column;
import java.util.List;

/**
 * @author AlmostLover
 */
@RestController
@RequestMapping("/api")
public class SearchController {
    @Autowired
    private ChinaFightService chinaFightService;
    @Autowired
    private AbroadFightService abroadFightService;
    @Autowired
    private SearchService searchService;
    @Autowired
    private RedisUtil redisUtil;

    @PostMapping("/china_plane")
    @CrossOrigin
    public String china_plane(@RequestBody PageEntity page) {
        Object fightCompanyPlaneVOPage = redisUtil.get(page.getStartCity() + "_" + page.getEndCity());
        if (fightCompanyPlaneVOPage == null) {
            Page<FightCompanyPlaneVO> page1 = new Page<>(page.getCurrentPage(), page.getPageSize());
            page1.addOrder(new OrderItem().setColumn(page.getOrderByField()).setAsc(false));
            QueryWrapper<FightCompanyPlaneVO> wrapper = new QueryWrapper<>();
            wrapper.eq("start_city", page.getStartCity()).eq("end_city", page.getEndCity());
            fightCompanyPlaneVOPage = chinaFightService.page(page1, wrapper).getRecords();
            redisUtil.set(page.getStartCity() + "_" + page.getEndCity(), fightCompanyPlaneVOPage);
        }
        return JSONData.toJsonString(200, "", fightCompanyPlaneVOPage);
    }

    @PostMapping("/abroad_plane")
    @CrossOrigin
    public String abroad_plane(@RequestBody PageEntity page) {
        Object fightCompanyPlaneVOPage = redisUtil.get(page.getStartCity() + "_" + page.getEndCity());
        if (fightCompanyPlaneVOPage == null) {
            Page<FightCompanyPlaneVO> page1 = new Page<>(page.getCurrentPage(), page.getPageSize());
            page1.addOrder(new OrderItem().setColumn(page.getOrderByField()).setAsc(false));
            QueryWrapper<FightCompanyPlaneVO> wrapper = new QueryWrapper<>();
            wrapper.eq("start_city", page.getStartCity()).eq("end_city", page.getEndCity());
            fightCompanyPlaneVOPage = abroadFightService.page(page1, wrapper).getRecords();
            redisUtil.set(page.getStartCity() + "_" + page.getEndCity(), fightCompanyPlaneVOPage);
        }
        return JSONData.toJsonString(200, "", fightCompanyPlaneVOPage);
    }

    @GetMapping("/importEs")
    public void importEs() {
        List<FightCompanyPlaneVO> china = chinaFightService.allOptions();
        searchService.importEs(china);
        List<FightCompanyPlaneVO> abroad = abroadFightService.allOptions();
        searchService.importEs(abroad);
    }

    @PostMapping("/query")
    @UserLoginToken
    public String query(@RequestBody PageEntity page) {
        page.setCurrentPage(1);
        page.setPageSize(7);
//        JSONObject res = searchService.searchEntity(page);
//        if (res == null) {
        Page<FightCompanyPlaneVO> page1 = new Page<>(page.getCurrentPage(), page.getPageSize());
        page1.addOrder(new OrderItem().setColumn(page.getOrderByField()).setAsc(page.getIsAsc()));
        QueryWrapper<FightCompanyPlaneVO> wrapper = new QueryWrapper<>();
        wrapper.eq("start_city", page.getStartCity())
                .eq("end_city", page.getEndCity())
                .ge("start_time", page.getStartTime())
                .le("end_time", page.getEndTime());
        QueryWrapper<FightCompanyPlaneVO> wrapper2 = new QueryWrapper<>();
        wrapper2.eq("start_city", page.getStartCity())
                .eq("end_city", page.getEndCity())
                .ge("start_time", page.getStartTime())
                .le("end_time", page.getEndTime());
        List<FightCompanyPlaneVO> china = chinaFightService.page(page1, wrapper).getRecords();
        List<FightCompanyPlaneVO> abroad = abroadFightService.page(page1, wrapper2).getRecords();
        china.addAll(abroad);
        JSONObject object = new JSONObject();
        object.put("records", china);
        object.put("total", china.size());
        return JSONData.toJsonString(200, "ES未打开，连接失败，启动数据库查询", object);
//        }
//        return JSONData.toJsonString(200, "查询成功", res);

    }

    @PostMapping("/search")
    @CrossOrigin
    public String search(@RequestBody String key){
//        JSONObject res = searchService.searchEntity(page);
//        if (res == null) {
        Page<FightCompanyPlaneVO> page1 = new Page<>(1, 7);
        page1.addOrder(new OrderItem().setColumn("price").setAsc(false));
        QueryWrapper<FightCompanyPlaneVO> wrapper = new QueryWrapper<>();
        wrapper.eq("end_city", key).or()
                .eq("number",key);
        QueryWrapper<FightCompanyPlaneVO> wrapper2 = new QueryWrapper<>();
        wrapper2.eq("end_city", key).or()
                .eq("number",key);
        List<FightCompanyPlaneVO> china = chinaFightService.page(page1, wrapper).getRecords();
        List<FightCompanyPlaneVO> abroad = abroadFightService.page(page1, wrapper2).getRecords();
        china.addAll(abroad);
        JSONObject object = new JSONObject();
        object.put("records", china);
        object.put("total", china.size());
        return JSONData.toJsonString(200, "ES未打开，连接失败，启动数据库查询", null);
    }

}
