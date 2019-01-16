package com.swx.ibms.business.common.service;


import com.swx.ibms.business.archives.bean.Person;
import com.swx.ibms.business.common.bean.TreeSelect;
import com.swx.ibms.business.rbac.bean.BMBM;
import com.swx.ibms.common.bean.TreeNode;

import java.util.List;
import java.util.Map;

/**
 * 树形结构展示接口
 * @author 李佳
 * @date: 2017年3月5日
 */
@SuppressWarnings("all")
public interface TreeSelectService {

	/**
	 * 查询出所有检查院
	 * @return Map
	 */
	Map allJcy();
	/**
	 * 根据单位编码获取所有子单位编码
	 * @param fdwbm 父单位编码
	 * @return List<TreeSelect>
	 */
	List<TreeSelect> zDwbm(String fdwbm);
	/**
	 * 根据检查院编码查询出所有部门的编码和名称
	 * @param dwbm 单位编码
	 * @return Map
	 */
	Map department(String dwbm);
	/**
	 * 根据单位、部门编码查询出所有角色的编码和名称
	 * @param dwbm 单位编码
	 * @param bmbm 部门编码
	 * @return Map
	 */
	Map role(String dwbm, String bmbm);
	/**
	 * 根据角色、单位、部门、查询下属的所有人员的工号和名称
	 * @param jsbm 角色编码
	 * @param dwbm 单位编码
	 * @param bmbm 部门编码
	 * @return Map
	 */
	Map person(String jsbm, String dwbm, String bmbm);
	/**
	 * 获取单位的树形结构
	 * @param rootDwbm 树形结构根的单位编码(起始单位编码)
	 * @return 树形结构
	 */
	TreeNode getDwTree(String rootDwbm);
	/**
	 * 获取组织机构的树形结构
	 * @param rootDwbm 树形结构根的单位编码(起始单位编码)
	 * @param gh 工号
	 * @return TreeNode
	 */
	TreeNode getZzjgTree(String rootDwbm, String gh);
	/**
	 * 获取单位树形结构数据
	 * @param rootDwbm
	 * @param all 是否所有
	 * @return TreeNode
	 */
	TreeNode getTreeNode(String rootDwbm, boolean all);
	/**
	 * 获取部门树形结构数据
	 * @param rootdwbm 树形结构根的单位编码(起始单位编码)
	 * @param dwmc 单位名称
	 * @return TreeNode
	 */
	TreeNode getBmtree(String dwmc,String rootdwbm);
	/**
	 * 获取部门树形结构数据
	 * @param rootDwbm 单位编码
	 * @param gh 工号
	 * @return TreeNode
	 */
	TreeNode getZjldTree(String rootDwbm, String gh);
	/**
	 * 返回该部门下的全部领导
	 * @param dwbm 单位编码
	 * @param gh 工号
	 * @return List<Person>
	 */
	List<Person> selectAllzjld(String dwbm, String gh);

//	/**
//	 * 生成领导树形结构
//	 * @param dwbm 单位编码
//	 * @param gh 工号
//	 * @param bmjs 部门角色列表
//	 * @return 树形结构
//	 */
//	TreeNode getHeaderTree (String dwbm,String gh,List<String> bmjs);

	/**
	 * 生成领导树形结构
	 * @param dwbm 单位编码
	 * @param gh 工号
	 * @param bmjs 部门角色列表
	 * @return 树形结构
	 */
	TreeNode getTreeOfHeader(String dwbm,String gh,List<String> bmjs);

	/**
	 * 查询某单位的所有部门并生成树形结构
	 * @param dwbm 单位编码
	 * @return
	 */
	List<TreeNode> getBmbmSelectTree(String dwbm);

	/**
	 * 获取某个单位下的某人的所属部门、角色下的所有人员
	 * @param paramDwbm 单位编码
	 * @param paramGh 工号
	 * @return 部门人员树
	 */
	List<TreeNode> getAllRyOfDwBmTree(String paramDwbm,String paramGh,String paramType);

	/**
	 * 获取树 【业务类型->案件类别】
	 * @return
	 */
	List<TreeNode> getAllYwAjlbTree();

	/**
	 * 根据单位编码获取该单位所对应的单位树【树结构：省院->分市院->基层院】
	 * @param dwbm 单位编码
	 * @return 单位树结构对象
	 */
	List<TreeNode> getDwTreeByDwbm(String dwbm);


	List<TreeNode> getAllXtBmJsTree(String dwbm);

	List<BMBM> getAllXtBmInfo(String dwbm, String fbmbm);

	Map<String,Object> getAllXtRyInfo(String dwbm,String bmbm,String jsbm,String dlbm,Integer page,Integer pageSize);

	List<Map<String,Object>> getAllXtBmJs(String dwbm,String bmbm);

	List<Map<String,Object>> getDwBmByParamDwBmGh(String dwbm,String gh,String fbmbm);

	/**
	 * 查询某人所办结的案件并生成树形结构
	 * @param dwbm 单位编码
	 * @return
	 */
	List<TreeNode> getAjSelectTree(String dwbm, String gh, String kssj, String jssj, String bmlbbm, String rylx);
}
