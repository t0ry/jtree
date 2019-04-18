package com.nextgen.jtree;

public final class JTreeManager {
  public static <T> int defineMaxChildrenLimitForNode(final Node<T> node,
      final int maxAllowedChildren) {
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
