# for abstract class
from abc import ABC
import random
import re

class ResponseGenerator(ABC):

    def generate_response(self, detected_intent, detected_entities, session, session_id):
        pass
        # raise Exception("Please override generate_response function.")

    def create_api_output(self, answer):
        pass

class SimpleResponseGenerator(ResponseGenerator):

    MODE_SEQUENTIAL = 1
    MODE_RANDOM = 2
    mode = MODE_SEQUENTIAL
    responses = []

    def __init__(self, responses, mode):
        self.mode = mode
        self.responses = responses

    def create_api_output(self, answer):
        chatbot_response = dict()
        chatbot_response["type"] = "simple_response"
        chatbot_response["response"] = answer
        return chatbot_response

    def generate_response(self, detected_intent, detected_entities, session, session_id):
        if self.mode == self.MODE_SEQUENTIAL:
            answer_counter = session.get_sequential_response_counter(session_id, detected_intent)
            # print(answer_counter)
            if detected_intent and answer_counter is None:
                answer_counter = 0
                session.set_sequential_response_counter(session_id, detected_intent, answer_counter+1)
                answer = self.responses[answer_counter % len(self.responses)]
            else:
                answer = self.responses[answer_counter % len(self.responses)]
                session.update_sequential_response_counter(session_id, detected_intent, answer_counter+1)
            for each_entity in detected_entities:
                answer = answer.replace(each_entity["entity"], each_entity["value"])
            if answer.find("@"):
                answer = re.sub('[@]\\S+ ', '', answer)

            context_var = session.get_context_var(session_id)
            if context_var:
                for each_context_entity in context_var:
                    answer = answer.replace(each_context_entity, context_var[each_context_entity])
                if answer.find("$"):
                    answer = re.sub('[$]\\S+ ', '', answer)
            else:
                answer = re.sub('[$]\\S+ ', '', answer)
            chatbot_response = self.create_api_output(answer)
            return chatbot_response
        elif self.mode == self.MODE_RANDOM:
            answer = self.responses[random.randrange(0, len(self.responses))]
            for each_entity in detected_entities:
                answer = answer.replace(each_entity["entity"], each_entity["value"])
            if answer.find("@"):
                answer = re.sub('[@]\\S+ ', '', answer)
            context_var = session.get_context_var(session_id)
            if context_var:
                for each_context_entity in context_var:
                    answer = answer.replace(each_context_entity, context_var[each_context_entity])
                if answer.find("$"):
                    answer = re.sub('[$]\\S+ ', '', answer)
            else:
                answer = re.sub('[$]\\S+ ', '', answer)

            chatbot_response = self.create_api_output(answer)
            return chatbot_response