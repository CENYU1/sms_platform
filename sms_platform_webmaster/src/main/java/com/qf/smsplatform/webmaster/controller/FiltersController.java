package com.qf.smsplatform.webmaster.controller;


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


import com.qf.smsplatform.webmaster.pojo.TFilters;
import com.qf.smsplatform.webmaster.service.FiltersService;
import com.qf.smsplatform.webmaster.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by jackiechan on 2020-02-28 09:49
 *
 * @Author jackiechan
 */
@RestController
public class FiltersController {
    @Autowired
    private FiltersService filtersService;

    /**
     * 修改某个分组的过滤器的顺序
     * @param filterOrder 过滤器的分组id
     * @param filters 过滤器
     * @return
     */
    @RequestMapping("/sys/filters/update")
    public R updateFilters(String filterOrder, String filters) {
        try {
            int updateFilters = filtersService.updateFilters(filterOrder, filters);
            if (updateFilters==1) {
                return R.ok();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return R.error();
    }


    /**
     * 添加一个过滤器的分组
     * @param filter 过滤器
     * @return
     */
    @RequestMapping("/sys/filters/add")
    public R addFilters( TFilters filter) {
        try {
            filtersService.addFilters(filter);
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return R.error();
    }
}
