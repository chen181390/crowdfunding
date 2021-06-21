package org.hiden.crowd.mvc.handler;

import com.google.gson.Gson;
import jdk.management.resource.internal.UnassignedContext;
import org.hiden.crowd.entity.Auth;
import org.hiden.crowd.entity.Role;
import org.hiden.crowd.service.api.AdminService;
import org.hiden.crowd.service.api.AuthService;
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
    @Autowired
    private AuthService authService;

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
    public String saveAdminRoleRelationship(@RequestParam("adminId") Integer adminId,
                                            @RequestParam("pageNum") Integer pageNum,
                                            @RequestParam("keyword") String keyword,
                                            @RequestParam(value="assignedRoleList",required = false) List<Integer> assignedRoleList) {
        adminService.saveAdminRoleRelationShip(adminId, assignedRoleList);
        return "redirect:/assign/to/assign/role/page.html?adminId="+adminId+"&pageNum=" + pageNum + "&keyword=" + keyword;
    }

    @ResponseBody
    @RequestMapping("/get/all/auth.json")
    public ResultEntity<List<Auth>> getAllAuth() {
        List<Auth> authList = authService.getAllAuth();
        return ResultEntity.successWithData(authList);
    }

    @ResponseBody
    @RequestMapping("/get/assigned/auth/id/by/role/id..json")
    public ResultEntity<List<Integer>> getAssignedAuthIdByRoleId(Integer roleId) {
        List<Integer> list = authService.getAssignedAuthIdByRoleId(roleId);
        return ResultEntity.successWithData(list);
    }

    @ResponseBody
    @RequestMapping("/do/auth/assign.html")
    public ResultEntity<String> saveRoleAuthRelationship(@RequestParam("authIdArray[]") List<Integer> authIdArray, Integer roleId) {
        authService.saveRoleAuthRelationship(roleId, authIdArray);
        return ResultEntity.successWithoutData();
    }
}
