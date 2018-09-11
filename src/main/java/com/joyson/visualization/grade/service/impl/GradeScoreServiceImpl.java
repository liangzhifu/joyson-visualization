package com.joyson.visualization.grade.service.impl;

import com.joyson.visualization.grade.dao.GradeLineMapper;
import com.joyson.visualization.grade.dao.GradeScoreMapper;
import com.joyson.visualization.grade.model.GradeLine;
import com.joyson.visualization.grade.model.GradeScore;
import com.joyson.visualization.grade.service.GradeScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by L on 2018/7/26.
 */
@Service
public class GradeScoreServiceImpl implements GradeScoreService {

    @Autowired
    private GradeLineMapper gradeLineMapper;

    @Autowired
    private GradeScoreMapper gradeScoreMapper;

    @Override
    public List<Map<String, Object>> queryGradeScoreList(Integer gradeLineId) {
        return this.gradeScoreMapper.selectGradeScoreList(gradeLineId);
    }

    @Override
    public void updateGradeScoreList(List<GradeScore> gradeScoreList) {
        Long gradeLineId = null;
        for (GradeScore gradeScore : gradeScoreList) {
            gradeLineId = gradeScore.getGradeLineId();
            this.gradeScoreMapper.updateGradeScore(gradeScore);
        }
        GradeLine gradeLine = new GradeLine();
        gradeLine.setId(gradeLineId);
        gradeLine.setGradeStatus(2);
        this.gradeLineMapper.updateGradeStatus(gradeLine);
    }
}
