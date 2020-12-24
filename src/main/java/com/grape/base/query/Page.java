package com.grape.base.query;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 分页
 *
 * @author duwenlei
 **/
@Getter
@Setter
public class Page<T> implements Serializable {
    /**
     * 游标
     */
    private int offset;

    /**
     * 条数
     */
    private int limit;

    /**
     * 总条数
     */
    private int total;

    /**
     * 总页数
     */
    private int pages = 1;

    /**
     * 当前页数
     */
    private int currentNum = 1;

    /**
     * 每页显示条数，默认显示 20 条
     */
    private int pageSize = 20;

    /**
     * 是否查询总条数
     */
    private boolean searchTotalCount = true;

    /**
     * 是否排序
     */
    private boolean openSort = true;

    /**
     * SQL 排序 ASC 集合
     */
    private List<String> ascs;

    /**
     * SQL 排序 DESC 集合
     */
    private List<String> descs;

    /**
     * 是否为升序排序
     *
     * <code>true</code> 是
     * <code>false</code> 否
     */
    private boolean isAsc = true;

    /**
     * SQL order by 字段
     * <p>
     * 例如 id desc
     */
    private String orderByField;

    /**
     * 数据列表
     */
    private List<T> records = Collections.emptyList();

    /**
     * 条件
     */
    private Map<String, Object> condition;

    private SearchFilter filters;

    public int getPages() {
        if (total > 0 && pageSize > 0) {
            pages = total / pageSize;
            if (total % pageSize != 0) {
                pages++;
            }
        }
        return pages;
    }

    public Page() {
    }

    public Page(int currentNum, int pageSize) {
        this.currentNum = currentNum;
        this.pageSize = pageSize;
    }

    public Page<T> setOffset(int offset) {
        this.offset = offset;
        return this;
    }

    public Page<T> setAsc(boolean isAsc) {
        this.isAsc = isAsc;
        return this;
    }

    public Page<T> setAscs(List<String> ascs) {
        this.ascs = ascs;
        return this;
    }

    public Page<T> setDescs(List<String> descs) {
        this.descs = descs;
        return this;
    }

    public Page<T> setOrderByField(String orderByField) {
        this.orderByField = orderByField;
        return this;
    }

    /**
     * 赋值条件
     *
     * @param condition 条件
     * @return
     */
    public Page<T> setCondition(Map<String, Object> condition) {
        this.condition = condition;
        return this;
    }

    public Page<T> addCondition(String fieldName, Object value) {
        if (Objects.isNull(this.condition)) {
            this.condition = new HashMap<>(1);
        }
        this.condition.put(fieldName, value);
        return this;
    }

    public Page<T> setFilters(SearchFilter filters) {
        this.filters = filters;
        return this;
    }

    public Page<T> addFilter(String fieldName, SearchFilter.Operator operator, Object value) {
        if (filters == null) {
            filters = new SearchFilter();
        }
        filters.add(fieldName, operator, value);
        return this;
    }


}
