package org.springframework.data.mongodb.repository;

import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.data.mongodb.repository.EntityRepository.Entity;
import static org.springframework.data.mongodb.repository.NestedEntityRepository.NestedEntity;

@Slf4j
@ContextConfiguration("config/MongoNamespaceIntegrationTests-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class FindByIdQueryIntegrationTests {

    @Autowired
    private EntityRepository entityRepository;

    @Autowired
    private NestedEntityRepository nestedEntityRepository;

    private Entity createdEntity;

    private NestedEntity createdNestedEntity;

    @Before
    public void setup() {
        nestedEntityRepository.deleteAll();
        createdNestedEntity = nestedEntityRepository.save(new NestedEntity());

        entityRepository.deleteAll();
        createdEntity = entityRepository.save(new Entity(createdNestedEntity));
    }

    @Test
    public void findEntityById_String() {
        final Entity entityById_String = entityRepository.findById(createdEntity.getId());
        assertThat(entityById_String, notNullValue());
        assertThat(entityById_String, equalTo(createdEntity));
    }

    @Test
    public void findEntityById_ObjectId() {
        final Entity entityById_ObjectId = entityRepository.findById(new ObjectId(createdEntity.getId()));
        assertThat(entityById_ObjectId, notNullValue());
        assertThat(entityById_ObjectId, equalTo(createdEntity));
    }

    @Test
    public void findEntityByNestedEntityId_String() {
        final Entity entityByNestedId_String = entityRepository.findByNestedEntityId(createdNestedEntity.getId());
        assertThat(entityByNestedId_String, notNullValue());
        assertThat(entityByNestedId_String, equalTo(createdEntity));
    }

    @Test
    public void findEntityByNestedEntityId_ObjectId() {
        final Entity entityByNestedId_ObjectId = entityRepository.findByNestedEntityId(new ObjectId(createdNestedEntity.getId()));
        assertThat(entityByNestedId_ObjectId, notNullValue());
        assertThat(entityByNestedId_ObjectId, equalTo(createdEntity));
    }
}
