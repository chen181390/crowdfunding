package org.hiden.crowd.handler;

import org.hiden.crowd.api.MySQLRemoteService;
import org.hiden.crowd.api.RedisRemoteService;
import org.hiden.crowd.config.ShortMessageProperties;
import org.hiden.crowd.constant.CrowdConstant;
import org.hiden.crowd.entity.po.MemberPO;
import org.hiden.crowd.entity.vo.MemberVO;
import org.hiden.crowd.util.CrowdUtil;
import org.hiden.crowd.util.ResultEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Controller
public class MemberHandler {
    @Autowired
    private ShortMessageProperties properties;

    @Autowired
    private RedisRemoteService redisRemoteService;

    @Autowired
    private MySQLRemoteService mySQLRemoteService;

    @ResponseBody
    @RequestMapping("/auth/member/send/short/message.json")
    public ResultEntity<String> sendMessage(@RequestParam("phoneNum") String phoneNum) {
        ResultEntity<String> sendMessageResultEntity = CrowdUtil.sendCodeByShortMessageTest(properties.getHost(), phoneNum, properties.getAppcode(), properties.getTemplateId());
        if (ResultEntity.SUCCESS.equals(sendMessageResultEntity.getResult())) {
            String code = sendMessageResultEntity.getData();
            String key = CrowdConstant.REDIS_CODE_PREFIX + phoneNum;
            ResultEntity<String> saveCodeResultEntity = redisRemoteService.setRedisKeyValueRemoteWithTimeout(key, code, 15, TimeUnit.MINUTES);
            return saveCodeResultEntity;
        }
        return sendMessageResultEntity;
    }

    @RequestMapping("/auth/do/member/register")
    public String register(MemberVO memberVO, ModelMap modelMap) {
        try {
            if (!CrowdUtil.checkObjPropsNotNull(memberVO)) {
                modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_FORM_INVALID);
                return "member-reg";
            }
            String redisCodeId = CrowdConstant.REDIS_CODE_PREFIX + memberVO.getPhoneNum();
            ResultEntity<String> redisResult = redisRemoteService.getRedisStringValueByKeyRemote(redisCodeId);
            if (!ResultEntity.SUCCESS.equals(redisResult.getResult())) {
                modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, redisResult.getMessage());
                return "member-reg";
            }
            String redisCode = redisResult.getData();
            if (redisCode == null) {
                modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_CODE_NOT_EXISTS);
                return "member-reg";
            }
            if (!Objects.equals(redisCode, memberVO.getCode())) {
                modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_CODE_INVAILD);
                return "member-reg";
            }

            redisRemoteService.removeRedisKeyRemote(redisCodeId);

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String encodedPwd = encoder.encode(memberVO.getUserpswd());
            memberVO.setUserpswd(encodedPwd);

            MemberPO memberPO = new MemberPO();
            BeanUtils.copyProperties(memberVO, memberPO);

            ResultEntity<String> mySQLResult = mySQLRemoteService.saveMemberRemote(memberPO);
            if (!ResultEntity.SUCCESS.equals(mySQLResult.getResult())) {
                modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, redisResult.getMessage());
                return "member-reg";
            }

            return "member-login";
        } catch (Exception e) {
            e.printStackTrace();
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, e.getMessage());
            return "member-reg";
        }
    }

}
