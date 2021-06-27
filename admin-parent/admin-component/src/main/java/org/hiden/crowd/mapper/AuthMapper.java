package org.hiden.crowd.mapper;

import org.hiden.crowd.entity.Auth;
import org.hiden.crowd.entity.AuthExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AuthMapper {
    long countByExample(AuthExample example);

    int deleteByExample(AuthExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Auth record);

    int insertSelective(Auth record);

    List<Auth> selectByExample(AuthExample example);

    Auth selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Auth record, @Param("example") AuthExample example);

    int updateByExample(@Param("record") Auth record, @Param("example") AuthExample example);

    int updateByPrimaryKeySelective(Auth record);

    int updateByPrimaryKey(Auth record);

    List<Integer> selectAssignedAuthIdByRoleId(Integer roleId);

    void insertRoleAuthRelationship(@Param("roleId") Integer roleId, @Param("authIdArray") List<Integer> authIdArray);

    void deleteRoleAuthRelationship(Integer roleId);

    List<String> selectAssignedAuthNameByAdminId(Integer adminId);
}