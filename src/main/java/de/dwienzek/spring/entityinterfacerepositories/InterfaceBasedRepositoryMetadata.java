/*
 * Copyright 2011-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.dwienzek.spring.entityinterfacerepositories;

import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.AbstractRepositoryMetadata;
import org.springframework.data.util.ClassTypeInformation;
import org.springframework.data.util.TypeInformation;

class InterfaceBasedRepositoryMetadata extends AbstractRepositoryMetadata {

    private final TypeInformation<?> idTypeInformation;
    private final TypeInformation<?> domainTypeInformation;

    InterfaceBasedRepositoryMetadata(RepositoryMetadata actualMetadata, Class<?> newType) {
        super(actualMetadata.getRepositoryInterface());
        this.domainTypeInformation = ClassTypeInformation.from(newType);
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