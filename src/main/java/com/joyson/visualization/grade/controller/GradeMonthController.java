package com.joyson.visualization.grade.controller;

import com.joyson.visualization.grade.model.Userinfo;
import com.joyson.visualization.grade.service.ExportPDFService;
import com.joyson.visualization.grade.service.GradeMonthService;
import com.joyson.visualization.grade.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
@RequestMapping("/gradeMonth")
public class GradeMonthController {

    @Autowired
    private GradeMonthService gradeMonthService;

    @Autowired
    private ExportPDFService exportPDFService;

    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping("/getPageListDlg.do")
    public String getPageListDlg(String userId, HttpServletRequest httpServletRequest) {
        httpServletRequest.setAttribute("userId", userId);
        Userinfo userinfo = this.userInfoService.queryUserInfoById(Integer.valueOf(userId));
        httpServletRequest.setAttribute("userName", userinfo.getUsername());
        return "gradeMonthList";
    }

    @RequestMapping("/getPageList.do")
    @ResponseBody
    public Object getPageList(String userId, String page, String rows, String deptName) {
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
        List<Map<String, Object>> mapList = this.gradeMonthService.queryGradeMonthPageList(Integer.valueOf(userId), map);
        Integer count = this.gradeMonthService.queryGradeMonthPageCount(Integer.valueOf(userId), map);
        resultMap.put("rows", mapList);
        resultMap.put("total", count);
        return resultMap;
    }

    @RequestMapping("/doExportPDF.do")
    @ResponseBody
    public Object doExportPDF(HttpServletRequest httpServletRequest, String id){
        Map<String, Object> map = new HashMap<>();
        try {
            String path = httpServletRequest.getSession().getServletContext().getRealPath("/");
            String fileName = this.exportPDFService.exportGradeMonthPDF(id, path);
            map.put("success", true);
            map.put("path", "/stdout/" + fileName);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
            map.put("message", e.getMessage());
        }
        return map;
    }
}
