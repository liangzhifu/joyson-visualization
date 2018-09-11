package com.joyson.visualization.grade.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "grade_line")
public class GradeLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "begin_date")
    private Date beginDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "shift_name")
    private String shiftName;

    @Column(name = "dept_name")
    private String deptName;

    @Column(name = "grade_status")
    private Integer gradeStatus;

    @Column(name = "grade_date")
    private Date gradeDate;

    @Column(name = "delete_state")
    private Integer deleteState;

    @Column(name = "line_id")
    private Integer lineId;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return user_id
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * @return begin_date
     */
    public Date getBeginDate() {
        return beginDate;
    }

    /**
     * @param beginDate
     */
    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    /**
     * @return end_date
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * @param endDate
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * @return shift_name
     */
    public String getShiftName() {
        return shiftName;
    }

    /**
     * @param shiftName
     */
    public void setShiftName(String shiftName) {
        this.shiftName = shiftName;
    }

    /**
     * @return dept_name
     */
    public String getDeptName() {
        return deptName;
    }

    /**
     * @param deptName
     */
    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    /**
     * @return grade_status
     */
    public Integer getGradeStatus() {
        return gradeStatus;
    }

    /**
     * @param gradeStatus
     */
    public void setGradeStatus(Integer gradeStatus) {
        this.gradeStatus = gradeStatus;
    }

    /**
     * @return grade_date
     */
    public Date getGradeDate() {
        return gradeDate;
    }

    /**
     * @param gradeDate
     */
    public void setGradeDate(Date gradeDate) {
        this.gradeDate = gradeDate;
    }

    /**
     * @return delete_state
     */
    public Integer getDeleteState() {
        return deleteState;
    }

    /**
     * @param deleteState
     */
    public void setDeleteState(Integer deleteState) {
        this.deleteState = deleteState;
    }

    public Integer getLineId() {
        return lineId;
    }

    public void setLineId(Integer lineId) {
        this.lineId = lineId;
    }
}