import json
import yaml


def contains(examples, utterance):
    for each in examples:
        if utterance == each["text"]:
            return True
    return False
# read file
with open('rasa_dataset.json', 'r') as myfile:
    data=myfile.read()

# parse file
obj = json.loads(data)
examples = obj["rasa_nlu_data"]["common_examples"]
# print(examples)

with open('datasets/trivia.yml', 'r') as myfile:
    new_data=myfile.read()
new_category=yaml.load(new_data, Loader=yaml.FullLoader)
conversations = new_category["conversations"]
new_entry_added = False
for each in conversations:
    # print(each[0])
    new_entry = {
            "text": each[0].lower(),
            "intent": "trivia",
            "entities": []
    }
    if not contains(examples, new_entry["text"]):
        examples.append(new_entry)
        new_entry_added = True
if new_entry_added:
    with open('rasa_dataset.json', 'w') as outfile:
        json.dump(obj, outfile, indent=4)
# examples.append(new_entry)
# print(obj)

