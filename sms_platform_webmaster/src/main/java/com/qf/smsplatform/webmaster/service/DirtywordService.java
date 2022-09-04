package com.qf.smsplatform.webmaster.service;

import com.qf.smsplatform.webmaster.dto.DataGridResult;
import com.qf.smsplatform.webmaster.dto.QueryDTO;
import com.qf.smsplatform.webmaster.pojo.TDirtyword;

import java.util.List;

public interface DirtywordService {

    public int addDirtyword(TDirtyword tDirtyword);

    public int delDirtyword(Long id);

    public int updateDirtyword(TDirtyword tDirtyword);

    public TDirtyword findById(Long id);

    public List<TDirtyword> findAll();

    public DataGridResult findByPage(QueryDTO queryDTO);

    /**
     * 同步敏感词到Redis缓存
     * @return
     */
    boolean sync();
}
