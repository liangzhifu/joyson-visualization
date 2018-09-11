package com.joyson.visualization.grade.service.impl;

import com.joyson.visualization.grade.dao.*;
import com.joyson.visualization.grade.model.*;
import com.joyson.visualization.grade.service.GradeLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Condition;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by L on 2018/7/25.
 */
@Service
public class GradeLineServiceImpl implements GradeLineService {

    @Autowired
    private GradeLineMapper gradeLineMapper;

    @Autowired
    private UserinfoMapper userinfoMapper;

    @Autowired
    private LineinfoMapper lineinfoMapper;

    @Autowired
    private GradeScoreMapper gradeScoreMapper;

    @Autowired
    private GradeMonthMapper gradeMonthMapper;

    @Autowired
    private GradeTaskMapper gradeTaskMapper;

    @Override
    public List<Map<String, Object>> queryGradeLinePageList(Integer userId, Map<String, Object> map) {
        List<Map<String, Object>> gradeLineList = new LinkedList<>();
        Userinfo userinfo = this.userinfoMapper.selectByPrimaryKey(userId);
        String access = userinfo.getAccess();
        if (access == null) {

        } else if ("员工".equals(access)) {

        } else if ("线长".equals(access)) {
            map.put("userId", userId);
        } else {

        }
        gradeLineList = this.gradeLineMapper.selectGradeLinePageList(map);
        return gradeLineList;
    }

    @Override
    public Integer queryqueryGradeLinePageCount(Integer userId, Map<String, Object> map) {
        Userinfo userinfo = this.userinfoMapper.selectByPrimaryKey(userId);
        String access = userinfo.getAccess();
        if (access == null) {

        } else if ("员工".equals(access)) {

        } else if ("线长".equals(access)) {
            map.put("userId", userId);
        } else {

        }
        return this.gradeLineMapper.selectGradeLinePageCount(map);
    }

    @Override
    public void addGradeLine(String lineId, String shiftName) throws Exception {
        Condition condition = new Condition(GradeLine.class);
        condition.createCriteria().andEqualTo("lineId", Integer.valueOf(lineId))
                .andEqualTo("shiftName", shiftName);
        Integer num = this.gradeLineMapper.selectCountByCondition(condition);
        if (num > 0) {
            throw new Exception("该线体班次已有评价，不能再添加！");
        }
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH);
        int weekDay = cal.get(Calendar.DAY_OF_WEEK);
        Date beginDate = date;
        while (Calendar.MONDAY != weekDay) {
            cal.add(Calendar.DATE, -1);
            weekDay = cal.get(Calendar.DAY_OF_WEEK);
            int monthTemp = cal.get(Calendar.MONTH);
            if (month == monthTemp) {
                beginDate = cal.getTime();
            } else {
                break;
            }
        }
        Date endDate = date;
        while (Calendar.SUNDAY != weekDay) {
            cal.add(Calendar.DATE, 1);
            weekDay = cal.get(Calendar.DAY_OF_WEEK);
            int monthTemp = cal.get(Calendar.MONTH);
            if (month == monthTemp) {
                endDate = cal.getTime();
            } else {
                break;
            }
        }
        Lineinfo lineinfo = this.lineinfoMapper.selectByPrimaryKey(Integer.valueOf(lineId));
        if (lineinfo == null) {
            throw new Exception("查不到线体，不能再添加！");
        }
        if ("A班".equals(shiftName)) {
            String leader = lineinfo.getShiftaleader();
            if (leader == null || "".equals(leader)) {
                throw new Exception("该线体班次没有线长，不能再添加！");
            }
        } else {
            String leader = lineinfo.getShiftbleader();
            if (leader == null || "".equals(leader)) {
                throw new Exception("该线体班次没有线长，不能再添加！");
            }
        }

        GradeLine gradeLine = new GradeLine();
        gradeLine.setBeginDate(beginDate);
        gradeLine.setEndDate(endDate);
        gradeLine.setDeleteState(0);
        gradeLine.setGradeStatus(1);
        gradeLine.setLineId(lineinfo.getId());
        gradeLine.setShiftName(shiftName);
        if ("A班".equals(shiftName)) {
            gradeLine.setDeptName(lineinfo.getDeptname() + "A");
            gradeLine.setUserId(Integer.valueOf(lineinfo.getShiftaleader()));
        } else {
            gradeLine.setDeptName(lineinfo.getDeptname() + "B");
            gradeLine.setUserId(Integer.valueOf(lineinfo.getShiftbleader()));
        }
        this.gradeLineMapper.insertUseGeneratedKeys(gradeLine);
        // 获取生产线人员
        List<Userinfo> userinfoList = this.userinfoMapper.selectUserinfoList(gradeLine);
        if (userinfoList != null) {
            for (Userinfo userinfo : userinfoList) {
                GradeScore gradeScore = new GradeScore();
                gradeScore.setGradeLineId(gradeLine.getId());
                gradeScore.setUserId(userinfo.getId());
                this.gradeScoreMapper.insertUseGeneratedKeys(gradeScore);
            }
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        String monthOfGrade = format.format(beginDate);
        GradeMonth gradeMonth = new GradeMonth();
        gradeMonth.setLineId(lineinfo.getId());
        gradeMonth.setMonth(monthOfGrade);
        gradeMonth.setDeptName(lineinfo.getDeptname());
        gradeMonth.setShiftName(shiftName);
        if ("A班".equals(shiftName)) {
            gradeMonth.setUserId(Integer.valueOf(lineinfo.getShiftaleader()));
        } else {
            gradeMonth.setUserId(Integer.valueOf(lineinfo.getShiftbleader()));
        }
        this.gradeMonthMapper.insertUseGeneratedKeys(gradeMonth);

        GradeTask gradeTask = new GradeTask();
        gradeTask.setGradeMonthId(gradeMonth.getId());
        if ("A班".equals(shiftName)) {
            gradeTask.setUserId(Integer.valueOf(lineinfo.getShiftaleader()));
        } else {
            gradeTask.setUserId(Integer.valueOf(lineinfo.getShiftbleader()));
        }
        gradeTask.setTaskSeq(1);
        gradeTask.setTaskStatus(1);
        this.gradeTaskMapper.insertUseGeneratedKeys(gradeTask);
        gradeTask.setId(null);
        gradeTask.setUserId(null);
        gradeTask.setTaskSeq(2);
        gradeTask.setTaskStatus(0);
        this.gradeTaskMapper.insertUseGeneratedKeys(gradeTask);
        gradeTask.setId(null);
        gradeTask.setUserId(null);
        gradeTask.setTaskSeq(1);
        gradeTask.setTaskStatus(10);
        this.gradeTaskMapper.insertUseGeneratedKeys(gradeTask);
        gradeTask.setId(null);
        gradeTask.setUserId(null);
        gradeTask.setTaskSeq(2);
        gradeTask.setTaskStatus(10);
        this.gradeTaskMapper.insertUseGeneratedKeys(gradeTask);
    }

    @Override
    public void editGradeLineUserId(String id, String userId) throws Exception {
        GradeLine gradeLine = this.gradeLineMapper.selectGradeLineById(Integer.valueOf(id));
        // 判断是否已评价
        Integer gradeStatus = gradeLine.getGradeStatus();
        if (gradeStatus.intValue() == 2) {
            throw new Exception("已评价，不能修改线长！");
        }
        gradeLine.setUserId(Integer.valueOf(userId));
        this.gradeLineMapper.updateGradeUserId(gradeLine);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        GradeMonth gradeMonth = new GradeMonth();
        gradeMonth.setLineId(gradeLine.getLineId());
        gradeMonth.setShiftName(gradeLine.getShiftName());
        gradeMonth.setMonth(format.format(gradeLine.getBeginDate()));
        gradeMonth = this.gradeMonthMapper.selectOne(gradeMonth);

        if (gradeMonth != null) {
            GradeTask gradeTask = new GradeTask();
            gradeTask.setGradeMonthId(gradeMonth.getId());
            gradeTask.setTaskSeq(1);
            gradeTask.setTaskStatus(1);
            gradeTask = this.gradeTaskMapper.selectOne(gradeTask);
            if (gradeTask != null) {
                gradeTask.setUserId(Integer.valueOf(userId));
                this.gradeTaskMapper.updateGradeTask(gradeTask);

                gradeMonth.setUserId(Integer.valueOf(userId));
                this.gradeMonthMapper.updateGradeMonthUserId(gradeMonth);
            }
        }
    }
}
