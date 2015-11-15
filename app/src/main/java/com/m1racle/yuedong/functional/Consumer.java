package com.m1racle.yuedong.functional;

/**
 * Functional Library
 * Consumer container
 * Represents an operation that accepts a single input argument and returns no
 * result.
 */
public interface Consumer<T> {

    void accept(T t);

}
