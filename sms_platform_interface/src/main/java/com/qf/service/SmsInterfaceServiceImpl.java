package com.qf.service;

import com.qf.config.IdWorker;
import com.qf.constant.CacheConstants;
import com.qf.constant.RabbitMQConstants;
import com.qf.constant.ReportStateConstants;
import com.qf.constant.SourceType;
import com.qf.form.SmsInterfaceForm;
import com.qf.model.StandardSubmit;
import com.qf.service.api.CacheFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author cenyu
 */
@Service
@Slf4j
public class SmsInterfaceServiceImpl implements SmsInterfaceService {
    @Resource
    private CacheFeign cacheFeign;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private IdWorker idWorker;

    @Override
    public Map<String, String> checkInterfaceForm(SmsInterfaceForm interfaceForm) {
        Map<String, String> resultMap = new HashMap();

        //--------------------1. 校验ClientID-----------------------------------------
        //--------------------101 认证错：clientId错误----------------------------------
        //1.1 根据用户传递的clientID去缓存模块查询客户信息
        String clientID = interfaceForm.getClientID();
        Map map = cacheFeign.hMGet(CacheConstants.CACHE_PREFIX_CLIENT + clientID);

        //1.2 判断map集合是否为空以及长度
        if(map == null || map.size() == 0){
            // 根据clientID什么都没查询到
            log.info("【接口模块】 认证错误：clientId错误。  clientID={}", clientID);
            resultMap.put("code", "101");
            resultMap.put("msg", "认证错误：clientId错误");
            return resultMap;
        }

        //---------------------2. 校验pwd-------------------------------------------
        //---------------------102	密码错误----------------------------------------
        //2.1 获取用户输入的pwd
        String pwd = interfaceForm.getPwd();
        //2.2 判断两个pwd是否相等
        if(StringUtils.isEmpty(pwd) || !pwd.equals(map.get("pwd"))){
            // 没传递密码,或者密码错误
            log.info("【接口模块】 密码错误。  pwd={}", pwd);
            resultMap.put("code", "102");
            resultMap.put("msg", "密码错误");
            return resultMap;
        }

        //---------------------3. 校验ip地址----------------------------------------
        //---------------------103	IP校验错误----------------------------------------
        //3.1 获取用户ip
        String ip = interfaceForm.getIp();
        //3.2 校验用户的ip和允许的ip是否一致
        String ipAddress = (String) map.get("ipaddress");
        //3.3 声明一个标识
        boolean flag = false;
        for (String s : ipAddress.split(",")) {
            if(!StringUtils.isEmpty(ip) && ip.equals(s)){
                // 当前用户的ip地址,是白名单内的
                flag = true;
                break;
            }
        }
        //3.4 如果ip不属于白名单  // localhost的ip地址是0:0:0:0:0:0:0:1
        if(!flag){
            log.info("【接口模块】 IP校验错误。  ip={}", ip);
            resultMap.put("code", "103");
            resultMap.put("msg", "IP校验错误");
            return resultMap;
        }

        //---------------------4. 校验短信长度----------------------------------------
        //---------------------104	消息长度错，为空或超长（目前定为500个字）--------------
        //4.1 校验短信长度
        String content = interfaceForm.getContent();
        //4.2 校验非空和长度
        if(StringUtils.isEmpty(content) || content.length() > 500){
            log.info("【接口模块】 消息长度错，为空或超长（目前定为500个字）。  content={}",content);
            resultMap.put("code", "104");
            resultMap.put("msg", "消息长度错，为空或超长（目前定为500个字）");
            return resultMap;
        }

        //---------------------5. 校验手机号----------------------------------------
        //---------------------105	手机号错误----------------------------------------
        //5.1 获取全部手机号
        String mobiles = interfaceForm.getMobile();
        //5.2 手机号不能为空
        if(StringUtils.isEmpty(mobiles)){
            log.info("【接口模块】 手机号错误  mobiles={}",mobiles);
            resultMap.put("code", "105");
            resultMap.put("msg", "手机号错误");
            return resultMap;
        }
        //5.3 手机号的数量不能超过100个
        String[] mobileArray = mobiles.split(",");
        if(mobileArray.length == 0 || mobileArray.length > 100){
            log.info("【接口模块】 手机号错误  mobileArray={}", Arrays.toString(mobileArray));
            resultMap.put("code", "105");
            resultMap.put("msg", "手机号错误");
            return resultMap;
        }
        //5.4 使用正则校验手机号格式是否正确
        Set<String> errorMobile = new HashSet<>();
        String regex = "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$";
        for (String mobile : mobileArray) {
            // 使用正则表达式校验手机号格式
            if(!mobile.matches(regex)){
                // 手机号有问题
                errorMobile.add(mobile);
            }
        }
        //5.5 抛出异常的同时,还要包含全部的错误手机号信息
        if(errorMobile.size() > 0){
            // 有错误的手机号
            log.info("【接口模块】 手机号错误  mobileArray={}", Arrays.toString(mobileArray));
            resultMap.put("code", "105");
            resultMap.put("msg", "手机号错误");
            return resultMap;
        }

        //----------------------6. 校验srcID---------------------------------------
        //----------------------106	下行编号（srcID）错误---------------------------------------
        //6.1 获取用户输入的srcID
        String srcIDStr = interfaceForm.getSrcID();
        Long srcID = null;
        //6.2 不等于Null的前提下，转换为整型
        if(!StringUtils.isEmpty(srcIDStr)){
            try {
                srcID = Long.parseLong(srcIDStr);
            } catch (NumberFormatException e) {
                log.info("【接口模块】 下行编号（srcID）错误  srcID={}", srcIDStr);
                resultMap.put("code", "106");
                resultMap.put("msg", "下行编号（srcID）错误");
                return resultMap;
            }
        }

        // ----------------------7. 如果上述校验全部通过,发送消息到RabbitMQ----------------------------
        // 根据手机号发送消息到MQ
        for (String mobile : mobileArray) {
            StandardSubmit submit = new StandardSubmit();
            // sequenceId为submit的唯一标识,需要在这里封装
            submit.setSequenceId(String.valueOf(idWorker.nextId()));
            //客户端ID
            submit.setClientID(Integer.parseInt(clientID));
            //设置短信发送时间
            submit.setSendTime(new Date());
            //设置短信目的手机号
            submit.setDestMobile(mobile);
            //设置短信发送内容
            submit.setMessageContent(content);
            //消息的优先级 0 最低 --- 3 最高
            submit.setMessagePriority(Short.parseShort((String) map.get("priority")));
            //设置短信发送方式为Http
            submit.setSource(SourceType.HTTP);
            //客户侧唯一序列号
            submit.setSrcSequenceId(srcID);
            //手机接收的状态值  0 成功 1 等待 2 失败
            submit.setReportState(ReportStateConstants.WAIT);
            rabbitTemplate.convertAndSend(
                    RabbitMQConstants.TOPIC_PRE_SEND + "_exchange",
                    "routingKey",
                    submit);
        }
        resultMap.put("code", "0");
        resultMap.put("msg", "接收成功");
        return resultMap;
    }
}
