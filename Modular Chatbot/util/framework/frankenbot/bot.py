from frankenbot.nlu.intent_and_entity_detector import RasaIntentAndEntityDetector
from anytree import Node, NodeMixin, RenderTree
from frankenbot.dialoguetree.dialog_tree import ModuleBasedDialogTree
from frankenbot.dialogmanager.dialogmanager import MaxActivationDialogTreeManager
import json

class Bot:

    dialog_tree_manager = None
    name = "Bot"
    welcome_message = None
    fallback_message = None
    active_node = None
    active_tree = None
    active_tree_manager = None

    def __init__(self, dialog_tree_manager):
        # print("bot called")
        self.dialog_tree_manager = dialog_tree_manager

    def say(self, utterance, rasa_interpreter, session, session_id):

        # module, intent_activation, intent, entity_activation, entity = self.dialog_manager.find_module(utterance, session)
        # print(self.active_node)
        # if not self.active_node:
        final_api_output = dict()
        module, intent_activation, intent, entity_activation, detected_entities, active_node, dialog_tree, api_output = self.dialog_tree_manager.find_node_module(utterance, rasa_interpreter, session)
        # print(detected_entities)
        if detected_entities:
            context_var = session.get_context_var(session_id)
            # print(context_var)
            if not context_var:
                context = dict()
                for each_entity in detected_entities:
                    context_entity = each_entity["entity"].replace("@", "$")
                    new_context_var = {context_entity: each_entity["value"]}
                    context.update(new_context_var)
                # print(context)
                session.set_context_var({session_id: context})
            else:
                if context_var:
                    for each_entity in detected_entities:
                        context_entity = each_entity["entity"].replace("@", "$")
                        new_context_var = {context_entity: each_entity["value"]}
                        context_var.update(new_context_var)
                    # print(session.get_context_var(session_id))



        # module, intent_activation, intent, entity_activation, entity, active_node, dialog_tree, api_output = self.dialog_tree_manager.find_node_module(utterance, session)
            # if active_node:
            #     self.active_node=active_node
            #     print(active_node)
        # else:
        #     module, intent_activation, intent, entity_activation, entity, active_node, dialog_tree = self.active_tree_manager.find_node_module(utterance, session)
        #     print(active_node)

        # print(intent)
        response = ""
        # print(api_output)
        if intent_activation <= 0.2:
        # if api_output["active_module"]["activation_value"] == 0.0:
            if self.fallback_message is not None:
                response = self.fallback_message
                chatbot_response = dict()
                chatbot_response["type"] = "simple_response"
                chatbot_response["response"] = response
                final_api_output["chatbot_utterance"] = chatbot_response
                api_output["active_module"]["module_output"]["recognized_intent"] = ""
                api_output["active_module"]["module_output"]["recognized_entities"] = []
                final_api_output.update(api_output)

        else:
            chatbot_response = module.generate_response(intent, detected_entities, session, session_id)
            # response, chatbot_response = module.generate_response(session)
            final_api_output["chatbot_utterance"] = chatbot_response
            final_api_output.update(api_output)
            # print(json.dumps(final_api_output))
            # print(final_api_output)
            # print("here"+response)
            # dialog_tree.print_tree()
            # active_node.print_tree()
            # for each_node in dialog_tree:
            # if active_node:
            #     # .intent_name == intent
            #     active_tree = {"active_tree": []}
            #     for pre, _, c_node in RenderTree(active_node):
            #         active_tree["active_tree"].append(c_node)
            #         treestr = u"%s%s" % (pre, c_node.intent_name)
            #         print(treestr.ljust(8), c_node.parent_node)
            #     print(active_tree)
            #     self.active_tree = ModuleBasedDialogTree(active_tree)
            #     self.active_tree_manager = MaxActivationDialogTreeManager(self.active_tree)

            # print(active_node.node_id)
        return intent, active_node, final_api_output
        # return response, intent, entity, active_node, final_api_output
        # return response, entity, active_node, final_api_output

    def get_welcome_message(self):
        return self.welcome_message

    def get_fallback_message(self):
        return self.fallback_message
