package com.yws.plane.web;

import com.auth0.jwt.JWT;
import com.yws.plane.annotation.UserLoginToken;
import com.yws.plane.entity.Info;
import com.yws.plane.entity.User;
import com.yws.plane.service.NewsService;
import com.yws.plane.service.UserService;
import com.yws.plane.util.JSONData;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author AlmostLover
 */
@RestController
@RequestMapping("/api")
public class IndexController {
    @Autowired
    private NewsService newsService;
    @Autowired
    private UserService userService;

    @GetMapping("/news/{id}")
    @CrossOrigin
    public String news(@PathVariable Integer id) {
        return JSONData.toJsonString(200, "", newsService.getById(id));
    }

    @GetMapping("/authentication")
    @CrossOrigin
    @UserLoginToken
    public String authentication(HttpServletRequest request) {
        String token = request.getHeader("token");
        if(!StringUtils.isEmpty(token)){
            String userId = JWT.decode(token).getAudience().get(0);
            User user = userService.getById(userId);
            if(StringUtils.isEmpty(user.getEmail())){
                return JSONData.toJsonString(400, "邮箱未绑定", "");
            }
        }
        return JSONData.toJsonString(200, "身份验证通过", "");
    }

    @PostMapping("/email")
    @CrossOrigin()
    public String addEmail(@RequestBody Info info, HttpServletRequest request,HttpSession session){
        String code2 = String.valueOf(session.getAttribute("code"));
        if(info.getCode().equals(code2)){
            System.out.println(info.getCode());
            String token = request.getHeader("token");
            String userId = JWT.decode(token).getAudience().get(0);
            User user = new User();
            user.setId(Integer.valueOf(userId));
            user.setEmail(info.getEmail());
            boolean res = userService.updateById(user);
            if(res){
                return JSONData.toJsonString(200, "邮箱绑定成功", "");
            }
        }
        return JSONData.toJsonString(500, "验证码错误", "");
    }
}
