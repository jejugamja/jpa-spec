package com.github.jejugamja.jpaspec;

import org.springframework.data.domain.Pageable;

public interface PageSpecificationable<T> extends Specificationable<T> {

    Pageable pageable();
}
