# for abstract class
from abc import ABC
import hashlib
import json
import random
import time

# The session is a place to store variables that are only visible for the current user. So each
# user has their own session. Users can access their own sessions only, sessions of other users
# are invisible.
#
# The session has two parts to store variables: context variables can be defined by the chatbot
# author. For example we can store extracted entities. On the other side, session variables can be
# used by the programmer, e.g. to store the history of activated modules. The chatbot designer does
# not see the session variables.


# all session and context variables are strings
class Session(ABC):

    def get_session_var(self, key, value):
        pass
        # raise Exception("Abstract function")

    def set_session_var(self, session_id_obj, active_node, active_bot, active_module_id, utterance):
        pass
        # raise Exception("Abstract function")

    def get_context_var(self, key):
        pass
        # raise Exception("Abstract function")

    def set_context_var(self, context):
        pass

    def get_user_session_active_state(self, key, value):
        pass

    def update_user_session_active_state(self, key, value, active_node, active_bot, module_id):
        pass

    def session_exist(self, key, value):
        pass
        # raise Exception("Abstract function")

    def get_last_user_utterance(self, key, value):
        pass

    def update_last_user_utterance(self, key, value, utterance):
        pass

    def set_sequential_response_counter(self, user_session_id, intent, count):
        pass

    def get_sequential_response_counter(self, user_session_id, intent):
        pass

    def update_sequential_response_counter(self, user_session_id, intent, count):
        pass


class InMemorySession(Session):
    session_vars = []
    context_vars = dict()
    sequential_response_counter = []

    def get_session_var(self, key, value):
        for each in self.session_vars:
            if each[key] == value:
                return each[key]
        return None

    def set_session_var(self, session_id_obj, active_node, active_bot, module_id, last_user_utterance):
        new_session = dict()
        # new_session["active_node"] = active_node
        new_session["active_nodes"] = dict()
        if module_id and active_node:
            new_session["active_nodes"][module_id] = active_node
        new_session["active_bot"] = active_bot
        new_session["active_module_id"] = module_id
        new_session["last_user_utterance"] = last_user_utterance
        new_session.update(session_id_obj)
        self.session_vars.append(new_session)

    def get_context_var(self, key):
        if key not in self.context_vars:
            return None
        return self.context_vars[key]

    def set_context_var(self, context):
        self.context_vars.update(context)

    def session_exist(self, key, value):
        for each in self.session_vars:
            if each[key] == value:
                return True
        return False

    def get_user_session_active_state(self, key, value):
        for each in self.session_vars:
            if each[key] == value:
                return each["active_nodes"], each["active_bot"], each["active_module_id"]
        return None, None, None

    def update_user_session_active_state(self, key, value, active_node, active_bot, module_id):
        for each in self.session_vars:
            if each[key] == value:
                each["active_nodes"][module_id] = active_node
                each["active_bot"] = active_bot
                each["active_module_id"] = module_id

    def get_last_user_utterance(self, key, value):
        for each in self.session_vars:
            if each[key] == value:
                return each["last_user_utterance"]
        return None

    def update_last_user_utterance(self, key, value, utterance):
        for each in self.session_vars:
            if each[key] == value:
                each["last_user_utterance"] = utterance

    def set_sequential_response_counter(self, user_session_id, intent, count):
        counter = dict()
        counter[user_session_id] = {intent: count}
        self.sequential_response_counter.append(counter)

    def get_sequential_response_counter(self, user_session_id, intent):
        for each in self.sequential_response_counter:
            if user_session_id in each:
                if intent in each[user_session_id]:
                    return each[user_session_id][intent]
        return None

    def update_sequential_response_counter(self, user_session_id, intent, count):
        for each in self.sequential_response_counter:
            if user_session_id in each:
                if intent in each[user_session_id]:
                    each[user_session_id][intent] = count

    def print_session_vars(self):
        print(self.session_vars)

    def print_context_vars(self):
        print(self.context_vars)