package org.springframework.data.mongodb.repository;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import static org.springframework.data.mongodb.repository.NestedEntityRepository.NestedEntity;

public interface EntityRepository extends MongoRepository<EntityRepository.Entity, String> {

    Entity findById(final String id);

    Entity findByNestedEntityId(final String id);

    Entity findById(final ObjectId id);

    Entity findByNestedEntityId(final ObjectId id);

    @Data
    class Entity {

        @Id
        public String id;

        public final NestedEntity nestedEntity;
    }
}
