package com.qf.smsplatform.webmaster.service.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(value = "SMS-PLATFORM-SEARCH", fallback = SearchServiceFallback.class)
public interface SearchService {

    /**
     * 根据条件查询日志集合数据返回
     * @param paras
     */
    @RequestMapping(value = "/search/search", method = RequestMethod.POST)
    List<Map> searchLog(@RequestParam("params") String paras);

    /**
     * 根据条件查询日志总条数
     * @param paras
     */
    @RequestMapping(value = "/search/searchcount", method = RequestMethod.POST)
    Long searchLogCount(@RequestParam("params") String paras);

    /**
     * 统计报表查询
     * @param paras
     */
    @RequestMapping(value = "/search/statStatus", method = RequestMethod.POST)
    public Map<String, Long> statSendStatus(@RequestParam("params") String paras);

}
