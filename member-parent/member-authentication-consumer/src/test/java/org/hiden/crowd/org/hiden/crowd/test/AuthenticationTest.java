package org.hiden.crowd.org.hiden.crowd.test;

import org.hiden.crowd.entity.vo.MemberVO;
import org.hiden.crowd.util.CrowdUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthenticationTest {
    Logger logger = LoggerFactory.getLogger(AuthenticationTest.class);
    @Test
    public void test1() throws IllegalAccessException {
        MemberVO memberVO = new MemberVO(null, "123456", "awei", "awei@qq.com", "123212", "dsadas");
        boolean result = CrowdUtil.checkObjPropsNotNull(memberVO);
        logger.info("检查结果：" + result);
    }
}
