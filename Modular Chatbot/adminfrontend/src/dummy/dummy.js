import MyNavbar from './../UI/MyNavbar/MyNavbar';
import {BrowserRouter, Route, Switch} from 'react-router-dom';
import './dummy.css';
import React from 'react';
import Intents from '../Intents/Intents';
import Entities from '../Entities/Entities';
import Dialogs from '../Dialog/Dialogs';
import Intent from '../Intents/Intent/Intent';
import Entity from '../Entities/Entity/Entity';
import WelcomeScreen from '../WelcomeScreen/WelcomeScreen';
import Dialog from '../Dialog/Dialog/Dialog';
import {connect} from "react-redux";
import Settings from '../Settings/Settings';
import NewIntent from '../Intents/NewIntent/NewIntent';
import NewChatbot from '../NewChatbot/NewChatbot';
import NewModul from '../NewModul/NewModul';
import ModuleSettings from '../ModuleSettings/ModuleSettings';
import {NotificationContainer, NotificationManager} from 'react-notifications';

const dummy = props => {
  // axios.interceptors.request.use(function (config) {
  //   const token = store.getState().session.token;
  //   config.headers.Authorization =  token;

  //   return config;
// });
// if(props.chosenMod != 0);

return (
  
<div>
<BrowserRouter>
<Route path="/" component={MyNavbar} />
  <Route path="/" exact component={WelcomeScreen} />
  <Route path="/login" exact component={WelcomeScreen} />
  <Route path="/signin" exact component={WelcomeScreen} />
  <Switch>
  <Route path="/Intents:id"  exact component={Intent} />
  <Route path="/Intents" exact component={Intents} />
  </Switch>
  <Route path="/NewIntent" exact component={NewIntent} />
  <Switch>
  <Route path="/Entities:id" exact component={Entity} />
  <Route path="/Entities" exact component={Entities} />
  </Switch>
  <Switch>
  <Route path="/Dialogs" exact component={Dialogs} />
  <Route path="/Dialogs:id" exact component={Dialog} />
  </Switch>
  <Route path="/Settings" exact component={Settings} />
  <Route path="/ModuleSettings" exact component={ModuleSettings} />
  <Route path="/NewChatbot" exact component={NewChatbot} />
  <Route path="/NewModule" exact component={NewModul} />
</BrowserRouter>
<NotificationContainer/> 
</div>
)
}
const mapStateToProps = state => {
    return {
      IntentState: state.Intents,
      chosenMod: state.chosenModule
    };
  }
  
  export default connect(mapStateToProps)(dummy);
  