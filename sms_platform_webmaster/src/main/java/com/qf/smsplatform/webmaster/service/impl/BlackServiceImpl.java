package com.qf.smsplatform.webmaster.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qf.constant.CacheConstants;
import com.qf.smsplatform.webmaster.dao.TBlackListMapper;
import com.qf.smsplatform.webmaster.dto.DataGridResult;
import com.qf.smsplatform.webmaster.dto.QueryDTO;
import com.qf.smsplatform.webmaster.pojo.TBlackList;
import com.qf.smsplatform.webmaster.pojo.TBlackListExample;
import com.qf.smsplatform.webmaster.service.BlackService;
import com.qf.smsplatform.webmaster.service.api.CacheService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BlackServiceImpl implements BlackService {

    @Resource
    private TBlackListMapper tBlackListMapper;

    @Resource
    private CacheService cacheService;

    @Override
    public int addBlack(TBlackList tBlackList) {
        cacheService.saveCache(CacheConstants.CACHE_PREFIX_BLACK+tBlackList.getMobile(),"1");
        return tBlackListMapper.insertSelective(tBlackList);
    }

    @Override
    public int delBlack(Long id) {
        TBlackList tBlackList = findById(id);
        cacheService.del(CacheConstants.CACHE_PREFIX_BLACK+tBlackList.getMobile());
        return tBlackListMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int updateBlack(TBlackList tBlackList) {
        int i =  tBlackListMapper.updateByPrimaryKey(tBlackList);
        if(i>0){
            cacheService.saveCache(CacheConstants.CACHE_PREFIX_BLACK+tBlackList.getMobile(),"1");
        }
        return i;
    }

    /*
    将手机号作为Redis的key:
        BLACK:18888888888
        BLACK:18888889999
        BLACK:18888887777
        BLACK:18888886666
    校验方式
        redis - get BLACK:submit.getDestMobile();
    ------------------------------------------
    将黑名单手机号存储在Redis的set集合中
        BLACK:MOBILE  18888888888,18888889999,18888887777
    校验方式
        redis - isMember(手机号);


     */

    @Override
    public TBlackList findById(Long id) {
        return tBlackListMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<TBlackList> findAll() {
        return tBlackListMapper.selectByExample(null);
    }

    @Override
    public DataGridResult findByPage(QueryDTO queryDTO) {
        PageHelper.offsetPage(queryDTO.getOffset(),queryDTO.getLimit());
        TBlackListExample example = new TBlackListExample();
        String sort = queryDTO.getSort();
        if(!StringUtils.isEmpty(sort)){
            example.setOrderByClause("id");
        }
        List<TBlackList> tBlackLists = tBlackListMapper.selectByExample(example);
        PageInfo<TBlackList> info = new PageInfo<>(tBlackLists);
        long total = info.getTotal();
        DataGridResult result = new DataGridResult(total,tBlackLists);
        return result;
    }


    @Override
    public boolean sync() {
        List<TBlackList> tBlackLists = tBlackListMapper.selectByExample(null);
        for (TBlackList tBlackList : tBlackLists) {
            cacheService.saveCache(CacheConstants.CACHE_PREFIX_BLACK +tBlackList.getMobile(),"1");
        }
        return true;
    }
}
