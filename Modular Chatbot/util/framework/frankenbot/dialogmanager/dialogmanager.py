# for abstract class
from abc import ABC


class DialogManager(ABC):

    def find_module(self, utterance, session):
        pass
        # raise Exception("Subclass should implement find_ module")


class DialogTreeManager(ABC):

    def find_node_module(self, utterance, rasa_interpreter, session):
        pass

# This dialogmanager iterates through all its modules and returns the module with the highest activation.


class MaxActivationDialogManager(DialogManager):

    modules: []

    def __init__(self, modules):
        self.modules = modules

    def find_module(self, utterance, session):

        intent_highest_activation = -1
        entity_highest_activation = -1
        highest_module = None
        intent_name = ""
        entity_name = ""

        for module in self.modules:

            intent_activation, intent, entity_activation, entity = module.activation_function(utterance, session)
            if intent_activation > intent_highest_activation:
                intent_highest_activation = intent_activation
                highest_module = module
                intent_name = intent
                if entity_activation > entity_highest_activation:
                    entity_highest_activation = entity_activation
                    entity_name = entity

        return highest_module, intent_highest_activation, intent_name, entity_highest_activation, entity_name


class MaxActivationDialogTreeManager(DialogTreeManager):
    dialog_tree: None

    def __init__(self, dialog_tree):
        self.dialog_tree = dialog_tree

    def find_node_module(self, utterance, rasa_interpreter, session):

        intent_highest_activation = -1
        entity_highest_activation = -1
        highest_module = None
        intent_name = ""
        # entity_name = ""
        detected_entities = []
        active_node = None
        active_node_id = None
        api_output = dict()
        api_modules_output = []
        user_utterance = dict()
        active_module = dict()
        active_module_output = dict()

        for each_tree in self.dialog_tree.trees:
            # print(each_tree)
            for each_node in self.dialog_tree.trees[each_tree]:
                # print(each_node.intent_name)
                # print(node.node_id)
                intent_activation, intent, entity_activation, recognized_entities, api_module_output = each_node.module.activation_function(utterance, rasa_interpreter, each_node.node_id, session)
                # intent_activation, intent, entity_activation, entity, recognized_entities, api_module_output = each_node.module.activation_function(utterance, each_node.node_id, session)
                if intent_activation > intent_highest_activation:
                    # print(each_node.intent_name)
                    intent_highest_activation = intent_activation
                    highest_module = each_node.module
                    intent_name = intent
                    active_node_id = each_node.node_id
                    # print(each_node.intent_name, intent)
                    if each_tree == "topic_tree" and each_node.intent_name == intent:
                        # print(each_node.intent_name, intent)
                        active_node = each_node
                        # if active_node.intent_name != intent:
                        #     active_node = None
                        # print(active_node.intent_name)
                    # print(active_node.intent_name)
                    if entity_activation > entity_highest_activation:
                        entity_highest_activation = entity_activation
                        # entity_name = entity
                        if recognized_entities:
                            detected_entities = recognized_entities
                if api_module_output:
                    api_modules_output.append(api_module_output)


        user_utterance["text"] = utterance
        active_module["id"] = active_node_id
        active_module["type"] = "dialog_tree_module"
        active_module["activation_value"] = intent_highest_activation
        active_module_output["recognized_intent"] = intent_name
        active_module_output["recognized_entities"] = detected_entities
        active_module["module_output"] = active_module_output
        api_output["user_utterance"] = user_utterance
        api_output["active_module"] = active_module
        api_output["modules_output"] = api_modules_output
        # print(api_output)

        return highest_module, intent_highest_activation, intent_name, entity_highest_activation, detected_entities, active_node, self.dialog_tree, api_output
        # return highest_module, intent_highest_activation, intent_name, entity_highest_activation, entity_name, active_node, self.dialog_tree, api_output
