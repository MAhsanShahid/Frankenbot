import React, { Component } from 'react';
import './App.css';
import { connect } from 'react-redux';
import {BrowserRouter, Route, Switch} from 'react-router-dom';
import Login from './Login/Login';
import Dummy from './dummy/dummy';
import Signin from './Signin/Signin';
import Cookies from 'js-cookie';
import { loadCookie } from './store/actions';
class App extends Component {

  render() {
    let routes = (
      <div>
        <Switch>
            <Route path="/signin" component={Signin} />
            <Route path="/" component={Login} />
        </Switch>
    </div>
    )
    console.log(Cookies.get('AUTH_TOKEN'));
    if(Cookies.get('AUTH_TOKEN')){
       if (this.props.loadCookie()){
        routes =  <Route component={Dummy} />
      }
    }else if(this.props.authorized){
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
const mapDispatchToProps = dispatch => {
  return {
      loadCookie: () => dispatch(loadCookie()),
  };
}
export default connect(mapStateToProps, mapDispatchToProps)(App);
