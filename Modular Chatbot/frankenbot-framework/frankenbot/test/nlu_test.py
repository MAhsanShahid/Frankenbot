import unittest
from frankenbot.nlu.intent_detector import IntentDetector, KeywordIntentDetector
from frankenbot.persistence.json.json_unserializer import JSONUnserializer
from frankenbot.session.session import InMemorySession

class TestKeywordIntentDetector(unittest.TestCase):

    def test_intent_detector(self):

        nlu_def = {
            "type": "keyword_based_intent_detector",
            "intents": {
                "#greeting": ["hello", "hi", "good morning"],
                "#confirm_yes": ["yes", "yo", "jo"]
            }
        }

        json_unserializer = JSONUnserializer()
        kid = json_unserializer.generate(nlu_def)
        session = InMemorySession()
        intents = kid.detect("Hi Alice. How are you doing?", session)
        assert("#greeting" in intents)
        assert("#confirm_yes" in intents)

        intents = kid.detect("Yes. I would like to buy a new vacuum cleaner.", session)
        assert("#confirm_yes" in intents)
        
        intents = kid.detect("No I do not need a new vacuum cleaner.", session)
        assert(len(intents) == 0)