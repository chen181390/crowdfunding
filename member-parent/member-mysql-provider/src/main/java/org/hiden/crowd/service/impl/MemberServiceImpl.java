package org.hiden.crowd.service.impl;

import org.hiden.crowd.entity.po.MemberPO;
import org.hiden.crowd.entity.po.MemberPOExample;
import org.hiden.crowd.mapper.MemberPOMapper;
import org.hiden.crowd.service.api.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberPOMapper memberPOMapper;

    @Override
    public MemberPO getMemberPOByLoginAcct(String loginacct) {
        MemberPOExample memberPOExample = new MemberPOExample();
        MemberPOExample.Criteria criteria = memberPOExample.createCriteria();
        criteria.andLoginacctEqualTo(loginacct);
        return memberPOMapper.selectByExample(memberPOExample).get(0);
    }
}
