package com.grape.base.service;

/**
 * @author duwenlei
 **/
public interface InsertService<T, ID> {

    /**
     * 保存
     *
     * @param t 条目
     * @return
     */
    T insert(T t);
}
