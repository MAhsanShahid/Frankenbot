# Parent class for all intent detectors. Dont instantiate this class but rather a subclass. All subclasses must override method detect(self, utterance)
# for abstract class
from abc import ABC
# from rasa_nlu.training_data  import load_data
# from rasa_nlu.config import RasaNLUModelConfig
# from rasa_nlu.model import Trainer, Interpreter
# from rasa_nlu import config


class IntentAndEntityDetector(ABC):

    # returns a map from intents -> scores between 0 and 1
    # def detect_rasa_intent(self, utterance, rasa_interpreter, session):
    #     pass

    def detect(self, utterance, rasa_interpreter, session):
        pass
        # raise Exception("The intent detector should override the method get_intents")


# An intent detector that detects intents when keywords are present. This intent detector is language agnostic.
# Initialize like this:
# nlu_def = {
#     "type": "keyword_based_intent_detector",
#     "intents": {
#         "#greeting": ["hello", "hi", "good morning"],
#         "#confirm_yes": ["yes", "yo", "jo"]
#     }
# }
# kid = KeywordIntentDetector(nlu_def)

class RasaIntentAndEntityDetector(IntentAndEntityDetector):

    intents = []
    entities = []

    def __init__(self, intents, entities):
        self.intents = intents
        self.entities = entities

    # def detect(self, utterance, session):
    #
    #     intents = {}
    #     utterance = utterance.lower()
    #
    #     for keyword in self.keywords_to_intents:
    #         if utterance.find(keyword) >= 0:
    #             # print(keyword)
    #             intent_list = self.keywords_to_intents[keyword]
    #             for intent in intent_list:
    #                 intents[intent] = 1
    #                 # print(intents)
    #
    #     return intents
    # ____________________
    # def detect_rasa_intent(self, utterance, rasa_interpreter, session):
    #     # intents = dict()
    #     utterance = utterance.lower()
    #     rasa_intents = rasa_interpreter.parse(utterance)
    #     print("rasa worked: ", rasa_intents)
    #     return rasa_intents

    def detect(self, utterance, rasa_interpreter, session):

        intents = dict()
        # rasa_intent = dict()
        entities = []
        utterance = utterance.lower()
        # print(utterance)
        rasa_result = rasa_interpreter.parse(utterance)
        # print(rasa_result)

        # print(self.intents, self.entities)

        for intent in self.intents:
            if intent == rasa_result["intent"]["name"]:
                intents[intent] = rasa_result["intent"]["confidence"]
        # print(intents)

        # # print(rasa_intents)
        # # rasa_intent[rasa_intents["intent"]["name"]] = rasa_intents["intent"]["confidence"]
        # # print("rs", rasa_intent)
        # for keyword in self.keywords_to_intents:
        #     # print(self.keywords_to_intents[keyword][0]["intent"], rasa_intents["intent"]["name"])
        #     if self.keywords_to_intents[keyword][0]["intent"] == rasa_result["intent"]["name"]:
        #         # print(self.keywords_to_intents[keyword][0]["intent"], rasa_intents["intent"]["name"])
        #         intent_list = self.keywords_to_intents[keyword]
        #         # print(intent_list)
        #         for intent in intent_list:
        #             # rasa_intent[rasa_intents["intent"]["name"]] = rasa_intents["intent"]["confidence"]
        #             # print("here", intent)
        #             intents[intent["intent"]] = rasa_result["intent"]["confidence"]
        #             # print("here", intents)
        #             break
        #     break

        # for entity in self.entities:
        for each_entity in rasa_result["entities"]:
            # if entity == each_entity["entity"]:
            recognized_entities = dict()
            recognized_entities[each_entity["entity"]] = each_entity["confidence"]
            recognized_entities["value"] = each_entity["value"]
            recognized_entities["start_index"] = each_entity["start"]
            recognized_entities["end_index"] = each_entity["end"]
            # print(recognized_entities)
            entities.append(recognized_entities)
        # print("entities", entities)


        # for keyword in self.keywords_to_entities:
        #     # print(keyword)
        #     # print(self.keywords_to_entities)
        #     # print(keyword, utterance)
        #     # print("here")
        #     # entity_start_index = utterance.find(keyword)
        #     for each_entity in rasa_result["entities"]:
        #         # print("here", self.keywords_to_entities[keyword][0], each_entity["entity"])
        #         # if self.keywords_to_entities[keyword][0] == each_entity["entity"]:
        #         if keyword == each_entity["value"]:
        #             # print(self.keywords_to_entities[keyword][0], each_entity["value"])
        #             recognized_entities = dict()
        #             recognized_entities[each_entity["entity"]] = each_entity["confidence"]
        #             recognized_entities["value"] = each_entity["value"]
        #             recognized_entities["start_index"] = each_entity["start"]
        #             recognized_entities["end_index"] = each_entity["end"]
        #             # print(recognized_entities)
        #             entities.append(recognized_entities)
        #             # # or keyword.find(utterance) >= 0
        #             # # print(utterance)
        #             # # intent_list = self.keywords_to_intents[keyword]
        #             # entity_list = self.keywords_to_entities[keyword]
        #             # # print(entity_list)
        #             # # for intent in intent_list:
        #             # #     intents[intent] = 1
        #             # for entity in entity_list:
        #             #     recognized_entities = dict()
        #             #     recognized_entities[entity] = 1
        #             #     recognized_entities["value"] = keyword
        #             #     recognized_entities["start_index"] = entity_start_index
        #             #     recognized_entities["end_index"] = entity_start_index + len(keyword)-1
        #             #     # print(recognized_entities)
        #             #     entities.append(recognized_entities)


        # for keyword in self.keywords_to_intents:
        #     # print(self.keywords_to_intents)
        #     # print(keyword)
        #     # print("here")
        #     if utterance.find(keyword) >= 0:
        #         # or keyword.find(utterance) >= 0
        #         print("kti:", self.keywords_to_intents)
        #         intent_list = self.keywords_to_intents[keyword]
        #         # entity_list = self.keywords_to_entities[keyword]
        #         print(intent_list)
        #         for intent in intent_list:
        #             print("here", intent)
        #             intents[intent["intent"]] = intent["activation_value"]
        #             print("here", intents)
        #             # intents[intent["intent"]] = intent["activation_value"]
        #             # intents[intent] = 1
        #         # for entity in entity_list:
        #         #     entities[entity] = 1
        #         # print(intents, entities)
        # # print(entities)
        # # intents = rasa_interpreter.parse(utterance)
        # # print("rasa worked: ", intents)

        # for keyword in self.keywords_to_entities:
        #     print(self.keywords_to_entities)
        #     # print(keyword, utterance)
        #     # print("here")
        #     entity_start_index = utterance.find(keyword)
        #     if entity_start_index >= 0:
        #         # or keyword.find(utterance) >= 0
        #         # print(utterance)
        #         # intent_list = self.keywords_to_intents[keyword]
        #         entity_list = self.keywords_to_entities[keyword]
        #         # print(entity_list)
        #         # for intent in intent_list:
        #         #     intents[intent] = 1
        #         for entity in entity_list:
        #             recognized_entities = dict()
        #             recognized_entities[entity] = 1
        #             recognized_entities["value"] = keyword
        #             recognized_entities["start_index"] = entity_start_index
        #             recognized_entities["end_index"] = entity_start_index + len(keyword)-1
        #             # print(recognized_entities)
        #             entities.append(recognized_entities)

                # print(intents, entities)
        # print(intents, entities)
        # return intents, entities
        return intents, entities, rasa_result["intent_ranking"]

##class BOWIntentDetector:
#
#    def __init__(self, )