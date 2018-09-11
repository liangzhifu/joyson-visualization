package com.joyson.visualization.grade.service;

import java.util.List;
import java.util.Map;

/**
 * Created by L on 2018/7/25.
 */
public interface GradeLineService {

    /**
     * 查询评价生产线
     * @param userId 用户ID
     * @param map 查询条件
     * @return 返回结果
     */
    List<Map<String, Object>> queryGradeLinePageList(Integer userId, Map<String, Object> map);

    Integer queryqueryGradeLinePageCount(Integer userId, Map<String, Object> map);

    void addGradeLine(String lineId, String shiftName) throws Exception;

    /**
     * 修改待评价线体的线长
     * @param id
     * @param userId
     * @throws Exception
     */
    void editGradeLineUserId(String id, String userId) throws Exception;

}
