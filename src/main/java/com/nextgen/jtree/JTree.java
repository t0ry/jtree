package com.nextgen.jtree;

import java.util.Set;
import java.util.function.Predicate;

/**
 * Represents tree container for keeping data of arbitrary type {@code T}. Holds tree root and
 * allows to start tree building.
 * 
 * @param <T> data type to be hold in nodes
 */
public final class JTree<T> {
  private Node<T> root;

  private JTree() {}

  private JTree(final T data) {
    root = new Node<T>(data);
  }

  /**
   * Creates empty {@code JTree} without root initialized.
   * 
   * @param <T> data type to be hold in tree
   * @return {@code JTree} tree
   */
  public static <T> JTree<T> emptyTree() {
    return new JTree<T>();
  }

  /**
   * Creates {@code JTree} with root already populated with {@code data}.
   * 
   * @param data data to be hold in tree root
   * @param <T> data type to be hold in tree
   * @return {@code JTree} with root
   * @throws IllegalArgumentException if {@code data} is null
   */
  public static <T> JTree<T> treeWithRoot(final T data) {
    if (data == null) {
      throw new IllegalArgumentException();
    }

    return new JTree<T>(data);
  }

  /**
   * Adds root to {@code JTree} populating it with specified {@code data}.
   * 
   * @param data data data to be hold in tree root
   * @throws IllegalArgumentException if {@code data} is null
   * @throws IllegalStateException if root already initialized
   */
  public void addRoot(final T data) {
    if (data == null) {
      throw new IllegalArgumentException();
    }
    if (root != null) {
      throw new IllegalStateException("Root is already initialized");
    }

    root = new Node<T>(data);
  }

  /**
   * Gets root of {@code JTree}.
   * 
   * @return root of {@code JTree}
   */
  public Node<T> getRoot() {
    return root;
  }

  /**
   * Searches the tree for data satisfying conditions provided in {@code filter}.
   * 
   * @param filter conditional predicate to search for
   * @return found data if any
   * @throws IllegalStateException if root is {@code null}
   */
  public Set<T> findData(final Predicate<T> filter) {
    if (filter == null) {
      throw new IllegalArgumentException();
    }
    if (root == null) {
      throw new IllegalStateException("Tree is empty.");
    }

    return root.findData(filter);
  }

  /**
   * Searches the tree for {@link Node}s with data satisfying conditions provided in {@code filter}.
   * 
   * @param filter conditional predicate to search for
   * @return found {@code Node}s with data if any
   * @throws IllegalStateException if root is {@code null}
   */
  public Set<Node<T>> findNodes(final Predicate<T> filter) {
    if (filter == null) {
      throw new IllegalArgumentException();
    }
    if (root == null) {
      throw new IllegalStateException("Tree is empty.");
    }

    return root.findNodes(filter);
  }
}
