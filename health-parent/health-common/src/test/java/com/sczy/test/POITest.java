package com.sczy.test;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.*;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class POITest {
    @Test
    public void test1() throws Exception {
        XSSFWorkbook excle = new XSSFWorkbook(new FileInputStream("F:\\java 资料\\传智健康\\项目资料\\day05\\素材\\预约设置模板文件\\hello.xlsx"));
        //读取Excel第一个sheet
        XSSFSheet sheetAt = excle.getSheetAt(0);
        //遍历sheet标签页，获得每一行数据
        for (Row row : sheetAt) {
            //遍历行，获得每个单元格
            for (Cell cell : row) {
                System.out.println(cell.getStringCellValue());
            }
        }
        excle.close();

    }


    @Test
    public void test2() throws Exception {
        XSSFWorkbook excle = new XSSFWorkbook(new FileInputStream("F:\\java 资料\\传智健康\\项目资料\\day05\\素材\\预约设置模板文件\\hello.xlsx"));
        //读取Excel第一个sheet
        XSSFSheet sheetAt = excle.getSheetAt(0);
        //获取最后的行号
        int lastRowNum = sheetAt.getLastRowNum();
        for (int i = 0;i<=lastRowNum;i++){//遍历所有的行
            XSSFRow row = sheetAt.getRow(i);//获取当前行
            short lastCellNum = row.getLastCellNum();//获取当前行最后一个索引
            for (int j=0;j<lastCellNum;j++){
                XSSFCell cell = row.getCell(j);//获取每个单元格
                System.out.println(cell.getStringCellValue());
            }
        }
        excle.close();

    }

    @Test
    public void test3() throws Exception{
        //首先创建一个工作蒲
        XSSFWorkbook excle = new XSSFWorkbook();
        //创建一个工作表对象
        XSSFSheet sheet = excle.createSheet("first");
        //在工作表创建行对象
        XSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("name");
        row.createCell(1).setCellValue("age");
        row.createCell(2).setCellValue("sex");
        XSSFRow row1 = sheet.createRow(1);
        row1.createCell(0).setCellValue("zhangsan");
        row1.createCell(1).setCellValue("22");
        row1.createCell(2).setCellValue("nan");
        //通过输出流写到磁盘
        FileOutputStream out = new FileOutputStream(new File("E:\\hello.xlsx"));
        excle.write(out);
        out.flush();
        excle.close();
        out.close();

    }
}
