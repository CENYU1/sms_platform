package com.qf.smsplatform.webmaster.service;

import com.qf.smsplatform.webmaster.dto.DataGridResult;
import com.qf.smsplatform.webmaster.dto.QueryDTO;
import com.qf.smsplatform.webmaster.pojo.TPhase;

import java.util.List;

public interface PhaseService {
    public int addPhase(TPhase tPhase);
    public int delPhase(Long id);
    public int updatePhase(TPhase tPhase);
    public TPhase findById(Long id);
    public List<TPhase> findALL();

    public DataGridResult findByPage(QueryDTO queryDTO);


    void syncPhase();
}
