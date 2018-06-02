package com.github.jejugamja.jpaspec;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.util.Optional;

import static com.github.jejugamja.jpaspec.Specificationable.PathUtil.get;

public interface
Specificationable<T> extends Specification<T> {

    Specification<T> specification();

    @Override
    default Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return  specification().toPredicate(root, query, criteriaBuilder);
    }

    default Specification<T> where(Specification<T> specification) {
        return Specification.where(specification);
    }

    default <V> Specification<T> eq(String column, V value) {
        return (root, q, builder) -> builder.equal(get(root, column), value);
    }

    default <V> Specification<T> eq(String column, Optional<V> optional) {
        return optional.map(v -> eq(column, v))
                .orElseGet(EmptySpecification::new);
    }

    default <V> Specification<T> ne(String column, V value) {
        return (root, q, builder) -> builder.notEqual(get(root, column), value);
    }

    default <V> Specification<T> ne(String column, Optional<V> optional) {
        return optional.map(v -> ne(column, v))
                .orElseGet(EmptySpecification::new);
    }

    default <V extends Comparable<? super V>> Specification<T> gt(String column, V value) {
        return (root, q, builder) -> builder.greaterThan(get(root, column), value);
    }

    default <V extends Comparable<? super V>> Specification<T> gt(String column, Optional<V> optional) {
        return optional.map(v -> gt(column, v))
                .orElseGet(EmptySpecification::new);
    }

    default <V extends Comparable<? super V>> Specification<T> gte(String column, V value) {
        return (root, q, builder) -> builder.greaterThanOrEqualTo(get(root, column), value);
    }

    default <V extends Comparable<? super V>> Specification<T> gte(String column, Optional<V> optional) {
        return optional.map(v -> gte(column, v))
                .orElseGet(EmptySpecification::new);
    }

    default <V extends Comparable<? super V>> Specification<T> lt(String column, V value) {
        return (root, q, builder) -> builder.lessThan(get(root, column), value);
    }

    default <V extends Comparable<? super V>> Specification<T> lt(String column, Optional<V> optional) {
        return optional.map(v -> lt(column, v))
                .orElseGet(EmptySpecification::new);
    }

    default <V extends Comparable<? super V>> Specification<T> lte(String column, V value) {
        return (root, q, builder) -> builder.lessThanOrEqualTo(get(root, column), value);
    }

    default <V extends Comparable<? super V>> Specification<T> lte(String column, Optional<V> optional) {
        return optional.map(v -> lte(column, v))
                .orElseGet(EmptySpecification::new);
    }

    default <V extends Comparable<? super V>> Specification<T> isNull(String column) {
        return (root, q, builder) -> builder.isNull(get(root, column));
    }

    default <V extends Comparable<? super V>> Specification<T> isNotNull(String column) {
        return (root, q, builder) -> builder.isNull(get(root, column).isNotNull());
    }

    default <V extends Comparable<? super V>> Specification<T> isTrue(String column) {
        return (root, q, builder) -> builder.isTrue(get(root, column));
    }

    default <V extends Comparable<? super V>> Specification<T> isFalse(String column) {
        return (root, q, builder) -> builder.isFalse(get(root, column));
    }

    default <V extends Comparable<? super V>> Specification<T> like(String column, String pattern) {
        return (root, q, builder) -> builder.like(get(root, column), pattern);
    }

    default <V extends Comparable<? super V>> Specification<T> like(String column, Optional<String> optional) {
        return optional
                .filter(StringUtils::hasText)
                .map(v -> like(column, v))
                .orElseGet(EmptySpecification::new);
    }

    default <V extends Comparable<? super V>> Specification<T> containing(String column, String pattern) {
        return like(column, "%" + pattern + "%");
    }

    default <V extends Comparable<? super V>> Specification<T> containing(String column, Optional<String> optional) {
        return optional
                .filter(StringUtils::hasText)
                .map(v -> containing(column, v))
                .orElseGet(EmptySpecification::new);
    }

    class PathUtil {
        private PathUtil() { }

        static <Y> Expression<Y> get(Root root, String column) {
            Path e = root;
            for(String s : column.split("[.]")) {
                e = e.get(s);
            }
            return e;
        }
    }

    class EmptySpecification implements Specification {
        @Override
        public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder builder) {
            return builder.conjunction();
        }
    }
}
