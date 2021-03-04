import React, { Component } from 'react';
import './WelcomeScreen.css';
import DialogSum from '../Dialog/DialogSum/DialogSum';
import { changeMod, changeChat, loadIntent } from '../store/actions';
import { connect } from 'react-redux';
import Button from '../UI/Button/Button';
class WelcomeScreen extends Component {
    clicker = num => {
        this.setState({chosenModule:num});
    }
    render(){
return(
    <React.Fragment>
    <div className="row cStyle">
       <div className="col-12">
        <h1 >Welcome to the DFKI Assistant</h1>
        <a href='http://localhost:3000/Intents:'><img style={{width:"100%"}}src={ require('../ressources/tiles.jpeg') } /></a>
        </div></div>
        <br></br>
        <br></br> 
    <div className="row justify-content-md-center">
        <div
         className="col-1 modul" 
         style={this.props.chosenModule===1 ? {backgroundColor: "rgb(161, 199, 255)"} : {backgroundColor:"inherit"}}
         onClick={() => {
            this.props.changeMod(1);
            this.props.loadIED();}}>
            <p>Modul 1</p>
        </div>
        <div className="col-1 modul" 
        style={this.props.chosenModule===2 ? {backgroundColor: "rgb(161, 199, 255)"} : {backgroundColor:"inherit"}}
        onClick={() => {
            this.props.changeMod(2);
            this.props.loadIED();}}>
            <p>Modul 2</p>
        </div>
    </div>
    <div className="row justify-content-md-center">
        <div className="col-1 modul"
        style={this.props.chosenChatbot===1 ? {backgroundColor: "rgb(161, 199, 255)"} : {backgroundColor:"inherit"}}
        onClick={() => this.props.changeChat(1)}>
            <p>Chatbot 1</p>
        </div>
        <div className="col-1 modul"
        style={this.props.chosenChatbot===2 ? {backgroundColor: "rgb(161, 199, 255)"} : {backgroundColor:"inherit"}}
        onClick={() => this.props.changeChat(2)}>
            <p>Chatbot 2</p>
        </div>
    </div>
    {/* <div className="row justify-content-md-center">
        <Button text="Load" onClick={()=>this.props.loadIED()}>Load All</Button>
    </div> */}

</React.Fragment>

)}
}
const mapStateToProps = state => {
    return {
      chosenChatbot: state.chosenChat,
      chosenModule: state.chosenModule,
    };
  }
const mapDispatchToProps = dispatch => {
return {
    changeMod: (mod) => dispatch(changeMod(mod)),
    changeChat: (chat) => dispatch(changeChat(chat)),
    loadIED: () => dispatch(loadIntent()),
};
};
  
  export default connect(mapStateToProps, mapDispatchToProps)(WelcomeScreen);
  