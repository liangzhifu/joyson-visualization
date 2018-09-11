package com.joyson.visualization.grade.dao;

import com.joyson.visualization.common.core.Mapper;
import com.joyson.visualization.grade.model.GradeScore;

import java.util.List;
import java.util.Map;

public interface GradeScoreMapper extends Mapper<GradeScore> {

    /**
     * 查询评价生产线
     * @param gradeLineId 生产线ID
     * @return 返回结果
     */
    List<Map<String, Object>> selectGradeScoreList(Integer gradeLineId);

    /**
     * 更新评价
     * @param gradeScore 评价
     */
    void updateGradeScore(GradeScore gradeScore);

    void deleteGradeScore();

}