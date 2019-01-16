package com.swx.ibms.business.performance.service;


import com.swx.ibms.business.performance.bean.YxGrjxGsxx;

import java.util.List;
import java.util.Map;

public interface YxGrjxGsxxService {

    Integer deleteByPrimaryKey(String id);

    Integer insert(YxGrjxGsxx record);

    Integer insertSelective(YxGrjxGsxx record);

    YxGrjxGsxx selectByPrimaryKey(String id);

    YxGrjxGsxx selectByOtherKey(String jxkhid);

    Integer updateByPrimaryKeySelective(YxGrjxGsxx record);

    Integer updateByPrimaryKey(YxGrjxGsxx record);

    List<YxGrjxGsxx> selectByParams(YxGrjxGsxx record);

    List<YxGrjxGsxx> selectAll();

    Map<String,Object> selectPageList(Integer page, Integer pageSize);
}
