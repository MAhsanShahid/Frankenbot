import React, {Component} from 'react';
import './Popup.css';
import Button from '../Button/Button';

class Popup extends Component {
    render() {
      return (
        <div className='popup'>
          <div className='popup_inner'>
            <h3>{this.props.text}</h3>
          <Button style ={{position:'relative',
      left:'-3%'}} onClick={this.props.closePopup} text="No, cancel"></Button>
          <Button style={{backgroundColor:'rgb(228, 91, 91)',position:'relative',
      left:'-3%',}} onClick={this.props.clickHandler} text="Yes, Delete"></Button>
          </div>
        </div>
      );
    }
  }

  export default Popup;