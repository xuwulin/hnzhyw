package com.swx.ibms.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * 通用集合工具类
 * @author admin
 *
 */
public class CollectionUtil {
	
	/**
	 * 移除集合中空的元素【特别是excel读取数据，最后一行有空数据时】
	 * @param list
	 * @return
	 */
	public static List<Map<String,Object>> returnNewList(List<Map<String,Object>> list) {
		List<Map<String,Object>> tempList = new ArrayList<Map<String,Object>>();
		if(list!=null&&!list.isEmpty()) {
			int listSize = list.size();
			Map<String, Object> lastData = list.get(listSize-1);
			int r = 0;
			for (String key : lastData.keySet()) {
				if (!StringUtils.isNotBlank(lastData.get(key).toString())) {
					r+=1;
				}
			}
			if(listSize==r) {
				list.remove(listSize-1);
			}
			tempList = list;
		}
		return tempList;
	}


	/**
	 * 删除ArrayList中重复元素，保持顺序
	 * @param list
	 */
	public static void removeDuplicateWithOrder(List list) {
		Set set = new HashSet();
		List newList = new ArrayList();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Object element = iterator.next();
			if (set.add(element)){
				newList.add(element);
			}
		}
		list.clear();
		list.addAll(newList);
	}

}
