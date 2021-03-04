import React, {Component} from 'react';
import './Settings.css';
import Input from '../UI/Input/Input';
import Button from '../UI/Button/Button';
import axios from 'axios';
import {changeChat, resetSettingss} from '../store/actions';
import { connect } from 'react-redux';
import {NotificationContainer, NotificationManager} from 'react-notifications';
import 'react-notifications/lib/notifications.css';

class Settings extends Component {
  
    state = {
        showPopup1: false,
        showPopup2: false,
        modules : [],
        chosenModules: [],
        name: "",
        welcomeMessage: "",
        fallbackMessage: "",
  
      };

      componentDidMount() {
          axios.get("/api/defaultmodule").then(res => {
            this.setState({
                modules: res.data,
                name: this.props.chatName,
                welcomeMessage: this.props.welcomeMessage,
                fallbackMessage: this.props.fallbackMessage,
                chosenModules:this.props.chatModules,
                });
            console.log(this.state.modules);
            console.log(this.state.chosenModules);
          })
    //     if(!this.props.history.location.state.new)
    //    { this.updateUtterances();
    //     this.setState({
    //       name: this.props.history.location.state.name,
    //     });
    //     console.log(this.props.history);
    //     console.log(this.props.history.location.state.name);
    //     }else{
    //       this.setState({new:true});
    //     }
      }
      saveSettings = () => {
        axios.put("/api/chatbot/" + this.props.chosenChat, {   
          "modules": this.state.chosenModules,
          "name": this.state.name,
          "fallbackMessage": this.state.fallbackMessage,
          "welcomeMessage": this.state.welcomeMessage,
      }).then(res => {
        NotificationManager.success('Chatbot Settings Saved', '', 3000);
        this.goBack();
        console.log(res.data);
        this.props.changeChat(res.data)}).catch(error => {
          console.log(error)
      });
      } 
    togglePopup1 = () => {
        console.log("POPUP TOGGLE");
      this.setState({
        showPopup1: !this.state.showPopup1
      });
    }
    togglePopup2 = () => {
      console.log("POPUP TOGGLE");
    this.setState({
      showPopup2: !this.state.showPopup2
    });
  }
    goBack = () => {
        this.props.history.push('/');
    }
    // updateUtterances = () => {
    //   axios.get('/api/utterance/by_Settings/' + this.state.id)
    //   .then(res => {
    //     this.setState({utterancesArray: res.data });
    //   });
    // }
    // addUtterance = () => {
    //   console.log("Add Utterance");
    //   axios.post("/api/utterance", {   
    //       "Settings": {
    //           "id": this.state.id
    //       },
    //       "text": this.state.example
    //   }).then(() => {this.updateUtterances()
    //   this.setState({example: ""})});
    // }
    // deleteAndUpdateUtterance = (id) => {
    //   axios.delete('/api/utterance/' + id)
    //   .then(() => {
    //     this.updateUtterances();
    //   });
    // }
    // keyPressed(event) {
    //   if (event.key === "Enter") {
    //     this.addUtterance()
    //   }
    // }
render(){
  const id = this.state.id;
  const chatbotModsHTML =  this.state.chosenModules.map(m => {
    return ( 
      <div key={m.id} className="block">
    <li style={{'display':'inline-block'}}
        // onClick={() => {
        //    this.props.changeMod(m.name, m.id);
        //    this.props.loadI(m.id);
        //    this.props.loadE();}}
        >
           <p>{m.name}</p>
           {/* <p>SOMETHING</p> */}
       </li>
       <img 
       src={require('./icons8-minus-50.png')} className="floatRight" 
       alt="x"
       onMouseOver={e => (e.currentTarget.src = require('./icons8-minus-50(1).png'))}
       onMouseOut={e => (e.currentTarget.src = require('./icons8-minus-50.png'))}
       onClick={()=> {
        this.setState({
            chosenModules: this.state.chosenModules.filter(el => el !== m),
        });
        console.log(this.state.chosenModules);
       }}/>
       </div>
       )});
       const allModsHTML =  this.state.modules.map(m => {
           console.log(m);
           let icon = (<img 
            src={require('./icons8-plus-50(1).png')} className="floatRight" 
            alt="x"
            onMouseOver={e => (e.currentTarget.src = require('./icons8-plus-50.png'))}
            onMouseOut={e => (e.currentTarget.src = require('./icons8-plus-50(1).png'))}
            onClick={()=> {
             this.setState({chosenModules: this.state.chosenModules.concat(m)});
         }}/>);
        if(this.state.chosenModules.some(i => i.name == m.name)) {
          icon = (<div/>);
        }else {
          icon =  (<img 
            src={require('./icons8-plus-50(1).png')} className="floatRight" 
            alt="x"
            onMouseOver={e => (e.currentTarget.src = require('./icons8-plus-50.png'))}
            onMouseOut={e => (e.currentTarget.src = require('./icons8-plus-50(1).png'))}
            onClick={()=> {
             this.setState({chosenModules: this.state.chosenModules.concat(m)});
         }}/>);
        }
        return ( 
          <div key={m.id} className="block">
        <li style={{'display':'inline-block'}}
            // onClick={() => {
            //    this.props.changeMod(m.name, m.id);
            //    this.props.loadI(m.id);
            //    this.props.loadE();}}
            >
               <p>{m.name}</p>
               {/* <p>SOMETHING</p> */}
           </li>
           {icon}
           </div>
           )});
       

    const fullWidth={width:'100%'}
    const thisButtonShouldGoLeft={
        position:'relative',
        left:'-90%',
        top:'26%'
    }
    const thisButtonShouldGoLeftandRed={
      position:'relative',
      left:'-90%',
      top:'26%',
      backgroundColor:'rgb(228, 91, 91)',
  }
    return(
      <div>
        <div className="SettingsReposition">
        <div className="IntDivStyle" style={{position:"relative", left:"28%",paddingLeft:"12%", paddingTop:"2%"}}>
                <h4>Chatbot Settings</h4>
            </div>
                <div className="row">    
                    <div className="col-11">
                        <Input style={fullWidth} 
                        label="Chatbot Name" 
                        value={this.state.name}
                        onChange={(e) => {
                          this.setState({name: e.target.value});
                        }}
                        ></Input>
                    </div>
                    <div className="col-1">
                        <Button 
                        style={thisButtonShouldGoLeft} 
                        text="Save Settings"
                        onClick={() => this.saveSettings()}></Button>
                </div>
                    <div className="col-12">
                        <Input 
                        style={fullWidth} 
                        label="Welcome Message" 
                        value={this.state.welcomeMessage}
                        onChange={(e) => {
                          this.setState({welcomeMessage: e.target.value});
                        }}></Input>
                    </div>
                 
                <div className="col-12">
                        <Input 
                        style={fullWidth} 
                        label="Fallback Message" 
                        value={this.state.fallbackMessage}
                        onChange={(e) => {
                          this.setState({fallbackMessage: e.target.value});
                        }}></Input>
                    </div>
                    </div>
                <br/>
                    <div className="row">    
                    <div className="col-12">
                    <h4>Chatbot Modules</h4>
                    </div>
                    </div>
                    
                <hr/>
                <ul>
                    {chatbotModsHTML}
                </ul>
                <div className="row">    
                    <div className="col-12">
                    <h4>Available Modules</h4>
                    </div>
                    </div>
                    
                <hr/>
                <ul>
                    {allModsHTML}
                </ul>
                </div>
                {/* {this.state.showPopup1 ? 
          <Popup
            clickHandler={() => {
                //   this.props.closePopup();
                  this.setState({chosenModules: chosenModules.concat(this.state.moduleToDe)})
                  this.togglePopup1();       
              }}
            id={id}
            text='Are you sure you want to delete this module from Chatbot?'
            closePopup={() => {
                this.togglePopup1();
            }}
          />
          : null
        } */}
        <NotificationContainer/> 
        </div>
        
        
)
}
}
const mapStateToProps = state => {
    return {
        module_id: state.module_id,
        module: state.chosenModule,
        chatName:state.chatName,
        chosenChat: state.chosenChat,
        fallbackMessage: state.fallbackMessage,
        welcomeMessage: state.welcomeMessage,
        chatModules: state.chatModules
    };
  }
  const mapDispatchToProps = dispatch => {
    return {
        // loadI: id => dispatch(loadSettings(id)),
        // resetSettingss : () => dispatch(resetSettingss()),
        changeChat : chat => dispatch(changeChat(chat)),
    };
    };
  export default connect(mapStateToProps, mapDispatchToProps)(Settings);