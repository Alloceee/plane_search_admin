package com.yws.plane.web;

import com.auth0.jwt.JWT;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yws.plane.entity.*;
import com.yws.plane.service.*;
import com.yws.plane.util.CronUtil;
import com.yws.plane.util.HelloJob;
import com.yws.plane.util.JSONData;
import com.yws.plane.util.MessageUtil;
import org.apache.commons.lang3.StringUtils;
import org.quartz.JobDataMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("/api")
public class ClockController {
    @Autowired
    private MessageService messageService;
    @Autowired
    private IQuartzService quartzService;
    @Autowired
    private ChinaFightService chinaFightService;
    @Autowired
    private AbroadFightService abroadFightService;
    @Autowired
    private UserService userService;
    @Autowired
    private MailService mailService;

    @PostMapping("/send")
    @CrossOrigin()
    public String send(@RequestBody Info info, HttpSession session) {
        System.out.println(info.getEmail());
        Integer code = this.random(1000, 9999);
        if (!StringUtils.isEmpty(info.getPhone())) {
            String res = MessageUtil.phoneCode("18855109072", code);
            session.setAttribute("code",String.valueOf(code));
            return JSONData.toJsonString(200, "", res);
        } else if (!StringUtils.isEmpty(info.getEmail())) {
            session.setAttribute("code",code);
            mailService.send(info.getEmail(),"验证码","您的验证码为：<h2>"+code+"</h2>,请注意查收");
            return JSONData.toJsonString(200, "发送成功", null);
        }
        return JSONData.toJsonString(500, "没有匹配类型", null);
    }

    @PostMapping("/clock")
    @CrossOrigin
    public String clock(@RequestBody Message message,HttpServletRequest request) {
        String token = request.getHeader("token");
        String userId = JWT.decode(token).getAudience().get(0);
        User user = userService.getById(Integer.valueOf(userId));
        message.setUid(Integer.valueOf(userId));
        if (message.getFightId()!=null) {
            boolean res = messageService.save(message);
            if (res) {
                String startTime,startCity,endCity,startAirport = null;
                if(message.getType()==0){
                    ChinaFight chinaFight = chinaFightService.getById(message.getFightId());
                    startTime = chinaFight.getStartTime();
                    startCity = chinaFight.getStartCity();
                    endCity = chinaFight.getEndCity();
                    startAirport =  chinaFight.getStartAirport();
                }else {
                    AbroadFight abroadFight = abroadFightService.getById(message.getFightId());
                    startTime = abroadFight.getStartTime();
                    startCity = abroadFight.getStartCity();
                    endCity = abroadFight.getEndCity();
                    startAirport = abroadFight.getStartAirport();
                }
                JobDataMap jobDataMap = new JobDataMap();
                jobDataMap.put("uid",user.getId());
                jobDataMap.put("email",user.getEmail());
                jobDataMap.put("fightId",message.getFightId());
                jobDataMap.put("username",user.getUsername());
                jobDataMap.put("number",message.getNumber());
                jobDataMap.put("startTime",startTime);
                jobDataMap.put("startCity",startCity);
                jobDataMap.put("endCity",endCity);
                jobDataMap.put("startAirport",startAirport);
                quartzService.startJob(CronUtil.getCron(startTime), userId+"_"+message.getFightId(), "sendEmail",  jobDataMap, HelloJob.class);
                return JSONData.toJsonString(200, "添加成功", null);
            }
        } else {
            return JSONData.toJsonString(500, "参数错误", null);
        }
        return JSONData.toJsonString(500, "添加失败", null);
    }

    @GetMapping("/myClock")
    @CrossOrigin
    public String myClock(HttpServletRequest request){
        String token =  request.getHeader("token");
        Integer userId = Integer.valueOf(JWT.decode(token).getAudience().get(0));
        QueryWrapper<Message> wrapper = new QueryWrapper<>();
        Message message = new Message();
        message.setUid(userId);
        wrapper.setEntity(message);
        List<Message> messages = messageService.list(wrapper);
        return JSONData.toJsonString(200, "查询成功", messages);
    }

    private Integer random(Integer start, Integer end) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        return random.nextInt(start, end);
    }
}
