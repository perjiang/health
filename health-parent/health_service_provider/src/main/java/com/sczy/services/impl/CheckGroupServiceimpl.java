package com.sczy.services.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sczy.dao.CheckGroupDao;
import com.sczy.entity.PageResult;
import com.sczy.entity.QueryPageBean;
import com.sczy.pojo.CheckGroup;
import com.sczy.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = CheckGroupService.class)
@Transactional
public class CheckGroupServiceimpl implements CheckGroupService {
    @Autowired
    private CheckGroupDao checkGroupDao;

    @Override
    public void add(CheckGroup checkGroup, Integer[] checkitemIds) {
        //添加检查组还要和检查项进行关联
        checkGroupDao.add(checkGroup);
        //设置关联关系
        this.setCheckGroupAndCheckItem(checkGroup.getId(),checkitemIds);
    }

    @Override
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        long total = 0;
        List<CheckGroup> rows = null;
        if(queryString==null || queryString.length()==0){
            PageHelper.startPage(currentPage,pageSize);
            Page<CheckGroup> page = checkGroupDao.selectByCondition(queryString);//对接收的到的Page对象里的数据进行获取，封装成为我们自己需要的数据
            total = page.getTotal();
            rows = page.getResult();
        }else if(queryString!=null || queryString.length()>0 ){
            PageHelper.startPage(1,pageSize);
            Page<CheckGroup> page = checkGroupDao.selectByCondition(queryString);//对接收的到的Page对象里的数据进行获取，封装成为我们自己需要的数据
            total = page.getTotal();
            rows = page.getResult();
        }
        return new PageResult(total,rows);
    }

    @Override
    public void delete(Integer id) {
        checkGroupDao.deleteAssocication(id);
        checkGroupDao.deleteCheckGroupById(id);
    }

    @Override
    public CheckGroup findById(Integer id) {
        return checkGroupDao.findById(id);
    }

    @Override
    public List<Integer> findCheckItemIdsByCheckGroupId(Integer id) {
        return checkGroupDao.findCheckItemIdsByCheckGroupId(id);
    }

    @Override
    public void edit(CheckGroup checkGroup, Integer[] checkitemIds) {
        //修改检查组基本信息，操作检查组t_checkgroup表
        checkGroupDao.edit(checkGroup);
        //清空和改检查组关联的检查项
        checkGroupDao.deleteAssocication(checkGroup.getId());
        //重新建立关系
        this.setCheckGroupAndCheckItem(checkGroup.getId(),checkitemIds);

    }

    @Override
    public List<CheckGroup> findAll() {
        List<CheckGroup> checkGroups = checkGroupDao.findAll();
        return checkGroups;
    }

    //建立检查组和检查项多对多关系
    public void setCheckGroupAndCheckItem(Integer checkGroupId,Integer[] checkitemIds){
        if(checkitemIds != null && checkitemIds.length > 0){
            for (Integer checkitemId : checkitemIds) {
                Map<String,Integer> map = new HashMap<>();
                map.put("checkgroupId",checkGroupId);
                map.put("checkitemId",checkitemId);
                checkGroupDao.setCheckGroupAndCheckItem(map);
            }
        }
    }
}
