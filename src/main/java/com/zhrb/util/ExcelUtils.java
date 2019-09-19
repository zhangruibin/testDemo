package com.zhrb.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author : WuWei
 * @Date : Created on 11:04 2018/3/6
 * @Description: 读取Excel工具类
 * @Version : 1.0
 * @Modified By :
 **/

public class ExcelUtils {
    public static List<Map<String,String>> readFile(MultipartFile file){
        Workbook wb =null;
        Sheet sheet = null;
        Row row = null;
        List<Map<String,String>> list = null;
        String cellData = null;
        wb = readExcel(file);
        if(wb != null){
            //用来存放表中数据
            list = new ArrayList<Map<String,String>>();
            //获取第一个sheet
            sheet = wb.getSheetAt(0);
            //获取最大行数
            int rownum = sheet.getPhysicalNumberOfRows();
            //获取第一行
            row = sheet.getRow(0);
            //获取最大列数
            int colnums = row.getPhysicalNumberOfCells();
            //获取表头
            String tb[] =new String[colnums];
            for (int j=0;j<colnums-1;j++){
                cellData = (String) getCellFormatValue(row.getCell(j));
                tb[j] =cellData;
            }
            for (int i = 1; i<rownum-1; i++) {
                Map<String,String> map = new LinkedHashMap<String,String>();
                row = sheet.getRow(i);
                if(row !=null){
                    for (int j=0;j<colnums-1;j++){
                        cellData = (String) getCellFormatValue(row.getCell(j));
                        map.put(tb[j], cellData);
                    }
                }else{
                    break;
                }
                list.add(map);
            }
        }

        return list;
    }

    //读取excel
    public static Workbook readExcel(MultipartFile file){
        Workbook wb = null;
        if(file.getOriginalFilename()==null){
            return null;
        }
        String extString = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        InputStream is = null;
        try {
            is = new ByteArrayInputStream(file.getBytes());
            if(".xls".equals(extString)){
                return wb = new HSSFWorkbook(is);
            }else if(".xlsx".equals(extString)){
                return wb = new XSSFWorkbook(is);
            }else{
                return wb = null;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wb;
    }

    public static Object getCellFormatValue(Cell cell){
        Object cellValue = null;
        if(cell!=null){
            //判断cell类型
            switch(cell.getCellTypeEnum()){
                case NUMERIC:{
                    cellValue = String.valueOf(cell.getNumericCellValue());
                    break;
                }
                case FORMULA:{
                    //判断cell是否为日期格式
                    if(DateUtil.isCellDateFormatted(cell)){
                        //转换为日期格式YYYY-mm-dd
                        cellValue = cell.getDateCellValue();
                    }else{
                        //数字
                        cellValue = String.valueOf(cell.getNumericCellValue());
                    }
                    break;
                }
                case STRING:{
                    cellValue = cell.getRichStringCellValue().getString();
                    break;
                }
                default:
                    cellValue = "";
            }
        }else{
            cellValue = "";
        }
        return cellValue;
    }
}
