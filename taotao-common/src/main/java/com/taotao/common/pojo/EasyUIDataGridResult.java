package com.taotao.common.pojo;

import java.util.List;

/**
 *
 * @author lenovo
 * @date 2018/2/2
 */
public class EasyUIDataGridResult {
    // 总记录数
    private long total;
    // 每页数据
    private List<?> rows;
    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
    public List<?> getRows() {
        return rows;
    }
    public void setRows(List<?> rows) {
        this.rows = rows;
    }
}
