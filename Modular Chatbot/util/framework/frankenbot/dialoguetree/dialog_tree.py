# for abstract class
from abc import ABC
from anytree import Node, NodeMixin, RenderTree
from anytree.exporter import DotExporter
from graphviz import Digraph

class DialogTree(ABC):

    def generate_tree(self):
        pass

    # def generate_tree(self, tree_nodes):
    #     pass
        # raise Exception("Subclass should implement find_ module")

# This dialogmanager iterates through all its modules and returns the module with the highest activation.


class ModuleBasedDialogTree(DialogTree, NodeMixin):

    trees = dict()

    # def __init__(self, name, length, width):
    #     super(ModulesBasedDialogTree, self).__init__()
    #     self.name = name
    #     self.length = length
    #     self.width = width

    def __init__(self, trees):

        self.trees = trees
        self.generate_tree()

    # def __repr__(self):
    #     return str(self)

    def generate_tree(self):
        for each_tree in self.trees:
            # print(each_tree)
                for each_node in self.trees[each_tree]:
                    if each_node.parent_node:
                        # for each_parent in each_node.parent_node:
                        for each in self.trees[each_tree]:
                            if each_node.parent_node == each.intent_name:
                                each_node.parent = each
            # self.print_tree()
                # return self.tree_nodes
                        # if each.children:
                        #     for each_c in each.children:
                        #         print(each_c.intent_name)
            # tree_nodes[0].generate_tree(tree_nodes[0])

    def print_tree(self):
        for each_tree in self.trees:
            for each_node in self.trees[each_tree]:
                # for node in RenderTree(each_node):
                #     print(node)
                if each_node.is_root:
                    for pre, _, c_node in RenderTree(each_node):
                        # if node.is_root:
                        #     for pre, _, c_node in RenderTree(node):
                        treestr = u"%s%s" % (pre, c_node.intent_name)
                        print(treestr.ljust(8), c_node.parent_node)

            # for pre, _, node in RenderTree(each_node):
            #     treestr = u"%s%s" % (pre, node.intent_name)
            #     print(treestr.ljust(8), node.parent_node)

    # def generate_tree(self, tree_nodes):
    #     if self.parent_node:
    #         for each_parent in self.parent_node:
    #             for each in tree_nodes:
    #                 if each_parent == each.intent_name:
    #                     self.parent = each
    #                 if each.children:
    #                     for each_c in each.children:
    #                         print(each_c.intent_name)
    #     # tree_nodes[0].generate_tree(tree_nodes[0])
    #     for pre, _, node in RenderTree(tree_nodes[0]):
    #         treestr = u"%s%s" % (pre, node.intent_name)
    #         print(treestr.ljust(8), node.parent_node)

        # tree = []
        # root = ""
        # parent = ""
        # for node in self.tree_nodes:
        #     if node["parent_node"] is None:
        #         root = Node(node)
        #         tree.append(root)
        #     else:
        #         for each in node["parent_node"]:
        #             if each == "root":
        #                 child = Node(node, parent=root)
        #                 tree.append(child)
        #                 # for each_node in self.tree_nodes:
        #                 #     if each == each_node["intent_name"]:
        #                 #         child = Node(node, parent=each_node)
        #                 #         tree.append(child)
        #             # parent = Node(node, parent=root)
        #             # tree.append(parent)
        #     # else:
        #     #     child = Node(node, parent=root)
        #     #     tree.append(child)
        # # print(tree)
        # # print(RenderTree(root))

        # for pre, _, node in RenderTree(obj):
        #     treestr = u"%s%s" % (pre, node.intent_name)
        #     print(treestr.ljust(8), node.intent_name)

        # for pre, fill, node in RenderTree(root):
        #     print("%s%s" % (pre, node.name))
        # DotExporter(root).to_picture("root.png")

        # return tree
