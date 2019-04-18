package com.nextgen.jtree;

import java.util.Set;
import java.util.function.Predicate;

public class JTree<T> {
  private Node<T> root;

  private JTree() {}

  private JTree(final T data) {
    root = new Node<T>(data);
  }

  public static <T> JTree<T> emptyTree() {
    return new JTree<T>();
  }

  public static <T> JTree<T> treeWithRoot(final T data) {
    return new JTree<T>(data);
  }

  public void addRoot(final T data) {
    if (root != null) {
      throw new IllegalStateException("Root is already initialized");
    }
    root = new Node<T>(data);
  }

  // public void addNode(final T data, final Node<T> parentNode) {
  // parentNode.addNode(new Node<T>(data));
  // }
  //
  // public void addNodeToRoot(final T data) {
  // root.addNode(new Node<T>(data));
  // }
  //
  // public boolean removeNode(final Node<T> nodeToRemove, final Node<T>
  // parentNode) {
  // return parentNode.removeNode(nodeToRemove);
  // }
  //
  // public boolean removeFromRoot(final Node<T> nodeToRemove) {
  // return root.removeNode(nodeToRemove);
  // }
  //
  // public Set<Node<T>> getSubtree(final Node<T> parent) {
  // return parent.getSubtree();
  // }

  public Node<T> getRoot() {
    return root;
  }

  public Set<T> find(final Predicate<T> filter) {
    return root.find(filter);
  }
}
