package com.joyson.visualization.grade.controller;

import com.joyson.visualization.grade.model.Userinfo;
import com.joyson.visualization.grade.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by L on 2018/7/25.
 */
@Controller
public class MainController {

    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping("/main.do")
    public String getMainDlg(String userId, HttpServletRequest httpServletRequest) {
        httpServletRequest.setAttribute("userId", userId);
        Userinfo userinfo = this.userInfoService.queryUserInfoById(Integer.valueOf(userId));
        httpServletRequest.setAttribute("userName", userinfo.getUsername());
        return "main";
    }

}
