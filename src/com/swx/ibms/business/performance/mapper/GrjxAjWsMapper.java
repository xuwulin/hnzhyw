package com.swx.ibms.business.performance.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by xuwl on 2018/9/12.
 * description: 个人绩效自动计算 案件文书查询
 */
public interface GrjxAjWsMapper {

    /**
     * 指定时间段内办结的案件：刑申
     * @param dwbm
     * @param gh
     * @param kssj
     * @param jssj
     * @return
     */
    List<Map<String, Object>> getAj(@Param("dwbm") String dwbm,
                                    @Param("gh") String gh,
                                    @Param("kssj") String kssj,
                                    @Param("jssj") String jssj);

    /**
     * 【受理日期和完成日期】都在指定时间段内办结的案件：（检查技术）
     * @param dwbm
     * @param gh
     * @param kssj
     * @param jssj
     * @return
     */
    List<Map<String, Object>> queryBjAj(@Param("dwbm") String dwbm,
                                        @Param("gh") String gh,
                                        @Param("kssj") String kssj,
                                        @Param("jssj") String jssj);

    /**
     * 【受理日期和完成日期】都在指定时间段内的（检验鉴定和技术性证据审查）这两类案件的办结的案件：（检查技术）
     * @param dwbm
     * @param gh
     * @param kssj
     * @param jssj
     * @return
     */
    List<Map<String, Object>> queryBjAjOfJyAndJs(@Param("dwbm") String dwbm,
                                                 @Param("gh") String gh,
                                                 @Param("kssj") String kssj,
                                                 @Param("jssj") String jssj);

    /**
     * 【完成日期】在指定时间内的案件，案件状态为已办/归档，案件的当前阶段为流程结束:(公诉)
     * @param dwbm
     * @param gh
     * @param kssj
     * @param jssj
     * @return
     */
    List<Map<String, Object>> selectBjAj(@Param("dwbm") String dwbm,
                                         @Param("gh") String gh,
                                         @Param("kssj") String kssj,
                                         @Param("jssj") String jssj);

    /**
     * 查询某一部门,所办结的案件总数 (公诉)
     * 【完成日期】在指定时间内的案件，案件状态为已办/归档，案件的当前阶段为流程结束
     * @param dwbm
     * @param bmbm
     * @param kssj
     * @param jssj
     * @return
     */
    List<Map<String, Object>> selectBjAjOfBm(@Param("dwbm") String dwbm,
                                             @Param("bmbm") String bmbm,
                                             @Param("kssj") String kssj,
                                             @Param("jssj") String jssj);

    /**
     * 查询 受理日期在指定日期内的 案件状态为已办或归档的案件 为办结案件：【侦监、执检（检察官）】
     * @param dwbm
     * @param gh
     * @param kssj
     * @param jssj
     * @return
     */
    List<Map<String, Object>> selectBjAjOfZj(@Param("dwbm") String dwbm,
                                             @Param("gh") String gh,
                                             @Param("kssj") String kssj,
                                             @Param("jssj") String jssj);

    /**
     * 【执检 检察辅助人员的办结案件】检察文书、流转签发单最后一位审核人的审核日期在考核日期内：【执检（检察辅助人员）】
     * @param dwbm
     * @param gh
     * @param kssj
     * @param jssj
     * @return
     */
    List<Map<String, Object>> selectBjAjOfZjJf(@Param("dwbm") String dwbm,
                                               @Param("gh") String gh,
                                               @Param("kssj") String kssj,
                                               @Param("jssj") String jssj);

    /**
     * 【侦监】：追加逮捕的犯罪嫌疑人被依法批准逮捕的，加分0.3分/人
     * @param dwbm
     * @param gh
     * @param kssj
     * @param jssj
     * @return
     */
    List<Map<String, Object>> selectZj1(@Param("dwbm") String dwbm,
                                        @Param("gh") String gh,
                                        @Param("kssj") String kssj,
                                        @Param("jssj") String jssj);

    /**
     * 【侦监】：监督行政执法部门移送涉嫌犯罪案件的，加分0.4分/件
     * @param dwbm
     * @param gh
     * @param kssj
     * @param jssj
     * @return
     */
    List<Map<String, Object>> selectZj2(@Param("dwbm") String dwbm,
                                        @Param("gh") String gh,
                                        @Param("kssj") String kssj,
                                        @Param("jssj") String jssj);

    /**
     * 【侦监】：监督行政执法机关移送后公安机关直接立案的，加分0.5分/件
     * @param dwbm
     * @param gh
     * @param kssj
     * @param jssj
     * @return
     */
    List<Map<String, Object>> selectZj3(@Param("dwbm") String dwbm,
                                        @Param("gh") String gh,
                                        @Param("kssj") String kssj,
                                        @Param("jssj") String jssj);

    /**
     * 【侦监】：监督公安机关立案或者撤案的，加分0.4分/件
     * @param dwbm
     * @param gh
     * @param kssj
     * @param jssj
     * @return
     */
    List<Map<String, Object>> selectZj4(@Param("dwbm") String dwbm,
                                        @Param("gh") String gh,
                                        @Param("kssj") String kssj,
                                        @Param("jssj") String jssj);

    /**
     * 【侦监】：上述1-5监督案件的犯罪嫌疑人被判有罪的，再加分0.3分/件
     * @param dwbm
     * @param gh
     * @param kssj
     * @param jssj
     * @return
     */
    List<Map<String, Object>> selectZj5(@Param("dwbm") String dwbm,
                                        @Param("gh") String gh,
                                        @Param("kssj") String kssj,
                                        @Param("jssj") String jssj);

    /**
     * 【侦监】：上述1-5监督案件的犯罪嫌疑人被判处三年以上有期徒刑的，再加分0.2分/件
     * @param dwbm
     * @param gh
     * @param kssj
     * @param jssj
     * @return
     */
    List<Map<String, Object>> selectZj6(@Param("dwbm") String dwbm,
                                        @Param("gh") String gh,
                                        @Param("kssj") String kssj,
                                        @Param("jssj") String jssj);

    /**
     * 【侦监】：上述1-5监督案件的犯罪嫌疑人被判处十年以上有期徒刑的，再加分0.5分/件
     * @param dwbm
     * @param gh
     * @param kssj
     * @param jssj
     * @return
     */
    List<Map<String, Object>> selectZj7(@Param("dwbm") String dwbm,
                                        @Param("gh") String gh,
                                        @Param("kssj") String kssj,
                                        @Param("jssj") String jssj);

    /**
     * 【侦监】：书面纠正违法且侦查机关回复整改的，加分0.3分/件
     * @param dwbm
     * @param gh
     * @param kssj
     * @param jssj
     * @return
     */
    List<Map<String, Object>> selectZj8(@Param("dwbm") String dwbm,
                                        @Param("gh") String gh,
                                        @Param("kssj") String kssj,
                                        @Param("jssj") String jssj);

    /**
     * 【侦监】：发出检察建议且有关单位整改回复的，加分0.5分/件
     * @param dwbm
     * @param gh
     * @param kssj
     * @param jssj
     * @return
     */
    List<Map<String, Object>> selectZj9(@Param("dwbm") String dwbm,
                                        @Param("gh") String gh,
                                        @Param("kssj") String kssj,
                                        @Param("jssj") String jssj);

    /**
     * 【执检】：减假暂案件发现不当提出书面纠正意见被采纳的，每件计2分
     * @param dwbm
     * @param gh
     * @param kssj
     * @param jssj
     * @return
     */
    List<Map<String, Object>> selectZj10(@Param("dwbm") String dwbm,
                                         @Param("gh") String gh,
                                         @Param("kssj") String kssj,
                                         @Param("jssj") String jssj);

    /**
     * 【执检】：提出释放或变更强制措施建议被采纳的，每件计2分
     * @param dwbm
     * @param gh
     * @param kssj
     * @param jssj
     * @return
     */
    List<Map<String, Object>> selectZj11(@Param("dwbm") String dwbm,
                                         @Param("gh") String gh,
                                         @Param("kssj") String kssj,
                                         @Param("jssj") String jssj);

    /**
     * 【执检】：纠正违法通知书或检察建议书被采纳的，每件计2分
     * @param dwbm
     * @param gh
     * @param kssj
     * @param jssj
     * @return
     */
    List<Map<String, Object>> selectZj12(@Param("dwbm") String dwbm,
                                         @Param("gh") String gh,
                                         @Param("kssj") String kssj,
                                         @Param("jssj") String jssj);

    /**
     * 【民行】：办理生效裁判（含调解）监督案件提出再审检察建议或者提请、提出抗诉的，每件计8分
     * @param dwbm
     * @param gh
     * @param kssj
     * @param jssj
     * @return
     */
    List<Map<String, Object>> selectMx1(@Param("dwbm") String dwbm,
                                        @Param("gh") String gh,
                                        @Param("kssj") String kssj,
                                        @Param("jssj") String jssj);

    /**
     * 【民行】：办理审判人员违法行为监督案件提出检察建议的，每件计5分
     * @param dwbm
     * @param gh
     * @param kssj
     * @param jssj
     * @return
     */
    List<Map<String, Object>> selectMx2(@Param("dwbm") String dwbm,
                                        @Param("gh") String gh,
                                        @Param("kssj") String kssj,
                                        @Param("jssj") String jssj);

    /**
     * 【民行】：办理执行监督案件提出检察建议的，每件计5分
     * @param dwbm
     * @param gh
     * @param kssj
     * @param jssj
     * @return
     */
    List<Map<String, Object>> selectMx3(@Param("dwbm") String dwbm,
                                        @Param("gh") String gh,
                                        @Param("kssj") String kssj,
                                        @Param("jssj") String jssj);

    /**
     * 【民行】：办理行政机关不当履行职责监督案件（包括督促起诉案件）提出检察建议的，每件计5分
     * @param dwbm
     * @param gh
     * @param kssj
     * @param jssj
     * @return
     */
    List<Map<String, Object>> selectMx4(@Param("dwbm") String dwbm,
                                        @Param("gh") String gh,
                                        @Param("kssj") String kssj,
                                        @Param("jssj") String jssj);

    /**
     * 【民行】：检察官办理对下级院不支持监督决定进行复查的案件,维持原处理决定的，每件计1分
     * @param dwbm
     * @param gh
     * @param kssj
     * @param jssj
     * @return
     */
    List<Map<String, Object>> selectMx5(@Param("dwbm") String dwbm,
                                        @Param("gh") String gh,
                                        @Param("kssj") String kssj,
                                        @Param("jssj") String jssj);

    /**
     * 【民行】：办理生效裁判监督案件提请上级院抗诉未获支持或案件抗诉后法院未予采纳的，每件扣2分
     * @param dwbm
     * @param gh
     * @param kssj
     * @param jssj
     * @return
     */
    List<Map<String, Object>> selectMx6(@Param("dwbm") String dwbm,
                                        @Param("gh") String gh,
                                        @Param("kssj") String kssj,
                                        @Param("jssj") String jssj);

    /**
     * 【民行】：办理生效裁判（含调解）监督案件提出再审检察建议被法院采纳或者提请抗诉被上级院支持、提出抗诉得到法院采纳的，每件加2分
     * @param dwbm
     * @param gh
     * @param kssj
     * @param jssj
     * @return
     */
    List<Map<String, Object>> selectMx7(@Param("dwbm") String dwbm,
                                        @Param("gh") String gh,
                                        @Param("kssj") String kssj,
                                        @Param("jssj") String jssj);

    /**
     * 【民行】：办理案件中促成当事人和解，案件作终结审查处理的，每件加3分
     * @param dwbm
     * @param gh
     * @param kssj
     * @param jssj
     * @return
     */
    List<Map<String, Object>> selectMx8(@Param("dwbm") String dwbm,
                                        @Param("gh") String gh,
                                        @Param("kssj") String kssj,
                                        @Param("jssj") String jssj);

    /**
     * 【民行】：办理审判人员违法行为监督案件提出检察建议被法院采纳的，每件加2分
     * @param dwbm
     * @param gh
     * @param kssj
     * @param jssj
     * @return
     */
    List<Map<String, Object>> selectMx9(@Param("dwbm") String dwbm,
                                        @Param("gh") String gh,
                                        @Param("kssj") String kssj,
                                        @Param("jssj") String jssj);

    /**
     * 【民行】：办理执行监督案件提出检察建议被法院采纳的，每件加2分
     * @param dwbm
     * @param gh
     * @param kssj
     * @param jssj
     * @return
     */
    List<Map<String, Object>> selectMx10(@Param("dwbm") String dwbm,
                                         @Param("gh") String gh,
                                         @Param("kssj") String kssj,
                                         @Param("jssj") String jssj);

    /**
     * 【民行】：办理行政机关不当履职的监督案件（包括督促起诉案件）提出检察建议被采纳的，每件加2分
     * @param dwbm
     * @param gh
     * @param kssj
     * @param jssj
     * @return
     */
    List<Map<String, Object>> selectMx11(@Param("dwbm") String dwbm,
                                         @Param("gh") String gh,
                                         @Param("kssj") String kssj,
                                         @Param("jssj") String jssj);

    /**
     * 【民行】：办理复查案件，改变原处理决定，提出抗诉，得到法院采纳的，每件加3分
     * @param dwbm
     * @param gh
     * @param kssj
     * @param jssj
     * @return
     */
    List<Map<String, Object>> selectMx12(@Param("dwbm") String dwbm,
                                         @Param("gh") String gh,
                                         @Param("kssj") String kssj,
                                         @Param("jssj") String jssj);

    /**
     * 指定时间段内受理的案件：适用于每个部门
     * @param dwbm
     * @param gh
     * @param kssj
     * @param jssj
     * @return
     */
    List<Map<String, Object>> getAjOfAccepted(@Param("dwbm") String dwbm,
                                              @Param("gh") String gh,
                                              @Param("kssj") String kssj,
                                              @Param("jssj") String jssj);

    /**
     * 根据部门受案号、统一受案号和文书分值查询一般2分/3分的文书
     * @param bmsahs
     * @param tysahs
     * @return
     */
    List<Map<String, Object>> getWsByScore(@Param("bmsahs") List<String> bmsahs,
                                           @Param("tysahs") List<String> tysahs,
                                           @Param("fz") String fz,
                                           @Param("gh") String gh,
                                           @Param("bmlbbm") String bmlbbm);

    /**
     * 根据部门受案号、统一受案号和文书分值查询其他文书
     * @param bmsahs
     * @param tysahs
     * @param fz
     * @return
     */
    List<Map<String, Object>> getOtherWs(@Param("bmsahs") List<String> bmsahs,
                                         @Param("tysahs") List<String> tysahs,
                                         @Param("fz") String fz,
                                         @Param("gh") String gh,
                                         @Param("bmlbbm") String bmlbbm);

    /**
     * 应该制作而未制作文书的案件数量：刑申和检技的应制作未制作文书的模板编码
     * @param bmsah
     * @param tysah
     * @return
     */
    int getCountsOfNotMadeWs(@Param("bmsah") String bmsah,
                             @Param("tysah") String tysah);

    /**
     * 根据部门受案号获取“某一个案件”：完结日期要根据文书来判断 ==》刑申
     * @param bmsah
     * @return
     * @throws Exception
     */
    Map<String, Object> getAjByBmsah(@Param("bmsah") String bmsah);

    /**
     * 根据部门受案号获取“某一个案件”：完结日期取完成日期 ==》公诉
     * @param bmsah
     * @return
     */
    Map<String, Object> selectAjByBmsah(@Param("bmsah") String bmsah);

    /**
     * 根据部门受案号（集合）获取所有案件集合,案件的完结日期需要根据提交的文书来判断
     * @param bmsahs
     * @return
     */
    List<Map<String, Object>> getAjsByBmsahs(@Param("bmsahs") List<String> bmsahs);


    /**
     * 根据部门受案号（集合）获取所有案件集合,案件的完结日期直接取案件的“完成日期”
     * @param bmsahs
     * @return
     */
    List<Map<String, Object>> getWjajsByBmsahs(@Param("bmsahs") List<String> bmsahs);

    /**
     * 部门领导或领导指定人核阅的案件
     * @return
     */
    List<Map<String, Object>> getAjOfReviewAj(@Param("dwbm") String dwbm,
                                              @Param("gh") String gh,
                                              @Param("kssj") String kssj,
                                              @Param("jssj") String jssj);

    /**
     * 红灯案件（办理超期），办结日期获或完成日期大于到期日期
     * @param dwbm
     * @param gh
     * @param kssj
     * @param jssj
     * @return
     */
    List<Map<String, Object>> getAjOfOvertimeAj(@Param("dwbm") String dwbm,
                                                @Param("gh") String gh,
                                                @Param("kssj") String kssj,
                                                @Param("jssj") String jssj);

    /**
     * 根据部门受案号和统一受案号查询文书
     * @param bmsah
     * @param tysah
     * @return
     */
    List<Map<String, Object>> getWsInfoByBmsahAndTysah(@Param("bmsah") String bmsah,
                                                       @Param("tysah") String tysah);

    /**
     * 审查受理控告申诉案件
     * 根据案件类别编码获取案件
     * @param dwbm
     * @param gh
     * @param kssj
     * @param jssj
     * @return
     */
    List<Map<String, Object>> countsOfExamineAndAccept(@Param("dwbm") String dwbm,
                                                       @Param("gh") String gh,
                                                       @Param("kssj") String kssj,
                                                       @Param("jssj") String jssj);

    /**
     * 根据案件类别编码获取案件
     * @param dwbm
     * @param gh
     * @param kssj
     * @param jssj
     * @param bmlbbms
     * @return
     */
    List<Map<String, Object>> getAjByajlbbm(@Param("dwbm") String dwbm,
                                            @Param("gh") String gh,
                                            @Param("kssj") String kssj,
                                            @Param("jssj") String jssj,
                                            @Param("ajlbbms") List<String> bmlbbms);

}
