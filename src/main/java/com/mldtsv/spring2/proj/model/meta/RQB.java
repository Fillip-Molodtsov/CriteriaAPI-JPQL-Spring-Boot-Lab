package com.mldtsv.spring2.proj.model.meta;


import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Getter
@AllArgsConstructor
public class RQB<T> {
    private final Root<T> r;
    private final CriteriaQuery<?> q;
    private final CriteriaBuilder cb;
}
