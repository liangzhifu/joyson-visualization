package com.joyson.visualization.grade.dao;

import com.joyson.visualization.common.core.Mapper;
import com.joyson.visualization.grade.model.GradeMonth;

import java.util.List;
import java.util.Map;

public interface GradeMonthMapper extends Mapper<GradeMonth> {

    List<Map<String, Object>> selectGradeMonthPageList(Map<String, Object> map);

    Integer selectGradeMonthPageCount(Map<String, Object> map);

    void updateGradeMonthUserId(GradeMonth gradeMonth);

}