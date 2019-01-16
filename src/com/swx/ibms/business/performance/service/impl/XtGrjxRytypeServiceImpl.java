package com.swx.ibms.business.performance.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.swx.ibms.business.performance.bean.XtGrjxRytype;
import com.swx.ibms.business.performance.mapper.XtGrjxRytypeMapper;
import com.swx.ibms.business.performance.service.XtGrjxRytypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("all")
@Service("xtGrjxRytypeService")
public class XtGrjxRytypeServiceImpl implements XtGrjxRytypeService {

    @Autowired
    private XtGrjxRytypeMapper xtGrjxRytypeMapper;

    @Override
    public int deleteByPrimaryKey(String id) {
        Integer res = 0;
        try{
            res = xtGrjxRytypeMapper.deleteByPrimaryKey(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public int insert(XtGrjxRytype record) {
        return 0;
    }

    @Override
    public int insertSelective(XtGrjxRytype record) {
        Integer res = 0;
        try{
            res = xtGrjxRytypeMapper.insertSelective(record);
        }catch (Exception e){
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public XtGrjxRytype selectByPrimaryKey(String id) {
        XtGrjxRytype type = new XtGrjxRytype();
        try{
            type = xtGrjxRytypeMapper.selectByPrimaryKey(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return type;
    }

    @Override
    public int updateByPrimaryKeySelective(XtGrjxRytype record) {
        Integer res = 0;
        try{
            res = xtGrjxRytypeMapper.updateByPrimaryKeySelective(record);
        }catch (Exception e){
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public int updateByPrimaryKey(XtGrjxRytype record) {
        return 0;
    }

    @Override
    public Map<String, Object> getGrjxKhryTypePzPageList(Integer page, Integer pageSize) {
        Map<String,Object> resMap = new HashMap<String,Object>();
        List<XtGrjxRytype> listInfo = new ArrayList<>();
        Page<XtGrjxRytype> pager = PageHelper.startPage(page, pageSize);
        try{
            listInfo = xtGrjxRytypeMapper.getGrjxKhryTypePzList();
        }catch (Exception e){
            e.printStackTrace();
        }
        resMap.put("total",pager.getTotal());
        resMap.put("rows",listInfo);
        return resMap;
    }

    @Override
    public List<XtGrjxRytype> getGrjxKhryTypePzList() {
        List<XtGrjxRytype> listInfo = new ArrayList<>();
        try{
            listInfo = xtGrjxRytypeMapper.getGrjxKhryTypePzList();
        }catch (Exception e){
            e.printStackTrace();
        }
        return listInfo;
    }

    @Override
    public List<XtGrjxRytype> getListKhryType() {
        List<XtGrjxRytype> listInfo = new ArrayList<>();
        try{
            listInfo = xtGrjxRytypeMapper.getListKhryTypeByKhzb();
        }catch (Exception e){
            e.printStackTrace();
        }
        return listInfo;
    }
}
