import React, {Component} from 'react';
import Button from '../UI/Button/Button';
import './Intents.css';
import IntentSum from './IntentSum/IntentSum';
import {BrowserRouter, Route} from 'react-router-dom';
import connect from 'react-redux/lib/connect/connect';
import Intent from './Intent/Intent';

class Intents extends Component { 
    
    routeChange=()=> {
        let path = 'Intents:0';
        this.props.history.push(path);
      }

render() {
        
    const thisButtonShouldGoLeft={
        position:'absolute',
        left:'-50%',
        top:'-85%'
    }
    
    var IntensElems= this.props.Intents.map(((i) => {return(
    <IntentSum 
    History={this.props.history}
    Name={i['Name']} 
    Desc={i['Desc']} 
    ExCount={i['ExCount']}
    id={i['id']}
    key={i['id']}
    ></IntentSum>);}))
    
    return(
        <BrowserRouter>
            <br></br>
            <br></br>
                <div className="row">    
                <div className="col-2 IntDivStyle">
                <h4>Intents</h4>
                </div>
                <div className="col-5 IntDivStyle">
                <h4>Description</h4>
                </div>
                <div className="col-2 IntDivStyle">
                <h4>Examples</h4>
                </div>
                <div className="col-1 IntDivStyle">
                <Button onClick={this.routeChange} style={thisButtonShouldGoLeft} text="Add New +" />
                </div>
                </div>
           
            <hr/>
            <ul>
           {IntensElems}
            </ul>
            
        </BrowserRouter>
        )
}
}
const mapStateToProps = state => {
    return {
      Intents: state.Intents
    };
  }
  
  export default connect(mapStateToProps)(Intents);
  