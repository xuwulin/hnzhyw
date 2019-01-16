package com.swx.ibms.business.performance.service;


import com.swx.ibms.business.performance.bean.XtGrjxRytype;

import java.util.List;
import java.util.Map;

public interface XtGrjxRytypeService {

    int deleteByPrimaryKey(String id);

    int insert(XtGrjxRytype record);

    int insertSelective(XtGrjxRytype record);

    XtGrjxRytype selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(XtGrjxRytype record);

    int updateByPrimaryKey(XtGrjxRytype record);

    Map<String,Object> getGrjxKhryTypePzPageList(Integer page, Integer pageSize);

    List<XtGrjxRytype> getGrjxKhryTypePzList();

    List<XtGrjxRytype> getListKhryType();
}
