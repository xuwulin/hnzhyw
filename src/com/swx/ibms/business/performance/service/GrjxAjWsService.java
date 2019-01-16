package com.swx.ibms.business.performance.service;

import java.util.Map;

/**
 * Created by xuwl on 2018/9/12.
 * description: 个人绩效案件文书查询
 */
public interface GrjxAjWsService {

    /**
     * 根据单位编码，工号，开始时间，结束时间获取案件状态为已办的案件
     * @param dwbm
     * @param gh
     * @param kssj
     * @param jssj
     * @param page
     * @param rows
     * @return
     */
    Map<String, Object> getAj(String dwbm, String gh, String kssj, String jssj, Integer page, Integer rows, String bmlbbm, String rylx);

    /**
     * 获取某一部门所办结的案件总数（公诉）
     * 案件状态为已办/归档，案件的当前阶段为流程结束
     * @param dwbm
     * @param kssj
     * @param jssj
     * @param page
     * @param rows
     * @param bmlbbm
     * @return
     */
    Map<String, Object> selectBjAjOfBm(String dwbm, String bmbm, String kssj, String jssj, Integer page, Integer rows, String bmlbbm);

    /**
     * 根据单位编码，工号，开始时间，结束时间获取案件状态为已办的案件，再获取案件对应的文书
     * @param dwbm
     * @param gh
     * @param kssj
     * @param jssj
     * @return
     */
    Map<String, Object> getAjWs(String dwbm, String gh, String kssj, String jssj,
                                Integer page, Integer rows, String fz, String queryNo, String bmlbbm);

    /**
     * 根据部门受案号查询案件
     * @param bmsahs
     * @param khid
     * @param khfzid
     * @param zxmbm
     * @return
     */
    Integer getAllAjByBmsahs(String[] bmsahs,String khid,String khfzid,String zxmbm, String bmlbbm);

    /**
     * 查询选中添加到统计表的的案件
     * @param page
     * @param pageSize
     * @param khid
     * @param zxmbm
     * @return
     */
    Map<String, Object> getGrjxAjtjPageList(Integer page, Integer pageSize, String khid, String zxmbm);

    /**
     * 移除选中的案件
     * @param id
     * @return
     */
    int deleteAjById(String id);

    /**
     * 应制作而未制作文书案件
     * @param page
     * @param pageSize
     * @param dwbm
     * @param gh
     * @param kssj
     * @param jssj
     * @return
     */
    Map<String, Object> getCountsOfNotMadeWsPageList(Integer page, Integer pageSize, String dwbm, String gh,
                                                     String kssj, String jssj, String bmlbbm);

    /**
     * 核阅案件
     * @param page
     * @param pageSize
     * @param dwbm
     * @param gh
     * @param kssj
     * @param jssj
     * @return
     */
    Map<String, Object> getCountsOfReviewAjPageList(Integer page, Integer pageSize, String dwbm, String gh,
                                                    String kssj, String jssj);

    /**
     * 亮红灯案件(办理超期案件)
     * 部门不同，判定方式不同
     * @param page
     * @param pageSize
     * @param dwbm
     * @param gh
     * @param kssj
     * @param jssj
     * @return
     */
    Map<String, Object> getCountsOfOvertimeAjPageList(Integer page, Integer pageSize, String dwbm, String gh,
                                                      String kssj, String jssj, String bmlbbm);

    /**
     * 【侦监】
     * @param page
     * @param pageSize
     * @param dwbm
     * @param gh
     * @param kssj
     * @param jssj
     * @param bmlbbm
     * @return
     */
    Map<String, Object> countsOfZj(Integer page, Integer pageSize, String dwbm, String gh,
                                   String kssj, String jssj, String bmlbbm, String cxbh);

    /**
     * 获取【受理日期和完成日期】都在指定时间内的案件
     * @param page
     * @param pageSize
     * @param dwbm
     * @param gh
     * @param kssj
     * @param jssj
     * @return
     */
    Map<String, Object> queryCountsOfBjajPageList(Integer page, Integer pageSize, String dwbm,
                                                  String gh, String kssj, String jssj);

    /**
     * 审查受理控告申诉案件，每件加0.5分。
     * @param page
     * @param pageSize
     * @param dwbm
     * @param gh
     * @param kssj
     * @param jssj
     * @return
     */
    Map<String, Object> getCountsOfExamineAndAcceptPageList(Integer page, Integer pageSize, String dwbm, String gh,
                                                            String kssj, String jssj);

    /**
     * 根据部门受案号和统一受案号查询文书
     * @param page
     * @param pageSize
     * @param bmsah
     * @param tysah
     * @return
     */
    Map<String, Object> getWsInfoByBmsahAndTysah(Integer page, Integer pageSize, String bmsah, String tysah);

    /**
     * 指定时间内受理的案件
     * @param page
     * @param pageSize
     * @param dwbm
     * @param gh
     * @param kssj
     * @param jssj
     * @return
     */
    Map<String, Object> getAjOfAcceptedPageList(Integer page, Integer pageSize, String dwbm, String gh,
                                                String kssj, String jssj);

    Map<String, Object> getAjByajlbbm(Integer page, Integer pageSize, String dwbm, String gh,
                                      String kssj, String jssj, String cxbh);
}
