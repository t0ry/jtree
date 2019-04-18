package com.nextgen.jtree.client;

import java.util.Set;
import com.nextgen.jtree.JTree;

/**
 * Client example with custom data.
 */
final class Client {

  static JTree<ClientData> buildTreeWithDataToNotFind() {
    final JTree<ClientData> tree = JTree.treeWithRoot(new ClientData("root"));

    tree.getRoot().addNode(new ClientData("sub root 1"))
        .addNode(new ClientData("Hey-ho! Hello, I'm sub-sub root with test data."));
    tree.getRoot().addNode(new ClientData("sub root 2"))
        .addNode(new ClientData("I'm not saying 'Hello', I'm sub-sub root with test data."));
    tree.getRoot().addNode(new ClientData("sub root 3")).addNode(new ClientData("Hello"));

    return tree;
  }


  static JTree<ClientData> buildTreeWithDataToFind() {
    final JTree<ClientData> tree = JTree.treeWithRoot(new ClientData("root"));

    tree.getRoot().addNode(new ClientData("sub root 1"))
        .addNode(new ClientData("Hello, I'm sub-sub root with test data."));
    tree.getRoot().addNode(new ClientData("sub root 2"))
        .addNode(new ClientData("I'm not saying 'Hello', I'm sub-sub root with test data."));
    tree.getRoot().addNode(new ClientData("sub root 3"))
        .addNode(new ClientData("Hello, I'm another sub-sub root with test data."));

    return tree;
  }

  static Set<ClientData> findInTree(final JTree<ClientData> tree) {
    return tree.findData(new ClientBiginningFilter().and(new ClientEndingFilter()));
  }
}
