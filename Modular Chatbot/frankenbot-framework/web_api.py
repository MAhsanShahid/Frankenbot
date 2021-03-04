
from anytree import RenderTree



class WebApi:
    utterance = None
    session = None

    def __init__(self, utterance, session):
        self.utterance = utterance
        self.session = session

    def add_new_session(self, session_key, session_value, active_node, active_bot, module_id):
        session_obj = {session_key: session_value}
        self.session.set_session_var(session_obj, active_node, active_bot, module_id, self.utterance)
        return self.session

    def user_session_exist(self, session_key, session_value):
        return self.session.session_exist(session_key, session_value)

    def get_last_user_utterance(self, session_key, session_value):
        return self.session.get_last_user_utterance(session_key, session_value)

    def update_last_user_utterance(self, session_key, session_value):
        self.session.update_last_user_utterance(session_key, session_value, self.utterance)

    def update_user_active_state(self, session_key, session_value, active_node, active_bot, module_id):
        self.session.update_user_session_active_state(session_key, session_value, active_node, active_bot, module_id)

    def get_user_active_state(self, session_key, session_value):
        active_node, active_bot, active_module_id = self.session.get_user_session_active_state(session_key, session_value)
        return active_node, active_bot, active_module_id

    def print_active_tree(self, active_node):
        for pre, _, c_node in RenderTree(active_node):
            tree_str = u"%s%s%s" % (pre, c_node.intent_name, c_node.node_id)
            print(tree_str.ljust(8), c_node.parent_node)

    def finalize_api_output(self, chatbot_output_obj, session_obj):
        final_api_output = dict()
        final_api_output.update(chatbot_output_obj)
        final_api_output.update(session_obj)
        return final_api_output
