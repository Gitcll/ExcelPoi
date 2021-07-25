package com.cll.easyexcel_main;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.metadata.Sheet;
import com.cll.mode.ExcelModeRelation;
import com.cll.util.ErgodicReadFile;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

/**
 * @Author: Cll
 * @Description: TODO
 * @DateTime: 2021/06/18 13:57
 **/
public class EasyExcelSynchronize {

    private static final Set<String> fileNameSet = new HashSet<>();
    private static final Map<String, Integer> result = new HashMap<>();
    private static final Set<String> quchongFileName = new HashSet<String>();

    public static void main(String[] args) throws Exception {
        String path = "";
        // 简单读取 (同步读取)
        List<File> pathList = ErgodicReadFile.readFile(path);
        for (File file : pathList) {
            readExcute(file);
        }
    }

    /**
     * 简单读取 (同步读取)
     * @param readPath
     */
    public static void readExcute(File readPath) {

        try {
            Map<String, Integer> resultPart = null;
            Sheet sheet = new Sheet(2, 5, ExcelModeRelation.class);
            List<Object> readList = EasyExcelFactory.read(new FileInputStream(readPath), sheet);

            //存入Mode实体类中
            List<ExcelModeRelation> lists = new ArrayList<>();
            for (Object readListO : readList) {
                lists.add((ExcelModeRelation)readListO);
            }

            for (ExcelModeRelation excelModeRelation : lists) {
                String fileName2 = excelModeRelation.getColumn1();
                String fileName3 = excelModeRelation.getColumn2();
                fileNameSet.add(fileName2 + "--" + fileName3);
            }
            //处理逻辑
            resultPart = ExecutionProcess(fileNameSet,readPath);
            fileNameSet.clear();
            result.clear();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理分类，计算类的个数
     * @param fileNameSet
     * @param file
     * @return
     */
    public static Map<String, Integer> ExecutionProcess(Set<String> fileNameSet,File file) {


        String[] fileName = null;
        String str = "";
        //把分类进行去重处理
        for (String s : fileNameSet) {
            fileName = s.split("--");
            quchongFileName.add(fileName[1]);
        }
        //根据分类的个数，动态创建Map   start
        Map<String,Set<String>> dynamicSetMap = new HashMap<>();
        for (String s : fileNameSet) {
            dynamicSetMap.put(s.replaceAll("^.*--","").trim() ,new HashSet<>());
        }
        //根据分类的个数，动态创建Map   end

        //遍历分类的个数
        for (int i = 0; i < quchongFileName.size(); i++) {
            //遍历全部的结果集
            for (String s : fileNameSet) {
                str = s.replaceAll("^.*--","").trim();
                //通过读取当前的??,?取Map中?中,然后把当前s存在Set集合
                dynamicSetMap.get(str).add(s);
            }
        }

        String[] fileName1 = null;
        Set<String> quchongSet = null;
        for (Set<String> strings : dynamicSetMap.values()) {
            if(strings.size() > 0){
                quchongSet = new HashSet<>();
                for (String string : strings) {
                    fileName1 = string.split("--");
                    quchongSet.add(fileName1[0]);
                }
                System.out.println(file.getName() + "分类的个数====" + fileName1[1] + "---"  + quchongSet.size());
                result.put(fileName1[1],Integer.valueOf(quchongSet.size()));
            }
        }
        quchongFileName.clear();
        quchongSet.clear();
        return result;
    }

}
