from anytree import LevelOrderIter


class Bot:

    dialog_tree_manager = None
    name = "Bot"
    welcome_message = None
    fallback_message = None
    # active_node = None
    # active_tree = None
    # active_tree_manager = None

    def __init__(self, dialog_tree_manager):
        self.dialog_tree_manager = dialog_tree_manager

    def respond_with_fallback_message(self, final_api_output, api_output):
        if self.fallback_message is not None:
            response = self.fallback_message
            chatbot_response = dict()
            chatbot_response["type"] = "simple_response"
            chatbot_response["response"] = response
            final_api_output["chatbot_utterance"] = chatbot_response
            api_output["active_module"]["module_output"]["recognized_intent"] = ""
            api_output["active_module"]["module_output"]["recognized_entities"] = []
            final_api_output.update(api_output)
            return final_api_output

    def say(self, utterance, active_state_nodes, last_active_node, active_module_id, session, session_id):
        final_api_output = dict()
        highest_module, intent_activation, intent, entity_activation, detected_entities, active_node, module_id, highest_module_id, dialog_tree, api_output = self.dialog_tree_manager.find_node_module(utterance, active_state_nodes, session)
        if detected_entities:
            context_var = session.get_context_var(session_id)
            if not context_var:
                context = dict()
                for each_entity in detected_entities:
                    context_entity = each_entity["entity"].replace("@", "$")
                    new_context_var = {context_entity: each_entity["value"]}
                    context.update(new_context_var)
                session.set_context_var({session_id: context})
            else:
                if context_var:
                    for each_entity in detected_entities:
                        context_entity = each_entity["entity"].replace("@", "$")
                        new_context_var = {context_entity: each_entity["value"]}
                        context_var.update(new_context_var)
        if intent_activation <= 0.2 or not highest_module:
            intent = ""
            if not last_active_node:
            # if module_id not in active_state_nodes:
                for each_tree in dialog_tree.trees:
                    if "module_id" and "dialog_tree" and "training_data" and "rasa_interpreter" in each_tree:
                        if each_tree["module_id"] == highest_module_id:
                            for each_node in each_tree["dialog_tree"]:
                                if (not each_node.parent) and each_node.intent_name == "#anything_else":
                                    chatbot_response = each_node.module.generate_response(each_node.intent_name,
                                                        detected_entities, session, session_id)
                                    final_api_output["chatbot_utterance"] = chatbot_response
                                    final_api_output.update(api_output)
            elif last_active_node:
                anything_else_condition = False
                for each_child in LevelOrderIter(last_active_node):
                    if each_child.parent and each_child.parent.node_id == last_active_node.node_id and each_child.parent.activation_flag and each_child.intent_name == "#anything_else":
                        anything_else_condition = True
                        chatbot_response = each_child.module.generate_response(each_child.intent_name, detected_entities,
                                            session, session_id)
                        final_api_output["chatbot_utterance"] = chatbot_response
                        final_api_output.update(api_output)
                if not anything_else_condition:
                    final_api_output = self.respond_with_fallback_message(final_api_output, api_output)

            else:
                final_api_output = self.respond_with_fallback_message(final_api_output, api_output)


        else:
            chatbot_response = highest_module.generate_response(intent, detected_entities, session, session_id)
            final_api_output["chatbot_utterance"] = chatbot_response
            final_api_output.update(api_output)
        return intent, active_node, module_id, final_api_output

    def get_welcome_message(self):
        return self.welcome_message

    def get_fallback_message(self):
        return self.fallback_message

