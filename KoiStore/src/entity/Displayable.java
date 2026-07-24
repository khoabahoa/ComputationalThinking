package entity;

/**
 * Any entity that can print itself to console.
 * Lets the generic data structures (list/BST/etc.) call display()
 * without knowing the concrete entity type.
 */
public interface Displayable {
    void display();
}
