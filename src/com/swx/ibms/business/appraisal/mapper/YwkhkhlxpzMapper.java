package com.swx.ibms.business.appraisal.mapper;


import com.swx.ibms.business.appraisal.bean.YwkhKhlxPz;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface YwkhkhlxpzMapper {
    /**
     * @param map 类型
     * @return 考核类型表结果集
     */
    List<YwkhKhlxPz> getPageListKhlx(Map<String,Object> map) throws Exception;
    /**
     * @param map 类型
     * @return 考核类型下拉框结果集
     */
    List<String> getywkhkhlx(Map<String,Object> map);
    /**
     * @param map 类型
     * @return 添加
     */
    void  addywkhkhlx(Map<String,Object> map);
    /**
     * @param map 类型
     * @return 删除
     */
  void  deleteywkhkhlx(Map<String,Object> map);
    /**
     * @param map 类型
     * @return 修改考核
     */
   List<YwkhKhlxPz> updateywkhkhlx(Map<String,Object> map);
    /**
     * @param map 类型
     * @return 查询业务类型下考核类型新增编码结果
     */
    void ywkhkhlxbm(Map<String,Object> map);

    void getZhyw(Map<String, Object> map);
    
    /**
     * 通过考核名称获取考核类型配置信息
     * @param khlxmc 考核类型名称
     * @return 考核类型配置对象
     */
	YwkhKhlxPz getKhlxInfoByKhmc(@Param("khlxmc")String khlxmc);
}
