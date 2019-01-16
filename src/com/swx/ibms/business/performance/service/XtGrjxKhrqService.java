package com.swx.ibms.business.performance.service;


import com.swx.ibms.business.performance.bean.XtGrjxKhrq;

import java.util.Map;

public interface XtGrjxKhrqService {

    Map<String,Object> getGrjxKhrqPzPageList(Integer page, Integer pageSize);

    int insertSelective(XtGrjxKhrq record);

    int updateByPrimaryKeySelective(XtGrjxKhrq record);

    int deleteByPrimaryKey(String id);

    /**
     * 获取最新的一条记录
     * @return 单条记录
     */
    XtGrjxKhrq getGrjxKhrqPzLatest();



}
