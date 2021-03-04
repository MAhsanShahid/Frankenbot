import React from 'react';
import ReactDOM from 'react-dom';
import App from './App';
import './index.css';
import { Provider } from 'react-redux'
import { createStore, applyMiddleware } from 'redux'
import store from './store/store'
import thunk from 'redux-thunk';
import axios from 'axios';
import data from './config.json';
import {NotificationManager} from 'react-notifications';

// axios.interceptors.request.use(request => {
//   console.log(request);
//   return request;
// }, error => {
//   console.log(error);
//   return Promise.reject(error);
// }); 
axios.defaults.headers.common['x-auth-token'] = '18e9d995-dd5f-49d9-8262-4b16a6f0c020';
axios.interceptors.response.use(null, function(error) {
  console.log("ERROR INTERCEPTED");
  NotificationManager.error('An error has occurred', '', 3000);
  return Promise.reject(error);
});

console.log(data['baseURL']);
axios.defaults.baseURL = data['baseURL'];
const str = createStore(store , applyMiddleware(thunk));
ReactDOM.render(
  <Provider store={str}>
  <App />
  </Provider>,
  document.getElementById('root')
);
