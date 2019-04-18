package com.nextgen.jtree;

/**
 * Utility class providing useful operations on {@link JTree}.
 */
public final class JTreeManager {

  /**
   * Calculates maximum allowed number of child nodes for particular {@code node} in presence of
   * given {@code maxAllowedChildren} limit.
   * 
   * @param node to be populated with child nodes
   * @param maxAllowedChildren limit for child nodes
   * @param <T> data type to be hold in {@code node}
   * @return child nodes limit for {@code node}
   * @throws IllegalArgumentException if {@code node} is {@code null} or {@code maxAllowedChildren}
   *         less than {@code 0}
   */
  public static <T> int defineMaxChildrenLimitForNode(final Node<T> node,
      final int maxAllowedChildren) {
    if (node == null || maxAllowedChildren < 0) {
      throw new IllegalArgumentException();
    }

    int limit = maxAllowedChildren - node.getSubtreeLength();

    if (limit <= 0) {
      return 0;
    }

    if (node.getParent() == null) {
      return limit;
    }

    return defineMaxChildrenLimitForNode(node.getParent(), maxAllowedChildren);
  }
}
