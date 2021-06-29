package org.hiden.crowd.service.api;

import org.hiden.crowd.entity.po.MemberPO;

public interface MemberService {

    MemberPO getMemberPOByLoginAcct(String loginacct);

    void saveMember(MemberPO memberPO);

    Boolean judgeLoginacctExist(String loginacct);
}
