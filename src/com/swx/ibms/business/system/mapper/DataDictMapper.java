package com.swx.ibms.business.system.mapper;

import com.swx.ibms.business.system.bean.DataDict;

import java.util.List;
import java.util.Map;



/**
 * Created by Try on 2017/11/7.
 * 数据字典的Mapper
 */
public interface DataDictMapper {

    /**
     * 根据父id查询数据字典信息
     * @param map 父id
     * @return
     */
    DataDict getDictByPid(Map<String,Object> map);

    /**
     * 查询数据字典并分页
     * @param map 数据字典父id、开始查询的行数、结束查询的行数
     */
    void getPageListDict(Map<String,Object> map);

    /**
     * 添加字典信息
     * @param map 字典信息
     */
    void addDict(Map<String,Object> map);

    /**
     * 根据字典id修改字典信息
     * @param map 字典信息
     */
    void modifyDict(Map<String,Object> map);

    /**
     * 根据字典id删除字典信息
     * @param map 字典id
     */
    void delDict(Map<String,Object> map);
    /**
     * 根据字典id查询字典信息节点下的数量
     * @param map 字典id
     */
    void selectidcount(Map<String,Object> map);
    
    /**
     * 根据考核类型查询权重信息
     * @param map 考核类型
     */
    Integer selectqz(Map<String, Object> map);
    
    /**
     * 根据gs标识查询公示值
     * @param map 公示值
     */
    List<DataDict> selectisgs(Map<String,Object> map) ;
    
    /**
     * 根据类型与标示获取数据字典信息
     * @param map 类型与标示
     */
    void getDataDictBylxSign(Map<String,Object> map);
}
