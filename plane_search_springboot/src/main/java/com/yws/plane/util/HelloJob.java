package com.yws.plane.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yws.plane.entity.Message;
import com.yws.plane.service.MailService;
import com.yws.plane.service.MessageService;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HelloJob implements Job {

    @Autowired
    private MailService mailService;
    @Autowired
    private MessageService messageService;

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //打印当前的执行时间 例如 2017-11-23 00:00:00
        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("现在的时间是：" + sf.format(date));
        //具体的业务逻辑
        System.out.println("开始任务");
        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        mailService.send((String) jobDataMap.get("email"),"起飞提醒",MessageUtil.getEmailTemp(jobDataMap));
        Message message = new Message();
        message.setStatus(1);
        QueryWrapper<Message> wrapper = new QueryWrapper<>();
        Message message1 = new Message();
        message1.setUid((Integer) jobDataMap.get("uid"));
        message1.setFightId((Long) jobDataMap.get("fightId"));
        wrapper.setEntity(message1);
        messageService.update(message,wrapper);
    }

}
