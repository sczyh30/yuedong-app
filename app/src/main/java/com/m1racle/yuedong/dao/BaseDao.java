package com.m1racle.yuedong.dao;

/**
 * Yuedong App
 * Base Dao Interface
 * @author sczyh30
 * @since v1.22
 */
public interface BaseDao<T> {

    boolean save(final T t);

    boolean remove(int id);

    T get(int id);

    boolean update(final T t, int id);

}
