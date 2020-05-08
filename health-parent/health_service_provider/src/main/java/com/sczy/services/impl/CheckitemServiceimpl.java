package com.sczy.services.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sczy.dao.CheckitemDao;
import com.sczy.entity.PageResult;
import com.sczy.entity.QueryPageBean;
import com.sczy.pojo.CheckItem;
import com.sczy.service.CheckitemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = CheckitemService.class)
@Transactional
public class CheckitemServiceimpl implements CheckitemService {
    @Autowired
    private CheckitemDao checkitemDao;
    @Override
    public void add(CheckItem checkItem) {
        checkitemDao.add(checkItem);
    }

    @Override
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        long total = 0;
        List<CheckItem> rows = null;

        //对数据进行判断，如果是不带条件的查询，就直接使用分页插件进行查询，如果搜素输入框有内容，我们就让其从第一页开始查询
        if(queryString==null || queryString.length()==0){
            //调用分页插件
            PageHelper.startPage(currentPage,pageSize);
            Page<CheckItem> page = checkitemDao.selectByCondition(queryString);//对接收的到的Page对象里的数据进行获取，封装成为我们自己需要的数据
           total = page.getTotal();
           rows = page.getResult();
        }else if (queryString!=null || queryString.length()>0){
            PageHelper.startPage(1,pageSize);
            Page<CheckItem> page = checkitemDao.selectByCondition(queryString);
            total = page.getTotal();
            rows = page.getResult();
        }


        return new PageResult(total,rows);
    }

    @Override
    public void deleteById(Integer id) {
        //检查该项目是否已经关联检查组，管理了检查组直接抛出异常
        long count = checkitemDao.findCountByCheckItemId(id);
        if(count>0){
            throw  new RuntimeException();
        }else{
            checkitemDao.deleteById(id);
        }

    }

    @Override
    public CheckItem findById(Integer id) {
        CheckItem checkItem = checkitemDao.findById(id);
        return checkItem;
    }

    @Override
    public void edit(CheckItem checkItem) {
        checkitemDao.edit(checkItem);
    }

    @Override
    public List<CheckItem> findAll() {
        return checkitemDao.findAll();
    }
}
