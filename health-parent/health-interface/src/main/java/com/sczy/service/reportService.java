package com.sczy.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface reportService {
    Map<String, Object> getBusinessReportData() throws Exception;
    List<Map<String,Object>> reportAgeAndSex();


    List<Map<String,Object>> AgeBand() throws ParseException;
}
