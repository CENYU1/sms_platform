package com.qf.smsplatform.webmaster.service;

import com.qf.smsplatform.webmaster.dto.DataGridResult;
import com.qf.smsplatform.webmaster.dto.QueryDTO;
import com.qf.smsplatform.webmaster.pojo.TBlackList;

import java.util.List;

public interface BlackService {
    public int addBlack(TBlackList tBlackList);

    public int delBlack(Long id);

    public int updateBlack(TBlackList tBlackList);

    public TBlackList findById(Long id);

    public List<TBlackList> findAll();

    public DataGridResult findByPage(QueryDTO queryDTO);

    /**
     * 同步黑名单信息到Redis缓存
     * @return
     */
    boolean sync();
}
