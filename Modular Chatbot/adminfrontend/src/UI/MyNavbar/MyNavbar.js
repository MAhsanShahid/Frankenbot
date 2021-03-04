import {Navbar, Nav} from 'react-bootstrap';
import './MyNavbar.css';
import logo from './../../Ressources/DFKI_Logo.png';
import React from 'react';
import { useHistory } from 'react-router-dom';
import {connect} from "react-redux";
import Cookies from 'js-cookie';

const MyNavbar = props => {
let history = useHistory();
let selected = (<h6 className="navElem2">Please Select a module or a chatbot</h6>);
if(props.module_id !== -1){
  selected = <h6 className="navElem2">Selected Modul: {props.modul_name}</h6>
}
if(props.chatbot_id !== -1){
  selected = <h6 className="navElem2">Selected Chatbot: {props.chatbot_name}</h6>
}

let IED = (<Nav className="mr-auto"></Nav>);
if(props.module_id !== -1) IED = (
  <Nav className="mr-auto">
  <Nav.Link onClick={() => history.push('Intents') }><h4 className="navElem" >Intents </h4></Nav.Link>
  <Nav.Link onClick={() => history.push('Entities') }><h4 className="navElem" >Entities </h4></Nav.Link>
  <Nav.Link onClick={() => history.push('Dialogs') }><h4 className="navElem" >Dialogs </h4></Nav.Link>
  <Nav.Link onClick={() => history.push('ModuleSettings') }><h4 className="navElem" >Module Settings </h4></Nav.Link>
</Nav>
);

if(props.chatbot_id !== -1) IED = (  <Nav className="mr-auto">
<Nav.Link onClick={() => history.push('Settings') }><h4 className="navElem" >Chatbot Settings </h4></Nav.Link>
</Nav>);
return (
<div>
  <Navbar className="navbar-default"  style={{'height': '70px'}} >
    <Navbar.Brand onClick={() => history.push('/') }><img src={logo} className="App-logo-navbar" alt="logo" /></Navbar.Brand>
    {IED}
  {selected}
    <Nav className="ml-auto">
  <Nav.Link onClick={() => {
    Cookies.remove('AUTH_TOKEN');
    window.location.reload();} }><h4 className="navElem" >Logout</h4></Nav.Link>

</Nav>
  </Navbar>
</div>
)
}
const mapStateToProps = state => {
  return {
    modul_name: state.chosenModule,
    module_id: state.module_id,
    chatbot_name: state.chatName,
    chatbot_id: state.chosenChat
  };
}

export default connect(mapStateToProps)(MyNavbar);
