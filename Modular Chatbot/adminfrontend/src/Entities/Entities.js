import React, { Component } from 'react';
import EntitySum from './EntitySum/EntitySum';
import {BrowserRouter} from 'react-router-dom';
import Button from '../UI/Button/Button';
import {connect} from "react-redux";

class Entities extends Component{  


render() {
    var EntityElems= this.props.dummy.map((i => {return(
    <EntitySum 
    History={this.props.history}
    Name={i['Name']} 
    Desc={i['Desc']} 
    ExCount={i['ExCount']} 
    id={i['id']}
    key={i['id']}>
    </EntitySum>);}))
    
    const thisButtonShouldGoLeft={
        position:'absolute',
        left: '-0%',
        top:'-85%'
    }
    return(
        <BrowserRouter>
            <br></br>
            <br></br>
                <div className="row">    
                <div className="col-2 IntDivStyle">
                <h4>Entity</h4>
                </div>
                <div className="col-2 IntDivStyle">
                <h4>Values</h4>
                </div>
                <div className="col-1 IntDivStyle">
                <Button style={thisButtonShouldGoLeft} text="Add New +" />
                </div>
                </div>
            <hr/>
            <ul>
           {EntityElems}
            </ul>
        </BrowserRouter>
        )
}
};
const mapStateToProps = state => {
    return {
      dummy: state.Entities
    };
  }
  
  export default connect(mapStateToProps)(Entities);
  