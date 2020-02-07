package com.yws.plane.web;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yws.plane.entity.Manager;
import com.yws.plane.service.ManagerService;
import com.yws.plane.service.TokenService;
import com.yws.plane.util.EncryptUtil;
import com.yws.plane.util.JSONData;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yewenshu123
 * @since 2020-01-04
 */
@RestController
@RequestMapping("/api/admin")
public class ManagerController {
    @Autowired
    private ManagerService managerService;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    @CrossOrigin
    public String login(@RequestBody Manager manager){
        //添加用户认证信息
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(
                manager.getUsername(),
                EncryptUtil.Md5(manager.getPassword(),"admin")
        );
        try {
            //进行验证，这里可以捕获异常，然后返回对应信息
            subject.login(usernamePasswordToken);
//            subject.checkRole("admin");
//            subject.checkPermissions("query", "add");
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return JSONData.toJsonString(500,"用户名或密码不正确",null);
        } catch (AuthorizationException e) {
            e.printStackTrace();
            return JSONData.toJsonString(500,"没有权限",null);
        }
        String token = tokenService.getAdminToken(manager);
        return JSONData.toJsonString(200,"登录成功",JSONData.tokenObject(token,manager.getUsername()));

    }

    @PostConstruct
    public void init(){
        Manager manager = new Manager();
        manager.setUsername("root");
        manager.setPassword(EncryptUtil.Md5("root","admin"));
        QueryWrapper<Manager> wrapper = new QueryWrapper<>();
        wrapper.setEntity(manager);
        Manager manager1 = managerService.getOne(wrapper);
        if(manager1==null){
            managerService.save(manager);
        }
    }

}

