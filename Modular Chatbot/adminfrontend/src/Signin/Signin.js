import React, { Component } from 'react';
import logo from './../Ressources/DFKI_Logo.png';
import './Signin.css';
import Input from '../UI/Input/Input';
import axios from 'axios';
import { connect } from 'react-redux';

class Signin extends Component {
  state = {
    username: "",
    password: "",
    authorized: false,
  }
  nameChanged = (event) => {
    console.log(event.target.value);
    this.setState({username: event.target.value});
  };
  passChanged = (event) => {
    console.log(event.target.value);
    this.setState({password: event.target.value});
    
  };
  Signin = () => {
    const headers = {
      'x-auth-email': this.state.username,
      'x-auth-password': this.state.password,
    };
    axios.post("/api/auth_token", {headers:headers}).then(response =>{
      // axios.defaults.headers.common['x-auth-token'] = response.data["token"],
      // addAuth(Auth, isAuthed)
      console.log(response)
    }
    );
    
    // // axios.post('http://localhost:8080/api/auth_token', headers=headers);
    // if (this.state.authorized) {
    //   axios.defaults.headers.common['x-auth-token'] = AUTH_TOKEN;
    // };
    
  };
  render() {
    return (
      <div className="App">
        <div className="App-header">
          <img src={logo} className="App-logo" alt="logo" />
          <h1 className="signinTitle" >Enter your information below to create a new account</h1>
        </div>
        <div className="App-intro">
        <Input onChange={this.nameChanged} name="Firstname" label="Firstname" />
        <Input onChange={this.nameChanged} name="Lastname" label="Lastname" />
          <Input onChange={this.nameChanged} name="Username" label="Username" />
          <Input onChange={this.passChanged} type="password" name="Password" label="Password" />
          <button type="submit" onClick={this.Signin} className="signinbutton"><span>Sign-up</span></button>
       </div>
       <a className="SigninLink" href='login'>Already a member? Log-in!</a>
      </div>
    );
  }
}

const mapStateToProps = state => {
  return {
    ctr: state.counter
  };
}

const mapDispatchToProps = dispatch => {
  return {
    addAuth: (Auth, isAuthed) => dispatch({type: 'DoSomething',auth: Auth, authorized:isAuthed})
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(Signin);
