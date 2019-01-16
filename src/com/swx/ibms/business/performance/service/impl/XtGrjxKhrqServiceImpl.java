package com.swx.ibms.business.performance.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.swx.ibms.business.performance.bean.XtGrjxKhrq;
import com.swx.ibms.business.performance.mapper.XtGrjxKhrqMapper;
import com.swx.ibms.business.performance.service.XtGrjxKhrqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("xtGrjxKhrqService")
@SuppressWarnings("all")
public class XtGrjxKhrqServiceImpl implements XtGrjxKhrqService {

    @Autowired
    private XtGrjxKhrqMapper xtGrjxKhrqMapper;

    @Override
    public Map<String, Object> getGrjxKhrqPzPageList(Integer page, Integer pageSize) {
        Map<String,Object> resMap = new HashMap<String,Object>();
        List<XtGrjxKhrq> listInfo = new ArrayList<>();
        Page<XtGrjxKhrq> pager = PageHelper.startPage(page, pageSize);
        try{
            listInfo = xtGrjxKhrqMapper.getGrjxKhrqPzList();
        }catch (Exception e){
            e.printStackTrace();
        }
        resMap.put("total",pager.getTotal());
        resMap.put("rows",listInfo);
        return resMap;
    }

    @Override
    public int insertSelective(XtGrjxKhrq record) {
        Integer result = 0 ;
        try{
            result = xtGrjxKhrqMapper.insertSelective(record);
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int updateByPrimaryKeySelective(XtGrjxKhrq record) {
        Integer result = 0 ;
        try{
            result = xtGrjxKhrqMapper.updateByPrimaryKeySelective(record);
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int deleteByPrimaryKey(String id) {
        Integer result = 0 ;
        try{
            result = xtGrjxKhrqMapper.deleteByPrimaryKey(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public XtGrjxKhrq getGrjxKhrqPzLatest() {
        XtGrjxKhrq khrqpz = new XtGrjxKhrq();
        try{
            khrqpz = xtGrjxKhrqMapper.getGrjxKhrqPzLatest();
        }catch (Exception e){
            e.printStackTrace();
        }
        return khrqpz;
    }
}
