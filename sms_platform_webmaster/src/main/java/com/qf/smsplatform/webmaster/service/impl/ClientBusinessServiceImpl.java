package com.qf.smsplatform.webmaster.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qf.constant.CacheConstants;
import com.qf.smsplatform.webmaster.dao.TClientBusinessMapper;
import com.qf.smsplatform.webmaster.dto.DataGridResult;
import com.qf.smsplatform.webmaster.dto.QueryDTO;
import com.qf.smsplatform.webmaster.pojo.TClientBusiness;
import com.qf.smsplatform.webmaster.pojo.TClientBusinessExample;
import com.qf.smsplatform.webmaster.service.ClientBusinessService;
import com.qf.smsplatform.webmaster.service.api.CacheService;
import com.qf.smsplatform.webmaster.util.JsonUtils;
import com.qf.smsplatform.webmaster.util.MD5Builder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class ClientBusinessServiceImpl implements ClientBusinessService {

    @Resource
    private TClientBusinessMapper tClientBusinessMapper;

    @Resource
    private CacheService cacheService;

    @Override
    public int addClientBusiness(TClientBusiness tClientBusiness) {
        String pwd = tClientBusiness.getPwd();
        String build = MD5Builder.build(pwd, "UTF-8");
        String md5PASS = build.toUpperCase();
        tClientBusiness.setPwd(md5PASS);
        //持久层已经获得返回的主键
        int i = tClientBusinessMapper.insertSelective(tClientBusiness);
        Map<String, String> map1 = JsonUtils.objectToMap(tClientBusiness);
        cacheService.hMSet("CLIENT:"+tClientBusiness.getId(),map1);
        return i;
    }

    @Override
    public int delClientBusiness(Long id) {
        cacheService.del("CLIENT:"+id);
        return tClientBusinessMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int updateClientBusiness(TClientBusiness tClientBusiness) {
        int i =  tClientBusinessMapper.updateByPrimaryKey(tClientBusiness);
        if(i>0){
            TClientBusiness byId = findById(tClientBusiness.getId());
            Map<String, String> map1 = JsonUtils.objectToMap(byId);
            cacheService.hMSet("CLIENT:"+tClientBusiness.getId(),map1);
        }
        return i;
    }

    @Override
    public TClientBusiness findById(Long id) {
        return tClientBusinessMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<TClientBusiness> findAll() {
        return tClientBusinessMapper.selectByExample(null);
    }

    @Override
    public DataGridResult findByPage(QueryDTO queryDTO) {
        PageHelper.offsetPage(queryDTO.getOffset(), queryDTO.getLimit());
        TClientBusinessExample example = new TClientBusinessExample();
        String sort = queryDTO.getSort();
        if (!StringUtils.isEmpty(sort)) {
            example.setOrderByClause("id");
        }
        List<TClientBusiness> tClientBusinesses = tClientBusinessMapper.selectByExample(example);
        for (TClientBusiness tClientBusiness : tClientBusinesses) {
            Long id = tClientBusiness.getId();
            Long paidValueStr  = tClientBusiness.getFee();
            if(!StringUtils.isEmpty(paidValueStr)){
                Long i = paidValueStr/1000;
                tClientBusiness.setPaidValueStr(i+"元");
            }
        }
        PageInfo<TClientBusiness> info = new PageInfo<>(tClientBusinesses);
        long total = info.getTotal();
        DataGridResult result = new DataGridResult(total, tClientBusinesses);
        return result;
    }


    @Override
    public boolean sync() {
        List<TClientBusiness> list = this.findAll();
        // 遍历存放到Redis中
        for (TClientBusiness business : list) {
            cacheService.hMSet(CacheConstants.CACHE_PREFIX_CLIENT + business.getId(),JsonUtils.object2Map(business));
        }
        return true;
    }


}
