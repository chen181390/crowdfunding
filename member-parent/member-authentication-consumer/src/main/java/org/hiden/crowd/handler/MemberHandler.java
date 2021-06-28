package org.hiden.crowd.handler;

import org.hiden.crowd.config.ShortMessageProperties;
import org.hiden.crowd.util.CrowdUtil;
import org.hiden.crowd.util.ResultEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MemberHandler {
    @Autowired
    private ShortMessageProperties properties;

    private Logger logger = LoggerFactory.getLogger(MemberHandler.class);

    @ResponseBody
    @RequestMapping("/auth/member/send/short/message.json")
    public ResultEntity<String> sendMessage(@RequestParam("phoneNum") String phoneNum) {
        ResultEntity<String> resultEntity = CrowdUtil.sendCodeByShortMessage(phoneNum, properties.getAppcode(), properties.getTemplateId());
        return resultEntity;
    }

}
