package org.hiden.crowd.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.hiden.crowd.constant.CrowdConstant;
import org.hiden.crowd.entity.Admin;
import org.hiden.crowd.entity.AdminExample;
import org.hiden.crowd.exception.LoginAcctAlreadyInUseException;
import org.hiden.crowd.exception.LoginFailedException;
import org.hiden.crowd.mapper.AdminMapper;
import org.hiden.crowd.service.api.AdminService;
import org.hiden.crowd.util.CrowdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.RuntimeMBeanException;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public List<Admin> getAll() {
        return adminMapper.selectByExample(new AdminExample());
    }

    @Deprecated
    @Override
    public Admin getAdminByLoginAcct(String loginAcct, String userPswd) {
        AdminExample adminExample = new AdminExample();
        AdminExample.Criteria criteria = adminExample.createCriteria();
        criteria.andLoginAcctEqualTo(loginAcct);
        List<Admin> adminList = adminMapper.selectByExample(adminExample);
        if (adminList == null || adminList.size() == 0) {
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }
        if (adminList.size() > 1) {
            throw new RuntimeException(CrowdConstant.MESSAGE_SYSTEM_ERROR_LOGIN_NOT_UNIQUE);
        }
        Admin admin = adminList.get(0);
        if (admin == null) {
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }
        String userPswdDB = admin.getUserPswd();
        String userPswdForm = CrowdUtil.md5(userPswd);
        if (!Objects.equals(userPswdForm, userPswdDB)) {
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }
        return admin;
    }

    @Override
    public PageInfo<Admin> getPageInfo(String keyword, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Admin> list = adminMapper.selectAdminByKeyWord(keyword);
        return new PageInfo<>(list);
    }

    @Override
    public void add(Admin admin) {
        String userPwd = admin.getUserPswd();
        admin.setUserPswd(passwordEncoder.encode(userPwd));
        admin.setCreateTime(new Date());
        try {
            adminMapper.insertSelective(admin);
        } catch (DuplicateKeyException e) {
            throw new LoginAcctAlreadyInUseException(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
        }
    }

    @Override
    public long getAllCount() {
        return adminMapper.countByExample(null);
    }

    @Override
    public void remove(Integer adminId) {
        adminMapper.deleteByPrimaryKey(adminId) ;
    }

    @Override
    public Admin getAdminById(Integer adminId) {
        return adminMapper.selectByPrimaryKey(adminId);
    }

    @Override
    public void update(Admin admin) {
        try {
            adminMapper.updateByPrimaryKeySelective(admin);
        } catch (DuplicateKeyException e) {
           throw new RuntimeException(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
        }
    }

    @Override
    public void saveAdminRoleRelationShip(Integer adminId, List<Integer> assignedRoleList) {
        adminMapper.deleteAdminRoleRelationShip(adminId);
        if (assignedRoleList == null || assignedRoleList.size() == 0) return;
        adminMapper.insertAdminRoleRelationShip(adminId, assignedRoleList);
    }

    @Override
    public Admin getAdminByLoginAcct(String s) {
        AdminExample adminExample = new AdminExample();
        AdminExample.Criteria criteria = adminExample.createCriteria();
        criteria.andLoginAcctEqualTo(s);
        List<Admin> adminList = adminMapper.selectByExample(adminExample);
        return adminList.get(0);
    }
}
