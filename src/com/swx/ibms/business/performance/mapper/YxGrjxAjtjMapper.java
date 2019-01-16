package com.swx.ibms.business.performance.mapper;


import com.swx.ibms.business.performance.bean.YxGrjxAjtj;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface YxGrjxAjtjMapper {
    int deleteByPrimaryKey(String id);

    int deleteByKhid(String khid);

    int insert(YxGrjxAjtj record);

    int insertSelective(YxGrjxAjtj record);

    YxGrjxAjtj selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(YxGrjxAjtj record);

    int updateByPrimaryKey(YxGrjxAjtj record);

    /**
     * 根据考核id和子项目编码获取 从办结案件的下拉树中选择案件添加到统计表 的案件
     * @param khid
     * @param zxmbm
     * @return
     */
    List<YxGrjxAjtj> getGrjxAjtjList(@Param("khid") String khid,@Param("zxmbm") String zxmbm);

    int selectByBmsah(@Param("khid") String khid,
                      @Param("khfzid") String khfzid,
                      @Param("bmsah")String bmsah,
                      @Param("tysah")String tysah,
                      @Param("zxmbm")String zxmbm);
}