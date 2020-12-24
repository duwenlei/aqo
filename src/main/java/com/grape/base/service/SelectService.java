package com.grape.base.service;

import com.grape.base.query.Page;
import com.grape.base.query.SearchFilter;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.List;

/**
 * @author duwenlei
 */
public interface SelectService<T, ID extends Serializable> {
    /**
     * 根据主键查询
     *
     * @param id 主键
     * @return 查询结果, 无结果时返回{@code null}
     */
    T get(ID id);

    /**
     * 根据多个主键查询
     *
     * @param ids 主键集合
     * @return 查询结果, 如果无结果返回空集合
     */
    List<T> query(Iterable<ID> ids);

    /**
     * 查询所有结果
     *
     * @return 所有结果, 如果无结果则返回空集合
     */
    List<T> queryAll();

    /**
     * 查询所有结果
     *
     * @param page 条件
     * @return
     */
    Page<T> queryPage(Page<T> page);

    /**
     * 根据多个条件查询列表数据
     *
     * @param filters
     * @return
     */
    List<T> queryAll(SearchFilter filters);

    /**
     * 根据多个条件查询列表数据，并排序
     *
     * @param filters
     * @param sort
     * @return
     */
    List<T> queryAll(SearchFilter filters, Sort sort);
}
