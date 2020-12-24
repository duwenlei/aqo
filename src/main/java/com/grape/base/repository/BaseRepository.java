package com.grape.base.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;

/**
 * @author duwenlei
 **/
@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {
    /**
     * 根据 SQL 进行查询
     *
     * @param sql 本地SQL
     * @return
     */
    List<Object[]> queryObjBySql(String sql);

    /**
     * 根据 SQL 查询，返回相应的 T 集合
     *
     * @param sql 本地SQL
     * @return
     */
    List<T> queryBySql(String sql);

    /**
     * 返回一条数据
     *
     * @param sql 本地SQL
     * @return
     */
    Object getObjBySql(String sql);

    /**
     * 返回一条数据 T
     *
     * @param sql 本地SQL
     * @return
     */
    T get(String sql);

    /**
     * 执行SQL
     *
     * @param sql 本地SQL
     * @return
     */
    int execute(String sql);

    /**
     * 获取 T 的类型
     *
     * @return
     */
    Class<T> getDataClass();
}
