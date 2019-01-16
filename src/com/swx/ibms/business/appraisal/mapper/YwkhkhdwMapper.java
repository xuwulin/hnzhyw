package com.swx.ibms.business.appraisal.mapper;

import com.swx.ibms.business.appraisal.bean.Ywkhkhdw;

import java.util.List;
import java.util.Map;

/**
 * Created by Try on 2017/11/1.
 */
public interface YwkhkhdwMapper {

    /**
     * 查询全部业务考核中参与考核的单位
     * @param map 单位编码
     * @return 考核单位结果集
     */
    List<Ywkhkhdw> getPageListKhdw(Map<String,Object> map) throws Exception;

    /**
     * 根据单位编码获取单位级别
     * @param map 单位编码
     * @return 传入单位编码对应的单位信息
     */
    List<Map<String,Object>> getDwjbByDwbm(Map<String,Object> map)throws Exception;

    /**
     * 添加或者修改考核单位信息
     * @param map 单位考核信息
     * @throws Exception 异常
     */
    void addKhdw(Map<String,Object> map)throws Exception;

    /**
     * 根据考核编码修改考核单位状态实现逻辑删除
     * @param map 单位编码
     * @throws Exception 异常
     */
    void modifyKhdw(Map<String,Object> map)throws Exception;

    /**
     * 根据考核单位或者父单位编码获取考核信息
     * @param map 考核单位、父单位编码
     * @throws Exception 异常
     */
    void getKhdwByDw(Map<String,Object> map) throws Exception;
}
