package com.nextgen.jtree;

import java.util.Set;
import java.util.function.Predicate;

/**
 * Represents complex searching functionality with conditions defines as predicate.
 *
 * @param <T> data type to apply {@code filter}
 */
public interface Searcheable<T> {

  /**
   * Finds data corresponding to conditions in {@code filter}
   * 
   * @param filter predicate to satisfy
   * @return found data if any
   */
  Set<T> findData(final Predicate<T> filter);

  /**
   * Finds {@link Node}s with data corresponding to conditions in {@code filter}
   * 
   * @param filter predicate to satisfy
   * @return found nodes if any
   */
  Set<Node<T>> findNodes(final Predicate<T> filter);
}
