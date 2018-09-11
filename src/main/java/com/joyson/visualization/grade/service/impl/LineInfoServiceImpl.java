package com.joyson.visualization.grade.service.impl;

import com.joyson.visualization.grade.dao.LineinfoMapper;
import com.joyson.visualization.grade.model.Lineinfo;
import com.joyson.visualization.grade.service.LineInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by L on 2018/8/1.
 */
@Service
public class LineInfoServiceImpl implements LineInfoService {

    @Autowired
    private LineinfoMapper lineinfoMapper;

    @Override
    public List<Lineinfo> queryLineInfoList() {
        return this.lineinfoMapper.selectLineinfoList();
    }

}
