import React from 'react';
import './DialogSum.css';

const DialogSum = props => {
  // const goABitLeft = {
  //   position: 'relative',
  //   left: '3.5%',
  // };
  // const goABitRight = {
  //   position: 'relative',
  //   left: '9%',
  // };
  let response = (<p className="DialogElemP2">{props.responseCount} Responses</p>)
  if(props.responseCount < 2){
    response = (<p className="DialogElemP2">{props.responseCount} Response</p>)
  }
  const routeChange = () => {
    let path = 'Dialogs:' + id;
    props.History.push({
      pathname: path,
      search: '',
      state: { 
        name: props.Name,
        condition:props.condition,
        conditionId:props.conditionId,
        answer: props.answer,
        answerId: props.answerId,
        parentId: props.parentId,
        new : false }
    });
  }
  const id = props.id;
    return(
   
    <li className="DialogElemLi" onClick={routeChange}>
      {/* <div style={{'position': "relative"}}>
    <img
       src={require('./addStandard.png')} className="addIcon" 
       alt="x"
       onMouseOver={e => (e.currentTarget.src = require('./addHover.png'))}
       onMouseOut={e => (e.currentTarget.src = require('./addStandard.png'))}
      //  onClick={()=> {
      //   this.setState({showPopup2: true, thisUtterance: m.id})
      //  }}
       />
    </div> */}
       <div  className="DialogElemA">
    <div className="row">    
    
    <div className="col-3 DialogElem">
    
    <p className={"DialogElemP"}>{props.Name}</p>
        {/* {response}
        */}
    </div>
    
    
</div>
</div>
</li>

)
}

export default DialogSum;