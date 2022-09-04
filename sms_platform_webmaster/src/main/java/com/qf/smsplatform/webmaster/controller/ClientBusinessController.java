package com.qf.smsplatform.webmaster.controller;

import com.qf.smsplatform.webmaster.dto.DataGridResult;
import com.qf.smsplatform.webmaster.dto.QueryDTO;
import com.qf.smsplatform.webmaster.pojo.TClientBusiness;
import com.qf.smsplatform.webmaster.service.ClientBusinessService;
import com.qf.smsplatform.webmaster.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ClientBusinessController {

    @Autowired
    private ClientBusinessService clientBusinessService;

    @ResponseBody
    @RequestMapping("/sys/clientbusiness/list")
    public DataGridResult findClientBusiness(QueryDTO queryDTO) {
        return clientBusinessService.findByPage(queryDTO);
    }

    @ResponseBody
    @RequestMapping("/sys/clientbusiness/del")
    public R delClientBusiness(@RequestBody List<Long> ids) {
        for (Long id : ids) {
            clientBusinessService.delClientBusiness(id);
        }
        return R.ok();
    }


    @ResponseBody
    @RequestMapping("/sys/clientbusiness/info/{id}")
    public R findById(@PathVariable("id") Long id) {
        TClientBusiness tClientBusiness = clientBusinessService.findById(id);
        return R.ok().put("clientbusiness", tClientBusiness);
    }

    @ResponseBody
    @RequestMapping("/sys/clientbusiness/save")
    public R addClientBusiness(@RequestBody TClientBusiness tClientBusiness) {
        int i = clientBusinessService.addClientBusiness(tClientBusiness);
        return i > 0 ? R.ok() : R.error("添加失败");
    }

    @ResponseBody
    @RequestMapping(value = "/sys/clientbusiness/update")
    public R updateClientBusiness(@RequestBody TClientBusiness tClientBusiness) {
        int i = clientBusinessService.updateClientBusiness(tClientBusiness);
        return i > 0 ? R.ok() : R.error("修改失败");
    }

    //同步所有的客户接入管理信息
    @ResponseBody
    @RequestMapping("/sys/clientbusiness/sync")
    public R sync(){
        boolean b = clientBusinessService.sync();
        return b ? R.ok() : R.error("同步失败");
    }

}
