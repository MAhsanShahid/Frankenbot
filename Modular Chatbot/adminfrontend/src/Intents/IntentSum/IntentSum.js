import React from 'react';
import './IntentSum.css';
const IntentSum = props => {

  const goABitLeft = {
    position: 'relative',
    left: '3.5%',
  };
  const goABitRight = {
    position: 'relative',
    left: '9%',
  };
  const id = props.id;
  const routeChange = () => {
    let path = 'Intents:' + id;
    props.History.push({
      pathname: path,
      search: '',
      state: { 
        name: props.Name,
        description:props.Desc,
        new : false }
    });
  }
    return(
<React.Fragment>
      {/* //href={'http://localhost:3000/Intents:' + id} */}
    <div onClick={routeChange} className="intentElemA">
    <li className="IntentElemLi">
    <div className="row">    
    <div className="col-3 intentElem">
    <p className={"intentElemP"}>{props.Name}</p>
    </div>
    <div className="col-7 intentElem" style={goABitLeft}>
    <p className={"intentElemP"}>{props.Desc}</p>
    </div>
    <div className="col-2 intentElem" style={goABitRight}>
    <p className={"intentElemP"}>{props.ExCount}</p>
    </div>
</div>
</li>
</div>
</React.Fragment>
)
}

export default IntentSum;