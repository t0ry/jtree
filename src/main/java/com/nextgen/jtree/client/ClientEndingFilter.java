package com.nextgen.jtree.client;

import java.util.function.Predicate;

final class ClientEndingFilter implements Predicate<ClientData> {

  @Override
  public boolean test(final ClientData data) {
    return data.getString().endsWith("test data.");
  }
}
