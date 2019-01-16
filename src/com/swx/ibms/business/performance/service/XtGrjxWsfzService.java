package com.swx.ibms.business.performance.service;


import com.swx.ibms.business.performance.bean.XtGrjxWsfz;

import java.util.List;
import java.util.Map;

public interface XtGrjxWsfzService {

    int deleteByPrimaryKey(String id);

    int insert(XtGrjxWsfz record);

    int insertSelective(XtGrjxWsfz record);

    XtGrjxWsfz selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(XtGrjxWsfz record);

    int updateByPrimaryKey(XtGrjxWsfz record);

    Map<String,Object> getXtGrjxWsfzAllPageList(Integer page,Integer pageSize);

    List<XtGrjxWsfz> getXtGrjxWsfzAllList();

    List<XtGrjxWsfz> queryResultByParams(XtGrjxWsfz record);

    /**
     * 根据部门类别编码和分值查询文书模板信息
     * @param bmlbbm
     * @param wsfz
     * @return
     */
    List<XtGrjxWsfz> queryWsfzByFz(String bmlbbm, double wsfz);
}
