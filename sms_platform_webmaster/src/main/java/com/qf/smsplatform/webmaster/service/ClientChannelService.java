package com.qf.smsplatform.webmaster.service;

import com.qf.smsplatform.webmaster.dto.DataGridResult;
import com.qf.smsplatform.webmaster.dto.QueryDTO;
import com.qf.smsplatform.webmaster.pojo.TClientChannel;

import java.util.List;

public interface ClientChannelService {
    public int addClientChannel(TClientChannel tClientChannel);

    public int delClientChannel(Long id);

    public int updateClientChannel(TClientChannel tClientChannel);

    public TClientChannel findById(Long id);

    public List<TClientChannel> findAll();

    public DataGridResult findByPage(QueryDTO queryDTO);

    /**
     * 同步所有数据
     * @return
     */
    boolean syncAllDataToCache();

}
