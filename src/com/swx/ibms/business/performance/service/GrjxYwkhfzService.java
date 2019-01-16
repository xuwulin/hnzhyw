package com.swx.ibms.business.performance.service;


import com.swx.ibms.business.performance.bean.ydkhqbtg;

import java.util.Date;
import java.util.Map;

public interface GrjxYwkhfzService {

    Integer deleteByPrimaryKey(String id);

    Integer insert(ydkhqbtg record);

    Integer insertSelective(ydkhqbtg record);

    ydkhqbtg selectByPrimaryKey(String id);

    Integer updateByPrimaryKeySelective(ydkhqbtg record);

    Integer updateByPrimaryKey(ydkhqbtg record);

    /**
     * 计算考核指标中每一项的得分
     * @param dwbm
     * @param dwjb
     * @param gh
     * @param year
     * @param ksrq
     * @param jsrq
     * @param bmbm
     * @return
     */
    ydkhqbtg getGrjxKhBtAndKhNrByParams(String dwbm, String dwjb, String gh, Integer year, Date ksrq, Date jsrq, String bmbm);

    ydkhqbtg getGrjxkhByParams(String dwbm, String gh, Integer year, Date ksrq, Date jsrq, String bmbm);

    Integer updateByYdkhIdSelective(ydkhqbtg record);

    Integer updatePerOfScoreByPrimaryKey(ydkhqbtg record);

    ydkhqbtg selectByYwkhId(String ydkhid);

    /**
     * 结案率，办结案件/受理案件
     * @param page
     * @param pageSize
     * @param dwbm
     * @param gh
     * @param ksrq
     * @param jsrq
     * @return
     */
    Map<String, Object> getCaseHandlingEfficiency(Integer page, Integer pageSize, String dwbm, String gh, String ksrq,
                                                  String jsrq, String bmlbbm, String rylx);

    /**
     * 本人办结案件数量/本部门平均办结案件数量
     * @param page
     * @param pageSize
     * @param dwbm
     * @param gh
     * @param ksrq
     * @param jsrq
     * @return
     */
    Map<String, Object> getCaseHandlingRatio(Integer page, Integer pageSize, String dwbm, String gh,
                                             String ksrq, String jsrq, String bmbm, String bmlbbm, String perTypeName);
}
