package com.joyson.visualization.common.scheduled;

import com.joyson.visualization.grade.dao.*;
import com.joyson.visualization.grade.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by L on 2018/7/13.
 */
@Slf4j
@Component
public class ScheduledGrade {

    @Autowired
    private GradeLineMapper gradeLineMapper;

    @Autowired
    private LineinfoMapper lineinfoMapper;

    @Autowired
    private UserinfoMapper userinfoMapper;

    @Autowired
    private GradeScoreMapper gradeScoreMapper;

    @Autowired
    private GradeMonthMapper gradeMonthMapper;

    @Autowired
    private GradeTaskMapper gradeTaskMapper;

//    @Scheduled(cron = "0 0/5 * * * ?")
//    public void test() throws Exception{
//        System.out.println("begin");
//        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
//        Date beginDate = format2.parse("2018-07-23");
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(beginDate);
//        cal.add(Calendar.DATE, 6);
//        Date endDate = cal.getTime();
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
//        String month = format.format(beginDate);
//        try {
//            this.generateGradeLine(beginDate, endDate);
//            System.out.println("GradeLine");
//            this.generateGradeMonth(month);
//        }catch (Exception e) {
//            e.printStackTrace();
//        }
//        System.out.println("GradeMonth");
//    }

    /**
     * 每周一凌晨2点定时执行
     */
    @Scheduled(cron = "0 0 2 ? * MON")
    public void generateGradeLineWeek() {
        // 获取开始日期 结束日期
        Date beginDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(beginDate);
        Date endDate = cal.getTime();
        int month = cal.get(Calendar.MONTH);
        for (int i = 0; i < 6; i++) {
            cal.add(Calendar.DATE, 1);
            int newMonth = cal.get(Calendar.MONTH);
            if (newMonth == month) {
                endDate = cal.getTime();
            } else {
                break;
            }
        }
        this.generateGradeLine(beginDate, endDate);
    }

    /**
     * 每月一号凌晨3点执行
     */
    @Scheduled(cron = "0 0 3 1 * ?")
    public void generateGradeLineMonth() {
        // 判断当天是否星期一
        Date beginDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        String month = format.format(beginDate);
        this.generateGradeMonth(month);
        Calendar cal = Calendar.getInstance();
        cal.setTime(beginDate);
        int week = cal.get(Calendar.DAY_OF_WEEK);
        if (week != 2) { // 判断是否周一
            int day = 0;
            switch (week) {
                case 1:
                    day = 0;
                    break;
                case 3:
                    day = 5;
                    break;
                case 4:
                    day = 4;
                    break;
                case 5:
                    day = 3;
                    break;
                case 6:
                    day = 2;
                    break;
                case 7:
                    day = 1;
                    break;
                default:
                    break;
            }
            for (int i = 0; i < day; i++) {
                cal.add(Calendar.DATE, 1);
            }
            Date endDate = cal.getTime();
            this.generateGradeLine(beginDate, endDate);
        }
    }

    /**
     * 根据月份生成月度考核
     * @param month 月份
     */
    private void generateGradeMonth(String month) {
        // 查询生产线
        List<Lineinfo> lineinfoList = this.lineinfoMapper.selectLineinfoList();
        if (lineinfoList != null) {
            for (Lineinfo lineinfo : lineinfoList) {
                GradeMonth gradeMonth = new GradeMonth();
                gradeMonth.setLineId(lineinfo.getId());
                gradeMonth.setMonth(month);
                String shiftaleader = lineinfo.getShiftaleader();
                gradeMonth.setDeptName(lineinfo.getDeptname());
                if (!(shiftaleader == null || "".equals(shiftaleader))) {
                    gradeMonth.setUserId(Integer.valueOf(shiftaleader));
                    gradeMonth.setShiftName("A班");
                    this.gradeMonthMapper.insertUseGeneratedKeys(gradeMonth);

                    GradeTask gradeTask = new GradeTask();
                    gradeTask.setGradeMonthId(gradeMonth.getId());
                    gradeTask.setUserId(Integer.valueOf(shiftaleader));
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
                String shiftbleader = lineinfo.getShiftbleader();
                if (!(shiftbleader == null || "".equals(shiftbleader))) {
                    gradeMonth.setUserId(Integer.valueOf(shiftbleader));
                    gradeMonth.setShiftName("B班");
                    this.gradeMonthMapper.insertUseGeneratedKeys(gradeMonth);

                    GradeTask gradeTask = new GradeTask();
                    gradeTask.setGradeMonthId(gradeMonth.getId());
                    gradeTask.setUserId(Integer.valueOf(shiftbleader));
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
            }
        }
    }

    /**
     * 根据开始时间、结束时间生成打分生产线班次
     * @param beginDate 开始时间
     * @param endDate 结束时间
     */
    private void generateGradeLine(Date beginDate, Date endDate) {
        // 查询生产线
        List<Lineinfo> lineinfoList = this.lineinfoMapper.selectLineinfoList();
        if (lineinfoList != null) {
            for (Lineinfo lineinfo : lineinfoList) {
                GradeLine gradeLine = new GradeLine();
                gradeLine.setBeginDate(beginDate);
                gradeLine.setEndDate(endDate);
                gradeLine.setDeleteState(0);
                gradeLine.setGradeStatus(1);
                gradeLine.setLineId(lineinfo.getId());
                String shiftaleader = lineinfo.getShiftaleader();
                if (!(shiftaleader == null || "".equals(shiftaleader))) {
                    gradeLine.setUserId(Integer.valueOf(shiftaleader));
                    gradeLine.setId(null);
                    gradeLine.setDeptName(lineinfo.getDeptname() + "A");
                    gradeLine.setShiftName("A班");
                    this.gradeLineMapper.insertUseGeneratedKeys(gradeLine);
                    this.generateGradeScore(gradeLine);
                }
                String shiftbleader = lineinfo.getShiftbleader();
                if (!(shiftbleader == null || "".equals(shiftbleader))) {
                    gradeLine.setUserId(Integer.valueOf(shiftbleader));
                    gradeLine.setId(null);
                    gradeLine.setDeptName(lineinfo.getDeptname() + "B");
                    gradeLine.setShiftName("B班");
                    this.gradeLineMapper.insertUseGeneratedKeys(gradeLine);
                    this.generateGradeScore(gradeLine);
                }
            }
        }
    }

    /**
     * 根据打分生产线班次，生产对应人员的打分
     * @param gradeLine 打分生产线班次
     */
    private void generateGradeScore(GradeLine gradeLine) {
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
    }

}
