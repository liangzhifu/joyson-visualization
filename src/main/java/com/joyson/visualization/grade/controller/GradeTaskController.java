package com.joyson.visualization.grade.controller;

import com.joyson.visualization.grade.model.Userinfo;
import com.joyson.visualization.grade.service.GradeTaskService;
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
@RequestMapping("/gradeTask")
public class GradeTaskController {

    @Autowired
    private GradeTaskService gradeTaskService;

    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping("/getPageListDlg.do")
    public String getPageListDlg(String userId, HttpServletRequest httpServletRequest) {
        httpServletRequest.setAttribute("userId", userId);
        Userinfo userinfo = this.userInfoService.queryUserInfoById(Integer.valueOf(userId));
        httpServletRequest.setAttribute("userName", userinfo.getUsername());
        return "gradeTaskList";
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
        map.put("userId", userId);
        map.put("pageStart", pageStart);
        map.put("pageSize", pageSize);
        map.put("deptName", deptName);
        List<Map<String, Object>> mapList = this.gradeTaskService.queryGradeTaskList(map);;
        Integer count = this.gradeTaskService.queryGradeTaskCount(map);
        resultMap.put("rows", mapList);
        resultMap.put("total", count);
        return resultMap;
    }

    /**
     * 选择确认人
     * @param confirmUserId 确认人
     * @param userId 代办任务人
     * @return 返回结果
     */
    @RequestMapping("/confirmUser.do")
    @ResponseBody
    public Object confirmUser(String confirmUserId, String userId) {
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> map = new HashMap<>();
        try {
            this.gradeTaskService.confirmUser(confirmUserId, Integer.valueOf(userId));
            map.put("success", true);
        } catch (Exception e) {
            map.put("success", false);
        }
        return map;
    }

    /**
     * 选择审核人
     * @param confirmUserId 审核人员
     * @param userId 代办人员
     * @return 返回结果
     */
    @RequestMapping("/approveUser.do")
    @ResponseBody
    public Object approveUser(String confirmUserId, String userId) {
        Map<String, Object> map = new HashMap<>();
        try {
            this.gradeTaskService.approveUser(confirmUserId, Integer.valueOf(userId));
            map.put("success", true);
        } catch (Exception e) {
            map.put("success", false);
        }
        return map;
    }

    /**
     * 确认评价
     * @param id 月份ID
     * @param userId 代办人
     * @return 返回结果
     */
    @RequestMapping("/confirmGrade.do")
    @ResponseBody
    public Object confirmGrade(String id, String userId) {
        Map<String, Object> map = new HashMap<>();
        try {
            this.gradeTaskService.confirmData(Long.valueOf(id), Integer.valueOf(userId));
            map.put("success", true);
        } catch (Exception e) {
            map.put("success", false);
        }
        return map;
    }

    /**
     * 确认多个评价
     * @param ids 月份IDs
     * @param userId 代办人
     * @return 代办人
     */
    @RequestMapping("/confirmGradeList.do")
    @ResponseBody
    public Object confirmGradeList(String ids, String userId) {
        Map<String, Object> map = new HashMap<>();
        try {
            this.gradeTaskService.confirmDatas(ids, Integer.valueOf(userId));
            map.put("success", true);
        } catch (Exception e) {
            map.put("success", false);
        }
        return map;
    }

    /**
     * 承认评价
     * @param id 月份Id
     * @param userId 代办人
     * @return 返回结果
     */
    @RequestMapping("/approveGrade.do")
    @ResponseBody
    public Object approveGrade(String id, String userId) {
        Map<String, Object> map = new HashMap<>();
        try {
            this.gradeTaskService.approveData(Long.valueOf(id), Integer.valueOf(userId));
            map.put("success", true);
        } catch (Exception e) {
            map.put("success", false);
        }
        return map;
    }

    /**
     * 承认多个评价
     * @param ids  月份Ids
     * @param userId 代办人
     * @return 返回结果
     */
    @RequestMapping("/approveGradeList.do")
    @ResponseBody
    public Object approveGradeList(String ids, String userId){
        Map<String, Object> map = new HashMap<>();
        try {
            this.gradeTaskService.approveDatas(ids, Integer.valueOf(userId));
            map.put("success", true);
        } catch (Exception e) {
            map.put("success", false);
        }
        return map;
    }
}
