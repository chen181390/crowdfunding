package org.hiden.crowd.service.api;

import com.github.pagehelper.PageInfo;
import org.hiden.crowd.entity.Admin;

import java.util.List;

public interface AdminService {
    List<Admin> getAll();

    Admin getAdminByLoginAcct(String loginAcct, String userPswd);

    PageInfo<Admin> getPageInfo(String keyword, Integer pageNum, Integer pageSize);

    void add(Admin admin);

    long getAllCount();

    void remove(Integer adminId);

    Admin getAdminById(Integer adminId);

    void update(Admin admin);
}
