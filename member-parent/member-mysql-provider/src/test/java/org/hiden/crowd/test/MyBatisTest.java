package org.hiden.crowd.test;

import org.hiden.crowd.entity.po.MemberPO;
import org.hiden.crowd.mapper.MemberPOMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MyBatisTest {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private MemberPOMapper memberPOMapper;

    private final Logger logger = LoggerFactory.getLogger(MyBatisTest.class);

    @Test
    public void testConnection() throws SQLException {
        Connection connection = dataSource.getConnection();
        logger.info(connection.toString());
    }

    @Test
    public void testMapper() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        MemberPO memberPO = new MemberPO(null, "ajie", encoder.encode("123456"), "jack", "jack@qq.com", 1, 1, "杰克", "123123", 2);
        memberPOMapper.insert(memberPO);
    }
}
