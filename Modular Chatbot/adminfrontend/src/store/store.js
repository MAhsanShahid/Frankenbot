const initialState = {
    authorized: false,
    AUTH_TOKEN: '',
    Intents:[
        // {
        //     'Name': "example",
        //     'Desc': "Dummy intent for testing",
        //     'ExCount':"4",
        //     'id':'1'
        // },
        // {
        //     'Name': "example2",
        //     'Desc': "More Dummy Intent for testing",
        //     'ExCount':"3",
        //     'id':'2'
        // },
        // {
        //     'Name': "example3",
        //     'Desc': "This one has a very very very very very very very very very very very very very very very very very very very very long description",
        //     'ExCount':"1",
        //     'id':'3'
        // },
        // {
        //     'Name': "example4",
        //     'Desc': "Even more Dummy Intent for testing",
        //     'ExCount':"5",
        //     'id':'4'
        // }
    ],
    Entities:[
    ],
    Dialog:[

    ],
    chosenModule: "",
    module_id : -1,
    module_language: "",
    chosenChat: -1,
    chatName:"",
    chatModules:[],
    welcomeMessage:"",
    fallbackMessage:"",
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
                chosenModule: action.mod,
                module_id: action.mod_id,
                module_language: action.language,
                chosenChat: -1,
                })
        case 'changeChat':
            return Object.assign({}, state, {
                module_id: -1,
                chosenChat: action.id,
                chatName:action.chatName,
                welcomeMessage:action.welcomeMessage,
                fallbackMessage:action.fallbackMessage,
                chatModules:action.chatModules,

                // navAus: false,
                })
        case 'loadI':
            console.log(state.Intents)
            return {
                ...state,
                Intents: state.Intents.concat(action.Intents),
                }
        case 'resetI':
            console.log(state.Intents)
            return {
                ...state,
                Intents: [],
                } 
        case 'resetD':
            console.log(state.Dialog)
            return {
                ...state,
                Dialog: [],
                } 
        case 'loadE':
            console.log(state.Entities)
            return {
                ...state,
                Entities:action.Entities,
        }
        case 'loadD':
            console.log(state.Dialog)
            return {
                ...state,
                Dialog: state.Dialog.concat(action.Dialog),
        }
        default:
        return state; 
    }
};

export default store;