package com.nextgen.jtree;

import java.util.Set;
import java.util.function.Predicate;

public interface Searcheable<T> {
  Set<T> find(final Predicate <T> filter);
}
