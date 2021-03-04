import React, { Component } from 'react';
import './App.css';
import { connect } from 'react-redux';
import {BrowserRouter, Route, Switch} from 'react-router-dom';
import Login from './Login/Login';
import Dummy from './dummy/dummy';
import Signin from './Signin/Signin';
class App extends Component {
  state = {
    authorized: false,
  }
  render() {
    let routes = (
      <div>
        <Switch>
            <Route path="/signin" component={Signin} />
            <Route path="/" component={Login} />
        </Switch>
    </div>

    )
    if(this.props.authorized){
       routes =  <Route component={Dummy} />
      }
    return (
      <BrowserRouter>
      {routes} 
      </BrowserRouter>
    );
  }
}

const mapStateToProps = state => {
  return {
    authorized: state.authorized
  };
}

export default connect(mapStateToProps)(App);
