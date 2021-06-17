package org.hiden.crowd.service.impl;

import org.hiden.crowd.entity.Menu;
import org.hiden.crowd.entity.MenuExample;
import org.hiden.crowd.mapper.MenuMapper;
import org.hiden.crowd.service.api.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuMapper menuMapper;


    @Override
    public List<Menu> getAll() {
        return menuMapper.selectByExample(null);
    }
}
