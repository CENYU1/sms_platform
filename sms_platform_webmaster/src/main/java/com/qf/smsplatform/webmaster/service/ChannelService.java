package com.qf.smsplatform.webmaster.service;

import com.qf.smsplatform.webmaster.dto.DataGridResult;
import com.qf.smsplatform.webmaster.dto.QueryDTO;
import com.qf.smsplatform.webmaster.pojo.TChannel;

import java.util.List;

public interface ChannelService {
    public int addChannel(TChannel tChannel);

    public int delChannel(Long id);

    public int updateChannel(TChannel tChannel);

    public TChannel findById(Long id);

    public List<TChannel> findALL();

    public DataGridResult findByPage(QueryDTO queryDTO);

    /**
     * 同步管道信息到Redis
     */
    void sync();
}
