function sendMessage(utterance, session_id){
    let initialRequest = new XMLHttpRequest();
    // Open a new connection, using the GET request on the URL endpoint
    initialRequest.open('GET', "http://127.0.0.1:5000/?utterance="+utterance+"&user_session_id="+session_id, false);
    initialRequest.onload = function () {
        // Begin accessing JSON data here
        let data = JSON.parse(this.response);
        for (let key in data){
            if (data.hasOwnProperty(key) && key === "session_id"){
                // sessionStorage.setItem(key, data[key]);
                localStorage.setItem(key, data[key]);
                // user_session_id = localStorage.getItem(key);
            }
        }
        // console.log(data);
        let answer = data["chatbot_output"]["chatbot_utterance"]["response"];
        if (data["chatbot_output"]["active_module"]["module_output"]["recognized_intent"] !== ""){
            let intent = data["chatbot_output"]["active_module"]["module_output"]["recognized_intent"];
            answer = answer + " " + intent;
        }
        if (data["chatbot_output"]["active_module"]["module_output"]["recognized_entities"] !== []){
            let entities = data["chatbot_output"]["active_module"]["module_output"]["recognized_entities"];
            for (let key in entities){
                if(entities.hasOwnProperty(key)){
                    answer = answer + " " + entities[key]["entity"];
                }

            }
        }

        insertChat("bot", answer);
    };
    // Send request
    initialRequest.send();
}
let user = {};
user.avatar = "https://lh6.googleusercontent.com/-lr2nyjhhjXw/AAAAAAAAAAI/AAAAAAAARmE/MdtfUmC0M4s/photo.jpg?sz=48";

let bot = {};
bot.avatar = "https://a11.t26.net/taringa/avatares/9/1/2/F/7/8/Demon_King1/48x48_5C5.jpg";

function formatAMPM(date) {
    let hours = date.getHours();
    let minutes = date.getMinutes();
    let ampm = hours >= 12 ? 'PM' : 'AM';
    hours = hours % 12;
    hours = hours ? hours : 12; // the hour '0' should be '12'
    minutes = minutes < 10 ? '0'+minutes : minutes;
    let strTime = hours + ':' + minutes + ' ' + ampm;
    return strTime;
}

//-- No use time. It is a javaScript effect.
function insertChat(who, text, time){
    if (time === undefined){
        time = 0;
    }
    let date = formatAMPM(new Date());
    let control='';

    if (who === "bot"){
        control = '<li style="width:100%">' +
            '<div class="msj macro">' +
            '<div class="avatar">' +
            // '<img class="img-circle" style="width:100%;" src="'+ user.avatar +'" />' +
            '<i class="img-circle fa fa-robot" style="width:100%;font-size:36px;"  />'+
            '</div>' +
            '<div class="text text-l">' +
            '<p>'+ text +'</p>' +
            '<p><small>'+date+'</small></p>' +
            '</div>' +
            '</div>' +
            '</li>';
    }else{
        control = '<li style="width:100%;">' +
            '<div class="msj-rta macro">' +
            '<div class="text text-r">' +
            '<p style="word-wrap: break-word;">'+text+'</p>' +
            '<p><small>'+date+'</small></p>' +
            '</div>' +
            '<div class="avatar" style="padding:0px 0px 0px 10px !important">' +
            // '<img class="img-circle" style="width:100%;" src="'+bot.avatar+'" />' +
            '<i class="img-circle fa fa-user" style="width:100%;font-size:36px;"  />'+
            '</div>' +
            '</li>';
    }
    let uList = $("#list");
    uList.append(control).animate({scrollTop:uList.prop('scrollHeight')},500);
}

function resetChat(){
    $("ul").empty();
}
//-- Clear Chat
resetChat();

function displayMessage(value) {
    insertChat("me", value, 0);
}

function emptyInputField() {
    document.getElementById('message').value = '';
}

function enableSurveys(){
    let minutes = 60000; //1 minute = 60000
    // $("#survey1").addClass("disabled");// for 2nd li disable
    setTimeout(function(){$("#survey1").removeClass("disabled");}, minutes);
    setTimeout(function(){$("#survey2").removeClass("disabled");}, minutes);
    // $("#survey1").removeClass("disabled");// for 3rd li enable

}

function setAllData(){
    jQuery(document).ready(function() {
        jQuery('#loading').fadeOut(1000);
    });
    localStorage.clear();
    // sessionStorage.clear();
    let user_session_id = localStorage.getItem("session_id");
    // console.log(user_session_id);
    enableSurveys();
    sendMessage('', user_session_id);
    // insertChat("bot", "Hello Tom...", 0);
    let msg=document.getElementById('message');

        msg.addEventListener("keydown", function (e) {
            if (e.keyCode === 13) {  //checks whether the pressed key is "Enter"
                // msg.value.match(/^\s*$/) check for spaces only
                if(msg.value && !msg.value.match(/^\s*$/)) {
                    // console.log(String.IsNullOrEmpty(msg.value));
                    displayMessage(msg.value);
                    sendMessage(msg.value, localStorage.getItem("session_id"));
                    emptyInputField();
                }
            }
        });
        document.getElementById('send-message').onclick = function () {
            if(msg.value && !msg.value.match(/^\s*$/)) {
                // console.log(String.IsNullOrEmpty(msg.value));
                displayMessage(msg.value);
                sendMessage(msg.value, localStorage.getItem("session_id"));
                emptyInputField();
        }
    }
}

// window.addEventListener('beforeunload', function (e) {
//     e.preventDefault();
//     localStorage.removeItem("session_id");
//     setAllData();
//
// });

$(window).on('load', setAllData);