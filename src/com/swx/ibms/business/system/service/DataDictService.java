package com.swx.ibms.business.system.service;


import com.swx.ibms.business.system.bean.DataDict;
import com.swx.ibms.common.bean.TreeNode;
import com.swx.ibms.common.utils.PageCommon;

import java.util.List;
import java.util.Map;

/**
 * Created by Try on 2017/11/7.
 */
public interface DataDictService {

    /**
     * 根据父id查询数据字典信息
     * @param pid 父id
     * @return 数据字典信息结果集
     */
    List<DataDict> getDictByPid(String pid) throws Exception;

    /**
     * 获取数据字典树
     * @return 数据字典树对象
     * @throws Exception 异常
     */
    TreeNode getDictTree() throws Exception;

    /**
     * 获取全部数据字典并分页
     * @param pid 父id（可传可不传）
     * @param page 当前页
     * @param pageSize 每页显示数
     * @return 分页对象
     */
    PageCommon<Object> getPageListDict(String pid, Integer page, Integer pageSize)throws Exception;

    /**
     * 添加数据字典
     * @param dict 数据字典对象
     * @return 包含成功与否的字符串的map集合
     */
    Map<String,Object> addDict(DataDict dict)throws Exception;

    /**
     * 根据字典id修改数据字典信息
     * @param dict 数据字典对象
     * @return 包含成功与否的字符串的map集合
     */
    Map<String,Object> modifyDict(DataDict dict)throws Exception;

    /**
     * 根据字典id删除字典信息
     * @param dictId 字典id
     * @return 包含成功与否的字符串的map集合
     */
    Map<String,Object> delDict(String dictId)throws Exception;
    
    /**
     * 根据字典id查询字典下节点数量，判断是否可删除
     * @param dictId 字典id
     * @return 包含true或false
     */
    public  boolean selectidcount(Map<String,Object> map);
    /**
     * 根据考核类型查询权重信息
     * @param map 考核类型
     */
    String  selectqz(Map<String,Object> map);
    /**
     * 根据gs标识查询公示值
     * @param map 公示值
     */
    List<DataDict> selectisgs(Map<String,Object> map) ;
    
    /**
     * 根据类型与标示获取数据字典信息
     * @param type 类型
     * @param sign 标示
     * @return 数据字典结果集
     */
    List<DataDict> getDataDictBylxSign(String type,String sign) throws Exception;
}
