package com.mldtsv.spring2.proj.model;

import lombok.Data;

@Data
public class QueryParam {
    protected BookField bookField;

    public QueryParam(BookField bookField) {
        this.bookField = bookField;
    }
}
