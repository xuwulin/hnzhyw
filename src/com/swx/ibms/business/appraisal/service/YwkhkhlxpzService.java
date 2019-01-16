package com.swx.ibms.business.appraisal.service;

import com.swx.ibms.business.appraisal.bean.YwkhKhlxPz;
import com.swx.ibms.common.utils.PageCommon;

import java.util.List;
import java.util.Map;



/**
 * @author liuc
 * @description  考核类型管理
 *@date2017年11月7日
 */
public interface YwkhkhlxpzService {
	  /**
     * 查询所有业务类型
     * @param ywlx 业务类型
     * @param page 当前开始行数
     * @param pageSize 结束行数
     * @return 符合条件的业务类型结果集
     */
    PageCommon<Object> getPageListKhlx(String ywlx, Integer page, Integer pageSize)throws Exception;
    
/**
 * 查询业务类型填充下拉框
 *  @return 符合条件的业务类型结果集
 * */    
    public  List<YwkhKhlxPz> getywkhkhlx(Map<String, Object> map);
    
    /**
     * @param map 类型
     * @return 添加
     */
    List<String>  addywkhkhlx(Map<String,Object> map);
    /**
     * @param map 类型
     * @return 删除
     */
    public boolean deleteywkhkhlx(Map<String,Object> map);
    /**
     * @param map 类型
     * @return 修改考核
     */
    public  boolean  updateywkhkhlx(Map<String,Object> map);
    /**
     * @param map 类型
     * @return 查询业务类型下考核类型新增编码结果
     */
    public String ywkhkhlxbm(Map<String,Object> map);

    List getZhyw(Map<String, Object> map);
    
    /**
     * 通过考核名称获取考核类型配置信息
     * @param khlxmc 考核类型名称
     * @return 考核类型配置对象
     */
    YwkhKhlxPz getKhlxInfoByKhmc(String khlxmc);
    
}
