class ConsoleChatInterface:

    bot = None

    def __init__(self, bot):
        self.bot = bot

    def start_chat(self, utterance, active_state_nodes, last_active_node, active_module_id, current_session, session_id):
        user_utterance = dict()
        active_module = dict()
        active_module_output = dict()
        welcome = self.bot.get_welcome_message()
        if welcome is not None and not utterance:
            final_api_output = dict()
            welcome_api_output = dict()
            welcome_api_output["type"] = "simple_response"
            welcome_api_output["response"] = welcome
            user_utterance["text"] = utterance
            active_module["id"] = "welcome_message"
            active_module["type"] = "dialog_tree_module"
            active_module["activation_value"] = 0
            active_module_output["recognized_intent"] = ""
            active_module_output["recognized_entities"] = []
            active_module["module_output"] = active_module_output
            final_api_output["user_utterance"] = user_utterance
            final_api_output["active_module"] = active_module
            final_api_output["chatbot_utterance"] = welcome_api_output
            final_api_output["modules_output"] = []
            return final_api_output
        else:
            intent, active_node, module_id, final_api_output = self.bot.say(utterance, active_state_nodes, last_active_node, active_module_id, current_session, session_id)
            return active_node, module_id, final_api_output
