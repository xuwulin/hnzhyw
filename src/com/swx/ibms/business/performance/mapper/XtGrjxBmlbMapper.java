package com.swx.ibms.business.performance.mapper;


import com.swx.ibms.business.performance.bean.XtGrjxBmlb;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by xuwl on 2018/9/13.
 * description：部门类别
 */
public interface XtGrjxBmlbMapper {

    List<Map<String, Object>> selectAllBmlb();

    int insertBmlb(@Param("id") String id,
                   @Param("bmlbmc") String bmlbmc,
                   @Param("bmlbbm") String bmlbbm,
                   @Param("cjr") String cjr,
                   @Param("cjsj") String cjsj,
                   @Param("ms") String ms,
                   @Param("xh") Integer xh);

    int updateBmlb(@Param("id") String id,
                   @Param("bmlbmc") String bmlbmc,
                   @Param("gxr") String gxr,
                   @Param("gxsj") String gxsj,
                   @Param("ms") String ms,
                   @Param("xh") Integer xh);

    int deleteBmlb(@Param("id") String id);

    String getMaxBmlbbm();

    int isExistBmlbmc(@Param("bmlbmc") String bmlbmc);

    List<XtGrjxBmlb> getGrjxBmlbList();

    XtGrjxBmlb getBmlbByBmlbbm(@Param("bmlbbm") String bmlbbm);

}
