import {Navbar, Nav} from 'react-bootstrap';
import './MyNavbar.css';
import logo from './../../Ressources/DFKI_Logo.png';
import React from 'react';
import { useHistory } from 'react-router-dom';

const MyNavbar = () => {
let history = useHistory();
return (
<div>
  <Navbar className="navbar-default">
    <Navbar.Brand onClick={() => history.push('/') }><img src={logo} className="App-logo-navbar" alt="logo" /></Navbar.Brand>
    <Nav className="mr-auto">
      <Nav.Link onClick={() => history.push('Intents') }><h4 className="navElem" >Intents </h4></Nav.Link>
      <Nav.Link onClick={() => history.push('Entities') }><h4 className="navElem" >Entities </h4></Nav.Link>
      <Nav.Link onClick={() => history.push('Dialogs') }><h4 className="navElem" >Dialogs </h4></Nav.Link>
    </Nav>
  </Navbar>
</div>
)
}
export default MyNavbar;