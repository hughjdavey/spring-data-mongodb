package org.springframework.data.mongodb.repository;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class NestedEntity {

    @Id
    public String id;
}
