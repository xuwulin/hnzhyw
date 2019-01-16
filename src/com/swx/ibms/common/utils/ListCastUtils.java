package com.swx.ibms.common.utils;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * List转换工具
 * @author 李维健
 * 2017年9月9日
 */
public class ListCastUtils {
    
    /**
     * 抽取List<Map<String,String>的values值
     * 
     * @return 返回list集合
     * 
     * @param 传入一个List<Map<String,String>>
     */
    public static List<String> castListMap(final List<Map<String,String>> param){
        
        //将list<Map>转为流并获取其value值转为新的流
        List<String> values = param.stream().flatMap(item -> {
        	
            return item.values().stream();
            //将流转为新的list
        }).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        
        return values;
    }
    
    /**
     * 合并多个list<Map>中的map
     * 
     * @param targetList 保留list
     * 
     * @param name 相同类型map的key名
     * 
     * @param extractList 被合并的list
     */
    public static void mergeListMap(List<Map<String,Object>> targetList,
            String name, List<Map<String,Object>> extractList){

        //遍历目标list
        targetList.forEach(item -> {
            
            //根据相同类型Key获取目标list相同类型的值
            String baseMapValue = MapUtils.getString(item, name, StringUtils.EMPTY);
            
            //获取两个list<Map>中具有相同类型的map
            Map map = extractList.stream().filter(param -> {
                String extractMapValue = MapUtils.getString(param, name, StringUtils.EMPTY);
                return StringUtils.equals(baseMapValue, extractMapValue);
            }).findFirst().orElse(Collections.EMPTY_MAP);
            
            //将获取的map合并到目标list中
            item.putAll(map);
        
        });
    }
    
}
