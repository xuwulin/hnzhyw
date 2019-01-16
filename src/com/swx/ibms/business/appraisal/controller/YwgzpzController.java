/**
 * 
 */
package com.swx.ibms.business.appraisal.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.swx.ibms.business.appraisal.bean.Ywgzzbpz;
import com.swx.ibms.business.appraisal.bean.Ywgzzbpzbt;
import com.swx.ibms.business.appraisal.bean.YwkhKhlxPz;
import com.swx.ibms.business.appraisal.bean.YwkhzbpzTemp;
import com.swx.ibms.business.appraisal.service.YwgzpzService;
import com.swx.ibms.business.appraisal.service.YwkhkhlxpzService;
import com.swx.ibms.business.common.utils.Constant;
import com.swx.ibms.business.common.utils.ExcelUtil;
import com.swx.ibms.business.rbac.bean.RYBM;
import com.swx.ibms.common.utils.DateUtil;
import com.swx.ibms.common.utils.Identities;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 业务工作指标配置
 * @author 封泽超
 *@since 2017年6月5日
 * <p>description:</p>
 * <p>业务工作指标配置总共有两张表：XT_YWGZ_ZBPZBT【表头表】、XT_YWGZ_ZBPZ【表头数据表】；</p>
 */
@Controller
@RequestMapping("/ywgzpz")
public class YwgzpzController {
	
	/**
	 * 业务工作指标配置服务
	 */
	@Autowired
	private YwgzpzService ywgzpzService;
	
	@Resource
    private YwkhkhlxpzService ywkhkhlxpzService;
	 
	/**
	 * 添加业务考核指标配置
	 * @param ywgzzbpz   
	 * @param request   
	 * @return String 
	 */
	@RequestMapping("/insert")
	@ResponseBody
	public String insertOrModify(Ywgzzbpz ywgzzbpz, HttpServletRequest request){
		String result = "";
		RYBM ry = (RYBM)request.getSession().getAttribute("ry");
		ywgzzbpz.setCzr(ry.getMc());
		
		JsonArray pjf = new JsonArray();
		JsonArray tabledata =(JsonArray) Constant.JSON_PARSER.parse(ywgzzbpz.getPznr());
		JsonArray pztabledata =(JsonArray) Constant.JSON_PARSER.parse(ywgzzbpz.getPznr());
		
		int xmbm = Constant.NUM_10;
		int zxmbm = Constant.NUM_10;
		String pre  = "";
		
		for(int i = 0 ; i < tabledata.size() ; i++ ){
			
			JsonObject jobj = (JsonObject) tabledata.get(i);
			
			//空白数据判断
			String temp = jobj.toString();
			if(temp.indexOf("khzxm") < 0||temp.indexOf("xmfz") < 0){
				tabledata.remove(i);
				pztabledata.remove(i--);
				continue;
			}
			
			
			String khzxm = jobj.get("khzxm").getAsString();
			zxmbm++;
			if(!pre.equals(khzxm)){
				JsonObject fz = new JsonObject();
				xmbm++;
				fz.addProperty(xmbm + "", jobj.get("xmfz").getAsString());
				pjf.add(fz);
			}
			
			jobj.addProperty("xmbm", xmbm);
			jobj.addProperty("zxmbm", zxmbm);
			if(jobj.has("sfxtpf")){
				if (StringUtils.equals(jobj.get("sfxtpf").getAsString(), "1")) {
					jobj.addProperty("editable", false);
				}else{
					jobj.addProperty("editable", true);
				}
			}else{
				jobj.addProperty("editable", true);
			}
			jobj.addProperty("bmpf", 0); //"0.0"
			jobj.addProperty("agpf", 0); //"0.0"
			jobj.addProperty("bmpfbz", "1"); 
			jobj.addProperty("agpfbz", "2");
				
			pre = khzxm;
		}
		
		
		ywgzzbpz.setZbnr(tabledata.toString());
		ywgzzbpz.setPznr(pztabledata.toString());
		ywgzzbpz.setPjf(pjf.toString());
		
		if(StringUtils.isBlank(ywgzzbpz.getStatus())) {
			ywgzzbpz.setStatus("0");
		}
		
		//根据有无id 进行插入或更新操作
		if(StringUtils.isBlank(ywgzzbpz.getId())){
			result = ywgzpzService.insert(ywgzzbpz);
		}else{
			result = ywgzzbpz.getId();
			if(!ywgzpzService.updata(ywgzzbpz)){
				result = "0";
			}
		}
		// 需要处理数据
		return result;
	}
	/**
	 * 删除指标配置
	 * @param id 需要删除的id 
	 * @return String 
	 */
	@RequestMapping("/delete")
	public @ResponseBody String delete(@RequestParam String id){
		if(StringUtils.isBlank(id)){			
			return  "0";
		}
		
		//删除表头
		
		Ywgzzbpz y = ywgzpzService.selectbyid(id);
		
		ywgzpzService.deletebyidbt(y.getPzbtid());
		
		//删除数据
		boolean b = ywgzpzService.deletebyid(id);

		
		if(b){
			return "1";
		}else{
			return "0";
		}
		
	}
	/**
	 * 查询指标数据 
	 * @param request 
	 * @return String 
	 */
	@RequestMapping("/search")
	public @ResponseBody String search(HttpServletRequest request){
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> temp = null;
		String dwjb = request.getParameter("dwjb");
		String khlx = request.getParameter("khlx");
		String id = request.getParameter("id");
		String ssrq = request.getParameter("ssrq");
		
		//如果有khlx则说明是查询单条数据	
		if(StringUtils.isBlank(khlx)){
			int page = Integer.valueOf(request.getParameter("page"));
			int rows = Integer.valueOf(request.getParameter("rows"));
			temp = ywgzpzService.search(page, rows, dwjb);
			map.put("total", temp.get("p_total"));
			map.put("rows",temp.get("p_cur"));
			return Constant.GSON.toJson(map);
		}else{
			dwjb = dwjb.substring(1, dwjb.length()-1);
			String[] params =  dwjb.split(",");
			List<Ywgzzbpz> list = new ArrayList<Ywgzzbpz>();
			Ywgzzbpz y = null;
			for(String p:params){
				y = ywgzpzService.selectone(p, khlx,ssrq);
				if(y != null && !id.equals(y.getId())){
					list.add(y);
				}
			}
			if(CollectionUtils.isEmpty(list)){
				return "0";
			}else{
				return Constant.GSON.toJson(list);
			}
		}
	}
	/**
	 * 得到根据id得到一个配置所有信息
	 * @param request 
	 * @return String 
	 */
	@RequestMapping("/selectbyid")
	public @ResponseBody String selectbyid(HttpServletRequest request){
		
		String id = request.getParameter("id");
		
		Ywgzzbpz y = ywgzpzService.selectbyid(id);
		
		return Constant.GSON.toJson(y);
	}
	////////////表头部分
	
	/**
	 * 添加表头
	 * @param request 
	 * @param y  
	 * @return String
	 */
	@RequestMapping("/insertbt")
	public @ResponseBody String insertbt(Ywgzzbpzbt y, HttpServletRequest request){
		String result = "";
		RYBM ry = (RYBM) request.getSession().getAttribute("ry");
		y.setCjr(ry.getMc());
		
		//根据有无id判断操作为更新或插入
		if(StringUtils.isBlank(y.getId())){
			result = ywgzpzService.insertbt(y);	
		}else{
			result = y.getId();
			if(!ywgzpzService.updatebt(y)){
				result = "0";
			}
		}
		
		return result;
	}
	
	/**
	 * 根据id
	 * @param request 
	 * @return String 
	 */
	@RequestMapping("/selectbyidbt")
	public @ResponseBody String selectbyidbt(HttpServletRequest request){
		String id = request.getParameter("id");
		
		Ywgzzbpzbt y = ywgzpzService.selectbyidbt(id);
		return Constant.GSON.toJson(y);
	}
	/**
	 * 更新表头
	 * @param request 
	 * @return String
	 */
	@RequestMapping("/updatebt")
	public @ResponseBody String updatebt(HttpServletRequest request){
		Ywgzzbpzbt y = new Ywgzzbpzbt();
		
		String id = request.getParameter("id");
		String pzbtsj = request.getParameter("pzbtsj");
		String btsj = request.getParameter("btsj");
		
		y.setId(id);
		y.setBtsj(btsj);
		y.setPzbtsj(pzbtsj);
		
		boolean b = ywgzpzService.updatebt(y);
		if(b){
			return "1";
		}
		return "0";
	}
	
	/**
	 * 删除表头
	 * @param request 
	 * @return String
	 */
	@RequestMapping("/deletebyidbt")
	public @ResponseBody String deletebyidbt(HttpServletRequest request){
		
		String id = request.getParameter("id");
		
		boolean b = ywgzpzService.deletebyidbt(id);
		
		if(b){
			return "1";
		}
		return "0";
	}
	
	
	/**
	 * 导入excel实现考核指标录入
	 * 【思路】
	 * 1、读取excel数据返回集合
	 * 2、遍历集合获取每一单元格的内容
	 * 3、组装成jsonArray
	 * 4、根据条件查询业务考核指标配置表的数据是否存在，存在则进行修改，不存在则进行添加操作。
	 * @param file excel文件
	 * @param request 
	 * @return 成功与否的json符串
	 */
	@RequestMapping(value = "/uploadYwkhzbExcel",produces = { "text/html;charset=UTF-8" },method = RequestMethod.POST)
	@ResponseBody
	public String addFjOutId(@RequestParam("file") MultipartFile file,
										HttpServletRequest request){
		Map<String,Object> resMap = new HashMap<String,Object>();
		RYBM ry = (RYBM)request.getSession().getAttribute("ry");
		Ywgzzbpz ywgzzbpz = new Ywgzzbpz();
		JSONArray pjf = new JSONArray();
		JSONArray pztabledata = new JSONArray(); //考核指标配置的表格内容
		JSONArray tabledata = new JSONArray();  //业务考核前台页面调用的表格内容
		String nowYear = DateUtil.getCurrDate("YYYY");
		String msg = "导入失败，可能是没有数据或者数据混乱！";
		
		List<Map<String, Object>> zbList = ExcelUtil.importExcelData(file, 0, Constant.NUM_2, new YwkhzbpzTemp());
		if(zbList!=null&&!zbList.isEmpty()) {
			int zbListSize = zbList.size();
			zbList.remove(zbListSize-1);
//			System.out.println("excel集合："+zbList.toString());
			
			//组装配置表部分数据
			Map<String, Object> ywkhzbTemp = zbList.get(0);
			String khlx = StringUtils.EMPTY;
			String dwjb = "3,4";   //注：【下列key值大部分对应的是YwkhzbpzTemp这个类中的值】【默认是代表（分市院,区县院），这儿本来应该不写死的，由于时间关系】
			String dwjbmcTemp = (String) ywkhzbTemp.get("dwjbMc");
			String khmcTemp = (String) ywkhzbTemp.get("khlxMc");
			if(StringUtils.isNotBlank(khmcTemp)) {
				YwkhKhlxPz lxpz = ywkhkhlxpzService.getKhlxInfoByKhmc(khmcTemp);
				khlx = lxpz.getKhlx();
			}
			if(StringUtils.isNotBlank(dwjbmcTemp)) {
				if("分市院".equals(dwjbmcTemp)) {
					dwjb = "3";
				}else if("区县院".equals(dwjbmcTemp)) {
					dwjb = "4";
				}
			}
			
			//本身这儿是可以直接将list集合放到jsonArray中，但是会多出一层不利于解析，下面有两个字段不用放到配置表表格所以未取出
			for (Map<String, Object> map : zbList) {
				JSONObject jsonObj = new JSONObject();
				for (String key : map.keySet()) {
					if(!"khlxMc".equals(key)&&!"dwjbMc".equals(key)) {
						String tempStr = map.get(key).toString(); 
						if("sfxtpf".equals(key)) {  //是否系统评分【sfstpf】为空或者为手动录入（“0”），系统计算（“1”）
							if(StringUtils.isNotBlank(map.get(key).toString())) {
								if("系统计算".equals(map.get(key).toString())) {
									tempStr = "1";
								}else {
									tempStr = "0";
								}
							}
						}
						jsonObj.put(key, tempStr);
					}
				}
//				System.out.println("----表格单行数据：--"+jsonObj.toJSONString());
				pztabledata.add(jsonObj);
				tabledata.add(jsonObj);
			}
			ywgzzbpz.setPznr(pztabledata.toString());  
			
			//组装业务考核前台页面展示的数据
			int xmbm = Constant.NUM_10;
			int zxmbm = Constant.NUM_10;
			String pre  = "";
			int pzTableDataSize = tabledata.size();
			
			for(int i = 0 ; i < pzTableDataSize; i++ ){
				JSONObject jobj = (JSONObject) tabledata.get(i);
//				System.out.println("----"+jobj);
				//空白数据判断
				String temp = jobj.toJSONString();
				if(temp.indexOf("khzxm") < 0||temp.indexOf("xmfz") < 0){
					tabledata.remove(i);
					pztabledata.remove(i--);
					continue;
				}
				
				String khzxm = jobj.get("khzxm").toString();
				if(StringUtils.isNotBlank(khzxm)) {
					zxmbm++;
					if(!pre.equals(khzxm)){
						JSONObject fz = new JSONObject();
						xmbm++;
						fz.put(xmbm + "", jobj.get("xmfz").toString());
						pjf.add(fz);
					}
					
					jobj.put("xmbm", xmbm);
					jobj.put("zxmbm", zxmbm);
					if(jobj.containsKey("sfxtpf")){
						if (StringUtils.equals(jobj.get("sfxtpf").toString(), "1")) {
							jobj.put("editable", false);
						}else{
							jobj.put("editable", true);
						}
					}else{
						jobj.put("editable", true);
					}
					jobj.put("bmpf", 0); //"0.0"
					jobj.put("agpf", 0); //"0.0"
					jobj.put("bmpfbz", "1"); 
					jobj.put("agpfbz", "2");
				}
				pre = khzxm;
			}
			
			ywgzzbpz.setDwjb("["+dwjb+"]");
			ywgzzbpz.setKhlx(khlx);
			ywgzzbpz.setZbnr(tabledata.toString()); 
			ywgzzbpz.setPjf(pjf.toString());
			ywgzzbpz.setCzr(ry.getMc());
			ywgzzbpz.setCzsj(new Date());
//			ywgzzbpz.setPzbtid("");
			ywgzzbpz.setSsrq(nowYear);
			ywgzzbpz.setStatus("0");
			
			//查询本年度是否存在该业务类型的考核指标【ssrq、khlx、dwjb、status】，存在则进行修改操作，不存在则进行添加操作 （String khlx,String ssrq,String dwjb）
			List<Ywgzzbpz> zbpzList = ywgzpzService.getYwkhZbpzByParams(khlx,nowYear,ywgzzbpz.getDwjb());
			if(zbpzList!=null&&zbpzList.size()>0) {
				//修改操作
				Ywgzzbpz zbpzObj = zbpzList.get(0);
				ywgzzbpz.setId(zbpzObj.getId());
				boolean res = ywgzpzService.updata(ywgzzbpz);
				if(res) {
					msg = "导入成功！";
				}
			}else {
				//添加操作
				ywgzzbpz.setId(Identities.get32LenUUID());
				String resMsg = ywgzpzService.insert(ywgzzbpz);
				if(!"0".equals(resMsg)) {
					msg = "导入成功！";
				}
			}
		}
		
		resMap.put("msg", msg);
		return Constant.GSON.toJson(resMap);
	}
	
}
