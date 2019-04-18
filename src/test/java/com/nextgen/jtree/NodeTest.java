package com.nextgen.jtree;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import com.nextgen.jtree.JTreeStructureChangesEventHandler.TreeStructureChangeEvent;

public final class NodeTest {

  @Test
  public void constructorWithDataTest() {
    final Object data = new Object();

    Node<Object> node = new Node<>(data);
    assertEquals(0, node.getSubtree().size());
    assertEquals(data, node.getData());
  }

  @Test
  public void constructorWithNullDataTest() {
    assertThrows(IllegalArgumentException.class, () -> new Node<>(null));
  }

  @Test
  public void isRootTest() {
    final Node<Object> node = new Node<>(new Object());

    assertTrue(node.isRoot());
    assertFalse(node.addNode(new Object()).isRoot());
  }

  @Test
  public void addNodeWithDataTest() {
    final Node<Object> node = new Node<>(new Object());
    final Object data1 = new Object();
    final Node<Object> addedNode = node.addNode(data1);

    assertEquals(1, node.getSubtree().size());
    assertEquals(addedNode.getData(), data1);
    assertEquals(addedNode, node.getSubtree().iterator().next());
  }

  @Test
  public void addNodeWithNullDataTest() {
    final Node<Object> node = new Node<>(new Object());

    assertThrows(IllegalArgumentException.class, () -> node.addNode(null));
  }

  @Test
  public void addNodeWithExistingDataTest() {
    final Object data = new Object();
    final Node<Object> node = new Node<>(data);

    final DataCollisionException actualException =
        assertThrows(DataCollisionException.class, () -> node.addNode(data));
    assertEquals(String.format("Data [%s] is already present in node or subtree", data),
        actualException.getMessage());
  }

  @Test
  public void addNodeWithNotificationTest() {
    final Node<Object> node = new Node<>(new Object());

    node.addStructureChangesHandler((n, c, e) -> {
      assertEquals(node, n);
      assertEquals(TreeStructureChangeEvent.ADD_NODE, e);
    }, false);
    node.addStructureChangesHandler((n, c, e) -> {
      assertTrue(node.equals(n));
      assertTrue(TreeStructureChangeEvent.ADD_NODE.equals(e));
    }, false);

    node.addNode(new Node<Object>(new Object()));
  }

  @Test
  public void addNodeWithDataAndNotificationTest() {
    final Node<Object> node = new Node<>(new Object());

    node.addStructureChangesHandler((n, c, e) -> {
      assertEquals(node, n);
      assertEquals(TreeStructureChangeEvent.ADD_NODE, e);
    }, false);
    node.addStructureChangesHandler((n, c, e) -> {
      assertTrue(node.equals(n));
      assertTrue(TreeStructureChangeEvent.ADD_NODE.equals(e));
    }, false);

    node.addNode(new Object());
  }

  @Test
  public void removeNodeWithDataTest() {
    final Object data = new Object();
    final Node<Object> node = new Node<>(new Object());
    node.addNode(data).addNode(new Object());

    assertTrue(node.removeNodeWithData(data));
    assertEquals(0, node.getSubtree().size());
  }

  @Test
  public void removeNodeWithDataAndNotificationTest() {
    final Node<Object> node = new Node<>(new Object());
    final Object data = new Object();
    node.addNode(data);

    node.addStructureChangesHandler((n, c, e) -> {
      assertEquals(node, n);
      assertEquals(TreeStructureChangeEvent.REMOVE_NODE, e);
    }, false);
    node.addStructureChangesHandler((n, c, e) -> {
      assertTrue(node.equals(n));
      assertTrue(TreeStructureChangeEvent.REMOVE_NODE.equals(e));
    }, false);

    assertTrue(node.removeNodeWithData(data));
    assertEquals(0, node.getSubtree().size());
  }

  @Test
  public void removeNodeTest() {
    final Node<Object> node = new Node<>(new Object());
    final Node<Object> nodeToDelete = node.addNode(new Object());
    nodeToDelete.addNode(new Object());

    assertTrue(node.removeNode(nodeToDelete));
    assertEquals(0, node.getSubtree().size());
  }

  @Test
  public void removeNodeWithNotificationTest() {
    final Node<Object> node = new Node<>(new Object());
    final Node<Object> nodeToDelete = node.addNode(new Object());
    nodeToDelete.addNode(new Object());

    node.addStructureChangesHandler((n, c, e) -> {
      assertEquals(node, n);
      assertEquals(TreeStructureChangeEvent.REMOVE_NODE, e);
    }, false);
    node.addStructureChangesHandler((n, c, e) -> {
      assertTrue(node.equals(n));
      assertTrue(TreeStructureChangeEvent.REMOVE_NODE.equals(e));
    }, false);

    assertTrue(node.removeNode(nodeToDelete));
    assertEquals(0, node.getSubtree().size());
  }

  @Test
  public void addStructureChangesHandlerRecursevelyTest() {
    final Node<Object> node = new Node<>(new Object());

    @SuppressWarnings("unchecked")
    final JTreeStructureChangesEventHandler<Object> structureChangesHandler =
        Mockito.mock(JTreeStructureChangesEventHandler.class);

    node.addStructureChangesHandler(structureChangesHandler, true);

    final Node<Object> subNode = node.addNode(new Node<Object>(new Object()));
    final Node<Object> subSubNode = subNode.addNode(new Node<Object>(new Object()));

    Mockito.verify(structureChangesHandler).handle(node, subNode,
        TreeStructureChangeEvent.ADD_NODE);
    Mockito.verify(structureChangesHandler).handle(subNode, subSubNode,
        TreeStructureChangeEvent.ADD_NODE);
  }

  @SuppressWarnings("unchecked")
  @Test
  public void removeStructureChangeHandlerRecursevelyTest() {
    final Node<Object> node = new Node<>(new Object());

    final JTreeStructureChangesEventHandler<Object> structureChangesHandler =
        Mockito.mock(JTreeStructureChangesEventHandler.class);

    node.addStructureChangesHandler(structureChangesHandler, true);

    final Node<Object> subNode = node.addNode(new Node<Object>(new Object()));
    final Node<Object> subSubNode = subNode.addNode(new Node<Object>(new Object()));

    Mockito.verify(structureChangesHandler).handle(node, subNode,
        TreeStructureChangeEvent.ADD_NODE);
    Mockito.verify(structureChangesHandler).handle(subNode, subSubNode,
        TreeStructureChangeEvent.ADD_NODE);

    node.removeStructureChangesHandler(structureChangesHandler, true);
    Mockito.reset(structureChangesHandler);

    subSubNode.addNode(new Object());

    Mockito.verify(structureChangesHandler, Mockito.never()).handle(Mockito.any(), Mockito.any(),
        Mockito.any());
  }

  @Test
  public void addStructureChangeHandlerNotRecursevelyTest() {
    final Node<Object> node = new Node<>(new Object());

    @SuppressWarnings("unchecked")
    final JTreeStructureChangesEventHandler<Object> structureChangesHandler =
        Mockito.mock(JTreeStructureChangesEventHandler.class);

    final Node<Object> subNode = node.addNode(new Node<Object>(new Object()));

    node.addStructureChangesHandler(structureChangesHandler, false);

    final Node<Object> subNode2 = node.addNode(new Node<Object>(new Object()));
    final Node<Object> subSubNode = subNode.addNode(new Node<Object>(new Object()));

    Mockito.verify(structureChangesHandler).handle(node, subNode2,
        TreeStructureChangeEvent.ADD_NODE);
    Mockito.verify(structureChangesHandler, Mockito.never()).handle(subNode, subSubNode,
        TreeStructureChangeEvent.ADD_NODE);
  }

  @SuppressWarnings("unchecked")
  @Test
  public void removeStructureChangeHandlerNotRecursevelyTest() {
    final Node<Object> node = new Node<>(new Object());

    final JTreeStructureChangesEventHandler<Object> structureChangesHandler =
        Mockito.mock(JTreeStructureChangesEventHandler.class);

    node.addStructureChangesHandler(structureChangesHandler, true);

    final Node<Object> subNode = node.addNode(new Node<Object>(new Object()));
    final Node<Object> subSubNode = subNode.addNode(new Node<Object>(new Object()));

    Mockito.verify(structureChangesHandler, Mockito.times(1)).handle(node, subNode,
        TreeStructureChangeEvent.ADD_NODE);
    Mockito.verify(structureChangesHandler, Mockito.times(1)).handle(subNode, subSubNode,
        TreeStructureChangeEvent.ADD_NODE);

    node.removeStructureChangesHandler(structureChangesHandler, false);

    Mockito.reset(structureChangesHandler);

    node.addNode(new Object());

    Mockito.verify(structureChangesHandler, Mockito.never()).handle(Mockito.eq(node),
        Mockito.<Node<Object>>any(), Mockito.eq(TreeStructureChangeEvent.ADD_NODE));
  }

  @Test
  public void findDeepTest() {
    final Object data = new Object();
    final Node<Object> node = new Node<>(new Object());
    final Node<Object> nodeWithDataToFind = node.addNode(data);
    nodeWithDataToFind.addNode(new Object());

    assertEquals(new HashSet<>(Collections.singletonList(nodeWithDataToFind.getData())),
        node.find(data::equals));
  }

  @Test
  public void getSubtreeTest() {
    final List<Object> data = Arrays.asList(new Object(), new Object(), new Object());
    final Node<Object> node = new Node<>(new Object());
    data.forEach(node::addNode);

    assertEquals(data.size(), node.getSubtree().size());
    node.getSubtree().forEach(n -> assertTrue(data.contains(n.getData())));

    assertThrows(UnsupportedOperationException.class,
        () -> node.getSubtree().add(new Node<Object>(new Object())));
  }

  @Test
  public void getSubtreeLengthTest() {
    final List<Object> data = Arrays.asList(new Object(), new Object(), new Object());
    final Node<Object> node = new Node<>(new Object());
    data.forEach(node::addNode);
    node.getSubtree().iterator().next().addNode(new Object());

    assertEquals(4, node.getSubtreeLength());
  }
}
