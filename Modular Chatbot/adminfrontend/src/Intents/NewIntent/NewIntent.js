import React, {Component} from 'react';
import './NewIntent.css';
import Input from './../../UI/Input/Input';
import Button from '../../UI/Button/Button';
import Popup from '../../UI/Popup/Popup';
import axios from 'axios';
import {loadIntent, resetIntents} from '../../store/actions';
import { connect } from 'react-redux';

class NewIntent extends Component {
  
    state = {
      };

      componentDidMount() {
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
        this.goBack();}).catch(error => {
          console.log(error)
      });
      } 
    goBack = () => {
        this.props.history.push('/Intents');
    }
render(){
  const id = this.state.id;

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
                        text="Cancel"
                        onClick={() => this.goBack()}></Button>
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
                </div>
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
  export default connect(mapStateToProps, mapDispatchToProps)(NewIntent);