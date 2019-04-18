package com.nextgen.jtree;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class JTreeManagerTest {

  @Test
  public void defineMaxChildrenLimitForNodeTest() {
    final Object data = new Object();
    final int maxChildrenLimit = 10;

    final Node<Object> node = new Node<>(data);
    final Node<Object> lastNode =
        node.addNode(new Object()).addNode(new Object()).addNode(new Object());

    final int expectedSubtreeLength = 3;
    assertEquals(expectedSubtreeLength, node.getSubtreeLength());

    lastNode.addNode(new Object());
    lastNode.addNode(new Object());

    final int expectedMaxChildrenLimit = 5;
    assertEquals(expectedMaxChildrenLimit,
        JTreeManager.defineMaxChildrenLimitForNode(lastNode, maxChildrenLimit));
  }
}
