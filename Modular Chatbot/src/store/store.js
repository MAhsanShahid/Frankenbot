import {addAuth} from './actions.js';
const initialState = {
    authorized: false,
    AUTH_TOKEN: '',
    Intents:[
        {
            'Name': "example",
            'Desc': "Dummy intent for testing",
            'ExCount':"4",
            'id':'1'
        },
        {
            'Name': "example2",
            'Desc': "More Dummy Intent for testing",
            'ExCount':"3",
            'id':'2'
        },
        {
            'Name': "example3",
            'Desc': "This one has a very very very very very very very very very very very very very very very very very very very very long description",
            'ExCount':"1",
            'id':'3'
        },
        {
            'Name': "example4",
            'Desc': "Even more Dummy Intent for testing",
            'ExCount':"5",
            'id':'4'
        }
    ],
    Entities:[
        {
            'Name': "example",
            'Desc': "Dummy Entity for testing",
            'ExCount':"4",
            'id':'1'
        },
        {
            'Name': "example2",
            'Desc': "More Dummy Entity for testing",
            'ExCount':"3",
            'id':'2'
        },
        {
            'Name': "example3",
            'Desc': "This one has a very very very very very very very very very very very very very very very very very very very very long description",
            'ExCount':"1",
            'id':'3'
        },
        {
            'Name': "example4",
            'Desc': "Even more Dummy Entity for testing",
            'ExCount':"5",
            'id':'4'
        }
    ],
    chosenModule: 0,
    chosenChat: 0,
};

const store = (state = initialState, action) => {
    switch (action.type) {
        case 'addAuth':
            console.log("Actions : " + action.authorized, action.AUTH_TOKEN);
            return Object.assign({}, state, {
                AUTH_TOKEN: action.AUTH_TOKEN,
                authorized: action.authorized,
              })
        case 'changeMod':
            return Object.assign({}, state, {
                chosenModule: action.mod
                })
        case 'changeChat':
            return Object.assign({}, state, {
                chosenChat: action.chat
                })
        case 'load':
            console.log(state.Intents)
            return {
                ...state,
                Intents:action.Intents,
                Entities:action.Entities,
                }
            
    }
    return state;
};

export default store;