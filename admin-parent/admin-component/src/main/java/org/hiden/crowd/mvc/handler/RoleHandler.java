package org.hiden.crowd.mvc.handler;

import com.github.pagehelper.PageInfo;
import org.hiden.crowd.entity.Role;
import org.hiden.crowd.service.api.RoleService;
import org.hiden.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/role")
public class RoleHandler {
    @Autowired
    private RoleService roleSercice;

    @ResponseBody
    @RequestMapping("/get/page/info.json")
    public ResultEntity<PageInfo<Role>> getPageInfo(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
            @RequestParam(value = "keyword", defaultValue = "") String keyword
    ) {
        PageInfo<Role> pageInfo = roleSercice.getPageInfo(pageNum, pageSize, keyword);
        return ResultEntity.successWithData(pageInfo);
    }

    @ResponseBody
    @RequestMapping("/save.json")
    public ResultEntity<String> saveRole(Role role) {
        roleSercice.saveRole(role);
        return ResultEntity.successWithoutData();
    }

}
