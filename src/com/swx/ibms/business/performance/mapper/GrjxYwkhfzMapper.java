package com.swx.ibms.business.performance.mapper;


import com.swx.ibms.business.performance.bean.ydkhqbtg;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface GrjxYwkhfzMapper {
    int deleteByPrimaryKey(String id);

    int insert(ydkhqbtg record);

    int insertSelective(ydkhqbtg record);

    ydkhqbtg selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ydkhqbtg record);

    int updateByPrimaryKey(ydkhqbtg record);

    /**
     * 重置评分人
     * @param record
     * @return
     */
    int updatePerOfScoreByPrimaryKey(ydkhqbtg record);

    ydkhqbtg getGrjxKhBtAndKhNrByParams(@Param("dwbm") String dwbm,
                                        @Param("gh") String gh,
                                        @Param("year")Integer year,
                                        @Param("ksrq") Date ksrq,
                                        @Param("jsrq") Date jsrq,
                                        @Param("bmbm") String bmbm);

    /**
     * 根据月度考核id删除月度考核分值信息
     * @param ydkhid
     * @return
     */
    int deleteByYdKhId(String ydkhid);

    int updateByYdkhIdSelective(ydkhqbtg record);

    ydkhqbtg selectByYwkhId(String ydkhid);

    /**
     * 获取指定时间内办结的案件总数
     * 注：并不是获取当前年受理当前年办结的案件数
     * @param dwbm
     * @param gh
     * @param ksrq
     * @param jsrq
     * @return
     */
    int getCountsOfCompleted(@Param("dwbm") String dwbm,
                             @Param("gh") String gh,
                             @Param("ksrq") String ksrq,
                             @Param("jsrq") String jsrq);

    int getCountsOfAccepted(@Param("dwbm") String dwbm,
                            @Param("gh") String gh,
                            @Param("ksrq") String ksrq,
                            @Param("jsrq") String jsrq);

    /**
     * 查询部门中的所有人员的工号，内勤除外
     * @param dwbm
     * @param bmbm
     * @return
     */
    List<String> selectByAjyjGh(@Param("dwbm") String dwbm,
                                @Param("bmbm") String bmbm);

}