import React from 'react';
import './DialogSum.css';

const DialogSum = props => {
  const goABitLeft = {
    position: 'relative',
    left: '3.5%',
  };
  const goABitRight = {
    position: 'relative',
    left: '9%',
  };
  let response = (<p className="DialogElemP2">{props.responseCount} Responses</p>)
  if(props.responseCount < 2){
    response = (<p className="DialogElemP2">{props.responseCount} Response</p>)
  }
  const routeChange = () => {
    let path = 'Dialogs:' + id;
    props.History.push(path);
  }
  const id = props.id;
    return(
   
    <li className="DialogElemLi">
       <a onClick={routeChange} className="DialogElemA">
    <div className="row">    
    
    <div className="col-3 DialogElem">
    
    <p className={"DialogElemP"}>{props.Name}</p>
        {response}
       
    </div>
    
</div>
</a>
</li>

)
}

export default DialogSum;