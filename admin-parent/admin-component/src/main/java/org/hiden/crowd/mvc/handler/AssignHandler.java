package org.hiden.crowd.mvc.handler;

import com.google.gson.Gson;
import jdk.management.resource.internal.UnassignedContext;
import org.hiden.crowd.entity.Role;
import org.hiden.crowd.service.api.AdminService;
import org.hiden.crowd.service.api.RoleService;
import org.hiden.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/assign")
public class AssignHandler {
    @Autowired
    private AdminService adminService;
    @Autowired
    private RoleService roleService;

    @ResponseBody
    @RequestMapping("/get/whole/assign.json")
    public ResultEntity<String> getRoleList(Integer adminId) {
        List<Role> assignedRoleList = roleService.getAssignedRole(adminId);
        List<Role> unAssignedRoleList = roleService.getUnAssignedRole(adminId);
        Gson gson = new Gson();
        String assignedRoleListStr = gson.toJson(assignedRoleList);
        String unAssignedRoleListStr = gson.toJson(unAssignedRoleList);
        String jsonResult = "{\"assignedRoleList\":" + assignedRoleListStr + ",\"unAssignedRoleList\":" + unAssignedRoleListStr + "}";;
        return ResultEntity.successWithData(jsonResult);
    }

    @RequestMapping("/do/role/assign.html")
    public String saveAdminRoleRelationShip(@RequestParam("adminId") Integer adminId,
                                            @RequestParam("pageNum") Integer pageNum,
                                            @RequestParam("keyword") String keyword,
                                            @RequestParam(value="assignedRoleList",required = false) List<Integer> assignedRoleList) {
        adminService.saveAdminRoleRelationShip(adminId, assignedRoleList);
        return "redirect:/assign/to/assign/role/page.html?adminId="+adminId+"&pageNum=" + pageNum + "&keyword=" + keyword;
    }
}
