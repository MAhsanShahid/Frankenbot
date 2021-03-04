import axios from 'axios';
import Cookies from 'js-cookie';
export const Auth = (a, b) => {
    console.log("AUTH DISPATCHED");
    Cookies.set('AUTH_TOKEN', a);
    return {
        type: 'addAuth',
        AUTH_TOKEN:a,
        authorized:b
    };
}
export const changeMod = (mod, mod_id, language) => {
    console.log("mod changed")
    return {
        type: 'changeMod',
        mod:mod,
        mod_id:mod_id,
        language: language
    };
}
export const changeChat = (chat) => {
    console.log("chat changed")
    console.log(chat)
    return {
        type: 'changeChat',
        chatName:chat.name,
        id:chat.id,
        chatModules:chat.modules,
        welcomeMessage:chat.welcomeMessage,
        fallbackMessage:chat.fallbackMessage
    };
}
export const loadI = (intent) => {
    console.log("load")
    console.log(intent);
    return {
        type: 'loadI',
        Intents:intent,
    };
}
export const resetIntents = () => {
    console.log("resetInt")
    return {
        type: 'resetI',
    };
}
export const resetDialog = () => {
    console.log("reset Dialog")
    return {
        type: 'resetD',
    };
}
export const loadE = (entity) => {
    console.log("load")
    console.log(entity);
    return {
        type: 'loadE',
        Entities:entity,
    };
}
export const loadD = (dialog) => {
    console.log("load")
    console.log(dialog);
    return {
        type: 'loadD',
        Dialog:dialog,
    };
}

export const addAuth = (username, password) => {
    return dispatch => {
        console.log("ADD AUTH DISPATCHED")
        axios({
            method: 'post',
            url: '/api/auth_token',
            headers: {
              'x-Auth-Email': username,
              'x-Auth-Password': password }
            })
            .then(response => {
                if(response.status === 200){
                dispatch(Auth(response.data["token"], true));
                }else{
                    console.log("Something wrong with the status code");
                }
            })
            // .catch(response => {
            //     console.log(response);
            // });
        };
}
export const loadIntent = id => {
    return dispatch => {
        console.log("LIST INTENT DISPATCHED")
        dispatch(resetIntents());
        axios({
            method: 'get',
            url: '/api/intent/by_module/' + id,
            headers: {
              'x-auth-token': '18e9d995-dd5f-49d9-8262-4b16a6f0c020'}
            })
            .then(response => {
                console.log(response.data);
                if(response.status === 200 && response.data.length > 0){
                    response.data.forEach(element => {
                        dispatch(loadI([{'Name':element["name"], 'Desc':element["description"], 'ExCount':element[""], 'id':element["id"]}]));
                    });
                // dispatch(loadI([{'Name':response.data[0]["name"], 'Desc':response.data[0]["description"], 'ExCount':"1", 'id':response.data[0]["id"]}]));
                }else{
                    dispatch(loadI([]));
                    console.log("Something wrong with the status code on intent");
                }
            })
        };
}
export const loadEntity = () => {
    return dispatch => {
        console.log("LIST Entity DISPATCHED")
        axios({
            method: 'get',
            url: '/api/entity',
            headers: {
              'x-auth-token': '18e9d995-dd5f-49d9-8262-4b16a6f0c020'}
            })
            .then(response => {
                console.log(response);
                if(response.status === 200){
                dispatch(loadE([{'Name':response.data[0]["name"], 'Desc':"description", 'ExCount':"1", 'id':'1'}]));
                }else{
                    console.log("Something wrong with the status code on intent");
                }
            })
            axios({
                method: 'get',
                url: '/api/entity',
                headers: {
                  'x-auth-token': '18e9d995-dd5f-49d9-8262-4b16a6f0c020'}
                })
                .then(response => {

                    if(response.status === 200){
                    dispatch(loadE([{'Name':response.data[0]["name"], 'Desc':"description", 'ExCount':"1", 'id':'1'}]));
                    }else{
                        console.log("Something wrong with the status code on intent");
                    }
                })
        };
}
export const loadDialog = id => {
    return dispatch => {
        console.log("LIST DIALOG DISPATCHED")
        dispatch(resetDialog());
        axios({
            method: 'get',
            url: '/api/dialog/node/by_module/' + id,
            headers: {
              'x-auth-token': '18e9d995-dd5f-49d9-8262-4b16a6f0c020'}
            })
            .then(response => {
                
                if(response.status === 200 && response.data.length > 0){
                    response.data.forEach(element => {
                        // console.log("CONDITION IS SUPPOSED TO BE HERE:" + element["dialogNodeIntentConditions"][0]["intent"]["name"]);
                        dispatch(loadD([
                            {'Name':element["name"], 
                            'children':element["children"], 
                            'condition': element["dialogNodeIntentConditions"], 
                            'answer':element['dialogNodeTextAnswers'], 'id':element["id"]}]));
                    });
                // dispatch(loadI([{'Name':response.data[0]["name"], 'Desc':response.data[0]["description"], 'ExCount':"1", 'id':response.data[0]["id"]}]));
                }else{
                    dispatch(loadD([]));
                    console.log("Something wrong with the status code on intent");
                }
            })
        };
}
export const loadCookie = () => {
    let cookie = Cookies.get('AUTH_TOKEN');
    return dispatch => dispatch(Auth(cookie, true));
}