package com.sczy.service;

import com.sczy.entity.Result;

import java.util.Map;

public interface OrderService {

    public Result order(Map map) throws Exception;

    Map findById(Integer id) throws Exception;
}
