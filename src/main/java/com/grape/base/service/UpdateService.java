package com.grape.base.service;

/**
 * @author duwenlei
 **/
public interface UpdateService<T, ID> {
    /**
     * 更新条目
     *
     * @param t 条目
     * @return
     */
    T update(T t);
}
