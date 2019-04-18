package com.nextgen.jtree;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import com.nextgen.jtree.JTreeStructureChangesEventHandler.TreeStructureChangeEvent;

public class Node<T> implements Searcheable<T> {
  private Node<T> parent;

  private final T data;
  private final Set<Node<T>> subtree = new HashSet<>();

  private final List<JTreeStructureChangesEventHandler<T>> structureChangesHandlers =
      new LinkedList<>();

  public Node(final T data) {
    verifyArguments(data);

    this.data = data;
  }

  public Node<T> addNode(final T data) {
    verifyArguments(data);

    return addNode(new Node<>(data));
  }

  public Node<T> addNode(final Node<T> node) {
    verifyArguments(node);
    verifyDataCollision(node);

    node.parent = this;
    structureChangesHandlers.forEach(h -> node.addStructureChangesHandler(h, true));

    subtree.add(node);

    notifyChanges(node, TreeStructureChangeEvent.ADD_NODE);

    return node;
  }

  public boolean removeNode(final Node<T> node) {
    final boolean removed = subtree.removeIf(n -> n.equals(node));

    if (removed) {
      notifyChanges(node, TreeStructureChangeEvent.REMOVE_NODE);
    }

    return removed;
  }

  public boolean removeNodeWithData(final T data) {
    final Node<T> nodeToRemove =
        subtree.stream().filter(n -> n.getData().equals(data)).findFirst().orElse(null);

    return removeNode(nodeToRemove);
  }

  public T getData() {
    return this.data;
  }

  public Set<Node<T>> getSubtree() {
    return Collections.unmodifiableSet(subtree);
  }

  public int getSubtreeLength() {
    int subtreeLength = subtree.size();
    for (Node<T> node : subtree) {
      subtreeLength += node.getSubtreeLength();
    }

    return subtreeLength;
  }

  public void addStructureChangesHandler(
      final JTreeStructureChangesEventHandler<T> structureChangeHandler,
      final boolean requrcively) {
    structureChangesHandlers.add(structureChangeHandler);

    if (requrcively) {
      subtree.forEach(n -> n.addStructureChangesHandler(structureChangeHandler, requrcively));
    }
  }

  public void removeStructureChangesHandler(
      final JTreeStructureChangesEventHandler<T> structureChangeHandler,
      final boolean requrcively) {
    structureChangesHandlers.remove(structureChangeHandler);

    if (requrcively) {
      subtree.forEach(n -> n.removeStructureChangesHandler(structureChangeHandler, requrcively));
    }
  }

  public Node<T> getParent() {
    return parent;
  }

  public boolean isRoot() {
    return parent == null;
  }

  @Override
  public String toString() {
    return String.format("%s: [data: %s, subtree size: %s]", getClass().getName(), data,
        subtree.size());
  }


  @Override
  public Set<T> find(final Predicate<T> filter) {
    final Set<T> result = new HashSet<>();
    if (filter.test(data)) {
      result.add(data);
    }

    for (Node<T> node : subtree) {
      result.addAll(node.find(filter));
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

  private void verifyDataCollision(final Node<T> node) {
    if (!find(node.getData()::equals).isEmpty()) {
      throw new DataCollisionException(node.getData());
    }
  }

  private void notifyChanges(final Node<T> cause, final TreeStructureChangeEvent event) {
    if (structureChangesHandlers.isEmpty()) {
      return;
    }
    structureChangesHandlers.forEach(h -> h.handle(this, cause, event));
  }

}
