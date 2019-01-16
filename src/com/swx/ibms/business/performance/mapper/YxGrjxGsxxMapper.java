package com.swx.ibms.business.performance.mapper;


import com.swx.ibms.business.performance.bean.YxGrjxGsxx;

import java.util.List;

public interface YxGrjxGsxxMapper {
    int deleteByPrimaryKey(String id);

    int insert(YxGrjxGsxx record);

    int insertSelective(YxGrjxGsxx record);

    YxGrjxGsxx selectByPrimaryKey(String id);

    YxGrjxGsxx selectByOtherKey(String jxkhid);

    int updateByPrimaryKeySelective(YxGrjxGsxx record);

    int updateByPrimaryKey(YxGrjxGsxx record);

    List<YxGrjxGsxx> selectByParams(YxGrjxGsxx record);

    List<YxGrjxGsxx> selectAll();
}