import React, { Component } from 'react';
import logo from './../Ressources/DFKI_Logo.png';
import './Login.css';
import Input from './../UI/Input/Input';
import axios from 'axios';
import { connect } from 'react-redux';
import { addAuth, Auth } from '../store/actions';

class Login extends Component {
  state = {
    username: "",
    password: "",
    toggle : 0,
  }
  nameChanged = (event) => {
    this.setState({username: event.target.value});
  };
  passChanged = (event) => {
    this.setState({password: event.target.value});
    
  };
  updateStore = (a, b) => {
    this.props.addAuth(a, b);
  }
  login = (username, password) => {
    this.props.addAuth(username, password);
    console.log(this.props.authorized);
  }
  // login = (username, password) => {
  //   // // axios.post('http://localhost:8080/api/auth_token', headers=headers);
  //   // if (this.state.authorized) {
  //   //   axios.defaults.headers.common['x-auth-token'] = AUTH_TOKEN;
  //   // };
  //   const self = this;
  //   axios({
  //     method: 'post',
  //     url: 'http://localhost:8080/api/auth_token',
  //     headers: {
  //       'x-Auth-Email': username,
  //       'x-Auth-Password': password }
  //     })
  //     .then(function (response) {
  //         //handle success
  //         console.log(response);
  //         if(response.status === 200){
  //         self.updateStore(response.data["token"], true);
  //         return 1;
  //         }else{
  //           return 0;
  //         }
  //     })
  //     .catch(function (response) {
  //         //handle error
  //         console.log(response);
  //     });
  // };
  render() {
    let toggle = {display: "none"};
    const aBitDown = {
      position: "relative",
      top:"8px",
  };
    return (
      <div className="App">
        <div className="App-header">
          <img src={logo} className="App-logo" alt="logo" />
          <h2 className="WelcomeText">Welcome to DFKI Assistant</h2>
          <h1>Please enter your Login information below</h1>
        </div>
        <div className="WrongPass" style={{toggle}}>
          <p style={aBitDown}>Username or password wrong</p>
        </div>
        <div className="App-intro">
          <Input onChange={this.nameChanged} name="Username" label="Username" />
          <Input onChange={this.passChanged} type="password" name="Password" label="Password" />
          <button type="submit" onClick={(  ) => { 
            this.login(this.state.username, this.state.password);
            console.log(this.props.authorized);}} className="loginbutton"><span>Log In</span></button>
       </div>
       <a className="SigninLink" href='signIn'>Not already a member? Create new account!</a>
      </div>
    );
  }
}

const mapStateToProps = state => {
  return {
    authorized: state.authorized
  };
}

const mapDispatchToProps = dispatch => {
  return {
    addAuth: (a,b) => dispatch(addAuth(a,b))
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(Login);
