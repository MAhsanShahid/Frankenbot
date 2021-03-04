import React, {Component} from 'react';
import Button from '../UI/Button/Button';
import Input from '../UI/Input/Input';
import './Intents.css';
import IntentSum from './IntentSum/IntentSum';
import {connect} from "react-redux";
import axios from 'axios';
import {loadIntent, resetIntents} from '../store/actions';
import Modal from "react-bootstrap/Modal";
import "bootstrap/dist/css/bootstrap.min.css";

class Intents extends Component { 
    state = {
        show:false,
        name:"",
        description:"",
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
        console.log(res.data);
        this.props.loadI(this.props.module_id);
        this.triggerModal();}).catch(error => {
          console.log(error)
      });
      }
      triggerModal = () => {
          this.setState({show:!this.state.show})
      }

render() {
        
    const thisButtonShouldGoLeft={
        position:'absolute',
        left:'-50%',
        top:'-85%'
    }
    
    var IntensElems= this.props.Intents.map(((i) => {
        return(
    <IntentSum 
    History={this.props.history}
    Name={i['Name']} 
    Desc={i['Desc']} 
    ExCount={i['ExCount']}
    id={i['id']}
    key={i['id']}
    ></IntentSum>);}))
    
    return(
        <React.Fragment>
            
                  <Modal style={{textAlign: "center"}} show={this.state.show} onHide={this.triggerModal}>
        <Modal.Header className="innerModalTitle">
          <h4 className="IntDivStyle">Add New</h4>
        </Modal.Header>
        <Modal.Body>
    
            <div className="col-10 innerModal">
                        <Input style={{"width":"100%"}} 
                        label="Intent Name" 
                        value={this.state.name}
                        onChange={(e) => {
                          this.setState({name: e.target.value});
                        }}
                        ></Input>
                    </div>
                    <div className="col-10 innerModal">
                        <Input 
                        inputtype='textarea' 
                        style={{"width":"100%"}} 
                        label="Description" 
                        value={this.state.description}
                        onChange={(e) => {
                          this.setState({description: e.target.value});
                        }}></Input>
                    </div>
          <div className="row">
          <div className="col-5">
          <Button style={{position:"relative", top:"-1.5px"}} text="Close" onClick={this.triggerModal} />
          </div>
          <div className="col-2">
          <Button style={{backgroundColor:"rgb(66, 172, 66)", position:"relative", top:"-1.5px"}} text="Add intent" onClick={this.saveNewIntent}/>
          </div>
          </div>
          </Modal.Body>
      </Modal>
            <br></br>
            <br></br>
                <div className="row">    
                <div className="col-2 IntDivStyle">
                <h4>Intents</h4>
                </div>
                <div className="col-8 IntDivStyle">
                <h4>Description</h4>
                </div>
                <div className="col-2 IntDivStyle">
                <Button onClick={this.triggerModal} style={{backgroundColor:"rgb(66, 172, 66)", position:'absolute', left:'-50%', top:'-125%', height: '180%'}} text="Add New +" />
                </div>
                </div>
           
            <hr/>
            <ul>
           {IntensElems}
            </ul>
            
        </React.Fragment>
       
        )
}
}
const mapStateToProps = state => {
    return {
      Intents: state.Intents,
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
  export default connect(mapStateToProps, mapDispatchToProps)(Intents);
  