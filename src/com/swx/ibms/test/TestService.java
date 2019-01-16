package com.swx.ibms.test;


import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletResponse;


import com.swx.ibms.business.appraisal.service.YwgzkhSpService;
import com.swx.ibms.business.appraisal.service.YwlxkhService;
import com.swx.ibms.business.archives.bean.SfdaGrjbxxInputExcel;
import com.swx.ibms.business.archives.bean.SfdaGrjlInputExcel;
import com.swx.ibms.business.common.bean.Splc;
import com.swx.ibms.business.common.service.DbywService;
import com.swx.ibms.business.common.service.SpService;
import com.swx.ibms.business.common.service.TreeSelectService;
import com.swx.ibms.business.common.service.UploadService;
import com.swx.ibms.business.etl.utils.QuartzJob;
import com.swx.ibms.business.etl.utils.QuartzManager;
import com.swx.ibms.common.utils.DateUtil;
import com.swx.ibms.common.web.workflow.WorkflowDeployer;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;



/**
 * @author admin
 * @Description:
 * @date 2017/6/7
 */
 @RunWith(SpringJUnit4ClassRunner.class)
 @ContextConfiguration(locations= {"classpath*:/baseConfig/applicationContext.xml"})
public class TestService extends SpringTestCase {
	 
	 @Autowired
	 RuntimeService runtimeService;
	 
	 @Autowired
	 WorkflowDeployer workflowDeployer;
	 
	 @Autowired
	 ProcessEngineFactoryBean factoryBean;
	@Autowired
	private TreeSelectService treeSelectService;

	@Autowired
	private SpService spService;
	
	@Autowired
	private YwgzkhSpService ywgzkhSpService;

	@Autowired
	private YwlxkhService ywlxkhService;
	@Autowired
	private DbywService dbywService;

	@Autowired
	private UploadService uploadService;

	private static HttpServletResponse response;
	
	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

/*	@Test
	public void testTreeSelectService() {
		// String spdw, String spr, String dlrbm,List<String> bmjs
		List<String> bmjs = new ArrayList<>();
		bmjs.add("460102,0004,004");
		Object o = dbywService.getDbywList("460102", "0035", "0004", bmjs);
		// Object o=ywlxkhService.updatezjcftoinsertpjf("934");

		// Object o = ywgzkhSpService.getBmkhry("05","460000", "0100", "0001");
		// o = ywgzkhSpService.getKhById("B77AF7C1977D4C268987AEC12B15FA05");
		System.err.println(Constant.GSON.toJson(o));
		// assertEquals("1","1");

		// Object o = ywlxkhService.getYwlxB("05","460100","2017");
		// Object o = ywlxkhService.getkhzl("2017","460300");
		// System.err.println(o);
	}

	@Test
	public void testFileService() {
		// List<Upload> upload = (List<Upload>)
		// uploadService.selectbyid("a1584fac13b04a53a5406760a5e6e163");
		// System.out.println(upload.get(0).toString());
//		System.out.println("---------");
	}

	public static void main(String[] args) throws ParseException {
//		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//		System.out.println(new Date());
//		System.out.println(sf.parseObject(sf.format(new Date())).toString());
		try {
			RYBM ry = new RYBM();
			ry.setGh("0059");
			ry.setDwbm("460000");
			ry.setMc("TEST");
			ry.setKl("111111111");
			ry.setBmbm("0001");
			ry.setGzzh("0059");
			List<RYBM> list = new ArrayList<RYBM>();
			list.add(ry);
			list.add(ry);
			list.add(ry);
			list.add(ry);
			final String DEFAULT_EXCEL_PATH = "E:/";
			final String DEFAULT_EXCEL_SUBFIX = ".xls";
			String fileName = "2017-5-9到2017-6-9的杨宇个人绩效信息";
			String sheetName = "杨宇的个人绩效信息";
			String titleName = "2017-1-1到2017-3-4杨宇的个人绩效";
			OutputStream outXlsx = new FileOutputStream(DEFAULT_EXCEL_PATH+fileName+DEFAULT_EXCEL_SUBFIX);
			
			//文本信息
			JsonArray jsonArr = new JsonArray();
			//第二行的列名
			JsonObject jsonObj2 = new JsonObject();
			jsonObj2.addProperty("line", 2);
			jsonObj2.addProperty("coloum1", "单位名称");
			jsonObj2.addProperty("coloum2", "部门名称");
			jsonObj2.addProperty("coloum3", "工号");
			jsonObj2.addProperty("coloum4","姓名");
			jsonObj2.addProperty("coloum5","部门评审人");
			jsonObj2.addProperty("coloum6","考评委员会评审人");
 			jsonArr.add(jsonObj2);
 			
 			//第三行的列名
			JsonObject jsonObj3 = new JsonObject();
			jsonObj3.addProperty("line", 3);
			jsonObj3.addProperty("coloum1", "海南省人民检察院");
			jsonObj3.addProperty("coloum2", "侦监一部");
			jsonObj3.addProperty("coloum3", "0059");
			jsonObj3.addProperty("coloum4","杨宇");
			jsonObj3.addProperty("coloum5","刘涛");
			jsonObj3.addProperty("coloum6","杨云海");
 			jsonArr.add(jsonObj3);
 			
 			//第四行的列名
			JsonObject jsonObj4 = new JsonObject();
			jsonObj4.addProperty("line", 4);
			jsonObj4.addProperty("coloum1", "评分部分");
			jsonObj4.addProperty("coloum2", "指标类别");
			jsonObj4.addProperty("coloum3", "分数");
			jsonObj4.addProperty("coloum4","指标项");
			jsonObj4.addProperty("coloum5","检察官自评");
			jsonObj4.addProperty("coloum6","部门评审");
			jsonObj4.addProperty("coloum7","考评委员会评审");
			jsonObj4.addProperty("coloum8","评价得分");
 			jsonArr.add(jsonObj4);
 			
 			//第五行的列名
			JsonObject jsonObj5 = new JsonObject();
			jsonObj5.addProperty("line", 5);
			jsonObj5.addProperty("coloum1", "自评分");
			jsonObj5.addProperty("coloum2", "备注");
			jsonObj5.addProperty("coloum3", "部门评分");
			jsonObj5.addProperty("coloum4","备注");
			jsonObj5.addProperty("coloum5","考评委员会评分");
			jsonObj5.addProperty("coloum6","备注");
 			jsonArr.add(jsonObj5);
			
 			for (int i = 0; i < 10; i++) {
 				JsonObject jsonObj = new JsonObject();
 				jsonObj.addProperty("line", 6+i);
 				jsonObj.addProperty("coloum1", "1111"+"-"+i);
 				jsonObj.addProperty("coloum2", "1112"+"-"+i);
 				jsonObj.addProperty("coloum3", "1113"+"-"+i);
 				jsonObj.addProperty("coloum4", "1114"+"-"+i);
 				jsonObj.addProperty("coloum5", "1115"+"-"+i);
 				jsonObj.addProperty("coloum6", "1116"+"-"+i);
 				jsonObj.addProperty("coloum7", "1117"+"-"+i);
 				jsonObj.addProperty("coloum8", "1118"+"-"+i);
 				jsonObj.addProperty("coloum9", "1119"+"-"+i);
 				jsonObj.addProperty("coloum10", "11110"+"-"+i);
 				jsonObj.addProperty("coloum11", "11111"+"-"+i);
 				jsonArr.add(jsonObj);
			}
//			System.out.println("=============开始");
			ExcelUtil.exportExcelNotUtils(outXlsx,sheetName,titleName,jsonArr);
//			System.out.println("=============end");
			outXlsx.flush();
			outXlsx.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	 *//**导出数据
	 * @throws Exception *//*   
    @SuppressWarnings("all")
	private static void exportExcelNotUtils(OutputStream os,
											String sheetName,
											String titleName,
											List<RYBM> list,
											JsonArray jsonArr) throws IOException{   
       
    	Workbook workbook = new HSSFWorkbook();
        //设置工作薄的内容垂直居中、水平居中
        HSSFCellStyle style = (HSSFCellStyle) workbook.createCellStyle(); // 样式对象       
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直       
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平  

        //表头样式
        HSSFCellStyle titleStyle = (HSSFCellStyle) workbook.createCellStyle();
        titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        HSSFFont titleFont = (HSSFFont) workbook.createFont();
        titleFont.setFontHeightInPoints((short) 14);
        titleFont.setBoldweight((short) 700);
        titleStyle.setFont(titleFont);
//        // 列头样式
//        HSSFCellStyle headerStyle = (HSSFCellStyle) workbook.createCellStyle();
//        headerStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//        headerStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//        headerStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//        headerStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
//        headerStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
//        headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//        HSSFFont headerFont = (HSSFFont) workbook.createFont();
//        headerFont.setFontHeightInPoints((short) 14);
//        headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
//        headerStyle.setFont(headerFont);
//        // 单元格样式
//        CellStyle cellStyle = (HSSFCellStyle) workbook.createCellStyle();
//        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
//        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
//        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
//        HSSFFont cellFont = (HSSFFont) workbook.createFont();
//        cellFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
//        cellStyle.setFont(cellFont);
        // 生成一个(带标题)工作薄
        HSSFSheet sheet = (HSSFSheet) workbook.createSheet(sheetName);
        //标题行
        Row titleRow = sheet.createRow((short)0);
        titleRow.createCell((short)0).setCellValue(titleName);
        HSSFCell titleCell = (HSSFCell) titleRow.getCell((short)0);
		titleCell.setCellStyle(titleStyle);
		//合并列  合并11列
		sheet.addMergedRegion(new CellRangeAddress((short)0,(short)0, (short)0, (short)11));  
		
		//第二行
		Row headerRow = sheet.createRow((short)1);
		//第三行
		Row headerContentRow = sheet.createRow((short)2);
		//第四行
		Row row4 = sheet.createRow((short)3);
		sheet.addMergedRegion(new CellRangeAddress((short)3,(short)4, (short)0, (short)0)); 
		sheet.addMergedRegion(new CellRangeAddress((short)3,(short)4, (short)1, (short)1));
		sheet.addMergedRegion(new CellRangeAddress((short)3,(short)4, (short)2, (short)2));
		sheet.addMergedRegion(new CellRangeAddress((short)3,(short)4, (short)3, (short)3));
		sheet.addMergedRegion(new CellRangeAddress((short)3,(short)3, (short)4, (short)5));
		sheet.addMergedRegion(new CellRangeAddress((short)3,(short)3, (short)6, (short)7));
		sheet.addMergedRegion(new CellRangeAddress((short)3,(short)3, (short)8, (short)9));
		sheet.addMergedRegion(new CellRangeAddress((short)3,(short)4, (short)10, (short)10));
		//第五行
		Row row5 = sheet.createRow((short)4);
		
//		System.out.println("========"+jsonArr.get(i).getAsJsonObject()+"-----------"+"2".equals(jsonObj.get("line").toString()));
		for (int i = 0; i < jsonArr.size(); i++) {
			JsonObject jsonObj = jsonArr.get(i).getAsJsonObject();
			if ("2".equals(jsonObj.get("line").toString())) {
				headerRow.createCell(0).setCellValue(jsonObj.get("coloum1").getAsString());
				headerRow.createCell(1).setCellValue(jsonObj.get("coloum2").getAsString());
				headerRow.createCell(2).setCellValue(jsonObj.get("coloum3").getAsString());
				headerRow.createCell(3).setCellValue(jsonObj.get("coloum4").getAsString());
				headerRow.createCell(4).setCellValue(jsonObj.get("coloum5").getAsString());
				headerRow.createCell(5).setCellValue(jsonObj.get("coloum6").getAsString());
//				System.out.println("------第二行："+jsonObj);
			}
			else if("3".equals(jsonObj.get("line").toString())){
				headerContentRow.createCell(0).setCellValue(jsonObj.get("coloum1").getAsString());
				headerContentRow.createCell(1).setCellValue(jsonObj.get("coloum2").getAsString());
				headerContentRow.createCell(2).setCellValue(jsonObj.get("coloum3").getAsString());
				headerContentRow.createCell(3).setCellValue(jsonObj.get("coloum4").getAsString());
				headerContentRow.createCell(4).setCellValue(jsonObj.get("coloum5").getAsString());
				headerContentRow.createCell(5).setCellValue(jsonObj.get("coloum6").getAsString());
//				System.out.println("------第三行："+jsonObj);
			}
			else if("4".equals(jsonObj.get("line").toString())){
				row4.createCell(0).setCellValue(jsonObj.get("coloum1").getAsString());
				row4.createCell(1).setCellValue(jsonObj.get("coloum2").getAsString());
				row4.createCell(2).setCellValue(jsonObj.get("coloum3").getAsString());
				row4.createCell(3).setCellValue(jsonObj.get("coloum4").getAsString());
				row4.createCell(4).setCellValue(jsonObj.get("coloum5").getAsString());
				row4.createCell(6).setCellValue(jsonObj.get("coloum6").getAsString());
				row4.createCell(8).setCellValue(jsonObj.get("coloum7").getAsString());
				row4.createCell(10).setCellValue(jsonObj.get("coloum8").getAsString());
//				System.out.println("------第四行："+jsonObj);
			}
			else if("5".equals(jsonObj.get("line").toString())){
				row5.createCell(4).setCellValue(jsonObj.get("coloum1").getAsString());
				row5.createCell(5).setCellValue(jsonObj.get("coloum2").getAsString());
				row5.createCell(6).setCellValue(jsonObj.get("coloum3").getAsString());
				row5.createCell(7).setCellValue(jsonObj.get("coloum4").getAsString());
				row5.createCell(8).setCellValue(jsonObj.get("coloum5").getAsString());
				row5.createCell(9).setCellValue(jsonObj.get("coloum6").getAsString());
//				System.out.println("------第五行："+jsonObj);
			}
			else{  //if("6".equals(jsonObj.get("line").getAsString())){
				//excel需要填入的数据
//				System.out.println("0000000======="+i);
				Row row6 = sheet.createRow(i+1);
				row6.createCell(0).setCellValue(jsonObj.get("coloum1").getAsString());
	            row6.createCell(1).setCellValue(jsonObj.get("coloum2").getAsString());
	            row6.createCell(2).setCellValue(jsonObj.get("coloum3").getAsString());
	            row6.createCell(3).setCellValue(jsonObj.get("coloum4").getAsString());
	            row6.createCell(4).setCellValue(jsonObj.get("coloum5").getAsString());
	            row6.createCell(5).setCellValue(jsonObj.get("coloum6").getAsString());
	            row6.createCell(6).setCellValue(jsonObj.get("coloum7").getAsString());
	            row6.createCell(7).setCellValue(jsonObj.get("coloum8").getAsString());
	            row6.createCell(8).setCellValue(jsonObj.get("coloum9").getAsString());
	            row6.createCell(9).setCellValue(jsonObj.get("coloum10").getAsString());
	            row6.createCell(10).setCellValue(jsonObj.get("coloum11").getAsString());
//				System.out.println("-----------无数据存入excel-----------");
			}
		}
		
//		headerRow.createCell(0).setCellValue("单位名称");
//		headerRow.createCell(1).setCellValue("部门名称");
//		headerRow.createCell(2).setCellValue("工号");
//		headerRow.createCell(3).setCellValue("姓名");
//		headerRow.createCell(4).setCellValue("部门评审人");
//		headerRow.createCell(5).setCellValue("考评委员会评审人");
		
//		headerContentRow.createCell(0).setCellValue("海南省人民检察院");
//		headerContentRow.createCell(1).setCellValue("侦监一处");
//		headerContentRow.createCell(2).setCellValue("0059");
//		headerContentRow.createCell(3).setCellValue("杨宇");
//		headerContentRow.createCell(4).setCellValue("刘涛");
//		headerContentRow.createCell(5).setCellValue("贾志鸿");
		
//		row4.createCell(0).setCellValue("评分部分");
//		row4.createCell(1).setCellValue("指标类别");
//		row4.createCell(2).setCellValue("分数");
//		row4.createCell(3).setCellValue("指标项");
//		row4.createCell(4).setCellValue("检察官自评");
//		row4.createCell(6).setCellValue("部门评审");
//		row4.createCell(8).setCellValue("考评委员会评审");
//		row4.createCell(10).setCellValue("评价得分");
		
//		row5.createCell(4).setCellValue("检察官自评分");
//		row5.createCell(5).setCellValue("备注");
//		row5.createCell(6).setCellValue("部门评分");
//		row5.createCell(7).setCellValue("备注");
//		row5.createCell(8).setCellValue("考评委员会评分");
//		row5.createCell(9).setCellValue("备注");
		
//        for (int ii = 0; ii < list.size(); ii++) {   
//            RYBM user = list.get(ii);   
//            Row row6 = sheet.createRow(ii+5); 
//            row6.createCell(0).setCellValue(user.getGh());
//            row6.createCell(1).setCellValue(user.getDwbm());
//            row6.createCell(2).setCellValue(user.getMc());
//            row6.createCell(3).setCellValue(user.getKl());
//            row6.createCell(4).setCellValue(user.getBmbm());
//            row6.createCell(5).setCellValue(user.getGzzh());
//            row6.createCell(6).setCellValue(user.getGzzh());
//            row6.createCell(7).setCellValue(user.getGzzh());
//            
//        }   
	        try{   
	        	workbook.write(os); 
	        }catch(Exception ex){   
	            ex.printStackTrace();   
	        }   
    } */
    
    @Test
    public void TestActivitiUser() throws Exception {
    	//获取配置文件
    	
    	assertNotNull(runtimeService);
    	// 创建流程引擎
    	ProcessEngine processEngine = factoryBean.getObject();
    	assertNotNull(processEngine.getRuntimeService());
    	
    	// 获取流程存储服务组件
    	RepositoryService repositoryService = processEngine.getRepositoryService();

    	// 获取运行时服务组件
    	RuntimeService runtimeService = processEngine.getRuntimeService();

    	// 获取流程任务组件
    	TaskService taskService = processEngine.getTaskService();
    	
    	// 1、部署流程文件
    	String deploymentName = "sfdaAudit";
    	List<Deployment> deployments = repositoryService.createDeploymentQuery()
														.deploymentName(deploymentName)
														.orderByDeploymenTime()
														.desc()
														.list();
    	if(!(deployments.size()>0)) {
    		//部署方式一：根据classpath部署
/*    		repositoryService.createDeployment().name("sfdaAudit")
    		.addClasspathResource("sfda_audit.bpmn")//加载资源文件，一次只能加载一个文件  
    		.addClasspathResource("sfda_audit.png")//  
    		.deploy();*/
    		
    		//方式二：通过输入输出流进行部署  zip方式
    	}
    	
    	
    	long flowDeploymentCount = repositoryService.createDeploymentQuery().count();
    	System.out.println("第一个打印：流程发布数量："+flowDeploymentCount);
    	
    	//流程定义信息【常用】
    	List<ProcessDefinition> processDefinitionList = repositoryService.createProcessDefinitionQuery().list();
    	for (ProcessDefinition processDefinition : processDefinitionList) {
			System.out.println("第二个打印：流程定义ID:"+processDefinition.getId()
							 +"---->流程定义NAME:"+processDefinition.getName()+processDefinition.getDeploymentId()
							 +"---->流程定义KEY:"+processDefinition.getKey()
							 +"---->流程定义版本:"+processDefinition.getVersion()
							 +"---->流程配置文件名:"+processDefinition.getResourceName()
							 +"---->资源名称:"+processDefinition.getDiagramResourceName()
							 +"---->部署id:"+processDefinition.getDeploymentId()); 
			
			//查看流程图 部署id  流程图名称
//    		InputStream in = null;
//			in =  repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), processDefinition.getDiagramResourceName());
//			for (int i = -1; (i = in.read())!=-1;) {
//				 OutputStream.write(i);
//			}
    	}
    	
    	
    	//部署信息查询
    	List<Deployment> deploymentList = repositoryService.createDeploymentQuery().list();
    	for (Deployment deployment : deploymentList) {
    		System.out.println("第三个打印：流程ID:"+deployment.getId()
			 +"---->流程NAME:"+deployment.getName()
			 +"---->流程部署时间:"+ DateUtil.dateToString(deployment.getDeploymentTime(),"YYYY-MM-dd HH:mm:ss")
			 +"---->流程:"+deployment.getCategory()
			 ); 
		}
    	
    	
    	//发布流程
    	 /** 
         * 启动请假单流程  并获取流程实例 
         * 因为该请假单流程可以会启动多个所以每启动一个请假单流程都会在数据库中插入一条新版本的流程数据 
         * 通过key启动的流程就是当前key下最新版本的流程 
         *  
         */  
    /*    ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("sfdaAudit");  
        System.out.println("第四个打印：流程id:"+processInstance.getId()
        				  +"---->流程activitiId:"+processInstance.getActivityId()
        				  +"---->流程name:"+ processInstance.getName()
					      +"---->流程业务key:"+processInstance.getBusinessKey()
					      +"---->Variables:"+processInstance.getProcessVariables()
					      +"----> Name:"+processInstance.getProcessDefinitionKey()
					      +"----> KEY:"+processInstance.getProcessDefinitionName()
					      +"----> version:"+processInstance.getProcessDefinitionVersion()
					      +"----> instanceId:"+processInstance.getProcessInstanceId()); */ 
        
    }
    
    
    @Test
    public void TestDel() throws Exception {
    	//部署对象id 删除流程
    	
    	// 创建流程引擎
    	ProcessEngine processEngine = factoryBean.getObject();
    	assertNotNull(processEngine.getRuntimeService());
    	
    	// 获取流程存储服务组件
    	RepositoryService repositoryService = processEngine.getRepositoryService();
    	long count = repositoryService.createDeploymentQuery().count();
    	List<Deployment> list = repositoryService.createDeploymentQuery().list();
    	for (Deployment deployment : list) {
    		System.out.println("===【部署流程个数】----->"+count
    				+"----->【部署id】："+deployment.getId()
    				+"---->【部署Name】:"+deployment.getName());
		}
    	
    	long processCount = repositoryService.createProcessDefinitionQuery().count();
    	List<ProcessDefinition> list1 = repositoryService.createProcessDefinitionQuery().list();
    	for (ProcessDefinition processDefinition : list1) {
    		System.out.println("【部署流程个数】----->"+processCount
    				+"----->【部署id】："+processDefinition.getDeploymentId()
    				+"----->【流程名称】："+processDefinition.getName()
    				+"----->【其他】："+processDefinition.getResourceName());
		}
    	
//    	String deploymentId = "17501";
//    	repositoryService.deleteDeployment(deploymentId,true);
    }
    
    
    @Test
    public void testStartProcess() throws Exception {
    	/*********************************************************************
         * 待办页面的数据：
         * 单位编码     单位名称     部门名称   部门编码    姓名   工号     审批内容名称【司法档案、荣誉技能、业务考核】   审批类型【已审批，待审批】  审批状态【同意，不同意】 审批意见   审批时间
         * 
        /*********************************************************************/
/*        DBYW dbyw = new DBYW();
        dbyw.setLx("2");
        dbyw.setBspdwmc("海南省人民检察院");
        dbyw.setBspdwbm("460000");
        dbyw.setBspbmmc("侦查技术处");
        dbyw.setBspbmbm("0003");
        dbyw.setSsr("0059");
        dbyw.setWbid("da_id_20180505");
        dbyw.setClnr("司法档案审批");
        dbyw.setRq(DateUtil.getCurrDate("yyyy-MM-dd"));
        dbyw.setYwlx("2");*/
        //或者是审批表 
    	//1、根据页面传来的信息进行查询此人的个人信息，注意部门信息可能有多个，但是只能取一个
    	//引入loginService,resList = loginService.getGrxxByDwbmAndGh(paramDwbm, paramGh);【出来的全是大写】
    	
    	//查询待办任务的时候 应该是去查询审批表状态为1的数据。 但是搜索的任务名称 应该是发起人的。
    	
        Splc sp = new Splc();
        sp.setSpdw("460000");
        sp.setSpdwmc("海南省人民检察院");
        sp.setSpstid("spid_123456789_fqr");
        sp.setSpbm("0005");
        sp.setSpbmmc("侦查侦监处");
        sp.setSplx("2");
        
        
        // 创建流程引擎
    	ProcessEngine processEngine = factoryBean.getObject();
    	assertNotNull(processEngine.getRuntimeService());
    	
    	// 获取流程存储服务组件
    	RepositoryService repositoryService = processEngine.getRepositoryService();
    	
    	// 获取运行时服务组件
    	RuntimeService runtimeService = processEngine.getRuntimeService();
    	//流程id
    	String lc_key = "sfdaAudit";
    	Map<String,Object> paramsMap = new HashMap<String,Object>();
    	//存入dabm_gh
    	String user_name = "460000_0059";
    	paramsMap.put("FQR_DWBM_GH", user_name);
    	//业务id
    	String da_id = "daid123456789";
    	String sp_id = "spid123456789";
    	runtimeService.startProcessInstanceByKey(lc_key,da_id, paramsMap);
    }
    
    /**
     * 查询代办
     * @throws Exception
     */
    @Test
    public void testQueryProcess() throws Exception {
    	 // 创建流程引擎
    	ProcessEngine processEngine = factoryBean.getObject();
    	assertNotNull(processEngine.getRuntimeService());
    	
    	// 获取流程存储服务组件
    	RepositoryService repositoryService = processEngine.getRepositoryService();
    	
    	// 获取运行时服务组件
    	RuntimeService runtimeService = processEngine.getRuntimeService();
    	
    	//查询多条任务
    	String user_name = "460000_0059";
    	List<Task> taskList = processEngine.getTaskService().createTaskQuery().taskAssignee(user_name).list();
    	for (Task task : taskList) {
			System.out.println("【查询杨宇的申请审批业务】--->任务id:"+task.getId()
							   +"--->任务名称："+task.getName()
							   +"--->创建时间："+DateUtil.dateToString(task.getCreateTime(),"YYYY-MM-dd HH:mm:ss")
							   +"--->姓名："+task.getAssignee()
							   +"---> "+task.getDescription()
							   +"---> 任务表单form的值："+task.getFormKey());	
		}
    	
    	//查询单条任务
    	Task singleTask = processEngine.getTaskService().createTaskQuery().taskAssignee(user_name).singleResult();
    	
    	//添加批注信息,注意设置用户【记录当前申请人的审核信息、审批意见】  comment
    	Authentication.setAuthenticatedUserId("460000_0059"); //认证中心
    	String processInstanceId = singleTask.getProcessInstanceId();
    	String message = "领导同意了";
    	processEngine.getTaskService().addComment(singleTask.getId(), processInstanceId, message);
    	
    	//根据任务id完成任务信息、流程变量
    	//【非领导】
    	processEngine.getTaskService().complete(singleTask.getId());
    	
    	//【领导的同意】  
    	Map<String,Object> paramsMap = new HashMap<String,Object>();
    	paramsMap.put("AGREE", "1");  //对应连线的同意、不同意
    	processEngine.getTaskService().complete(singleTask.getId(),paramsMap);
    	
    	//设置下一个处理人
    	processEngine.getTaskService().setAssignee(user_name, user_name);
    	
    	//判定流程是否结束
    	ProcessInstance pi = processEngine.getRuntimeService().createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
    	if(pi==null) {
    		//将审批表的状态更新
    		
    	}
    	
    	
    }
    
    
    @SuppressWarnings("all")
	@Test
    public void testExcelImport() {
    	String path = "D:\\人员录入通用表.xls";
    	SfdaGrjbxxInputExcel person = new SfdaGrjbxxInputExcel();
    	SfdaGrjlInputExcel personJl = new SfdaGrjlInputExcel();
		try {
//			List<Map<String, Object>> objList = ExcelUtil.importExcelData(path, 1,4, personJl);//ExcelUtil.importExcelData(path, 0,3, person);
//			for (Map<String, Object> map : objList) {
//				for (String key : map.keySet()) {
//					System.out.println(key+"====="+map.get(key).toString());
//				}
//			}
			/*int s = objList.size();
			for (int i=0;i<s;i++) {
				System.out.println(objList.get(i)+"========="+objList.get(i).entrySet().iterator().next());
			}*/
		} catch (Exception e) {
			e.printStackTrace();
		}
		
    }
    
    
    public static void main(String[] args) {
    	int year = 2013;
    	int month = 2;
    	Calendar c = Calendar.getInstance();
    	c.set(year, month, 3); //输入类型为int类型

    	int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
    	System.out.println(year + "年" + month + "月有" + dayOfMonth + "天");
    	
    	
    	//读取配置文件

	}
    
    @SuppressWarnings("all")
	private Properties loadProperty() {  
        Properties prop=new Properties();  
        try {  
            InputStream is=this.getClass().getResourceAsStream("/resource/templet/templet_ywkhzb.xls");  
            prop.load(is);  
            is.close();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return prop;  
    }  
    
    @Test
    public void testQuartz() {
    	QuartzJob jobClass = new QuartzJob();
    	String jobName = "test";
    	String jobGroupName = "test_group";
    	String triggerName = "trigger_test";
    	String triggerGroupName = "trigger_test_group";
    	String time = "/1 * * * * ?";
    	QuartzManager.addJob(jobName, jobGroupName, triggerName, triggerGroupName, jobClass.getClass(), time);
//    	CronTrigger trigger = (CronTrigger)ApplicationContextUtils.getBean("synchroDataTrigger");
//    	System.out.println(trigger.getCronExpression()+"---------------");
//    	JobDetail jobDetail = (JobDetail)ApplicationContextUtils.getBean("synchroDataHandler");
//    	System.out.println("========="+jobDetail.getJobBuilder());
    }
}