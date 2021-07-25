package com.cll.easyexcel_main;

import com.cll.execufun.ExecutionProcess;
import com.cll.util.CellValueUtil;
import com.cll.util.ErgodicReadFile;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @Author: Cll
 * @Description: TODO
 * @DateTime: 2021/06/22 16:26
 **/
public class PoiExcel<names> {

    private static final Set<String> fileNameSet = new HashSet<>();
    private static final Map<String, Integer> result = new HashMap<>();
    private static final Set<String> quchongFileName = new HashSet<String>();
    private static XSSFWorkbook wb =null;
    private static XSSFSheet sheetR = null;
    private static XSSFSheet sheetW = null;

    public static void main(String[] args) {
        String path = "D:\\Java\\ExcelPoi\\src\\main\\resources";
        readFile(path);
    }

    /**
     * 读取Excel文件
     * @param file
     */
    public static void readExcelPoi(File file) {

        Map<String, Integer> resultPart = null;
        wb = (XSSFWorkbook) ErgodicReadFile.readExcel(file);
        if(wb != null){
            sheetR = wb.getSheetAt(1);
            int maxRow = sheetR.getLastRowNum()+1;
            //遍历一行
            for (int row1 = 5; row1 < maxRow; row1++) {
                //读取一行当中单元格的个数,单元格的个数（0，1，2，3）
                int maxRol = sheetR.getRow(row1).getLastCellNum();
                //遍历一行当中每个单元格，rol = 3是读取D列的
                //for (int rol = 3; rol < maxRol; rol++) {
                //getCell(row1)读取指定行，getCell(3)读取指定单元格，
                XSSFCell cell2 = sheetR.getRow(row1).getCell(3);
                XSSFCell cell3 = sheetR.getRow(row1).getCell(5);
                String fileName2 = CellValueUtil.getCellValue(cell2);;
                String fileName3 = CellValueUtil.getCellValue(cell3);;
                fileNameSet.add(fileName2 + "--" + fileName3);
                //}
            }
            //System.out.println("去重后的个数"+fileNameSet.size());
            //处理逻辑
            resultPart = ExecutionProcess.executionProcess(fileNameSet,file);
            //处理逻辑，逻辑简化
            //resultPart = ExecutionProcess.executionProcess1(fileNameSet,file);
            //将结果写入Excel  sheet“1.共通部品PGM一覧サマリー”
            wrirtExcel(wb,resultPart,file);
            fileNameSet.clear();
            resultPart.clear();
            result.clear();
        }
    }

    /**
     * 将结果写入“1.共通部品PGM一覧サマリー”
     * @param wb
     * @param result
     * @param file
     */
    public static void wrirtExcel(Workbook wb, Map<String, Integer> result, File file) {
        sheetW = (XSSFSheet) wb.getSheetAt(0);
        int maxRowW = sheetW.getLastRowNum();
        int cell1Cell = 0;
        int row1Count = 4;

        //通过数组定义排序
//        for (int i = 0; i <names.length ; i++) {
//            Integer value = result.get(names[i]);
//            if(value!=null){
//                for (int row1 = row1Count; row1 < maxRowW; row1++) {
//                    cell1Cell++;
//                    cell1 = sheetW.getRow(row1).getCell(1);
//                    cell2 = sheetW.getRow(row1).getCell(2);
//                    cell3 = sheetW.getRow(row1).getCell(3);
//
//                    cell1.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
//                    cell1.setCellValue(cell1Cell);
//
//                    cell2.setCellType(XSSFCell.CELL_TYPE_STRING);
//                    cell2.setCellValue(names[i]);
//
//                    cell3.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
//                    cell3.setCellValue(value);
//                    row1Count++;
//                    break;
//                }
//            }
//        }

        for (Map.Entry<String, Integer> stringIntegerEntry : result.entrySet()) {
            for (int row1 = row1Count; row1 < maxRowW; row1++) {
                cell1Cell++;
                XSSFCell cell1 = sheetW.getRow(row1).getCell(1);
                XSSFCell cell2 = sheetW.getRow(row1).getCell(2);
                XSSFCell cell3 = sheetW.getRow(row1).getCell(3);

                cell1.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
                cell1.setCellValue(cell1Cell);

                cell2.setCellType(XSSFCell.CELL_TYPE_STRING);
                cell2.setCellValue(stringIntegerEntry.getKey());

                cell3.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
                cell3.setCellValue(stringIntegerEntry.getValue());
                row1Count++;
                break;
            }

        }
        OutputStream out = null;
        try {
            out = new FileOutputStream(file);
            wb.write(out);
            out.close();
            result.clear();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 遍历文件夹
     * @param path
     */
    public static void readFile(String path) {
        File f = new File(path);
        if (!f.exists()) {
            return;
        }

        if (f.isDirectory()) {
            for (File file : f.listFiles()) {
                //读取Excel
                readExcelPoi(file);
            }
        }
    }

    //    static String[] names = new String[]{
//            "Applogic通信",
//            "CSV操作",
//            "DB処理",
//            "MQ処理",
//            "バッチの呼び出し",
//            "ファイル操作",
//            "コンポネント(イメージ)",
//            "コンポネント(コンボボックス)",
//            "コンポネント(テキスト)",
//            "コンポネント(テーブル)",
//            "コンポネント(プルダウンリスト)",
//            "コンポネント(ボタン)",
//            "コンポネント(ラジオボタン)",
//            "コンポネント(ラベル)",
//            "コンポネント(その他)",
//            "メール関連",
//            "共通ユーティリティ",
//            "帳票処理"};
}