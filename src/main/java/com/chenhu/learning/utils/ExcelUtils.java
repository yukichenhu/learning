package com.chenhu.learning.utils;

import cn.hutool.poi.excel.BigExcelWriter;
import lombok.Synchronized;
public class ExcelUtils {
    private static final String PATH = "/excel/";
    private static final String SUFFIX = ".xlsx";

    // Excel 列宽
    private static final int COLUMN_WIDTH = 20;

    @Synchronized
    public static BigExcelWriter createBigExcelWriter(String fileName , int columnSize){
        BigExcelWriter bigWriter = cn.hutool.poi.excel.ExcelUtil.getBigWriter(PATH + fileName + SUFFIX);
        for (int size = columnSize; size >= 0 ;size--){
            bigWriter.setColumnWidth(size,COLUMN_WIDTH);
        }
        return bigWriter;
    }
}

