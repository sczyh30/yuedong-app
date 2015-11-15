package com.m1racle.yuedong.functional;

/**
 * Samsara Common Library
 * Functional programming standard
 * Optional Container
 * monad principle
 * like Maybe in Haskell
 * for JDK 1.7 below
 */
public final class Optional<T> {

    /**
     * The common empty container
     */
    private static final Optional<?> NONE = new Optional<>();

    private final T object;

    private Optional() {
        this.object = null;
    }

    private Optional(T obj) {
        if (obj == null)
            throw new NullPointerException();
        this.object = obj;
    }

    public static<T> Optional<T> none() {
        @SuppressWarnings("unchecked")
        Optional<T> t = (Optional<T>) NONE;
        return t;
    }

    /**
     * This method is used to create an optional object without null object
     * @param obj target object (do not support null object)
     * @param <T> object type
     * @return the new optional object
     */
    public static <T> Optional<T> of(T obj) {
        return new Optional<>(obj);
    }

    /**
     * This method is used to create an optional object
     * @param <T> object type
     * @param obj target object (any, support null object)
     * @return the new optional object
     */
    public static <T> Optional<?> ofNullable(T obj) {
        return obj == null ? none() : of(obj);
    }

    /**
     * Get the obj if it exists, or throws an exception
     * @return the object
     * @throws java.util.NoSuchElementException
     */
    public T get() {
        if(object == null)
            throw new java.util.NoSuchElementException("target obj do not exist");
        return object;
    }

    /**
     * If the obj exists, return true
     * @return if the obj exists
     */
    public boolean exists() {
        return object != null;
    }

    /**
     * If the obj exists, return this object
     * Or else, return the other object
     * @param other the replace object
     * @return the result
     */
    public T orElse(T other) {
        return object != null ? object : other;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;

        if (!(obj instanceof Optional)) {
            return false;
        }

        Optional<?> other = (Optional<?>) obj;
        return (object == other.object) || (object != null && object.equals(other.object));
    }

    @Override
    public int hashCode() {
        return object != null ? object.hashCode() : 0;
    }

    @Override
    public String toString() {
        return object != null
                ? String.format("Optional[%s]", object)
                : "Optional.None";
    }



}
