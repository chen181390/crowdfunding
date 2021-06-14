package org.hiden.crowd.mvc.handler;

import org.hiden.crowd.entity.Admin;
import org.hiden.crowd.service.api.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

@Controller
public class TestHandler {
    @Autowired
    private AdminService adminService;
    private Logger logger = LoggerFactory.getLogger(TestHandler.class);

    @ResponseBody
    @RequestMapping("/send/array2.html")
    public Admin testReceiveArrayTwo(@RequestBody List<Integer> array) {
        return new Admin(1,"root","123", "阿伟", "awei@qq.com", new Date());
    }

    @ResponseBody
    @RequestMapping("/send/array.html")
    public String testReceiveArrayOne(@RequestParam("array[]") List<Integer> array) {
        array.forEach(System.out::println);
        return "success";
    }

    @RequestMapping("/test/ssm.html")
    public String testSsm(ModelMap modelMap) {
        List<Admin> adminList = adminService.getAll();
        modelMap.addAttribute("adminList", adminList);
        int i = 10 / 0;
        return "target";
    }

    @ResponseBody
    @RequestMapping("/insert/example.html")
    public long insertExample() {
        int insertNum = 100;
        long affectNum = 0;
        long baseIndex = adminService.getAllCount();
        for(int i = 0; i < insertNum; i++) {
            String name = "test" + ++baseIndex;
            Admin admin = new Admin(null, name, "123456", name, name + "@126.com", new Date());
            adminService.add(admin);
            affectNum++;
        }
        return affectNum;
    }
}
