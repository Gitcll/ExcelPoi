package com.cll.execufun;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: Cll
 * @Description: TODO
 * @DateTime: 2021/06/21 16:05
 **/
public class ExecutionProcess {

    private static final Set<String> fileNameSet = new HashSet<>();
    private static final Map<String, Integer> result = new HashMap<>();
    private static final Set<String> quchongFileName = new HashSet<String>();
    private static final Map<String,Set<String>> dynamicSetMap = new HashMap<>();


    /**
     * 处理分类，计算类的个数
     * @param fileNameSet
     * @param file
     * @return
     */
    public static Map<String, Integer> executionProcess(Set<String> fileNameSet, File file) {


        String[] fileName = null;
        String str = "";
        Map<String, Integer> sortedMap = new TreeMap<String, Integer>((a,b)->{
            if(a.contains("コンポネント")&&b.contains("コンポネント")){
                if(a.contains("その他")){
                    return 1;
                }
            }
            return a.compareTo(b);
        });
        //把分类进行去重处理
        for (String s : fileNameSet) {
            fileName = s.split("--");
            quchongFileName.add(fileName[1]);
        }
        //根据分类的个数，动态创建Map   start
        Map<String, Set<String>> dynamicSetMap = new HashMap<>();
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
        sortedMap.putAll(result);
        result.clear();
        fileNameSet.clear();
        dynamicSetMap.clear();
        quchongFileName.clear();
        quchongSet.clear();
        return sortedMap;
    }

    /**
     * 处理分类，计算类的个数
     * @param fileNameSet
     * @param file
     * @return
     */
    public static Map<String, Integer> ExecutionProcess1(Set<String> fileNameSet,File file) {
        fileNameSet.stream().collect(Collectors.toMap(v->v.replaceAll("^.*--","").trim(), v->{
            Set<String> fullSet = dynamicSetMap.getOrDefault(v.replaceAll("^.*--",""),new HashSet<>());
            fullSet.add(v);
            dynamicSetMap.put(v.replaceAll("^.*--",""),fullSet);
            return fullSet;
        },(a,b)->b));
        dynamicSetMap.forEach((k,v)->{
            System.out.println(file.getName() + "分类的个数====" + k + "---"  + v.size());
            result.put(k,v.size());
        });
        return result;
    }
}