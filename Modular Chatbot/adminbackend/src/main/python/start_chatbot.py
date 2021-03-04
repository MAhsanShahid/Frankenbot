import sys
from pathlib import Path
import requests
import os.path
from os import path
import json
import shutil

if len(sys.argv) < 3:
    print("Error\nNot enough parameters")
    exit()

api_url = "http://localhost:8080"

chatbot_id = sys.argv[1]
api_token = sys.argv[2]

output_folder = "/tmp/frankenbot_definitions/" + str(chatbot_id) + "/"
if path.exists(output_folder):
    shutil.rmtree(output_folder)
Path(output_folder).mkdir(parents=True, exist_ok=True)

# read chatbot definition
target_url = api_url + "/api/export/chatbot/" + chatbot_id
headers = {"X-Auth-Token": api_token}
resp = requests.get(target_url, headers=headers)
if not resp.status_code == 200:
    print("Error\n" + target_url + " returns status " + str(resp.status_code))

f = open(output_folder + "frankenbot.json", "w")
f.write(str(resp.content))
f.close()

# create nlu stuff
chatbot_data = json.loads(resp.content)
for module in chatbot_data["dialog_manager"]["modules"]:
    module_id = module["id"]
    module_name = module["module_name"]
    module_path = output_folder + "rasa/" + module_name + "/"
    Path(module_path).mkdir(parents=True, exist_ok=True)

    target_url = api_url + "/api/export/nlu/" + str(module_id)
    headers = {"X-Auth-Token": api_token}
    resp = requests.get(target_url, headers=headers)
    if not resp.status_code == 200:
        print("Error\n" + target_url + " returns status " + str(resp.status_code))

    f = open(module_path + "/rasa.json", "w")
    f.write(str(resp.content))
    f.close()

    


