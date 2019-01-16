package com.swx.ibms.business.system.service;


import com.swx.ibms.business.common.utils.Constant;
import com.swx.ibms.business.system.bean.DataDict;
import com.swx.ibms.business.system.mapper.DataDictMapper;
import com.swx.ibms.common.bean.TreeNode;
import com.swx.ibms.common.utils.Identities;
import com.swx.ibms.common.utils.PageCommon;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by Try on 2017/11/7.
 */
@SuppressWarnings("all")
@Service("dataDictService")
public class DataDictServiceImpl implements DataDictService {

    @Resource
    private DataDictMapper dataDictMapper;

    @Override
    public List<DataDict> getDictByPid(String pid) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>(Constant.NUM_3);
        map.put("p_pid",pid);
        map.put("p_cursor", StringUtils.EMPTY);

        dataDictMapper.getDictByPid(map);

        List<DataDict> dictList = (List<DataDict>)map.get("p_cursor");
        return dictList;
    }

    @Override
    public TreeNode getDictTree() throws Exception{
        List<DataDict> dictList = this.getDictByPid(StringUtils.EMPTY);//查询pid为null的对象
        //最高的树节点--根节点
        TreeNode rootTreeNode = new TreeNode();
        rootTreeNode.setId(StringUtils.EMPTY);
        rootTreeNode.setText("所有数据字典");
        rootTreeNode.setOpen("true");
        rootTreeNode.setValueSelect(true);
        
        List<TreeNode> dictChildren = new ArrayList<>();
        DataDict dict = null; 
        int dictSize = dictList.size();
        for(int i=0;i<dictSize;i++){
        	TreeNode subNode =  new TreeNode();
        	dict=dictList.get(i);
        	if(dict.getDictFid()==null){
	        	subNode.setId(dict.getDictId());
	        	subNode.setText(dict.getDictName());
	        	subNode.setValueSelect(true);  
	        	dictChildren.add(subNode);
	          	this.SubNode(subNode);
        	}  
        	rootTreeNode.setChidren(dictChildren);                 
        }    
        return rootTreeNode;

    }

    //查询子节点下得数据
    private void SubNode(TreeNode node) throws Exception{   	    
	    List<DataDict> children = this.getDictByPid(node.getId());
	    List<TreeNode> nodeList = new ArrayList<>();
	    DataDict child= null;
	    int childrenSize = children.size();
		for(int i=0;i<childrenSize;i++){
			 TreeNode childnode =  new TreeNode();	
			 child=children.get(i);
			 if(child.getDictFid().equals(node.getId())){
			    childnode.setId(child.getDictId());
			    childnode.setText(child.getDictName());
			    nodeList.add(childnode);
			    SubNode(childnode);
			 }    	         	 	        	
			node.setChidren(nodeList);
		}          	           	        	
    }
    
    
    @Override
    public PageCommon<Object> getPageListDict(String pid, Integer page, Integer pageSize)throws Exception {
        PageCommon<Object> pageCommon = new PageCommon<Object>();
        Map<String,Object> map = new HashMap<String,Object>(Constant.NUM_6);

        int nowPage = (0==page? Constant.NUM_1:page);
        int pageStart = (nowPage - Constant.NUM_1) * pageSize;
        int pageEnd = pageStart+pageSize;
        pageStart++;

        map.put("p_pid", pid);
        map.put("p_start", pageStart);
        map.put("p_end", pageEnd);
        map.put("p_total", StringUtils.EMPTY);
        map.put("p_cursor", StringUtils.EMPTY);

        dataDictMapper.getPageListDict(map);
        // 得到page对象、总记录数、
        List<Object> list = (List<Object>) map.get("p_cursor");
        pageCommon.setList(list);
        pageCommon.setTotalRecords(Integer.parseInt(map.get("p_total").toString()));
        return pageCommon;
    }

    @Override
    public Map<String, Object> addDict(DataDict dict)throws Exception {
        Map<String, Object> paramMap = new HashMap<String,Object>(Constant.NUM_12);
        Map<String, Object> resMap = new HashMap<String,Object>(Constant.NUM_2);
        paramMap.put("p_id", Identities.get32LenUUID());
        paramMap.put("p_fid",dict.getDictFid());
        paramMap.put("p_name",dict.getDictName());
        paramMap.put("p_ident",dict.getDictIdentifying());
        paramMap.put("p_type", dict.getDictType());
        paramMap.put("p_type_value", dict.getDictTypeValue());
        paramMap.put("p_explain", dict.getDictExplain());
        paramMap.put("p_status", "0");  //这儿是数字零 非字母大写O
        paramMap.put("p_createTime", new Date());
        paramMap.put("p_dwbm",dict.getDictOperateDwbm());
        paramMap.put("p_mc",dict.getDictOperator());
        paramMap.put("p_errMsg",StringUtils.EMPTY);

        dataDictMapper.addDict(paramMap);
        String msg = (String)paramMap.get("p_errMsg");
        if("1".equals(msg)){
            resMap.put("msg","操作成功！");
        }else{
            resMap.put("msg","操作失败！");
        }
        return resMap;
    }

    @Override
    public Map<String, Object> modifyDict(DataDict dict) throws Exception{
        Map<String, Object> paramMap = new HashMap<String,Object>(Constant.NUM_12);
        Map<String, Object> resMap = new HashMap<String,Object>(Constant.NUM_2);
        paramMap.put("p_id",dict.getDictId());
        paramMap.put("p_fid",dict.getDictFid());
        paramMap.put("p_name",dict.getDictName());
        paramMap.put("p_ident",dict.getDictIdentifying());
        paramMap.put("p_type",dict.getDictType());
        paramMap.put("p_type_value",dict.getDictTypeValue());
        paramMap.put("p_explain",dict.getDictExplain());
        paramMap.put("p_status","0");  //这儿是数字零 非字母大写O
        paramMap.put("p_modifyTime", new Date());
        paramMap.put("p_dwbm",dict.getDictOperateDwbm());
        paramMap.put("p_mc",dict.getDictOperator());

        dataDictMapper.modifyDict(paramMap);
        String msg = (String)paramMap.get("p_errMsg");
        if("1".equals(msg)){
            resMap.put("msg","操作成功！");
        }else{
            resMap.put("msg","操作失败！");
        }
        return resMap;
    }

    @Override
    public Map<String, Object> delDict(String dictId)throws Exception {
        Map<String, Object> paramMap = new HashMap<String,Object>(Constant.NUM_3);
        Map<String, Object> resMap = new HashMap<String,Object>(Constant.NUM_2);
        paramMap.put("p_id",dictId);
        paramMap.put("p_errMsg",StringUtils.EMPTY);

        dataDictMapper.delDict(paramMap);
        String msg = (String)paramMap.get("p_errMsg");
        if("1".equals(msg)){
            resMap.put("msg","操作成功！");
        }else{
            resMap.put("msg","操作失败！");
        }
        return resMap;
    }

	@Override
	public boolean selectidcount(Map<String, Object> map) {
		dataDictMapper.selectidcount(map);		 
		  if(map.get("P_COUNT").equals("1")){
				return true;
			}
			return false;
	}

	@Override
	public String selectqz(Map<String, Object> map) {
		// TODO Auto-generated method stub
		map.put("P_VALUE", StringUtils.EMPTY);
		map.put("P_ERRMSG", StringUtils.EMPTY);
		dataDictMapper.selectqz(map);
		  if("1".equals(map.get("P_ERRMSG").toString())){
				return  (String)map.get("P_VALUE");
			}
		return "未获取到权重！";
	}
	@Override
	public List<DataDict> selectisgs(Map<String, Object> map) {
		   dataDictMapper.selectisgs(map);
		   List<DataDict> dictList=new ArrayList<DataDict>();
		   if(null!=map.get("P_CURSOR")){
			   dictList = (List<DataDict>)map.get("P_CURSOR");
		   }
	      return dictList;
	}

	@Override
	public List<DataDict> getDataDictBylxSign(String type, String sign) throws Exception {
		Map<String, Object> paramMap = new HashMap<String,Object>(Constant.NUM_4);
		paramMap.put("p_type", type);
		paramMap.put("p_sign", sign);
		paramMap.put("p_cursor", StringUtils.EMPTY);
		dataDictMapper.getDataDictBylxSign(paramMap);
		List<DataDict> dictList = (List<DataDict>)paramMap.get("p_cursor");
		return dictList;
	}
}
