package com.joyson.visualization.grade.service.impl;

import com.joyson.visualization.grade.dao.GradeTaskMapper;
import com.joyson.visualization.grade.model.GradeTask;
import com.joyson.visualization.grade.service.GradeTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Condition;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by L on 2018/7/26.
 */
@Service
public class GradeTaskServiceImpl implements GradeTaskService {

    @Autowired
    private GradeTaskMapper gradeTaskMapper;

    @Override
    public List<Map<String, Object>> queryGradeTaskList(Map<String, Object> map) {
        return this.gradeTaskMapper.selectGradeTaskList(map);
    }

    @Override
    public Integer queryGradeTaskCount(Map<String, Object> map) {
        return this.gradeTaskMapper.selectGradeTaskCount(map);
    }

    @Override
    public void confirmUser(String confirmUserId, Integer userId) {
        Condition condition = new Condition(GradeTask.class);
        condition.createCriteria().andEqualTo("userId", userId)
                .andEqualTo("taskSeq", 1)
                .andEqualTo("taskStatus", 1);
        List<GradeTask> gradeTaskList = this.gradeTaskMapper.selectByCondition(condition);
        if (gradeTaskList != null && gradeTaskList.size() > 0) {
            List<Long> gradeMonthIdList = new LinkedList<>();
            for (GradeTask gradeTask: gradeTaskList) {
                gradeMonthIdList.add(gradeTask.getGradeMonthId());
                gradeTask.setTaskStatus(2);
                this.gradeTaskMapper.updateGradeTask(gradeTask);
            }
            condition = new Condition(GradeTask.class);
            condition.createCriteria().andEqualTo("taskSeq", 2)
                    .andEqualTo("taskStatus", 0)
                    .andIn("gradeMonthId", gradeMonthIdList);
            List<GradeTask> confirmList = this.gradeTaskMapper.selectByCondition(condition);
            if (confirmList != null && confirmList.size() > 0) {
                for (GradeTask gradeTask : confirmList) {
                    gradeTask.setUserId(Integer.valueOf(confirmUserId));
                    gradeTask.setTaskStatus(1);
                    this.gradeTaskMapper.updateGradeTask(gradeTask);
                }
            }
        }
    }

    @Override
    public void approveUser(String approveUserId, Integer userId) {
        Condition condition = new Condition(GradeTask.class);
        condition.createCriteria().andEqualTo("userId", userId)
                .andEqualTo("taskSeq", 2)
                .andEqualTo("taskStatus", 1);
        List<GradeTask> gradeTaskList = this.gradeTaskMapper.selectByCondition(condition);
        if (gradeTaskList != null && gradeTaskList.size() > 0) {
            List<Long> gradeMonthIdList = new LinkedList<>();
            for (GradeTask gradeTask: gradeTaskList) {
                gradeMonthIdList.add(gradeTask.getGradeMonthId());
                gradeTask.setTaskStatus(2);
                this.gradeTaskMapper.updateGradeTask(gradeTask);
            }
            condition = new Condition(GradeTask.class);
            condition.createCriteria().andEqualTo("taskSeq", 1)
                    .andEqualTo("taskStatus", 10)
                    .andIn("gradeMonthId", gradeMonthIdList);
            List<GradeTask> confirmDataList = this.gradeTaskMapper.selectByCondition(condition);
            if (confirmDataList != null || confirmDataList.size() > 0) {
                if (approveUserId == null || "".equals(approveUserId)) {
                    for(GradeTask gradeTask : confirmDataList) {
                        gradeTask.setTaskStatus(12);
                        this.gradeTaskMapper.updateGradeTask(gradeTask);
                    }
                    condition = new Condition(GradeTask.class);
                    condition.createCriteria().andEqualTo("taskSeq", 2)
                            .andEqualTo("taskStatus", 10)
                            .andIn("gradeMonthId", gradeMonthIdList);
                    List<GradeTask> approveDataList = this.gradeTaskMapper.selectByCondition(condition);
                    if (approveDataList != null || approveDataList.size() > 0) {
                        for(GradeTask gradeTask : approveDataList) {
                            gradeTask.setTaskStatus(11);
                            gradeTask.setUserId(userId);
                            this.gradeTaskMapper.updateGradeTask(gradeTask);
                        }
                    }
                } else {
                    for(GradeTask gradeTask : confirmDataList) {
                        gradeTask.setTaskStatus(11);
                        gradeTask.setUserId(userId);
                        this.gradeTaskMapper.updateGradeTask(gradeTask);
                    }
                    condition = new Condition(GradeTask.class);
                    condition.createCriteria().andEqualTo("taskSeq", 2)
                            .andEqualTo("taskStatus", 10)
                            .andIn("gradeMonthId", gradeMonthIdList);
                    List<GradeTask> approveDataList = this.gradeTaskMapper.selectByCondition(condition);
                    if (approveDataList != null || approveDataList.size() > 0) {
                        for(GradeTask gradeTask : approveDataList) {
                            gradeTask.setUserId(Integer.valueOf(approveUserId));
                            this.gradeTaskMapper.updateGradeTask(gradeTask);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void confirmData(Long id, Integer userId) {
        GradeTask gradeTask = this.gradeTaskMapper.selectByPrimaryKey(id);
        if (gradeTask != null) {
            gradeTask.setTaskStatus(12);
            this.gradeTaskMapper.updateGradeTask(gradeTask);
            GradeTask approveGradeTask = new GradeTask();
            approveGradeTask.setTaskStatus(10);
            approveGradeTask.setTaskSeq(2);
            approveGradeTask.setGradeMonthId(gradeTask.getGradeMonthId());
            approveGradeTask = this.gradeTaskMapper.selectOne(approveGradeTask);
            if (approveGradeTask != null) {
                approveGradeTask.setTaskStatus(11);
                this.gradeTaskMapper.updateGradeTask(approveGradeTask);
            }
        }
    }

    @Override
    public void confirmDatas(String ids, Integer userId) {
        String[] idArray = ids.split(",");
        for (String id : idArray) {
            this.confirmData(Long.valueOf(id), userId);
        }
    }

    @Override
    public void approveData(Long id, Integer userId) {
        GradeTask gradeTask = this.gradeTaskMapper.selectByPrimaryKey(id);
        if (gradeTask != null) {
            gradeTask.setTaskStatus(12);
            this.gradeTaskMapper.updateGradeTask(gradeTask);
        }
    }

    @Override
    public void approveDatas(String ids, Integer userId) {
        String[] idArray = ids.split(",");
        for (String id : idArray) {
            this.approveData(Long.valueOf(id), userId);
        }
    }
}
