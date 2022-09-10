package com.repository;

import com.model.Field;
import org.springframework.data.repository.CrudRepository;

public interface FieldRepository extends CrudRepository<Field, Integer> {
    Field findByName(String field);
}
