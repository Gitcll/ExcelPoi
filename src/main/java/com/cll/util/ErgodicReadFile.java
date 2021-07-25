package com.cll.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Cll
 * @Description: TODO
 * @DateTime: 2021/06/18 14:47
 **/
public class ErgodicReadFile {
    /**
     * 读取文件
     * @param path
     * return bufferedInputStream
     */
    public static BufferedInputStream readFileInputStream(String path) {
        try {

            File f = new File(path);
            if (!f.exists()) {
                return null;
            }

            if (f.isDirectory()) {
                for (File file : f.listFiles()) {
                    InputStream in = new FileInputStream(file);
                    BufferedInputStream bufferedInputStream = new BufferedInputStream(in);
                    return bufferedInputStream;
                }
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
        return null;
    }

    /**
     * 读取文件
     * @param path
     * return file
     */
    public static List<File> readFile(String path) {
        try {
            List<File> fileList = new ArrayList<>();
            File f = new File(path);
            if (!f.exists()) {
                return null;
            }

            if (f.isDirectory()) {
                for (File file : f.listFiles()) {
                    fileList.add(file);
                }
                return fileList;
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
        return null;
    }
    /**
     * 获取指定后缀名的文件
     * @param path
     * return file
     */
    public static List<File> readFile(String path,String suffix) {
        try {
            List<File> fileList = new ArrayList<>();
            File f = new File(path);
            if (!f.exists()) {
                return null;
            }

            if (f.isDirectory()) {
                for (File file : f.listFiles()) {
                    String absolutePath = file.getAbsolutePath();
                    absolutePath = absolutePath.substring(absolutePath.lastIndexOf("."));
                    if (absolutePath.equals(suffix)) {
                        fileList.add(file);
                    }
                }
                return fileList;
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
        return null;
    }


    /**
     * 读取excel,区分Excel版本格式
     * @param filePath
     * @return
     */
    public static Workbook readExcel(File filePath){
        Workbook wb = null;
        if(filePath==null){
            return null;
        }
        String path = filePath.getAbsolutePath();
        String extString = path.substring(path.lastIndexOf("."));
        InputStream is = null;
        try {
            is = new FileInputStream(path);
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
}