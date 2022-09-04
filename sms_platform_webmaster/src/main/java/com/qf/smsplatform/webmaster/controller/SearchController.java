package com.qf.smsplatform.webmaster.controller;

import com.qf.smsplatform.webmaster.pojo.TAdminUser;
import com.qf.smsplatform.webmaster.service.api.CacheService;
import com.qf.smsplatform.webmaster.service.api.SearchService;
import com.qf.smsplatform.webmaster.util.JsonUtil;
import com.qf.smsplatform.webmaster.util.SearchPojo;
import com.qf.smsplatform.webmaster.util.ShiroUtils;
import com.qf.smsplatform.webmaster.util.TableData;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * 搜索服务
 */
@RestController
public class SearchController {

    @Resource
    private SearchService searchService;

    @Resource
    private CacheService cacheService;


    @RequestMapping("/sys/search/list")
    public TableData smssearch(SearchPojo criteria) {
        TAdminUser userEntity = ShiroUtils.getUserEntity();
        Integer clientid = userEntity.getClientid();
        if (clientid != 0) {//非管理员只能查自己
            criteria.setClientID(clientid);
        }
        criteria.setHighLightPostTag("</font>");
        criteria.setHighLightPreTag("<font style='color:red'>");
        String str = JsonUtil.getJSON(criteria);
        //查询日志总条数
        Long count = searchService.searchLogCount(str);
        if (count != null && count > 0) {
            //查询日志的具体数据
            List<Map> list = searchService.searchLog(str);
            for (Map map : list) {
                String clientID = String.valueOf(map.get("clientID"));
                Map<String, String> hmget = cacheService.hMGet("CLIENT:" + clientID);
                String corpname = hmget.get("corpname");
                map.put("corpname", corpname);
                Object sendTime1 = map.get("sendTime");
                if (!StringUtils.isEmpty(sendTime1)) {
                    //                  Long sendTime = Long.parseLong(sendTime1.toString());
                    //   String sendTimeStr = DateUtils.stringToDate(sendTime1);
//                    map.put("sendTimeStr", sendTimeStr);
                    map.put("sendTimeStr", sendTime1);
                } else {
                    map.put("sendTimeStr", "");
                }
            }
            return new TableData(count, list);
        }
        return new TableData(0, null);
    }


}
