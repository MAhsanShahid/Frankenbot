import unittest
from frankenbot.response.response_generator import SimpleResponseGenerator
from frankenbot.persistence.json.json_unserializer import JSONUnserializer
from frankenbot.session.session import InMemorySession

class TestSimpleResponseGenerator(unittest.TestCase):

    def test(self):

        json = {
            "type": "simple_response_generator",
            "mode": "sequential",
            "responses": [
                "hallo",
                "hi",
                "moin"
            ]
        }
        unserializer = JSONUnserializer()
        srg = unserializer.generate(json)
        session = InMemorySession()

        a1 = srg.generate_response(session)
        assert(a1 == "hallo")
        a2 = srg.generate_response(session)
        assert(a2 == "hi")
        a3 = srg.generate_response(session)
        assert(a3 == "moin")
        a4 = srg.generate_response(session)
        assert(a4 == "hallo")

        json["mode"] = "random"
        srg = unserializer.generate(json)
        for i in range(0,10):
            srg.generate_response(session)