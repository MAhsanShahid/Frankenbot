import json
import sys
from frankenbot.bot import Bot
from frankenbot.console_chat_interface import ConsoleChatInterface
from frankenbot.persistence.json.json_unserializer import JSONUnserializer

bot_def = "bot_def/restaurantsearch.json"
unserializer = JSONUnserializer()
#simpleResponseGenerator object
bot = unserializer.load_from_file(bot_def)
chat = ConsoleChatInterface(bot)
chat.start_chat()
# 'show me chines restaurants in the north'
# ________________________________

# from rasa_nlu.training_data  import load_data
# from rasa_nlu.config import RasaNLUModelConfig
# from rasa_nlu.model import Trainer, Interpreter
# from rasa_nlu import config
#
# train_data = load_data('../intent_categorization_example_rasa/rasa_dataset.json')
# trainer = Trainer(config.load("../intent_categorization_example_rasa/config_spacy.yaml"))
#
# # train the model and save it to a folder
# trainer.train(train_data)
# model_directory = trainer.persist('../intent_categorization_example_rasa/projects/')
#
# # load the model in the interpreter
# interpreter = Interpreter.load(model_directory)
#
#
# # run two utterances through the interpreter
# def nlu(utterance):
#     nlu_result = interpreter.parse(utterance)
#     print("utterance: " + utterance)
#     print("nlu resutl: " + str(nlu_result["intent"]))
#
#
# nlu(u"i'm looking for a place in the north of town")
# nlu(u"Good Morning World")
# nlu(u"vegg")