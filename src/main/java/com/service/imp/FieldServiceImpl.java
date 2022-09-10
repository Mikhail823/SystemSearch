package com.service.imp;

import com.model.Field;
import com.repository.FieldRepository;
import com.service.FieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FieldServiceImpl implements FieldService {
    private final FieldRepository fieldRepository;

    @Autowired
    public FieldServiceImpl(FieldRepository fieldRepository) {
        this.fieldRepository = fieldRepository;
    }

    @Override
    public Field getFieldByName(String field) {
        return fieldRepository.findByName(field);
    }

    @Override
    public void save(Field field) {
        fieldRepository.save(field);
    }

    @Override
    public List<Field> getAllField() {
        List<Field> fields = new ArrayList<>();
        Iterable<Field> iterable = fieldRepository.findAll();
        iterable.forEach(fields::add);
        return fields;
    }
}
