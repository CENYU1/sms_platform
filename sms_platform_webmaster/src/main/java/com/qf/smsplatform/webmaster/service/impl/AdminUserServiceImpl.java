package com.qf.smsplatform.webmaster.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qf.smsplatform.webmaster.dao.TAdminUserMapper;
import com.qf.smsplatform.webmaster.dao.TRoleMapper;
import com.qf.smsplatform.webmaster.dao.TUserRoleMapper;
import com.qf.smsplatform.webmaster.dto.DataGridResult;
import com.qf.smsplatform.webmaster.dto.QueryDTO;
import com.qf.smsplatform.webmaster.pojo.TAdminUser;
import com.qf.smsplatform.webmaster.pojo.TRole;
import com.qf.smsplatform.webmaster.pojo.TRoleExample;
import com.qf.smsplatform.webmaster.pojo.TUserRoleKey;
import com.qf.smsplatform.webmaster.service.AdminUserService;
import com.qf.smsplatform.webmaster.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminUserServiceImpl implements AdminUserService {
    @Autowired
    private TAdminUserMapper tAdminUserMapper;
    @Autowired
    private TRoleMapper tRoleMapper;
    @Autowired
    private TUserRoleMapper tUserRoleMapper;

    @Override
    public List<TAdminUser> findAll() {
        return tAdminUserMapper.selectByExample(null);
    }

    @Override
    public TAdminUser findByUsername(String username) {
        return tAdminUserMapper.findByUsername(username);
    }

    @Override
    public DataGridResult findUserByPage(QueryDTO queryDTO) {
        PageHelper.offsetPage(queryDTO.getOffset(), queryDTO.getLimit());
        if (queryDTO.getSort() != null && !queryDTO.getSort().equals("")) {
            queryDTO.setSort("id");
        }
        List<TAdminUser> byPage = tAdminUserMapper.findByPage(queryDTO);
        PageInfo<TAdminUser> info = new PageInfo<>(byPage);
        long total = info.getTotal();
        DataGridResult result = new DataGridResult(total, info.getList());
        return result;
    }

    @Override
    public int addAdminUser(TAdminUser tAdminUser) {
        String password = tAdminUser.getPassword();
        String usercode = tAdminUser.getUsercode();
        String s = MD5Utils.md5(password, usercode, 1024);
        tAdminUser.setPassword(s);
        int i =  tAdminUserMapper.insertSelective(tAdminUser);
        TRoleExample example = new TRoleExample();
        TRoleExample.Criteria criteria = example.createCriteria();
        criteria.andNameEqualTo("客户");
        List<TRole> tRoles = tRoleMapper.selectByExample(example);
        if(tRoles!=null&&tRoles.size()>0){
            TRole tRole = tRoles.get(0);
            TUserRoleKey record = new TUserRoleKey();
            record.setRoleId(tRole.getId());
            record.setUserId(tAdminUser.getId());
            tUserRoleMapper.insert(record);
        }
        return i;
    }

    @Override
    public int delAdminUser(Integer id) {
        return tAdminUserMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int updateAdminUser(TAdminUser tAdminUser) {
        return tAdminUserMapper.updateByPrimaryKey(tAdminUser);
    }

    @Override
    public TAdminUser findById(Integer id) {
        return tAdminUserMapper.selectByPrimaryKey(id);

    }

}
