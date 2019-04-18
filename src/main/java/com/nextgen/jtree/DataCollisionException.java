package com.nextgen.jtree;

public final class DataCollisionException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public DataCollisionException(final Object data) {
    super(String.format("Data [%s] is already present in node or subtree", data));
  }
}
