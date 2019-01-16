package com.swx.ibms.business.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wh on 2017/12/8.
 */
public class OutNdkhExcel {

    /**
     * 导出数据
     */
    @SuppressWarnings("all")
    public static Map<String,String> dcndkh(List<Map<String, Object>> mapList, String strdwmc, String kwdwbm, String strkhlxmc, String khlx, String starDate, String endDate, HttpServletRequest request) throws IOException {
       
    	//集合大小
    	int mapListSize = mapList.size();
    	
    	//返回文字
        boolean sfysj = false;
        //业务考核汇总存放地址
        String dz = request.getServletContext().getRealPath(File.separator+"ywkhhz");
        File baseFilePath = new File(dz);
        if (!baseFilePath.exists()) {
        	baseFilePath.mkdirs();
        }
        OutputStream os = null;
        //文件名称
        String firstName = starDate + "至" + endDate + "-检察院业务考核汇总表";
        String lastName = ".xls";
        //文件名称后缀数

        File file = new File(dz + File.separator + firstName + lastName);
        os = new FileOutputStream(file);

        //单位map：单位编码：单位名称
        Map<String, String> dwKeyMap = new HashMap<>();
        //类型map：类型编号：类型名称
        Map<String, String> lxKeyMap = new HashMap<>();
        //单位名称数组
        String[] strdwmcs = null;
        //单位编码数组
        String[] kwdwbms = null;
        //给数组赋值
        if (strdwmc.indexOf(",") != -1) {
            strdwmcs = strdwmc.split("\\,");
            kwdwbms = kwdwbm.split("\\,");
            for (int i = 0; i < strdwmcs.length; i++) {
                dwKeyMap.put(kwdwbms[i], strdwmcs[i]);
            }
        } else {
            strdwmcs = new String[1];
            kwdwbms = new String[1];
            strdwmcs[0] = strdwmc;
            kwdwbms[0] = kwdwbm;
            dwKeyMap.put(kwdwbm, strdwmc);
        }
        //考核类型名称数组
        String[] strkhlxmcs = null;
        //考核类型代码数组
        String[] khlxs = null;
        //给数组赋值
        if (strkhlxmc.indexOf(",") != -1) {
            strkhlxmcs = strkhlxmc.split("\\,");
            khlxs = khlx.split("\\,");
            for (int i = 0; i < strkhlxmcs.length; i++) {
                lxKeyMap.put(khlxs[i], strkhlxmcs[i]);
            }
        } else {
            strkhlxmcs = new String[1];
            khlxs = new String[1];
            strkhlxmcs[0] = strkhlxmc;
            khlxs[0] = khlx;
            lxKeyMap.put(khlx, strkhlxmc);
        }

        //创建工作表
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

        //通过类型进行创建sheet
        for (int i = 0; i < strkhlxmcs.length; i++) {
            //当考核类型为13时，不导出
            if ("13".equals(khlxs[i])) {
                continue;
            }
            sfysj = true;
            //数据赋值开始的number
            int sjNum = 4;
            //装数据的map
            Map<String, Map<Integer, Double>> valueMap = new HashMap<>();
            //sheetName
            String title = starDate + "至" + endDate + " " + strkhlxmcs[i] + "考核评价情况";
            // 生成一个(带标题)工作薄
            HSSFSheet sheet = (HSSFSheet) workbook.createSheet(strkhlxmcs[i] + "考核评价情况");

            //标题行
            Row titleRow = sheet.createRow((short) 0);
            //给标题赋值
            titleRow.createCell((short) 0).setCellValue(title);

            HSSFCell titleCell = (HSSFCell) titleRow.getCell((short) 0);
            //get标题加样式
            titleCell.setCellStyle(titleStyle);

            //第二行
            Row twoRow = sheet.createRow((short) 1);
            //第三行
            Row threeContentRow = sheet.createRow((short) 2);
            //第四行
            Row fourContentRow = sheet.createRow((short) 3);

            //横向number
            int oneNum = 0;
            //给第一竖前3个排赋值
            twoRow.createCell((short) oneNum).setCellValue("单位名称");
            threeContentRow.createCell((short) oneNum).setCellValue("单位名称");
            fourContentRow.createCell((short) oneNum).setCellValue("单位名称");
            //合并3个竖排
            sheet.addMergedRegion(new CellRangeAddress((short) 1, (short) 3, (short) oneNum, (short) oneNum));

            //检测map：看是否已经处理过这个类型的数据
            Map<String, String> matchMap = new HashMap<>();
            //考核单位：拿来获取对应数据
            String khdw = "";
            //最后接收的横排数最大值
            int sjNumber = 0;
            //开始对各种类型进行处理
            for (int m = 0; m < mapListSize; m++) {
                //类型名称
                String lxMc = lxKeyMap.get(mapList.get(m).get("khlx"));
                //给考核单位赋值
                khdw = mapList.get(m).get("khdw").toString();
                //给横向number初始化值
                oneNum = 1;

                //判断是否已经对该类型进行处理，并且，在接收数据中并没有该单位的信息
                if (strkhlxmcs[i].equals(lxMc) && matchMap.containsKey(strkhlxmcs[i]) && !valueMap.containsKey(khdw)) {
                    //创建一个map与来接收该单位的值
                    valueMap.put(khdw, new HashMap<Integer, Double>());
                    //数据源
                    JSONArray listMap = JSON.parseArray(mapList.get(m).get("zbkpgl").toString());
                    int listMapSize = listMap.size();
                    //从数据源获取相应数据
                    for (int n = 0; n < listMapSize; n++) {
                        Map<String, Object> stringMap = (Map<String, Object>) listMap.get(n);
//                        double bmpf = ("".equals(stringMap.get("bmpf")) || stringMap.get("bmpf") == null) ? 0 : Double.parseDouble(stringMap.get("bmpf").toString());
//                        valueMap.get(khdw).put(oneNum, bmpf);
//                        oneNum++;
                        double agpf = ("".equals(stringMap.get("agpf")) || stringMap.get("agpf") == null) ? 0 : Double.parseDouble(stringMap.get("agpf").toString());
                        valueMap.get(khdw).put(oneNum, agpf);
                        oneNum++;
                        double pjdf = ("".equals(stringMap.get("jcf")) || stringMap.get("jcf") == null) ? 0.00 : Double.parseDouble(stringMap.get("jcf").toString());
                        valueMap.get(khdw).put(oneNum, pjdf);
                        oneNum++;
                        double xmfz = ("".equals(stringMap.get("pjdf")) || stringMap.get("pjdf") == null) ? 0 : Double.parseDouble(stringMap.get("pjdf").toString());
                        valueMap.get(khdw).put(oneNum, xmfz);
                        oneNum++;
                    }
                    //添加总分数【ywzf-->zpjdf】
                    valueMap.get(khdw).put(oneNum, Double.parseDouble(("".equals(mapList.get(m).get("zpjdf")) || mapList.get(m).get("zpjdf") == null) ? "0" : mapList.get(m).get("zpjdf").toString()));
                }
                //当第一次遇到这个类型时，将对该类型进行处理
                if (strkhlxmcs[i].equals(lxMc) && !matchMap.containsKey(strkhlxmcs[i])) {
                    //创建一个map来接收对应数据
                    if (!valueMap.containsKey(khdw)) {
                        valueMap.put(khdw, new HashMap<Integer, Double>());
                    }
                    //在检测map中添加该考核类型名称
                    matchMap.put(strkhlxmcs[i], "");
                    //获取数据源数据
                    JSONArray listMap = JSON.parseArray(mapList.get(m).get("zbkpgl").toString());
                    int listMapSize = listMap.size();
                    
                    //对数据源进行处理
                    for (int n = 0; n < listMapSize; n++) {

                        Map<String, Object> stringMap = (Map<String, Object>) listMap.get(n);

                        String pfbf = stringMap.get("pfbf").toString();
                        //横向开始number
                        int startNum = oneNum;
                        //将评分制度分解成不同列
                        //给oneNum列赋值3个，并合并
                        twoRow.createCell((short) oneNum).setCellValue(stringMap.get("khzxm") + "（" + stringMap.get("xmfz") + "分）");
                        threeContentRow.createCell((short) oneNum).setCellValue(pfbf);
                        threeContentRow.createCell((short) oneNum + 1).setCellValue(pfbf);
                        sheet.addMergedRegion(new CellRangeAddress((short) 2, (short) 2, (short) oneNum, (short) oneNum + 1));

                        //给oneNum列赋值
                        fourContentRow.createCell((short) oneNum).setCellValue("数据");
                        double bmpf = ("".equals(stringMap.get("agpf")) || stringMap.get("agpf") == null) ? 0 : Double.parseDouble(stringMap.get("agpf").toString());

                        //将该项数据获取到
                        valueMap.get(khdw).put(oneNum, bmpf);
                        oneNum++;

                        //给oneNum列赋值
                        fourContentRow.createCell((short) oneNum).setCellValue("基础分");
                        double agpf = ("".equals(stringMap.get("jcf")) || stringMap.get("jcf") == null) ? 0.00 : Double.parseDouble(stringMap.get("jcf").toString());

                        //将该项数据获取到
                        valueMap.get(khdw).put(oneNum, agpf);
                        oneNum++;

                        //给oneNum列赋值3个，并合并
/*                        twoRow.createCell((short) oneNum).setCellValue(stringMap.get("khzxm") + "（" + stringMap.get("xmfz") + "分）");
                        threeContentRow.createCell((short) oneNum).setCellValue("基础分");
                        fourContentRow.createCell((short) oneNum).setCellValue("基础分");
                        double pjdf = ("".equals(stringMap.get("jcf")) || stringMap.get("jcf") == null) ? 0 : Double.parseDouble(stringMap.get("jcf").toString());

                        //将该项数据获取到
                        valueMap.get(khdw).put(oneNum, pjdf);
                        //合并列
                        sheet.addMergedRegion(new CellRangeAddress((short) 2, (short) 3, (short) oneNum, (short) oneNum));
                        oneNum++;*/

                        //给oneNum列赋值3个，并合并
                        twoRow.createCell((short) oneNum).setCellValue(stringMap.get("khzxm") + "（" + stringMap.get("xmfz") + "分）");
                        threeContentRow.createCell((short) oneNum).setCellValue("评价分");
                        fourContentRow.createCell((short) oneNum).setCellValue("评价分");
                        double xmfz = ("".equals(stringMap.get("pjdf")) || stringMap.get("pjdf") == null) ? 0 : Double.parseDouble(stringMap.get("pjdf").toString());
                        //将该项数据获取到
                        valueMap.get(khdw).put(oneNum, xmfz);

                        //合并列
                        sheet.addMergedRegion(new CellRangeAddress((short) 2, (short) 3, (short) oneNum, (short) oneNum));
                        oneNum++;

//                    }
                        //合并第二横排
                        sheet.addMergedRegion(new CellRangeAddress((short) 1, (short) 1, (short) startNum, (short) oneNum - 1));
                        //接收最大横排数
                        sjNumber = oneNum;
                        //将业务总分数据获取到【ywzf-->zpjdf】
                        valueMap.get(khdw).put(oneNum, Double.parseDouble(("".equals(mapList.get(m).get("zpjdf")) || mapList.get(m).get("zpjdf") == null) ? "0" : mapList.get(m).get("zpjdf").toString()));

                    }

                }
            }

            //给sjNumber列赋值3个，并合并
            twoRow.createCell((short) sjNumber).setCellValue("合计得分");
            threeContentRow.createCell((short) sjNumber).setCellValue("合计得分");
            fourContentRow.createCell((short) sjNumber).setCellValue("合计得分");

            //合并列
            sheet.addMergedRegion(new CellRangeAddress((short) 1, (short) 3, (short) sjNumber, (short) sjNumber));
            sjNumber++;

            //给sjNumber列赋值3个，并合并
          /*  twoRow.createCell((short) sjNumber).setCellValue("评价系数");
            threeContentRow.createCell((short) sjNumber).setCellValue("评价系数");
            fourContentRow.createCell((short) sjNumber).setCellValue("评价系数");
            //合并列
            sheet.addMergedRegion(new CellRangeAddress((short) 1, (short) 3, (short) sjNumber, (short) sjNumber));
            sjNumber++;

            //给sjNumber列赋值3个，并合并
            twoRow.createCell((short) sjNumber).setCellValue("换算成案管工作考评分");
            threeContentRow.createCell((short) sjNumber).setCellValue("换算成案管工作考评分");
            fourContentRow.createCell((short) sjNumber).setCellValue("换算成案管工作考评分");
            //合并列
            sheet.addMergedRegion(new CellRangeAddress((short) 1, (short) 3, (short) sjNumber, (short) sjNumber));*/

            //合并第一排的标题
            sheet.addMergedRegion(new CellRangeAddress((short) 0, (short) 0, (short) 0, (short) sjNumber));

            //对获取的的数据进行填充
            for (int q = 0; q < kwdwbms.length; q++) {
                Map<Integer, Double> valuesMap = valueMap.get(kwdwbms[q]);
                if (valuesMap == null) {
                    continue;
                }
                Row sjRow = sheet.createRow((short) sjNum);
                sjRow.createCell((short) 0).setCellValue(dwKeyMap.get(kwdwbms[q]));
                for (int w = 1; w <= valuesMap.size(); w++) {
                    sjRow.createCell((short) w).setCellValue(valuesMap.get(w));
                }
                sjNum++;
            }
        }
        Map<String,String> returnMap = new HashMap<>();

        if (sfysj) {
            try {
                //写出Excel；
                workbook.write(os);
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                //关闭流
                if (os != null) {
                    os.close();
                }
                returnMap.put("fileName",firstName+lastName);
                returnMap.put("filePath",dz+File.separator+firstName+lastName);
                //返回信息
                return returnMap;
            }
        }
        return returnMap;

    }


    /**
     * 导出数据
     @SuppressWarnings("all") public static boolean dcndkh(String dz, List<Map<String, Object>> mapList, String strdwmc, String kwdwbm, String strkhlxmc, String khlx, String starDate, String endDate) throws IOException {
     //返回文字
     boolean sfysj = false;
     OutputStream os = null;
     //文件名称
     String fileName = starDate + "至" + endDate + "  检察院业务考核汇总表";
     //文件名称后缀数
     int num = 1;
     //是否选择地址
     if (null == dz || "".equals(dz)) {
     File file = new File("D:" + File.separator + fileName + ".xls");
     while (file.exists()) {
     fileName = starDate + "至" + endDate + "  检察院业务考核汇总表" + "(" + num + ")";
     file = new File("D:" + File.separator + fileName + ".xls");
     num++;
     }
     os = new FileOutputStream("D:" + File.separator + fileName + ".xls");
     } else {
     File file = new File(dz + File.separator + fileName + ".xls");
     while (file.exists()) {
     fileName = starDate + "至" + endDate + "  检察院业务考核汇总表" + "(" + num + ")";
     file = new File(dz + File.separator + fileName + ".xls");
     num++;
     }
     os = new FileOutputStream(dz + File.separator + fileName + ".xls");
     }

     //单位map：单位编码：单位名称
     Map<String, String> dwKeyMap = new HashMap<>();
     //类型map：类型编号：类型名称
     Map<String, String> lxKeyMap = new HashMap<>();
     //单位名称数组
     String[] strdwmcs = null;
     //单位编码数组
     String[] kwdwbms = null;
     //给数组赋值
     if (strdwmc.indexOf(",") != -1) {
     strdwmcs = strdwmc.split("\\,");
     kwdwbms = kwdwbm.split("\\,");
     for (int i = 0; i < strdwmcs.length; i++) {
     dwKeyMap.put(kwdwbms[i], strdwmcs[i]);
     }
     } else {
     strdwmcs = new String[1];
     kwdwbms = new String[1];
     strdwmcs[0] = strdwmc;
     kwdwbms[0] = kwdwbm;
     dwKeyMap.put(kwdwbm, strdwmc);
     }
     //考核类型名称数组
     String[] strkhlxmcs = null;
     //考核类型代码数组
     String[] khlxs = null;
     //给数组赋值
     if (strkhlxmc.indexOf(",") != -1) {
     strkhlxmcs = strkhlxmc.split("\\,");
     khlxs = khlx.split("\\,");
     for (int i = 0; i < strkhlxmcs.length; i++) {
     lxKeyMap.put(khlxs[i], strkhlxmcs[i]);
     }
     } else {
     strkhlxmcs = new String[1];
     khlxs = new String[1];
     strkhlxmcs[0] = strkhlxmc;
     khlxs[0] = khlx;
     lxKeyMap.put(khlx, strkhlxmc);
     }

     //创建工作表
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

     //通过类型进行创建sheet
     for (int i = 0; i < strkhlxmcs.length; i++) {
     //当考核类型为13时，不导出
     if ("13".equals(khlxs[i])) {
     continue;
     }
     sfysj = true;
     //数据赋值开始的number
     int sjNum = 4;
     //装数据的map
     Map<String, Map<Integer, Double>> valueMap = new HashMap<>();
     //sheetName
     String title = starDate + "至" + endDate + "  " + strkhlxmcs[i] + "  汇总对院考核信息";
     // 生成一个(带标题)工作薄
     HSSFSheet sheet = (HSSFSheet) workbook.createSheet(strkhlxmcs[i] + "考核评价情况");

     //标题行
     Row titleRow = sheet.createRow((short) 0);
     //给标题赋值
     titleRow.createCell((short) 0).setCellValue(title);

     HSSFCell titleCell = (HSSFCell) titleRow.getCell((short) 0);
     //get标题加样式
     titleCell.setCellStyle(titleStyle);

     //第二行
     Row twoRow = sheet.createRow((short) 1);
     //第三行
     Row threeContentRow = sheet.createRow((short) 2);
     //第四行
     Row fourContentRow = sheet.createRow((short) 3);

     //横向number
     int oneNum = 0;
     //给第一竖前3个排赋值
     twoRow.createCell((short) oneNum).setCellValue("单位");
     threeContentRow.createCell((short) oneNum).setCellValue("单位");
     fourContentRow.createCell((short) oneNum).setCellValue("单位");
     //合并3个竖排
     sheet.addMergedRegion(new CellRangeAddress((short) 1, (short) 3, (short) oneNum, (short) oneNum));

     //检测map：看是否已经处理过这个类型的数据
     Map<String, String> matchMap = new HashMap<>();
     //考核单位：拿来获取对应数据
     String khdw = "";
     //最后接收的横排数最大值
     int sjNumber = 0;
     //开始对各种类型进行处理
     for (int m = 0; m < mapList.size(); m++) {
     //类型名称
     String lxMc = lxKeyMap.get(mapList.get(m).get("khlx"));
     //给考核单位赋值
     khdw = mapList.get(m).get("khdw").toString();
     //给横向number初始化值
     oneNum = 1;

     //判断是否已经对该类型进行处理，并且，在接收数据中并没有该单位的信息
     if (strkhlxmcs[i].equals(lxMc) && matchMap.containsKey(strkhlxmcs[i]) && !valueMap.containsKey(khdw)) {
     //创建一个map与来接收该单位的值
     valueMap.put(khdw, new HashMap<Integer, Double>());
     //数据源
     JSONArray listMap = JSON.parseArray(mapList.get(m).get("zbkpgl").toString());
     //从数据源获取相应数据
     for (int n = 0; n < listMap.size(); n++) {
     Map<String, Object> stringMap = (Map<String, Object>) listMap.get(n);
     double bmpf = ("".equals(stringMap.get("bmpf")) || stringMap.get("bmpf") == null) ? 0 : Double.parseDouble(stringMap.get("bmpf").toString());
     valueMap.get(khdw).put(oneNum, bmpf);
     oneNum++;
     double agpf = ("".equals(stringMap.get("agpf")) || stringMap.get("agpf") == null) ? 0 : Double.parseDouble(stringMap.get("agpf").toString());
     valueMap.get(khdw).put(oneNum, agpf);
     oneNum++;
     double pjdf = ("".equals(stringMap.get("pjdf")) || stringMap.get("pjdf") == null) ? 0 : Double.parseDouble(stringMap.get("pjdf").toString());
     valueMap.get(khdw).put(oneNum, pjdf);
     oneNum++;
     double xmfz = ("".equals(stringMap.get("xmfz")) || stringMap.get("xmfz") == null) ? 0 : Double.parseDouble(stringMap.get("xmfz").toString());
     valueMap.get(khdw).put(oneNum, xmfz);
     oneNum++;
     }
     //添加总分数
     valueMap.get(khdw).put(oneNum, Double.parseDouble(("".equals(mapList.get(m).get("ywzf")) || mapList.get(m).get("ywzf") == null) ? "0" : mapList.get(m).get("ywzf").toString()));
     //下面为其他数据处理，现在并不知道该数据的处理方式
     //                    oneNum++;
     //                    valueMap.get(khdw).put(oneNum, null);
     //                    oneNum++;
     //                    valueMap.get(khdw).put(oneNum, null);
     }
     //当第一次遇到这个类型时，将对该类型进行处理
     if (strkhlxmcs[i].equals(lxMc) && !matchMap.containsKey(strkhlxmcs[i])) {
     //创建一个map来接收对应数据
     if (!valueMap.containsKey(khdw)) {
     valueMap.put(khdw, new HashMap<Integer, Double>());
     }
     //在检测map中添加该考核类型名称
     matchMap.put(strkhlxmcs[i], "");
     //获取数据源数据
     JSONArray listMap = JSON.parseArray(mapList.get(m).get("zbkpgl").toString());

     //对数据源进行处理
     for (int n = 0; n < listMap.size(); n++) {

     Map<String, Object> stringMap = (Map<String, Object>) listMap.get(n);
     //评分名称
     //                        stringMap.get("khzxm");
     //评分值
     //                        stringMap.get("xmfz");
     //评分标准
     //                        stringMap.get("pfbf");


     String pfbf = stringMap.get("pfbf").toString();
     //横向开始number
     int startNum = oneNum;
     //将评分制度分解成不同列
     //                    if (pfbf.indexOf("\n") != -1) {
     //                        String[] pfbfs = pfbf.split("\\\n");
     //                        for (int k = 0; k < pfbfs.length; k++) {
     //                            twoRow.createCell((short) oneNum).setCellValue(stringMap.get("khzxm") + "（" + stringMap.get("xmfz") + "分）");
     //                            threeContentRow.createCell((short) oneNum).setCellValue(pfbfs[k]);
     //                            threeContentRow.createCell((short) oneNum).setCellValue(pfbfs[k]);
     //                            sheet.addMergedRegion(new CellRangeAddress((short) 2, (short) 2, (short) oneNum, (short) oneNum+1));
     //
     //                            fourContentRow.createCell((short) oneNum).setCellValue("考核人评分");
     //                            String bmpf = ("".equals(stringMap.get("bmpf"))||stringMap.get("bmpf")==null)?"0":stringMap.get("bmpf").toString();
     //                            valueMap.get(khdw).put(oneNum,bmpf);
     //                            oneNum++;
     //                            fourContentRow.createCell((short) oneNum).setCellValue("部门评分");
     //                            String agpf = ("".equals(stringMap.get("agpf"))||stringMap.get("agpf")==null)?"0":stringMap.get("agpf").toString();
     //                            valueMap.get(khdw).put(oneNum,agpf);
     //                            oneNum++;
     //                        }
     //
     //                        twoRow.createCell((short) oneNum).setCellValue(stringMap.get("khzxm") + "（" + stringMap.get("xmfz") + "分）");
     //                        threeContentRow.createCell((short) oneNum).setCellValue("评价分");
     //                        fourContentRow.createCell((short) oneNum).setCellValue("评价分");
     ////                        String bmpf = stringMap.get("agpf")==""?"0":stringMap.get("agpf");
     ////                        sjRow.createCell((short) oneNum).setCellValue(bmpf);
     //                        String pjdf = ("".equals(stringMap.get("pjdf"))||stringMap.get("pjdf")==null)?"0":stringMap.get("pjdf").toString();
     //                        valueMap.get(khdw).put(oneNum,pjdf);
     //                        //合并列  合并列
     //                        sheet.addMergedRegion(new CellRangeAddress((short) 2, (short) 3, (short) oneNum, (short) oneNum));
     //                        oneNum++;
     //
     //                        twoRow.createCell((short) oneNum).setCellValue(stringMap.get("khzxm") + "（" + stringMap.get("xmfz") + "分）");
     //                        threeContentRow.createCell((short) oneNum).setCellValue("最高分");
     //                        fourContentRow.createCell((short) oneNum).setCellValue("最高分");
     //                        String xmfz = ("".equals(stringMap.get("xmfz"))||stringMap.get("xmfz")==null)?"0":stringMap.get("xmfz").toString();
     //                        valueMap.get(khdw).put(oneNum,xmfz);
     //                        //合并列  合并列
     //                        sheet.addMergedRegion(new CellRangeAddress((short) 2, (short) 3, (short) oneNum, (short) oneNum));
     //                        oneNum++;
     //
     //
     //
     //                    } else {
     //给oneNum列赋值3个，并合并
     twoRow.createCell((short) oneNum).setCellValue(stringMap.get("khzxm") + "（" + stringMap.get("xmfz") + "分）");
     threeContentRow.createCell((short) oneNum).setCellValue(pfbf);
     threeContentRow.createCell((short) oneNum + 1).setCellValue(pfbf);
     sheet.addMergedRegion(new CellRangeAddress((short) 2, (short) 2, (short) oneNum, (short) oneNum + 1));

     //给oneNum列赋值
     fourContentRow.createCell((short) oneNum).setCellValue("考核人评分");
     double bmpf = ("".equals(stringMap.get("bmpf")) || stringMap.get("bmpf") == null) ? 0 : Double.parseDouble(stringMap.get("bmpf").toString());

     //将该项数据获取到
     valueMap.get(khdw).put(oneNum, bmpf);
     oneNum++;

     //给oneNum列赋值
     fourContentRow.createCell((short) oneNum).setCellValue("部门评分");
     double agpf = ("".equals(stringMap.get("agpf")) || stringMap.get("agpf") == null) ? 0 : Double.parseDouble(stringMap.get("agpf").toString());

     //将该项数据获取到
     valueMap.get(khdw).put(oneNum, agpf);
     oneNum++;

     //给oneNum列赋值3个，并合并
     twoRow.createCell((short) oneNum).setCellValue(stringMap.get("khzxm") + "（" + stringMap.get("xmfz") + "分）");
     threeContentRow.createCell((short) oneNum).setCellValue("评价分");
     fourContentRow.createCell((short) oneNum).setCellValue("评价分");
     double pjdf = ("".equals(stringMap.get("pjdf")) || stringMap.get("pjdf") == null) ? 0 : Double.parseDouble(stringMap.get("pjdf").toString());

     //将该项数据获取到
     valueMap.get(khdw).put(oneNum, pjdf);
     //合并列
     sheet.addMergedRegion(new CellRangeAddress((short) 2, (short) 3, (short) oneNum, (short) oneNum));
     oneNum++;

     //给oneNum列赋值3个，并合并
     twoRow.createCell((short) oneNum).setCellValue(stringMap.get("khzxm") + "（" + stringMap.get("xmfz") + "分）");
     threeContentRow.createCell((short) oneNum).setCellValue("最高分");
     fourContentRow.createCell((short) oneNum).setCellValue("最高分");
     double xmfz = ("".equals(stringMap.get("xmfz")) || stringMap.get("xmfz") == null) ? 0 : Double.parseDouble(stringMap.get("xmfz").toString());
     //将该项数据获取到
     valueMap.get(khdw).put(oneNum, xmfz);

     //合并列
     sheet.addMergedRegion(new CellRangeAddress((short) 2, (short) 3, (short) oneNum, (short) oneNum));
     oneNum++;

     //                    }
     //合并第二横排
     sheet.addMergedRegion(new CellRangeAddress((short) 1, (short) 1, (short) startNum, (short) oneNum - 1));
     //接收最大横排数
     sjNumber = oneNum;
     //将业务总分数据获取到
     valueMap.get(khdw).put(oneNum, Double.parseDouble(("".equals(mapList.get(m).get("ywzf")) || mapList.get(m).get("ywzf") == null) ? "0" : mapList.get(m).get("ywzf").toString()));

     }

     }
     }

     //给sjNumber列赋值3个，并合并
     twoRow.createCell((short) sjNumber).setCellValue("合计得分");
     threeContentRow.createCell((short) sjNumber).setCellValue("合计得分");
     fourContentRow.createCell((short) sjNumber).setCellValue("合计得分");

     //合并列
     sheet.addMergedRegion(new CellRangeAddress((short) 1, (short) 3, (short) sjNumber, (short) sjNumber));
     sjNumber++;

     //给sjNumber列赋值3个，并合并
     twoRow.createCell((short) sjNumber).setCellValue("评价系数");
     threeContentRow.createCell((short) sjNumber).setCellValue("评价系数");
     fourContentRow.createCell((short) sjNumber).setCellValue("评价系数");
     //合并列
     sheet.addMergedRegion(new CellRangeAddress((short) 1, (short) 3, (short) sjNumber, (short) sjNumber));
     sjNumber++;

     //给sjNumber列赋值3个，并合并
     twoRow.createCell((short) sjNumber).setCellValue("换算成案管工作考评分");
     threeContentRow.createCell((short) sjNumber).setCellValue("换算成案管工作考评分");
     fourContentRow.createCell((short) sjNumber).setCellValue("换算成案管工作考评分");
     //合并列
     sheet.addMergedRegion(new CellRangeAddress((short) 1, (short) 3, (short) sjNumber, (short) sjNumber));

     //合并第一排的标题
     sheet.addMergedRegion(new CellRangeAddress((short) 0, (short) 0, (short) 0, (short) sjNumber));

     //            System.out.println("-------------------------------------------------------------------");

     //            System.out.println(valueMap);
     //            System.out.println(sjNumber);
     //对获取的的数据进行填充
     for (int q = 0; q < kwdwbms.length; q++) {
     Map<Integer, Double> valuesMap = valueMap.get(kwdwbms[q]);
     if (valuesMap == null) {
     continue;
     }
     Row sjRow = sheet.createRow((short) sjNum);
     sjRow.createCell((short) 0).setCellValue(dwKeyMap.get(kwdwbms[q]));
     for (int w = 1; w <= valuesMap.size(); w++) {
     sjRow.createCell((short) w).setCellValue(valuesMap.get(w));
     }
     sjNum++;
     }
     }

     if (sfysj) {
     try {
     //写出Excel；
     workbook.write(os);
     } catch (Exception ex) {
     ex.printStackTrace();
     } finally {
     //关闭流
     if (os != null) {
     os.close();
     }
     //返回信息
     return sfysj;
     }
     }
     return sfysj;

     }

     */

}
