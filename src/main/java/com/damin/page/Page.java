package com.damin.page;

import org.springframework.stereotype.Component;

//分页显示实体
//@Component
public class Page {
    private int page;
    private int rows;
    private int offset;

    public Page() {
    }

    public Page(int page, int rows, int offset) {
        this.page = page;
        this.rows = rows;
        this.offset = offset;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getOffset() {
        return offset = (page - 1)*rows;
    }

    public void setOffset(int offset) {
        this.offset = (page - 1)*rows;
    }
}
