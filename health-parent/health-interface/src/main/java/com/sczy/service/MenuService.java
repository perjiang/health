package com.sczy.service;

import com.sczy.entity.PageResult;
import com.sczy.entity.QueryPageBean;
import com.sczy.pojo.Menu;

import java.util.List;

public interface MenuService {

    PageResult findAll(QueryPageBean queryPageBean);

    void menuAdd(Menu menu);

    Menu menuUpdate(Integer id);

    void menuEdit(Menu menu);

    void menuDelete(Integer id) throws Exception;

    List<Menu> findByRoleId(int id);
}
