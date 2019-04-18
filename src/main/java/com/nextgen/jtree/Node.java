package com.nextgen.jtree;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import com.nextgen.jtree.JTreeStructureChangesEventHandler.TreeStructureChangeEvent;

/**
 * Represents data container as a node of {@link JTree}. Provides main operations on the node i.e.
 * add, remove, find, etc.
 * 
 * Data immutability is not guaranteed.
 *
 * @param <T> data to be hold
 */
public final class Node<T> implements Searcheable<T> {
  private Node<T> parent;

  private final T data;
  private final Set<Node<T>> subtree = new HashSet<>();

  private final List<JTreeStructureChangesEventHandler<T>> structureChangesHandlers =
      new LinkedList<>();

  /**
   * Creates {@code Node} with {@code data}.
   * 
   * @param data data to be hold in node
   * @throws IllegalArgumentException if data is {@code null}
   */
  public Node(final T data) {
    verifyArguments(data);

    this.data = data;
  }

  /**
   * Adds new {@code Node} to the current one with {@code data}.
   * 
   * @param data data to be hold in node
   * @return added node
   * @throws IllegalArgumentException if data is {@code null}
   */
  public Node<T> addNode(final T data) {
    verifyArguments(data);

    return addNode(new Node<>(data));
  }


  /**
   * Adds new {@code node} to the current one.
   * 
   * @param node node to add
   * @return added node
   * @throws IllegalArgumentException if node is {@code null}
   */
  public Node<T> addNode(final Node<T> node) {
    verifyArguments(node);

    node.parent = this;
    structureChangesHandlers.forEach(h -> node.addStructureChangesHandler(h, true));

    subtree.add(node);

    notifyChanges(node, TreeStructureChangeEvent.ADD_NODE);

    return node;
  }

  /**
   * Removes {@code node} from the current one if found.
   * 
   * @param node node to remove
   * @return {@code true} if {@code node} has been removed, otherwise returns {@code false}
   */
  public boolean removeNode(final Node<T> node) {
    final boolean removed = subtree.removeIf(n -> n.equals(node));

    if (removed) {
      notifyChanges(node, TreeStructureChangeEvent.REMOVE_NODE);
    }

    return removed;
  }

  /**
   * Gets data hold in current node.
   * 
   * @return data hold in current node
   */
  public T getData() {
    return this.data;
  }

  /**
   * Gets subtree of current node as unmodifiable {@code Set}.
   * 
   * @return subtree as unmodifiable {@code Set}
   */
  public Set<Node<T>> getSubtree() {
    return Collections.unmodifiableSet(subtree);
  }

  /**
   * Gets full subtree length.
   * 
   * @return full subtree length
   */
  public int getSubtreeLength() {
    int subtreeLength = subtree.size();
    for (Node<T> node : subtree) {
      subtreeLength += node.getSubtreeLength();
    }

    return subtreeLength;
  }

  /**
   * Allows to add {@link JTreeStructureChangesEventHandler} to the node either recursively or for
   * current node only.
   * 
   * @param structureChangeHandler event handler
   * @param recursively specifies whether this handler must be attributed recursively to all child
   *        nodes
   */
  public void addStructureChangesHandler(
      final JTreeStructureChangesEventHandler<T> structureChangeHandler,
      final boolean recursively) {
    structureChangesHandlers.add(structureChangeHandler);

    if (recursively) {
      subtree.forEach(n -> n.addStructureChangesHandler(structureChangeHandler, recursively));
    }
  }

  /**
   * Allows to remove {@link JTreeStructureChangesEventHandler} from the node either recursively or
   * for current node only.
   * 
   * @param structureChangeHandler event handler
   * @param recursively specifies whether this handler must be removed recursively from all child
   *        nodes
   */
  public void removeStructureChangesHandler(
      final JTreeStructureChangesEventHandler<T> structureChangeHandler,
      final boolean recursively) {
    structureChangesHandlers.remove(structureChangeHandler);

    if (recursively) {
      subtree.forEach(n -> n.removeStructureChangesHandler(structureChangeHandler, recursively));
    }
  }

  /**
   * Gets parent node.
   * 
   * @return parent node.
   */
  public Node<T> getParent() {
    return parent;
  }

  /**
   * Identifies whether current node is root.
   * 
   * @return {@code true} if current node is root, otherwise returns {@code false}
   */
  public boolean isRoot() {
    return parent == null;
  }

  @Override
  public String toString() {
    return String.format("%s: [data: %s, subtree size: %s]", getClass().getName(), data,
        subtree.size());
  }


  @Override
  public Set<T> findData(final Predicate<T> filter) {
    verifyArguments(filter);

    final Set<T> result = new HashSet<>();
    if (filter.test(data)) {
      result.add(data);
    }

    for (Node<T> node : subtree) {
      result.addAll(node.findData(filter));
    }

    return result;
  }

  @Override
  public Set<Node<T>> findNodes(final Predicate<T> filter) {
    verifyArguments(filter);

    final Set<Node<T>> result = new HashSet<>();
    if (filter.test(data)) {
      result.add(this);
    }

    for (Node<T> node : subtree) {
      result.addAll(node.findNodes(filter));
    }

    return result;
  }

  private void verifyArguments(final Object... args) {
    for (Object arg : args) {
      if (arg == null) {
        throw new IllegalArgumentException();
      }
    }
  }

  private void notifyChanges(final Node<T> cause, final TreeStructureChangeEvent event) {
    if (structureChangesHandlers.isEmpty()) {
      return;
    }
    structureChangesHandlers.forEach(h -> h.handle(this, cause, event));
  }
}
