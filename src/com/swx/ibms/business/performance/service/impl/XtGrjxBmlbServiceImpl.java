package com.swx.ibms.business.performance.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.swx.ibms.business.performance.bean.XtGrjxBmlb;
import com.swx.ibms.business.performance.mapper.XtGrjxBmlbMapper;
import com.swx.ibms.business.performance.service.XtGrjxBmlbService;
import com.swx.ibms.common.utils.Identities;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.swx.ibms.common.utils.DateUtil.getSysCurrentDateTime;


/**
 * Created by xuwl on 2018/9/13.
 */
@Service("xtGrjxBmlb")
public class XtGrjxBmlbServiceImpl implements XtGrjxBmlbService {

    /**
     * 日志对象
     */
    private static final Logger LOG = Logger.getLogger(XtGrjxBmlbServiceImpl.class);

    /**
     * 部门类别mapper
     */
    @Resource
    private XtGrjxBmlbMapper xtGrjxBmlbMapper;

    @Override
    public int deleteBmlb(String id) {

        int res = 0;
        try {
            res = xtGrjxBmlbMapper.deleteBmlb(id);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error(StringUtils.EMPTY, e);
        }
        return res;
    }

    @Override
    public int insertBmlb(XtGrjxBmlb record) {
        int res = 0;

        String id = Identities.get32LenUUID();
        String bmlbmc = record.getBmlbmc();

//        int flag = 0;
//        try {
//            flag = xtGrjxBmlbMapper.isExistBmlbmc(bmlbmc);
//        } catch (Exception e) {
//            e.printStackTrace();
//            LOG.error(StringUtils.EMPTY, e);
//        }
//        if (flag > 0) {
//            return res;
//        }

        String bmlbbm = "";
        // 编码
        int bm = 0;
        try {
            bmlbbm = xtGrjxBmlbMapper.getMaxBmlbbm();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (StringUtils.isEmpty(bmlbbm)) {
            bm = 1;
            bmlbbm = bm + "";
        } else {
            bm = Integer.parseInt(bmlbbm) + 1;
            bmlbbm = bm + "";
        }


        String cjr = record.getCreateby();
        String cjsj = getSysCurrentDateTime();

        String ms = record.getRemarks();
        Integer xh = record.getXh();

        try {
            res = xtGrjxBmlbMapper.insertBmlb(id,bmlbmc,bmlbbm,cjr,cjsj,ms,xh);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error(StringUtils.EMPTY, e);
        }
        return res;
    }

    @Override
    public int updateBmlb(XtGrjxBmlb record) {
        int res = 0;

        String id = record.getId();
        String bmlbmc = record.getBmlbmc();

//        int flag = 0;
//        try {
//            flag = xtGrjxBmlbMapper.isExistBmlbmc(bmlbmc);
//        } catch (Exception e) {
//            e.printStackTrace();
//            LOG.error(StringUtils.EMPTY, e);
//        }
//        if (flag > 1) {
//            return res;
//        }

        String gxr = record.getUpdateby();
        String gxsj = getSysCurrentDateTime();


        String ms = record.getRemarks();
        Integer xh = record.getXh();

        try {
            res = xtGrjxBmlbMapper.updateBmlb(id, bmlbmc, gxr, gxsj,ms,xh);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error(StringUtils.EMPTY, e);
        }
        return res;
    }

    @Override
    public Map<String, Object> selectAllBmlb(Integer page, Integer rows) {

        Map<String, Object> resMap = new HashMap<>();
        List<Map<String, Object>> bmlbList = new ArrayList<>();
        Page pager = PageHelper.startPage(page, rows);
        try {
            bmlbList = xtGrjxBmlbMapper.selectAllBmlb();
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error(StringUtils.EMPTY, e);
        }

        resMap.put("total", pager.getTotal());
        resMap.put("rows", bmlbList);
        return resMap;
    }

    @Override
    public List<XtGrjxBmlb> getGrjxBmlbList() {
        List<XtGrjxBmlb> listInfo = new ArrayList<>();
        try{
            listInfo = xtGrjxBmlbMapper.getGrjxBmlbList();
        }catch (Exception e){
            e.printStackTrace();
        }
        return listInfo;
    }

    @Override
    public XtGrjxBmlb getBmlbByBmlbbm(String bmlbbm) {
        XtGrjxBmlb bmlb = new XtGrjxBmlb();
        try{
            bmlb = xtGrjxBmlbMapper.getBmlbByBmlbbm(bmlbbm);
        }catch (Exception e){
            e.printStackTrace();
        }
        return bmlb;
    }
}
