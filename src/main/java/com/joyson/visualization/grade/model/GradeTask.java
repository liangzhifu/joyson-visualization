package com.joyson.visualization.grade.model;

import javax.persistence.*;

@Table(name = "grade_task")
public class GradeTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "grade_month_id")
    private Long gradeMonthId;

    @Column(name = "task_seq")
    private Integer taskSeq;

    @Column(name = "task_status")
    private Integer taskStatus;

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
     * @return grade_month_id
     */
    public Long getGradeMonthId() {
        return gradeMonthId;
    }

    /**
     * @param gradeMonthId
     */
    public void setGradeMonthId(Long gradeMonthId) {
        this.gradeMonthId = gradeMonthId;
    }

    /**
     * @return task_seq
     */
    public Integer getTaskSeq() {
        return taskSeq;
    }

    /**
     * @param taskSeq
     */
    public void setTaskSeq(Integer taskSeq) {
        this.taskSeq = taskSeq;
    }

    /**
     * @return task_status
     */
    public Integer getTaskStatus() {
        return taskStatus;
    }

    /**
     * @param taskStatus
     */
    public void setTaskStatus(Integer taskStatus) {
        this.taskStatus = taskStatus;
    }
}