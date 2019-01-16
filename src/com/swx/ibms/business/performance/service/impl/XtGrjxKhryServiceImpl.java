package com.swx.ibms.business.performance.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.swx.ibms.business.performance.bean.XtGrjxRypz;
import com.swx.ibms.business.performance.mapper.XtGrjxRypzMapper;
import com.swx.ibms.business.performance.service.XtGrjxKhryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("all")
@Service("xtGrjxKhryService")
public class XtGrjxKhryServiceImpl implements XtGrjxKhryService {

    @Autowired
    private XtGrjxRypzMapper xtGrjxKhryMapper;


    @Override
    public int deleteByPrimaryKey(String id) {
        Integer res = 0;
        try{
            res = xtGrjxKhryMapper.deleteByPrimaryKey(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public int insert(XtGrjxRypz record) {
        return 0;
    }

    @Override
    public int insertSelective(XtGrjxRypz record) {
        Integer res = 0;
        try{
            res = xtGrjxKhryMapper.insertSelective(record);
        }catch (Exception e){
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public XtGrjxRypz selectByPrimaryKey(String id) {
        return null;
    }

    @Override
    public int updateByPrimaryKeySelective(XtGrjxRypz record) {
        Integer res = 0;
        try{
            res = xtGrjxKhryMapper.updateByPrimaryKeySelective(record);
        }catch (Exception e){
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public int updateByPrimaryKey(XtGrjxRypz record) {
        return 0;
    }

    @Override
    public Map<String, Object> getGrjxKhryPzList(String dwbm, Integer page, Integer pageSize) {
        Map<String,Object> resMap = new HashMap<String,Object>();
        List<XtGrjxRypz> listInfo = new ArrayList<>();
        Page<XtGrjxRypz> pager = PageHelper.startPage(page, pageSize);
        try{
            listInfo = xtGrjxKhryMapper.getGrjxKhryPzList(dwbm);
        }catch (Exception e){
            e.printStackTrace();
        }
        resMap.put("total",pager.getTotal());
        resMap.put("rows",listInfo);
        return resMap;
    }

    @Override
    public List<XtGrjxRypz> getGrjxKhryPzByDwGh(String dwbm, String gh) {
        List<XtGrjxRypz> listInfo = new ArrayList<>();
        try{
            listInfo = xtGrjxKhryMapper.getGrjxKhryPzByDwGh(dwbm,gh);
        }catch (Exception e){
            e.printStackTrace();
        }
        return listInfo;
    }
}
