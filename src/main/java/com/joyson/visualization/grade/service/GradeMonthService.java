package com.joyson.visualization.grade.service;

import java.util.List;
import java.util.Map;

/**
 * Created by L on 2018/7/26.
 */
public interface GradeMonthService {

    List<Map<String, Object>> queryGradeMonthPageList(Integer userId, Map<String, Object> map);

    Integer queryGradeMonthPageCount(Integer userId, Map<String, Object> map);

}
