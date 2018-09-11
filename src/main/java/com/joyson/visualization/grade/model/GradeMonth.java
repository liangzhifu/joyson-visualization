package com.joyson.visualization.grade.model;

import javax.persistence.*;

@Table(name = "grade_month")
public class GradeMonth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Integer userId;

    private String month;

    @Column(name = "dept_name")
    private String deptName;

    @Column(name = "shift_name")
    private String shiftName;

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
     * @return month
     */
    public String getMonth() {
        return month;
    }

    /**
     * @param month
     */
    public void setMonth(String month) {
        this.month = month;
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
     * @return line_id
     */
    public Integer getLineId() {
        return lineId;
    }

    /**
     * @param lineId
     */
    public void setLineId(Integer lineId) {
        this.lineId = lineId;
    }
}