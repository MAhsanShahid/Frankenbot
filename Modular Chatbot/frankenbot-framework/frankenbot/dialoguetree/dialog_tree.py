# for abstract class
from abc import ABC
from anytree import Node, NodeMixin, RenderTree
from anytree import Node, RenderTree, AsciiStyle, PreOrderIter


class DialogTree(ABC):

    def generate_tree(self):
        pass


class ModuleBasedDialogTree(DialogTree, NodeMixin):

    trees: None

    def __init__(self, trees):

        self.trees = trees
        self.generate_tree()

    def generate_tree(self):
        for each_tree in self.trees:
            for each in each_tree:
                if each == "dialog_tree":
                    for each_node in each_tree[each]:
                        if each_node.parent_node:
                            for node in each_tree[each]:
                                if each_node.parent_node == node.node_id:
                                    each_node.parent = node
    # def generate_tree(self):
    #     for each_tree in self.trees:
    #         for each in each_tree:
    #             if each == "dialog_tree":
    #                 for each_node in each_tree[each]:
    #                     if each_node.parent_node:
    #                         for every_tree in self.trees:
    #                             for e_node in every_tree["dialog_tree"]:
    #                                 # for node in each_tree[each]:
    #                                 if each_node.parent_node == e_node.node_id:
    #                                     each_node.parent = e_node
    #     self.print_tree()

    def print_tree(self):
        for each_tree in self.trees:
            for each_node in each_tree["dialog_tree"]:
                if each_node.is_root:
                    print([each_node.intent_name for each_node in PreOrderIter(each_node)])
                    # print([each_node.intent_name for each_node in PreOrderIter(each_node, maxlevel=2)])
                else:
                    print([each_node.intent_name for each_node in PreOrderIter(each_node)])