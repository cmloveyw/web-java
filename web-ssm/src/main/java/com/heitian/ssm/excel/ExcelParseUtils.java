package com.heitian.ssm.excel;


import com.heitian.ssm.exceptions.PlatformException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类描述：execl导出公共功能
 *
 * @author 杨建 yang.jian@mobcb.com
 * @fileName ExcelExportUtils.java
 * @data 2016年2月19日 下午2:57:19
 */
public class ExcelParseUtils {

    private static Log logger = LogFactory.getLog(ExcelParseUtils.class);

    public static List<Map<String, String>> parseCommonExcel(InputStream is) {
        XSSFWorkbook workBook = null;

        try {
            workBook = new XSSFWorkbook(is);
            XSSFSheet sheet = workBook.getSheetAt(0);
            List<Map<String, String>> list = new ArrayList<Map<String, String>>();
            int rowNum = sheet.getPhysicalNumberOfRows();
            XSSFRow row = null;
            XSSFCell cell = null;
            int cellNum = 0;

            for (int i = 0; i < rowNum; i++) {
                row = sheet.getRow(i);
                if (null != row) {
                    cellNum = row.getLastCellNum();//row.getPhysicalNumberOfCells();

                    Map<String, String> rowData = new HashMap<String, String>();
                    for (int k = 0; k < cellNum; k++) {
                        cell = row.getCell(k);
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        /*if (null == cell) {
                            rowData = null;
                            break;
                        }*/

                        String value = "";
                        if (cell != null) {
                            value = cell.getStringCellValue();
                        }
                        rowData.put("" + k, value);
                    }
                    if (null != rowData) {
                        list.add(rowData);
                    }
                } else {
                    break;
                }
            }
            return list;
        } catch (IOException e) {
            throw new PlatformException("文件读取错误", e);
        } catch (IllegalStateException e) {
            throw new PlatformException("请将所有单元格的格式转换成文本", e);
        }
    }

    public static List<Map<String, String>> parseCommonExcel(File file) {
        InputStream is;
        try {
            is = new FileInputStream(file);
            return ExcelParseUtils.parseCommonExcel(is);
        } catch (FileNotFoundException e) {
            throw new PlatformException("文件不存在", e);
        }

    }

//    public static void main(String[] args) {
//
//        File f = new File("f://excel.xlsx");
//
//        List<Map<String, String>> data;
//
//        try {
//
//            data = parseCommonExcel(f);
//            System.out.println(JsonUtils.toJson(data));
//
//            if (null != data && data.size() > 0) {
//                for (int i = 1; i < data.size(); i++) {
//                    Map<String, String> map = data.get(i);
//
//                }
//            } else {
//                // 未解析到数据
//            }
//        } catch (InvalidFormatException e) {
//            e.printStackTrace(); // 文件格式错误
//        } catch (IllegalStateException e) {
//            e.printStackTrace(); // 请将所有单元格的格式转换成字符串
//        }
//        catch (IOException e) {
//            e.printStackTrace(); // 文件读取异常
//        }
//
//    }
}
