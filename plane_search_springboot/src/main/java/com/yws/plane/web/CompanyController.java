package com.yws.plane.web;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yws.plane.entity.Company;
import com.yws.plane.entity.PageEntity;
import com.yws.plane.service.CompanyService;
import com.yws.plane.util.ExcelUtil;
import com.yws.plane.util.FileUploadUtils;
import com.yws.plane.util.JSONData;
import com.yws.plane.util.TypeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author yewenshu123
 * @since 2019-12-21
 */
@RestController
@RequestMapping("/api/admin/company")
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @PostMapping("/all")
    @CrossOrigin
    public String show(@RequestBody PageEntity page) {
        Page<Company> page1 = new Page<>(page.getCurrentPage(), page.getPageSize());
       if(page.getKey()!=null){
           QueryWrapper<Company> wrapper = new QueryWrapper<>();
           wrapper.like("name", page.getKey())
                   .or()
                   .like("description", page.getKey());
           return JSONData.toJsonString(200, "", companyService.page(page1, wrapper));
       }
        return JSONData.toJsonString(200, "", companyService.page(page1));
    }

    @GetMapping("/all")
    @CrossOrigin
    public String all() {
        return JSONData.toJsonString(200, "", companyService.list(null));
    }

    @PostMapping("/del")
    @CrossOrigin
    public String del(@RequestBody String companies) {
        JSONObject com = JSONObject.parseObject(companies);
        Object value = com.get("companies");
        JSONArray array = JSONArray.parseArray(value.toString());
        List<Company> companies1 = array.toJavaList(Company.class);
        boolean flag = companyService.removeByIds(TypeUtil.companyIds(companies1));
        if (flag) {
            return JSONData.toJsonString(200, "删除成功", null);
        }
        return JSONData.toJsonString(500, "删除失败", null);
    }

    @PostMapping("/update")
    @CrossOrigin
    public String update(@RequestBody Company company) {
        boolean flag = companyService.updateById(company);
        if (flag) {
            return JSONData.toJsonString(200, "修改成功", null);
        }
        return JSONData.toJsonString(500, "修改失败", null);
    }

    @PostMapping("/add")
    @CrossOrigin
    public String add(@RequestBody Company company) {
        boolean flag = companyService.save(company);
        if (flag) {
            return JSONData.toJsonString(200, "添加成功", null);
        }
        return JSONData.toJsonString(500, "添加失败", null);
    }

    @PostMapping("/icon")
    @CrossOrigin
    public String icon(@RequestBody MultipartFile file) {
        try {
            String filename = "http://www.yewenshu.top/" + FileUploadUtils.fileUpload(file.getBytes());
            return JSONData.toJsonString(200, "上传成功", filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return JSONData.toJsonString(500, "上传失败", null);
    }

    @PostMapping("/file")
    @CrossOrigin
    public String file(@RequestBody MultipartFile file) {
        List<Company> companies = ExcelUtil.importExcel(file, 0, 1, Company.class);
        System.out.println(companies);
        boolean flag = companyService.saveBatch(companies);
        if (flag) {
            return JSONData.toJsonString(200, "批量文件导入成功", null);
        }
        return JSONData.toJsonString(500, "批量文件导入失败", null);
    }

    @GetMapping("/importes")
    public void importEs(){
        companyService.importEs(companyService.list(null));
    }

    @PostMapping("/query")
    public String query(@RequestBody PageEntity page){
        return JSONData.toJsonString(200, "搜索成功", companyService.searchEntity(page));
    }

    @GetMapping("/get/{key}")
    public String get(@PathVariable String key){
        PageEntity pageEntity = new PageEntity();
        pageEntity.setKey(key);
        pageEntity.setCurrentPage(1);
        pageEntity.setPageSize(4);
        return JSONData.toJsonString(200, "搜索成功", companyService.searchEntity(pageEntity));
    }

}

