package com.swx.ibms.business.performance.mapper;


import com.swx.ibms.business.performance.bean.XtGrjxRypz;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface XtGrjxRypzMapper {
    int deleteByPrimaryKey(String id);

    int insert(XtGrjxRypz record);

    int insertSelective(XtGrjxRypz record);

    XtGrjxRypz selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(XtGrjxRypz record);

    int updateByPrimaryKey(XtGrjxRypz record);

    List<XtGrjxRypz> getGrjxKhryPzList(@Param("dwbm") String dwbm);

    List<XtGrjxRypz> getGrjxKhryPzByDwGh(@Param("dwbm") String dwbm, @Param("gh") String gh);
}