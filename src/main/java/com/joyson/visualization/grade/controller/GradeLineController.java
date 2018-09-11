package com.joyson.visualization.grade.controller;

import com.joyson.visualization.grade.model.Userinfo;
import com.joyson.visualization.grade.service.GradeLineService;
import com.joyson.visualization.grade.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by L on 2018/7/25.
 */
@Controller
@RequestMapping("/gradeLine")
public class GradeLineController {

    @Autowired
    private GradeLineService gradeLineService;

    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping("/getPageListDlg.do")
    public String getPageListDlg(String userId, HttpServletRequest httpServletRequest) {
        httpServletRequest.setAttribute("userId", userId);
        Userinfo userinfo = this.userInfoService.queryUserInfoById(Integer.valueOf(userId));
        httpServletRequest.setAttribute("userName", userinfo.getUsername());
        return "gradeLineList";
    }

    @RequestMapping("/add.do")
    @ResponseBody
    public Object addGradeLine(String lineId, String shiftName) {
        Map<String, Object> map = new HashMap<>();
        try {
            this.gradeLineService.addGradeLine(lineId, shiftName);
            map.put("success", true);
        } catch (Exception e) {
            map.put("success", false);
            map.put("message", e.getMessage());
        }
        return map;
    }

    @RequestMapping("/getPageList.do")
    @ResponseBody
    public Object getPageList(String userId, String page, String rows, String deptName, String gradeStatus) {
        Map<String, Object> resultMap = new HashMap<>();
        if (page == null || "".equals(page)) {
            page = "1";
        }
        if (rows == null || "".equals(rows)) {
            page = "10";
        }
        Integer pageNum = Integer.valueOf(page);
        Integer pageSize = Integer.valueOf(rows);
        Integer pageStart = (pageNum - 1) * pageSize;
        Map<String, Object> map = new HashMap<>();
        map.put("pageStart", pageStart);
        map.put("pageSize", pageSize);
        map.put("deptName", deptName);
        map.put("gradeStatus", gradeStatus);
        // 判断当前用户是否线长

        List<Map<String, Object>> mapList = this.gradeLineService.queryGradeLinePageList(Integer.valueOf(userId), map);
        Integer count = this.gradeLineService.queryqueryGradeLinePageCount(Integer.valueOf(userId), map);
        resultMap.put("rows", mapList);
        resultMap.put("total", count);
        return resultMap;
    }

    /**
     * 修改待评价线的线长
     * @param id
     * @param userId
     * @return
     */
    @RequestMapping("/editUserId.do")
    @ResponseBody
    public Object editUserId(String id, String userId) {
        Map<String, Object> map = new HashMap<>();
        try {
            this.gradeLineService.editGradeLineUserId(id, userId);
            map.put("success", true);
        } catch (Exception e) {
            map.put("success", false);
            map.put("message", e.getMessage());
        }
        return map;
    }

}
