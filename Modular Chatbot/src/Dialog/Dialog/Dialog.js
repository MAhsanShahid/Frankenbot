import React, { Component } from 'react';
import './Dialog.css';
import Input from './../../UI/Input/Input';
import Button from '../../UI/Button/Button';
import Slot from './Slot/Slot';

class Dialog extends Component {   
    state = {
        fieldCount: 0
    }
    addField = () => {
        this.setState({
            fieldCount: this.state.fieldCount + 1
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
  const blue ={
    color:"#678FC9"
  }
  const goRight={
    position:'relative',
    left:'1%',
}
const goLeft={
    position:'relative',
    left:'-1.5%',
}
  const thisButtonShouldGoLeftandRed={
    position:'relative',
    left:'-90%',
    top:'26%',
    backgroundColor:'rgb(228, 91, 91)',
}
const goGreen = { 
    position: 'relative',

    backgroundColor:"rgb(66, 172, 66)",
}
    return(
        <div className="DialogReposition">
                <div className="row">    
                    <div className="col-10">
                        <Input style={fullWidth} label="Dialog Name" value="Example"></Input>
                    </div>
                    <div className="col-1">
                        <Button style={thisButtonShouldGoLeft} text="Save Dialog"></Button>
                </div>
                <div className="col-1">
                        <Button style={thisButtonShouldGoLeftandRed} text="Delete"></Button>
                </div>
                    <div className="col-2">
                        <Input style={fullWidth} label="If bot recognizes:" value="#Example"></Input>
                    </div>
                    {card}
                    <div className="col-1" >
                        <Button text="Add Field" style={{   
                            position:'relative', 
                            left:'-90%',
                            top:'25%'}} onClick={this.addField}></Button>
                    </div>
                </div> 
           
                <br/>
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
                <Button text="Add Slot" style={Object.assign({}, goGreen, goLeft)} ></Button>
        </div>
)
                    }
}

export default Dialog;