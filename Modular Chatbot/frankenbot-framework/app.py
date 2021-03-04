import flask
import logging
import sys
import uuid
import webbrowser
import json
from flask_cors import CORS
from flask import jsonify, request, render_template
from frankenbot.console_chat_interface import ConsoleChatInterface
from frankenbot.persistence.json.json_unserializer import JSONUnserializer
from frankenbot.session.session import InMemorySession
from web_api import WebApi

app = flask.Flask(__name__)
app.config["DEBUG"] = False
log = logging.getLogger('werkzeug')
log.setLevel(logging.ERROR)
log.disabled = True
CORS(app)

#Create and configure logger
logging.basicConfig(filename="Logs/LogFile.log", format='%(asctime)s %(message)s', filemode='w')

#Creating an object
logger=logging.getLogger()

#Setting the threshold of logger to DEBUG
logger.setLevel(logging.INFO)

new_session = InMemorySession()

# print('Number of arguments:', len(sys.argv), 'arguments.')
# print('Argument List:', str(sys.argv[1]))
bot_directory = str(sys.argv[1])
# frontend_dir = "../chat-frontend-web/index.html"


def initialize_bot():
    bot_def = dict()
    bot_def["bot_directory"] = "bot_def/"+bot_directory+"/frankenbot.json"
    bot_def["training_data_directory"] = "bot_def/" + bot_directory + "/rasa/"
    bot_def["model_directory"] = "rasa_nlu/intent_categorization_example_rasa/projects/"
    unserializer = JSONUnserializer()
    bot = unserializer.load_from_file(bot_def)
    return bot


def render_again(utterance, active_state_nodes, session, session_id):
    bot = initialize_bot()
    chat = ConsoleChatInterface(bot)
    active_node, module_id, final_api_output = chat.start_chat(utterance, active_state_nodes, session, session_id)
    return active_node, module_id, final_api_output


def add_user_and_start_chat(web_api, chat_interface, active_state_nodes, key_session_id, session_id, last_active_node, active_bot, active_module_id, user_utterance):
    new_user_session = web_api.add_new_session(key_session_id, session_id, last_active_node, active_bot, active_module_id)
    final_api_output = chat_interface.start_chat(user_utterance, active_state_nodes, last_active_node, active_module_id, new_user_session, session_id)
    return final_api_output


@app.route('/', methods=['GET'])
def home():
    utterance = request.args.get('utterance')
    user_session_id = request.args.get('user_session_id')
    key_session_id = "session_id"
    web_api = WebApi(utterance, new_session)
    active_module_node = dict()
    last_active_node = None
    # check for last active state for a user
    active_state_nodes, active_bot, active_module_id = web_api.get_user_active_state(key_session_id, user_session_id)
    if active_state_nodes:
        if active_module_id in active_state_nodes:
            last_active_node = active_state_nodes[active_module_id]

    if not active_bot:
        bot = initialize_bot()
    else:
        bot = active_bot

    chat = ConsoleChatInterface(bot)

    if not utterance or user_session_id == "null":
        if user_session_id == "null":    # for new user coming first time
            session_id = str(uuid.uuid4())
            final_api_output = add_user_and_start_chat(web_api, chat, active_state_nodes, key_session_id, session_id, last_active_node, bot, active_module_id, '')
            logger.info(json.dumps(final_api_output))
        else:   # if user open new tab while old tab is also open i.e. session exist
            session_id = user_session_id
            if not web_api.user_session_exist(key_session_id, session_id):
                final_api_output = add_user_and_start_chat(web_api, chat, active_state_nodes, key_session_id, session_id, last_active_node, bot, active_module_id, '')
                logger.info(json.dumps(final_api_output))

            else:   # for last user utterance as first bot message
                last_user_utterance = web_api.get_last_user_utterance(key_session_id, user_session_id)
                if not last_user_utterance:  # if user open new tab without any chat in old tab
                    final_api_output = chat.start_chat('', active_state_nodes, last_active_node, active_module_id, new_session, session_id)
                    logger.info(json.dumps(final_api_output))
                else:
                    active_node, module_id, final_api_output = chat.start_chat(last_user_utterance, active_state_nodes, last_active_node, active_module_id, new_session, session_id)
                    logger.info(json.dumps(final_api_output))
    else:

        session_id = user_session_id
        active_node, module_id, final_api_output = chat.start_chat(utterance,  active_state_nodes, last_active_node, active_module_id, new_session, session_id)

        if active_node and module_id and active_bot:
            last_active_node = active_node
            active_module_id = module_id
            active_user_bot = active_bot

        if web_api.user_session_exist(key_session_id, session_id):
            web_api.update_last_user_utterance(key_session_id, user_session_id)
            if active_node and module_id and active_bot:
                web_api.update_user_active_state(key_session_id, user_session_id, active_node, active_bot, module_id)

        logger.info(json.dumps(final_api_output))

    # if last_active_node:
    #     web_api.print_active_tree(last_active_node)

    chatbot_output ={"chatbot_output": final_api_output}
    user_session_id = {str(key_session_id): session_id}
    final_api_output = web_api.finalize_api_output(chatbot_output, user_session_id)

    return jsonify(final_api_output)

if __name__ == "__main__":
    # url = "../chat-frontend-web/index.html"
    url = "file://D:/TUB Masters/Theses/modular-chatbot/chat-frontend-web/index.html"
    webbrowser.open_new(url)
    # 'http://127.0.0.1:5000/'
    # Timer(1, open_browser).start();
    # render_template(frontend_dir)
    app.run()

