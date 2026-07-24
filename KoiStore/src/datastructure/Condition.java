package datastructure;

/**
 * Small functional-style interface so lists can be filtered by any field
 * (color, name keyword, ...) without hard-coding the entity type.
 */
public interface Condition<T> {
    boolean test(T item);
}
