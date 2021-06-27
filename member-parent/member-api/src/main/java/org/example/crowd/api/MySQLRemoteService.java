package org.example.crowd.api;

import org.hiden.crowd.entity.po.MemberPO;
import org.hiden.crowd.util.ResultEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("hiden-crowd-mysql")
public interface MySQLRemoteService {

    @RequestMapping("get/memberpo/by/Login/acct/remote")
    ResultEntity<MemberPO> getMemberPOByLoginAcctRemote(@RequestParam("loginacct") String loginacct);

}
