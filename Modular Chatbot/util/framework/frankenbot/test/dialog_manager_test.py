import unittest
from frankenbot.dialogmanager.dialogmanager import MaxActivationDialogManager
from frankenbot.persistence.json.json_unserializer import JSONUnserializer
from frankenbot.session.session import InMemorySession

class TestMaxActivationDialogManager(unittest.TestCase):

    def test_max_activation_dialog_manager(self):

        json = {
            
            "type": "max_activation_dialog_manager",
            "modules": [
                {
                    "type": "atom",
                    "response_generator": {
                        "type": "simple_response_generator",
                        "mode": "sequential",
                        "responses": ["Hi"],
                    },
                    "nlu": {
                        "type": "keyword_based_intent_detector",
                        "intents": {
                            "#greeting": ["hello", "hi", "good morning"]
                        }
                    }
                },
                {
                    "type": "atom",
                    "response_generator": {
                        "type": "simple_response_generator",
                        "mode": "sequential",
                        "responses": ["Bye"],
                    },
                    "nlu": {
                        "type": "keyword_based_intent_detector",
                        "intents": {
                            "#bye": ["bye", "goodbye"]
                        }
                    }
                }
            ]
        }
        unserializer = JSONUnserializer()
        mas = unserializer.generate(json)
        session = InMemorySession()

        module, activation = mas.find_module("Hey you. How are you doing?", session)

        response = module.generate_response(session)
        assert(response == "Hi")

        module, activation = mas.find_module("Bye, it was nice talking to you.", session)
        response = module.generate_response(session)
        assert(response == "Bye")
