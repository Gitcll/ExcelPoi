package com.cll.easyexcel_main;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.cll.execufun.ExecutionProcess;
import com.cll.listener.ExcelModelListener;
import com.cll.mode.ExcelModeRelation;
import com.cll.mode.ExcelModeSummary;
import com.cll.util.ErgodicReadFile;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

/**
 * @Author: Cll
 * @Description: TODO
 * @DateTime: 2021/06/21 15:29
 **/
public class EasyExcelAsynchronous {

    private static final Set<String> fileNameSet = new HashSet<>();
    private static final Map<String, Integer> result = new HashMap<>();
    private static final Set<String> quchongFileName = new HashSet<String>();


    public static void main(String[] args) {
        String path = "";
        List<File> pathList = ErgodicReadFile.readFile(path);
        for (File file : pathList) {
            readExcute(file);
        }
    }

    /**
     * // 异步读取
     * @param readPath
     */
    public static void readExcute(File readPath) {
        try {
            Sheet sheet = new Sheet(2, 5, ExcelModeRelation.class);
            EasyExcelFactory.readBySax(new FileInputStream(readPath), sheet, new ExcelModelListener(readPath));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Map<String, Integer> excuteExcel(File readPath, List<ExcelModeRelation> excelModeRelationList) {
        Map<String, Integer> resultPart = null;

        for (ExcelModeRelation excelModeRelation : excelModeRelationList) {
            String fileName2 = excelModeRelation.getColumn1();
            String fileName3 = excelModeRelation.getColumn2();
            fileNameSet.add(fileName2 + "--" + fileName3);
        }

        //处理逻辑
        resultPart = ExecutionProcess.executionProcess(fileNameSet,readPath);
        fileNameSet.clear();
        result.clear();
        return resultPart;
    }

    /**
     * 写入数据
     * @param readPath
     * @param resultPart
     */
    public static void writeExcel(File readPath,Map<String, Integer> resultPart) {
        Sheet sheet = new Sheet(1, 4, ExcelModeSummary.class);
        //方法一
        //EasyExcel.write(readPath, ExcelModeSummary.class).sheet(1,"1.サマリー").doWrite(getData(resultPart));


        //方法二
        ExcelWriter excelWriter = EasyExcel.write(readPath, ExcelModeSummary.class).build();
        WriteSheet writeSheet = EasyExcel.writerSheet(1,"1.サマリー").build();
        excelWriter.write(getData(resultPart), writeSheet);
        // 千万别忘记finish 会帮忙关闭流
        excelWriter.finish();
    }

    public static List<ExcelModeSummary> getData(Map<String, Integer> resultPart) {

        List<ExcelModeSummary> excelModeSummaryList = new ArrayList<>();
        for (Map.Entry<String, Integer> stringIntegerEntry : resultPart.entrySet()) {
            ExcelModeSummary excelModeSummary = new ExcelModeSummary();
            excelModeSummary.setColumn1(stringIntegerEntry.getKey());
            excelModeSummary.setColumn2(stringIntegerEntry.getValue());
            excelModeSummaryList.add(excelModeSummary);
        }
        return excelModeSummaryList;
    }
}
