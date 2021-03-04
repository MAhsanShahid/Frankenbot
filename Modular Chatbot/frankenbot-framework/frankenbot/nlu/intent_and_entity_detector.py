# Parent class for all intent detectors. Dont instantiate this class but rather a subclass. All subclasses must override method detect(self, utterance)
# for abstract class
from abc import ABC


class IntentAndEntityDetector(ABC):

    # returns a map from intents -> scores between 0 and 1

    def detect(self, utterance, rasa_interpreter, session):
        pass
        # raise Exception("The intent detector should override the method get_intents")


class RasaIntentAndEntityDetector(IntentAndEntityDetector):

    intents = []
    entities = []

    def __init__(self, intents, entities):
        self.intents = intents
        self.entities = entities

    def detect(self, utterance, rasa_interpreter, session):
        intents = dict()
        entities = []
        rasa_result = rasa_interpreter.parse(utterance)
        # print(rasa_result)
        for intent in self.intents:
            if rasa_result["intent"]:
                if intent == rasa_result["intent"]["name"]:
                    intents[intent] = rasa_result["intent"]["confidence"]
        for each_entity in rasa_result["entities"]:
            recognized_entities = dict()
            recognized_entities[each_entity["entity"]] = each_entity["confidence"]
            recognized_entities["value"] = each_entity["value"]
            recognized_entities["start_index"] = each_entity["start"]
            recognized_entities["end_index"] = each_entity["end"]
            entities.append(recognized_entities)
        if "intent_ranking" not in rasa_result:
            intent_ranking = []
            return intents, entities, intent_ranking
        return intents, entities, rasa_result["intent_ranking"]