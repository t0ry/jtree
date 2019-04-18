package com.nextgen.jtree;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

public final class JTreeTest {
  @Test
  public void emptyTreeTest() {
    final JTree<Object> jTree = JTree.<Object>emptyTree();

    assertNull(jTree.getRoot());
  }

  @Test
  public void treeWithRootTest() {
    final Object data = new Object();
    final JTree<Object> jTree = JTree.treeWithRoot(data);

    assertNotNull(jTree.getRoot());
    assertEquals(jTree.getRoot().getData(), data);
  }

  @Test
  public void treeWithRootWithNullDataTest() {
    assertThrows(IllegalArgumentException.class, () -> JTree.treeWithRoot(null));
  }

  @Test
  public void addRootTest() {
    final Object data = new Object();
    final JTree<Object> jTree = JTree.<Object>emptyTree();
    jTree.addRoot(data);

    assertNotNull(jTree.getRoot());
    assertEquals(jTree.getRoot().getData(), data);
  }

  @Test
  public void addRootWithExistingRootTest() {
    final Object data = new Object();
    final JTree<Object> jTree = JTree.<Object>treeWithRoot(data);

    final IllegalStateException actualException =
        assertThrows(IllegalStateException.class, () -> jTree.addRoot(data));
    assertEquals("Root is already initialized", actualException.getMessage());
    assertEquals(jTree.getRoot().getData(), data);
  }

}
