package com.swx.ibms.business.performance.service;


import com.swx.ibms.business.performance.bean.XtGrjxRypz;

import java.util.List;
import java.util.Map;

public interface XtGrjxKhryService {

    int deleteByPrimaryKey(String id);

    int insert(XtGrjxRypz record);

    int insertSelective(XtGrjxRypz record);

    XtGrjxRypz selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(XtGrjxRypz record);

    int updateByPrimaryKey(XtGrjxRypz record);

    Map<String,Object> getGrjxKhryPzList(String dwbm, Integer page, Integer pageSize);

    List<XtGrjxRypz> getGrjxKhryPzByDwGh(String dwbm, String gh);
}
