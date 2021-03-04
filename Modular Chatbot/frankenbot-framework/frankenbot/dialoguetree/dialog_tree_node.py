# for abstract class
from abc import ABC
from anytree import Node, NodeMixin, RenderTree


class DialogTreeNode(ABC):

    def generate_node(self, tree_node):
        pass


class ModuleBasedDialogTreeNode(DialogTreeNode, NodeMixin):

    node_id: int
    parent_node = []
    intent_name = ""
    anything_else = bool
    activation_flag = bool
    responses = dict()
    module = None

    def __init__(self, node_id, parent_node, intent_name, anything_else, responses, module, parent=None, children=None):

        self.node_id = node_id
        self.parent_node = parent_node
        self.intent_name = intent_name
        self.anything_else = anything_else
        self.activation_flag = False
        self.responses = responses
        self.module = module
        self.parent = parent
        if children:
            self.children = children

    def generate_node(self, tree_node):
        if self.parent_node:
            for each in tree_node:
                if self.parent_node == each.intent_name:
                    self.parent = each
        return self