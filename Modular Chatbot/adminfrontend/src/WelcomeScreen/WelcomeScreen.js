import React, { Component } from 'react';
import './WelcomeScreen.css';
import { changeMod, changeChat, loadIntent, loadEntity, loadDialog } from '../store/actions';
import { connect } from 'react-redux';
import axios from 'axios';
// import Cookies from 'js-cookie';
class WelcomeScreen extends Component {
 

    state = {
        modArray : [],
        chatArray : [],
        dummy : ['1', '2', '3']
    }
    componentDidMount() {
        axios.get('/api/defaultmodule')
          .then(res => {
            
            this.setState({modArray: res.data });
            console.log(this.state.modArray);
            console.log(this.state.modArray[0].name);
          })
          axios.get('/api/chatbot')
          .then(res => {
            
            this.setState({chatArray: res.data });
            console.log(this.state.chatArray);
            console.log(this.state.chatArray[0].name);
          })
      }
    render(){
        const modHTML =  this.state.modArray.map((m, index) => {
            console.log(m);
            return ( <div
                className="col-1 modul" 
                style={this.props.module_id === m.id ? {backgroundColor: "rgb(161, 199, 255)"} : {backgroundColor:"inherit"}}
                key={index}
                onClick={() => {
                   this.props.changeMod(m.name, m.id, m.language);
                   this.props.loadI(m.id);
                   this.props.loadE();
                   this.props.loadD(m.id);}}>
                   <p>{m.name}</p>
                   {/* <p>SOMETHING</p> */}
               </div>)});
        const chatHTML =  this.state.chatArray.map((m, index) => {
            console.log(m);
            return ( <div
                className="col-1 modul" 
                style={this.props.chatbot_id === m.id ? {backgroundColor: "rgb(161, 199, 255)"} : {backgroundColor:"inherit"}}
                key={index}
                onClick={() => {
                   this.props.changeChat(m);
                //    this.props.loadI(m.id);
                //    this.props.loadE();
                   }}>
                   <p>{m.name}</p>
                   {/* <p>SOMETHING</p> */}
               </div>)});
        
        // const modHTML =  this.state.dummy.map((m, index) => ( <div
        //     >
        //        {/* <p>{m.name}</p> */}
        //        <p>SOMETHING</p>
        //    </div>))
    
return(
    <React.Fragment>
    <div className="row cStyle">
       <div className="col-12">
        <h1 >Frankenbot Administration</h1>
        <a href='/'><img 
        style={{width:"100%"}}
        src={ require('../ressources/tiles.jpeg')}
        alt="" /></a>
        </div></div>
        <br></br>
        <br></br> 
        <div className="row justify-content-md-center">
            <div className="modulTitel">
       <h4>Modules</h4>
       </div>
       </div>   
    <div className="row justify-content-md-center">
    <div
                className="col-1 createModul" 
                onClick={() => {
                    this.props.history.push('/newModule');}}>
                   <p>Create Modul</p>
                   {/* <p>SOMETHING</p> */}
               </div>
       {modHTML}
        {/* <div className="col-1 modul" 
        style={this.props.chosenModule===2 ? {backgroundColor: "rgb(161, 199, 255)"} : {backgroundColor:"inherit"}}
        onClick={() => {
            this.props.changeMod(2);
            this.props.loadI();
            this.props.loadE();}}>
            <p>Modul 2</p>
        </div> */}
    </div>
    <div className="row justify-content-md-center">
            <div className="modulTitel">
       <h4>Chatbots</h4>
       </div>
       </div>   
    <div className="row justify-content-md-center">
    <div
                className="col-1 createModul" 
                onClick={() => {
                    this.props.history.push('/newChatbot');}}>
                   <p>Create Chatbot</p>
                   {/* <p>SOMETHING</p> */}
               </div>
        
        {chatHTML}        
        
    
        {/* <div className="col-1 modul"
        style={this.props.chosenChatbot===1 ? {backgroundColor: "rgb(161, 199, 255)"} : {backgroundColor:"inherit"}}
        onClick={() => {
            console.log(Cookies.get('AUTH_TOKEN'));
            console.log("Cookies get");
            this.props.changeChat(1);}}
            >
            <p>Chatbot 1</p>
        </div>
        <div className="col-1 modul"
        style={this.props.chosenChatbot===2 ? {backgroundColor: "rgb(161, 199, 255)"} : {backgroundColor:"inherit"}}
        onClick={() => this.props.changeChat(2)}>
            <p>Chatbot 2</p>
        </div> */}
    </div>
    {/* <div className="row justify-content-md-center">
        <Button text="Load" onClick={()=>this.props.loadIED()}>Load All</Button>
    </div> */}

</React.Fragment>

)}
}
const mapStateToProps = state => {
    return {
      chosenChatbot: state.chatName,
      chosenModule: state.chosenModule,
      chatbot_id: state.chosenChat,
      module_id: state.module_id
    };
  }
const mapDispatchToProps = dispatch => {
return {
    changeMod: (mod, mod_id, language) => dispatch(changeMod(mod, mod_id, language)),
    changeChat: (chat) => dispatch(changeChat(chat)),
    loadI: id => dispatch(loadIntent(id)),
    loadE: () => dispatch(loadEntity()),
    loadD: id => dispatch(loadDialog(id)),
};
};
  
  export default connect(mapStateToProps, mapDispatchToProps)(WelcomeScreen);
  