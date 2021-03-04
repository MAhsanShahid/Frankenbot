import sys
# sys.path.remove('..')
# print(sys.path)
import uuid
import json
from frankenbot.bot import Bot
from frankenbot.session.session import InMemorySession


class ConsoleChatInterface:

    bot = None

    def __init__(self, bot):
        self.bot = bot
# # for console
#     def start_chat(self):
#         session = InMemorySession()
#         welcome = self.bot.get_welcome_message()
#         if welcome != None:
#             print("Bot: " + welcome)
#         while(True):
#             sys.stdout.write("User: ")
#             utterance = str(input())
#             answer, intent, entity = self.bot.say(utterance, session)
#             print("Bot: " + answer + " (" + intent + ")" + " (" + entity + ")")

# for web api
    def start_chat(self, utterance, rasa_interpreter, current_session, session_id):
        # session = InMemorySession()
        # session.set_session_var("session", uuid.uuid1().hex)
        # print(session.get_session_var("session"))
        # if(current_session):
        #     if(session.session_exist(current_session)):
        #         session.set_session_var("session", uuid.uuid1().hex)
        #         current_session = session.get_session_var("session")
        #         print(current_session)
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
            active_module["id"] = 0
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
            # return welcome
            # print("Bot: " + welcome)
        # while(True):
        #     sys.stdout.write("User: ")
        #     utterance = str(input())
        else:

            intent, active_node, final_api_output = self.bot.say(utterance, rasa_interpreter, current_session, session_id)
            # answer, intent, entity, active_node, final_api_output = self.bot.say(utterance, current_session)
            # answer, entity, active_node, final_api_output = self.bot.say(utterance, current_session)
            # context = {}
            # context["utterance"] = utterance
            # context["response"] = final_api_output["chatbot_utterance"]["response"]
            # context["intent"] = intent
            # # context["intent"] = final_api_output["active_module"]["module_output"]["recognized_intent"]
            # context["entities"] = entity
            # print(context)

            # context = json.dumps(final_api_output)
            # current_session.set_context_var(context)
            # current_session.print_session_vars()
            # current_session.print_context_vars()

            return active_node, final_api_output
            # if intent and entity:
            #     return active_node, final_api_output
            #     # return active_node, answer + " ( #: " + intent + " )" + " ( @: " + entity + " )"
            # elif intent:
            #     return active_node, answer + " ( #: " + intent + " )"
            # else:
            #     return active_node, answer
            # print("Bot: " + answer + " (" + intent + ")")
