package com.grape.base.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import java.io.Serializable;

/**
 * @author duwenlei
 **/
public class BaseRepositoryFactoryBean<R extends JpaRepository<T, ID>, T, ID extends Serializable> extends JpaRepositoryFactoryBean<R, T, ID> {

    /**
     * Creates a new {@link JpaRepositoryFactoryBean} for the given repository interface.
     *
     * @param repositoryInterface must not be {@literal null}.
     */
    public BaseRepositoryFactoryBean(Class<? extends R> repositoryInterface) {
        super(repositoryInterface);
    }

    @Override
    protected RepositoryFactorySupport createRepositoryFactory(EntityManager entityManager) {
        return new BaseRepositoryFactory(entityManager);
    }

    private static class BaseRepositoryFactory<T, ID extends Serializable> extends JpaRepositoryFactory {
        private final EntityManager entityManager;

        /**
         * Creates a new {@link JpaRepositoryFactory}.
         *
         * @param entityManager must not be {@literal null}
         */
        public BaseRepositoryFactory(EntityManager entityManager) {
            super(entityManager);
            this.entityManager = entityManager;
        }

        @Override
        protected JpaRepositoryImplementation<?, ?> getTargetRepository(RepositoryInformation information, EntityManager entityManager) {
            JpaEntityInformation<?, Object> entityInformation = this.getEntityInformation(information.getDomainType());
            Object targetRepositoryViaReflection = this.getTargetRepositoryViaReflection(information, new Object[]{entityInformation, entityManager});
            Assert.isInstanceOf(BaseRepositoryImpl.class, targetRepositoryViaReflection);
            return (JpaRepositoryImplementation<?, ?>) targetRepositoryViaReflection;
        }

        @Override
        protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
            return BaseRepositoryImpl.class;
        }
    }


}
