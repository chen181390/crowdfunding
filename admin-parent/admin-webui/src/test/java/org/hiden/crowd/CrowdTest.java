package org.hiden.crowd;

import org.hiden.crowd.entity.Admin;
import org.hiden.crowd.mapper.AdminMapper;
import org.hiden.crowd.service.api.AdminService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-persist-mybatis.xml", "classpath:spring-persist-tx.xml"})
public class CrowdTest {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private AdminService adminService;

    @Test
    public void testTx() {
        Admin admin = new Admin(null,"ajie", "123456", "阿杰", "ajie@qq.com", new Date());
        adminService.add(admin);
    }

    @Test
    public void testLog() {
        Logger log = LoggerFactory.getLogger(CrowdTest.class);
        log.debug("我是你哥哥");
        log.info("啊？啥子哦");
        log.warn("辣你去找物管啊");
    }

    @Test
    public void testInsertAdmin() {
        Admin admin = new Admin(null,"awei", "123123", "阿伟", "awei@qq.com", new Date());
        int insert = adminMapper.insert(admin);
        System.out.println("受影响行数：" + insert);
    }

    @Test
    public void testConn() throws SQLException {
        System.out.println(dataSource.getConnection());
    }
}
