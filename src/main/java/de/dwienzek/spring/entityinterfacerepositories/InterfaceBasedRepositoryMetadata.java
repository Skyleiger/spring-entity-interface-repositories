package de.dwienzek.spring.entityinterfacerepositories;

import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.DefaultRepositoryMetadata;
import org.springframework.data.util.TypeInformation;

class InterfaceBasedRepositoryMetadata extends DefaultRepositoryMetadata {

    private final TypeInformation<?> idTypeInformation;
    private final TypeInformation<?> domainTypeInformation;

    InterfaceBasedRepositoryMetadata(RepositoryMetadata actualMetadata, Class<?> newType) {
        super(actualMetadata.getRepositoryInterface());
        this.domainTypeInformation = TypeInformation.of(newType);
        this.idTypeInformation = actualMetadata.getIdTypeInformation();
    }

    @Override
    public TypeInformation<?> getIdTypeInformation() {
        return idTypeInformation;
    }

    @Override
    public TypeInformation<?> getDomainTypeInformation() {
        return domainTypeInformation;
    }

}