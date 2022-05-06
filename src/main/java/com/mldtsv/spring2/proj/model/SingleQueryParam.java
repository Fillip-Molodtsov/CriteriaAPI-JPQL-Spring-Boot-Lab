package com.mldtsv.spring2.proj.model;

import lombok.*;

@Getter
@Setter
public class SingleQueryParam extends QueryParam{
    private Object value;

    public SingleQueryParam(BookField bookField, Object value) {
        super(bookField);
        this.value = value;
    }
}
