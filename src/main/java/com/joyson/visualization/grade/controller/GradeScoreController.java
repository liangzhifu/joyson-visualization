package com.joyson.visualization.grade.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.joyson.visualization.grade.model.GradeScore;
import com.joyson.visualization.grade.service.GradeScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static javafx.scene.input.KeyCode.T;

/**
 * Created by L on 2018/7/26.
 */
@Controller
@RequestMapping("/gradeScore")
public class GradeScoreController {

    @Autowired
    private GradeScoreService gradeScoreService;

    @RequestMapping("/getGradeScoreList.do")
    @ResponseBody
    public Object getGradeScoreList(String gradeLineId) {
        return this.gradeScoreService.queryGradeScoreList(Integer.valueOf(gradeLineId));
    }

    @RequestMapping("/updateGradeScoreList.do")
    @ResponseBody
    public Object updateGradeScoreList(HttpServletRequest servletRequest) {
        List<GradeScore> gradeScoreList = new LinkedList<>();
        String gradeScoreListStr = servletRequest.getParameter("gradeScoreList");
        JSONArray jsonArray = JSON.parseArray(gradeScoreListStr);
        int size = jsonArray.size();
        for (int i = 0; i < size; i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            GradeScore gradeScore = new GradeScore();
            gradeScore.setId(Long.valueOf(jsonObject.get("id").toString()));
            gradeScore.setScore(jsonObject.get("score").toString());
            gradeScore.setGradeLineId(Long.valueOf(jsonObject.get("gradeLineId").toString()));
            gradeScoreList.add(gradeScore);
        }
        this.gradeScoreService.updateGradeScoreList(gradeScoreList);
        Map<String, Object> map = new HashMap<>();
        map.put("status", true);
        return  map;
    }

}
