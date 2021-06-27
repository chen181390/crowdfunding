package org.hiden.crowd.mvc.config;

import org.hiden.crowd.entity.Admin;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public class SecurityAdmin extends User {
    private static final long serialVersionUID = 6488448841937460821L;
    private Admin admin;

    public SecurityAdmin(Admin admin, List<GrantedAuthority> authorities) {
        super(admin.getLoginAcct(), admin.getUserPswd(), authorities);
        this.admin = admin;
        this.admin.setUserPswd(null);
    }

    public Admin getAdmin() {
        return admin;
    }
}
