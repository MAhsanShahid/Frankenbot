import unittest
from frankenbot.bot import Bot
from frankenbot.persistence.json.json_unserializer import JSONUnserializer
from frankenbot.session.session import InMemorySession

class TestBot(unittest.TestCase):

    def test_bot(self):

        json = {
            "type": "bot",
            "name": "Werner",
            "welcome_message": "Hi I am Werner.",
            "fallback_message": "I did not understand you.",
            "strategy": {            
                "type": "max_activation_strategy",
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
                                "#greeting": ["hello", "hi", "good morning", "hey"]
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
        }
        unserializer = JSONUnserializer()
        bot = unserializer.generate(json)
        
        session = InMemorySession()
        response = bot.say("Hey you. How are you doing?", session)
        assert(response == "Hi")

        response = bot.say("Bye, it was nice talking to you.", session)
        assert(response == "Bye")
