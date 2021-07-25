package com.cll.mode;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author: Cll
 * @Description: TODO
 * @DateTime: 2021/06/21 14:38
 **/
@Getter
@Setter
@ToString
public class ExcelModeSummary extends BaseRowModel {
    /**
     * Sheet1第二列的数据
     */
    @ExcelProperty(index = 2)
    private String column1;
    /**
     * Sheet1第三列的数据
     */
    @ExcelProperty(index = 3)
    private Integer column2;

}
