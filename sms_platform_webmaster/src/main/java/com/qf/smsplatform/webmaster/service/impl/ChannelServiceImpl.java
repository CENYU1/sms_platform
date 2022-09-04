package com.qf.smsplatform.webmaster.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qf.constant.CacheConstants;
import com.qf.smsplatform.webmaster.dao.TChannelMapper;
import com.qf.smsplatform.webmaster.dto.DataGridResult;
import com.qf.smsplatform.webmaster.dto.QueryDTO;
import com.qf.smsplatform.webmaster.pojo.TChannel;
import com.qf.smsplatform.webmaster.pojo.TChannelExample;
import com.qf.smsplatform.webmaster.service.ChannelService;
import com.qf.smsplatform.webmaster.service.api.CacheService;
import com.qf.smsplatform.webmaster.util.JsonUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;



@Service
public class ChannelServiceImpl implements ChannelService {

    @Resource
    private TChannelMapper tChannelMapper;

    @Resource
    private CacheService cacheService;



    @Override
    public int addChannel(TChannel tChannel) {
        return tChannelMapper.insertSelective(tChannel);
    }

    @Override
    public int delChannel(Long id) {
        return tChannelMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int updateChannel(TChannel tChannel) {
        return tChannelMapper.updateByPrimaryKey(tChannel);
    }

    @Override
    public TChannel findById(Long id) {
        return tChannelMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<TChannel> findALL() {
        return tChannelMapper.selectByExample(null);
    }

    @Override
    public DataGridResult findByPage(QueryDTO queryDTO) {
        PageHelper.offsetPage(queryDTO.getOffset(), queryDTO.getLimit());
        TChannelExample example = new TChannelExample();
        String sort = queryDTO.getSort();
        if (!StringUtils.isEmpty(sort)) {
            example.setOrderByClause("id");
        }
        List<TChannel> tChannels = tChannelMapper.selectByExample(example);
        PageInfo<TChannel> info = new PageInfo<>(tChannels);
        long total = info.getTotal();
        DataGridResult result = new DataGridResult(total, tChannels);
        return result;
    }

    @Override
    public void sync() {
        List<TChannel> list = this.findALL();
        for (TChannel tChannel : list) {
            cacheService.hMSet(CacheConstants.CACHE_PREFIX_SMS_CHANNEL + tChannel.getChanneltype() + ":" + tChannel.getId(), JsonUtils.object2Map(tChannel));
        }
    }

}
