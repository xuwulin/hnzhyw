package com.swx.ibms.business.performance.service;


import com.swx.ibms.business.performance.bean.XtGrjxBmlb;

import java.util.List;
import java.util.Map;

public interface XtGrjxBmlbService {

    int deleteBmlb(String id);

    int insertBmlb(XtGrjxBmlb record);

    int updateBmlb(XtGrjxBmlb record);

    Map<String,Object> selectAllBmlb(Integer page, Integer pageSize);

    List<XtGrjxBmlb> getGrjxBmlbList();

    /**
     * 根据部门类别编码（唯一）获取部门类别信息
     * @param bmlbbm
     * @return
     */
    XtGrjxBmlb getBmlbByBmlbbm(String bmlbbm);

}
