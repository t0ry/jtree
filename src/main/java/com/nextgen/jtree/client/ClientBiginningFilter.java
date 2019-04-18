package com.nextgen.jtree.client;

import java.util.function.Predicate;

final class ClientBiginningFilter implements Predicate<ClientData> {

  @Override
  public boolean test(final ClientData data) {
    return data.getString().startsWith("Hello,");
  }
}
