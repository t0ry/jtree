package com.nextgen.jtree.client;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import com.nextgen.jtree.Node;

public class TreeStructureChangeEventHandler implements Consumer<Node<ClientData>> {

  private final List<ClientData> dataFromEvent = new ArrayList<>();

  @Override
  public void accept(Node<ClientData> nodeWithChanges) {
    getDataFromEvent().add(nodeWithChanges.getData());
  }

  public List<ClientData> getDataFromEvent() {
    return dataFromEvent;
  }

}
