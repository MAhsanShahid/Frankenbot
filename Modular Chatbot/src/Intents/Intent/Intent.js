import React from 'react';
import './Intent.css';
import Input from './../../UI/Input/Input';
import Button from '../../UI/Button/Button';

const Intent = props => {
    
  const id = props.id;
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
        <div className="IntentReposition">
                <div className="row">    
                    <div className="col-10">
                        <Input style={fullWidth} label="Intent Name" value={props.intentName}></Input>
                    </div>
                    <div className="col-1">
                        <Button style={thisButtonShouldGoLeft} text="Save Intent"></Button>
                </div>
                <div className="col-1">
                        <Button style={thisButtonShouldGoLeftandRed} text="Delete"></Button>
                </div>
                    <div className="col-12">
                        <Input inputtype='textarea' style={fullWidth} label="Description" value={props.intentDesc}></Input>
                    </div>
                </div> 
                    <div className="row">  
                    <div className="col-11">
                        <Input inputtype='textarea' style={fullWidth} label="Add User Examples"></Input>
            </div>
            <div className="col-1">
                        <Button style={thisButtonShouldGoLeft} text="Add New"></Button>
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
                    <li className="goABitLeft">Some example</li>
                    <li className="goABitLeft">Some longer example1</li>
                    <li className="goABitLeft">Some much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much much longer example2</li>
                    <li className="goABitLeft">Some example3</li>
                    <li className="goABitLeft">Some example4</li>
                    <li className="goABitLeft">Some example5</li>
                    <li className="goABitLeft">Some example6</li>
                </ul>
        </div>
)
}

export default Intent;