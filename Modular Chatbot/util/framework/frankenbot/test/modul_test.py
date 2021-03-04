import unittest
from frankenbot.modules.atom import Atom
from frankenbot.persistence.json.json_unserializer import JSONUnserializer
from frankenbot.session.session import InMemorySession

class TestAtom(unittest.TestCase):

    def test(self):

        json = {
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
        }
        unserializer = JSONUnserializer()
        session = InMemorySession()
        
        atom = unserializer.generate(json)
        activation = atom.activation_function("hi schnuffi!", session)
        assert(activation == 1.0)
        activation = atom.activation_function("moin schnuffi!", session)
        assert(activation == 0.0)

        response = atom.generate_response(session)
        assert(response == "Hi")