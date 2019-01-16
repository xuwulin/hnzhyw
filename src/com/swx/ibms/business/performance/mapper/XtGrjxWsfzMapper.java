package com.swx.ibms.business.performance.mapper;


import com.swx.ibms.business.performance.bean.XtGrjxWsfz;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface XtGrjxWsfzMapper {

    int deleteByPrimaryKey(String id);

    int insert(XtGrjxWsfz record);

    int insertSelective(XtGrjxWsfz record);

    XtGrjxWsfz selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(XtGrjxWsfz record);

    int updateByPrimaryKey(XtGrjxWsfz record);

    List<XtGrjxWsfz> getXtGrjxWsfzAllList();

    List<XtGrjxWsfz> queryResultByParams(XtGrjxWsfz record);

    /**
     * 根据部门类别编码和分值查询文书模板信息
     * @param fz
     * @return
     */
    List<XtGrjxWsfz> queryWsfzByFz(@Param("bmlbbm")String bmlbbm,
                                   @Param("fz")double fz);

}