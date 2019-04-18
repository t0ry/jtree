package com.nextgen.jtree;

import java.util.Set;
import java.util.function.Predicate;

public final class JTree<T> {
  private Node<T> root;

  private JTree() {}

  private JTree(final T data) {
    root = new Node<T>(data);
  }

  public static <T> JTree<T> emptyTree() {
    return new JTree<T>();
  }

  public static <T> JTree<T> treeWithRoot(final T data) {
    if (data == null) {
      throw new IllegalArgumentException();
    }

    return new JTree<T>(data);
  }

  public void addRoot(final T data) {
    if (root != null) {
      throw new IllegalStateException("Root is already initialized");
    }
    root = new Node<T>(data);
  }

  public Node<T> getRoot() {
    return root;
  }

  public Set<T> find(final Predicate<T> filter) {
    return root.find(filter);
  }
}
