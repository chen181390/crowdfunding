package org.hiden.crowd.mvc.config;

import org.hiden.crowd.entity.Admin;
import org.hiden.crowd.entity.Role;
import org.hiden.crowd.service.api.AdminService;
import org.hiden.crowd.service.api.AuthService;
import org.hiden.crowd.service.api.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.jaas.AuthorityGranter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CrowdUserDetailsService implements UserDetailsService {
    @Autowired
    private AdminService adminService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private AuthService authService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Admin admin = adminService.getAdminByLoginAcct(s);
        Integer adminId = admin.getId();
        List<Role> assignedRoleList = roleService.getAssignedRole(adminId);
        List<String> authNameList = authService.getAssignedAuthNameByAdminId(adminId);
        List<GrantedAuthority> authorities = assignedRoleList.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName())).collect(Collectors.toList());
        for (String authName:authNameList) {
            GrantedAuthority authority = new SimpleGrantedAuthority(authName);
            authorities.add(authority);
        }
        SecurityAdmin securityAdmin = new SecurityAdmin(admin, authorities);
        return securityAdmin;
    }
}
