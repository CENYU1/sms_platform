package com.qf.controller;

import com.qf.form.SmsInterfaceForm;
import com.qf.service.SmsInterfaceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author cenyu
 */
@RestController
@RequestMapping("/sms")
@Slf4j
public class InterfaceController {
    /**
     * 获取Gitee配置文件中nginx代理的ip地址, 请求头信息:
     */
    @Value("${ip-header}")
    private List<String> ipHeaders;

    @Autowired
    private SmsInterfaceService smsInterfaceService;

    /**
     * 接收用户请求, 返回响应编码
     * 请服务方url地址：http://ip:port/smsInterface,客户方将下行数据发送到该url
     */
    @PostMapping("/smsInterface")
    public Map smsInterface(SmsInterfaceForm smsInterfaceForm, HttpServletRequest req){
        //1. 获取用户请求时携带的ip地址信息
        String ip = getReallyIpAddress(req);

        //2. 将ip地址封装到form对象中
        smsInterfaceForm.setIp(ip);

        //3. 对参数做非空校验
        Map resultMap = checkNotNull(smsInterfaceForm);
        if (resultMap != null) {
            log.info("[接口模块 - 接口客户请求] 参数非空校验失败! resultMap = {}, interfaceForm= {}", resultMap, smsInterfaceForm);
            return resultMap;
        }

        //4. 调用service做处理
        resultMap = smsInterfaceService.checkInterfaceForm(smsInterfaceForm);

        //5. 根据service结果反馈给客户
        return resultMap;
    }

    /**
     * 不为空校验
     */
    private Map checkNotNull(SmsInterfaceForm smsInterfaceForm) {
        Map<String, String> resultMap = new HashMap();
        if (StringUtils.isEmpty(smsInterfaceForm.getClientID())) {
            resultMap.put("code", "101");
            resultMap.put("msg", "认证错：clientId错误");
            return resultMap;
        }
        if (StringUtils.isEmpty(smsInterfaceForm.getPwd())) {
            resultMap.put("code", "102");
            resultMap.put("msg", "密码错误");
            return resultMap;
        }
        if (StringUtils.isEmpty(smsInterfaceForm.getIp())) {
            resultMap.put("code", "103");
            resultMap.put("msg", "IP校验错误");
            return resultMap;
        }
        if (StringUtils.isEmpty(smsInterfaceForm.getContent())) {
            resultMap.put("code", "104");
            resultMap.put("msg", "消息长度错，为空或超长（目前定为500个字）");
            return resultMap;
        }
        if (StringUtils.isEmpty(smsInterfaceForm.getMobile())) {
            resultMap.put("code", "105");
            resultMap.put("msg", "手机号错误");
            return resultMap;
        }
        if (StringUtils.isEmpty(smsInterfaceForm.getSrcID())) {
            resultMap.put("code", "106");
            resultMap.put("msg", "下行编号（srcID）错误");
            return resultMap;
        }
        return null;
    }

    /**
     * 从请求中获取并校验ip地址是否正确
     * @param req
     * @return
     */
    private String getReallyIpAddress(HttpServletRequest req) {
        for (String ipHeader : ipHeaders) {
            //1. 校验请求头不为空
            if(!StringUtils.isEmpty(ipHeader) && ipHeader.length() > 0){
                //2. 获取真实的ip地址
                String ip = req.getHeader(ipHeader);
                //3. 判断是否真的拿到了ip地址
                if(!StringUtils.isEmpty(ip) && !"unknown".equalsIgnoreCase(ip)){
                    //4. 获取到了真实的ip
                    return ip;
                }
            }
        }
        //5. 循环结束,没有拿到真实的ip地址,通过传统的方式获取ip
        return req.getRemoteAddr();
    }
}
