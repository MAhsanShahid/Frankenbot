import flask
import logging
import sys
import os
import uuid
import webbrowser
import json
from flask_cors import CORS
from flask import jsonify, request
# from flask_session import Session
from frankenbot.bot import Bot
from frankenbot.console_chat_interface import ConsoleChatInterface
from frankenbot.persistence.json.json_unserializer import JSONUnserializer
from frankenbot.session.session import InMemorySession
from anytree import Node, NodeMixin, RenderTree
from frankenbot.dialoguetree.dialog_tree import ModuleBasedDialogTree
from frankenbot.dialogmanager.dialogmanager import MaxActivationDialogTreeManager

from rasa_nlu.training_data  import load_data
from rasa_nlu.config import RasaNLUModelConfig
from rasa_nlu.model import Trainer, Interpreter
from rasa_nlu import config


app = flask.Flask(__name__)
# app.secret_key = "abcd"
app.config["DEBUG"] = False
log = logging.getLogger('werkzeug')
log.setLevel(logging.ERROR)
log.disabled = True
# app.logger.disabled = True
# SESSION_TYPE = 'redis'
# app.config.from_object(__name__)
# Session(app)
CORS(app)

#Create and configure logger
logging.basicConfig(filename="LogFile.log", format='%(asctime)s %(message)s', filemode='w')

#Creating an object
logger=logging.getLogger()

#Setting the threshold of logger to DEBUG
logger.setLevel(logging.INFO)

#Test messages
# logger.debug("Harmless debug Message")
# logger.info("Just an information")
# logger.warning("Its a Warning")
# logger.error("Did you try to divide by zero")
# logger.critical("Internet is down")


train_data = load_data('rasa_nlu/rasa_dataset.json')
trainer = Trainer(config.load('rasa_nlu/config_spacy.yaml'))
# trainer = Trainer(config.load("../intent_categorization_example_rasa/config_spacy.yaml"))

# train the model and save it to a folder
trainer.train(train_data)
model_directory = trainer.persist('../intent_categorization_example_rasa/projects/')
print("trained")

# load the model in the interpreter
rasa_interpreter = Interpreter.load(model_directory)

new_session = InMemorySession()


def initialize_bot():
    bot_def = "bot_def/sampleBot.json"
    unserializer = JSONUnserializer()
    bot = unserializer.load_from_file(bot_def)
    return bot


def render_again(utterance, session, session_id):
    bot = initialize_bot()
    chat = ConsoleChatInterface(bot)
    active_node, final_api_output = chat.start_chat(utterance, rasa_interpreter, session, session_id)
    return active_node, final_api_output


# print(bot.welcome_message)
    # utterance = request.args.get('utterance')
    # user_session_id = request.args.get('user_session_id')
    # print(user_session_id, "empty")
    # run two utterances through the interpreter
    # rasa_intent=""
# def rasa_nlu(utterance):
#     nlu_result = rasa_interpreter.parse(utterance)
#     print("utterance: " + utterance)
#     print("nlu result: " + str(nlu_result))
#     print("rasa intent: " + nlu_result["intent"]["name"])
#     return nlu_result
    # print("nlu result: " + str(nlu_result["intent"]["name"]) + str( nlu_result["entities"]))
    # return nlu_result["intent"], nlu_result["entities"]
    #
    # # nlu(u"i'm looking for a place in the north of town")
    # # nlu(u"Good Morning World")
    # # nlu(u"vegg")

# active_node_state = None


@app.route('/', methods=['GET'])
def home():
    # global active_node_state
    utterance = request.args.get('utterance')
    user_session_id = request.args.get('user_session_id')
    # print("type:", type(user_session_id))
    key_session_id = "session_id"
    active_node_state = new_session.get_user_session_active_node(key_session_id, user_session_id)
    # last_user_utterance = new_session.get_last_user_utterance(key_session_id, user_session_id)
    active_tree_rendered = False

    if not active_node_state:
        bot = initialize_bot()
    else:
        active_bot = {
            "name": "Restaurant Assistant",
            "welcome_message": "Hi, welcome to restaurant assistant.",
            "fallback_message": "I did not understand you.",
            "dialog_tree": {
                "topic_tree": []
            }
        }
        for pre, _, c_node in RenderTree(active_node_state):
            active_bot["dialog_tree"]["topic_tree"].append(c_node)

        if len(active_bot["dialog_tree"]["topic_tree"]) <= 1:
            bot = initialize_bot()
        else:
            active_tree_rendered = True
            active_tree_state = ModuleBasedDialogTree(active_bot["dialog_tree"])
            active_tree_dialog_manager = MaxActivationDialogTreeManager(active_tree_state)
            bot = Bot(active_tree_dialog_manager)
            if "name" in active_bot:
                bot.name = active_bot["name"]
            if "welcome_message" in active_bot:
                bot.welcome_message = active_bot["welcome_message"]
            if "fallback_message" in active_bot:
                bot.fallback_message = active_bot["fallback_message"]

    chat = ConsoleChatInterface(bot)

    if not utterance or user_session_id == "null":
        if user_session_id == "null":    # for new user coming first time
            session_id = str(uuid.uuid4())
            session_id_obj = {key_session_id: session_id}
            new_session.set_session_var(session_id_obj, active_node_state, '')
            final_api_output = chat.start_chat('', rasa_interpreter, new_session, session_id)
            logger.info(json.dumps(final_api_output))
        else:   # if user open new tab while old tab is also open i.e. session exist
            session_id = user_session_id
            if not new_session.session_exist(key_session_id, session_id):  # if service restarted but user session exists
                session_id_obj = {key_session_id: session_id}
                new_session.set_session_var(session_id_obj, active_node_state, '')
                final_api_output = chat.start_chat('', rasa_interpreter, new_session, session_id)
                logger.info(json.dumps(final_api_output))
            else:   # for last user utterance as first bot message
                last_user_utterance = new_session.get_last_user_utterance(key_session_id, user_session_id)
                if not last_user_utterance:  # if user open new tab without any chat in old tab
                    final_api_output = chat.start_chat('', rasa_interpreter, new_session, session_id)
                    logger.info(json.dumps(final_api_output))
                else:
                    active_node, final_api_output = chat.start_chat(last_user_utterance, rasa_interpreter, new_session, session_id)
                    logger.info(json.dumps(final_api_output))
                    # logger.info("Bot: " + bot_msg)
    else:
        # rasa_nlu_result = rasa_nlu(utterance)
        # rasa_intent = rasa_nlu_result["intent"]
        # print(rasa_entities["entity"])

        session_id = user_session_id
        active_node, final_api_output = chat.start_chat(utterance, rasa_interpreter, new_session, session_id)
        bot_msg = final_api_output["chatbot_utterance"]["response"]

        if active_tree_rendered and bot_msg == bot.fallback_message:
            active_node, final_api_output = render_again(utterance, new_session, session_id)
        if active_node:
            print(active_node)
            active_node_state = active_node

        # if not new_session.session_exist(key_session_id, session_id):
        #     new_session.set_session_var(session_id_obj, active_node_state)
        #     print(new_session.session_vars)
        #     # new_session.set_session_var(session_id_obj, active_node_obj)
        # else:
        # if new_session.session_exist(key_session_id, session_id) and active_node:
        if new_session.session_exist(key_session_id, session_id):
            new_session.update_last_user_utterance(key_session_id, user_session_id, utterance)
            # last_user_utterance = new_session.get_last_user_utterance(key_session_id, user_session_id)
            if active_node:
                new_session.update_user_session_active_node(key_session_id, user_session_id, active_node)
                # print(new_session.session_vars)
                active_node_state = new_session.get_user_session_active_node(key_session_id, user_session_id)
                # print("updated", active_node)

        logger.info(json.dumps(final_api_output))
        # logger.info("user: " + utterance)
        # logger.info("Bot: " + bot_msg)

    if active_node_state:
        for pre, _, c_node in RenderTree(active_node_state):
            tree_str = u"%s%s" % (pre, c_node.intent_name)
            print(tree_str.ljust(8), c_node.parent_node)
    chatbot_output ={"chatbot_output": final_api_output}
    user_session_id = {str(key_session_id): session_id}
    api_output = dict()
    api_output.update(chatbot_output)
    api_output.update(user_session_id)
    return jsonify(api_output)

if __name__ == "__main__":
    # url = "../../frontend/index.html"
    url = "file://D:/TUB Masters/Theses/modular-chatbot/chat-frontend-web/index.html"
    webbrowser.open_new(url)
    # 'http://127.0.0.1:5000/'
    # Timer(1, open_browser).start();
    app.run()

