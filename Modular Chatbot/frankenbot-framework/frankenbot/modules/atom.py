from frankenbot.modules.modul import Modul


class Atom(Modul):

    nlu: None
    response_generator: None

    def __init__(self, nlu, response_generator):
        self.nlu = nlu
        self.response_generator = response_generator

    def create_api_output(self, utterance, module_id, intent_highest_activation, intent_name, detected_entities, intent_ranking):
        module_output = dict()
        api_module_output = dict()
        module_output["recognized_intent"] = intent_name
        module_output["recognized_entities"] = detected_entities
        module_output["intent_ranking"] = intent_ranking
        api_module_output["id"] = module_id
        api_module_output["type"] = "dialog_tree_module"
        api_module_output["activation_value"] = intent_highest_activation
        api_module_output["module_output"] = module_output
        return api_module_output

    def activation_function(self, utterance, rasa_interpreter, module_id, session):
        intents, entities, intent_ranking = self.nlu.detect(utterance, rasa_interpreter, session)
        intent_highest_activation = 0.0
        entity_highest_activation = 0.0
        intent_name = ""
        recognized_entities = []
        api_module_output = dict()

        for index, entity in enumerate(entities):
            if entity[next(iter(entity))] > entity_highest_activation:
                entities_names = dict()
                entities_names["entity"] = next(iter(entity))
                entities_names["value"] = entity["value"]
                entities_names["start_index"] = entity["start_index"]
                entities_names["end_index"] = entity["end_index"]
                recognized_entities.append(entities_names)
            if index == (len(entities) - 1):
                entity_highest_activation = entity[next(iter(entity))]

        for intent in intents:
            if intents[intent] > intent_highest_activation:
                intent_highest_activation = intents[intent]
                intent_name = intent
                api_module_output = self.create_api_output(utterance, module_id, intent_highest_activation, intent_name, recognized_entities, intent_ranking)
        return intent_highest_activation, intent_name, entity_highest_activation, recognized_entities, api_module_output

    def generate_response(self, detected_intent, detected_entities, session, session_id):
        chatbot_response = self.response_generator.generate_response(detected_intent, detected_entities, session, session_id)
        return chatbot_response