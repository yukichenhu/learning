package com.chenhu.learning.utils;

import cn.hutool.poi.excel.BigExcelWriter;
import cn.hutool.poi.excel.sax.Excel07SaxReader;
import lombok.Synchronized;
import org.springframework.util.ObjectUtils;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public static void main(String[] args) {
        List<String> columns=new ArrayList<>();
        String excelPath = "http://192.168.0.158:9000/cdp/crowd/8b7eb04f07b74ccc86b707151cd4fa07.xlsx";
        try (InputStream is = new URL(excelPath).openStream()) {
            Excel07SaxReader reader = new Excel07SaxReader((sheet, rowIdx, list) -> {
                if (rowIdx == 0) {
                    System.out.println(list);
                    columns.addAll(list.stream().filter(e->!ObjectUtils.isEmpty(e)).map(Object::toString).collect(Collectors.toList()));
                } else {
                    throw new RuntimeException("读取终止");
                }
            });
            reader.read(is, 0);
        } catch (RuntimeException be) {
            System.out.println(columns);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

