package com.nextgen.jtree;

/**
 * Represents event handler for {@link JTree} structure changes namely adding and removing
 * {@link Node}s.
 * 
 * Clients are expected to implement this interface in order to provide custom event handler for
 * structural modification in tree.
 * 
 * @param <T> data type to be hold in {@code Node}
 */
@FunctionalInterface
public interface JTreeStructureChangesEventHandler<T> {
  /**
   * Represents types of evens in {@link JTree}.
   */
  public enum TreeStructureChangeEvent {
    ADD_NODE, REMOVE_NODE
  };

  /**
   * Handles {@code event} caused by {@code cause} node to {@code changedNode}.
   * 
   * @param changedNode node under structural modification
   * @param cause node causes structural modification
   * @param event type of structural modification
   */
  void handle(Node<T> changedNode, Node<T> cause, TreeStructureChangeEvent event);
}
