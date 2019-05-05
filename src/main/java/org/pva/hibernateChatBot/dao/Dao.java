package org.pva.hibernateChatBot.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {

    Optional<T> get(long id);

    T findByLogin(String login);

    T findByUserId(Long userId);

    List<T> getAll();

    void save(T t);

    void update(T t);

    void delete(T t);
}
