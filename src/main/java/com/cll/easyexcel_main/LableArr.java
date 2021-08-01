package com.cll.easyexcel_main;

import com.cll.util.CellValueUtil;
import com.cll.util.ErgodicReadFile;
import javafx.scene.control.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LableArr {
    static Map<String, String> stringMap = new HashMap<>();

    public static void main(String[] args) {
        String path = "D:\\Java\\ExcelPoi\\src\\main\\resources";
        List<File> listJspPath = ErgodicReadFile.readFile(path, ".jsp");
        List<File> listExcelPath = ErgodicReadFile.readFile(path, ".xlsx");
        for (File file : listJspPath) {
            LableArrFun(file);
        }
        for (File file : listExcelPath) {
            readExcel(file);
        }
    }

    public static void readExcel(File file) {
        XSSFWorkbook wb = null;
        XSSFSheet sheet = null;
        XSSFCell cellRow = null;
        XSSFCell cellColumn = null;
        XSSFCell rowColumn = null;
        wb = (XSSFWorkbook) ErgodicReadFile.readExcel(file);
        if (wb != null) {
            //获取对应的sheet，当前获取第三个sheet(下标从0开始)
            sheet = wb.getSheetAt(2);
            int lastRowNum = sheet.getLastRowNum() + 1;
            for (int row = 4; row < lastRowNum; row++) {
                //获取最大行数
                int maxRol = sheet.getRow(row).getLastCellNum();
                //获取第四行第五列的坐标位置(下标从0开始)
                cellRow = sheet.getRow(row).getCell(5);
                String cellRowStr = CellValueUtil.getCellValue(cellRow);
                for (int column = 6; column < maxRol; column++) {
                    //获取第三行第六列的坐标位置(下标从0开始)
                    cellColumn = sheet.getRow(3).getCell(column);
                    String cellColumnStr = CellValueUtil.getCellValue(cellColumn);
                    //获取对应文件中的内容
                    String cellRow1 = stringMap.get(cellRowStr);
                    //判断文件内容是否包含某些标签
                    if (!cellColumnStr.equals("") && !cellRow1.equals("") && cellRow1.contains(cellColumnStr)) {
                        //获取第四行第六列坐标位置(下标从0开始)
                        rowColumn = sheet.getRow(row).getCell(column);
                        rowColumn.setCellType(XSSFCell.CELL_TYPE_STRING);
                        rowColumn.setCellValue("●");
                    }
                }
            }
            OutputStream out = null;
            try {

                out = new FileOutputStream(file);
                wb.write(out);
                out.close();
                stringMap.clear();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                System.out.println("数据处理完成");
            }
        }
    }

    public static void LableArrFun(File file) {
        try {
            String absolutePath = "";
            absolutePath = file.getAbsolutePath();
            FileReader fileReader = new FileReader(absolutePath);
            String[] absolutePathArr = absolutePath.split("resources", absolutePath.lastIndexOf("resources"));
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuffer stringBuffer = new StringBuffer();
            String str = "";
            while ((str = bufferedReader.readLine()) != null) {
                System.out.println("str = " + str);
                stringBuffer.append(str);
            }
            stringMap.put(absolutePath, stringBuffer.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static String[] pathName = {
            "a",
            "b",
            "c",
            "d",
    };
}
