package com.swx.ibms.business.performance.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.swx.ibms.business.performance.bean.YxGrjxGsxx;
import com.swx.ibms.business.performance.mapper.YxGrjxGsxxMapper;
import com.swx.ibms.business.performance.service.YxGrjxGsxxService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("all")
@Service("yxGrjxGsxxService")
public class YxGrjxGsxxServiceImpl implements YxGrjxGsxxService {

    @Resource
    private YxGrjxGsxxMapper yxGrjxGsxxMapper;

    @Override
    public Integer deleteByPrimaryKey(String id) {
        return null;
    }

    @Override
    public Integer insert(YxGrjxGsxx record) {
        Integer result = 0 ;
        try {
            result = yxGrjxGsxxMapper.insert(record);
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Integer insertSelective(YxGrjxGsxx record) {
        Integer result = 0 ;
        try {
            result = yxGrjxGsxxMapper.insertSelective(record);
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public YxGrjxGsxx selectByPrimaryKey(String id) {
        YxGrjxGsxx gsxx = new YxGrjxGsxx();
        try {
            gsxx = yxGrjxGsxxMapper.selectByPrimaryKey(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return gsxx;
    }

    @Override
    public YxGrjxGsxx selectByOtherKey(String jxkhid) {
        YxGrjxGsxx gsxx = new YxGrjxGsxx();
        try {
            gsxx = yxGrjxGsxxMapper.selectByOtherKey(jxkhid);
        }catch (Exception e){
            e.printStackTrace();
        }
        return gsxx;
    }

    @Override
    public Integer updateByPrimaryKeySelective(YxGrjxGsxx record) {
        Integer result = 0 ;
        try {
            result = yxGrjxGsxxMapper.updateByPrimaryKeySelective(record);
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Integer updateByPrimaryKey(YxGrjxGsxx record) {
        Integer result = 0 ;
        try {
            result = yxGrjxGsxxMapper.updateByPrimaryKey(record);
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<YxGrjxGsxx> selectByParams(YxGrjxGsxx record) {
        List<YxGrjxGsxx> gsxxList = new ArrayList<>();
        try {
            gsxxList = yxGrjxGsxxMapper.selectByParams(record);
        }catch (Exception e){
            e.printStackTrace();
        }
        return gsxxList;
    }

    @Override
    public List<YxGrjxGsxx> selectAll() {
        List<YxGrjxGsxx> gsxxList = new ArrayList<>();
        try {
            gsxxList = yxGrjxGsxxMapper.selectAll();
        }catch (Exception e){
            e.printStackTrace();
        }
        return gsxxList;
    }

    @Override
    public Map<String, Object> selectPageList(Integer page,Integer pageSize) {
        Map<String,Object> resMap = new HashMap<String,Object>();
        List<YxGrjxGsxx> listInfo = new ArrayList<>();
        Page<YxGrjxGsxx> pager = PageHelper.startPage(page, pageSize);
        try{
            listInfo = this.selectAll();
        }catch (Exception e){
            e.printStackTrace();
        }
        resMap.put("total",pager.getTotal());
        resMap.put("rows",listInfo);
        return resMap;
    }
}
