package com.qf.smsplatform.webmaster.service.impl;

import com.qf.smsplatform.webmaster.dao.TInstMapper;
import com.qf.smsplatform.webmaster.pojo.TInst;
import com.qf.smsplatform.webmaster.pojo.TInstExample;
import com.qf.smsplatform.webmaster.service.InstService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstServiceImpl implements InstService {

    @Autowired
    private TInstMapper tInstMapper;

    @Override
    public List<TInst> findProvs() {
        TInstExample tInstExample = new TInstExample();
        TInstExample.Criteria criteria = tInstExample.createCriteria();
        criteria.andParentidEqualTo(1L);
        List<TInst> tInsts = tInstMapper.selectByExample(tInstExample);
        return tInsts;
    }

    @Override
    public List<TInst> findCitys(Long provId) {
        TInstExample tInstExample = new TInstExample();
        TInstExample.Criteria criteria = tInstExample.createCriteria();
        criteria.andParentidEqualTo(provId);
        List<TInst> tInsts = tInstMapper.selectByExample(tInstExample);
        return tInsts;
    }
}
