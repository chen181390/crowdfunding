package org.hiden.crowd.mvc.handler;

import org.hiden.crowd.entity.Menu;
import org.hiden.crowd.service.api.MenuService;
import org.hiden.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/menu")
public class MenuHandler {
    @Autowired
    private MenuService menuService;

    @RequestMapping("/get/whole/tree.json")
    public ResultEntity<Menu> getWholeTreeNew() {
        List<Menu> menuList = menuService.getAll();
        Menu root = null;
        Map<Integer, Menu> menuMap = new HashMap<>();
        for (Menu menu : menuList) {
            Integer id = menu.getId();
            menuMap.put(id, menu);
        }

        for (Menu menu : menuList) {
            Integer pid = menu.getPid();
            if (pid == null) {
                root = menu;
                continue;
            }
            Menu father = menuMap.get(pid);
            father.getChildren().add(menu);
        }
        return ResultEntity.successWithData(root);
    }

    @RequestMapping("/save.json")
    public ResultEntity<String> saveMenu(Menu menu) {
        menuService.saveMenu(menu);
        return ResultEntity.successWithoutData();
    }

    @RequestMapping("/update.json")
    public ResultEntity<String> updateMenu(Menu menu) {
        menuService.updateMenu(menu);
        return ResultEntity.successWithoutData();
    }

    @RequestMapping("/remove.json")
    public ResultEntity<String> removeMenu(Integer id) {
        menuService.removeMenu(id);
        return ResultEntity.successWithoutData();
    }
}
