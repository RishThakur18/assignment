package com.losung.assignment.service;

import com.losung.assignment.models.entity.Contact;
import com.losung.assignment.repository.GenericRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilterService {

    @Autowired
    private GenericRepository genericRepository;

    public List<Contact> search(String key, List<String> columns, String entity){
        return genericRepository.search(key, columns, entity);
    };
}
