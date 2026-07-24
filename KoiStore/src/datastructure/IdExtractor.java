package datastructure;

/**
 * Lets a generic structure (SinglyLinkedList, BinarySearchTree, ...) know
 * how to pull the unique String id out of whatever entity T it is storing,
 * without the structure needing to know about KoiFish / Customer / etc.
 */
public interface IdExtractor<T> {
    String getId(T item);
}
