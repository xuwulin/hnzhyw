package com.swx.ibms.business.appraisal.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.swx.ibms.business.appraisal.bean.YWGZPFCL;
import com.swx.ibms.business.appraisal.bean.Ywgzzltb;
import com.swx.ibms.business.appraisal.bean.Ywlxkh;
import com.swx.ibms.business.appraisal.bean.YwlxkhZgjcf;
import com.swx.ibms.business.appraisal.mapper.YwlxkhMapper;
import com.swx.ibms.business.cases.service.AjxxcxService;
import com.swx.ibms.business.common.utils.Constant;
import com.swx.ibms.business.common.utils.OutNdkhExcel;
import com.swx.ibms.business.rbac.bean.DWBM;
import com.swx.ibms.business.system.service.DataDictService;
import com.swx.ibms.business.system.service.XTGLService;
import com.swx.ibms.business.system.service.XTGLServiceImpl;
import com.swx.ibms.common.utils.DateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.Map.Entry;


/**
 * <p>
 * Title:YwlxkhServiceImpl 业务类型考核的服务
 * </p>
 * <p>
 * Description:
 * </p>
 * author 朱春雨 date 2017年6月5日 下午7:19:11
 */
@Service("YwlxkhService")
@SuppressWarnings("all")
public class YwlxkhServiceImpl implements YwlxkhService {
	
	/**
	 * 日志对象
	 * */
	private static final Logger LOG = Logger.getLogger(XTGLServiceImpl.class);
	
    /**
     * 业务类型考核
     */
    @Resource
    private YwlxkhMapper ywlxkhMapper;
    /**
     * 系统功能service
     */
    @Resource
    private XTGLService xtglservice;
    /**
     * 数据字典
     */
    @Resource
    private DataDictService dataDictService;

    /**
     * 案件信息【统一业务同步表案件基本信息】
     */
    @Resource
    private AjxxcxService ajxxService;

    
    
    
    
    /**
     * 得到业务类型考核分值数据
     */
    @Override
    public List<Ywlxkh> getYwlxB(String khlx, String dwbm, String year) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("p_khlx", khlx);
        map.put("p_dwbm", dwbm);
        map.put("p_year", year);
        map.put("p_cursor", null);
        ywlxkhMapper.selectYwlxkh(map);
        return (List<Ywlxkh>) map.get("p_cursor");
    }

    /**
     * 得到材料
     */
    @Override
    public YWGZPFCL getcl(String ywlxkhfzid, String lx, String zbxbm) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("p_ywlxkhfzid", ywlxkhfzid);
        map.put("p_lx", lx);
        map.put("p_zbxbm", zbxbm);
        YWGZPFCL ywgzpfcl = null;
        try {
            ywlxkhMapper.getcl(map);
            List<YWGZPFCL> list = ((List<YWGZPFCL>) map.get("p_cursor"));
            if (list.size() > 0) {
                ywgzpfcl = list.get(0);
            } else {
                ywlxkhMapper.upDatePfcl(map);
            }

        } catch (Exception e) {
            // TODO: handle exception
        }

        return ywgzpfcl;
    }

    /**
     * 得到材料
     */
    @Override
    public YWGZPFCL getcl2(String ywlxkhfzid, String lx, String zbxbm) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("p_ywlxkhfzid", ywlxkhfzid);
        map.put("p_lx", lx);
        map.put("p_zbxbm", zbxbm);
        YWGZPFCL ywgzpfcl = null;
        try {
            ywlxkhMapper.getcl2(map);
            List<YWGZPFCL> list = ((List<YWGZPFCL>) map.get("p_cursor"));
            if (list.size() > 0) {
                ywgzpfcl = list.get(0);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }

        return ywgzpfcl;
    }


    /**
     * 保存评分材料
     */
    @Override
    public String updatabz(String dwbm, String year, String khlx, String pflx,
                           String zbxbm, String fjxx, String bz) {
        String Y = "0";
        try {
            List<Ywlxkh> Ywlxkh = this.getYwlxB(khlx, dwbm, year);
            String ywlxkhfzid = Ywlxkh.get(0).getYwlxkhfzid();

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("p_ywlxkhfzid", ywlxkhfzid);
            map.put("p_lx", pflx);
            map.put("p_zbxbm", zbxbm);
            map.put("p_fjxx", fjxx);
            map.put("p_bz", bz);

            ywlxkhMapper.upDatePfcl(map);
            Y = "1";
        } catch (Exception e) {
            Y = "0";

        }
        return Y;
    }

    /**
     * 更新业务类型考核分值表里的数据
     */
    @Override
    public String updateYwlxkhfz(String ywlxkhid, String khlx, double ywzf, String zbkpgl, String zbkpdf) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("p_ywlxkhid", ywlxkhid);
        map.put("p_khlx", khlx);
        map.put("p_ywzf", ywzf);
        map.put("p_zbkpgl", zbkpgl);
        map.put("p_zbkpdf", zbkpdf);
        String Y = "0";
        try {
            ywlxkhMapper.updateYwlxkhfz(map);
            Y = "1";
        } catch (Exception e) {
            Y = "0";
        }
        return Y;
    }

    /**
     * 更新或插入最高基础分
     */
    @Override
    public String updateZgjcf(String year, String khlx, String dwjb, String xmbm, double jcf) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("p_year", year);
        map.put("p_khlx", khlx);
        map.put("p_dwjb", dwjb);
        map.put("p_xmbm", xmbm);
        map.put("p_jcf", jcf);
        String Y = "0";
        try {
            ywlxkhMapper.updateZgjcf(map);
            Y = "1";
        } catch (Exception e) {
            Y = "0";
        }
        return Y;
    }

    /**
     * @param dwbm 单位编码
     * @param year 年份
     * @param khlx 考核类型
     * @return 指标考评得分和最高评价分
     */
    @Override
    public Map<String, Object> selectZbkpglZbkpdfZgpjf(String dwbm, String year, String khlx) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("zbkpgl", null);
        result.put("zbkpdf", null);
        result.put("zgpjf", null);
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("p_dwbm", dwbm);
            map.put("p_year", year);
            map.put("p_khlx", khlx);
            List<DWBM> dwbmlist = xtglservice.getDw(dwbm);
            map.put("p_dwjb", dwbmlist.get(0).getDwjb());
            map.put("p_zbkpgl", null);
            map.put("p_zbkpdf", null);
            map.put("p_zgpjf", null);
            ywlxkhMapper.selectKpglZbkpdfZgpjf(map);  //这儿只是为了查询到分值id对应的那条数据的指标考评分值信息 以及最高基础分
            result.put("zbkpgl", map.get("p_zbkpgl"));
            result.put("zbkpdf", map.get("p_zbkpdf"));
            result.put("zgpjf", map.get("p_zgpjf"));
        } catch (Exception e) {
            throw e;
        }
        return result;
    }

    /**
     * @param year 年份
     * @param khlx 考核类型
     * @param dwjb 单位级别
     * @return 查询最高基础分list
     */
    @Override
    public List<YwlxkhZgjcf> selectZgjcf(String year, String khlx, String dwjb) {
        List<YwlxkhZgjcf> list = null;
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("p_year", year);
            map.put("p_khlx", khlx);
            map.put("p_dwjb", dwjb);
            ywlxkhMapper.selectZgjcf(map);
            list = (List<YwlxkhZgjcf>) map.get("p_cursor");
        } catch (Exception e) {
            throw e;
        }
        return list;
    }

    /**
     * 算出评价分后插入pjf
     * @param khfzid 
     */
    @Override
    public String insertpjf(String dwbm, String year, String khlx, String khfzid) {
        String Y = "0";
        try {

            //1、获取分值id对应的zbkpdf(指标考评得分)、zgpjf(最高评价分)；牵扯的表有：yx_ywgz_ywlxkhfz、yx_ywgz_ywlxkh
//            Map szzz = this.selectZbkpglZbkpdfZgpjf(dwbm, year, khlx);
        	Map szzz = new HashMap();
        	if(!StringUtils.isBlank(khfzid)){
        		Map<String, Object> fzMap = new HashMap<String, Object>(Constant.NUM_7);
                fzMap.put("p_khfzid", khfzid);
                fzMap.put("p_zbkpdf", StringUtils.EMPTY);
                fzMap.put("p_zgpjf", StringUtils.EMPTY);
                fzMap.put("p_dwbm", StringUtils.EMPTY);
                fzMap.put("p_kssj", StringUtils.EMPTY);
                fzMap.put("p_jssj", StringUtils.EMPTY);
                fzMap.put("p_year", StringUtils.EMPTY);
            	ywlxkhMapper.getKpglZbkpdfZgpjf(fzMap);
            	szzz = fzMap;
        	}else{
        		szzz = this.selectZbkpglZbkpdfZgpjf(dwbm, year, khlx);
        	}
        	
            List<DWBM> dwbmlist = xtglservice.getDw(dwbm);
            String dwjb = dwbmlist.get(0).getDwjb();// 单位级别

            String zbkpgl = (String) szzz.get("p_zbkpgl");// 指标考评概览
            JsonArray zbkpgljsonArray = (JsonArray) Constant.JSON_PARSER.parse(zbkpgl);

            String zbkpdf = (String) szzz.get("p_zbkpdf");// 指标考评得分
            JsonObject zbkpdfjson = (JsonObject) Constant.JSON_PARSER.parse(zbkpdf);
            JsonArray zbkpdfvalueJson = (JsonArray) zbkpdfjson.get("value");

            String zgpjfstr = (String) szzz.get("p_zgpjf");// 最高评价分
            JsonArray zgpjfjsonArray = (JsonArray) Constant.JSON_PARSER.parse(zgpjfstr);

            //2、查询最高基础分list；牵扯表有：yx_ywgz_ywlxkh、yx_ywgz_zgjcf
            List<YwlxkhZgjcf> zgjcflist = this.selectZgjcf(year, khlx, dwjb);// 最高基础分list
            int zgjcflength = zgjcflist.size();
            JsonArray pjdfvalue = new JsonArray();

            // 总评价得分
            Double zpjdf = 0.0;

            for (int i = 0; i < zgjcflength; i++) {
                JsonObject jo = (JsonObject) zgpjfjsonArray.get(i);
                Iterator iter = jo.entrySet().iterator();
                Map.Entry e = (Entry) iter.next();
                // 项目编码
                String xmbm = e.getKey().toString();
                // 最高评价分
                String temp = e.getValue().toString().replaceAll("\"", "").trim();
                Double zgpjf = Double.valueOf(temp);
                // 基础分
                Double jcf = zbkpdfvalueJson.get(i).getAsDouble();
                
                // 最高基础分
                Double zgjcf = zgjcflist.get(i).getZgjcf();
                // 评价得分
                Double pjdf = 1.0;
                if (zgjcf == 0.0) {
                    pjdf = zgpjf;
                } else {
                    pjdf = zgpjf / zgjcf * jcf;
                }

                BigDecimal bg = new BigDecimal(pjdf);
                pjdf = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                for (int j = 0; j < zbkpgljsonArray.size(); j++) {
                    JsonObject zbkpglvalueJsonI = ((JsonObject) zbkpgljsonArray.get(j));
                    String zbkpgxmbm = zbkpglvalueJsonI.get("xmbm").getAsString();

                    if (zbkpgxmbm.equals(xmbm)) {
                        zbkpglvalueJsonI.add("pjdf", new JsonPrimitive(pjdf));
                    }
                }
                pjdfvalue.add(new JsonPrimitive(pjdf));
                zpjdf = zpjdf + pjdf;
            }
            double khf = 0.0;

            //3、取出数据字典表中的权重值
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("P_KHLX", khlx);
            map.put("P_DICIDEN", Constant.YWKH_QZ_KEY);
            String qz = dataDictService.selectqz(map);

            double qzz = Double.parseDouble(qz) / 100;
            if ("01".equals(khlx)) {
                khf = zpjdf * qzz;
            } else if ("02".equals(khlx)) {
                khf = zpjdf * qzz;
            } else if ("03".equals(khlx)) {
                khf = zpjdf * qzz;
            } else if ("04".equals(khlx)) {
                khf = zpjdf * qzz;
            } else if ("05".equals(khlx)) {
                khf = zpjdf * qzz;
            } else if ("06".equals(khlx)) {
                khf = zpjdf * qzz;
            } else if ("07".equals(khlx)) {
                if (("3").equals(dwjb)) {
                    khf = (zpjdf * 0.6 + 40) * qzz;
                } else {
                    khf = (zpjdf * 0.5 + 50) * qzz;
                }
            } else if ("08".equals(khlx)) {
                khf = zpjdf * qzz;
            } else if ("09".equals(khlx)) {
                khf = zpjdf * qzz;
            } else if ("10".equals(khlx)) {
                khf = zpjdf * qzz;
            } else if ("11".equals(khlx)) {
                khf = zpjdf * qzz;
            } else if ("12".equals(khlx)) {
                khf = zpjdf * qzz;
            } else if ("13".equals(khlx)) {
                khf = zpjdf * qzz;
            }
            zbkpdfjson.add("pjdfvalue", pjdfvalue);
            Map<String, Object> resultmap = new HashMap<String, Object>();
            resultmap.put("p_dwbm", dwbm);
            resultmap.put("p_year", year);
            resultmap.put("p_khlx", khlx);
            resultmap.put("p_zbkpgl", zbkpgljsonArray.toString());
            resultmap.put("p_zbkpdf", zbkpdfjson.toString());
            resultmap.put("p_zpjdf", zpjdf);
            resultmap.put("p_khf", khf);
            //4、修改分值id对应的数据信息
            ywlxkhMapper.pjdfUpdateYwlxkhfz(resultmap);
            Y = "1";
        } catch (Exception e) {
            // TODO: handle exception
        	e.printStackTrace();
            Y = "0";
        }
        return Y;
    }

    /**
     * 更新该单位级别的最高基础分后，把该单位级别中审批通过的单位评价分依次插入
     * <p>
     * 大体思路：
     * 1、查询到当前考核分值id所对应的那一条的填分项的信息
     * @param khfzid 
     */
    @Override
    public String updatezjcftoinsertpjf(String dwbm, String year, String khlx, String khfzid) {
        String Y = "0";
        try {
            //1、获取分值id对应的zbkpdf(指标考评得分)、zgpjf(最高评价分)；牵扯的表有：yx_ywgz_ywlxkhfz、yx_ywgz_ywlxkh
            Map fzMap = this.selectZbkpglZbkpdfZgpjf(dwbm, year, khlx);

            List<DWBM> dwbmlist = xtglservice.getDw(dwbm);
            String dwjb = dwbmlist.get(0).getDwjb();// 单位级别

            String zbkpdf = (String) fzMap.get("zbkpdf");// 指标考评得分
            JsonObject zbkpdfjson = (JsonObject) Constant.JSON_PARSER.parse(zbkpdf);

            JsonArray zbkpdfvalueJson = (JsonArray) zbkpdfjson.get("value");
            int maxlength = zbkpdfvalueJson.size();

            String zgpjfstr = (String) fzMap.get("zgpjf");// 最高评价分
            JsonArray zgpjfjsonArray = (JsonArray) Constant.JSON_PARSER.parse(zgpjfstr);

            for (int i = 0; i < maxlength; i++) {// 根据项目编码依次更新最高基础分
                JsonObject jo = (JsonObject) zgpjfjsonArray.get(i);
                Iterator iter = jo.entrySet().iterator();
                Map.Entry e = (Entry) iter.next();
                String xmbm = (String) e.getKey();
                //2、更新或插入最高基础分；牵扯的表：yx_ywgz_ywlxkh、yx_ywgz_zgjcf、
                updateZgjcf(year, khlx, dwjb, xmbm, zbkpdfvalueJson.get(i).getAsDouble());
            }

            //3、通过单位级别和单位编码的前两位查出该省的此单位级别的单位信息；牵扯的表有：xt_zzjg_dwbm_tyyw
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("p_dwjb", dwjb);
            map.put("p_dwtwo", dwbm.substring(0, 2));
            ywlxkhMapper.dwjbDwtwoDwbm(map);
            List<DWBM> dwbmlist1 = (List<DWBM>) map.get("p_cursor");
            int dwbmListSize = dwbmlist1.size();
            for (int i = 0; i < dwbmListSize; i++) {
                String dwbmI = dwbmlist1.get(i).getDwbm();
                //4、通过业务类型、单位编码、年份查找业务类型考核的数据；牵扯的表有：yx_ywgz_ywlxkhfz、yx_ywgz_ywlxkh、xt_ywgz_khlxpz、、
                List<Ywlxkh> ywlxkhfz = this.getYwlxB(khlx, dwbmI, year);
                if (ywlxkhfz.size() != 0) {
                    String spsftg = ywlxkhfz.get(0).getSpsftg();
                    if ("1".equals(spsftg)) {
                        //5、更新分值id对应的分值信息；牵扯的表有：
                    	if(!StringUtils.isBlank(khfzid)){
                    		Y = this.insertpjf(dwbmI, year, khlx,khfzid);
                    	}else{
                    		Y = this.insertpjf(dwbmI, year, khlx,StringUtils.EMPTY);
                    	}
                        
                        if ("0".equals(Y)) {
                            return Y;
                        }
                    }
                }
            }
            Y = "1";
        } catch (Exception e) {
            // TODO: handle exception
        	e.printStackTrace();
            Y = "0";
        }
        return Y;

    }

    @Override
    public String countPjdf(String khfzid, String khlx) {
        String Y = "0";
        /**
         * 1、根据分值id查询到该分值id对应的填写分值信息
         * 2、将分值信息带入计算办法的方法里进行计算
         * 3、再将计算好的数据信息更新此分值id的分值信息
         */

        //暂未梳理清楚 先注释掉
        try {
        	
        	Map<String, Object> fzMap = new HashMap<String, Object>(Constant.NUM_7);
            fzMap.put("p_khfzid", khfzid);
            fzMap.put("p_zbkpdf", StringUtils.EMPTY);
            fzMap.put("p_zgpjf", StringUtils.EMPTY);
            fzMap.put("p_dwbm", StringUtils.EMPTY);
            fzMap.put("p_kssj", null);
            fzMap.put("p_jssj", null);
            fzMap.put("p_year", StringUtils.EMPTY);
            ywlxkhMapper.getKpglZbkpdfZgpjf(fzMap);
            String dwbm = (String) fzMap.get("p_dwbm");
            Date kssj = (Date) fzMap.get("p_kssj"); //记得转化为日期格式
            Date jssj = (Date) fzMap.get("p_jssj");
            String year = (String) fzMap.get("p_year");

            Y = this.updatezjcftoinsertpjf(dwbm, year, khlx,khfzid);
        	
          /* Map<String,Object> fzMap = ywlxkhMapper.getYwkhfzInfoById(khfzid);
            if(fzMap!=null&&!fzMap.isEmpty()&&fzMap.size()>0) {
            	for (String fzKey : fzMap.keySet()) {
					System.out.println("==="+fzKey+"------"+fzMap.get(fzKey));
					
				}
            	
            	String dwjb = fzMap.get("DWJB").toString();
            	Date kssj = DateUtil.stringtoDate(fzMap.get("KSSJ").toString(),"yyyy-MM-dd");
            	Date jssj = DateUtil.stringtoDate(fzMap.get("JSSJ").toString(),"yyyy-MM-dd");
            	Integer version = Integer.parseInt(fzMap.get("VERSION").toString());
            	String fzkhlx = fzMap.get("KHLX").toString();
            	Clob zbkpnrClob = (Clob)fzMap.get("ZBKPGL");  //考核指标内容
            	if(null != zbkpnrClob) {
            		String zbkpnrStr = ClobUtil.clobToString(zbkpnrClob);
            		JSONArray zbkpnrJsonArr = JSONArray.parseArray(zbkpnrStr); //System.out.println(zbkpnrJsonArr.toJSONString());
            		Iterator it = zbkpnrJsonArr.iterator();
            		
            		while(it.hasNext()){
            			JSONObject jsonObj = (JSONObject)it.next();
            			System.out.println("==="+jsonObj);
            			String xmbmTemp = StringUtils.EMPTY;  //临时项目编码
            			String xmfzTemp = StringUtils.EMPTY;  //临时该考核子项目最高分
            			String agpfTemp = StringUtils.EMPTY;  //临时填录案件数量
            			String cxfzTemp = "1.00";  //临时此项分值【默认1.00】
            			if(null!=jsonObj) {
            				if(StringUtils.isNotBlank(jsonObj.get("xmbm").toString())) {
                				xmbmTemp = jsonObj.get("xmbm").toString();
                			}
            				if(StringUtils.isNotBlank(jsonObj.get("xmfz").toString())) { 
            					xmfzTemp = jsonObj.get("xmfz").toString();
            				}
            				if(StringUtils.isNotBlank(jsonObj.get("agpf").toString())) {
            					agpfTemp = jsonObj.get("agpf").toString();
            				}
            				if(StringUtils.isNotBlank(jsonObj.get("cxfz").toString())) {
            					cxfzTemp = jsonObj.get("cxfz").toString();
            				}
            			}
            			Double jcffz = AjCountCommon.commonJcfCount(agpfTemp, cxfzTemp, xmfzTemp, Constant.NUM_4);
            			
            			Integer zgfCount = 0 ;
            			//查询最高基础分表有无此类数据，无则添加，有则修改【yx_ywgz_zgjcf】
            			try {
            				zgfCount = ywlxkhMapper.getZgjcfCount(dwjb,fzkhlx,xmbmTemp,kssj,jssj);
						} catch (Exception e) {
							e.printStackTrace();
						}
            			if(zgfCount>0) { //修改【id、year、khlx、dwjb、xmbm、zgjcf、cjsj、version、kssj、jssj】
            				try {
                				ywlxkhMapper.modifyZgjcfTableInfo(fzkhlx,dwjb,xmbmTemp,jcffz,kssj,jssj);
    						} catch (Exception e) {
    							e.printStackTrace();
    						}
            			}else { //添加【id、year、khlx、dwjb、xmbm、zgjcf、cjsj、version、kssj、jssj】
            				try {
                				ywlxkhMapper.addZgjcfTableInfo(Identities.get32LenUUID(),StringUtils.EMPTY,fzkhlx,dwjb,xmbmTemp,jcffz,new Date(),kssj,jssj);
    						} catch (Exception e) {
    							e.printStackTrace();
    						}
            			}
            		}
            	}
            	
            	//计算评价分并存入分数考核指标表
//        		if(Integer.parseInt(dwjb)>Constant.NUM_3) { //基层院
//        			ywlxkhMapper.getZgjcfTableInfo(dwjb,fzkhlx,xmbmTemp,kssj,jssj);
//        		}else if(Integer.parseInt(dwjb)>Constant.NUM_2&&Integer.parseInt(dwjb)<Constant.NUM_4) { //市院
//        			
//        		}
            	
            	Clob zbkpdfClob = (Clob) fzMap.get("ZBKPDF");// 最高评价分
            	if(null!=zbkpdfClob) {
            		String zbkpdfStr = ClobUtil.clobToString(zbkpnrClob);	
            		
            	}
            }*/
            
        } catch (Exception e) {
            // TODO: handle exception
        	e.printStackTrace();
        }
        return Y;

    }

    @Override
    public JsonArray getkhzl(String year, String dwbm, String khlx, String sfby) {
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> tempmap = new HashMap<String, Object>();

        map.put("p_dwbm", dwbm);
        map.put("p_year", year);
        map.put("p_khlx", khlx);
        map.put("p_sfby", sfby);//是否本院 N 否  Y 是

        map.put("p_cur", "");

        ywlxkhMapper.getkhzl(map);

        List<Ywgzzltb> templist = (List<Ywgzzltb>) map.get("p_cur");

        //组装(业务工作)json数组
//		JsonArray jsonarray = new JsonArray();

        return this.zzJsonArray(templist, tempmap);
    }

    @Override
    public JsonArray getkhzl(String year, String dwbm, String sfby) {
        return getkhzl(year, dwbm, "", sfby);
    }

    @Override
    public JsonArray selectDwkhByDwbmAndYear(Map<String, Object> map) {

        map.put("p_cur", StringUtils.EMPTY);
        ywlxkhMapper.selectDwkhByDwbmAndYear(map);

        List<Ywgzzltb> templist = (List<Ywgzzltb>) map.get("p_cur");

//		JsonArray jsonarray = new JsonArray();

        //组装(业务工作)json数组
        //jsonarray =

        return zzJsonArray(templist, map);
    }

    /**
     * 组装jsonArray
     *
     * @param templist 业务工作总览表头集合
     * @param tempmap  map集合
     * @return jsonArray数组
     */
    private JsonArray zzJsonArray(List<Ywgzzltb> templist,
                                  Map<String, Object> tempmap) {

        JsonArray jsonarray = new JsonArray();
        DecimalFormat doubleFormat = new DecimalFormat("0.00");

        if (!CollectionUtils.isEmpty(templist)) {
            for (Ywgzzltb y : templist) {

                JsonObject tjson = (JsonObject) tempmap.get(y.getKhid());
                //添加记录
                if (tjson == null) {
                    tjson = new JsonObject();
                    //添加基本数据
                    tjson.add("ScoreArray", new JsonArray());
                    tjson.add("khsj", new JsonArray());
                    tjson.addProperty("khid", y.getKhid());
                    tjson.addProperty("year", y.getYear());
                    tjson.addProperty("dwbm", y.getDwbm());
                    tjson.addProperty("dwmc", y.getDwmc());
                    tjson.addProperty("yyzt", y.getYyzt());
                    tjson.addProperty("dwkhzf", 0);//前台JS计算
                    tjson.addProperty("gbsj", y.getGbsj());
                    tjson.addProperty("yysjqx", y.getYysjqx());
                    tjson.addProperty("dwjb", y.getDwjb());
                    //记录
                    jsonarray.add(tjson);
                    tempmap.put(y.getKhid(), tjson);
                }
                //echart数据
                JsonArray sa = (JsonArray) tjson.get("ScoreArray");
                JsonObject saitem = new JsonObject();
                sa.add(saitem);

                saitem.addProperty("name", y.getKhmc());
                //考核分为0
                /*String khf = y.getKhf();
                if(StringUtils.isBlank(khf)){
					khf = "0";
				}*/
                saitem.addProperty("value", doubleFormat.format(y.getKhf()));
                //考核类型基本数据
                JsonArray khsj = (JsonArray) tjson.get("khsj");
                JsonObject khsjitem = new JsonObject();
                khsj.add(khsjitem);

                khsjitem.addProperty("khfzid", y.getKhfzid());
                khsjitem.addProperty("ywlxkhid", y.getYwlxkhid());
                khsjitem.addProperty("dwkhzt", y.getDwkhzt());
                khsjitem.addProperty("lxkhzt", y.getLxkhzt());
                khsjitem.addProperty("khf", y.getKhf());
                khsjitem.addProperty("khlx", y.getKhlx());
                khsjitem.addProperty("khmc", y.getKhmc());
                khsjitem.addProperty("khrdw", y.getKhrdw());
                khsjitem.addProperty("khrbm", y.getKhrbm());
                khsjitem.addProperty("khrgh", y.getKhrgh());
            }
            return jsonarray;
        }
        return null;
    }

    @Override
    public Map<String, Object> selectYwkhfzById(Map<String, Object> map) {

        map.put("p_zbrs", StringUtils.EMPTY);
        map.put("p_zbkpdf", StringUtils.EMPTY);
        map.put("p_maxjcf", StringUtils.EMPTY);
        map.put("p_errmsg", StringUtils.EMPTY);
        try {
        	ywlxkhMapper.selectYwkhfzById(map);
		} catch (Exception e) {
			e.printStackTrace();
		}

        return map;

    }

    @Override
    public String updateZbkpdfById(Map<String, Object> map) {

        map.put("p_errmsg", StringUtils.EMPTY);

        ywlxkhMapper.updateZbkpdfById(map);

        if ("1".equals(map.get("p_errmsg"))) {
            return "操作成功！";
        } else {
            return "操作失败！";
        }

    }

    @Override
    public String updateGbZtById(Map<String, Object> map) {

        map.put("p_errmsg", StringUtils.EMPTY);

        ywlxkhMapper.updateGbZtById(map);

        if ("1".equals(map.get("p_errmsg"))) {
            return "操作成功！";
        } else {
            return "操作失败！";
        }
    }

    @Override
    public Map<String, Object> selectKhlxByDwbmAndBmbm(String dlrDwbm, List<String> dlrBmjs) {
        Map<String, Object> map = new HashMap<String, Object>();
        String bmbmSql = getBmbmSql(dlrBmjs);

        map.put("p_dwbm", dlrDwbm);
        map.put("p_bmbmSql", bmbmSql);
        map.put("p_cur", StringUtils.EMPTY);

        ywlxkhMapper.selectKhlxByDwbmAndBmbm(map);

        return map;
    }


    /**
     * 生成部门编码sql语句
     *
     * @param dlrBmjs 部门角色
     * @return 部门sql语句
     */
    private String getBmbmSql(List<String> dlrBmjs) {
        StringBuilder bmbmSql = new StringBuilder(" and ( ");
        if (CollectionUtils.isNotEmpty(dlrBmjs)) {
            for (int i = 0; i < dlrBmjs.size(); i++) {
                if (i == 0) {
                    bmbmSql.append(" b.bmbm like '%")
                            .append(dlrBmjs.get(i).split(",")[1])
                            .append("%'");
                } else {
                    bmbmSql.append(" or b.bmbm like '%")
                            .append(dlrBmjs.get(i).split(",")[1])
                            .append("%'");
                }
            }
            bmbmSql.append(")");
        }
        return bmbmSql.toString();
    }


    @Override
    public Map<String, Object> getJsbmByDwbmAndGh(Map<String, Object> map) {
        ywlxkhMapper.getJsbmByDwbmAndGh(map);
        return map;
    }

    @Override
    public Map<String, Object> selectKpglZbkpdfZgpjfById(String khfzid) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("p_khfzid", khfzid);
        map.put("p_zbkpgl", null);
        map.put("p_zbkpdf", null);
        map.put("p_zgpjf", null);

        try {
        	ywlxkhMapper.selectKpglZbkpdfZgpjfById(map);
		} catch (Exception e) {
			e.printStackTrace();
		}

        map.put("zbkpgl", map.get("p_zbkpgl"));
        map.put("zbkpdf", map.get("p_zbkpdf"));
        map.put("zgpjf", map.get("p_zgpjf"));
        return map;
    }

    @Override
    public List<Ywlxkh> getYwkhfzById(String khfzid) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("p_khfzid", khfzid);
        map.put("p_cursor", StringUtils.EMPTY);
        
        try {
        	ywlxkhMapper.getYwkhfzById(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        List<Ywlxkh> ywkhfzList =(List<Ywlxkh>)map.get("p_cursor");
        
        //1、根据是否sfxtpf【是否系统评分的标示】、editable【是否能编辑的标示】
        //2、获取最高评价分、进入计算办法【注意区分是哪一个案件类别；考核类型、单位级别的不同计算办法不同】
        //3、算好的数据再重新设置到原来的集合里面，返回页面进行显示
        List<Ywlxkh> fzList = new ArrayList<Ywlxkh>();
        if(ywkhfzList!=null&&ywkhfzList.size()>0) {
        	Ywlxkh ywkhfz = ywkhfzList.get(0);
        	if(Integer.parseInt(ywkhfz.getLxkhzt())<5) {
        		fzList =  this.getNewYwkhList(ywkhfzList);
        	}else {
        		fzList = ywkhfzList;
        	}
        }
        
        return fzList;//(List<Ywlxkh>) map.get("p_cursor");
    }

    /**
     * 拿出计算此id对应的指标项集合，取出需要自动计算的考核项并进入相应的计算办法进行计算，最后再重新组合成一个新的指标项集合
     * 【注：1、因为案卡统计案件定义2017年为特殊的一年 案件的时间是2016-12-26 00:00:00 到 2017-12-31 23:59:59
     * 		2、取考核类型直接从系统配置考核类型配置表取出的
     * 】
     * @param ywkhfzList 业务考核分值id对应集合
     * @return 业务考核分值id对应的新的集合
     */
    private List<Ywlxkh> getNewYwkhList(List<Ywlxkh> ywkhfzList) {
    	List<Ywlxkh> list = new ArrayList<Ywlxkh>();
    	Map<String,Object> map = new HashMap<String,Object>(Constant.NUM_12);
    	
    	for (Ywlxkh ywlxkh : ywkhfzList) { 
    		//【注意：考虑一个问题，是不是每次都要进来计算？？还是只算一次，然后就不用管了(ywzf 、zpjcf不为0)】
    		//这儿的Constant.YWKH_KHLX_X由XT_YWGZ_KHLXPZ中的khlx提供对应【实际运行环境中可能不一样，需要核对】
    		if(!StringUtils.EMPTY.equals(ywlxkh.getYwzf())
    				||!StringUtils.EMPTY.equals(ywlxkh.getZpjdf())){
    			if(Constant.YWKH_KHLX_1.equals(ywlxkh.getKhlx())){
    				//1、考核类型为乡镇检察室工作【khlx值为06】的自动计算
    				ywlxkh = this.countYwkhXzjcsgz(ywlxkh);
    			}else if(Constant.YWKH_KHLX_3.equals(ywlxkh.getKhlx())){
    				//2、考核类型为侦查监督工作【khlx值为02】的自动计算
    				ywlxkh = this.countYwkhWcnxsZcjd(ywlxkh);
        		}else if(Constant.YWKH_KHLX_4.equals(ywlxkh.getKhlx())){
    				//3、考核类型为刑事申诉检察工作（分市院、基层院）【khlx值为09】的自动计算
    				ywlxkh = this.countYwkhXsssjcgzZdjs(ywlxkh);
        		}else if(Constant.YWKH_KHLX_5.equals(ywlxkh.getKhlx())){
        			//4、考核类型为案件管理工作（分市院,县区院）【khlx值为12】的自动计算
    				ywlxkh = this.countYwkhAjglgzZdjs(ywlxkh);
        		}else if(Constant.YWKH_KHLX_6.equals(ywlxkh.getKhlx())){
        			//5、考核类型为民事行政检察工作（县区院、分市院）【khlx值为07】的自动计算
    				ywlxkh = this.countYwkhMsxzjcgzZdjs(ywlxkh);
        		}else if(Constant.YWKH_KHLX_7.equals(ywlxkh.getKhlx())){
        			//6、考核类型为刑事执行检察工作（要区分分市院、县区院）【khlx值为04】的自动计算
    				ywlxkh = this.countYwkhXszxjcgzZdjs(ywlxkh);
        		}else if(Constant.YWKH_KHLX_8.equals(ywlxkh.getKhlx())){
        			//6、考核类型为公诉工作（分市院、县区院）【khlx值为03】的自动计算
    				ywlxkh = this.countYwkhGsgzZdjs(ywlxkh);
        		}else if(Constant.YWKH_KHLX_9.equals(ywlxkh.getKhlx())){
        			//7、考核类型为未成年人刑事检察工作（分市院、县区院）【khlx值为01】的自动计算
    				ywlxkh = this.countYwkhWcnrxsjcgzZdjs(ywlxkh);
        		}
    			if(Integer.parseInt(ywlxkh.getLxkhzt())<5) {
    				//调用服务修改此分值的指标考核概览字段的值
    				this.modifyYwFzxxById(ywlxkh);
    			}
    		}
			list.add(ywlxkh);
		}
    	return list;
	}

    /**
     * 自动计算业务考核的未成年人刑事检察工作（分市院、县区院）
     * @param ywlxkh 业务考核分值实体类
     * @return 新的组装好的业务考核分值实体类
     * 评分办法：
     * 1、
     * 
     * 确定是哪一行用zxmbm，目前是写死的,直接去考核指标配置表查看的具体是哪一行的zxmbm
     * 
     */
    private Ywlxkh countYwkhWcnrxsjcgzZdjs(Ywlxkh ywlxkh) {
    	Map<String,Object> map = new HashMap<String,Object>(Constant.NUM_9);
    	//获取该项的指标考评概览，是一个Json形式的数组
    	JsonArray jsonArr = (JsonArray) Constant.JSON_PARSER.parse(ywlxkh.getZbkpgl());
    	int jsonArrLength = jsonArr.size();
    	
    	for (int i = 0;i<jsonArrLength;i++) {  //JsonElement json : jsonArr
    		 //根据是否系统评分、是否能编辑来判定自动计算考核项   &&"false".equals(jsonArr.get(i).getAsJsonObject().get("editable").getAsString())
    		int ajsl = 0; //案件数量
    		if("1".equals(jsonArr.get(i).getAsJsonObject().get("sfxtpf").getAsString())){
    			//记录下该行的项目子编码【也就是要做计算的行，每行计算办法以及查询的信息可能不一样】
    			String zxmbm = jsonArr.get(i).getAsJsonObject().get("zxmbm").getAsString();  //子项目编码
    			Double xmfz = jsonArr.get(i).getAsJsonObject().get("xmfz").getAsDouble(); //项目最高分
    			Double dgdf = jsonArr.get(i).getAsJsonObject().get("cxfz").getAsDouble(); //项目最高分
    			
    			String type = StringUtils.EMPTY; //进入哪一个查询的标示【1：标示第一个查询 2:表示第二个查询】
    					
    			if("12".equals(zxmbm)){
    				type = "1";
    			}else if("13".equals(zxmbm)){
    				type = "2";
    			}else if("21".equals(zxmbm)){
    				type = "3";
    			}else if("22".equals(zxmbm)){
    				type = "4";
    			}else if("23".equals(zxmbm)){
    				type = "5";
    			}else if("25".equals(zxmbm)){
    				type = "6";
    			}else if("26".equals(zxmbm)){
    				type = "7";
    			}else if("27".equals(zxmbm)){
    				type = "8";
    			}else if("29".equals(zxmbm)){
    				type = "9";
    			}else if("30".equals(zxmbm)){
    				type = "10";
    			}else if("32".equals(zxmbm)){
    				type = "11";
    			}else if("36".equals(zxmbm)){
    				type = "12";
    			}else if("37".equals(zxmbm)){
    				type = "13";
    			}else if("38".equals(zxmbm)){
    				type = "14";
    			}else{
    				type = "1";
    			}		
    					
    			//确定该项是否是自动计算项  名称对应p_type
    			String dwbm = ywlxkh.getDwbm();
    			String kssj = StringUtils.EMPTY;
    			String jssj = StringUtils.EMPTY;
    			String tempYwkhKssj = DateUtil.dateToString(ywlxkh.getKssj(), "yyyy");  //因为案卡统计案件定义2017年为特殊的一年 案件的时间是2016-12-26 00:00:00 到 2017-12-31 23:59:59
    			if(Constant.YWKH_AJCX_TSYEAR.equals(tempYwkhKssj)) {
    				kssj = Constant.YWKH_AJCX_TSRQ;
    			}else {
    				kssj = DateUtil.dateToString(ywlxkh.getKssj(), DateUtil.FORMAT_FOUR); 
    			}
    			jssj = DateUtil.dateToString(ywlxkh.getJssj(), DateUtil.FORMAT_FOUR);
    					
    			String ajlbbmStr = StringUtils.EMPTY;  //暂时定位空 
    			String cxType = type; //查询标示
    					
    			try {
    				map = ajxxService.getYwkhWcnrxsjcgzAj(dwbm, kssj, jssj, ajlbbmStr, cxType,Constant.NUM_1,Constant.NUM_10);
    			} catch (Exception e) {
    				e.printStackTrace();
    			}
    			Double zhdf = 0.0d; //最后得分
    		
    			if(StringUtils.isNotBlank(map.get("p_count").toString())){
    				ajsl = Integer.parseInt(map.get("p_count").toString()); 
    			}
    					
//    			try {
//    				zhdf = AjCountCommon.commonFzCount(ajsl, dgdf, xmfz);
//    			} catch (Exception e) {
//    				e.printStackTrace();
//    			}
    			//(Double)map.get("zhdf")
    			jsonArr.get(i).getAsJsonObject().addProperty("bmpf", ajsl); 
    			jsonArr.get(i).getAsJsonObject().addProperty("agpf", ajsl);
    			jsonArr.get(i).getAsJsonObject().addProperty("cxajType", cxType);
    		}
    		
			Double fz = 1.00d;  //此项分值默认为1.0
			if(null != jsonArr.get(i).getAsJsonObject().get("cxfz")&&StringUtils.isNotBlank(jsonArr.get(i).getAsJsonObject().get("cxfz").getAsString())) {
				fz = jsonArr.get(i).getAsJsonObject().get("cxfz").getAsDouble();
			}
    		BigDecimal cxfz = new BigDecimal(fz);
    		BigDecimal cxajsl = new BigDecimal(ajsl); //此项案件数量
    		Double jcf = cxfz.multiply(cxajsl).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue(); //基础分=单项得分*案件处理数量
			jsonArr.get(i).getAsJsonObject().addProperty("jcf", jcf);
    	}
    	ywlxkh.setZbkpgl(jsonArr.toString());
    	return ywlxkh;
	}

	/**
     * 自动计算业务考核的公诉工作（分市院、县区院）
     * @param ywlxkh 业务考核分值实体类
     * @return 新的组装好的业务考核分值实体类
     * 评分办法：
     * 1、因复议、复核变更原处理决定以及决定被上级院撤销或变更，或被指令纠正的，扣5分/件， -----【基础表工具一】020201第16列、【基础表工具一】020201第22列
     * 2、追诉的漏犯被判处刑罚的（不含免于刑事处罚），计2分/人（含被追诉单位）；-----【基础表工具一】021501第1行第4列到19列合计
     * 3、纠正遗漏罪行的，计1分/件；-----【基础表工具一】 021501第一行第1列
     * 4、书面纠正违法被采纳的，计1分/件。-----【基础表工具一】021601第一行第13列、021701第一行第19列、021901第一行33列
     * 5、检察建议被采纳的，计1分/件。-----【基础表工具一】020201第一行第75列
     * 
     * 确定是哪一行用zxmbm，目前是写死的,直接去考核指标配置表查看的具体是哪一行的zxmbm
     * 
     */
    private Ywlxkh countYwkhGsgzZdjs(Ywlxkh ywlxkh) {
    	Map<String,Object> map = new HashMap<String,Object>(Constant.NUM_9);
    	//获取该项的指标考评概览，是一个Json形式的数组
    	JsonArray jsonArr = (JsonArray) Constant.JSON_PARSER.parse(ywlxkh.getZbkpgl());
    	int jsonArrLength = jsonArr.size();
    	
    	for (int i = 0;i<jsonArrLength;i++) {  //JsonElement json : jsonArr
    		 //根据是否系统评分、是否能编辑来判定自动计算考核项  &&"false".equals(jsonArr.get(i).getAsJsonObject().get("editable").getAsString())
    		int ajsl = 0; //案件数量
    		if("1".equals(jsonArr.get(i).getAsJsonObject().get("sfxtpf").getAsString())){
    			//记录下该行的项目子编码【也就是要做计算的行，每行计算办法以及查询的信息可能不一样】
    			String zxmbm = jsonArr.get(i).getAsJsonObject().get("zxmbm").getAsString();  //子项目编码
    			Double xmfz = jsonArr.get(i).getAsJsonObject().get("xmfz").getAsDouble(); //项目最高分
    					
    			String type = StringUtils.EMPTY; //进入哪一个查询的标示【1：标示第一个查询 2:表示第二个查询】
    			Double dgdf = 1.0d; //单个得分
    					
    			if("12".equals(zxmbm)){
    				type = "1";
    				dgdf = -5.0d;
    			}else if("16".equals(zxmbm)){
    				type = "2";
    				dgdf = 2.0d;
    			}else if("17".equals(zxmbm)){
    				type = "3";
    			}else if("18".equals(zxmbm)){
    				type = "4";
    			}else if("20".equals(zxmbm)){
    				type = "5";
    			}else{
    				type = "1";
    			}		
    					
    			//确定该项是否是自动计算项  名称对应p_type
    			String dwbm = ywlxkh.getDwbm();
    			String kssj = StringUtils.EMPTY;
    			String jssj = StringUtils.EMPTY;
    			String tempYwkhKssj = DateUtil.dateToString(ywlxkh.getKssj(), "yyyy");  //因为案卡统计案件定义2017年为特殊的一年 案件的时间是2016-12-26 00:00:00 到 2017-12-31 23:59:59
    			if(Constant.YWKH_AJCX_TSYEAR.equals(tempYwkhKssj)) {
    				kssj = Constant.YWKH_AJCX_TSRQ;
    			}else {
    				kssj = DateUtil.dateToString(ywlxkh.getKssj(), DateUtil.FORMAT_FOUR); 
    			}
    			jssj = DateUtil.dateToString(ywlxkh.getJssj(), DateUtil.FORMAT_FOUR);
    					
    			String ajlbbmStr = StringUtils.EMPTY;  //暂时定位空 
    			String cxType = type; //查询标示
    					
    			try {
    				map = ajxxService.getYwkhGsgzAj(dwbm, kssj, jssj, ajlbbmStr, cxType,Constant.NUM_1,Constant.NUM_10);
    			} catch (Exception e) {
    				e.printStackTrace();
    			}
    			Double zhdf = 0.0d; //最后得分
    			
    			if(StringUtils.isNotBlank(map.get("p_count").toString())){
    				ajsl = Integer.parseInt(map.get("p_count").toString()); 
    			}
    					
//    			try {
//    				zhdf = AjCountCommon.commonFzCount(ajsl, dgdf, xmfz);
//    			} catch (Exception e) {
//    				e.printStackTrace();
//    			}
    			//(Double)map.get("zhdf")
    			jsonArr.get(i).getAsJsonObject().addProperty("bmpf", ajsl); 
    			jsonArr.get(i).getAsJsonObject().addProperty("agpf", ajsl);
    			jsonArr.get(i).getAsJsonObject().addProperty("cxajType", cxType);
    		}
    		
			Double fz = 1.00d;  //此项分值默认为1.0
			if(null != jsonArr.get(i).getAsJsonObject().get("cxfz")&&StringUtils.isNotBlank(jsonArr.get(i).getAsJsonObject().get("cxfz").getAsString())) {
				fz = jsonArr.get(i).getAsJsonObject().get("cxfz").getAsDouble();
			}
    		BigDecimal cxfz = new BigDecimal(fz);
    		BigDecimal cxajsl = new BigDecimal(ajsl); //此项案件数量
    		Double jcf = cxfz.multiply(cxajsl).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue(); //基础分=单项得分*案件处理数量
			jsonArr.get(i).getAsJsonObject().addProperty("jcf", jcf);
    	}
    	ywlxkh.setZbkpgl(jsonArr.toString());
    	return ywlxkh;
	}

	/**
     * 自动计算业务考核的刑事执行检察工作（分市院、县区院）
     * @param ywlxkh 业务考核分值实体类
     * @return 新的组装好的业务考核分值实体类
     * 评分办法：
     * 【分市院、基层院】
     * 1、办理羁押必要性审查案件（ 羁押必要性审查案件）；
     * 立案。刑事执行检察部门对同级办案机关已批准或决定逮捕的人员有无羁押必要性逐人进行初审后，进行立案审查的，每人计2分。-----【基础表工具一】040101  第11行第5列
     * 2、办理。审查后提出释放或变更强制措施建议的，每人计10分，  -----【基础表工具一】040101  第11行第11列
     * 3、办理减刑、假释、暂予监外执行案件；
     * 对减刑、假释、暂予监外执行案件报请不当，提出书面检察意见并纠正的，每人计3分。 -----【基础表工具一】040401  第1行第4列、16列、43列
     * 4、对减刑、假释裁定不当、暂予监外执行决定不当，提出书面纠正意见并纠正的，每人计10分。 
     * 分市院基础分=本地区按以上方法计算的原始得分÷本地区上年底监狱服刑人员数 县区院基础分=本地区按以上方法计算的原始得分÷本地区上年底社区矫正人员和留所服刑人员数的总和。-----【基础表工具一】040401  第1行第76列
     * 5、办理刑事执行违法违规案件（非监外执行）；
     * 对刑事执行活动中的违法违规情形（非监外执行）提出书面纠正意见并纠正的，每件计3分。 基础分=本地区按以上方法计算的原始得分÷本地区上年底在押人员数。-----【基础表工具一】040501  第1行第49列
     * 6、办理财产刑执行监督、临场监督执行死刑、指定居所监视居住执行监督、强制医疗执行监督案件；
     * 对财产刑执行中存在的违法情形向法院提出书面纠正意见并被采纳的、对临场监督执行死刑向法院提出停止执行死刑检察建议或纠正意见并被采纳的、对指定居所监视居住执行中存在违法情形向办案单位提出书面纠正意见并被采纳的、对强制医疗执行中存在的违法情形向有关单位提出书面纠正意见并被采纳的，每人分别计5分。 
     * 基础分=本地区按以上方法计算的原始得分÷本地区上年底刑事执行检察干警数。
     * 7、办理羁押期限审查案件；
     * 依法开展纠防超期羁押和久押不决检察工作，没有超期羁押、久押不决或者发现超期羁押、久押不决案件后及时提出纠正意见并纠正的，记权重分3分。-----【基础表工具一】040201  第1行第1列 当第一列数字为0 权重计3分 如果数字不为0，则第1列与第2列与第11列数字相同时，权重计3分
     * 
     * 【基层院】
     * 1、办理社区矫正收监执行案件；
     * 对应当撤销缓刑、假释或作出收监执行决定的罪犯，提出撤销缓刑、假释建议书或提出收监执行建议,相关决定机关作出撤销裁定或收监决定的，每人计10分。 
     * 基础分=本地区按以上方法计算的原始得分÷本地区上年底刑事执行检察干警数。   【基础表工具一】040601  第1行第1列
     * 2、监外执行检察监督；
     * 在监外执行（社区矫正）检察中，发现违法问题提出书面纠正意见并纠正的，每件计3分。-----【基础表工具一】040601  第1行第58列
     */
    private Ywlxkh countYwkhXszxjcgzZdjs(Ywlxkh ywlxkh) {
    	Map<String,Object> map = new HashMap<String,Object>(Constant.NUM_9);

    	//获取该项的指标考评概览，是一个Json形式的数组
		JsonArray jsonArr = (JsonArray) Constant.JSON_PARSER.parse(ywlxkh.getZbkpgl());
		int jsonArrLength = jsonArr.size();
		
		for (int i = 0;i<jsonArrLength;i++) {  //JsonElement json : jsonArr
			//根据是否系统评分、是否能编辑来判定自动计算考核项    &&"false".equals(jsonArr.get(i).getAsJsonObject().get("editable").getAsString())
			int ajsl = 0; //案件数量
			if("1".equals(jsonArr.get(i).getAsJsonObject().get("sfxtpf").getAsString())
					&&"false".equals(jsonArr.get(i).getAsJsonObject().get("editable").getAsString())){
				//【单位级别不一样也会计算项zxmbm不一样】记录下该行的项目子编码【也就是要做计算的行，每行计算办法以及查询的信息可能不一样】
				String zxmbm = jsonArr.get(i).getAsJsonObject().get("zxmbm").getAsString();  //子项目编码
				Double xmfz = jsonArr.get(i).getAsJsonObject().get("xmfz").getAsDouble(); //项目最高分
				
				String type = StringUtils.EMPTY; //进入哪一个查询的标示【1：标示第一个查询 2:表示第二个查询】
				Double dgdf = 1.0d; //单个得分
				
				//单位级别为3时基层院\为4时市院
    			if(Constant.NUM_4==Integer.parseInt(ywlxkh.getDwjb())) {
    				if("11".equals(zxmbm)){
        				type = "1";
        				dgdf = 2.0d;
        			}else if("12".equals(zxmbm)){
        				type = "2";
        				dgdf = 10.0d;
        			}else if("13".equals(zxmbm)){
        				type = "3";
        				dgdf = 10.0d;
        			}else if("15".equals(zxmbm)){
        				type = "4";
        				dgdf = 3.0d;
        			}else if("16".equals(zxmbm)){
        				type = "5";
        				dgdf = 10.0d;
        			}else if("18".equals(zxmbm)){
        				type = "6";
        				dgdf = 3.0d;
        			}else if("19".equals(zxmbm)){
        				type = "7";
        				dgdf = 10.0d;
        			}else if("20".equals(zxmbm)){
        				type = "8";
        				dgdf = 5.0d;
        			}else if("22".equals(zxmbm)){
        				type = "9";
        			}else if("27".equals(zxmbm)){
        				type = "10";
        				dgdf = 3.0d;
        			}else{
        				type = "1";
        			}
    			}else if(Constant.NUM_3==Integer.parseInt(ywlxkh.getDwjb())) {
    				if("11".equals(zxmbm)){
        				type = "1";
        				dgdf = 2.0d;
        			}else if("12".equals(zxmbm)){
        				type = "2";
        				dgdf = 10.0d;
        			}else if("13".equals(zxmbm)){
        				type = "3";
        				dgdf = 10.0d;
        			}else if("15".equals(zxmbm)){
        				type = "4";
        				dgdf = 3.0d;
        			}else if("16".equals(zxmbm)){
        				type = "5";
        				dgdf = 10.0d;
        			}else if("18".equals(zxmbm)){
        				type = "6";
        				dgdf = 3.0d;
        			}else if("19".equals(zxmbm)){
        				type = "8";
        				dgdf = 5.0d;
        			}else if("21".equals(zxmbm)){
        				type = "9";
        			}else{
        				type = "1";
        			}
    			}
				
    			//确定该项是否是自动计算项  名称对应p_type
    			String dwbm = ywlxkh.getDwbm();
    			String kssj = StringUtils.EMPTY;
    			String jssj = StringUtils.EMPTY;
    			String tempYwkhKssj = DateUtil.dateToString(ywlxkh.getKssj(), "yyyy");  //因为案卡统计案件定义2017年为特殊的一年 案件的时间是2016-12-26 00:00:00 到 2017-12-31 23:59:59
    			if(Constant.YWKH_AJCX_TSYEAR.equals(tempYwkhKssj)) {
    				kssj = Constant.YWKH_AJCX_TSRQ;
    			}else {
    				kssj = DateUtil.dateToString(ywlxkh.getKssj(), DateUtil.FORMAT_FOUR); 
    			}
    			jssj = DateUtil.dateToString(ywlxkh.getJssj(), DateUtil.FORMAT_FOUR);
				String ajlbbmStr = StringUtils.EMPTY;
				String cxType = type; //查询标示
				
				try {
					map = ajxxService.getYwkhXszxjcgzAj(dwbm,kssj,jssj,ajlbbmStr,cxType,Constant.NUM_1,Constant.NUM_10);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				if(!StringUtils.EMPTY.equals(map.get("p_count").toString())){
					ajsl = Integer.parseInt(map.get("p_count").toString()); 
				}
				
				//计算得分以及计算公式
//				Double zhdf = 0.0d; //最终得分
//				try {
//    				zhdf = AjCountCommon.commonFzCount(ajsl, dgdf, xmfz);
//    			} catch (Exception e) {
//    				e.printStackTrace();
//    			}
				
				//(Double)map.get("zhdf")
				jsonArr.get(i).getAsJsonObject().addProperty("bmpf", ajsl); 
				jsonArr.get(i).getAsJsonObject().addProperty("agpf", ajsl);
				jsonArr.get(i).getAsJsonObject().addProperty("cxajType", cxType);
			}
			
			Double fz = 1.00d;  //此项分值默认为1.0
			if(null != jsonArr.get(i).getAsJsonObject().get("cxfz")&&StringUtils.isNotBlank(jsonArr.get(i).getAsJsonObject().get("cxfz").getAsString())) {
				fz = jsonArr.get(i).getAsJsonObject().get("cxfz").getAsDouble();
			}
    		BigDecimal cxfz = new BigDecimal(fz);
    		BigDecimal cxajsl = new BigDecimal(ajsl); //此项案件数量
    		Double jcf = cxfz.multiply(cxajsl).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue(); //基础分=单项得分*案件处理数量
			jsonArr.get(i).getAsJsonObject().addProperty("jcf", jcf);
		}
		ywlxkh.setZbkpgl(jsonArr.toString());
		
		return ywlxkh;
	}

	/**
     * 自动计算业务考核的民事行政检察工作
     * @param ywlxkh 业务考核分值实体类
     * @return 新的组装好的业务考核分值实体类
     * 评分办法：
     * 1、制发支持起诉意见书，计10分/件。
     * 
     */
    private Ywlxkh countYwkhMsxzjcgzZdjs(Ywlxkh ywlxkh) {
    	Map<String,Object> map = new HashMap<String,Object>(Constant.NUM_9);
		//获取该项的指标考评概览，是一个Json形式的数组
		JsonArray jsonArr = (JsonArray) Constant.JSON_PARSER.parse(ywlxkh.getZbkpgl());
		int jsonArrLength = jsonArr.size();
		
		for (int i = 0;i<jsonArrLength;i++) {  //JsonElement json : jsonArr
			//根据是否系统评分、是否能编辑来判定自动计算考核项   &&"false".equals(jsonArr.get(i).getAsJsonObject().get("editable").getAsString())
			int ajsl = 0; //案件数量
			if("1".equals(jsonArr.get(i).getAsJsonObject().get("sfxtpf").getAsString())
					&&"false".equals(jsonArr.get(i).getAsJsonObject().get("editable").getAsString())){
				//【单位级别不一样也会计算项zxmbm不一样】记录下该行的项目子编码【也就是要做计算的行，每行计算办法以及查询的信息可能不一样】
				String zxmbm = jsonArr.get(i).getAsJsonObject().get("zxmbm").getAsString();  //子项目编码
				Double xmfz = jsonArr.get(i).getAsJsonObject().get("xmfz").getAsDouble(); //项目最高分
				Double dgdf = jsonArr.get(i).getAsJsonObject().get("cxfz").getAsDouble(); //单个得分
				
				String type = StringUtils.EMPTY; //进入哪一个查询的标示【1：标示第一个查询 2:表示第二个查询】
				//单位级别为3时基层院\为4时市院
    			if(Constant.NUM_4==Integer.parseInt(ywlxkh.getDwjb())) {
    				if("11".equals(zxmbm)){
        				type = "1";
        			}else if("12".equals(zxmbm)){
        				type = "2";
        			}else if("13".equals(zxmbm)){
        				type = "4";
        			}else if("14".equals(zxmbm)){
        				type = "5";
        			}else if("15".equals(zxmbm)){
        				type = "6";
        			}else if("16".equals(zxmbm)){
        				type = "7";
        			}else{
        				type = "1";
        			}
    			}else if(Constant.NUM_3==Integer.parseInt(ywlxkh.getDwjb())) {
    				if("11".equals(zxmbm)){
        				type = "1";
        			}else if("12".equals(zxmbm)){
        				type = "2";
        			}else if("13".equals(zxmbm)){
        				type = "3";
        			}else if("14".equals(zxmbm)){
        				type = "4";
        			}else if("15".equals(zxmbm)){
        				type = "5";
        			}else if("16".equals(zxmbm)){
        				type = "6";
        			}else if("17".equals(zxmbm)){
        				type = "7";
        			}else if("19".equals(zxmbm)){
        				type = "8";
        			}else{
        				type = "1";
        			}
    			}
				
				//确定该项是否是自动计算项  名称对应p_type
				String dwbm = ywlxkh.getDwbm();
				String kssj = StringUtils.EMPTY;
				String jssj = StringUtils.EMPTY;
				String tempYwkhKssj = DateUtil.dateToString(ywlxkh.getKssj(), "yyyy");  //因为案卡统计案件定义2017年为特殊的一年 案件的时间是2016-12-26 00:00:00 到 2017-12-31 23:59:59
    			if(Constant.YWKH_AJCX_TSYEAR.equals(tempYwkhKssj)) {
    				kssj = Constant.YWKH_AJCX_TSRQ;
    			}else {
    				kssj = DateUtil.dateToString(ywlxkh.getKssj(), DateUtil.FORMAT_FOUR); 
    			}
    			jssj = DateUtil.dateToString(ywlxkh.getJssj(), DateUtil.FORMAT_FOUR);
				String ajlbbmStr = StringUtils.EMPTY;
				String cxType = type; //查询标示
				Double zhdf = 0.0d; //最终得分
				
				try {
					map = ajxxService.getYwkhMsxzjcgzAj(dwbm,kssj,jssj,ajlbbmStr,cxType,Constant.NUM_1,Constant.NUM_10);
					
					if(!StringUtils.EMPTY.equals(map.get("p_count").toString())){
						ajsl = Integer.parseInt(map.get("p_count").toString()); 
					}
				
//    				zhdf = AjCountCommon.commonFzCount(ajsl, dgdf, xmfz);
    			} catch (Exception e) {
    				e.printStackTrace();
    			}
				jsonArr.get(i).getAsJsonObject().addProperty("bmpf", ajsl); 
				jsonArr.get(i).getAsJsonObject().addProperty("agpf", ajsl);
				jsonArr.get(i).getAsJsonObject().addProperty("cxajType", cxType);
			}
			
			Double fz = 1.00d;  //此项分值默认为1.0
			if(null != jsonArr.get(i).getAsJsonObject().get("cxfz")&&StringUtils.isNotBlank(jsonArr.get(i).getAsJsonObject().get("cxfz").getAsString())) {
				fz = jsonArr.get(i).getAsJsonObject().get("cxfz").getAsDouble();
			}
    		BigDecimal cxfz = new BigDecimal(fz);
    		BigDecimal cxajsl = new BigDecimal(ajsl); //此项案件数量
    		Double jcf = cxfz.multiply(cxajsl).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue(); //基础分=单项得分*案件处理数量
			jsonArr.get(i).getAsJsonObject().addProperty("jcf", jcf);
		}
		ywlxkh.setZbkpgl(jsonArr.toString());
		
		return ywlxkh;
	}

	/**
     * 自动计算业务考核的案件管理工作（分市院,县区院）
     * @param ywlxkh 业务考核分值实体类
     * @return 新的组装好的业务考核分值实体类
     * 评分办法：
     * 1、法律文书公开率最高评价分2分：以公开率计基础分。
     * 
     */
    private Ywlxkh countYwkhAjglgzZdjs(Ywlxkh ywlxkh) {
    	Map<String,Object> map = new HashMap<String,Object>(Constant.NUM_9);
		
		//获取该项的指标考评概览，是一个Json形式的数组
		JsonArray jsonArr = (JsonArray) Constant.JSON_PARSER.parse(ywlxkh.getZbkpgl());
		int jsonArrLength = jsonArr.size();
		
		for (int i = 0;i<jsonArrLength;i++) {  //JsonElement json : jsonArr
			//根据是否系统评分、是否能编辑来判定自动计算考核项      &&"false".equals(jsonArr.get(i).getAsJsonObject().get("editable").getAsString())
			int ajsl = 0; //案件数量
			if("1".equals(jsonArr.get(i).getAsJsonObject().get("sfxtpf").getAsString())
					&&"false".equals(jsonArr.get(i).getAsJsonObject().get("editable").getAsString())){
				//【单位级别不一样也会计算项zxmbm不一样】记录下该行的项目子编码【也就是要做计算的行，每行计算办法以及查询的信息可能不一样】
				String zxmbm = jsonArr.get(i).getAsJsonObject().get("zxmbm").getAsString();  //子项目编码
				Double xmfz = jsonArr.get(i).getAsJsonObject().get("xmfz").getAsDouble(); //项目最高分
				
				String type = StringUtils.EMPTY; //进入哪一个查询的标示【1：标示第一个查询 2:表示第二个查询】

				//确定该项是否是自动计算项  名称对应p_type
				String dwbm = ywlxkh.getDwbm();
				String kssj = StringUtils.EMPTY;
				String tempYwkhKssj = DateUtil.dateToString(ywlxkh.getKssj(), "yyyy");  //因为案卡统计案件定义2017年为特殊的一年 案件的时间是2016-12-26 00:00:00 到 2017-12-31 23:59:59
				if(Constant.YWKH_AJCX_TSYEAR.equals(tempYwkhKssj)) {
					kssj = Constant.YWKH_AJCX_TSRQ;
				}else {
					kssj = DateUtil.dateToString(ywlxkh.getKssj(), DateUtil.FORMAT_FOUR); 
				}
				String jssj = DateUtil.dateToString(ywlxkh.getJssj(), DateUtil.FORMAT_FOUR);
				String ajlbbmStr = StringUtils.EMPTY;
				String cxType = type; //查询标示
				
				map = ajxxService.getYwkhAjglgzAj(dwbm,kssj,jssj,ajlbbmStr,cxType,Constant.NUM_1,Constant.NUM_10);
				Double zhdf = 0.0d; //最终得分
				
				if(!StringUtils.EMPTY.equals(map.get("p_count").toString())){
					ajsl = Integer.parseInt(map.get("p_count").toString()); 
				}
				
				//计算得分以及计算公式【后面可以更改为接口，计算接口】
//				Double jsdf = 0.0d;
//				
//				if(ajsl>0){
//					jsdf = 2.0;
//				}
//				
//				zhdf = this.returnNew(jsdf,xmfz);
				
				//(Double)map.get("zhdf")
				jsonArr.get(i).getAsJsonObject().addProperty("bmpf", ajsl); 
				jsonArr.get(i).getAsJsonObject().addProperty("agpf", ajsl);
				jsonArr.get(i).getAsJsonObject().addProperty("cxajType", cxType);
			}
			
			Double fz = 1.00d;  //此项分值默认为1.0
			if(null != jsonArr.get(i).getAsJsonObject().get("cxfz")&&StringUtils.isNotBlank(jsonArr.get(i).getAsJsonObject().get("cxfz").getAsString())) {
				fz = jsonArr.get(i).getAsJsonObject().get("cxfz").getAsDouble();
			}
    		BigDecimal cxfz = new BigDecimal(fz);
    		BigDecimal cxajsl = new BigDecimal(ajsl); //此项案件数量
    		Double jcf = cxfz.multiply(cxajsl).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue(); //基础分=单项得分*案件处理数量
			jsonArr.get(i).getAsJsonObject().addProperty("jcf", jcf);
		}
		ywlxkh.setZbkpgl(jsonArr.toString());
		
		return ywlxkh;
	}

	/**
     * 自动计算业务考核的刑事申诉检察工作（分市院、基层院)
     * @param ywlxkh 业务考核分值实体类
     * @return 新的组装好的业务考核分值实体类
     * type 查询信息的标示字符串，1：第一种查询；2：第二种查询
     * 【分市院】【基层院】评分办法：【不一样的有他们的最高评价分分值以及指标项】
     * 1、全年未受理或办理刑事申诉类案件的，得基础分10分；全年有受理或办理刑事申诉类案件的，得基础分20分。 
     * 2、全年未受理或办理国家赔偿类案件的，得基础分10分；全年有受理或办理国家赔偿类案件的，得基础分20分。 
     * 3、办结国家赔偿案件或复议案件，每件计2分。
     * 4、复议改变原赔偿决定的，每件加2分；
     * 5、办结国家司法救助案件并已发放救助资金的，每件计2分。
     * 
     */
    private Ywlxkh countYwkhXsssjcgzZdjs(Ywlxkh ywlxkh) {
    	Map<String,Object> map = new HashMap<String,Object>(Constant.NUM_9);
		
		//获取该项的指标考评概览，是一个Json形式的数组
		JsonArray jsonArr = (JsonArray) Constant.JSON_PARSER.parse(ywlxkh.getZbkpgl());
		int jsonArrLength = jsonArr.size();
		
		for (int i = 0;i<jsonArrLength;i++) {  //JsonElement json : jsonArr
			//根据是否系统评分、是否能编辑来判定自动计算考核项    &&"false".equals(jsonArr.get(i).getAsJsonObject().get("editable").getAsString())
			int ajsl = 0; //案件数量
			if("1".equals(jsonArr.get(i).getAsJsonObject().get("sfxtpf").getAsString())
					&&"false".equals(jsonArr.get(i).getAsJsonObject().get("editable").getAsString())){
				//【单位级别不一样也会计算项zxmbm不一样】记录下该行的项目子编码【也就是要做计算的行，每行计算办法以及查询的信息可能不一样】
				String zxmbm = jsonArr.get(i).getAsJsonObject().get("zxmbm").getAsString();  //子项目编码
				Double xmfz = jsonArr.get(i).getAsJsonObject().get("xmfz").getAsDouble(); //项目最高分
				Double cxfz = jsonArr.get(i).getAsJsonObject().get("cxfz").getAsDouble(); //单项得分
				
				String type = StringUtils.EMPTY; //进入哪一个查询的标示【1：标示第一个查询 2:表示第二个查询】
				
				//单位级别为3时基层院\为4时市院
    				if("11".equals(zxmbm)){
    					type = "1";
    				}else if("12".equals(zxmbm)){
    					type = "2";
    				}else if("13".equals(zxmbm)){
    					type = "3";
    				}else if("14".equals(zxmbm)){
    					type = "4";
    				}else if("15".equals(zxmbm)){
    					type = "5";
    				}else if("16".equals(zxmbm)){
    					type = "6";
    				}else if("19".equals(zxmbm)){
    					type = "7";
    				}else if("20".equals(zxmbm)){
    					type = "8";
    				}else if("21".equals(zxmbm)){
    					type = "9";
    				}else if("23".equals(zxmbm)){
    					type = "10";
    				}else if("24".equals(zxmbm)){
    					type = "11";
    				}else if("26".equals(zxmbm)){
    					type = "12";
    				}else{
    					type = "1";
    				}	
				
				//确定该项是否是自动计算项  名称对应p_type
				String dwbm = ywlxkh.getDwbm();
				String kssj = StringUtils.EMPTY;
				String jssj = StringUtils.EMPTY;
				String tempYwkhKssj = DateUtil.dateToString(ywlxkh.getKssj(), "yyyy");  //因为案卡统计案件定义2017年为特殊的一年 案件的时间是2016-12-26 00:00:00 到 2017-12-31 23:59:59
				if(Constant.YWKH_AJCX_TSYEAR.equals(tempYwkhKssj)) {
					kssj = Constant.YWKH_AJCX_TSRQ;
				}else {
					kssj = DateUtil.dateToString(ywlxkh.getKssj(), DateUtil.FORMAT_FOUR); 
				}
				jssj = DateUtil.dateToString(ywlxkh.getJssj(), DateUtil.FORMAT_FOUR);
				
				String ajlbbmStr = StringUtils.EMPTY;
				String cxType = type; //查询标示
				Double zhdf = 0.0d; //最终得分
				
				try {
					map = ajxxService.getYwkhXsssjcgzAj(dwbm,kssj,jssj,ajlbbmStr,cxType,Constant.NUM_1,Constant.NUM_10);
					
					if(StringUtils.isNotBlank(map.get("p_count").toString())){
						ajsl = Integer.parseInt(map.get("p_count").toString()); 
					}
					
//					if("1".equals(cxType)||"7".equals(cxType)) {
//						if(ajsl>0) {
//							zhdf = 20.0;
//						}else {
//							zhdf = 10.0;
//						}
//					}else {
//						zhdf = AjCountCommon.commonFzCount(ajsl, cxfz, xmfz);
//					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				//(Double)map.get("zhdf")
				jsonArr.get(i).getAsJsonObject().addProperty("bmpf", ajsl); 
				jsonArr.get(i).getAsJsonObject().addProperty("agpf", ajsl);
				jsonArr.get(i).getAsJsonObject().addProperty("cxajType", cxType);
			}
			
			Double fz = 1.00d;  //此项分值默认为1.0
			if(null != jsonArr.get(i).getAsJsonObject().get("cxfz")&&StringUtils.isNotBlank(jsonArr.get(i).getAsJsonObject().get("cxfz").getAsString())) {
				fz = jsonArr.get(i).getAsJsonObject().get("cxfz").getAsDouble();
			}
    		BigDecimal cxfz = new BigDecimal(fz);
    		BigDecimal cxajsl = new BigDecimal(ajsl); //此项案件数量
    		Double jcf = cxfz.multiply(cxajsl).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue(); //基础分=单项得分*案件处理数量
			jsonArr.get(i).getAsJsonObject().addProperty("jcf", jcf);
		}
		ywlxkh.setZbkpgl(jsonArr.toString());
		
		return ywlxkh;
	}

	/**
     * 自动计算业务考核的侦查监督工作
     * @param ywlxkh 业务考核分值实体类
     * @return 新的组装好的业务考核分值实体类
     * type 查询信息的标示字符串，1：第一种查询；2：第二种查询
     * 评分办法：
     * 1、监督立案，计1分/件；-----【基础表一】011101第22列
     * 2、监督行政执法机关移送案件，计1分/件；-----【基础表一】011301第9列
     * 3、监督撤案，计1分/件；【基础表一】011101第46列
     * 4、立案监督案件被判处有期徒刑以上刑罚的，计3分/人。【基础表一】011105第22列-37列
     * 5、纠正漏捕对象被判处有期徒刑以上刑罚的，计1分/人。【基础表一】011401第14列-29列
     * 6、书面纠正违法，计1分/件；【基础表一】011501第1列
     * 
     * 确定是哪一行用zxmbm，目前是写死的,直接去考核指标配置表查看的具体是哪一行的zxmbm
     */
    private Ywlxkh countYwkhWcnxsZcjd(Ywlxkh ywlxkh) {
    	Map<String,Object> map = new HashMap<String,Object>(Constant.NUM_9);
		
		//获取该项的指标考评概览，是一个Json形式的数组
		JsonArray jsonArr = (JsonArray) Constant.JSON_PARSER.parse(ywlxkh.getZbkpgl());
		int jsonArrLength = jsonArr.size();
		
		for (int i = 0;i<jsonArrLength;i++) {  //JsonElement json : jsonArr
			//根据是否系统评分、是否能编辑来判定自动计算考核项    
			int ajsl = 0; //案件数量
			if("1".equals(jsonArr.get(i).getAsJsonObject().get("sfxtpf").getAsString())
					&&"false".equals(jsonArr.get(i).getAsJsonObject().get("editable").getAsString())){
				//记录下该行的项目子编码【也就是要做计算的行，每行计算办法以及查询的信息可能不一样】
				String zxmbm = jsonArr.get(i).getAsJsonObject().get("zxmbm").getAsString();  //子项目编码
				Double xmfz = jsonArr.get(i).getAsJsonObject().get("xmfz").getAsDouble(); //项目最高分
				
				String type = StringUtils.EMPTY; //进入哪一个查询的标示【1：标示第一个查询 2:表示第二个查询】
				Double dgdf = 1.0d; //单个得分
				
				if("16".equals(zxmbm)){
					type = "1";
				}else if("17".equals(zxmbm)){
					type = "2";
				}else if("18".equals(zxmbm)){
					type = "3";
				}else if("19".equals(zxmbm)){
					type = "4";
					dgdf = 3.0d;
				}else if("20".equals(zxmbm)){
					type = "5";
				}else if("21".equals(zxmbm)){
					type = "6";
				}else{
					type = "1";
				}		
				
				//确定该项是否是自动计算项  名称对应p_type
				String dwbm = ywlxkh.getDwbm();
				String kssj = StringUtils.EMPTY;
				String jssj = StringUtils.EMPTY;
				String tempYwkhKssj = DateUtil.dateToString(ywlxkh.getKssj(), "yyyy");  //因为案卡统计案件定义2017年为特殊的一年 案件的时间是2016-12-26 00:00:00 到 2017-12-31 23:59:59
				if(Constant.YWKH_AJCX_TSYEAR.equals(tempYwkhKssj)) {
					kssj = Constant.YWKH_AJCX_TSRQ;
				}else {
					kssj = DateUtil.dateToString(ywlxkh.getKssj(), DateUtil.FORMAT_FOUR); 
				}
				jssj = DateUtil.dateToString(ywlxkh.getJssj(), DateUtil.FORMAT_FOUR);
				
				String ajlbbmStr = StringUtils.EMPTY;  //暂时定位空 
				String cxType = type; //查询标示
				Double zhdf = 0.0d; //最后得分
				
				try {
					map = ajxxService.getYwkhLajdAj(dwbm,kssj,jssj,ajlbbmStr,cxType,Constant.NUM_1,Constant.NUM_10);
					if(StringUtils.isNotBlank(map.get("p_count").toString())){
						ajsl = Integer.parseInt(map.get("p_count").toString()); 
					}
//					zhdf = AjCountCommon.commonFzCount(ajsl, dgdf, xmfz);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				//(Double)map.get("zhdf")
				jsonArr.get(i).getAsJsonObject().addProperty("bmpf", ajsl); 
				jsonArr.get(i).getAsJsonObject().addProperty("agpf", ajsl);
				jsonArr.get(i).getAsJsonObject().addProperty("cxajType", cxType);
			}
			
			Double fz = 1.00d;  //此项分值默认为1.0
			if(null != jsonArr.get(i).getAsJsonObject().get("cxfz")&&StringUtils.isNotBlank(jsonArr.get(i).getAsJsonObject().get("cxfz").getAsString())) {
				fz = jsonArr.get(i).getAsJsonObject().get("cxfz").getAsDouble();
			}
    		BigDecimal cxfz = new BigDecimal(fz);
    		BigDecimal cxajsl = new BigDecimal(ajsl); //此项案件数量
    		Double jcf = cxfz.multiply(cxajsl).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue(); //基础分=单项得分*案件处理数量
			jsonArr.get(i).getAsJsonObject().addProperty("jcf", jcf);
//			String sfstjsKey = StringUtils.EMPTY;
//			if(jsonArr.get(i).getAsJsonObject().has("sfxtpf")){
//				if (StringUtils.isNoneBlank(sfstjsKey)) {
//					sfstjsKey = jsonArr.get(i).getAsJsonObject().get("sfxtpf").getAsString();
//				}
//			}
//			jsonArr.get(i).getAsJsonObject().addProperty("sfxtpf", sfstjsKey);
		}
		ywlxkh.setZbkpgl(jsonArr.toString());
		
		return ywlxkh;
	}

	/**
     * 根据分值id修改业务考核分值表信息
     * @param ywlxkh 分值对象
     */
    private void modifyYwFzxxById(Ywlxkh ywlxkh) {
    	Map<String,Object> map = new HashMap<String,Object>(Constant.NUM_5);
    	map.put("p_khfzid", ywlxkh.getYwlxkhfzid());
    	map.put("p_ywzf",ywlxkh.getYwzf());
    	map.put("p_zbkpgl",ywlxkh.getZbkpgl());
    	map.put("p_zbkpdf",ywlxkh.getZbkpdf());
    	map.put("p_msg", StringUtils.EMPTY);
    	try {
    		ywlxkhMapper.modifyYwlxkhfz(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
     * 自动计算业务考核的乡镇检察室工作
     * @param ywlxkh 业务考核分值实体类
     * type 查询信息的标示字符串，1：第一种查询；2：第二种查询
     * 评分办法：受理、发现职务犯罪线索，每件计1分，检察机关据此立案的，每立案1人再计5分。[查询1]
     * 1、立案监督分值10分，2、侦查活动监督、法庭审判监督、社区矫正监督分值各5分。 【查询2】
     */
	private Ywlxkh countYwkhXzjcsgz(Ywlxkh ywlxkh) {
		Map<String,Object> map = new HashMap<String,Object>(Constant.NUM_9);
		
		//获取该项的指标考评概览，是一个Json形式的数组
		JsonArray jsonArr = (JsonArray) Constant.JSON_PARSER.parse(ywlxkh.getZbkpgl());
		int jsonArrLength = jsonArr.size();
		
		for (int i = 0;i<jsonArrLength;i++) {  //JsonElement json : jsonArr
			//根据是否系统评分、是否能编辑来判定自动计算考核项   &&"false".equals(jsonArr.get(i).getAsJsonObject().get("editable").getAsString())
			int ajsl = 0; //案件数量
			if("1".equals(jsonArr.get(i).getAsJsonObject().get("sfxtpf").getAsString())
					&&"false".equals(jsonArr.get(i).getAsJsonObject().get("editable").getAsString())){
				//记录下该行的项目子编码【也就是要做计算的行，每行计算办法以及查询的信息可能不一样】
				String zxmbm = jsonArr.get(i).getAsJsonObject().get("zxmbm").getAsString();  //子项目编码
				Double xmfz = jsonArr.get(i).getAsJsonObject().get("xmfz").getAsDouble(); //项目最高分
				
				String type = StringUtils.EMPTY; //进入哪一个查询的标示【1：标示第一个查询 2:表示第二个查询】
				
				if("11".equals(zxmbm)){
					type = "1";
				}else if("13".equals(zxmbm)){
					type = "2";
				}else{
					type = "1";
				}		
				
				//确定该项是否是自动计算项  名称对应p_type
				String dwbm = ywlxkh.getDwbm();
				String kssj = StringUtils.EMPTY;
				String tempYwkhKssj = DateUtil.dateToString(ywlxkh.getKssj(), "yyyy");  //因为案卡统计案件定义2017年为特殊的一年 案件的时间是2016-12-26 00:00:00 到 2017-12-31 23:59:59
				if(Constant.YWKH_AJCX_TSYEAR.equals(tempYwkhKssj)) {
					kssj = Constant.YWKH_AJCX_TSRQ;
				}else {
					kssj = DateUtil.dateToString(ywlxkh.getKssj(), DateUtil.FORMAT_FOUR); 
				}
				String jssj = DateUtil.dateToString(ywlxkh.getJssj(), DateUtil.FORMAT_FOUR);
				String ajlbbmStr = StringUtils.EMPTY;
				String cxType = type; //查询标示
				
				map = ajxxService.getYwkhXzjcAj(dwbm,kssj,jssj,ajlbbmStr,cxType,Constant.NUM_1,Constant.NUM_10);
				Double zhdf = 0.0d; //最终得分
				Double jsdf = 0.0; //计算得分
				
				if(!StringUtils.EMPTY.equals(map.get("p_count").toString())){
					ajsl = Integer.parseInt(map.get("p_count").toString()); 
				}
				
//				if("1".equals(map.get("p_type").toString())){
//					//计算得分以及计算公式【后面可以更改为接口，计算接口】
//					jsdf = (double) (ajsl*1+ajsl*5); 
//				}else if("2".equals(map.get("p_type").toString())){
//					jsdf = (double)(ajsl*5);
//				}
//				zhdf = this.returnNew(jsdf,xmfz);
				//(Double)map.get("zhdf")
				jsonArr.get(i).getAsJsonObject().addProperty("bmpf", ajsl); 
				jsonArr.get(i).getAsJsonObject().addProperty("agpf", ajsl);
				jsonArr.get(i).getAsJsonObject().addProperty("cxajType", cxType);
			}
			
			Double fz = 1.00d;  //此项分值默认为1.0
			if(null != jsonArr.get(i).getAsJsonObject().get("cxfz")&&StringUtils.isNotBlank(jsonArr.get(i).getAsJsonObject().get("cxfz").getAsString())) {
				fz = jsonArr.get(i).getAsJsonObject().get("cxfz").getAsDouble();
			}
    		BigDecimal cxfz = new BigDecimal(fz);
    		BigDecimal cxajsl = new BigDecimal(ajsl); //此项案件数量
    		Double jcf = cxfz.multiply(cxajsl).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue(); //基础分=单项得分*案件处理数量
			jsonArr.get(i).getAsJsonObject().addProperty("jcf", jcf);
		}
		ywlxkh.setZbkpgl(jsonArr.toString());
		
		return ywlxkh;
	}

	/**
	 * 根据传入的分值大小，返回一个最终分值
	 * @param fz1 查询或者计算出的分值
	 * @param fz2  固定分值
	 * @return Double的最终分值
	 */
	private Double returnNew(Double fz1,Double fz2) {
		Double zhfz = 0.0d; //最后分值
		if(fz1>=fz2){
			zhfz = fz2;  
		}else{
			zhfz = fz1;
		}
		return zhfz;
	}

	@Override
    public String getYwlxByBmys(String bmysStr) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>(Constant.NUM_3);
        StringBuilder ywlxStr = new StringBuilder();
        map.put("p_bmys", bmysStr);
        map.put("p_cursor", StringUtils.EMPTY);
        ywlxkhMapper.getYwlxByBmys(map);
        List<Map<String, Object>> ywlxList = (List<Map<String, Object>>) map.get("p_cursor");
        for (Map<String, Object> ywlxMap : ywlxList) {
            ywlxStr.append(ywlxMap.get("ywbm")).append(",");
        }
        return ywlxStr.toString();
    }

    @Override
    public String getYwkhDwkh(String dwbm, String ywlxStr, String startDate, String endDate, String sfgs) {
        Map<String, Object> map = new HashMap<String, Object>();
        if ("S".equals(sfgs)) {
            String[] value = ywlxStr.split("\\,");
            map.put("p_dwbm", dwbm);
            map.put("p_ywlxStr", value[0]);
            Date start = DateUtil.getDate4Str(startDate);
            Date end = DateUtil.getDate4Str(endDate);
            //获取数据字典中存放的业务考核日期【这儿是为了获取创建年度考核时的日期】
            Date date = new Date();
            if (null == start || null == end) {
                Map<String, Object> returnMap = new HashMap<String, Object>();
                returnMap.put("status", 0);
                returnMap.put("msg", "时间有误，请重新选择");
                return Constant.GSON.toJson(returnMap);
            }
            map.put("p_startDate", start);
            map.put("p_endDate", end);
            map.put("p_zt", sfgs);
            map.put("p_mc", value[1]);
            map.put("p_gh", value[2]);
            map.put("p_cursor", null);
            ywlxkhMapper.getYwkhDwkh2(map);

        } else {
            map.put("p_dwbm", dwbm);
            map.put("p_ywlxStr", ywlxStr);
            Date start = DateUtil.getDate4Str(startDate);
            Date end = DateUtil.getDate4Str(endDate);
            //获取数据字典中存放的业务考核日期【这儿是为了获取创建年度考核时的日期】
            Date date = new Date();
            if (null == start || null == end) {
                Map<String, Object> returnMap = new HashMap<String, Object>();
                returnMap.put("status", 0);
                returnMap.put("msg", "时间有误，请重新选择");
                return Constant.GSON.toJson(returnMap);
            }
            map.put("p_startDate", start);
            map.put("p_endDate", end);
            map.put("p_zt", sfgs);
            map.put("p_cursor", null);
            ywlxkhMapper.getYwkhDwkh(map);
        }
        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("status", 1);
        returnMap.put("data", (List) map.get("p_cursor"));
        return Constant.GSON.toJson(returnMap);
    }

    @Override
    public List<Map<String, Object>> getYwkhByIdAndYwlx(String khid, String ywlxStr) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>(Constant.NUM_3);
        map.put("p_khid", khid);
        map.put("p_ywlxStr", ywlxStr);
        map.put("p_cursor", StringUtils.EMPTY);
        ywlxkhMapper.getYwkhByIdAndYwlx(map);
        List<Map<String, Object>> khInfoList = (List<Map<String, Object>>) map.get("p_cursor");
        return khInfoList;
    }

    @Override
    public List<Map<String, Object>> getYwkhByywkhIdAndYwlx(String ywkhkhid, String ywlx,String khlx) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>(Constant.NUM_3);
        map.put("p_ywlxkhid", ywkhkhid);
        map.put("p_ywlx", ywlx);
        map.put("p_khlx", khlx);
        map.put("p_cursor", StringUtils.EMPTY);
        ywlxkhMapper.getYwkhByywkhIdAndYwlx(map);
        List<Map<String, Object>> khInfoList = (List<Map<String, Object>>) map.get("p_cursor");
        return khInfoList;
    }

    @Override
    public boolean selectkhspr(Map<String, Object> map) {
        ywlxkhMapper.selectkhspr(map);
        if (map.get("P_COUNT").equals("1")) {
            return true;
        }
        return false;
    }

    @Override
    public String modifyKhfzBzInfo(String ywlxkhfzid, String pflx,
                                   String zbxbm, String fjxx, String bz) {
        String Y = "0";
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("p_ywlxkhfzid", ywlxkhfzid);
            map.put("p_lx", pflx);
            map.put("p_zbxbm", zbxbm);
            map.put("p_fjxx", fjxx);
            map.put("p_bz", bz);

            ywlxkhMapper.upDatePfcl(map);
            Y = "1";
        } catch (Exception e) {
            Y = "0";

        }
        return Y;
    }

    @Override
    public String modifyYwlxkhfz(String khfzid, double ywzf, String zbkpgl, String zbkpdf)
            throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("p_khfzid", khfzid);
        map.put("p_ywzf", ywzf);
        map.put("p_zbkpgl", zbkpgl);
        map.put("p_zbkpdf", zbkpdf);
        map.put("p_msg", StringUtils.EMPTY);
        String Y = "0";
        try {
            ywlxkhMapper.modifyYwlxkhfz(map);
            Y = "1";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Y;
    }

    @Override
    public String modifyKhlxZtById(String khlxid) {
        Map<String, Object> map = new HashMap<String, Object>(Constant.NUM_3);
        map.put("p_khlxid", khlxid);
        map.put("p_errMsg", StringUtils.EMPTY);
        ywlxkhMapper.modifyKhlxZtById(map);
        String msg = (String) map.get("p_errMsg");
        if ("1".equals(msg)) {
            return "操作成功！";
        } else {
            return "操作失败！";
        }

    }

    @Override
    public String getKhrMcGh(String id) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("p_id", id);
        map.put("p_cursor", null);
        ywlxkhMapper.getKhrMcGh(map);
        List list = (List) map.get("p_cursor");
        if (list.size() != 0) {
            Map<String, String> map1 = (Map<String, String>) list.get(0);
            return map1.get("khrmc") + "," + map1.get("khrgh");
        }
        return "";
    }

    @Override
    public String getKhSj() {
    	//默认给出当年1月1号到12月31号
    	int nowYear = DateUtil.getYear(new Date());
    	
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("p_sign", Constant.YWKH_DICT_IDENTIFYING);
        map.put("p_cursor", StringUtils.EMPTY);
        try {
        	ywlxkhMapper.getKhSj(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
        List list = (List) map.get("p_cursor");
        if (list.size() != 0) {
            StringBuilder startDate = new StringBuilder();
            StringBuilder endDate = new StringBuilder();
            for (int i = 0; i < list.size(); i++) {
                Map<String, String> map1 = (Map<String, String>) list.get(i);
                switch (map1.get("dict_type")) {
                    case Constant.YWKH_DICT_IDENTIFYING + "-"+ Constant.NUM_1:
                    	startDate.append(nowYear).append("-").append(map1.get("dict_type_value"));  //年-月-日
                        break;
                    case Constant.YWKH_DICT_IDENTIFYING + "-"+ Constant.NUM_2:
                    	endDate.append(nowYear).append("-").append(map1.get("dict_type_value")); //年-月-日
                        break;
                }
            }
            return startDate.append(",").append(endDate).toString();
        }
        return StringUtils.EMPTY;
    }

    @Override
    public String isZdkhr(String mc, String gh, String startDate, String endDate) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("p_mc", mc);
        map.put("p_gh", gh);
        map.put("p_startDate", DateUtil.getDate4Str(startDate));
        map.put("p_endDate", DateUtil.getDate4Str(endDate));
        map.put("p_num", 0);
        ywlxkhMapper.isZdkhr(map);
        if ("0".equals(map.get("p_num").toString())) {
            return null;
        }
        return "S";
    }

    @Override
    public String getDqDw(String id) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("p_id", id);
        map.put("p_cursor", null);
        ywlxkhMapper.getDqDw(map);
        List list = (List) map.get("p_cursor");
        if (list.size() != 0) {
            Map<String, String> map1 = (Map<String, String>) list.get(0);
            return map1.get("dwbm");
        }
        return "";
    }
    @Override
	public String getyysmbyid(String id) {
		  Map<String, Object> map = new HashMap<String, Object>();
	        map.put("p_id", id);
	        map.put("p_yysm", "");
	        map.put("p_yyfj", "");
	        ywlxkhMapper.getyysmbyid(map);
	        return Constant.GSON.toJson(map);
	}


    @Override
    public String getNextOneGh(String wbid, String cljs) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("p_wbid", wbid);
        map.put("p_cljs", cljs);
        map.put("p_gh", "");
        ywlxkhMapper.getNextOneGh(map);
        return map.get("p_gh").toString();
    }


    @Override
    public String getOneMessage(String wbid) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("p_wbid", wbid);
        map.put("p_cursor", null);
        ywlxkhMapper.getOneMessage(map);
        return Constant.GSON.toJson(map.get("p_cursor"));
    }

    @Override
    public String dcndkh(String dz , String strdwmc , String kwdwbm , String strkhlxmc , String khlx , String starDate, String endDate, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("p_kwdwbm", kwdwbm);
        map.put("p_khlx", khlx);
        map.put("p_stardate", starDate);
        map.put("p_end_date", endDate);
        map.put("p_errmsg", "");
        map.put("p_cursor", StringUtils.EMPTY);
        try {
        	ywlxkhMapper.dcndkh(map);
		} catch (Exception e) {
			LOG.error("导出年度考核失败！", e);
			throw e;
		}
        List list = (List<Map<String,Object>>)map.get("p_cursor");
        if(list.size()==0){
            return "未查询到需要导出的信息";
        }

        try {
            Map<String,String> map1 = OutNdkhExcel.dcndkh( list,strdwmc , kwdwbm , strkhlxmc , khlx , starDate, endDate,request);
            if(map1.size()==0){
                return "未获取到需要导出的数据";
            }else {
                return Constant.GSON.toJson(map1);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "导出失败";
        }
    }
    
}
