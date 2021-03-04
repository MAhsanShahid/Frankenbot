from frankenbot.response.response_generator import SimpleResponseGenerator
from frankenbot.nlu.intent_and_entity_detector import RasaIntentAndEntityDetector
from frankenbot.modules.atom import Atom
from frankenbot.dialogmanager.dialogmanager import MaxActivationDialogTreeManager
from frankenbot.dialoguetree.dialog_tree_node import ModuleBasedDialogTreeNode
from frankenbot.dialoguetree.dialog_tree import ModuleBasedDialogTree
from frankenbot.bot import Bot
import json

from rasa_nlu.training_data  import load_data
from rasa_nlu.model import Trainer, Interpreter
from rasa_nlu import config

class JSONUnserializer:

    def load_from_file(self, bot_directories):
        f = open(bot_directories["bot_directory"], "r")
        json_str = f.read()
        bot_def = json.loads(json_str)
        f.close()
        return self.generate(bot_def, bot_directories)

    def generate(self, bot_json, bot_directories):
        if "type" not in bot_json:
            raise Exception("The json object does not contain a type.")

        t = bot_json["type"]
        if t == "simple_response_generator":
            return self.generate_simple_response_generator(bot_json)

        elif t == "tree_node":
            answer_generator = self.generate(bot_json["response_generator"], bot_directories)
            nlu = self.generate_keyword_nlu(bot_json)
            return Atom(nlu, answer_generator)

        elif t == "max_activation_dialog_manager":
            trees = []
            for module_json in bot_json["modules"]:
                tree = self.generate(module_json, bot_directories)
                trees.append(tree)
            dialog_tree = ModuleBasedDialogTree(trees)
            if dialog_tree:
                return MaxActivationDialogTreeManager(dialog_tree)
            else:
                raise Exception("Check with dialogue manager or tree in json unserializer max activation dialog managaer." )

        elif t == "dialog_tree":
            tree=dict()
            tree["module_id"] = bot_json["module_id"]
            tree["dialog_tree"] = []
            tree["training_data"], tree["rasa_interpreter"] = self.generate_rasa_training_data_and_interpreter(bot_directories, bot_json["module_id"])
            for tree_json in bot_json["dialog_tree"]:
                node_module = self.generate(tree_json, bot_directories)
                tree_node = self.generate_node(tree_json, node_module)
                if tree_node:
                    tree["dialog_tree"].append(tree_node)
            return tree

        elif t == "bot":
            return self.generate_bot(bot_json, bot_directories)

        else:
            raise Exception("Unknown type \"" + str(t) + "\"")

    def generate_rasa_training_data_and_interpreter(self, bot_directories, module_id):
        training_data_dir = bot_directories["training_data_directory"] + module_id + ".json"
        config_file_dir = bot_directories["training_data_directory"] + 'config_spacy.yaml'
        model_dir = bot_directories["model_directory"]
        training_data = load_data(training_data_dir)
        trainer = Trainer(config.load(config_file_dir))
        # train the model and save it to a folder
        trainer.train(training_data)
        model_directory = trainer.persist(model_dir)
        print("trained model for module '" + module_id + "'")
        rasa_interpreter = Interpreter.load(model_directory)
        return training_data, rasa_interpreter

    def generate_simple_response_generator(self, response_json):
        if response_json["type"] != "simple_response_generator":
            raise Exception("This response does not have the correct type.")
        if "responses" not in response_json:
            raise Exception("simple_response does not contain field \"responses\"")
        if "mode" not in response_json:
            raise Exception("simple_response does not contain field \"mode\"")
        mode = None
        if response_json["mode"] == "sequential":
            mode = SimpleResponseGenerator.MODE_SEQUENTIAL
        elif response_json["mode"] == "random":
            mode = SimpleResponseGenerator.MODE_RANDOM
        else:
            raise Exception("invalid mode for simple_response: \"" + response_json["mode"] + "\"")
        responses = response_json["responses"]
        return SimpleResponseGenerator(responses, mode)

    def generate_node(self, node_json, node_module):
        anything_else_condition = False
        if "anything_else_condition" in node_json:
            anything_else_condition = node_json["anything_else_condition"]
        return ModuleBasedDialogTreeNode(node_json["node_id"], node_json["parent_node"], node_json["intent_name"], anything_else_condition, node_json["response_generator"], node_module)

    def generate_keyword_nlu(self, bot_def):
        if "intent_name" not in bot_def:
            raise Exception("Cannot initialize IntentAndEntityDetector: No field \"intent_name \" in the definition.")
        intents = []
        entities = []
        if bot_def["intent_name"] not in intents:
            intents.append(bot_def["intent_name"])
        return RasaIntentAndEntityDetector(intents, entities)

    def generate_bot(self, bot_json, train_data_directory):
        if "dialog_manager" not in bot_json:
            raise Exception("This bot does not have a field \"dialog_manager\"")
        dialog_tree_manager = self.generate(bot_json["dialog_manager"], train_data_directory)
        bot = Bot(dialog_tree_manager)
        if "name" in bot_json:
            bot.name = bot_json["name"]
        if "welcome_message" in bot_json:
            bot.welcome_message = bot_json["welcome_message"]
        if "fallback_message" in bot_json:
            bot.fallback_message = bot_json["fallback_message"]
        return bot
