package com.qf.smsplatform.webmaster.controller;

import com.qf.constant.RabbitMQConstants;
import com.qf.model.StandardSubmit;
import com.qf.smsplatform.webmaster.dto.SmsDTO;
import com.qf.smsplatform.webmaster.pojo.TAdminUser;
import com.qf.smsplatform.webmaster.util.R;
import com.qf.smsplatform.webmaster.util.ShiroUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
public class SmsController {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @ResponseBody
    @RequestMapping("/sys/sms/save")
    public R addBlack(@RequestBody SmsDTO smsDTO){
        TAdminUser userEntity = ShiroUtils.getUserEntity();
        Integer clientid = userEntity.getClientid();
        String mobile = smsDTO.getMobile();
        String[] split = mobile.split("\n");
        for (String s : split) {
            StandardSubmit standardSubmit = new StandardSubmit();
            standardSubmit.setClientID(clientid);
            standardSubmit.setDestMobile(s);
            standardSubmit.setMessageContent(smsDTO.getContent());
            standardSubmit.setSource(2);//因为是通过网页发送的,所以不需要接收状态报告,所以设置 2
            standardSubmit.setSendTime(new Date());
            rabbitTemplate.convertAndSend(RabbitMQConstants.TOPIC_PRE_SEND, standardSubmit);
        }
        return R.ok();
    }

}
