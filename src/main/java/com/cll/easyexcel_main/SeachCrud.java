package com.cll.easyexcel_main;

import com.alibaba.druid.stat.TableStat;
import com.cll.util.ErgodicReadFile;
import com.cll.util.SqlParserUtil;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.util.TablesNamesFinder;

import java.io.*;
import java.util.*;

public class SeachCrud {
    static Set<String> stringSet = new HashSet<>();
    static Set<String> stringSetNew = new HashSet<>();
    static List<String> stringList = new ArrayList<>();
    static List<String> sqlList = new ArrayList<>();
    static String[] beforeString = {"<select", "<delete", "<update", "<insert"};
    static String[] afterString = {"</select>", "</delete>", "</update>", "</insert>"};
    static String[] subCurd = {"from"};

    public static void main(String[] args) {

        String path = "C:\\Users\\30270\\Desktop\\新建文件夹";
        List<File> listJspPath = ErgodicReadFile.readFile(path, ".xml");
        for (File file : listJspPath) {
            seachCrudFun(file);
        }
    }

    public static void seachCrudFun(File file) {
        try {
            String absolutePath = "";
            absolutePath = file.getAbsolutePath();
            FileReader fileReader = new FileReader(absolutePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuffer stringBuffer = new StringBuffer();
            String str = "";
            while ((str = bufferedReader.readLine()) != null) {
                System.out.println("str = " + str);
                if (str.contains(beforeString[0]) || str.contains(beforeString[1]) ||
                        str.contains(beforeString[2]) || str.contains(beforeString[3])) {
                    continue;
                }
                if (str.contains(afterString[0]) || str.contains(afterString[1]) ||
                        str.contains(afterString[2]) || str.contains(afterString[3])) {
                    stringList.add(stringBuffer.toString());
                    stringBuffer = new StringBuffer();
                    continue;
                }
                stringBuffer.append(str);
            }

            for (String crudSqlList : stringList) {
                if(!crudSqlList.equals("")) {
                    //select处理
                    crudSqlList = crudSqlList.replaceAll("\\s+", " ").replace("<![CDATA[", "").replace("]]>", "");
                    Map<TableStat.Name, TableStat> tableStatMap = SqlParserUtil.getFromTo(crudSqlList);
                    for (TableStat.Name name : tableStatMap.keySet()) {
                        System.out.println("table = " + name);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}