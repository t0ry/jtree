package com.nextgen.jtree.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import com.nextgen.jtree.JTree;

public class ClientTest {
  @Test
  public void findInTreeTest() {
    final JTree<ClientData> tree = Client.buildTreeWithDataToFind();

    final List<ClientData> expectedData =
        Arrays.asList(new ClientData("Hello, I'm sub-sub root with test data."),
            new ClientData("Hello, I'm another sub-sub root with test data."));
    final int expectedResultSize = 2;

    Set<ClientData> result = Client.findInTree(tree);

    assertEquals(expectedResultSize, result.size());
    expectedData.forEach(d -> result.contains(d));
  }

  @Test
  public void findInTreeNotFoundTest() {
    final JTree<ClientData> tree = Client.buildTreeWithDataToNotFind();

    Set<ClientData> result = Client.findInTree(tree);

    assertEquals(0, result.size());
  }
}
