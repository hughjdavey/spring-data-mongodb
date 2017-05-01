package org.springframework.data.mongodb.repository;

import org.bson.types.ObjectId;

public interface EntityRepository extends MongoRepository<Entity, String> {

    Entity findById(final String id);

    Entity findByNestedEntityId(final String id);

    Entity findById(final ObjectId id);

    Entity findByNestedEntityId(final ObjectId id);
}
