import React from 'react';
import './EntitySum.css';

const EntitySum = props => {
  const goABitLeft = {
    position: 'relative',
    left: '3.5%',
  };
  const routeChange = () => {
    let path = 'Entities:' + id;
    props.History.push(path);
  }
  const id = props.id;
    return(
    <div onClick={routeChange} className="intentElemA">
    <li className="IntentElemLi">
    <div className="row">    
    <div className="col-3 intentElem">
    <p className={"intentElemP"}>{props.Name}</p>
    </div>
    <div className="col-7 intentElem" style={goABitLeft}>
    <p className={"intentElemP"}>{props.Desc}</p>
    </div>
</div>
</li>
</div>
)
}

export default EntitySum;