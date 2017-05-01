package org.springframework.data.mongodb.repository;

import lombok.Data;
import org.springframework.data.annotation.Id;

public interface NestedEntityRepository extends MongoRepository<NestedEntityRepository.NestedEntity, String> {

    @Data
    class NestedEntity {

        @Id
        public String id;
    }
}
