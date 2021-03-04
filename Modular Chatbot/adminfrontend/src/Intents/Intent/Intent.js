import React, {Component} from 'react';
import './Intent.css';
import Input from './../../UI/Input/Input';
import Button from '../../UI/Button/Button';
import Popup from '../../UI/Popup/Popup';
import axios from 'axios';
import {loadIntent, resetIntents} from '../../store/actions';
import { connect } from 'react-redux';
import {NotificationContainer, NotificationManager} from 'react-notifications';
import 'react-notifications/lib/notifications.css';

class Intent extends Component {
  
    state = {
        showPopup1: false,
        showPopup2: false,
        utterancesArray : [],
        name: "",
        description: "",
        example : "",
        id: this.props.history.location.pathname.split(':').pop(),
        thisUtterance : "",
        new : false,
      };

      componentDidMount() {
        if(!this.props.history.location.state.new)
       { this.updateUtterances();
        this.setState({
          name: this.props.history.location.state.name,
          description: this.props.history.location.state.description,
        });
        console.log(this.props.history);
        console.log(this.props.history.location.state.name);
        }else{
          this.setState({new:true});
        }
      }
      saveIntent = () => {
        axios.put("/api/intent/" + this.state.id, {   
          "module": {
            "id": this.props.module_id,
            "name": this.props.module
          },
          "name": this.state.name,
          "description": this.state.description,
      }).then(res => {
        this.setState({new:false, id:res.data.id});
        NotificationManager.success('Intent Saved', '', 3000);
        console.log(res.data);
        this.props.loadI(this.props.module_id);}).catch(error => {
          console.log(error)
      });
      // console.log(this.props.module);
      } 
      saveNewIntent = () => {
        axios.post("/api/intent", {   
          "module": {
            "id": this.props.module_id,
            "name": this.props.module
          },
          "name": this.state.name,
          "description": this.state.description,
      }).then(res => {
        this.setState({new:false, id:res.data.id});
        console.log(res.data);
        this.props.loadI(this.props.module_id);}).catch(error => {
          console.log(error)
      });
      // console.log(this.props.module);
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
        this.props.history.push('/Intents');
    }
    updateUtterances = () => {
      axios.get('/api/utterance/by_intent/' + this.state.id)
      .then(res => {
        this.setState({utterancesArray: res.data });
      });
    }
    addUtterance = () => {
      console.log("Add Utterance");
      axios.post("/api/utterance", {   
          "intent": {
              "id": this.state.id
          },
          "text": this.state.example
      }).then(() => {this.updateUtterances()
      this.setState({example: ""})});
    }
    deleteAndUpdateUtterance = (id) => {
      axios.delete('/api/utterance/' + id)
      .then(() => {
        this.updateUtterances();
      });
    }
    keyPressed(event) {
      if (event.key === "Enter") {
        this.addUtterance()
      }
    }
render(){
  const id = this.state.id;
  const utterancesHTML =  this.state.utterancesArray.map(m => {;
    return ( 
      <div key={m.id} className="block">
    <li style={{'display':'inline-block'}}
        // onClick={() => {
        //    this.props.changeMod(m.name, m.id);
        //    this.props.loadI(m.id);
        //    this.props.loadE();}}
        >
           <p>{m.text}</p>
           {/* <p>SOMETHING</p> */}
       </li>
       <img 
       src={require('./trash.svg')} className="floatRight" 
       alt="x"
       onMouseOver={e => (e.currentTarget.src = require('./trash-2.svg'))}
       onMouseOut={e => (e.currentTarget.src = require('./trash.svg'))}
       onClick={()=> {
        this.setState({showPopup2: true, thisUtterance: m.id})
       }}/>
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
        <div className="IntentReposition">
                <div className="row">    
                    <div className="col-10">
                        <Input style={fullWidth} 
                        label="Intent Name" 
                        value={this.state.name}
                        onChange={(e) => {
                          this.setState({name: e.target.value});
                        }}
                        ></Input>
                    </div>
                    <div className="col-1">
                        <Button 
                        style={thisButtonShouldGoLeft} 
                        text="Save Intent"
                        onClick={() => this.state.new ? this.saveNewIntent() : this.saveIntent()}></Button>
                </div>
                <div className="col-1">
                        <Button 
                        style={thisButtonShouldGoLeftandRed} 
                        text="Delete"
                        onClick={() => this.togglePopup1()}></Button>
                </div>
                    <div className="col-12">
                        <Input 
                        inputtype='textarea' 
                        style={fullWidth} 
                        label="Description" 
                        value={this.state.description}
                        onChange={(e) => {
                          this.setState({description: e.target.value});
                        }}></Input>
                    </div>
                </div> 
                    <div className="row">  
                    <div className="col-11">
                        <Input inputtype='textarea' 
                        style={fullWidth} 
                        label="Add User Examples"
                        value={this.state.example}
                        onKeyPress={e => {
                          if(e.key === "Enter"){
                            e.preventDefault();
                            this.addUtterance();
                          }
                        }}
                        onChange={(e) => {
                          this.setState({example: e.target.value});
                        }}></Input>
            </div>
            <div className="col-1">
                        <Button 
                        onClick={() =>{
                          if(this.state.new){
                            console.log("You need to save the intent first to be able to add utterances");
                          }else this.addUtterance();
                        }}
                        style={thisButtonShouldGoLeft} 
                        text="Add New"></Button>
                </div>
                <br/>
                    <div className="row">    
                    <div className="col-12">
                    <h4>User Examples</h4>
                    </div>
                    </div>
                </div>
                <hr/>
                <ul>
                    {utterancesHTML}
                </ul>
                </div>
                {this.state.showPopup1 ? 
          <Popup
            clickHandler={() => {
                //   this.props.closePopup();
                  axios.delete("/api/intent/" 
                  + id).then(() => {
                  this.togglePopup1();
                  this.props.resetIntents();
                  this.props.loadI(this.props.module_id);
                  this.goBack();
                  
                });
                // + "4");
              }}
            id={id}
            text='You are about to delete this intent. This will delete all utterances of this intent also. This action cannot be undone. Are you sure?'
            closePopup={() => {
                this.togglePopup1();
            }}
          />
          : null
        }
        {this.state.showPopup2 ? 
          <Popup
            clickHandler={() => {
              this.deleteAndUpdateUtterance(this.state.thisUtterance)
              this.togglePopup2();}}
            id={id}
            text='You are about to delete this utterance. This action cannot be undone. Are you sure?'
            closePopup={() => {
                this.togglePopup2();
            }}
          />
          : null
        }
        <NotificationContainer/> 
        </div>
        
        
)
}
}
const mapStateToProps = state => {
    return {
        module_id: state.module_id,
        module: state.chosenModule,
    };
  }
  const mapDispatchToProps = dispatch => {
    return {
        loadI: id => dispatch(loadIntent(id)),
        resetIntents : () => dispatch(resetIntents()),
    };
    };
  export default connect(mapStateToProps, mapDispatchToProps)(Intent);