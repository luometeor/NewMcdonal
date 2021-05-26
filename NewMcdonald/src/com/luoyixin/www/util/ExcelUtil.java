package com.luoyixin.www.util;

import org.apache.poi.hssf.usermodel.*;

/**
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.util
 * @ClassName: ExcelUtil
 * @create 2021/5/22-23:29
 * @Version: 1.0
 */
public class ExcelUtil {
    /**
     *  导出Excel
     * @param sheetName sheet名称
     * @param title 标题
     * @param values 内容
     * @return
     */
    public static HSSFWorkbook getHssfWorkbook(String sheetName, String []title, String [][]values) {
        // 第一步，创建一个HSSFWorkbook对应一个Excel文件
        HSSFWorkbook excel = new HSSFWorkbook();

        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = excel.createSheet(sheetName);

        // 第三步，在sheet中添加表头第0行
        HSSFRow row = sheet.createRow(0);

        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = excel.createCellStyle();
        // 创建一个居中格式
        style.setAlignment(style.getAlignment());

        //声明列对象
        HSSFCell cell = null;

        //创建标题
        for(int i=0;i<title.length;i++){
            cell = row.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(style);
        }

        //创建内容
        if(values != null && values.length != 0) {
            for(int i = 0 ; i < values.length ; i++){
                row = sheet.createRow(i + 1);
                for(int j = 0 ; j < values[i].length ; j++){
                    //将内容按顺序赋给对应的列对象
                    row.createCell(j).setCellValue(values[i][j]);
                }
            }
        }

        return excel;
    }
}
