package com.grape;

import com.grape.base.repository.BaseRepositoryFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author duwenlei
 */
@SpringBootApplication
@EnableJpaRepositories(repositoryFactoryBeanClass = BaseRepositoryFactoryBean.class)
public class AqoApplication {

    public static void main(String[] args) {
        SpringApplication.run(AqoApplication.class, args);
    }

}
