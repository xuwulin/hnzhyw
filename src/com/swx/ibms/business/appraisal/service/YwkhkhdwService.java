package com.swx.ibms.business.appraisal.service;

import com.swx.ibms.business.appraisal.bean.Ywkhkhdw;
import com.swx.ibms.common.bean.TreeNode;
import com.swx.ibms.common.utils.PageCommon;

import java.util.Map;

/**
 * Created by Try on 2017/11/1.
 */
public interface YwkhkhdwService {

    /**
     * 查询所有参与考核的单位并分页
     * @param dwbm 单位编码
     * @param page 当前开始行数
     * @param pageSize 结束行数
     * @return 符合条件的考核单位结果集
     */
    PageCommon<Object> getPageListKhdw(String dwbm, Integer page, Integer pageSize)throws Exception;

    /**
     * 根据单位编码获取单位级别
     * @param dwbm 单位编码
     * @return 单位编码所对应的单位信息
     */
    Map<String,Object> getDwjbByDwbm(String dwbm)throws Exception;

    /**
     * 添加或者修改被考核单位信息
     * @param khdw 考核单位实体类
     * @return 含有是否成功字符串的map
     * @throws Exception 异常
     */
    Map<String,Object> addKhdw(Ywkhkhdw khdw)throws Exception;

    /**
     * 根据考核编码修改考核单位状态实现逻辑删除
     * @param dwbm 单位编码
     * @return 含有是否成功字符串的map
     * @throws Exception 异常
     */
    Map<String,Object> modifyKhdw(String dwbm)throws Exception;

    /**
     * 根据考核单位查询考核单位信息
     * @param khdw 考核单位
     * @return 考核单位树
     */
    TreeNode getKhdwTree(String khdw)throws Exception;
}
