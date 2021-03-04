# for abstract class
from abc import ABC

class Modul(ABC):

    # def intent_activation_function(self, utterance, rasa_interpreter, node_id, session):
    #     pass

    def activation_function(self, utterance, rasa_interpreter, node_id, session):
        pass
        # raise Exception("This modul does not implement the activation_function.")

    def create_api_output(self, utterance, node_id, intent_highest_activation, intent_name, detected_entities):
        pass

    def generate_response(self, detected_intent, detected_entities, session, session_id):
        pass
