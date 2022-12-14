package com.qf.smsplatform.webmaster.service;

import com.qf.smsplatform.webmaster.dto.DataGridResult;
import com.qf.smsplatform.webmaster.dto.QueryDTO;
import com.qf.smsplatform.webmaster.pojo.TAcountRecord;

import java.util.List;

public interface AcountRecordService {

    public int addAcount(TAcountRecord tAcountRecord);

    public int delAcount(Long id);

    public int updateAcount(TAcountRecord tAcountRecord);

    public TAcountRecord findById(Long id);

    public List<TAcountRecord> findAll();

    public DataGridResult findByPage(QueryDTO queryDTO);


}
