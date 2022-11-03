package de.dwienzek.spring.entityinterfacerepositories;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.repository.core.RepositoryMetadata;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class InterfaceBasedJpaRepositoryFactory extends JpaRepositoryFactory {

    private final Map<Class<?>, Class<?>> interfaceToEntityClassMap;
    private final EntityManager entityManager;

    InterfaceBasedJpaRepositoryFactory(EntityManager entityManager) {
        super(entityManager);

        this.entityManager = entityManager;
        this.interfaceToEntityClassMap = entityManager.getMetamodel().getEntities()
                .stream()
                .flatMap(this::collectInterfacesFromEntityType)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (possibleDuplicateInterface, entity) -> entity));
    }

    private Stream<AbstractMap.SimpleImmutableEntry<Class<?>, Class<?>>> collectInterfacesFromEntityType(
            EntityType<?> entityType) {
        return Arrays.stream(entityType.getJavaType().getInterfaces())
                .map(interfaceType -> new AbstractMap.SimpleImmutableEntry<>(interfaceType, entityType.getJavaType()));
    }

    @Override
    public <T, I> JpaEntityInformation<T, I> getEntityInformation(Class<T> domainClass) {
        if (domainClass.isInterface()) {
            Class<?> entityClass = this.interfaceToEntityClassMap.get(domainClass);

            if (entityClass == null) {
                throw new IllegalStateException(String.format("Entity class for interface '%s' not found.", domainClass));
            }

            //noinspection unchecked
            return (JpaEntityInformation<T, I>) JpaEntityInformationSupport.getEntityInformation(entityClass, entityManager);
        } else {
            return super.getEntityInformation(domainClass);
        }
    }

    @Override
    protected RepositoryMetadata getRepositoryMetadata(Class<?> repositoryInterface) {
        RepositoryMetadata originalMetadata = super.getRepositoryMetadata(repositoryInterface);
        return new InterfaceBasedRepositoryMetadata(originalMetadata, interfaceToEntityClassMap.get(originalMetadata.getDomainType()));
    }

}