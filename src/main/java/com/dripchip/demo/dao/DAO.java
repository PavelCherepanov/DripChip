package com.dripchip.demo.dao;

import com.dripchip.demo.models.Account;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.Optional;

/**
 * @author Pavel
 * @version 1.0
 * @date 22.02.2023 18:49
 */
public interface DAO<T> {
    List<T> list();
    void create(T t);
    Optional<T> get(Long id);
    void update(T t, Long id);
    void delete(Long id);


    List<Account> search(String email, String firstName, String lastName, Integer from, Integer size);
}
