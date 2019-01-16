package com.swx.ibms.business.common.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@SuppressWarnings("all")
public class ExcelUtil {

    final static String NO_DEFINE = "no_define";//未定义的字段
    final static String DEFAULT_DATE_PATTERN = "yyyy年MM月dd日";//默认日期格式
    final static int DEFAULT_COLOUMN_WIDTH = 17;

    /**
     * 导出数据
     * 注：此方法（修改前）仅适用于导出个人绩效，涉及到单元格合并
     * 现在修改为可以复用，导出检察官完成/办结案件统计信息，不需要合并单元格
     * @param os
     * @param sheetName 工作簿名
     * @param titleName 标题名
     * @param jsonArr 表格数据
     * @param isMerger 是否要合并单元格
     * @throws IOException
     */
    @SuppressWarnings("all")
    public static void exportExcelNotUtils(OutputStream os,
                                           String sheetName,
                                           String titleName,
                                           JsonArray jsonArr,
                                           Boolean isMerger) throws IOException {

        // 创建新的Excel工作簿
        Workbook workbook = new HSSFWorkbook();
        //设置工作薄的内容垂直居中、水平居中
        HSSFCellStyle allCellStyle = (HSSFCellStyle) workbook.createCellStyle(); // 样式对象       
        //居中显示
        allCellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直       
        allCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平
        //边框加线
        allCellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        allCellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);//下边框
        allCellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        allCellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        //字体
        HSSFFont textFont = (HSSFFont) workbook.createFont();
        textFont.setFontName("仿宋");
        textFont.setFontHeightInPoints((short) 12);
        allCellStyle.setFont(textFont);
        //自动换行
        allCellStyle.setWrapText(true);

        //表头样式
        HSSFCellStyle titleStyle = (HSSFCellStyle) workbook.createCellStyle();
        titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        HSSFFont titleFont = (HSSFFont) workbook.createFont();
        titleFont.setFontName("仿宋");
        titleFont.setFontHeightInPoints((short) 14);
        titleFont.setBoldweight((short) 700);
        titleStyle.setFont(titleFont);

        // 生成一个(带标题)工作薄
        HSSFSheet sheet = (HSSFSheet) workbook.createSheet(sheetName);

        //标题行
        Row titleRow = sheet.createRow((short) 0);
        titleRow.createCell((short) 0).setCellValue(titleName);
        HSSFCell titleCell = (HSSFCell) titleRow.getCell((short) 0);
        titleCell.setCellStyle(titleStyle);
        //合并列  合并列
        sheet.addMergedRegion(new CellRangeAddress((short) 0, (short) 0, (short) 0, (short) 9));

        //第二行
        Row headerRow = sheet.createRow((short) 1);
        //第三行
        Row headerContentRow = sheet.createRow((short) 2);
        //第五行
        Row row5 = sheet.createRow((short) 4);
//		sheet.addMergedRegion(new CellRangeAddress((short)3,(short)4, (short)0, (short)0)); 
//		sheet.addMergedRegion(new CellRangeAddress((short)3,(short)4, (short)1, (short)1));
//		sheet.addMergedRegion(new CellRangeAddress((short)3,(short)4, (short)2, (short)2));
//		sheet.addMergedRegion(new CellRangeAddress((short)3,(short)4, (short)3, (short)3));
//		sheet.addMergedRegion(new CellRangeAddress((short)3,(short)3, (short)4, (short)5));
//		sheet.addMergedRegion(new CellRangeAddress((short)3,(short)3, (short)6, (short)7));
//		sheet.addMergedRegion(new CellRangeAddress((short)3,(short)3, (short)8, (short)9));
//		sheet.addMergedRegion(new CellRangeAddress((short)3,(short)4, (short)10, (short)10));

        List<Integer> list = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();
        String listName = "";
        String listName2 = "";
        int listNum = 0;
        int listNum2 = 0;
        for (int i = 0; i < jsonArr.size(); i++) {
            JsonObject jsonObj = jsonArr.get(i).getAsJsonObject();
            // 需要合并单元格
            if (isMerger) {
                if(i>2){
                    if(listName.equals(jsonObj.get("coloum" + (1)).getAsString())){
                        listNum++;
                    }else {
                        list.add(listNum);
                        listName = jsonObj.get("coloum" + (1)).getAsString();
                        listNum = 1;
                    }
                    if(listName2.equals(jsonObj.get("coloum" + (2)).getAsString())){
                        listNum2++;
                    }else {
                        list2.add(listNum2);
                        listName2 = jsonObj.get("coloum" + (2)).getAsString();
                        listNum2 = 1;
                    }
                }
            }

            if ("2".equals(jsonObj.get("line").getAsString())) {
                for (int j = 0; j < jsonObj.get("length").getAsInt(); j++) {
                    headerRow.createCell(j).setCellValue(jsonObj.get("coloum" + (j + 1)).getAsString());
                    headerRow.getCell((short) j).setCellStyle(allCellStyle);
                }
            } else if ("3".equals(jsonObj.get("line").getAsString())) {
                for (int j = 0; j < jsonObj.get("length").getAsInt(); j++) {
                    headerContentRow.createCell(j).setCellValue(jsonObj.get("coloum" + (j + 1)).getAsString());
                    headerContentRow.getCell((short) j).setCellStyle(allCellStyle);
                }
            } else if ("5".equals(jsonObj.get("line").getAsString())) {
                for (int j = 0; j < jsonObj.get("length").getAsInt(); j++) {
                    row5.createCell(j).setCellValue(jsonObj.get("coloum" + (j + 1)).getAsString());
                    row5.getCell((short) j).setCellStyle(allCellStyle);
                }
            } else {
                //excel需要填入的数据
                Row row6 = sheet.createRow(i + 2);
                for (int j = 0; j < jsonObj.get("length").getAsInt(); j++) {
                    row6.createCell(j).setCellValue(jsonObj.get("coloum" + (j + 1)).getAsString());
                    row6.getCell((short) j).setCellStyle(allCellStyle);
                }
            }
        }

        if (isMerger) {
            int num = 5;
            int num2 = 0;
            for(int i = 1;i<list.size();i++){
                if(list.get(i)==1){
                    num++;
                    continue;
                }
                sheet.addMergedRegion(new CellRangeAddress((short)num,(short)num+list.get(i)-1, (short)0, (short)0));
                num = num+list.get(i);
            }
            num = 5;
            num2 = 0;
            for(int i = 1;i<list2.size();i++){
                if(list2.get(i)==1){
                    num++;
                    continue;
                }
                sheet.addMergedRegion(new CellRangeAddress((short)num,(short)num+list2.get(i)-1, (short)1, (short)1));
                sheet.addMergedRegion(new CellRangeAddress((short)num,(short)num+list2.get(i)-1, (short)2, (short)2));
                num = num+list2.get(i);
            }
        }

        // 调整列的自适应宽度
        sheet.autoSizeColumn((short) 0);
        sheet.autoSizeColumn((short) 1);
        sheet.autoSizeColumn((short) 2);
        sheet.autoSizeColumn((short) 3);
        sheet.autoSizeColumn((short) 4);
        sheet.autoSizeColumn((short) 5);
        sheet.autoSizeColumn((short) 6);
        sheet.autoSizeColumn((short) 7);
        sheet.autoSizeColumn((short) 8);
        sheet.autoSizeColumn((short) 9);
        sheet.autoSizeColumn((short) 10);

        try {
            workbook.write(os);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private static Map<String, Object>  dataObj(Object obj, HSSFRow row) throws Exception {    
        Class<?> rowClazz= obj.getClass();
        Field[] fields = FieldUtils.getAllFields(rowClazz);    
        if (fields == null || fields.length < 1) {    
            return null;    
        }    
            
        //容器    
        Map<String, Object> map = new HashMap<String, Object>();    
            
        //【注意：excel表格字段顺序要和obj字段顺序对齐】 （如果有多余字段请另作特殊下标对应处理）    
        for (int j = 0; j < fields.length; j++) {    
            map.put(fields[j].getName(), getVal(row.getCell(j)));    
        }    
        return map;     
    }   
    
    /**
     * 通用导入excel数据【03/07版本】
     * @param file 二进制文件
     * @param sheetNum 第几个工作簿
     * @param rowNum 开始读取excel的行数
     * @param obj 装载数据的对象
     * @return 数据的集合
     */
    public static List<Map<String, Object>> importExcelData(MultipartFile file,Integer sheetNum,Integer rowNum,Object obj) { 
    	//容器    
    	List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>();    
    	try {
	    	InputStream fis=  file.getInputStream();//new FileInputStream(filePath);  
	        HSSFWorkbook hw= new HSSFWorkbook(fis);  
	        //获取第一张Sheet表 
	        HSSFSheet sheet = hw.getSheetAt(sheetNum);  
	        int maxRow = sheet.getLastRowNum();
	            
	        //遍历行 从下标第一行开始（去除标题）    
	        for (int i = rowNum; i <= maxRow; i++) {    
	            HSSFRow row= sheet.getRow(i);    
	            if(row!=null){    
	                //装载obj    
					ret.add(dataObj(obj,row));
	            }    
	        }
    	} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}  
        return ret;  
    }
    
	public static String getVal(HSSFCell hssfCell) {    
		String cellValue = StringUtils.EMPTY;
		if(null != hssfCell) {
			switch (hssfCell.getCellType()) {
			case HSSFCell.CELL_TYPE_BOOLEAN:  //布尔类型
				cellValue = String.valueOf(hssfCell.getBooleanCellValue());
				break;
			case HSSFCell.CELL_TYPE_NUMERIC: //数字/日期类型
				if(HSSFDateUtil.isCellDateFormatted(hssfCell)) {
	        		short format = hssfCell.getCellStyle().getDataFormat();  
	            	SimpleDateFormat sdf = null;  
	            	if(format == 14 || format == 31 || format == 57 || format == 58){  
	            	   sdf = new SimpleDateFormat("yyyy-MM-dd");  
	            	}else if (format == 20 || format == 32) {  
	            	   sdf = new SimpleDateFormat("HH:mm");  
	            	}else if (format == 176) {  //目前自定义格式一
	            		sdf = new SimpleDateFormat("yyyy-MM-dd");  
	             	}else if (format == 177) {  //目前自定义格式二
	            		sdf = new SimpleDateFormat("yyyy-MM");  
	             	}else {
	            		sdf = new SimpleDateFormat("yyyy-MM-dd");  
	            	}  
	            	double value = hssfCell.getNumericCellValue();  
	            	Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(value);  
	            	cellValue = sdf.format(date);
	        	}else {
	        		// 返回数值类型的值
	        		cellValue = String.valueOf(hssfCell.getNumericCellValue());
	        	}
				break;
			case HSSFCell.CELL_TYPE_BLANK: //空
				cellValue = StringUtils.EMPTY;   
	            break;      
	        case HSSFCell.CELL_TYPE_FORMULA:   
	        	cellValue = String.valueOf(hssfCell.getCellFormula());   
	            break;  
	        case HSSFCell.CELL_TYPE_ERROR:   
	        	cellValue = String.valueOf(hssfCell.getErrorCellValue());   
	            break; 
			default:
				cellValue = String.valueOf(hssfCell);
				break;
			}
		}
        return cellValue;
    }  
    
}
