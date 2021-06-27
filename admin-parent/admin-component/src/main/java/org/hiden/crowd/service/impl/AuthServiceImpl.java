package org.hiden.crowd.service.impl;

import org.hiden.crowd.entity.Auth;
import org.hiden.crowd.mapper.AuthMapper;
import org.hiden.crowd.service.api.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private AuthMapper authMapper;

    @Override
    public List<Auth> getAllAuth() {
        return authMapper.selectByExample(null);
    }

    @Override
    public List<Integer> getAssignedAuthIdByRoleId(Integer roleId) {
        return authMapper.selectAssignedAuthIdByRoleId(roleId);
    }

    @Override
    public void saveRoleAuthRelationship(Integer roleId, List<Integer> authIdArray) {
        authMapper.deleteRoleAuthRelationship(roleId);
        if (authIdArray == null || authIdArray.size() == 0) return;
        authMapper.insertRoleAuthRelationship(roleId, authIdArray);
    }

    @Override
    public List<String> getAssignedAuthNameByAdminId(Integer adminId) {

        return authMapper.selectAssignedAuthNameByAdminId(adminId);
    }
}
