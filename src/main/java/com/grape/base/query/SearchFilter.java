package com.grape.base.query;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author duwenlei
 **/
public class SearchFilter<T extends Serializable> implements Serializable {
    public enum Operator {
        EQ, LIKE, GT, LT, GET, LTE, IN, IS_NULL, IS_NOT_NULL
    }
    private List<Map<String, Object>> params = new ArrayList<>();

    public List<Map<String, Object>> getParams() {
        return params;
    }
    public SearchFilter() {

    }

    public SearchFilter(String fieldName, Operator operator, Object value) {
        Map<String, Object> map = new HashMap<>(1);
        map.put("fieldName", fieldName);
        map.put("operator", operator);
        map.put("value", value);
        params.add(map);
    }

    /**
     * 添加条件
     *
     * @param fieldName 字段名称，数据库
     * @param operator  操作
     * @param value     值
     * @return 自己
     */
    public SearchFilter add(String fieldName, Operator operator, Object value) {
        Map<String, Object> map = new HashMap<>(1);
        map.put("fieldName", fieldName);
        map.put("operator", operator);
        map.put("value", value);
        params.add(map);
        return this;
    }

    @JsonIgnore
    public Specification<T> getSpecification() {
        return (Specification<T>) (root, query, builder) -> {
            if (params != null && !params.isEmpty()) {
                List<Predicate> predicates = new ArrayList<>();
                for (Map<String, Object> whereMap : params) {
                    String fieldName = (String) whereMap.get("fieldName");
                    Object value = whereMap.get("value");
                    Operator operator = (Operator) whereMap.get("operator");
                    Path path = root.get(fieldName);
                    switch (operator) {
                        case EQ:
                            predicates.add(builder.equal(path, value));
                            break;
                        case GT:
                            predicates.add(builder.greaterThan(path, (Comparable) value));
                            break;
                        case IN:
                            // value instanceof Array
                            if (value instanceof List) {
                                predicates.add(path.in((List) value));
                            }
                            break;
                        case LT:
                            predicates.add(builder.lessThan(path, (Comparable) value));
                            break;
                        case GET:
                            predicates.add(builder.greaterThanOrEqualTo(path, (Comparable) value));
                            break;
                        case LTE:
                            predicates.add(builder.lessThanOrEqualTo(path, (Comparable) value));
                            break;
                        case LIKE:
                            predicates.add(builder.like(path, String.format("%s%", value)));
                            break;
                        case IS_NULL:
                            predicates.add(path.isNull());
                            break;
                        case IS_NOT_NULL:
                            predicates.add(path.isNotNull());
                            break;
                        default:
                            break;
                    }
                }
                if (!predicates.isEmpty()) {
                    return builder.and(predicates.toArray(new Predicate[predicates.size()]));
                }
            }
            return builder.conjunction();
        };
    }
}
