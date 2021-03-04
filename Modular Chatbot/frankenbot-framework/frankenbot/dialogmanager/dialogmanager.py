# for abstract class
from abc import ABC
from anytree import Node, RenderTree, AsciiStyle, PreOrderIter, LevelOrderIter


class DialogTreeManager(ABC):

    def find_node_module(self, utterance, active_state_nodes, session):
        pass


class MaxActivationDialogTreeManager(DialogTreeManager):
    dialog_trees: None

    def __init__(self, dialog_trees):
        self.dialog_trees = dialog_trees

    def find_node_module(self, utterance, active_state_nodes, session):

        intent_highest_activation = 0.0
        entity_highest_activation = 0.0
        highest_module = None
        intent_name = ""
        detected_entities = []
        active_node = None
        active_node_id = None
        module_id = None
        api_output = dict()
        api_modules_output = []
        user_utterance = dict()
        active_module = dict()
        active_module_output = dict()
        highest_module_id = None
        highest_activation = 0.0
        highest_intent = ""
        highest_detected_module_id = None

        for each_tree in self.dialog_trees.trees:
            module_output = None
            if "module_id" and "dialog_tree" and "training_data" and "rasa_interpreter" in each_tree:
                for each_node in each_tree["dialog_tree"]:
                    intent_activation, intent, entity_activation, recognized_entities, api_module_output = each_node.module.activation_function(utterance, each_tree["rasa_interpreter"], each_tree["module_id"], session)
                    if intent_activation > highest_activation:
                        highest_activation = intent_activation
                        highest_module_id = each_tree["module_id"]
                        highest_intent = intent
                        highest_detected_module_id = each_tree["module_id"]


                    if intent_activation > intent_highest_activation:
                        # intent_highest_activation = intent_activation
                        # highest_module = each_node.module
                        # intent_name = intent
                        if each_node.is_root:
                            if "module_id" in each_tree:
                                module_id = each_tree["module_id"]
                            for each_child in LevelOrderIter(each_node, maxlevel=1):
                                if each_child.intent_name == intent and intent_activation == highest_activation:
                                    # and intent_activation == highest_activation
                                    each_child.activation_flag = True
                                    intent_highest_activation = intent_activation
                                    highest_module = each_child.module
                                    intent_name = intent
                                    active_node_id = each_child.node_id
                                    active_node = each_child
                                    # last_active_module_id = module_id
                        else:
                            for each_child in LevelOrderIter(each_node):
                                if each_child.intent_name == intent and intent_activation == highest_activation:
                                    # print(each_child.intent_name)
                                    # intent_highest_activation = intent_activation
                                    # and intent_activation == highest_activation
                                    if "module_id" in each_tree:
                                        module_id = each_tree["module_id"]
                                    if module_id in active_state_nodes:
                                        if each_child.parent.activation_flag:
                                            # print("here", each_child.intent_name)
                                            if each_child.parent.node_id == active_state_nodes[module_id].node_id or each_child.node_id == active_state_nodes[module_id].node_id:
                                                # or each_child.parent == active_state_nodes[module_id].parent
                                                # each_child.parent.node_id == active_state_nodes[module_id].node_id  # for children on first level of a parent
                                                # or each_child.node_id == active_state_nodes[module_id].node_id  # for same user utterance
                                                # or each_child.parent == active_state_nodes[module_id].parent  # for user utterance for another intent at same current tree level
                                                each_child.activation_flag = True
                                                intent_highest_activation = intent_activation
                                                highest_module = each_child.module
                                                intent_name = intent
                                                active_node_id = each_child.node_id
                                                active_node = each_child
                                                # last_active_module_id = module_id
                                            elif each_child.parent == active_state_nodes[module_id].parent and not each_child.children:
                                                # and not each_child.children (remove from above if condition to start comparison between child nodes at same level)
                                                each_child.activation_flag = True
                                                intent_highest_activation = intent_activation
                                                highest_module = each_child.module
                                                intent_name = intent
                                                active_node_id = each_child.node_id
                                                active_node = each_child
                                                # last_active_module_id = module_id

                        if entity_activation > entity_highest_activation:
                            entity_highest_activation = entity_activation
                            if recognized_entities:
                                detected_entities = recognized_entities
                    if api_module_output:
                        module_output = api_module_output
                api_modules_output.append(module_output)
            else:
                raise Exception('"module_id" or "dialog_tree" or "training_data" or "rasa_interpreter" not found in in tree')

        if highest_activation != intent_highest_activation:
            highest_module = None
            intent_highest_activation = 0.0
            intent_name = ""
            entity_highest_activation = 0.0
            detected_entities = []
            active_node = None
            module_id = None
            highest_activation = 0.0
            highest_module_id = None
            highest_intent = ""

        user_utterance["text"] = utterance
        active_module["id"] = highest_module_id
        # active_module["id"] = module_id
        # active_module["id"] = active_node_id
        active_module["type"] = "dialog_tree_module"
        active_module["activation_value"] = highest_activation
        # active_module["activation_value"] = intent_highest_activation
        active_module_output["recognized_intent"] = highest_intent
        # active_module_output["recognized_intent"] = intent_name
        active_module_output["recognized_entities"] = detected_entities
        active_module["module_output"] = active_module_output
        api_output["user_utterance"] = user_utterance
        api_output["active_module"] = active_module
        api_output["modules_output"] = api_modules_output

        return highest_module, intent_highest_activation, intent_name, entity_highest_activation, detected_entities, active_node, module_id, highest_detected_module_id, self.dialog_trees, api_output
        # else:
        #     user_utterance["text"] = utterance
        #     active_module["id"] = highest_module_id
        #     # active_module["id"] = module_id
        #     # active_module["id"] = active_node_id
        #     active_module["type"] = "dialog_tree_module"
        #     active_module["activation_value"] = highest_activation
        #     # active_module["activation_value"] = intent_highest_activation
        #     active_module_output["recognized_intent"] = highest_intent
        #     # active_module_output["recognized_intent"] = intent_name
        #     active_module_output["recognized_entities"] = detected_entities
        #     active_module["module_output"] = active_module_output
        #     api_output["user_utterance"] = user_utterance
        #     api_output["active_module"] = active_module
        #     api_output["modules_output"] = api_modules_output
        #     return highest_module, intent_highest_activation , intent_name , entity_highest_activation , detected_entities, active_node, module_id, highest_module_id, self.dialog_trees, api_output