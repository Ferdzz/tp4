package com.deguet.joris.tp4.data;

import java.util.List;

/**
 * Created by 1452284 on 2016-04-25.
 */
public interface CRUD<T> {

    long save(T o);

    void saveMany(Iterable<T> list);

    void saveMany(T... list);

    T getById(Long p);

    List<T> getAll();

    void deleteOne(Long o);

    void deleteOne(T o);

    void deleteAll();
}
