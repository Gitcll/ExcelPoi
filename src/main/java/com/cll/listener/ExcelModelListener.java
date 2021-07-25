package com.cll.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.cll.easyexcel_main.EasyExcelAsynchronous;
import com.cll.mode.ExcelModeRelation;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @Author: Cll
 * @Description: TODO 监听器
 * @DateTime: 2021/06/21 15:19
 **/
public class ExcelModelListener extends AnalysisEventListener<ExcelModeRelation> {

    private File readPath;
    //每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
    private static final int BATCH_COUNT = 10000;
    List<ExcelModeRelation> lists = new ArrayList<>();
    Map<String, Integer> resultPart = new TreeMap<>();


    public ExcelModelListener() {
    }

    public ExcelModelListener(File readPath) {
        this.readPath = readPath;
    }

    public List<ExcelModeRelation> getLists() {
        return lists;
    }

    @Override
    public void invoke(ExcelModeRelation data, AnalysisContext context) {
        //System.out.println("解析第一条数据data = " + data.toString());
        lists.add(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        resultPart= EasyExcelAsynchronous.excuteExcel(readPath,lists);
        EasyExcelAsynchronous.writeExcel(readPath,resultPart);
        resultPart.clear();
        System.out.println("所以数据解析完成");
    }

}