package com.service;

import com.model.Field;

import java.util.List;

public interface FieldService {
    Field getFieldByName(String field);
    void save(Field field);
    List<Field> getAllField();
}
