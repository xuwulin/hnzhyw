/**
 *
 */
package com.swx.ibms.business.archives.service;

import com.swx.ibms.business.archives.bean.DAGZGD;
import com.swx.ibms.business.archives.bean.Person;
import com.swx.ibms.business.archives.bean.PersonJl;
import com.swx.ibms.business.archives.mapper.PersonMapper;
import com.swx.ibms.business.archives.mapper.SfdaGrxxPldrMapper;
import com.swx.ibms.business.archives.mapper.SfdacjMapper;
import com.swx.ibms.business.cases.bean.DACJAJXYRXX;
import com.swx.ibms.business.cases.bean.JCGDAAJZL;
import com.swx.ibms.business.cases.bean.SFDAAJ;
import com.swx.ibms.business.common.service.LogService;
import com.swx.ibms.business.common.utils.Constant;
import com.swx.ibms.business.rbac.mapper.LoginMapper;
import com.swx.ibms.business.rbac.service.LoginService;
import com.swx.ibms.common.utils.DateUtil;
import com.swx.ibms.common.utils.Identities;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 封泽超
 * @since 2017年2月28日
 */
@SuppressWarnings("all")
@Service("sfdacjService")
public class SfdacjServiceImp implements SfdacjService {

    /**
     * 日志对象
     */
    private static final Logger LOG = Logger.getLogger(SfdacjServiceImp.class);

    /**
     * 日志记录服务
     */
    @Resource
    private LogService logService;

    /**
     * 个人基本信息的mapper接口
     */
    @Resource
    private PersonMapper personMapper;

    /**
     * 个人基本信息批量导入的mapper接口
     */
    @Resource
    private SfdaGrxxPldrMapper sfdaGrxxPldrMapper;

    @Resource
    private LoginService loginService;

    @Resource
    private PersonService personService;

    /**
     * 数据库接口
     */
    @Resource
    private SfdacjMapper sfdacjMapper;

    @Resource
    private LoginMapper loginmapper;

    /**
     * 查询承办案件列表
     *
     * @param cbrgh
     * @param dwbm
     * @return
     */
    @Override
    public List<SFDAAJ> getCbAjList(String cbrgh, String dwbm, String kssj, String jssj) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("p_cbrgh", cbrgh);
        map.put("p_dwbm", dwbm);
        map.put("p_kssj", kssj);
        map.put("p_jssj", jssj);
        map.put("p_cur", null);
        try {
            sfdacjMapper.getCbAjList(map);
        } catch (Exception e) {
            LOG.error(StringUtils.EMPTY, e);
        }
        List<SFDAAJ> list = (List<SFDAAJ>) map.get("p_cur");
        if (list != null) {
            return list;
        }
        return null;
    }

    /**
     * 查询案件嫌疑人列表
     *
     * @param tysah
     */
    @Override
    public List<DACJAJXYRXX> getAjXyrList(String tysah) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("p_tysah", tysah);
        map.put("p_cur", "");
        try {
            sfdacjMapper.getAjXyrList(map);
        } catch (Exception e) {
            LOG.error(StringUtils.EMPTY, e);
        }
        List<DACJAJXYRXX> list = (List<DACJAJXYRXX>) map.get("p_cur");
        if (list != null) {
            return list;
        }
        return null;
    }

    /**
     * 添加办案质量
     *
     * @param map 办案质量信息
     * @return String
     */
    @Override
    public String addBazl(Map<String, Object> map) {
        map.put("Y", "");
        try {
            sfdacjMapper.addBazl(map);
        } catch (Exception e) {
            LOG.error(StringUtils.EMPTY, e);
            logService.error(Constant.CURRENT_USER.get().getDwbm(),
                    Constant.CURRENT_USER.get().getGh(),
                    Constant.CURRENT_USER.get().getMc(),
                    Constant.RZLX_CWRZ, e.toString());
        }
        String temp = (String) map.get("Y");
        if (temp == null) {
            temp = "0";
            //日志记录
            logService.error(Constant.CURRENT_USER.get().getDwbm(),
                    Constant.CURRENT_USER.get().getGh(),
                    Constant.CURRENT_USER.get().getMc(),
                    Constant.RZLX_CWRZ, "添加办案质量失败");
        } else {
            //日志记录
            logService.info(Constant.CURRENT_USER.get().getDwbm(),
                    Constant.CURRENT_USER.get().getGh(),
                    Constant.CURRENT_USER.get().getMc(),
                    Constant.RZLX_CZRZ, "添加办案质量");
        }
        return temp;
    }

    /**
     * 根据归档id查询归档信息
     *
     * @param dagzgd
     * @return
     */
    @Override
    public List<DAGZGD> selectDagzId(DAGZGD dagzgd) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("p_gdid", dagzgd.getId());
        map.put("p_cur", StringUtils.EMPTY);
        try {
        	sfdacjMapper.selectDagzId(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<DAGZGD> list = (List<DAGZGD>) map.get("p_cur");
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list;
    }

    /**
     * 根据统一受案号查询案件信息
     */
    @Override
    public List<SFDAAJ> selectajbytysah(String tysah) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("p_tysah", tysah);
        map.put("p_cur", "");
        sfdacjMapper.selectajbytysah(map);
        List<SFDAAJ> list = (List<SFDAAJ>) map.get("p_cur");
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list;
    }


    /**
     * 根据所属人,所属人单位编码查询档案归档
     */
    @Override
    public List<DAGZGD> selectdassrdwbm(DAGZGD dagzgd) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("p_ssr", dagzgd.getSsr());
        map.put("p_ssrdwbm", dagzgd.getSsrdwbm());
        map.put("p_id", dagzgd.getId());
        map.put("p_sffc", dagzgd.getSffc());
        try {
        	sfdacjMapper.selectdassrdwbm(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
        List<DAGZGD> list = (List<DAGZGD>) map.get("p_cur");
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list;
    }

    /**
     * 办案质量查询
     *
     * @param bmsah
     * @return
     */
    @Override
    public List<JCGDAAJZL> getBazl(String bmsah) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("p_bmsah", bmsah);
        map.put("p_cur", "");
        sfdacjMapper.getBazl(map);
        if ("".equals(map.get("p_cur"))) {
            return null;
        }
        List<JCGDAAJZL> list = (List<JCGDAAJZL>) map.get("p_cur");
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list;
    }

    /**
     * 办案质量修改
     *
     * @param
     * @return
     */
    @Override
    public String updateBazl(JCGDAAJZL jc) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("Y", "");
        map.put("p_bmsah", jc.getBmsah());
        map.put("p_baxg", jc.getBaxg());
        map.put("p_wfxszqqk", jc.getWfxszqqk());
        map.put("p_baaq", jc.getBaaq());
        map.put("p_sfxc", jc.getSfxc());
        map.put("p_ajzlpcjg", jc.getAjzlpcjg());
        sfdacjMapper.updateBazl(map);
        String y = (String) map.get("Y");
        if ("".equals(y)) {
            //日志记录
            logService.error(Constant.CURRENT_USER.get().getDwbm(),
                    Constant.CURRENT_USER.get().getGh(),
                    Constant.CURRENT_USER.get().getMc(),
                    Constant.RZLX_CWRZ, "修改办案质量失败");
            return "0";

        } else {
            //日志记录
            logService.info(Constant.CURRENT_USER.get().getDwbm(),
                    Constant.CURRENT_USER.get().getGh(),
                    Constant.CURRENT_USER.get().getMc(),
                    Constant.RZLX_CZRZ, "修改办案质量");
        }
        return y;
    }

    /**
     * 得到嫌疑人列表
     *
     * @param pbmsah 部门受案号
     * @param pxyrbh 嫌疑人编号
     * @return
     */
    @Override
    public List<DACJAJXYRXX> selectXyrDetail(String pbmsah, String pxyrbh) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("p_bmsah", pbmsah);
        map.put("p_xyrbh", pxyrbh);
        map.put("p_cursor", null);
        sfdacjMapper.selectXyrDetail(map);
        return (List<DACJAJXYRXX>) map.get("p_cursor");
    }

    @Override
    public List<SFDAAJ> getZbAjList(String curentpage, String pagesize,
                                    String cbrgh, String dwbm, String kssj, String jssj) {
        int start = (Integer.valueOf(curentpage) - 1) * Integer.valueOf(pagesize);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("cbrgh", cbrgh);
        map.put("dwbm", dwbm);
        map.put("kssj", kssj);
        map.put("jssj", jssj);
        map.put("start", start);
        map.put("end", start + 1 + Integer.valueOf(pagesize));
        List<SFDAAJ> list = null;
        try {
            list = sfdacjMapper.getZbAjList(map);
        } catch (Exception e) {
            LOG.error(StringUtils.EMPTY, e);
        }
        return list;
    }

    @Override
    public List<SFDAAJ> getCbAjNum(String cbrgh, String dwbm, String kssj, String jssj) {
        try {
            return sfdacjMapper.getCbAjNum(cbrgh, dwbm, kssj, jssj);
        } catch (Exception e) {
            return ListUtils.EMPTY_LIST;
        }
    }

    @Override
    public List<SFDAAJ> getCbAj(String curentpage, String pagesize,
                                String cbrgh, String dwbm, String kssj, String jssj) {
        try {
            int size = Integer.parseInt(pagesize);
            int curent = Integer.parseInt(curentpage);
            int start = (curent - 1) * size;
            int end = start + size + 1;
            return sfdacjMapper.getCbAj(start, end, cbrgh, dwbm, kssj, jssj);
        } catch (Exception e) {
            return ListUtils.EMPTY_LIST;
        }
    }

    @Override
    public Boolean getFileCreater(String dwbm, String gh) {
        try {
            int flag = sfdacjMapper.getFileCreater(dwbm, gh);
            if (flag > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String uniteCreate(String dwbm, String xm, String gh, String cjr) {

        // 名称
        String[] mc = xm.split(",");
        // 工号
        String[] ssr = gh.split(",");
        // 档案开始时间，当前年01-01
        String kssj = DateUtil.getYear(new Date()) + "-01";
        // 档案结束时间，当前年12-31
        String jssj = DateUtil.getYear(new Date()) + "-12";

        Map<String, Object> map = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        String tjnf = sdf.format(new Date());
        String result = "";
        StringBuilder sb = new StringBuilder();

        try {
            for (int i = 0; i < ssr.length; i++) {
                // 要创建的档案id
                String daId = Identities.get32LenUUID();
                map.put("id", daId);
                map.put("ssr", ssr[i]);
                map.put("tjnf", tjnf);
                map.put("cjr", cjr);
                map.put("ssrdwbm", dwbm);
                map.put("cjrdwbm", dwbm);
                map.put("kssj", kssj);
                map.put("jssj", jssj);

                // 根据单位编码，工号，档案开始时间，结束时间查询是否有当前年份的档案
                int counts = sfdacjMapper.getInfoOfPer(dwbm, ssr[i], kssj, jssj);
                if (counts > 0) {
                    // 如果档案存在，返回此人的名字
                    sb = sb.append(mc[i] + ",");
                } else {
                    // 创建档案
                    sfdacjMapper.uniteCreate(map);

                    //查询当前人员的所有部门编码
                    List<Map<String, Object>> allBmbmList = sfdacjMapper.getAllBmbm(dwbm, ssr[i]);
                    StringBuilder bmbmsStr = new StringBuilder();
                    for (Map<String, Object> bmbmMap : allBmbmList) {
                        bmbmsStr.append(bmbmMap.get("BMBM") + ",");
                    }
                    // 去除最后一个,
                    bmbmsStr.deleteCharAt(bmbmsStr.length() - 1);

                    // 根据单位编码，工号去查询最近一次的档案信息，如果存在，将其覆盖至新档案，如果不存在则去批量导入的表中获取
                    Person recentlyGrxx = personMapper.selectInfo(dwbm, ssr[i]);
                    if (!Objects.isNull(recentlyGrxx)) {
                        Person person = new Person();
                        person.setDaId(daId);
                        person.setDwbm(recentlyGrxx.getDwbm());
                        person.setBmbm(bmbmsStr + "");
                        person.setGh((String) recentlyGrxx.getGh());
                        person.setGender((String) recentlyGrxx.getGender());
                        person.setZzmm((String) recentlyGrxx.getZzmm());
                        person.setWhcd((String) recentlyGrxx.getWhcd());
                        person.setBirthday((String) recentlyGrxx.getBirthday());
                        person.setSflb((String) recentlyGrxx.getSflb());
                        person.setGrade((String) recentlyGrxx.getGrade());
                        person.setPostInfo((String) recentlyGrxx.getPostInfo());
                        person.setAdminRank((String) recentlyGrxx.getAdminRank());
                        person.setReDate((String) recentlyGrxx.getReDate());
                        person.setXw((String) recentlyGrxx.getXw());

                        //导入个人信息
                        Map<String,Object> savedGrxx = personService.insertGrjbxxData(person);
                        // 刚导入的个人基本信息id
                        String grxxId = (String) savedGrxx.get("id");
                        // 只有当个人基本信息导入成功后才允许导入个人经历
                        if (!StringUtils.isEmpty(grxxId)) {

                            // 读取个人照片并插入
                            if (StringUtils.isNotEmpty(recentlyGrxx.getZp())) {
                                Integer res = 0;
                                try {
                                    byte[] grzp = recentlyGrxx.getZp().getBytes();
                                    res = personMapper.modifySfdaGrjbxxZpById(grzp,recentlyGrxx.getZpName(),grxxId);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    LOG.error("照片存入数据库错误！", e);
                                    //记录日志
                                    logService.error(Constant.CURRENT_USER.get().getDwbm(),Constant.CURRENT_USER.get().getGh(),Constant.CURRENT_USER.get().getMc(),Constant.RZLX_CWRZ, e.toString());
                                }
                            }

                            // 最近档案对应个人基本信息的id
                            String recentlyGrxxId = (String) recentlyGrxx.getId();
                            // 最近档案的档案id
                            String recentlyDaId = (String) recentlyGrxx.getDaId();
                            //根据查询出的最近的个人基本信息的id和da_id去查询对应的工作经历和教育经历
                            // 工作经历，可能有多个
                            List<Map<String,Object>> recentlyExpOfWork = personMapper.selectExpOfWork(recentlyDaId, recentlyGrxxId);
                            // 学习经历，可能有多个
                            List<Map<String,Object>> recentlyExpOfStudy = personMapper.selectExpOfStudy(recentlyDaId, recentlyGrxxId);
                            if (!Objects.isNull(recentlyExpOfWork)) {
                                for (Map<String, Object> expOfWorkMap : recentlyExpOfWork) {
                                    // 工作经历
                                    PersonJl expOfWork = new PersonJl();
                                    expOfWork.setPersonId(grxxId);
                                    expOfWork.setsDate((String) expOfWorkMap.get("S_DATE"));
                                    expOfWork.seteDate((String) expOfWorkMap.get("E_DATE"));
                                    expOfWork.setAdress((String) expOfWorkMap.get("ADRESS"));
                                    expOfWork.setType("1");
                                    expOfWork.setDaId(daId);
                                    expOfWork.setGzbm((String) expOfWorkMap.get("GZBM"));
                                    expOfWork.setGzzz((String) expOfWorkMap.get("GZZZ"));

                                    //导入工作经历
                                    personService.insertGrjlData(expOfWork);
                                }
                            }

                            if (!Objects.isNull(recentlyExpOfStudy)) {
                                for (Map<String, Object> expOfStudyMap : recentlyExpOfStudy) {
                                    // 学习经历
                                    PersonJl expOfStudy = new PersonJl();
                                    expOfStudy.setPersonId(grxxId);
                                    expOfStudy.setsDate((String) expOfStudyMap.get("S_DATE"));
                                    expOfStudy.seteDate((String) expOfStudyMap.get("E_DATE"));
                                    expOfStudy.setName((String) expOfStudyMap.get("NAME"));
                                    expOfStudy.setAdress((String) expOfStudyMap.get("ADRESS"));
                                    expOfStudy.setZwName((String) expOfStudyMap.get("ZW_NAME"));
                                    expOfStudy.setType("2");
                                    expOfStudy.setDaId(daId);

                                    //导入学习经历
                                    personService.insertGrjlData(expOfStudy);
                                }
                            }
                        }

                    } else {
                        // 没有最近个人信息
                        // 读取批量导入的个人信息和个人经历
                        Map<String, Object> grjbxx = sfdaGrxxPldrMapper.selectGrjbxxBydg(dwbm, ssr[i]);
                        if (!Objects.isNull(grjbxx)) {
                            Person person = new Person();
                            person.setDaId(daId);
                            person.setDwbm((String) grjbxx.get("DWBM"));
                            person.setBmbm(bmbmsStr + "");
                            person.setGh((String) grjbxx.get("GH"));
                            person.setGender((String) grjbxx.get("GENDER"));
                            person.setZzmm((String) grjbxx.get("ZZMM"));
                            person.setWhcd((String) grjbxx.get("WHCD"));
                            person.setBirthday((String) grjbxx.get("BIRTHDAY"));
                            person.setSflb((String) grjbxx.get("SFLB"));
                            person.setGrade((String) grjbxx.get("GRADE"));
                            person.setPostInfo((String) grjbxx.get("POSTINFO"));
                            person.setAdminRank((String) grjbxx.get("ADMINRANK"));
                            person.setReDate((String) grjbxx.get("REDATE"));
                            person.setXw((String) grjbxx.get("XW"));

                            //导入个人信息
                            Map<String,Object> savedGrxx = personService.insertGrjbxxData(person);
                            // 个人基本信息id
                            String grxxId = (String) savedGrxx.get("id");
                            // 只有当个人基本信息导入成功后才允许导入个人经历
                            if (!StringUtils.isEmpty(grxxId)) {
                                // 查询批量导入的个人经历
                                List<Map<String, Object>> grjl = sfdaGrxxPldrMapper.selectGrjlBydg(dwbm,ssr[i]);
                                for (Map<String, Object> grjlMap : grjl) {
                                    // 学习经历
                                    PersonJl expOfStudy = new PersonJl();
                                    expOfStudy.setPersonId(grxxId);
                                    expOfStudy.setsDate((String) grjlMap.get("JYKSSJ"));
                                    expOfStudy.seteDate((String) grjlMap.get("JYJSSJ"));
                                    expOfStudy.setName((String) grjlMap.get("XXMC"));
                                    expOfStudy.setAdress((String) grjlMap.get("XXDZ"));
                                    expOfStudy.setZwName((String) grjlMap.get("DRZZ"));
                                    expOfStudy.setType("2");
                                    expOfStudy.setDaId(daId);

                                    // 工作经历
                                    PersonJl expOfWork = new PersonJl();
                                    expOfWork.setPersonId(grxxId);
                                    expOfWork.setsDate((String) grjlMap.get("GZKSSJ"));
                                    expOfWork.seteDate((String) grjlMap.get("GZJSSJ"));
                                    expOfWork.setAdress((String) grjlMap.get("GZDD"));
                                    expOfWork.setType("1");
                                    expOfWork.setDaId(daId);
                                    expOfWork.setGzbm((String) grjlMap.get("GZBM"));
                                    expOfWork.setGzzz((String) grjlMap.get("GWZZ"));

                                    //导入学习经历
                                    personService.insertGrjlData(expOfStudy);
                                    //导入工作经历
                                    personService.insertGrjlData(expOfWork);
                                }
                            }
                        }
                    }

                    result = "success";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (sb.length() > 0) {
            // 去除最后一个,
            sb.deleteCharAt(sb.length() - 1);
            return sb + "";
        } else {
            return result;
        }
    }


}
