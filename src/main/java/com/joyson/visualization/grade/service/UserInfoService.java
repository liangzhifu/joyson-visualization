package com.joyson.visualization.grade.service;

import com.joyson.visualization.grade.model.Userinfo;

import java.util.List;
import java.util.Map;

/**
 * Created by L on 2018/7/26.
 */
public interface UserInfoService {

    List<Map<String, Object>> queryUserInfoList();

    Userinfo queryUserInfoById(Integer id);

}
