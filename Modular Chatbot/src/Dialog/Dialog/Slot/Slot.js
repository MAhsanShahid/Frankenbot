import React from 'react';
import './Slot.css';
import Input from '../../../UI/Input/Input';
const Slot = props => {
  const goABitLeft = {

  };
  const fullWidth={width:'100%'}
  const id = props.id;
    return(
    <div className="row">    
    <div className="col-2">
    <Input style={fullWidth} value={props.Name}></Input>
    </div>
    <div className="col-2" style={goABitLeft}>
    <Input style={fullWidth} value={props.Desc}></Input>
    </div>
    <div className="col-2" style={goABitLeft}>
    <Input style={fullWidth} value={props.Ask}></Input>
    </div>
</div>
)
}

export default Slot;