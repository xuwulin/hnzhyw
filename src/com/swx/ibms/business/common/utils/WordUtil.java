package com.swx.ibms.business.common.utils;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 适用于word 2007
 * poi 版本 3.7
 */
public class WordUtil {

    /**
     * 根据指定的参数值、模板，生成 word 文档
     * @param param 需要替换的变量
     * @param template 模板
     */
    public static CustomXWPFDocument generateWord(Map<String, Object> param, String template,
    		JsonArray rowsArray,JsonArray columnNameArray,JsonArray titleArray,JsonArray footArray) {
        CustomXWPFDocument doc = null;
       
        try {
            OPCPackage pack = POIXMLDocument.openPackage(template);
            doc = new CustomXWPFDocument(pack);
            if (param != null && param.size() > 0) {
                
                //处理段落
                List<XWPFParagraph> paragraphList = doc.getParagraphs();
                processParagraphs(paragraphList, param, doc);
                
                //处理表格
                Map<String,Object> params = new HashMap<String,Object>();
                params.put("${h_pfbf}", 
                		titleArray.get(0).toString().replaceAll("\"", ""));
        		params.put("${h_pflb}", 
        				titleArray.get(Constant.NUM_1).toString().replaceAll("\"", ""));
        		params.put("${h_fs}", 
        				titleArray.get(Constant.NUM_2).toString().replaceAll("\"", ""));
        		params.put("${h_zbx}", 
        				titleArray.get(Constant.NUM_3).toString().replaceAll("\"", ""));
        		params.put("${h_jcgzp}", 
        				titleArray.get(Constant.NUM_4).toString().replaceAll("\"", ""));
        		params.put("${h_bmpf}", 
        				titleArray.get(Constant.NUM_5).toString().replaceAll("\"", ""));
        		if(titleArray.size()==Constant.NUM_9){
	        		params.put("${h_jckhpf}", 
	        				titleArray.get(Constant.NUM_6).toString().replaceAll("\"", ""));
	        		params.put("${h_kpwyhpf}", 
	        				titleArray.get(Constant.NUM_7).toString().replaceAll("\"", ""));
	        		params.put("${h_pjdf}", 
	        				titleArray.get(Constant.NUM_8).toString().replaceAll("\"", ""));
        		}else{
        			params.put("${h_jckhpf}", 
	        				StringUtils.EMPTY);
        			params.put("${h_kpwyhpf}", 
            				titleArray.get(Constant.NUM_6).toString().replaceAll("\"", ""));
            		params.put("${h_pjdf}", 
            				titleArray.get(Constant.NUM_7).toString().replaceAll("\"", ""));
        		}
        		//替换表头信息
        		replaceInTable(doc, params);
        		//填充表格数据信息
        		List<XWPFTable> tableList = doc.getTables();
        		XWPFTable table = tableList.get(0);
                for(int i = 0; i<rowsArray.size(); i++){
    				JsonObject row = rowsArray.get(i).getAsJsonObject();
    				
    				String [] tableData = new String[columnNameArray.size()];
    				for(int l=0;l<columnNameArray.size();l++){
    					tableData[l] = row.get(columnNameArray.get(l).getAsString()).getAsString();
    				}
    				addRow(table, tableData);
                }
                //填充页脚行数据
                String [] footData = new String[columnNameArray.size()];
                JsonObject footrow = footArray.get(0).getAsJsonObject();
                for(int l=0;l<columnNameArray.size();l++){
                	try {
						footData[l] = footrow.get(
								columnNameArray.get(l).getAsString()).getAsString();
					} catch (Exception e) {
						footData[l] = "";
					}
				}
                addRow(table, footData);
                //对表格进行单元格合并
//                int [] colName = {1};
//                groupTable(table,colName);
//                mergeCellsVertically(table, 1, 0, 2);
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return doc;
    }
    /**
     * 处理段落
     * @param paragraphList
     */
    @SuppressWarnings("unchecked")
    public static void processParagraphs(List<XWPFParagraph> paragraphList,Map<String, Object> param,CustomXWPFDocument doc){
        List<XWPFRun> runs = null;
        String text = null;
        boolean isSetText = false;
        String key = null;
        Object value = null;
        Map<String, Object> pic = null;
        int width =  0;
        int height = 0;
        int picType = 0;
        byte[] byteArray = null;
        ByteArrayInputStream byteInputStream = null;
        int ind = 0;
        if(paragraphList != null && paragraphList.size() > 0){
            for(XWPFParagraph paragraph:paragraphList){
            	together(paragraph,param);
            	runs = paragraph.getRuns();
                for (XWPFRun run : runs) {
                    isSetText = false;
                    text = run.getText(0);
                    if(text != null){
                        for (Entry<String, Object> entry : param.entrySet()) {
                            key = entry.getKey();
                            if(text.indexOf(key) != -1){
                                isSetText = true;
                                value = entry.getValue();
                                if (value instanceof String) {//文本替换
                                    text = text.replace(key, value.toString());
                                } else if (value instanceof Map) {//图片替换
                                    text = text.replace(key, "");
                                    pic = (Map<String, Object>)value;
                                    width = Integer.parseInt(pic.get("width").toString());
                                    height = Integer.parseInt(pic.get("height").toString());
                                    picType = getPictureType(pic.get("type").toString());
                                    byteArray = (byte[]) pic.get("content");
                                    byteInputStream = new ByteArrayInputStream(byteArray);
                                    try {
                                        ind = doc.addPicture(byteInputStream,picType);
                                        doc.createPicture(ind, width , height,paragraph);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                        if(isSetText){
                            run.setText(text,0);
                        }
                    }
                }
            }
        }
    }
    /**
     * 根据图片类型，取得对应的图片类型代码
     * @param picType
     * @return int
     */
    private static int getPictureType(String picType){
        int res = CustomXWPFDocument.PICTURE_TYPE_PICT;
        if(picType != null){
            if(picType.equalsIgnoreCase("png")){
                res = CustomXWPFDocument.PICTURE_TYPE_PNG;
            }else if(picType.equalsIgnoreCase("dib")){
                res = CustomXWPFDocument.PICTURE_TYPE_DIB;
            }else if(picType.equalsIgnoreCase("emf")){
                res = CustomXWPFDocument.PICTURE_TYPE_EMF;
            }else if(picType.equalsIgnoreCase("jpg") || picType.equalsIgnoreCase("jpeg")){
                res = CustomXWPFDocument.PICTURE_TYPE_JPEG;
            }else if(picType.equalsIgnoreCase("wmf")){
                res = CustomXWPFDocument.PICTURE_TYPE_WMF;
            }
        }
        return res;
    }
    /**
     * 将输入流中的数据写入字节数组
     * @param in
     * @return
     */
    public static byte[] inputStream2ByteArray(InputStream in,boolean isClose){
        byte[] byteArray = null;
        try {
            int total = in.available();
            byteArray = new byte[total];
            in.read(byteArray);
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(isClose){
                try {
                    in.close();
                } catch (Exception e2) {
//                    System.out.println("关闭流失败");
                }
            }
        }
        return byteArray;
    }

// * @param i hhh
    /**
     * 替换表格里面的变量 
     * 
     * @param doc    要替换的文档 
     * @param params 参数 
     */
    private static void replaceInTable(XWPFDocument doc, Map<String, Object> params) {  
         
        List<XWPFTable> tableList = doc.getTables();
        XWPFTable table = tableList.get(0);  
        List<XWPFTableRow> rows;  
        List<XWPFTableCell> cells;  
        List<XWPFParagraph> paras;
        
        rows = table.getRows();
        for(XWPFTableRow row:rows){
	        cells = row.getTableCells();  
	        for (XWPFTableCell cell : cells) {  
	            paras = cell.getParagraphs();  
	            for (XWPFParagraph para : paras) {  
	                replaceInPara(para, params);
	            }  
	        }
        }        
        
    } 
    
    
    /** 
     * 替换表格里面的变量 
     * 
     * @param para   要替换的段落 
     * @param params 参数 
     */  
    private static void replaceInPara(XWPFParagraph para, Map<String, Object> params) {
        List<XWPFRun> runs;  
        
        if (matcher(para.getParagraphText()).find()) {  
            runs = para.getRuns();  
            int start = -1;  
            int end = -1;  
            String str = "";  
            for (int i = 0; i < runs.size(); i++) {  
                XWPFRun run = runs.get(i);  
                String runText = run.toString();  
                if ('$' == runText.charAt(0)&&'{' == runText.charAt(1)) {  
                    start = i;  
                }  
                if ((start != -1)) {  
                    str += runText;  
                }  
                if ('}' == runText.charAt(runText.length() - 1)) {  
                    if (start != -1) {  
                        end = i;  
                        break;  
                    }  
                }  
            }  
  
            for (int i = start; i <= end; i++) {  
                para.removeRun(i);  
                i--;  
                end--;  
            }  
  
            for (String key : params.keySet()) {  
                if (str.equals(key)) {  
                    para.createRun().setText((String) params.get(key));  
                    break;  
                }  
            }  
  
  
        }  
    }  
    
    /** 
     * 正则匹配字符串 
     * 
     * @param str 
     * @return 
     */  
    private static Matcher matcher(String str) {  
        Pattern pattern = Pattern.compile("\\$\\{(.+?)\\}", Pattern.CASE_INSENSITIVE);  
        Matcher matcher = pattern.matcher(str);  
        return matcher;  
    } 
    
    /**
     * 重组段落结构
     * @param paragraph 需要重组的段落
     * @param param 替换字符map
     * @return 重组后的段落
     */
    private static void together(XWPFParagraph paragraph,Map<String,Object> param){
    	
    	List<XWPFRun> runList = paragraph.getRuns();
    	
    	Set<String> keySet = param.keySet();
    	Iterator<String> it = keySet.iterator();
    			
    	String [] keyArray = new String[keySet.size()];
    	
    	for(int i = 0;i<keyArray.length;i++){
    		if(it.hasNext()){
    			String str = it.next();
    			keyArray[i] = str;
    		}
    	}
    	
    	for(int i=0;i<keyArray.length;i++){
    		String placeHolder = keyArray[i];
    		TextSegement found = paragraph.searchText(placeHolder, new PositionInParagraph());
        	
        	if(found == null){
        		continue ;
        	}
        	
        	int beginRun = found.getBeginRun();
        	int endRun = found.getEndRun();
        	
        	//如果字符串在一个run中，就不用合并，如果不在一个run中，进行合并
        	if(beginRun != endRun){
        		StringBuilder b = new StringBuilder();
        		
        		for (int runPos = beginRun ; runPos<=endRun ; runPos++){
        			XWPFRun run = runList.get(runPos);
        			b.append(run.getText(run.getTextPosition()));
        		}
        		
        		String connectedRuns = b.toString();
        		
        		XWPFRun partOne = runList.get((beginRun+endRun)/2);
        		partOne.setText(connectedRuns, 0);
        		
        		paragraph.removeRun(endRun);
        		paragraph.removeRun(beginRun);
        		
        	}
    	}
    }

    //     * @param tablemap 数据Map
    /**
     * 给某个表添加数据
     * @param table 表格对象
     */
    private static void addRow(XWPFTable table ,String [] tableData){
    	XWPFTableRow newRow = table.createRow();
    	List<XWPFTableCell> cells = newRow.getTableCells();
    	
    	for(int i = 0;i<cells.size();i++){
    		XWPFTableCell cell = cells.get(i);
    		cell.setText(tableData[i]);
//    		System.out.println(cell.getParagraphs().get(0));
    	}
//    	table.addRow(newRow,table.getRows().size());
    }
    
    
    /**
     * 将表格中的列进行合并
     * @param table 表格对象
     * @param colName 需要合并的列的列号数组
     */
    public static void groupTable(XWPFTable table,int [] colName){
    	for(int i = 0; i<colName.length;i++){
    		List<XWPFTableRow> rows = table.getRows();
    		
    		int mergeNumber = 0;
    		
    		for(int rownum = 0;rownum<rows.size();rownum=rownum+mergeNumber+1){
    			
    			mergeNumber = 0;
    			int startnumber = rownum;
//    			System.out.println(startnumber);
    			XWPFTableRow startrow = rows.get(startnumber);
    			
    			for(int nextrownum = startnumber+1;nextrownum<rows.size();nextrownum++){
    				XWPFTableRow nextrow = rows.get(nextrownum);
    				XWPFTableCell startCell = startrow.getCell(colName[i]);
    				XWPFTableCell endCell = nextrow.getCell(colName[i]);
    				//如果同列的上一个单元格的值与下一个单元格的值相同，跨行数+1，否则开始跨行操作
    				if(startCell.getText().equals(endCell.getText())){
    					mergeNumber++;
    				}else{
    					
    					
    					mergeCellsVertically(table, colName[i], startnumber, nextrownum-1);
						if(colName[i] ==1){
							mergeCellsVertically(table, 2, startnumber, nextrownum-1);
							mergeCellsVertically(table, table.getRows().get(0)
									.getTableCells().size()-1, startnumber, nextrownum-1);
						}
    					
    					break;
    				}
    			}
    		}
    	}
    }
    
    
    /**
     * 跨行合并单元格
     * @param table 表格对象
     * @param col 列号
     * @param fromRow 起始行
     * @param toRow 终止行
     */
    public static void mergeCellsVertically(XWPFTable table, int col, int fromRow, int toRow){
    	for (int rowIndex = fromRow; rowIndex <= toRow; rowIndex++) {    
            XWPFTableCell cell = table.getRow(rowIndex).getCell(col);    
            if ( rowIndex == fromRow ) {    
                // The first merged cell is set with RESTART merge value    
                cell.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.RESTART);    
            } else {    
                // Cells which join (merge) the first one, are set with CONTINUE    
                cell.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.CONTINUE);    
            }    
        }    
    }
    
}

