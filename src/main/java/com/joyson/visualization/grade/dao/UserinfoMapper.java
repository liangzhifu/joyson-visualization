package com.joyson.visualization.grade.dao;

import com.joyson.visualization.common.core.Mapper;
import com.joyson.visualization.grade.model.GradeLine;
import com.joyson.visualization.grade.model.Userinfo;

import java.util.List;
import java.util.Map;

public interface UserinfoMapper extends Mapper<Userinfo> {

    /**
     * 查询生产线各班人员
     * @param gradeLine 评分生产线班次
     * @return 返回结果
     */
    List<Userinfo> selectUserinfoList(GradeLine gradeLine);

    List<Map<String, Object>> selectPostInfo(Integer userId);

    List<Map<String, Object>> selectUserList();
}