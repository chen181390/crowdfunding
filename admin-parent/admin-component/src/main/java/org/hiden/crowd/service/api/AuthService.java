package org.hiden.crowd.service.api;

import org.hiden.crowd.entity.Auth;

import java.util.List;

public interface AuthService {
    List<Auth> getAllAuth();

    List<Integer> getAssignedAuthIdByRoleId(Integer roleId);

    void saveRoleAuthRelationship(Integer roleId, List<Integer> authIdArray);
}
