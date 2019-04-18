package com.nextgen.jtree;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.Collections;
import java.util.HashSet;
import java.util.function.Predicate;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

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

  @Test
  public void findDataWithEmptyTreeTest() {
    final JTree<Object> jTree = JTree.<Object>emptyTree();
    @SuppressWarnings("unchecked")
    final Predicate<Object> filterMock = Mockito.mock(Predicate.class);

    final IllegalStateException actualException =
        assertThrows(IllegalStateException.class, () -> jTree.findData(filterMock));
    assertEquals("Tree is empty.", actualException.getMessage());
  }

  @Test
  public void findNodesWithEmptyTreeTest() {
    final JTree<Object> jTree = JTree.<Object>emptyTree();
    @SuppressWarnings("unchecked")
    final Predicate<Object> filterMock = Mockito.mock(Predicate.class);

    final IllegalStateException actualException =
        assertThrows(IllegalStateException.class, () -> jTree.findNodes(filterMock));
    assertEquals("Tree is empty.", actualException.getMessage());
  }

  @Test
  public void findDataWithNullFilterTest() {
    final JTree<Object> jTree = JTree.<Object>treeWithRoot(new Object());

    assertThrows(IllegalArgumentException.class, () -> jTree.findData(null));
  }

  @Test
  public void findNodesWithNullFilterTest() {
    final JTree<Object> jTree = JTree.<Object>treeWithRoot(new Object());

    assertThrows(IllegalArgumentException.class, () -> jTree.findNodes(null));
  }

  @Test
  public void findDataTest() {
    final JTree<Object> jTree = JTree.<Object>emptyTree();
    final Object data = new Object();
    jTree.addRoot(new Object());
    final Node<Object> node = jTree.getRoot();
    final Node<Object> nodeWithDataToFind = node.addNode(data);
    nodeWithDataToFind.addNode(new Object());

    assertEquals(new HashSet<>(Collections.singletonList(nodeWithDataToFind.getData())),
        jTree.findData(data::equals));
  }

  @Test
  public void findNodesTest() {
    final JTree<Object> jTree = JTree.<Object>emptyTree();
    final Object data = new Object();
    jTree.addRoot(new Object());
    final Node<Object> node = jTree.getRoot();
    final Node<Object> nodeWithDataToFind = node.addNode(data);
    nodeWithDataToFind.addNode(new Object());

    assertEquals(new HashSet<>(Collections.singletonList(nodeWithDataToFind)),
        jTree.findNodes(data::equals));
  }

  @Test
  public void addRootWithNullDataTest() {
    final JTree<Object> jTree = JTree.<Object>emptyTree();

    assertThrows(IllegalArgumentException.class, () -> jTree.addRoot(null));
  }
}
