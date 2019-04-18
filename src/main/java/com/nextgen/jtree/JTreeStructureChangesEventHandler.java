package com.nextgen.jtree;

@FunctionalInterface
public interface JTreeStructureChangesEventHandler<T> {
  public enum TreeStructureChangeEvent {
    ADD_NODE, REMOVE_NODE
  };

  void handle(Node<T> changedNode, Node<T> cause, TreeStructureChangeEvent event);
}
