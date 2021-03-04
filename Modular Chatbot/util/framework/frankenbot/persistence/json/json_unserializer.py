# from frankenbot.response.response_generator import SimpleResponseGenerator
# from frankenbot.nlu.intent_detector import KeywordIntentDetector
# from frankenbot.modules.atom import Atom
# from frankenbot.dialogmanager.dialogmanager import MaxActivationDialogManager
# from frankenbot.bot import Bot
# import json
#
# class JSONUnserializer:
#
#     def load_from_file(self, filename):
#         f = open(filename, "r")
#         json_str = f.read()
#         bot_def = json.loads(json_str)
#         f.close()
#         return self.generate(bot_def)
#
#
#     def generate(self, json):
#
#         if not "type" in json:
#             raise Exception("The json object does not contain a type.")
#
#         t = json["type"]
#         if t == "simple_response_generator":
#             return self.generate_simple_response_generator(json)
#         elif t == "keyword_based_intent_detector":
#             return self.generate_keyword_nlu(json)
#         elif t == "atom":
#             answer_generator = self.generate(json["response_generator"])
#             nlu = self.generate(json["nlu"])
#             return Atom(nlu, answer_generator)
#         elif t == "max_activation_dialog_manager":
#             modules = []
#             for module_json in json["modules"]:
#                 module = self.generate(module_json)
#                 modules.append(module)
#             return MaxActivationDialogManager(modules)
#         elif t == "bot":
#             return self.generate_bot(json)
#         else:
#             raise Exception("Unknown type \"" + str(t) + "\"")
#
#     def generate_simple_response_generator(self, json):
#         if json["type"] != "simple_response_generator":
#             raise Exception("This response does not have the correct type.")
#         if "responses" not in json:
#             raise Exception("simple_response does not contain field \"responses\"")
#         if "mode" not in json:
#             raise Exception("simple_response does not contain field \"mode\"")
#
#         mode = None
#         if json["mode"] == "sequential":
#             mode = SimpleResponseGenerator.MODE_SEQUENTIAL
#         elif json["mode"] == "random":
#             mode = SimpleResponseGenerator.MODE_RANDOM
#         else:
#             raise Exception("invalid mode for simple_response: \"" + json["mode"] + "\"")
#
#         responses = json["responses"]
#         return SimpleResponseGenerator(responses,mode)
#
#     def generate_keyword_nlu(self, bot_def):
#
#         if bot_def["type"] != "keyword_based_intent_detector":
#             raise Exception("Invalid bot definition: type \"" + str(bot_def["type"]) + "\" does not match for KeywordIntentDetector")
#
#         if "intents" not in bot_def:
#             raise Exception("Cannot initialize KeywordIntentDetector: No field \"intents\" in the definition.")
#
#         keywords_to_intents = {}
#         for intent in bot_def["intents"]:
#             for keyword in bot_def["intents"][intent]:
#                 keyword = keyword.lower()
#                 if keyword not in keywords_to_intents:
#                     keywords_to_intents[keyword] = []
#                 keywords_to_intents[keyword].append(intent)
#         return KeywordIntentDetector(keywords_to_intents)
#
#     def generate_bot(self, json):
#
#         if not "dialog_manager" in json:
#             raise Exception("This bot does not have a field \"dialog_manager\"")
#
#         dialog_manager = self.generate(json["dialog_manager"])
#         bot = Bot(dialog_manager)
#
#         if "name" in json:
#             bot.name = json["name"]
#         if "welcome_message" in json:
#             bot.welcome_message = json["welcome_message"]
#         if "fallback_message" in json:
#             bot.fallback_message = json["fallback_message"]
#
#         return bot
# __________________

from frankenbot.response.response_generator import SimpleResponseGenerator
from frankenbot.nlu.intent_and_entity_detector import RasaIntentAndEntityDetector
from frankenbot.modules.atom import Atom
from frankenbot.dialogmanager.dialogmanager import MaxActivationDialogManager
from frankenbot.dialogmanager.dialogmanager import MaxActivationDialogTreeManager
from frankenbot.dialoguetree.dialog_tree_node import ModuleBasedDialogTreeNode
from frankenbot.dialoguetree.dialog_tree import ModuleBasedDialogTree
from frankenbot.bot import Bot
from anytree import Node, NodeMixin, RenderTree
import json


class JSONUnserializer:

    def load_from_file(self, filename):
        f = open(filename, "r")
        json_str = f.read()
        bot_def = json.loads(json_str)
        f.close()

        return self.generate(bot_def)

    def generate(self, json):

        if not "type" in json:
            raise Exception("The json object does not contain a type.")

        t = json["type"]
        if t == "simple_response_generator":
            return self.generate_simple_response_generator(json)

        elif t == "tree_node":
            answer_generator = self.generate(json["response_generator"])
            nlu = self.generate_keyword_nlu(json)
            return Atom(nlu, answer_generator)

        elif t == "max_activation_dialog_manager":
            trees = dict()
            for module_json in json["modules"]:
                tree = self.generate(module_json)
                trees.update(tree)
            dialog_tree = ModuleBasedDialogTree(trees)
            if dialog_tree:
                #     dialog_tree.print_tree()
                return MaxActivationDialogTreeManager(dialog_tree)
            else:
                raise Exception("Check with dialogue manager or tree in json unserializer max activation dialog managaer." )

        elif t == "dialog_tree":
            tree=dict()
            tree[json["module_id"]] = []
            for tree_json in json["dialog_tree"]:
                node_module = self.generate(tree_json)
                tree_node = self.generate_node(tree_json, node_module)
                if tree_node:
                    tree[json["module_id"]].append(tree_node)
            return tree

        elif t == "bot":
            return self.generate_bot(json)

        else:
            raise Exception("Unknown type \"" + str(t) + "\"")

    def generate_simple_response_generator(self, json):
        if json["type"] != "simple_response_generator":
            raise Exception("This response does not have the correct type.")
        if "responses" not in json:
            raise Exception("simple_response does not contain field \"responses\"")
        if "mode" not in json:
            raise Exception("simple_response does not contain field \"mode\"")

        mode = None
        if json["mode"] == "sequential":
            mode = SimpleResponseGenerator.MODE_SEQUENTIAL
        elif json["mode"] == "random":
            mode = SimpleResponseGenerator.MODE_RANDOM
        else:
            raise Exception("invalid mode for simple_response: \"" + json["mode"] + "\"")

        responses = json["responses"]
        return SimpleResponseGenerator(responses, mode)

    def generate_node(self, json, node_module):
        return ModuleBasedDialogTreeNode(json["node_id"],json["parent_node"], json["intent_name"], json["response_generator"], node_module)

    def generate_keyword_nlu(self, bot_def):
        if "intent_name" not in bot_def:
            raise Exception("Cannot initialize IntentAndEntityDetector: No field \"intent_name \" in the definition.")

        intents = []
        entities = []

        if bot_def["intent_name"] not in intents:
            intents.append(bot_def["intent_name"])

        # for entity in bot_def["entities"]:
        #     if entity not in entities:
        #         entities.append(entity)

        return RasaIntentAndEntityDetector(intents, entities)

    def generate_bot(self, json):

        if not "dialog_manager" in json:
            raise Exception("This bot does not have a field \"dialog_manager\"")

        dialog_tree_manager = self.generate(json["dialog_manager"])

        bot = Bot(dialog_tree_manager)

        if "name" in json:
            bot.name = json["name"]
        if "welcome_message" in json:
            bot.welcome_message = json["welcome_message"]
        if "fallback_message" in json:
            bot.fallback_message = json["fallback_message"]

        return bot
