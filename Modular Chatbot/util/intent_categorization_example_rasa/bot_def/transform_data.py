import json
import yaml


def contains(utterances, rasa_example):
    for each in utterances:
        if each["utterance"].lower() == rasa_example["text"].lower():
            return True
    return False
# read file
with open('restaurantsearch.json', 'r') as myfile:
    data=myfile.read()
# parse file
obj = json.loads(data)
modules = obj["dialog_manager"]["modules"]
# print(modules)

with open('../../intent_categorization_example_rasa/rasa_dataset.json', 'r') as myfile:
    training_data=myfile.read()
# parse file
rasa_dataset = json.loads(training_data)
rasa_examples = rasa_dataset["rasa_nlu_data"]["common_examples"]
# print(rasa_examples)

new_entry_added = False
for each in modules:
    for each_intent in rasa_examples:
        key= list(each["nlu"]["intents"].keys())
        # print(key[0], str("#"+each_intent["intent"]))
        if key[0] == each_intent["intent"]:
            if not contains(each["nlu"]["intents"][key[0]], each_intent):
                new_entry={
                    "utterance" : each_intent["text"].lower(),
                    "entities" : each_intent["entities"]
                }
                each["nlu"]["intents"][key[0]].append(new_entry)
                new_entry_added = True

# print(obj)
    # print(each["nlu"]["intents"].keys())
if new_entry_added:
    with open('restaurantsearch.json', 'w') as outfile:
        json.dump(obj, outfile, indent=4)

# with open('datasets/greetings.yml', 'r') as myfile:
#     greetings=myfile.read()
# greet=yaml.load(greetings, Loader=yaml.FullLoader)
# conversations = greet["conversations"]
# new_entry_added = False
# for each in conversations:
#     # print(each[0])
#     new_entry = {
#             "text": each[0].lower(),
#             "intent": "greet",
#             "entities": []
#     }
#     if not contains(examples, new_entry["text"]):
#         examples.append(new_entry)
#         new_entry_added = True
# if new_entry_added:
#     with open('rasa_dataset.json', 'w') as outfile:
#         json.dump(obj, outfile)
# # examples.append(new_entry)
# # print(obj)

