package com.sczy.dao;

import com.github.pagehelper.Page;
import com.sczy.entity.PageResult;
import com.sczy.entity.QueryPageBean;
import com.sczy.pojo.CheckItem;
import org.apache.ibatis.annotations.Insert;

import java.util.List;

public interface CheckitemDao {
    public void add(CheckItem checkItem);
    public Page<CheckItem> selectByCondition(String queryString);

    public long findCountByCheckItemId(Integer id);
    void deleteById(Integer id);

    CheckItem findById(Integer id);

    void edit(CheckItem checkItem);

    List<CheckItem> findAll();
}
