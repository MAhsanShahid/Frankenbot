import React from 'react';
import ReactDOM from 'react-dom';
import App from './App';
import './index.css';
import { Provider } from 'react-redux'
import { createStore, applyMiddleware } from 'redux'
import store from './store/store'
import thunk from 'redux-thunk';
import axios from 'axios';

axios.defaults.baseURL = 'http://localhost:8081';
const str = createStore(store , applyMiddleware(thunk));
ReactDOM.render(
  <Provider store={str}>
  <App />
  </Provider>,
  document.getElementById('root')
);
