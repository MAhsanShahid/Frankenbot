# # Load the Packages
# from rasa_nlu.training_data  import load_data
# from rasa_nlu.config import RasaNLUModelConfig
# from rasa_nlu.model import Trainer, Interpreter
# from rasa_nlu import config
#
# # loading training data and config
# train_data = load_data('rasa_dataset.json')
# trainer = Trainer(config.load("config_spacy.yaml"))
#
# # train the model and save it to a folder
# trainer.train(train_data)
# model_directory = trainer.persist('projects/')
#
# # load the model in the interpreter
# interpreter = Interpreter.load(model_directory)
#
# # run two utterances through the interpreter
# def nlu(utterance):
#     nlu_result = interpreter.parse(utterance)
#     print("utterance: " + utterance)
#     print("nlu result: " + str(nlu_result["intent"]))
#
# nlu(u"i'm looking for a place in the north of town")
# nlu(u"Good Morning World")
# nlu(u"vegg")

import flask
import json
import sys
import os
import webbrowser
import redis
from flask_cors import CORS
from flask import jsonify, request, session
from flask_session import Session
# from frankenbot.bot import Bot
# from frankenbot.console_chat_interface import ConsoleChatInterface
# from frankenbot.persistence.json.json_unserializer import JSONUnserializer

from rasa_nlu.training_data  import load_data
from rasa_nlu.config import RasaNLUModelConfig
from rasa_nlu.model import Trainer, Interpreter
from rasa_nlu import config

app = flask.Flask(__name__)
app.config["DEBUG"] = False
# SESSION_TYPE = 'redis'
# app.config.from_object(__name__)
# Session(app)
CORS(app)

train_data = load_data('../intent_categorization_example_rasa/rasa_dataset.json')
trainer = Trainer(config.load("../intent_categorization_example_rasa/config_spacy.yaml"))

# train the model and save it to a folder
trainer.train(train_data)
model_directory = trainer.persist('../intent_categorization_example_rasa/projects/')
print("trained")

# load the model in the interpreter
interpreter = Interpreter.load(model_directory)


@app.route('/', methods=['GET'])
def home():

    # unserializer = JSONUnserializer()
    # # simpleResponseGenerator object
    # bot = unserializer.load_from_file(bot_def)
    # chat = ConsoleChatInterface(bot)
    utterance = request.args.get('utterance')
    # run two utterances through the interpreter
    # rasa_intent=""
    bot_def = "bot_def/restaurantsearch.json"
    f = open(bot_def, "r")
    json_str = f.read()
    resp = json.loads(json_str)
    f.close()
    # print(resp)
    def get_rasa_response(rasa_intent, rasa_entities):
        for intent in resp["dialog_manager"]["modules"]:
            key=intent["nlu"]["intents"].keys()
            for intent_name in key:
                # print(intent_name)
                if rasa_intent["name"] == intent_name:
                    if rasa_entities:
                        entities = ""
                        for each in rasa_entities:
                            entities = entities + each["entity"] + " "
                        return intent["response_generator"]["responses"][0] + " ( #: " + str(
                            rasa_intent["name"]) + " )" + " ( @: " + entities + " )"
                    else:
                        return intent["response_generator"]["responses"][0] + " ( #: " + str(rasa_intent["name"]) + " )"
        return resp["fallback_message"]

    def nlu(utterance):

        nlu_result = interpreter.parse(utterance)
        print("utterance: " + str(utterance))
        print("nlu result: " + str(nlu_result))
        # print("nlu result: " + str(nlu_result["intent"]["name"]) + str( nlu_result["entities"]))

        return nlu_result["intent"], nlu_result["entities"]

    # nlu(u"i'm looking for a place in the north of town")
    # nlu(u"Good Morning World")
    # nlu(u"vegg")
    # nlu(utterance)
    if not utterance:
        bot_msg = resp["welcome_message"]
    else:
        rasa_intent, rasa_entities = nlu(utterance)
        bot_msg = get_rasa_response(rasa_intent, rasa_entities)
    #
    #     # print(rasa_entities["entity"])
    #     bot_msg = chat.start_chat(utterance, rasa_intent, rasa_entities)
    return jsonify(bot_msg)
# "<h1>Frankenbot</h1><p>Test API</p>"
# @app.route('/set/')
# def set():
#     session['key'] = 'value'
#     return 'ok'
#
# @app.route('/get/')
# def get():
#     return session.get('key', 'not set')

# def open_browser():
#     webbrowser.open('http://127.0.0.1:5000/')


if __name__ == "__main__":
    webbrowser.open_new('http://127.0.0.1:5000/')
    # Timer(1, open_browser).start();
    app.run()

# app.run()