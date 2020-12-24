package com.grape.base.service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author duwenlei
 **/
public interface BaseService<T> {

    /**
     * 对象持久化到DB
     *
     * @param t 对象类型
     * @return T
     */
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    T save(T t);

    /**
     * 更新对象
     *
     * @param t 对象类型
     * @return
     */
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    T update(T t);

    /**
     * 删除
     *
     * @param t 对象类型
     */
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    void delete(List<Integer> t);

    /**
     * 获取
     *
     * @param t 获取
     * @return
     */
    T get(T t);

    /**
     * 字段封装
     *
     * @param field
     * @return
     */
    default String getFiledValue(Object field) {
        Optional<Object> opt = Optional.ofNullable(field);
        return opt.map(s -> s + "%").orElse(null);
    }

    /**
     * 字段封装
     *
     * @param field
     * @return
     */
    default String getFiledStringValue(Object field) {
        Optional<Object> opt = Optional.ofNullable(field);
        return opt.orElse("").toString();
    }
}
