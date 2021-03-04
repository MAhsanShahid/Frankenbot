# Load the Packages
from rasa_nlu.training_data  import load_data
from rasa_nlu.config import RasaNLUModelConfig
from rasa_nlu.model import Trainer, Interpreter
from rasa_nlu import config

# loading training data and config
#infile = '../../frankenbot-framework/bot_def/jans_simple_testbot/rasa/smalltalk.json'
infile = '../../frankenbot-framework/bot_def/jans_simple_testbot/rasa/greetings.json'

train_data = load_data(infile)
trainer = Trainer(config.load("config_spacy.yaml"))

# train the model and save it to a folder
trainer.train(train_data)
model_directory = trainer.persist('projects/')

# load the model in the interpreter
interpreter = Interpreter.load(model_directory)

# run two utterances through the interpreter
def nlu(utterance):
    nlu_result = interpreter.parse(utterance)
    print(nlu_result)
    print("utterance: " + utterance)
    print("nlu result: " + str(nlu_result["intent"]))   

nlu(u"Say something about you")
