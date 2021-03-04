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
import connect from 'react-redux/lib/connect/connect';

const dummy = props => {
  // axios.interceptors.request.use(function (config) {
  //   const token = store.getState().session.token;
  //   config.headers.Authorization =  token;

  //   return config;
// });
let Navbar = (<p></p>)
if(props.chosenMod != 0) Navbar = (<Route path="/" component={MyNavbar} />)
return (
<div>
<BrowserRouter>
{Navbar}
<Route path="/" exact component={WelcomeScreen} />
<Route path="/login" exact component={WelcomeScreen} />
<Route path="/signin" exact component={WelcomeScreen} />
<Switch>
<Route path="/Intents:id"  exact component={Intent} />
<Route path="/Intents" exact component={Intents} />
</Switch>
<Route path="/Entities:id" component={Entity} />
<Route path="/Entities" component={Entities} />
<Route path="/Dialogs" component={Dialogs} />
<Route path="/Dialogs:id" component={Dialog} />
</BrowserRouter>
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
  