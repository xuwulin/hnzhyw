package com.swx.ibms.business.common.controller;

import com.google.gson.JsonArray;
import com.swx.ibms.business.common.utils.Constant;
import com.swx.ibms.business.common.utils.CustomXWPFDocument;
import com.swx.ibms.business.common.utils.WordUtil;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * word导出控制器
 * @author 李治鑫
 * @since 2017-5-23
 */
@RequestMapping("/export")
@Controller
public class WordExportController {
	
	/**
	 * 
	 */
	private static final Logger LOG = Logger.getLogger(WordExportController.class);
	
	/**
	 * 导出word文档并下载至本地
	 * @param request 请求
	 * @param response 响应
	 * @return 文件信息
	 */
	@RequestMapping(value="/word")
	public @ResponseBody String wordExport(HttpServletRequest request,HttpServletResponse response){
		String ssr = request.getParameter("ssr");//所属人
		String year = request.getParameter("year");//年度
		String season = request.getParameter("season");//季度
		String grjcdf = request.getParameter("grjcdf");//个人基础得分
		String grpjzf = request.getParameter("grpjzf");//个人评价总分
		String pddj = request.getParameter("pddj");//评定等级
		String zpr = request.getParameter("zpr");//自评人
		String bmpfr = request.getParameter("bmpfr");//部门评分人
		String jcpfr = request.getParameter("jcpfr");//交叉评分人
		String rsbpfr = request.getParameter("rsbpfr");//考评委员会评分人
		
		if("".equals(pddj)||pddj==null){
			pddj = "暂未评定";
		}
		
		String strTitles = request.getParameter("titles");//表头
		String strColumnName = request.getParameter("columnName");//表头对应的feild
		String strRows = request.getParameter("rows");//表格数据
		String strFoot = request.getParameter("foot");//页脚行数据
		
		//反序列化
		JsonArray titleArray = (JsonArray) Constant.JSON_PARSER.parse(strTitles);
		JsonArray columnNameArray = (JsonArray)Constant.JSON_PARSER.parse(strColumnName);
		JsonArray rowsArray = (JsonArray)Constant.JSON_PARSER.parse(strRows);
		JsonArray footArray = (JsonArray)Constant.JSON_PARSER.parse(strFoot);
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("${ssr.name}", ssr);
		params.put("${year}", year);
		params.put("${season}", season);
		params.put("${grjcdf}", grjcdf);
		params.put("${grpjzf}", grpjzf);
		params.put("${pddj}", pddj);
		params.put("${zpr}", zpr);
		params.put("${bmpfr}", bmpfr);
		params.put("${jckhpfr}", jcpfr);
		params.put("${kpwyhpfr}", rsbpfr);
		
		
		String strpath = "/com/swx/zhyw/doctemplates/poi_grjx_8.docx";
		
		if(titleArray.size()>Constant.NUM_11){
			strpath = "/com/swx/zhyw/doctemplates/poi_grjx_9.docx";
		}
		
		URL fileUrl = Thread.currentThread().getContextClassLoader().getResource(strpath);
		String filePath1 = request.getServletContext().getRealPath("/");
		File dir = new File(org.apache.commons.lang3.StringUtils.strip(filePath1));
		String dir2path = dir.getParent();
		dir2path = dir2path+"\\filetemp";
		File dir2 = new File(org.apache.commons.lang3.StringUtils.strip(dir2path));
		if(!dir2.exists()){
			dir2.mkdir();
		}
		String filePath2 = dir2path+"\\"+ssr+year+"年"+season+"季度"+"_1.docx";
		String filePath = dir2path+"\\"+ssr+year+"年"+season+"季度"+".docx";
		
		try {
			FileOutputStream fopts = new FileOutputStream(filePath);
			CustomXWPFDocument doc = null;
			doc = WordUtil.generateWord(params,
						Paths.get(fileUrl.toURI()).toString(),
						rowsArray,columnNameArray,titleArray,footArray);
			
			doc.write(fopts);
			fopts.close();
			//合并单元格
			OPCPackage pack = POIXMLDocument.openPackage(filePath);
			FileOutputStream fopts2 = new FileOutputStream(filePath2);
			
			try{
				CustomXWPFDocument newdoc = new CustomXWPFDocument(pack);
				List<XWPFTable> tableList = newdoc.getTables();
				XWPFTable table = tableList.get(0);
				int [] col = {0,1};
				WordUtil.groupTable(table,col);
				newdoc.write(fopts2);
				fopts2.close();
				pack.close();
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				fopts2.close();
	    		pack.close();
			}
			
		} catch (Exception e) {
			LOG.info(ExceptionUtils.getRootCauseStackTrace(e));
		} 
		String dwfilename =ssr+year+"年"+season+"季度绩效考核.docx";
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("filename", dwfilename);
		map.put("filepath", filePath2);
		return Constant.GSON.toJson(map);
	}
	
	/**下载个人绩效
	 * @param response response
	 * @param request request
	 */
	@RequestMapping("/getWord")
	public void download(HttpServletRequest request,HttpServletResponse response){
		String filename = request.getParameter("filename");
		String filepath = request.getParameter("filepath");
		response.setCharacterEncoding("utf-8");
		response.setContentType("multipart/form-data");
	        //设置Content-Disposition  
	        response.setHeader("Content-Disposition", "attachment;filename="+filename); 
		try {
			response.setHeader("Content-Disposition", 
					"attachment;fileName=" + URLEncoder.encode(filename, "utf-8"));
		} catch (UnsupportedEncodingException e1) {
			//日志记录
			LOG.info("导出个人绩效出错");
		}

		try {
			File file = new File(filepath);
			InputStream inputStream = new FileInputStream(file);
			OutputStream os = response.getOutputStream();
			byte[] b = new byte[Constant.NUM_1024];
			int length;
			while ((length = inputStream.read(b)) > 0) {
				os.write(b, 0, length);
			}
			inputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			//日志记录
			LOG.info("导出个人绩效出错");
		} catch (IOException e) {
			e.printStackTrace();
			//日志记录
			LOG.info("导出个人绩效出错");
		}
	}
}
