package com.dripchip.demo.dao;

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
    Optional<T> get(int id);
    void update(T t, int id);
    void delete(int id);
}
