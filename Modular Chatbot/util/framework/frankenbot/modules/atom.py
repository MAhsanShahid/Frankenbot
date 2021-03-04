from frankenbot.modules.modul import Modul
# from frankenbot.nlu.intent_detector import IntentDetector

class Atom(Modul):

    nlu: None
    response_generator: None

    def __init__(self, nlu, response_generator):
        self.nlu = nlu
        self.response_generator = response_generator

    def create_api_output(self, utterance, node_id, intent_highest_activation, intent_name, detected_entities, intent_ranking):

        # user_utterance = dict()
        module_output = dict()
        api_module_output = dict()

        # recognized_entities = []
        # user_utterance["text"] = utterance
        module_output["recognized_intent"] = intent_name
        module_output["recognized_entities"] = detected_entities
        module_output["intent_ranking"] = intent_ranking
        api_module_output["id"] = node_id
        api_module_output["type"] = "dialog_tree_module"
        api_module_output["activation_value"] = intent_highest_activation
        api_module_output["module_output"] = module_output
        # api_output["user_utterance"] = user_utterance
        # api_output["activation_value"] = intent_highest_activation
        # self.api_output["modules_output"] = modules_output
        return api_module_output

    # def intent_activation_function(self, utterance, rasa_interpreter, node_id, session):
    #     rasa_intents = self.nlu.detect_rasa_intent(utterance, rasa_interpreter, session)
    #     return rasa_intents

    def activation_function(self, utterance, rasa_interpreter, node_id, session):
        # intents = self.nlu.detect_rasa_intent(utterance, rasa_interpreter, session)
        intents, entities, intent_ranking = self.nlu.detect(utterance, rasa_interpreter, session)
        intent_highest_activation = 0.0
        entity_highest_activation = 0.0
        intent_name = ""
        # entities_name = ""
        recognized_entities = []
        api_module_output = dict()

        for index, entity in enumerate(entities):
            # print(entity[next(iter(entity))], len(entities))
            # print(index, entities[entity], len(entities))
            # detected_entity = entity[next(iter(entity))]
            # print(entity)
            if entity[next(iter(entity))] > entity_highest_activation:
                # print(entity[next(iter(entity))])
                # entities_name = entities_name + next(iter(entity)) + " "
                entities_names = dict()
                entities_names["entity"] = next(iter(entity))
                entities_names["value"] = entity["value"]
                entities_names["start_index"] = entity["start_index"]
                entities_names["end_index"] = entity["end_index"]
                # print(entity["value"])
                # entities_names.update(entity[0][1:])
                recognized_entities.append(entities_names)
                # print(recognized_entities)

            if index == (len(entities) - 1):
                entity_highest_activation = entity[next(iter(entity))]
                # print(entities_name)

        for intent in intents:
            # print(intents,intent)
            if intents[intent] > intent_highest_activation:
                intent_highest_activation = intents[intent]
                intent_name = intent
                # print(intent)
                api_module_output = self.create_api_output(utterance, node_id, intent_highest_activation, intent_name, recognized_entities, intent_ranking)

        # for intent in intents["intent_ranking"]:
        #     # print(intents,intent)
        #     if intent["confidence"] > intent_highest_activation:
        #         intent_highest_activation = intent["confidence"]
        #         intent_name = intent["name"]
        #         print(intent_name)
        #         api_module_output = self.create_api_output(utterance, node_id, intent_highest_activation, intent_name, recognized_entities)

        # intent_highest_activation = intents["intent"]["confidence"]
        # intent_name = intents["intent"]["name"]
        # # print(intent_name, intent_highest_activation)
        # api_module_output = self.create_api_output(utterance, node_id, intent_highest_activation, intent_name,
        #                                            recognized_entities)

        # print(entities)
        # return intent_highest_activation, intent_name, entity_highest_activation, entities_name, recognized_entities, api_module_output
        return intent_highest_activation, intent_name, entity_highest_activation, recognized_entities, api_module_output

    def generate_response(self, detected_intent, detected_entities, session, session_id):
        # answer, chatbot_response = self.response_generator.generate_response(session)
        chatbot_response = self.response_generator.generate_response(detected_intent, detected_entities, session, session_id)
        # return answer, chatbot_response
        return chatbot_response