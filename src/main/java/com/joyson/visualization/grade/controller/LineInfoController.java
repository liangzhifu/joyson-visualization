package com.joyson.visualization.grade.controller;

import com.joyson.visualization.grade.model.Lineinfo;
import com.joyson.visualization.grade.service.LineInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by L on 2018/8/1.
 */
@Controller
@RequestMapping("/lineInfo")
public class LineInfoController {

    @Autowired
    private LineInfoService lineInfoService;

    @RequestMapping("/getList.do")
    @ResponseBody
    public Object getPageList(){
        List<Lineinfo> lineinfoList = this.lineInfoService.queryLineInfoList();
        Map<String, Object> map = new HashMap<>();
        map.put("success", 1);
        map.put("lineinfoList", lineinfoList);
        return map;
    }

}
