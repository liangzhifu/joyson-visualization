package com.joyson.visualization.grade.dao;

import com.joyson.visualization.common.core.Mapper;
import com.joyson.visualization.grade.model.Lineinfo;

import java.util.List;

public interface LineinfoMapper extends Mapper<Lineinfo> {

    /**
     * 查询A班或者B班不为空的生产线
     * @return 生产线列表
     */
    List<Lineinfo> selectLineinfoList();

}