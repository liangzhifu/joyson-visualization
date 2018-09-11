package com.joyson.visualization.grade.service;

import com.joyson.visualization.grade.model.GradeScore;

import java.util.List;
import java.util.Map;

/**
 * Created by L on 2018/7/26.
 */
public interface GradeScoreService {

    /**
     * 查询需要打分的员工
     * @param gradeLineId 评价线ID
     * @return 返回结果
     */
    List<Map<String, Object>> queryGradeScoreList(Integer gradeLineId);

    /**
     * 更新评价
     * @param gradeScoreList 评价列表
     */
    void updateGradeScoreList(List<GradeScore> gradeScoreList);

}
