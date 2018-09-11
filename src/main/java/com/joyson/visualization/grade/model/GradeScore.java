package com.joyson.visualization.grade.model;

import javax.persistence.*;

@Table(name = "grade_score")
public class GradeScore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "grade_line_id")
    private Long gradeLineId;

    private String score;

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
     * @return grade_line_id
     */
    public Long getGradeLineId() {
        return gradeLineId;
    }

    /**
     * @param gradeLineId
     */
    public void setGradeLineId(Long gradeLineId) {
        this.gradeLineId = gradeLineId;
    }

    /**
     * @return score
     */
    public String getScore() {
        return score;
    }

    /**
     * @param score
     */
    public void setScore(String score) {
        this.score = score;
    }
}