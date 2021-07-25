package com.cll.mode;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author: Cll
 * @Description: TODO
 * @DateTime: 2021/06/21 14:38
 **/
@Getter
@Setter
public class ExcelModeRelation extends BaseRowModel {
    /**
     * Sheet2第三列的数据
     */
    @ExcelProperty(index = 3)
    private String column1;
    /**
     * Sheet2第五列的数据
     */
    @ExcelProperty(index = 5)
    private String column2;

}