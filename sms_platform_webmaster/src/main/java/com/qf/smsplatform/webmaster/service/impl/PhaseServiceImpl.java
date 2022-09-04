package com.qf.smsplatform.webmaster.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qf.constant.CacheConstants;
import com.qf.smsplatform.webmaster.dao.TInstMapper;
import com.qf.smsplatform.webmaster.dao.TPhaseMapper;
import com.qf.smsplatform.webmaster.dto.DataGridResult;
import com.qf.smsplatform.webmaster.dto.QueryDTO;
import com.qf.smsplatform.webmaster.pojo.TInst;
import com.qf.smsplatform.webmaster.pojo.TInstExample;
import com.qf.smsplatform.webmaster.pojo.TPhase;
import com.qf.smsplatform.webmaster.pojo.TPhaseExample;
import com.qf.smsplatform.webmaster.service.PhaseService;
import com.qf.smsplatform.webmaster.service.api.CacheService;
import com.qf.smsplatform.webmaster.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PhaseServiceImpl implements PhaseService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PhaseServiceImpl.class);

    @Resource
    private TPhaseMapper tPhaseMapper;

    @Resource
    private TInstMapper tInstMapper;

    @Resource
    private CacheService cacheService;

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private Integer port;

    @Override
    public int addPhase(TPhase tPhase) {
        cacheService.saveCache("PHASE:"+tPhase.getPhase(),tPhase.getProvId()+"&"+tPhase.getCityId());
        return tPhaseMapper.insertSelective(tPhase);
    }

    @Override
    public int delPhase(Long id) {
        TPhase tPhase = findById(id);
        cacheService.del("PHASE:"+tPhase.getPhase());
        return tPhaseMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int updatePhase(TPhase tPhase) {
        int i= tPhaseMapper.updateByPrimaryKey(tPhase);
        if(i>0){
            cacheService.saveCache("PHASE:"+tPhase.getPhase(),tPhase.getProvId()+"&"+tPhase.getCityId());
        }
        return i;
    }

    @Override
    public TPhase findById(Long id) {
        return tPhaseMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<TPhase> findALL() {
        return tPhaseMapper.selectByExample(null);
    }

    @Override
    public DataGridResult findByPage(QueryDTO queryDTO) {
        PageHelper.offsetPage(queryDTO.getOffset(), queryDTO.getLimit());
        TPhaseExample example = new TPhaseExample();
        String sort = queryDTO.getSort();
        if (!StringUtils.isEmpty(sort)) {
            example.setOrderByClause("id");
        }
        List<TPhase> messages = tPhaseMapper.selectByExample(example);
        for (TPhase message : messages) {
            Long provId = message.getProvId();
            TInst tInst1 = tInstMapper.selectByPrimaryKey(provId);
            message.setProvName(tInst1.getAreaname());
            Long cityId = message.getCityId();
            TInstExample tInstExample = new TInstExample();
            TInstExample.Criteria criteria = tInstExample.createCriteria();
            criteria.andIdEqualTo(cityId);
            criteria.andParentidEqualTo(provId);
            List<TInst> tInsts = tInstMapper.selectByExample(tInstExample);
            if(tInsts!=null&&tInsts.size()>0){
                TInst tInst = tInsts.get(0);
                message.setCityName(tInst.getAreaname());
            }
        }

        PageInfo<TPhase> info = new PageInfo<>(messages);
        long total = info.getTotal();
        DataGridResult result = new DataGridResult(total, messages);
        return result;
    }



    // 同步数据
    @Override
    public void syncPhase() {
        Jedis jedis = new Jedis(host, port);
        Pipeline p = jedis.pipelined();
        List<TPhase> allLists = findALL();
        for (TPhase tPhase : allLists) {
            p.set(CacheConstants.CACHE_PREFIX_PHASE +tPhase.getPhase(), JsonUtils.object2Json(tPhase.getProvId()+","+tPhase.getCityId()));
        }
        p.sync();
        jedis.close();
        LOGGER.info("同步手机号段数据完成");
    }
}
