package com.qf.smsplatform.webmaster.service;

import com.qf.smsplatform.webmaster.dto.DataGridResult;
import com.qf.smsplatform.webmaster.dto.QueryDTO;
import com.qf.smsplatform.webmaster.pojo.TClientBusiness;

import java.util.List;

public interface ClientBusinessService {
    public int addClientBusiness(TClientBusiness tClientBusiness);

    public int delClientBusiness(Long id);

    public int updateClientBusiness(TClientBusiness tClientBusiness);

    public TClientBusiness findById(Long id);

    public List<TClientBusiness> findAll();

    public DataGridResult findByPage(QueryDTO queryDTO);

    /**
     * 同步数据
     * @return
     */
    boolean sync();
}
