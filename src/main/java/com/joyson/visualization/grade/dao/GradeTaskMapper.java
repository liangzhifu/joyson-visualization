package com.joyson.visualization.grade.dao;

import com.joyson.visualization.common.core.Mapper;
import com.joyson.visualization.grade.model.GradeTask;

import java.util.List;
import java.util.Map;

public interface GradeTaskMapper extends Mapper<GradeTask> {

    /**
     * 查询代办任务列表
     * @param map 查询条件
     * @return 返回结果
     */
    List<Map<String, Object>> selectGradeTaskList(Map<String, Object> map);

    Integer selectGradeTaskCount(Map<String, Object> map);

    /**
     * 更新代办任务
     * @param gradeTask 代办任务
     */
    void updateGradeTask(GradeTask gradeTask);

}