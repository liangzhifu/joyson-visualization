package com.joyson.visualization.grade.service.impl;

import com.joyson.visualization.grade.dao.UserinfoMapper;
import com.joyson.visualization.grade.model.Userinfo;
import com.joyson.visualization.grade.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by L on 2018/7/26.
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserinfoMapper userinfoMapper;

    @Override
    public List<Map<String, Object>> queryUserInfoList() {
        return this.userinfoMapper.selectUserList();
    }

    @Override
    public Userinfo queryUserInfoById(Integer id) {
        return this.userinfoMapper.selectByPrimaryKey(id);
    }
}
