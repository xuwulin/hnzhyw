package com.swx.ibms.business.performance.mapper;


import com.swx.ibms.business.performance.bean.XtGrjxRytype;

import java.util.List;

public interface XtGrjxRytypeMapper {

    int deleteByPrimaryKey(String id);

    int insert(XtGrjxRytype record);

    int insertSelective(XtGrjxRytype record);

    XtGrjxRytype selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(XtGrjxRytype record);

    int updateByPrimaryKey(XtGrjxRytype record);

    List<XtGrjxRytype> getGrjxKhryTypePzList();

    List<XtGrjxRytype> getListKhryTypeByKhzb();
}