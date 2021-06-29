package org.hiden.crowd.api;

import org.hiden.crowd.entity.po.MemberPO;
import org.hiden.crowd.util.ResultEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("hiden-crowd-mysql")
public interface MySQLRemoteService {

    @RequestMapping("get/memberpo/by/loginacct/remote")
    ResultEntity<MemberPO> getMemberPOByLoginacctRemote(@RequestParam("loginacct") String loginacct);

    @RequestMapping("/save/member/remote")
    ResultEntity<String> saveMemberRemote(@RequestBody MemberPO memberPO);

    @RequestMapping("/judge/loginacct/exist/remote")
    ResultEntity<String> judgeLoginacctExistRemote(@RequestParam("loginacct") String loginacct);

}
