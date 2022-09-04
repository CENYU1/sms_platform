package com.qf.smsplatform.webmaster.listen;

import com.qf.smsplatform.webmaster.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class SyncAllCacheData implements ApplicationRunner {

    @Autowired
    private BlackService blackService;
    @Autowired
    private ChannelService channelService;
    @Autowired
    private ClientChannelService clientChannelService;
    @Autowired
    private ClientBusinessService clientBusinessService;
    @Autowired
    private PhaseService phaseService;
    @Autowired
    private DirtywordService dirtywordService;



    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("==========================================");
        System.out.println("开始同步数据到Redis缓存");
        System.out.println("==========================================");
        //同步黑名单数据到redis
        blackService.sync();
        //同步客户绑定的通道数据到redis
        channelService.sync();
        //同步客户信息到redis
        clientBusinessService.sync();
        //同步路由数据到redis
        clientChannelService.syncAllDataToCache();
        //同步号段补全策略到redis
        phaseService.syncPhase();
        //敏感词信息同步到redis
        dirtywordService.sync();
    }
}
