package com.joyson.visualization.grade.service.impl;

import com.joyson.visualization.grade.dao.GradeMonthMapper;
import com.joyson.visualization.grade.service.GradeMonthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by L on 2018/7/26.
 */
@Service
public class GradeMonthServiceImpl implements GradeMonthService {

    @Autowired
    private GradeMonthMapper gradeMonthMapper;

    @Override
    public List<Map<String, Object>> queryGradeMonthPageList(Integer userId, Map<String, Object> map) {
        return this.gradeMonthMapper.selectGradeMonthPageList(map);
    }

    @Override
    public Integer queryGradeMonthPageCount(Integer userId, Map<String, Object> map) {
        return this.gradeMonthMapper.selectGradeMonthPageCount(map);
    }
}
