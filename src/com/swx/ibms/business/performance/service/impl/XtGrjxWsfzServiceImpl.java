package com.swx.ibms.business.performance.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.swx.ibms.business.performance.bean.XtGrjxWsfz;
import com.swx.ibms.business.performance.mapper.XtGrjxWsfzMapper;
import com.swx.ibms.business.performance.service.XtGrjxWsfzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("all")
@Service("xtGrjxWsfzService")
public class XtGrjxWsfzServiceImpl implements XtGrjxWsfzService {

    @Autowired
    private XtGrjxWsfzMapper xtGrjxWsfzMapper;

    @Override
    public int deleteByPrimaryKey(String id) {
        Integer res = 0;
        try{
            res = xtGrjxWsfzMapper.deleteByPrimaryKey(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public int insert(XtGrjxWsfz record) {
        return 0;
    }

    @Override
    public int insertSelective(XtGrjxWsfz record) {
        Integer res = 0;
        try{
            res = xtGrjxWsfzMapper.insertSelective(record);
        }catch (Exception e){
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public XtGrjxWsfz selectByPrimaryKey(String id) {
        XtGrjxWsfz wsfz = new XtGrjxWsfz();
        try{
            wsfz = xtGrjxWsfzMapper.selectByPrimaryKey(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return wsfz;
    }

    @Override
    public int updateByPrimaryKeySelective(XtGrjxWsfz record) {
        Integer res = 0;
        try{
            res = xtGrjxWsfzMapper.updateByPrimaryKeySelective(record);
        }catch (Exception e){
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public int updateByPrimaryKey(XtGrjxWsfz record) {
        return 0;
    }

    @Override
    public Map<String, Object> getXtGrjxWsfzAllPageList(Integer page,Integer pageSize) {
        Map<String,Object> resMap = new HashMap<String,Object>();
        List<XtGrjxWsfz> listInfo = new ArrayList<>();
        Page<XtGrjxWsfz> pager = PageHelper.startPage(page, pageSize);
        listInfo = this.getXtGrjxWsfzAllList();
        resMap.put("total",pager.getTotal());
        resMap.put("rows",listInfo);
        return resMap;
    }

    @Override
    public List<XtGrjxWsfz> getXtGrjxWsfzAllList() {
        List<XtGrjxWsfz> listInfo = new ArrayList<>();
        try{
            listInfo = xtGrjxWsfzMapper.getXtGrjxWsfzAllList();
        }catch (Exception e){
            e.printStackTrace();
        }
        return listInfo;
    }

    @Override
    public List<XtGrjxWsfz> queryResultByParams(XtGrjxWsfz record) {
        List<XtGrjxWsfz> listInfo = new ArrayList<>();
        try{
            listInfo = xtGrjxWsfzMapper.queryResultByParams(record);
        }catch (Exception e){
            e.printStackTrace();
        }
        return listInfo;
    }

    @Override
    public List<XtGrjxWsfz> queryWsfzByFz(String bmlbbm, double wsfz) {
        List<XtGrjxWsfz> listInfo = new ArrayList<>();
        try{
            listInfo = xtGrjxWsfzMapper.queryWsfzByFz(bmlbbm, wsfz);
        }catch (Exception e){
            e.printStackTrace();
        }
        return listInfo;
    }
}
