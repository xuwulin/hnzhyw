package com.swx.ibms.business.common.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.swx.ibms.business.archives.bean.Person;
import com.swx.ibms.business.common.bean.TreeSelect;
import com.swx.ibms.business.common.mapper.TreeSelectMapper;
import com.swx.ibms.business.common.utils.Constant;
import com.swx.ibms.business.performance.mapper.GrjxAjWsMapper;
import com.swx.ibms.business.rbac.bean.*;
import com.swx.ibms.business.rbac.service.BmmcService;
import com.swx.ibms.business.rbac.service.DepartmentService;
import com.swx.ibms.business.system.service.XTGLService;
import com.swx.ibms.common.bean.TreeNode;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 树形结构展示接口实现层
 *
 * @author 李佳
 * @date: 2017年3月5日
 */
@SuppressWarnings("all")
@Service("TreeSelectService")
public class TreeSelectServiceImpl implements TreeSelectService {

	/**
	 * 树形结构展示Mapper接口
	 */
	@Resource
	private TreeSelectMapper treeSelectMapper;

	/**
	 * 系统管理服务Mapper接口
	 */
	@Resource
	private XTGLService xtglService;

	/**
	 * 部门接口
	 */
	@Resource
	private DepartmentService deptService;

	/**
	 * 根据单位编号和工号返回部门名称实体类集合接口
	 */
	@Resource
	private BmmcService bmmcService;

	@Resource
	private GrjxAjWsMapper grjxAjWsMapper;

	/**
	 * 单位的树形结构数据
	 */
	private TreeNode dwTreeNode = null;

	/**
	 * 查询所有检查院
	 *
	 * @return Map
	 */
	@Override
	public Map allJcy() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Y", "");
		treeSelectMapper.allJcy(map);
		return map;

	}

	/**
	 * 根据父单位编码获取所有子单位编码
	 *
	 * @param fdwbm
	 *
	 * @return List
	 */
	@Override
	public List<TreeSelect> zDwbm(String fdwbm) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Y", "");
		map.put("fdwbm", fdwbm);
		treeSelectMapper.zDwbm(map);
		List<TreeSelect> list = (List<TreeSelect>) map.get("Y");
		for (TreeSelect e : list) {
			e.getDwbm();
			e.getDwmc();
		}
		return list;
	}

	/**
	 * 根据检查院编码查询出所有部门的编码和名称
	 *
	 * @param 单位bm
	 * @return Map
	 */
	@Override
	public Map department(String dwbm) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dwbm", dwbm);
		map.put("Y", "");
		treeSelectMapper.department(map);
		return map;
	}

	/**
	 * 根据单位、部门编码查询出所有角色的编码和名称
	 *
	 * @param 部门bm
	 * @return Map
	 */
	@Override
	public Map role(String dwbm, String bmbm) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dwbm", dwbm);
		map.put("bmbm", bmbm);
		map.put("Y", "");
		treeSelectMapper.role(map);
		return map;
	}

	/**
	 * 根据角色、单位、部门、查询下属的所有人员的工号和名称
	 *
	 * @param 角色bm
	 * @return Map
	 */
	@Override
	public Map person(String jsbm, String dwbm, String bmbm) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("jsbm", jsbm);
		map.put("dwbm", dwbm);
		map.put("bmbm", bmbm);
		map.put("Y", "");
		treeSelectMapper.person(map);

		return map;
	}

	@Override
	public TreeNode getDwTree(String rootDwbm) {

		// 获取当前单位信息
		List<DWBM> rootdw = xtglService.getDw(rootDwbm);

		TreeNode treeNode = new TreeNode();
		treeNode.setId(rootdw.get(0).getDwbm());
		treeNode.setText(rootdw.get(0).getDwmc());

		// 获取子单位信息
		List<TreeSelect> leafdw = this.zDwbm(rootDwbm);
		if (CollectionUtils.isNotEmpty(leafdw)) {// 转换为TreeNode类型的list

			TreeNode treeChildNode = null;
			List<TreeNode> children = new ArrayList<>();

			for (TreeSelect treeSelect : leafdw) {
				treeChildNode = this.getDwTree(treeSelect.getDwbm());
				children.add(treeChildNode);
			}

			treeNode.setOpen("false");
			treeNode.setChidren(children);
		}

		return treeNode;
	}

	@Override
	public TreeNode getZzjgTree(String rootDwbm, String gh) {

		// 获取单位节点
		TreeNode treedwNode = new TreeNode();
		treedwNode.setId(rootDwbm);

		// 获取单位名称
		String rootdwmc = bmmcService.getDwmc(rootDwbm);
		treedwNode.setText(rootdwmc);
		treedwNode.setValueSelect(false);

		// 获取部门列表
		List<Bmmc> bmlist = bmmcService.getBmmc(rootDwbm, gh);
		List<TreeNode> dwchildren = new ArrayList<>();
		// 获取部门节点
		for (Bmmc bm : bmlist) {
			TreeNode treebmNode = new TreeNode();
			treebmNode.setId(bm.getBmbm());
			treebmNode.setText(bm.getBmmc());
			// 将该部门设置为本单位子节点
			dwchildren.add(treebmNode);
			treebmNode.setValueSelect(false);

			// 获取角色列表
			Map<String, Object> jsmap = this.role(rootDwbm, bm.getBmbm());
			List<Role> rolelist = (List<Role>) jsmap.get("Y");

			treebmNode.setOpen("false");
			List<TreeNode> bmchildren = new ArrayList<>();
			// 获取角色节点
			for (Role role : rolelist) {
				TreeNode treeroleNode = new TreeNode();
				treeroleNode.setId(role.getJsbm());
				treeroleNode.setText(role.getJsmc());
				// 将该角色设置为本部门子节点
				bmchildren.add(treeroleNode);

				treeroleNode.setValueSelect(false);

				// 获取人员列表
				Map<String, Object> personmap = this.person(role.getJsbm(), rootDwbm, bm.getBmbm());
				List<Person> personlist = (List<Person>) personmap.get("Y");
				treeroleNode.setOpen("false");
				List<TreeNode> jschildren = new ArrayList<>();
				// 获取人员节点
				for (Person person : personlist) {
					if (!gh.equals(person.getGh())) {
						TreeNode treepersonNode = new TreeNode();
						treepersonNode.setId(person.getGh());
						treepersonNode.setText(person.getMc());
						// 将该人员设置为本角色的子节点
						jschildren.add(treepersonNode);
					}
				}
				treeroleNode.setChidren(jschildren);
			}
			treebmNode.setChidren(bmchildren);
		}
		treedwNode.setChidren(dwchildren);

		return treedwNode;
	}

	@Override
	public TreeNode getTreeNode(String rootDwbm, boolean all) {
		if (all) {
			if (Objects.isNull(dwTreeNode)) {
				dwTreeNode = this.getDwTree(rootDwbm);
			}
			return dwTreeNode;
		} else {
			return this.getDwTree(rootDwbm);
		}
	}

	@Override
	public TreeNode getBmtree(String dwmc, String rootdwbm) {
		List<Bmmc> list = bmmcService.getBmByDwbm(rootdwbm);
		TreeNode treedwNode = new TreeNode();
		treedwNode.setId(rootdwbm);
		treedwNode.setText("所有部门");// 这里应该放入本单位的单位名称
		treedwNode.setOpen("true");
		List<TreeNode> bmchildren = new ArrayList<>();
		int f = list.size();
		Bmmc bm = null;
		for (int i = 0; i < f; i++) {
			bm = list.get(i);
			TreeNode treebmNode = new TreeNode();
			treebmNode.setId(bm.getBmbm());
			treebmNode.setText(bm.getBmmc());
			treebmNode.setSource("1"); // 标识为1，该节点是部门节点
			// 将该部门设置为本单位子节点
			treebmNode.setValueSelect(false);
			List<TreeNode> jschildren = new ArrayList<>();
			List<JSBM> jslist = new ArrayList<>();
			jslist = bmmcService.getJsBybm(bm.getDwbm(), bm.getBmbm());
			for (int k = 0; k < jslist.size(); k++) { // 遍历部门下面所有的角色
				JSBM jsbm = jslist.get(k);
				TreeNode jsNode = new TreeNode();
				jsNode.setId(jsbm.getJsbm());
				jsNode.setText(jsbm.getJsmc());
				jsNode.setSource(jsbm.getBmbm());
				jschildren.add(jsNode);
			}
			treebmNode.setChidren(jschildren);
			if (jslist.size() != 0) {
				bmchildren.add(treebmNode);
			}
		}
		treedwNode.setChidren(bmchildren);
		return treedwNode;
	}

	/*
	 * 直接领导
	 */
	@Override
	public TreeNode getZjldTree(String rootDwbm, String gh) {
		// 该人的直接领导list
		List<Person> allzjldlist = selectAllzjld(rootDwbm, gh);
		// 获取单位节点
		TreeNode treedwNode = new TreeNode();
		treedwNode.setId(rootDwbm);

		// 获取单位名称
		String rootdwmc = bmmcService.getDwmc(rootDwbm);
		treedwNode.setText(rootdwmc);
		treedwNode.setValueSelect(false);

		// 获取部门列表
		List<Bmmc> bmlist = bmmcService.getAllBmmc(rootDwbm);
		List<TreeNode> dwchildren = new ArrayList<>();
		boolean dwY = false;
		// 获取部门节点
		for (Bmmc bm : bmlist) {
			TreeNode treebmNode = new TreeNode();
			treebmNode.setId(bm.getBmbm());
			treebmNode.setText(bm.getBmmc());

			// 获取角色列表
			Map<String, Object> jsmap = this.role(rootDwbm, bm.getBmbm());
			List<Role> rolelist = (List<Role>) jsmap.get("Y");

			treebmNode.setOpen("false");
			List<TreeNode> bmchildren = new ArrayList<>();
			boolean bmY = false;
			// 获取角色节点
			for (Role role : rolelist) {
				TreeNode treeroleNode = new TreeNode();
				treeroleNode.setId(role.getJsbm());
				treeroleNode.setText(role.getJsmc());

				// 获取人员列表
				Map<String, Object> personmap = this.person(role.getJsbm(), rootDwbm, bm.getBmbm());
				List<Person> personlist = (List<Person>) personmap.get("Y");
				treeroleNode.setOpen("false");
				List<TreeNode> jschildren = new ArrayList<>();
				boolean roleY = false;
				// 获取人员节点
				for (Person person : personlist) {
					for (Person zjld : allzjldlist) {
						if (person.getGh() != null && zjld.getGh() != null) {
							if (person.getGh().equals(zjld.getGh())) {
								TreeNode treepersonNode = new TreeNode();
								treepersonNode.setId(person.getGh());
								treepersonNode.setText(person.getMc());
								// 将该人员设置为本角色的子节点
								jschildren.add(treepersonNode);
								roleY = true;
								bmY = true;
								dwY = true;
							}
						}
					}
				}
				if (roleY) {
					// 将该角色设置为本部门子节点
					bmchildren.add(treeroleNode);
					treeroleNode.setOpen("true");
					treeroleNode.setChidren(jschildren);
				}
			}
			if (bmY) {
				// 将该部门设置为本单位子节点
				dwchildren.add(treebmNode);
				treebmNode.setOpen("true");
				treebmNode.setChidren(bmchildren);
			}
		}
		if (dwY) {
			treedwNode.setOpen("true");
			treedwNode.setChidren(dwchildren);
		}
		return treedwNode;
	}

	@Override
	public List<Person> selectAllzjld(String dwbm, String gh) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("p_dwbm", dwbm);
		map.put("p_gh", gh);
		map.put("p_zjld", null);
		try {
			treeSelectMapper.selectzjld(map);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return (List<Person>) map.get("p_zjld");
	}

	/**
	 * 获取部门领导的审批人树形结构
	 * @param dwbm 单位编码
	 * @param gh 工号
	 * @param bmjs 部门角色列表
	 * @return 树形结构
	 */
	@Override
	public TreeNode getTreeOfHeader(String dwbm,String gh,List<String> bmjs){
		TreeNode rootNode = new TreeNode();//根节点
		TreeNode fgldNode = new TreeNode();//分管领导节点
		TreeNode bmldNode = new TreeNode();//部门领导节点

		List<TreeNode> rootChildrenList = new ArrayList<TreeNode>();//根节点的分支列表
		List<TreeNode> fgldChildrenList = new ArrayList<TreeNode>();//分管领导的分支列表
		List<TreeNode> bmldChildrenList = new ArrayList<TreeNode>();//部门领导的分支列表

		fgldChildrenList = this.getFgldList(dwbm,gh,bmjs);
		bmldChildrenList = this.getbmldList(dwbm,gh,bmjs);

		fgldNode.setId("-2");
		fgldNode.setText("分管领导列表");
		fgldNode.setChidren(fgldChildrenList);
		fgldNode.setValueSelect(false);
		fgldNode.setOpen("true");

		bmldNode.setId("-3");
		bmldNode.setText("部门领导列表");
		bmldNode.setChidren(bmldChildrenList);
		bmldNode.setValueSelect(false);
		bmldNode.setOpen("true");

		if(fgldNode.getChidren().size()>0){
			rootChildrenList.add(fgldNode);
		}
		if(bmldNode.getChidren().size()>0){
			rootChildrenList.add(bmldNode);
		}

		rootNode.setId("-1");
		rootNode.setText("可选领导列表");
		rootNode.setOpen("true");
		rootNode.setChidren(rootChildrenList);
		rootNode.setValueSelect(false);

		return rootNode;
	}

	/**
	 * 获取分管领导Children数据
	 * @param dwbm 单位编码
	 * @param gh 工号
	 * @param bmjs 部门角色列表
	 * @return 分管领导Children的List数据
	 */
	private List<TreeNode> getFgldList(String dwbm,String gh,List<String> bmjs){
		//这个人在哪些部门下担任领导（部门角色为‘001’）,获取这些部门编码
		TreeSet<String> bmSet = new TreeSet<String>();
		for(int i=0;i<bmjs.size();i++){
			if("001".equals(bmjs.get(i).split(",")[2])){
				bmSet.add(bmjs.get(i).split(",")[1]);
			}
		}

		List<TreeNode> resultList = new ArrayList<TreeNode>();

		//遍历这些部门编码
		for(String bmbm : bmSet){
			//从分管领导表中查询出该部门的分管领导列表
			List<Map> fgldList = treeSelectMapper.getFgld(dwbm, bmbm);
			for(int i = 0;i<fgldList.size();i++){
				//遍历分管领导列表，从人员角色分配表中查询出这些分管领导分配信息，都取第一个作为标准查询其所属部门和角色
				String ldgh = (String)fgldList.get(i).get("LDGH");
				List<Map> fpjlList = treeSelectMapper.getFpJl2(dwbm, ldgh);
				String ldbmbm = (String)fpjlList.get(0).get("BMBM");
				String ldbmmc = treeSelectMapper.getBmmcByBmbm(dwbm, ldbmbm);

				String ldjsbm = (String)fpjlList.get(0).get("JSBM");
				String ldjsmc = treeSelectMapper.getJsmcByJsbm(dwbm,ldbmbm,ldjsbm);

				String ldname = treeSelectMapper.getNameByGh(dwbm, ldgh);

				//人员节点
				TreeNode personNode = new TreeNode();
				personNode.setId(ldgh);
				personNode.setText(ldname);

				List<TreeNode> jsChildrenList = new ArrayList<TreeNode>();
				jsChildrenList.add(personNode);

				//角色节点
				TreeNode jsNode = new TreeNode();
				jsNode.setId(ldjsbm);
				jsNode.setText(ldjsmc);
				jsNode.setChidren(jsChildrenList);
				jsNode.setValueSelect(false);

				List<TreeNode> bmChildren = new ArrayList<TreeNode>();
				bmChildren.add(jsNode);

				//部门节点
				TreeNode bmNode = new TreeNode();
				bmNode.setId(ldbmbm);
				bmNode.setText(ldbmmc);
				bmNode.setChidren(bmChildren);
				bmNode.setValueSelect(false);

				resultList.add(bmNode);
			}
		}

		return resultList;
	}

	/**
	 * 获取部门领导Children数据
	 * @param dwbm 单位编码
	 * @param gh 工号
	 * @param bmjs 部门角色列表
	 * @return 部门领导Children的List数据
	 */
	private List<TreeNode> getbmldList(String dwbm,String gh,List<String> bmjs){
		//获取哪些不是部门领导的部门编码
		TreeSet<String> bmSet = new TreeSet<String>();
		int bmjsSize = bmjs.size();
		for(int i=0;i<bmjsSize;i++){
			if(!"001".equals(bmjs.get(i).split(",")[2])){
				bmSet.add(bmjs.get(i).split(",")[1]);
			}
		}

		List<TreeNode> resultList = new ArrayList<TreeNode>();

		//遍历这些部门
		for(String bmbm : bmSet){
			String bmmc = treeSelectMapper.getBmmcByBmbm(dwbm,bmbm);
			TreeNode bmNode = new TreeNode();
			bmNode.setId(bmbm);
			bmNode.setText(bmmc);
			bmNode.setValueSelect(false);
			int count = 1;
			List<Map> ryjsfp = new ArrayList<Map>();
			//依次去获取人员角色分配信息，以免出现一个部门并未设置‘001’角色的情况
			while(CollectionUtils.isEmpty(ryjsfp)){
				ryjsfp = treeSelectMapper.getFpJl(dwbm,bmbm,"00"+count);
				count++;
			}

			TreeNode jsNode = new TreeNode();
			String jsbm = "00"+(count-1);
			String jsmc = treeSelectMapper.getJsmcByJsbm(dwbm,bmbm,jsbm);
			jsNode.setId(jsbm);
			jsNode.setText(jsmc);
			jsNode.setValueSelect(false);

			List<TreeNode> jsChildren = new ArrayList<TreeNode>();
			int ryjsfpSize = ryjsfp.size();
			for(int i = 0;i<ryjsfpSize;i++){
				String mc = treeSelectMapper.getNameByGh(dwbm,(String)ryjsfp.get(i).get("GH"));
				TreeNode personNode = new TreeNode();
				personNode.setId((String)ryjsfp.get(i).get("GH"));
				personNode.setText(mc);
				jsChildren.add(personNode);
			}
			jsNode.setChidren(jsChildren);

			List<TreeNode> bmChidren = new ArrayList<TreeNode>();
			bmChidren.add(jsNode);

			bmNode.setChidren(bmChidren);

			resultList.add(bmNode);
		}

		return resultList;
	}

	@Override
	public List<TreeNode> getBmbmSelectTree(String dwbm) {
		//这儿正确的做法应该调用部门的接口进行查询部门信息（时间紧迫来不及梳理），后续可修改为调用接口。
		List<TreeNode> treeNodeList = new ArrayList<TreeNode>();
		List<BMBM> bmlist = this.getAllBmbmByDw(dwbm);
		for (BMBM bmbm : bmlist) {
			//一级目录
			TreeNode rootTreeNode = new TreeNode();
			rootTreeNode.setId(bmbm.getBmbm());
			rootTreeNode.setText(bmbm.getBmmc());
			rootTreeNode.setOpen("true");
			rootTreeNode.setValueSelect(false);

			//查询该部门下的子部门
			this.setChildTreeNode(dwbm,rootTreeNode);

			treeNodeList.add(rootTreeNode);
		}
		return treeNodeList;
	}

	private void setChildTreeNode(String dwbm,TreeNode rootTreeNode) {
		List<BMBM> bmChildlist = this.getAllBmByDwBmbm(dwbm, rootTreeNode.getId());
		if(CollectionUtils.isNotEmpty(bmChildlist)&&bmChildlist.size()>0) {
			List<TreeNode> bmChildTreeNode = new ArrayList<TreeNode>();
			for (BMBM bmChild : bmChildlist) {
				//二级目录
				TreeNode secondNode = new TreeNode();
				secondNode.setId(bmChild.getBmbm());
				secondNode.setText(bmChild.getBmmc());
				secondNode.setValueSelect(false);
				secondNode.setOpen("false");
				bmChildTreeNode.add(secondNode);
				this.setChildTreeNode(dwbm, secondNode);
			}
			rootTreeNode.setChidren(bmChildTreeNode);
		}
	}

	private List<BMBM> getAllBmbmByDw(String dwbm){
		Map<String,Object> params = new HashMap<String,Object>(Constant.NUM_6);
		List<BMBM> bmList = new ArrayList<BMBM>();
		params.put("p_dwbm", dwbm);
		params.put("p_sfsc", StringUtils.EMPTY);
		params.put("p_cursor", StringUtils.EMPTY);
		try {
			treeSelectMapper.getAllBmbmByDw(params);
			bmList = (List<BMBM>)params.get("p_cursor");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bmList;
	}

	private List<BMBM> getAllBmByDwBmbm(String dwbm,String fbmbm){
		Map<String,Object> params = new HashMap<String,Object>(Constant.NUM_6);
		List<BMBM> bmList = new ArrayList<BMBM>();
		params.put("p_dwbm", dwbm);
		params.put("p_fbmbm", fbmbm);
		params.put("p_cursor", StringUtils.EMPTY);
		try {
			treeSelectMapper.getAllBmByDwBmbm(params);
			bmList = (List<BMBM>)params.get("p_cursor");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bmList;
	}

	@Override
	public List<TreeNode> getAllRyOfDwBmTree(String paramDwbm,String paramGh,String paramType) {
		//初始化【单位->部门（子部门）->角色->人员】树的集合
		List<TreeNode> treeNodeList = new ArrayList<TreeNode>();

		//查询单位名称【因此接口只会返回最多一条记录，遂下列获取第一条记录信息】
		List<DWBM> dwList = xtglService.getDw(paramDwbm);

		if(CollectionUtils.isNotEmpty(dwList)&&dwList.size()>0) {
			TreeNode rootTreeNode = new TreeNode();
			rootTreeNode.setId(dwList.get(0).getDwbm());
			rootTreeNode.setText(dwList.get(0).getDwmc());
			rootTreeNode.setOpen("true");
			rootTreeNode.setValueSelect(false);

			//依此显示的树结构：单位->部门->角色->人员
			//查询该登录人的所有部门
			List<Map<String, Object>> bmList = ListUtils.EMPTY_LIST;
			if("1".equals(paramType)) {
				bmList = deptService.getRyBmByDwGh(paramDwbm, paramGh);  //根据单位编码、工号获取此人的所有部门
			}else if("2".equals(paramType)) {
				bmList = this.getDwBmByParamDwBmGh(paramDwbm,StringUtils.EMPTY,StringUtils.EMPTY); //deptService.getRyBmByDwGh(paramDwbm,StringUtils.EMPTY);//根据单位编码查询某单位的所有部门
//				bmbmList = this.getAllXtBmInfo(paramDwbm,StringUtils.EMPTY);
			}else {
				bmList = deptService.getRyBmByDwGh(paramDwbm, paramGh);  //根据单位编码、工号获取此人的所有部门
			}

			if(CollectionUtils.isNotEmpty(bmList)&&bmList.size()>0) {
				List<TreeNode> bmNodeList = new ArrayList<TreeNode>();  //部门集合
				for (Map<String, Object> bmMap : bmList) {
//					if (!bmMap.get("BMBM").equals("0001")) { // 排除信息化工作办公室/系统管理员这个部门
						TreeNode secondTreeNode = new TreeNode();
						if("2".equals(paramType)) {  //如果显示单位下所有【部门->角色->人员】树，则部门以下的节点都默认关闭
							secondTreeNode.setState("closed");
						}
						String bmbm_name = "BMBM";
						String bmmc_name = "BMMC";
						this.setNextNodeBmTree(rootTreeNode.getId(),secondTreeNode,bmMap,bmbm_name,bmmc_name);

						bmNodeList.add(secondTreeNode);
//					}
				}

				if(bmNodeList.size()>0){
					rootTreeNode.setChidren(bmNodeList);
				}
			}
			treeNodeList.add(rootTreeNode);
		}

		return treeNodeList;
	}

	private void setNextNodeBmTree(String dwbm,TreeNode nodeTree, Map<String, Object> map, String id_name, String text_name) {
		List<TreeNode> allBmTreeNodeList = new ArrayList<>();
		for (String bmstr : map.keySet()) {
			if(StringUtils.equals(id_name, bmstr)||StringUtils.equals(text_name, bmstr)) {
				nodeTree.setId(map.get(id_name).toString());
				nodeTree.setText(map.get(text_name).toString());
				nodeTree.setValueSelect(false);
				nodeTree.setOpen("false");
//				nodeTree.setState("closed");
			}
		}
		//没有子部门的组装单位->部门->角色->人员树
		List<TreeNode> jsTreeNodeList = this.setJsTreeNode(dwbm,nodeTree.getId());
		if(jsTreeNodeList.size()>0){
			allBmTreeNodeList.addAll(jsTreeNodeList);
		}

		//查询该部门是否有子部门并组装成树
		List<Map<String,Object>> zbmList = this.getDwBmByParamDwBmGh(dwbm,StringUtils.EMPTY,nodeTree.getId());
		if(zbmList.size()>0){
			List<TreeNode> zbmTreeNodeList = new ArrayList<>();
			for (int i = 0; i < zbmList.size(); i++) {
				Map<String,Object> bmMap = zbmList.get(i);
				TreeNode childNodeTree = new TreeNode();
				for (String zbmStr : bmMap.keySet()) {
					if(StringUtils.equals(id_name, zbmStr)||StringUtils.equals(text_name, zbmStr)) {
						childNodeTree.setId(bmMap.get(id_name).toString());
						childNodeTree.setText(bmMap.get(text_name).toString());
						childNodeTree.setValueSelect(false);
						childNodeTree.setOpen("false");
					}
				}
				//返回角色的树
				List<TreeNode> jsNodeList = this.setJsTreeNode(dwbm,childNodeTree.getId());
				childNodeTree.setChildren(jsNodeList);

				zbmTreeNodeList.add(childNodeTree);
				this.setNextNodeBmTree(dwbm,childNodeTree,bmMap,id_name,text_name);
			}

			if(zbmTreeNodeList.size()>0){
				allBmTreeNodeList.addAll(zbmTreeNodeList);
			}
		}
//		CollectionUtil.removeDuplicateWithOrder(allBmTreeNodeList);
		nodeTree.setChildren(allBmTreeNodeList);
	}


	private List<TreeNode> setJsTreeNode(String dwbm,String bmbm){
		List<TreeNode> jsNodeList = new ArrayList<>(); //角色树集合

		//查询每个部门的所有角色【注意角色顺序】
		List<Map<String, Object>> jsList = deptService.getRyJsByDwBm(dwbm, bmbm);
		if(CollectionUtils.isNotEmpty(jsList)&&jsList.size()>0) {
			for (Map<String, Object> jsMap : jsList) {  //注意遍历的集合别搞混了
				TreeNode thirdTreeNode = new TreeNode();
				String jsbm_name = "JSBM";
				String jsmc_name = "JSMC";
				this.setNextNodeTree(thirdTreeNode,jsMap,jsbm_name,jsmc_name);

				//查询每个角色的人员
				List<TreeNode> ryNodeList = this.setJsTreeNode(dwbm,bmbm,thirdTreeNode.getId());
				if(ryNodeList.size()>0){
					thirdTreeNode.setChidren(ryNodeList);
				}
				jsNodeList.add(thirdTreeNode);
			}
		}

		return jsNodeList;
	}


	private List<TreeNode> setJsTreeNode(String dwbm,String bmbm,String jsbm){

		List<TreeNode> ryTreeNodeList = new ArrayList<>(); //人员集合
		List<Map<String, Object>> ryList = deptService.getRyByDwBmJs(dwbm, bmbm,jsbm);

		if(CollectionUtils.isNotEmpty(ryList)&&ryList.size()>0) {
			for (Map<String, Object> ryMap : ryList) {  //注意遍历的集合别搞混了
				TreeNode fourTreeNode = new TreeNode();
				String rybm_name = "GH";
				String rydlm_name = "DLBM";
				this.setNextNodeTree(fourTreeNode,ryMap,rybm_name,rydlm_name);
				ryTreeNodeList.add(fourTreeNode);
			}
		}
		return ryTreeNodeList;
	}

	/**
	 * 组装子节点的数据
	 * @param nodeTree 子节点树
	 * @param map 字节点数据
	 * @param id_name 字段名
	 * @param text_name 字段名对应的文本名
	 */
	private void setNextNodeTree(TreeNode nodeTree,Map<String, Object> map, String id_name, String text_name) {
		for (String bmstr : map.keySet()) {
			if(StringUtils.equals(id_name, bmstr)||StringUtils.equals(text_name, bmstr)) {
				nodeTree.setId(map.get(id_name).toString());
				nodeTree.setText(map.get(text_name).toString());
				nodeTree.setValueSelect(false);
				nodeTree.setOpen("false");
//				nodeTree.setState("closed");
			}
		}
	}

	@Override
	public List<TreeNode> getAllYwAjlbTree() {
		List<TreeNode> treeNodeList = new ArrayList<TreeNode>();

		//查询业务编码表【因为没有一个业务编码、案件类别所对应的模块，所以此处直接写进了树形模块（未用存储过程）】
		List<Map<String,Object>> ywbmList = treeSelectMapper.getAllYwbm();
		if(CollectionUtils.isNotEmpty(ywbmList)) {
			//组装树的根节点
			TreeNode rootTreeNode = null;
			TreeNode sencondTreeNode = null;
			String YWLX_KEY = "YWBM";
			String YWMC_KEY = "YWJC";
			String AJLBBM_KEY = "AJLBBM";
			String AJLBMC_KEY = "AJLBMC";
			for (Map<String, Object> map : ywbmList) {
				rootTreeNode = new TreeNode();
				rootTreeNode.setId(map.get(YWLX_KEY).toString());
				rootTreeNode.setText(map.get(YWMC_KEY).toString());
				rootTreeNode.setValueSelect(false);
				rootTreeNode.setState("closed");

				List<Map<String,Object>> ajlbList = treeSelectMapper.getAllAjlbByYwbm(rootTreeNode.getId());
				List<TreeNode> secondTreeNodeList = new ArrayList<TreeNode>();
				if(CollectionUtils.isNotEmpty(ajlbList)) {
					for (Map<String, Object> ajlbMap : ajlbList) {
						sencondTreeNode = new TreeNode();
						sencondTreeNode.setId(ajlbMap.get(AJLBBM_KEY).toString());
						sencondTreeNode.setText(ajlbMap.get(AJLBMC_KEY).toString());
						sencondTreeNode.setValueSelect(false);
						secondTreeNodeList.add(sencondTreeNode);
					}

				}
				rootTreeNode.setChidren(secondTreeNodeList);
				treeNodeList.add(rootTreeNode);
			}
		}

		return treeNodeList;
	}

	@Override
	public List<TreeNode> getDwTreeByDwbm(String dwbm) {
		List<TreeNode> treeNodeList = new ArrayList<TreeNode>();
		List<DWBM> dwList = xtglService.getDw(dwbm);
		if(CollectionUtils.isNotEmpty(dwList)&&dwList.size()>0) {
			//根目录
			DWBM dwObj = dwList.get(0);
			TreeNode rootTreeNode = new TreeNode();
			if(dwbm.equals(dwObj.getDwbm())) {
				rootTreeNode.setId(dwObj.getDwbm());
				rootTreeNode.setText(dwObj.getDwmc());
				rootTreeNode.setOpen("true");
				rootTreeNode.setValueSelect(false);

				//查询该单位下的子单位
				this.setDwChildTreeNode(dwObj,rootTreeNode);
			}

			treeNodeList.add(rootTreeNode);
		}
		return treeNodeList;
	}

	private void setDwChildTreeNode(DWBM dwbm, TreeNode rootTreeNode) {
		List<TreeNode> dwChildTreeNode = new ArrayList<TreeNode>();
		List<DWBM> dwbmChildList = this.getDwbmByDw(dwbm.getDwbm());
		if(CollectionUtils.isNotEmpty(dwbmChildList)) {
			//子目录
			for (DWBM dwObj : dwbmChildList) {
				TreeNode secondNode = new TreeNode();
				secondNode.setId(dwObj.getDwbm());
				secondNode.setText(dwObj.getDwmc());
				secondNode.setValueSelect(false);
				secondNode.setOpen("false");
				dwChildTreeNode.add(secondNode);
				this.setDwChildTreeNode(dwObj,secondNode);
			}
		}

		rootTreeNode.setChidren(dwChildTreeNode);
	}

	@Override
	public List<TreeNode> getAllXtBmJsTree(String dwbm) {
		//所有的部门、角色树，树形结构为：登录人单位-->部门-->角色
		List<TreeNode> treeNodeList = new ArrayList<TreeNode>();
		List<DWBM> dwList = xtglService.getDw(dwbm);
		if(CollectionUtils.isNotEmpty(dwList)&&dwList.size()>0) {
			//根节点
			DWBM dwbmObj = dwList.get(0);
			TreeNode rootTreeNode = new TreeNode();
			if(dwbm.equals(dwbmObj.getDwbm())) {
				rootTreeNode.setId(dwbmObj.getDwbm());
				rootTreeNode.setText(dwbmObj.getDwmc());
//				rootTreeNode.setState("open");
//				rootTreeNode.setIconCls("icon-zzjg-dw");  //详情见jqeury easyui的icon.css
				rootTreeNode.setValueSelect(false);
				//查询该单位下的部门（注意顺序和子部门）
				this.setDwBmChildTreeNode(rootTreeNode,StringUtils.EMPTY);
			}
			treeNodeList.add(rootTreeNode);
		}
		return treeNodeList;
	}


	private void setDwBmChildTreeNode(TreeNode rootTreeNode,String fbmbm) {
		List<TreeNode> bmTreeNodeList = new ArrayList<>();

		//部门树装载结果【单位-->部门（可能有子部门）-->角色】
		List<BMBM> bmList = new ArrayList<BMBM>();
		bmList = this.getAllXtBmInfo(rootTreeNode.getId(),fbmbm);

		if(CollectionUtils.isNotEmpty(bmList)&&bmList.size()>0){
			for (BMBM bmbm : bmList) {
				List<TreeNode> allBmJsTreeNodeList = new ArrayList<>();
				TreeNode bmTreeNode = new TreeNode();
				bmTreeNode.setId(bmbm.getBmbm());

				bmTreeNode.setText(bmbm.getBmmc());
				bmTreeNode.setState("closed");
//				bmTreeNode.setIconCls("icon-zzjg-bm");

				//1、查询该部门下的子部门,并装载到部门树；2、查询部门的所有角色装载到部门树
				//角色树装载
				List<TreeNode> jsTreeNodeList = this.setAllBmJsTree(bmTreeNode,rootTreeNode.getId(),bmbm.getBmbm(),"JSBM","JSMC","closed");
				if(jsTreeNodeList.size()>0){
					allBmJsTreeNodeList.addAll(jsTreeNodeList);
				}

				List<BMBM> bmChildlist = this.getAllXtBmInfo(rootTreeNode.getId(),bmTreeNode.getId()); //this.getAllBmByDwBmbm(dwbm, bmTreeNode.getId());
				if(CollectionUtils.isNotEmpty(bmChildlist)&&bmChildlist.size()>0) {
//					this.setChildTreeNode(rootTreeNode.getId(),bmTreeNode);
					List<TreeNode> zbmJsTreeNodeList = this.setBmChildTreeNode(rootTreeNode.getId(),bmTreeNode,"closed");
					if(zbmJsTreeNodeList.size()>0){
						allBmJsTreeNodeList.addAll(zbmJsTreeNodeList);
					}
				}

				if(allBmJsTreeNodeList.size()>0){
					bmTreeNode.setChildren(allBmJsTreeNodeList);
				}

				bmTreeNodeList.add(bmTreeNode);
			}
		}
		if(bmTreeNodeList.size()>0){
			rootTreeNode.setChildren(bmTreeNodeList);
		}
	}

	private List<TreeNode> setBmChildTreeNode(String dwbm,TreeNode rootTreeNode,String state) {
		List<TreeNode> bmChildTreeNode = new ArrayList<TreeNode>();
		List<BMBM> bmChildlist = this.getAllXtBmInfo(dwbm,rootTreeNode.getId()); //this.getAllBmByDwBmbm(dwbm, rootTreeNode.getId());
		if(CollectionUtils.isNotEmpty(bmChildlist)&&bmChildlist.size()>0) {
			for (BMBM bmChild : bmChildlist) {
				//二级目录
				TreeNode secondNode = new TreeNode();
				secondNode.setId(bmChild.getBmbm());
				secondNode.setText(bmChild.getBmmc());
				secondNode.setValueSelect(false);
				secondNode.setOpen("false");
//				secondNode.setState("closed");
				//角色树装载
				List<TreeNode> jsTreeNodeList = this.setAllBmJsTree(secondNode,dwbm,bmChild.getBmbm(),"JSBM","JSMC","closed");
				if(jsTreeNodeList.size()>0){
					secondNode.setChildren(jsTreeNodeList);
				}
				bmChildTreeNode.add(secondNode);
//				this.setBmChildTreeNode(dwbm, secondNode,state);
			}
//			rootTreeNode.setChildren(bmChildTreeNode);
		}
		return bmChildTreeNode;
	}

	private List<TreeNode> setAllBmJsTree(TreeNode secondNode, String dwbm, String bmbm, String nodeId, String nodeText, String state) {
		List<TreeNode> bmjsTreeNodeList = new ArrayList<>();
		List<Map<String, Object>> bmJslist = this.getAllXtBmJs(dwbm,bmbm);
		if(bmJslist.size()>0){
			bmjsTreeNodeList = setCommonTreeNode(bmJslist,nodeId,nodeText,state);
		}
//		secondNode.setChidren(bmjsTreeNodeList);
		return bmjsTreeNodeList;
	}


	/**
	 * 获取单位信息
	 * @param dwbm 单位编码
	 * @return 单位结果集
	 */
	private List<DWBM> getDwbmByDw(String dwbm) {
		List<DWBM> allDwList = treeSelectMapper.getAllDwbmByDw(dwbm);
		return allDwList;
	}

	@Override
	public List<BMBM> getAllXtBmInfo(String dwbm, String fbmbm) {
		List<BMBM> bmList = new ArrayList<BMBM>();
		try{
			bmList = treeSelectMapper.getAllXtBmInfo(dwbm,fbmbm);
		}catch (Exception e){
			e.printStackTrace();
		}
		return bmList;
	}

	@Override
	public Map<String,Object> getAllXtRyInfo(String dwbm, String bmbm, String jsbm, String dlbm,Integer page,Integer pageSize) {
		Map<String,Object> ryInfoMap = new HashMap<String,Object>();
		Page<List<Map<String, String>>> pager = PageHelper.startPage(page, pageSize);
		List<Map<String, Object>> ryInfoList = new ArrayList<Map<String, Object>>();
		try{
			ryInfoList = treeSelectMapper.getAllXtRyInfo(dwbm,bmbm,jsbm,dlbm);
		}catch (Exception e){
			e.printStackTrace();
		}
		ryInfoMap.put("total",pager.getTotal());
		ryInfoMap.put("rows",ryInfoList);
		return ryInfoMap;
	}

	@Override
	public List<Map<String, Object>> getAllXtBmJs(String dwbm, String bmbm) {
		List<Map<String, Object>> bmJsList = new ArrayList<>(Constant.NUM_10);
		try {
			bmJsList = treeSelectMapper.getAllXtBmJs(dwbm, bmbm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bmJsList;
	}

	@Override
	public List<Map<String, Object>> getDwBmByParamDwBmGh(String dwbm, String gh, String fbmbm) {
		List<Map<String, Object>> dwBmList = new ArrayList<>(Constant.NUM_10);

		try {
			if(StringUtils.isNotBlank(gh)){
				dwBmList = treeSelectMapper.getFbmByParamDwGhFbm(dwbm, gh,fbmbm);
			}else{
				dwBmList = treeSelectMapper.getDwBmByParamDwFbm(dwbm, fbmbm);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dwBmList;
	}


	private static List<TreeNode> setCommonTreeNode(List<Map<String,Object>> list,String nodeId,String nodeText,String state){
		List<TreeNode> nodeList = new ArrayList<>();
		if(list.size()>0){
			for (Map<String,Object> map : list) {
				TreeNode treeNode = new TreeNode();
				treeNode.setId(map.get(nodeId).toString());
				treeNode.setText(map.get(nodeText).toString());
//				treeNode.setState(state);
//				treeNode.setChidren(childNodeList);
				nodeList.add(treeNode);
			}
		}
		return nodeList;
	}

	@Override
	public List<TreeNode> getAjSelectTree(String dwbm, String gh, String kssj, String jssj, String bmlbbm, String rylx) {
		List<TreeNode> treeNodeList = new ArrayList<TreeNode>();

		List<Map<String, Object>> AjList = this.getAllAjlbbm(dwbm, gh, kssj, jssj, bmlbbm, rylx);
		for (Map<String, Object> map : AjList) {
			//一级目录
			TreeNode rootTreeNode = new TreeNode();
			rootTreeNode.setId(map.get("AJLB_BM").toString());
			rootTreeNode.setText(map.get("AJLB_MC").toString());
			rootTreeNode.setOpen("true");
			rootTreeNode.setValueSelect(false);

			//查询该案件类别下的案件
			this.setChildAjTreeNode(dwbm, gh, kssj, jssj, rootTreeNode, bmlbbm, rylx);

			treeNodeList.add(rootTreeNode);
		}
		return treeNodeList;
	}

	private void setChildAjTreeNode(String dwbm, String gh, String kssj, String jssj, TreeNode rootTreeNode, String bmlbbm, String rylx) {
		List<Map<String,Object>> ajList = new ArrayList<>();
		try {
			if (StringUtils.equals(bmlbbm, "3")) {
				// 公诉办结的案件
				ajList = grjxAjWsMapper.selectBjAj(dwbm,gh,kssj,jssj);
			} else if (StringUtils.equals(bmlbbm, "2") || StringUtils.equals(bmlbbm, "4")) {
				// 侦监、执检办结的案件
				if (StringUtils.equals(bmlbbm, "4") && StringUtils.equals(rylx, "检察辅助人员")) {
					ajList = grjxAjWsMapper.selectBjAjOfZjJf(dwbm,gh,kssj,jssj);
				} else {
					ajList = grjxAjWsMapper.selectBjAjOfZj(dwbm, gh, kssj, jssj);
				}
			} else if (StringUtils.equals(bmlbbm, "1")) {
				// 刑申
				ajList = grjxAjWsMapper.getAj(dwbm,gh,kssj,jssj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if(CollectionUtils.isNotEmpty(ajList) && ajList.size()>0) {
			List<TreeNode> ajChildTreeNode = new ArrayList<TreeNode>();
			for (Map<String,Object> map : ajList) {
				if (map.get("AJLB_BM").equals(rootTreeNode.getId())) {
					//二级目录
					TreeNode secondNode = new TreeNode();
					secondNode.setId(map.get("BMSAH").toString());
					secondNode.setText(map.get("AJMC").toString());
					secondNode.setValueSelect(false);
					secondNode.setOpen("false");
					ajChildTreeNode.add(secondNode);
					this.setChildAjTreeNode(dwbm, gh, kssj, jssj, secondNode, bmlbbm, rylx);
				}
			}
			rootTreeNode.setChidren(ajChildTreeNode);
		}
	}

	/**
	 * 返回去重后的案件类别编码
	 * @param dwbm
	 * @param gh
	 * @param kssj
	 * @param jssj
	 * @return
	 */
	private List<Map<String, Object>> getAllAjlbbm(String dwbm, String gh, String kssj, String jssj, String bmlbbm, String rylx){
		List<Map<String,Object>> ajList = new ArrayList<>();
		List<Map<String,Object>> tmpList=new ArrayList<Map<String,Object>>();

		try {
			if (StringUtils.equals(bmlbbm, "3")) {
				// 公诉办结的案件
				ajList = grjxAjWsMapper.selectBjAj(dwbm,gh,kssj,jssj);
			} else if (StringUtils.equals(bmlbbm, "2") || StringUtils.equals(bmlbbm, "4")) {
				// 侦监、执检办结的案件
				if (StringUtils.equals(bmlbbm, "4") && StringUtils.equals(rylx, "检察辅助人员")) {
					ajList = grjxAjWsMapper.selectBjAjOfZjJf(dwbm,gh,kssj,jssj);
				} else {
					ajList = grjxAjWsMapper.selectBjAjOfZj(dwbm, gh, kssj, jssj);
				}
			} else if (StringUtils.equals(bmlbbm, "1")) {
				// 刑申的办结案件
				ajList = grjxAjWsMapper.getAj(dwbm,gh,kssj,jssj);
			}

			Set<String> keysSet = new HashSet<String>();
			for(Map<String, Object> map : ajList) {
				String keys = (String) map.get("AJLB_BM");
				int beforeSize = keysSet.size();
				keysSet.add(keys);
				int afterSize = keysSet.size();
				if (afterSize == beforeSize + 1){
					tmpList.add(map);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return tmpList;
	}
}
