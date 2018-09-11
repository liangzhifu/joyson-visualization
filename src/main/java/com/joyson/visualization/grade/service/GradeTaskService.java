package com.joyson.visualization.grade.service;

import java.util.List;
import java.util.Map;

/**
 * Created by L on 2018/7/26.
 */
public interface GradeTaskService {

    /**
     * 查询代办任务
     * @param map 查询条件
     * @return 返回结果
     */
    List<Map<String, Object>> queryGradeTaskList(Map<String, Object> map);

    Integer queryGradeTaskCount(Map<String, Object> map);

    /**
     * 选择确认人
     * @param confirmUserId 确认人员
     * @param userId 代办人员
     */
    void confirmUser(String confirmUserId, Integer userId);

    /**
     * 选择承认人
     * @param approveUserId 承认人
     * @param userId 代办人员
     */
    void approveUser(String approveUserId, Integer userId);

    /**
     * 确认评价
     * @param id 评价月份
     * @param userId 代办人
     */
    void confirmData(Long id, Integer userId);

    /**
     * 确认评价多个月份
     * @param ids 评价月份
     * @param userId 代办人
     */
    void confirmDatas(String ids, Integer userId);

    /**
     * 审核评价月份
     * @param id 评价月份
     * @param userId 代办人
     */
    void approveData(Long id, Integer userId);

    /**
     * 审核评价多个月份
     * @param ids 评价月份
     * @param userId 代办人
     */
    void approveDatas(String ids, Integer userId);

}
