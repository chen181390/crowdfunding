package org.hiden.crowd.mvc.handler;

import com.github.pagehelper.PageInfo;
import org.hiden.crowd.constant.CrowdConstant;
import org.hiden.crowd.entity.Admin;
import org.hiden.crowd.service.api.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminHandler {
    @Autowired
    private AdminService adminService;

    @RequestMapping("/do/login.html")
    public String doLogin(@RequestParam("loginAcct") String loginAcct, @RequestParam("userPswd") String userPswd, HttpSession session) {
        Admin admin = adminService.getAdminByLoginAcct(loginAcct, userPswd);

        session.setAttribute(CrowdConstant.ATTR_NAME_LOGIN_ADMIN, admin);
        return "redirect:/admin/to/main/page.html";
    }

    @RequestMapping("/do/logout.html")
    public String doLogout(HttpSession session) {
        session.invalidate();
        return "redirect:/admin/to/login/page.html";
    }

    @PreAuthorize("hasRole('经理') or hasAuthority('user:get')")
    @RequestMapping("/get/page.html")
    public String getPageInfo(@RequestParam(value = "keyword", defaultValue = "") String keyword, @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                              @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize, ModelMap modelMap) {
        PageInfo<Admin> pageInfo = adminService.getPageInfo(keyword, pageNum, pageSize);
        modelMap.addAttribute(CrowdConstant.ATTR_NAME_PAGE_INFO, pageInfo);
        return "admin-page";
    }

    @RequestMapping("/remove/{adminId}/{pageNum}/{keyword}.html")
    public String remove(@PathVariable("adminId") Integer adminId, @PathVariable("pageNum") Integer pageNum, @PathVariable(value = "keyword") String keyword) {
        adminService.remove(adminId);
        return "redirect:/admin/get/page.html?pageNum=" + pageNum + "&keyword=" + keyword;
    }

    @PreAuthorize("hasAnyAuthority('user:save')")
    @RequestMapping("/save.html")
    public String save(Admin admin) {
        adminService.add(admin);
        return "redirect:/admin/get/page.html?pageNum=" + Integer.MAX_VALUE;
    }

    @RequestMapping("/to/edit/page.html")
    public String toEditPage(
            @RequestParam("adminId") Integer adminId,
            ModelMap modelMap
    ) {
        Admin admin = adminService.getAdminById(adminId);
        modelMap.addAttribute("admin", admin);
        return "admin-edit";
    }

    @RequestMapping("/update.html")
    public String update(Admin admin, @RequestParam("pageNum") Integer pageNum, @RequestParam("keyword") String keyword) {
        adminService.update(admin);
        return "redirect:/admin/get/page.html?pageNum=" + pageNum + "&keyword=" + keyword;
    }
}
