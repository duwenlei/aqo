package com.grape.base.repository;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.List;

/**
 * @author duwenlei
 **/
public class BaseRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements BaseRepository<T, ID> {
    private final EntityManager entityManager;
    private Class<T> clazz;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public BaseRepositoryImpl(JpaEntityInformation<T, ID> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
        this.clazz = entityInformation.getJavaType();
    }

    @Override
    public List<Object[]> queryObjBySql(String sql) {
        Query query = entityManager.createNativeQuery(sql);
        return query.getResultList();
    }

    @Override
    public List<T> queryBySql(String sql) {
        return entityManager.createNativeQuery(sql, clazz).getResultList();
    }

    @Override
    public Object getObjBySql(String sql) {
        List resultList = entityManager.createNativeQuery(sql).getResultList();
        if (resultList.isEmpty()) {
            return null;
        }
        return resultList.get(0);
    }

    @Override
    public T get(String sql) {
        List<T> resultList = entityManager.createNativeQuery(sql, clazz).getResultList();
        if (resultList.isEmpty()) {
            return null;
        }
        return resultList.get(0);
    }

    @Override
    public int execute(String sql) {
        return entityManager.createNativeQuery(sql).executeUpdate();
    }

    @Override
    public Class<T> getDataClass() {
        return clazz;
    }
}
