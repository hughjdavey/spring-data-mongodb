package org.springframework.data.mongodb.repository;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Entity {

    @Id
    public String id;

    public final NestedEntity nestedEntity;
}
