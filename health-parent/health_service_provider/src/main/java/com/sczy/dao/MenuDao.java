package com.sczy.dao;

import com.github.pagehelper.Page;
import com.sczy.pojo.Menu;

import java.util.List;

public interface MenuDao {

    Page<Menu> findAll(String queryString);

    void menuAdd(Menu menu);

    Menu menuUpdate(Integer id);

    void menuEdit(Menu menu);

    void menuDelete(Integer id);

    List<Menu> findAllMenu();

    Integer menuDeletRole(Integer id);

    List<Menu> findByRoleId(int id);
}
