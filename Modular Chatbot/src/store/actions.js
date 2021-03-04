import axios from 'axios';
export const Auth = (a, b) => {
    console.log("AUTH DISPATCHED")
    return {
        type: 'addAuth',
        AUTH_TOKEN:a,
        authorized:b
    };
}
export const changeMod = (mod) => {
    console.log("mod changed")
    return {
        type: 'changeMod',
        mod:mod,
    };
}
export const changeChat = (chat) => {
    console.log("chat changed")
    return {
        type: 'changeChat',
        chat:chat,
    };
}
export const load = (intent) => {
    console.log("load")
    console.log(intent);
    return {
        type: 'load',
        Intents:intent,
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
                console.log(response);
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
export const loadIntent = () => {
    return dispatch => {
        console.log("LIST INTENT DISPATCHED")
        axios({
            method: 'get',
            url: '/api/intent',
            headers: {
              'x-auth-token': '18e9d995-dd5f-49d9-8262-4b16a6f0c020'}
            })
            .then(response => {
                console.log(response);
                if(response.status === 200){
                    console.log(response.data)
                dispatch(load([{'Name':response.data[0]["name"], 'Desc':response.data[0]["description"], 'ExCount':"1", 'id':'1'}]));
                }else{
                    console.log("Something wrong with the status code on intent");
                }
            })
            axios({
                method: 'get',
                url: '/api/intent',
                headers: {
                  'x-auth-token': '18e9d995-dd5f-49d9-8262-4b16a6f0c020'}
                })
                .then(response => {
                    console.log(response);
                    if(response.status === 200){
                        console.log(response.data)
                    dispatch(load([{'Name':response.data[0]["name"], 'Desc':response.data[0]["description"], 'ExCount':"1", 'id':'1'}]));
                    }else{
                        console.log("Something wrong with the status code on intent");
                    }
                })
        };
}