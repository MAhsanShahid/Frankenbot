# for abstract class
from abc import ABC
from anytree import Node, NodeMixin, RenderTree
from anytree.exporter import DotExporter
from graphviz import Digraph

class DialogTreeNode(ABC):

    def generate_node(self, tree_node):
        pass

    # def generate_tree(self, tree_nodes):
    #     pass
        # raise Exception("Subclass should implement find_ module")

# This dialogmanager iterates through all its modules and returns the module with the highest activation.


class ModuleBasedDialogTreeNode(DialogTreeNode, NodeMixin):

    # tree_nodes: []
    node_id: int
    parent_node = []
    intent_name = ""
    # intents = dict()
    # entities = dict()
    responses = dict()
    module = None

    # def __init__(self, name, length, width):
    #     super(ModulesBasedDialogTree, self).__init__()
    #     self.name = name
    #     self.length = length
    #     self.width = width

    # def __init__(self,node_id, parent_node, intent_name, intents, entities, responses, module, parent=None, children=None):
    def __init__(self, node_id, parent_node, intent_name, responses, module, parent=None, children=None):

        # self.tree_nodes = tree_nodes
        self.node_id = node_id
        self.parent_node = parent_node
        self.intent_name = intent_name
        # self.intents = intents
        # self.entities = entities
        self.responses = responses
        self.module = module
        self.parent = parent
        if children:
            self.children = children

    def generate_node(self, tree_node):
        if self.parent_node:
            # for each_parent in self.parent_node:
            for each in tree_node:
                if self.parent_node == each.intent_name:
                    self.parent = each
        return self
        # return tree_nodes
                    # if each.children:
                    #     for each_c in each.children:
                    #         print(each_c.intent_name)
        # tree_nodes[0].generate_tree(tree_nodes[0])
    # def print_tree(self):
    #     for pre, _, node in RenderTree(self.root):
    #         treestr = u"%s%s" % (pre, node.intent_name)
    #         print(treestr.ljust(8), node.parent_node)

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
