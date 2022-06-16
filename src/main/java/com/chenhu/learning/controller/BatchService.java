package com.chenhu.learning.controller;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.List;

/**
 * @author 陈虎
 * @date 2022-06-14 13:34
 */
@Service
public class BatchService {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public <T> List<T> batchInsert(Collection<T> records) {
        for (T record : records) {
            entityManager.persist(record);
        }
        entityManager.flush();
        return (List<T>) records;
    }

}
