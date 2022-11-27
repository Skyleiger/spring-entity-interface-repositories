package de.dwienzek.spring.entityinterfacerepositories;

import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

import jakarta.persistence.EntityManager;

public class InterfaceBasedJpaRepositoryFactoryBean<T extends Repository<S, I>, S, I> extends JpaRepositoryFactoryBean<T, S, I> {

    public InterfaceBasedJpaRepositoryFactoryBean(Class<? extends T> repositoryInterface) {
        super(repositoryInterface);
    }

    @Override
    protected RepositoryFactorySupport createRepositoryFactory(EntityManager entityManager) {
        return new InterfaceBasedJpaRepositoryFactory(entityManager);
    }

}