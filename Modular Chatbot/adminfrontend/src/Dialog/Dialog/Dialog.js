import React, { Component } from 'react';
import './Dialog.css';
import Input from './../../UI/Input/Input';
import Button from '../../UI/Button/Button';
// import Slot from './Slot/Slot';
import Popup from '../../UI/Popup/Popup';
import {loadDialog, resetDialog} from '../../store/actions';
import axios from 'axios';
import { connect } from 'react-redux';
import {NotificationContainer, NotificationManager} from 'react-notifications';
import 'react-notifications/lib/notifications.css';


class Dialog extends Component {  
    state = {
        fieldCount: 0,
        name: "",
        answer: "",
        condition: "",
        id: this.props.history.location.pathname.split(':').pop(),
        showPopup1: false,
        showPopup2: false,
        new: false,
        options: [],
        NameIdPair: [],
        parentId: null,
        conditionId: -1,
        answerId: -1,
    }
    componentDidMount() {
      console.log(this.props.intents);
              this.setState({
          options: this.props.intents.map(i => [...this.state.options , "#" + i["Name"]]) ,
          NameIdPair : this.props.intents.map(i => [...this.state.NameIdPair, i["id"]])
        });
       
      //   if(!this.props.history.location.state.new)
      //  { 
        console.log(this.props.history.location.state.conditionId);
        console.log(this.props.history.location.state.condition)
        this.setState({
          name: this.props.history.location.state.name,
          condition: "#" + this.props.history.location.state.condition,
          conditionId: this.props.history.location.state.conditionId,
          answer: this.props.history.location.state.answer,
          answerId: this.props.history.location.state.answerId,
          parentId: this.props.history.location.state.parentId
        });
        // }else{
        //   this.setState({new:true});
        // }
      }
      goBack = () => {
        this.props.history.push('/Dialogs');
    }
    UpdateName = () => {
      axios.put('/api/dialog/node/' + this.state.id, {  
            "defaultModule": {
                "id": this.props.module_id
            },
            "name": this.state.name,
      }).then(() => {
        NotificationManager.success('Your changes have been saved', '', 1000);
        console.log("New Answer Saved");
      this.props.loadD(this.props.module_id);})
    }
    UpdateNameWithParent = () => {
      axios.put('/api/dialog/node/' + this.state.id, {  
            "defaultModule": {
                "id": this.props.module_id
            },
            "parent": {
              "id": this.state.parentId
          },
            "name": this.state.name,
      }).then(() => {
        NotificationManager.success('Your changes have been saved', '', 1000);
        console.log("New Answer Saved");
      this.props.loadD(this.props.module_id);})
    }
    
    UpdateAnswer = () => {
      if(this.state.answerId.length == 0){
        axios.post('/api/dialog/textanswer/', {
          "node": {
              "id": this.state.id
          },
          "text": this.state.answer,
      }).then(() => {
        NotificationManager.success('Your changes have been saved', '', 1000);
        console.log("New Answer Saved");
      this.props.loadD(this.props.module_id);})
      }else {
        axios.put('/api/dialog/textanswer/' + this.state.answerId, {
            "node": {
                "id": this.state.id
            },
            "text": this.state.answer,
        }).then(() => {
          NotificationManager.success('Your changes have been saved', '', 1000);
          console.log("New Answer Saved");
        this.props.loadD(this.props.module_id);})
}      }
    UpdateCondition = (i) => {
      console.log(i);
      console.log(this.state.id);
      if(this.state.conditionId.length == 0) {
        axios.post('/api/dialog/intentcondition/', {
          "intent": {
            "id": i[0]
        },
          "node": {
              "id": this.state.id
          },
          
      }).then(() => {
        NotificationManager.success('Your changes have been saved', '', 1000);
        console.log("New Answer Saved");
      this.props.loadD(this.props.module_id);})
    }else{
        axios.put('/api/dialog/intentcondition/' + this.state.conditionId, {
          "intent": {
            "id": i[0]
        },
            "node": {
                "id": this.state.id
            },
           
        }).then(() => {

          NotificationManager.success('Your changes have been saved', '', 1000);
          console.log("New Answer Saved");
          this.props.loadD(this.props.module_id);})
      }
    // console.log(
    //   "axios.put('/api/dialog/intentcondition/'" + this.state.conditionId + ", {"
    //       +"node: {"
    //           + "id:" + this.state.id
    //       +"},"+
    //       "intent: {"
    //         +"id: " + i
    //     +"},")
      }
    addField = () => {
        this.setState({
            fieldCount: this.state.fieldCount + 1
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
  render(){
    let card=[];  
    for (var i = 0; i < this.state.fieldCount; i += 1) {
      card.push(<div className="col-2"><Input style={{
        position: "relative",
        top: "8px",
        width:"100%",
        left:"-5px"
      }} key={i}></Input></div>);
  };
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
        <div className="DialogReposition">
            <div className="row">    
                <div className="col-5">
                    <Input style={fullWidth} 
                    label="Node Name" 
                    onChange={(e) => {
                      this.setState({name: e.target.value});
                    }} 
                    value={this.state.name}
                    onKeyPress={e => {
                      if(e.key === "Enter"){
                        e.preventDefault();
                        this.state.parentId == null ? this.UpdateName() : this.UpdateNameWithParent();
                      }
                    }}
                    onBlur={e => {
                     // e.target.value
                     console.log("LOST FOCUS");
                        e.preventDefault();
                        this.state.parentId == null ? this.UpdateName() : this.UpdateNameWithParent();
                      }}
                  ></Input>
                </div>
                <div className="col-1">
                    <Button 
                    onClick={() =>
                      axios.post("/api/dialog/node", {
                          "defaultModule": {
                              "id": this.props.module_id
                          },
                          "parent": {
                            "id": this.state.id
                        },
                          "name": "",
                      }
                  ).then(res => {
                      this.props.loadD(this.props.module_id);
                      NotificationManager.success('The Child Node has been created', '', 3000);
                      this.props.history.push(
                      {
                      pathname: 'Dialogs:' + res.data.id,
                      search: '',
                      state: { 
                        name: "",
                        condition:"",
                        conditionId:"",
                        answer: "",
                        answerId: null,
                        parentId: this.state.id,
                        new : false }
                    }
                    );
                    })
                      
                  }
                    style={{
    position:'relative',
    // width:'100%',
    left:'-90%',
    top:'26%',
    
}} text="Add Child" />
                </div>
                
                
                <div className="col-1">
                    <Button style={thisButtonShouldGoLeft} 
                    onClick={() => this.goBack()}
                    text="Cancel"></Button>
                </div>
                <div className="col-1">
                    <Button style={thisButtonShouldGoLeftandRed} onClick={() => this.togglePopup1()} text="Delete"></Button>
                </div>
               
            </div>
            <div className="row">
                <div className="col-5">
                    <Input 
                    style={fullWidth} 
                    inputtype="dropdown"
                    label="Condition: Intent matches:" 
                    options={this.state.options}
                    value={this.state.condition.length < 2 ? "Please select a condition" : this.state.condition}
                    // onKeyPress={e => {
                    //   if(e.key === "Enter"){
                    //     e.preventDefault();
                    //   }
                    // }}
                    onChange={(e) => {
                      console.log(e.value);
                      console.log(this.state.NameIdPair);
                      console.log(this.state.options);
                      console.log(this.state.options.indexOf(e.value))
                      const i = this.state.NameIdPair[this.state.options.indexOf(e.value)]
                      this.setState({
                        condition: e.value[0],
                        // conditionId: this.state.NameIdPair[this.state.options.indexOf(e.value)]
                      });
                        console.log(this.state.conditionId);
                        this.UpdateCondition(i);
                      }
                  }
                    ></Input>
                </div>
                {card}
                {/* <div className="col-1" >
                    <Button 
                        text="Add Field" 
                        style={{   
                        position:'relative', 
                        left:'-90%',
                        top:'25%'}} 
                        onClick={this.addField}>
                    </Button>
                    </div> */}
                </div>
            <div className="row">
                <div className="col-5">
                    <Input 
                    style={fullWidth} 
                    label="Answer:" 
                    value={this.state.answer}
               onKeyPress={e => {
                 if(e.key === "Enter"){
                   e.preventDefault();
                   this.UpdateAnswer()
                 }
               }}
               onBlur={e => {
                // e.target.value
                console.log("LOST FOCUS");
                   e.preventDefault();
                   this.UpdateAnswer();
                 }}
               onChange={(e) => {
                 this.setState({answer: e.target.value});
               }}
                    ></Input>
                </div>
                {/* <div className="col-1" >
                    <Button 
                        text="Add Example" 
                        style={{   
                        backgroundColor:"rgb(66, 172, 66)",
                        position:'relative', 
                        left:'-90%',
                        top:'25%'}} 
                        onClick={this.addField}>
                    </Button>
                    </div> */}
                    
            </div>    
            
            </div>
                 {/*
                    <div className="row">    
                        <div className="col-12">
                            <h4>Then Check for:</h4>
                        </div>
                    </div>
                <br/>

                    <div className="row" style={goRight}>    
                      <div className="col-2">
                        <h5 style={blue}>Check for</h5>
                        </div>
                    <div className="col-2" style={{
                        position:'relative',
                        left:'-0.4%'}}>
                        <h5 style={blue}>Save it as</h5>
                    </div>
                    <div className="col-2" style={{
                        position:'relative',
                        left:'-0.8%'}}>
                        <h5 style={blue}>If not present, ask</h5>
                    </div>
                    </div>
                <hr/>
                <ul>
                   <Slot Name="@Example" Desc="$Example" Ask="something"></Slot>
                   <Slot Name="@Example2" Desc="$Example2" Ask="something else"></Slot>
                </ul>
                <Button text="Add Slot" style={Object.assign({}, goGreen, goLeft)} ></Button> */}
        {this.state.showPopup1 ? 
        <Popup
            clickHandler={() => {
                //   this.props.closePopup();
                  axios.delete("/api/dialog/node/" 
                  + this.state.id).then(() => {
                  this.togglePopup1();
                  this.props.resetDialog();
                  this.props.loadD(this.props.module_id);
                  this.goBack();
                  
                });
                // + "4");
              }}
            id={this.state.id}
            text='You are about to delete this dialog node. This action cannot be undone. Are you sure?'
            closePopup={() => {
                this.togglePopup1();
            }}
          />
          : null
        }
        {this.state.showPopup2 ? 
          <Popup
            clickHandler={() => {
                //   this.props.closePopup();
                  axios.put("/api/dialog/node/" 
                  + this.state.id,     {
                    "name": this.state.name,
                }).then(() => {
              axios.put("/api/dialog/textanswer/", {
                "id" : this.state.answerId,
                "text": this.state.answer,
              })
            }).then(() => {
                  this.togglePopup2();
                  this.props.loadD(this.props.module_id);
                });
                // + "4");
              }}
            id={this.state.id}
            text='You are about to update this dialog node. This action cannot be undone. Are you sure?'
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
      intents : state.Intents,
      module : state.module,
      module_id: state.module_id,
    };
  }
  const mapDispatchToProps = dispatch => {
    return {
        loadD: id => dispatch(loadDialog(id)),
        resetDialog : () => dispatch(resetDialog()),
    };
    };
  export default connect(mapStateToProps, mapDispatchToProps)(Dialog);