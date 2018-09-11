package com.joyson.visualization.grade.controller;

import com.joyson.visualization.grade.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by L on 2018/7/26.
 */
@Controller
@RequestMapping("/userInfo")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping("/getList.do")
    @ResponseBody
    public Object getPageList(){
        List<Map<String, Object>> userinfoList = this.userInfoService.queryUserInfoList();
        Map<String, Object> map = new HashMap<>();
        map.put("success", 1);
        map.put("userInfoList", userinfoList);
        return map;
    }
}
