package com.nextgen.jtree.client;

import java.util.Objects;

public class ClientData {
  private String string;

  public ClientData(String string) {
    this.string = string;
  }

  public String getString() {
    return string;
  }

  public void setString(String string) {
    this.string = string;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(string);
  }

  @Override
  public boolean equals(final Object other) {
    if (other == null) {
      return false;
    }
    if (this == other) {
      return true;
    }
    if (!other.getClass().equals(getClass())) {
      return false;
    }

    final ClientData otherData = (ClientData) other;

    return this.string.equals(otherData.string);
  }
}
