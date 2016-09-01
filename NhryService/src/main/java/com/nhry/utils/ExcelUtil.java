package com.nhry.utils;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;

/**
 * Created by cbz on 8/27/2016.
 */
public class ExcelUtil {
    public static XSSFWorkbook createWorkbook(){
        return new XSSFWorkbook();
    }

    public static XSSFSheet createSheet(XSSFWorkbook workbook,String name){
        XSSFSheet sheet = workbook.createSheet(name);
        sheet.setDefaultColumnWidth((short)8.25);
        sheet.setColumnWidth(0,(short)8 * 100);
        sheet.setColumnWidth(14,(short)8 * 100);
        return sheet;
    }

    public static void addMergedRegion(XSSFSheet sheet,int startRow,int endRow,int startColumn,int endColumn){
        sheet.addMergedRegion(new CellRangeAddress(startRow, endRow, startColumn, endColumn));
    }

    public static XSSFCellStyle getCellStyle1(XSSFWorkbook workbook){
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontName("微软雅黑");
        font.setFontHeightInPoints((short)7);
        cellStyle.setFont(font);
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);//水平居中
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);//垂直居中
        return cellStyle;
    }

    public static XSSFCellStyle getCellStyle2(XSSFWorkbook workbook){
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontName("微软雅黑");
        font.setFontHeightInPoints((short)7);
        cellStyle.setFont(font);
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_LEFT);//水平居中
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_TOP);//垂直居中
        return cellStyle;
    }

    public static XSSFCellStyle getCellStyle3(XSSFWorkbook workbook){
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontName("微软雅黑");
        font.setFontHeightInPoints((short)7);
        cellStyle.setFont(font);
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_LEFT);//水平居左
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);//垂直居中
        return cellStyle;
    }

    public static XSSFCellStyle getCellStyle4(XSSFWorkbook workbook){
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontName("微软雅黑");
        font.setFontHeightInPoints((short)7);
        cellStyle.setFont(font);
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_RIGHT);//水平居右
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);//垂直居中
        return cellStyle;
    }

    public static XSSFRow createRow(XSSFSheet sheet, int rowNum){
        return sheet.createRow(rowNum);
    }

    public static void createCell(XSSFRow row,int cellNum,String value,XSSFCellStyle cellStyle){
        XSSFCell cell = row.createCell(cellNum);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(value);
    }

    public static XSSFCellStyle setBorderStyle(XSSFWorkbook workbook){
        XSSFCellStyle styleBold = workbook.createCellStyle();
        styleBold.setBorderBottom(XSSFCellStyle.BORDER_THIN); //下边框
        styleBold.setBorderLeft(XSSFCellStyle.BORDER_THIN);//左边框
        styleBold.setBorderTop(XSSFCellStyle.BORDER_THIN);//上边框
        styleBold.setBorderRight(XSSFCellStyle.BORDER_THIN);//右边框
        styleBold.setAlignment(XSSFCellStyle.ALIGN_CENTER);//水平居中
        styleBold.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);//垂直居中
        styleBold.setWrapText(true);
        return styleBold;
    }

    public static XSSFCellStyle setFontStype(XSSFWorkbook workbook){
        XSSFCellStyle cellStyle2 = workbook.createCellStyle();
        XSSFFont font2 = workbook.createFont();
        font2.setFontName("微软雅黑");
        font2.setFontHeightInPoints((short) 7);
        cellStyle2.setFont(font2);
        cellStyle2.setAlignment(XSSFCellStyle.ALIGN_CENTER);//水平居中
        cellStyle2.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);//垂直居中
        return cellStyle2;
    }
}