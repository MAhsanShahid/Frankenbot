import React, {Component} from 'react';
import './ModuleSettings.css';
import Input from '../UI/Input/Input';
import Button from '../UI/Button/Button';
import {connect} from "react-redux";
import axios from 'axios';
import { changeMod } from '../store/actions';
import {NotificationContainer, NotificationManager} from 'react-notifications';
import 'react-notifications/lib/notifications.css';

class ModuleSettings extends Component {
  
    state = {
        name: "",
        language:""
      };

      componentDidMount() {
        //   axios.get("/api/defaultmodule").then(res => {
            this.setState({
                name: this.props.module,
                language: this.props.module_language
                // name: this.props.chatName,
                // welcomeMessage: this.props.welcomeMessage,
                // fallbackMessage: this.props.fallbackMessage,
                // chosenModules:this.props.chatModules,
                });
            console.log(this.state.modules);
            console.log(this.state.chosenModules);
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
        axios.put("/api/defaultmodule/" + this.props.module_id, {   
          "name": this.state.name,
          "language": this.state.language,
      }).then(res => {
        this.props.changeMod(this.state.name, this.props.module_id, this.state.language)
        NotificationManager.success('Module Settings Saved', '', 3000);
        console.log(res.data);
        this.goBack()}).catch(error => {
          console.log(error)
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
    const fullWidth={width:'100%'}
    const thisButtonShouldGoLeft={
        position:'relative',
        left:'-90%',
        top:'26%'
    }
    return(
        <div className="ModulSettingsReposition">
            <div className="IntDivStyle" style={{paddingLeft:"12%", paddingTop:"2%"}}>
                <h4>Create new Module</h4>
            </div>
                <div className="row">    
                    <div className="col-6">
                        <Input style={fullWidth} 
                        label="Module Name" 
                        value={this.state.name}
                        onChange={(e) => {
                          this.setState({name: e.target.value});
                        }}
                        ></Input>
                    </div>
                    <div className="col-1">
                        <Button 
                        style={thisButtonShouldGoLeft} 
                        text="Save Module"
                        onClick={() => this.saveSettings()}></Button>
                        </div>
                <div className="col-6">
                        <Input 
                        inputtype="dropdown"
                        label="Language" 
                        value={this.state.language}
                        options={["en", "de"]}
                        onChange={(e) => {
                          this.setState({language: e.value});
                          console.log(this.state.language);
                        }}></Input>
                    </div>
                    </div>
                <br/>
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
        
)}}
const mapStateToProps = state => {
    return {
      module: state.chosenModule,
      module_language: state.module_language,
      module_id: state.module_id
    };
  }
  const mapDispatchToProps = dispatch => {
    return {
        changeMod: (mod, mod_id, language) => dispatch(changeMod(mod, mod_id, language)),
    };
    };
  export default connect(mapStateToProps, mapDispatchToProps)(ModuleSettings);
  