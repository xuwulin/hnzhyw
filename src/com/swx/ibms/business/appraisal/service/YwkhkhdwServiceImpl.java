package com.swx.ibms.business.appraisal.service;


import com.swx.ibms.business.appraisal.bean.Ywkhkhdw;
import com.swx.ibms.business.appraisal.mapper.YwkhkhdwMapper;
import com.swx.ibms.business.common.utils.Constant;
import com.swx.ibms.common.bean.TreeNode;
import com.swx.ibms.common.utils.PageCommon;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by Try on 2017/11/1.
 */
@SuppressWarnings("all")
@Service("ywkhkhdwService")
public class YwkhkhdwServiceImpl implements YwkhkhdwService {

    /**
     * 业务工作单位考核mapper
     */
    @Resource
    private YwkhkhdwMapper ywkhkhdwMapper;

    @Override
    public PageCommon<Object> getPageListKhdw(String dwbm, Integer page, Integer pageSize) throws Exception {
        PageCommon<Object> pageCommon = new PageCommon<Object>();
        Map<String, Object> map = new HashMap<String, Object>(Constant.NUM_6);

        int nowPage = (0 == page ? Constant.NUM_1 : page);
        int pageStart = (nowPage - Constant.NUM_1) * pageSize;
        int pageEnd = pageStart + pageSize;
        pageStart++;

        map.put("p_dwbm", dwbm);
        map.put("p_start", pageStart);
        map.put("p_end", pageEnd);
        map.put("p_total", StringUtils.EMPTY);
        map.put("p_cursor", StringUtils.EMPTY);

        ywkhkhdwMapper.getPageListKhdw(map);
        // 得到page对象、总记录数、
        List<Object> list = (List<Object>) map.get("p_cursor");
        pageCommon.setList(list);
        pageCommon.setTotalRecords(Integer.parseInt(map.get("p_total").toString()));
        return pageCommon;
    }

    @Override
    public Map<String, Object> getDwjbByDwbm(String dwbm) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>(Constant.NUM_3);
        Map<String, Object> dwMap = new HashMap<String, Object>(Constant.NUM_6);
        if (StringUtils.isNotBlank(dwbm)) {
            map.put("p_dwbm", dwbm);
            map.put("p_cursor", StringUtils.EMPTY);
            ywkhkhdwMapper.getDwjbByDwbm(map);
            List<Map<String, Object>> dwbmList = (List<Map<String, Object>>) map.get("p_cursor");
            if (dwbmList.size() > 0) {
                dwMap.put("dwbm", dwbmList.get(0).get("dwbm"));
                dwMap.put("dwmc", dwbmList.get(0).get("dwmc"));
                dwMap.put("dwjb", dwbmList.get(0).get("dwjb"));
                dwMap.put("dwjc", dwbmList.get(0).get("dwjc"));
                dwMap.put("fdwbm", dwbmList.get(0).get("fdwbm"));
                return dwMap;
            } else {
                return Collections.EMPTY_MAP;
            }
        } else {
            return Collections.EMPTY_MAP;
        }
    }

    @Override
    public Map<String, Object> addKhdw(Ywkhkhdw khdw) throws Exception {
        Map<String, Object> map = new HashMap<>(Constant.NUM_8);
        Map<String, Object> resMap = new HashMap<>(Constant.NUM_2);
        map.put("p_dwbm", khdw.getDwbm());
        map.put("p_dwmc", khdw.getDwmc());
        map.put("p_fdwbm", khdw.getFdwbm());
        map.put("p_dwjb", khdw.getDwjb());
        map.put("p_dwjc", khdw.getDwjc());
        map.put("p_sfsc", "N");
        map.put("p_errmsg", StringUtils.EMPTY);
        ywkhkhdwMapper.addKhdw(map);
        String msg = (String) map.get("p_errmsg");
        if ("1".equals(msg)) {
            resMap.put("msg", "操作成功！");
        } else if ("2".equals(msg)) {
            resMap.put("msg", "当前记录已存在！");
        } else {
            resMap.put("msg", "操作失败！");
        }
        return resMap;
    }

    @Override
    public Map<String, Object> modifyKhdw(String dwbm) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>(Constant.NUM_3);
        Map<String, Object> resMap = new HashMap<String, Object>(Constant.NUM_2);
        map.put("p_dwbm", dwbm);
        map.put("p_errmsg", StringUtils.EMPTY);
        ywkhkhdwMapper.modifyKhdw(map);
        String msg = (String) map.get("p_errmsg");
        if ("1".equals(msg)) {
            resMap.put("msg", "操作成功！");
        } else {
            resMap.put("msg", "操作失败！");
        }
        return resMap;
    }

    @Override
    public TreeNode getKhdwTree(String khdw) throws Exception {
        //最高的树节点--根节点
        TreeNode rootTreeNode = new TreeNode();
        rootTreeNode.setId(StringUtils.EMPTY);
        rootTreeNode.setText("参与考核单位");
        rootTreeNode.setOpen("true");
        rootTreeNode.setValueSelect(true);
        Map<String, Object> map = this.getDwjbByDwbm(khdw);
        List<Ywkhkhdw> khdwList = null;
        if(!"2".equals(map.get("dwjb").toString())){
            khdwList = this.getKhdw(StringUtils.EMPTY, map.get("fdwbm").toString());//通过考核单位父单位编码获取的是一个考核单位结果集
        }else{
            khdwList = this.getKhdw(StringUtils.EMPTY, khdw);//通过考核单位编码获取的是一个考核单位结果集
        }
        List<TreeNode> khdwChildren = new ArrayList<TreeNode>();
        Ywkhkhdw ywkhkhdw = new Ywkhkhdw();
        int khdwSize = khdwList.size();
        for (int i = 0; i < khdwSize; i++) {
            TreeNode subNode = new TreeNode();
            ywkhkhdw = khdwList.get(i);
            subNode.setId(ywkhkhdw.getDwbm());
            subNode.setText(ywkhkhdw.getDwmc());
            subNode.setValueSelect(true);
            khdwChildren.add(subNode);
            this.SubNode(subNode);
            rootTreeNode.setChidren(khdwChildren);
        }
        return rootTreeNode;
    }


    /**
     * 根据考核单位或者父单位编码获取考核单位信息
     *
     * @param khdw  考核单位
     * @param fdwbm 父单位编码
     * @return 单条考核单位信息
     * @throws Exception 异常
     */
    private List<Ywkhkhdw> getKhdw(String khdw, String fdwbm) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>(Constant.NUM_4);
        map.put("p_dwbm", khdw);
        map.put("p_fdwbm", fdwbm);
        map.put("p_cursor", StringUtils.EMPTY);
        ywkhkhdwMapper.getKhdwByDw(map);
        return (List<Ywkhkhdw>) map.get("p_cursor");
    }

    //查询子节点下得数据
    private void SubNode(TreeNode node) throws Exception {
        List<Ywkhkhdw> children = this.getKhdw(StringUtils.EMPTY, node.getId());
        List<TreeNode> nodeList = new ArrayList<>();
        if (children.size() > 0) {
            Ywkhkhdw child = null;
            int childSize = children.size();
            for (int i = 0; i < childSize; i++) {
                TreeNode childNode = new TreeNode();
                child = children.get(i);
                if (child.getFdwbm().equals(node.getId())) {
                    childNode.setId(child.getDwbm());
                    childNode.setText(child.getDwmc());
                    childNode.setValueSelect(true);
                    nodeList.add(childNode);
                    SubNode(childNode);
                }
                node.setChidren(nodeList);
            }
        }

    }
}
