package com.swx.ibms.business.performance.mapper;


import com.swx.ibms.business.performance.bean.XtGrjxKhrq;

import java.util.List;

public interface XtGrjxKhrqMapper {

    int deleteByPrimaryKey(String id);

    int insert(XtGrjxKhrq record);

    int insertSelective(XtGrjxKhrq record);

    XtGrjxKhrq selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(XtGrjxKhrq record);

    int updateByPrimaryKey(XtGrjxKhrq record);

    List<XtGrjxKhrq> getGrjxKhrqPzList();

    XtGrjxKhrq getGrjxKhrqPzLatest();
}