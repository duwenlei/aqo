package com.grape.base.service;

import java.io.Serializable;

/**
 * @author duwenlei
 **/
public interface DeleteService<T, ID extends Serializable> {

    /**
     * 根据 id 删除
     *
     * @param id 标识
     */
    void delete(ID id);

    /**
     * 根据 id 删除
     *
     * @param ids id 集合
     */
    void delete(Iterable<ID> ids);

    /**
     * 清空表
     */
    void clear();
}
