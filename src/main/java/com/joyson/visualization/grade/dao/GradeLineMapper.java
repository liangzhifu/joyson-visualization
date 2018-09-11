package com.joyson.visualization.grade.dao;

import com.joyson.visualization.common.core.Mapper;
import com.joyson.visualization.grade.model.GradeLine;

import java.util.List;
import java.util.Map;

public interface GradeLineMapper extends Mapper<GradeLine> {

    /**
     * 查询评价生产线列表--分页
     * @param map 查询条件
     * @return 返回结果
     */
    List<Map<String, Object>> selectGradeLinePageList(Map<String, Object> map);

    Integer selectGradeLinePageCount(Map<String, Object> map);

    /**
     * 查询待评价线体
     * @param id
     * @return
     */
    GradeLine selectGradeLineById(Integer id);

    /**
     * 更新生产线
     */
    void updateGradeStatus(GradeLine gradeLine);

    /**
     * 更新线长
     * @param gradeLine
     */
    void updateGradeUserId(GradeLine gradeLine);

    List<GradeLine> selectMonthGradeLine(Map<String, Object> map);
}