package org.hiden.crowd.service.api;

import com.github.pagehelper.PageInfo;
import org.hiden.crowd.entity.Role;

public interface RoleService {
    PageInfo<Role>  getPageInfo(Integer pageNum, Integer pageSize, String keyword);

    void saveRole(Role role);
}
