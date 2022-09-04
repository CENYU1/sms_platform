package com.qf.smsplatform.webmaster.service.impl;


//
//                            _ooOoo_  
//                           o8888888o  
//                           88" . "88  
//                           (| -_- |)  
//                            O\ = /O  
//                        ____/`---'\____  
//                      .   ' \\| |// `.  
//                       / \\||| : |||// \  
//                     / _||||| -:- |||||- \  
//                       | | \\\ - /// | |  
//                     | \_| ''\---/'' | |  
//                      \ .-\__ `-` ___/-. /  
//                   ___`. .' /--.--\ `. . __  
//                ."" '< `.___\_<|>_/___.' >'"".  
//               | | : `- \`.;`\ _ /`;.`/ - ` : | |  
//                 \ \ `-. \_ __\ /__ _/ .-` / /  
//         ======`-.____`-.___\_____/___.-`____.-'======  
//                            `=---='  
//  
//         .............................................  
//                  佛祖镇楼                  BUG辟易  
//          佛曰:  
//                  写字楼里写字间，写字间里程序员；  
//                  程序人员写程序，又拿程序换酒钱。  
//                  酒醒只在网上坐，酒醉还来网下眠；  
//                  酒醉酒醒日复日，网上网下年复年。  
//                  但愿老死电脑间，不愿鞠躬老板前；  
//                  奔驰宝马贵者趣，公交自行程序员。  
//                  别人笑我忒疯癫，我笑自己命太贱；  


import com.qf.constant.CacheConstants;
import com.qf.smsplatform.webmaster.dao.FiltersMapper;
import com.qf.smsplatform.webmaster.mq.PushFilterChangeService;
import com.qf.smsplatform.webmaster.pojo.TFilters;
import com.qf.smsplatform.webmaster.service.FiltersService;
import com.qf.smsplatform.webmaster.service.api.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Service;

/**
 * Created by jackiechan on 2020-02-28 09:54
 *
 * @Author jackiechan
 */
@Service
public class FiltersServiceImpl implements FiltersService {
    @Autowired
    private FiltersMapper mapper;
    @Autowired
    private CacheService cacheService;
    @Autowired
    private PushFilterChangeService filterChangeService;

    @Override
    public int updateFilters(String filterOrder, String filters) throws Exception {
        int update = mapper.updateFilters(filterOrder, filters);
        if (update == 1) {
            //如果数据库中有这个数据,才更新到 redis
            cacheService.saveCache(CacheConstants.CACHE_PREFIX_SMS_FILTERS_ORDER + filterOrder, filters);
            //已经添加到 redis,但是我们的策略模块并不知道,我们需要通知策略模块
            MessageChannel channel = filterChangeService.message_channel();
            //发送消息,随便发送一个内容,主要让消费者知道我们发生了内容变化的情况
            channel.send(new GenericMessage<String>("suibianxie"));
        }

        return update;
    }

    @Override
    public void addFilters(TFilters filter) throws Exception {
        mapper.addFilters(filter);
        //添加到数据库中之后顺便添加到缓存中
        cacheService.saveCache(CacheConstants.CACHE_PREFIX_SMS_FILTERS_ORDER + filter.getFilterorder(), filter.getFilters());
        //上面已经更新完 redis 了,但是策略模块不知道,我们需要通知策略模块
        MessageChannel channel = filterChangeService.message_channel();
        //发送消息,随便发送一个内容,主要让消费者知道我们发生了内容变化的情况
        channel.send(new GenericMessage<String>("suibianxie"));
    }

}
