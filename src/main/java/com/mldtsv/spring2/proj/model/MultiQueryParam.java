package com.mldtsv.spring2.proj.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
public class MultiQueryParam extends QueryParam{
    private Object[] values;

    public MultiQueryParam(BookField bookField, Object[] values) {
        super(bookField);
        this.values = values;
    }
}
