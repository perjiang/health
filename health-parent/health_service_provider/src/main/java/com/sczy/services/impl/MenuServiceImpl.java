package com.sczy.services.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import com.sczy.dao.MenuDao;
import com.sczy.entity.PageResult;
import com.sczy.entity.QueryPageBean;
import com.sczy.pojo.Menu;
import com.sczy.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service(interfaceClass = MenuService.class)
@Transactional
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuDao menuDao;

    @Override
    public PageResult findAll(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        Page<Menu> page = menuDao.findAll(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public void menuAdd(Menu menu) {
        menuDao.menuAdd(menu);
    }

    /**
     * 回显
     *
     * @param id
     * @return
     */
    @Override
    public Menu menuUpdate(Integer id) {
        return menuDao.menuUpdate(id);
    }

    /**
     * 更新
     *
     * @param menu
     */
    @Override
    public void menuEdit(Menu menu) {
        System.out.println(menu);
        menuDao.menuEdit(menu);
    }

    @Override
    public void menuDelete(Integer id) throws Exception {
        //判断菜单与角色是否有关联
        Integer flag = menuDao.menuDeletRole(id);
        if (flag > 0) {
            throw new Exception("要删除的数据被使用,无法删除");
        }
        menuDao.menuDelete(id);
    }

    public List<Menu> findByRoleId(int id) {
        return menuDao.findByRoleId(id);
    }
}
