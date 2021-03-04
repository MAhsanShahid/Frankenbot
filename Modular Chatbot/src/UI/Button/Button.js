import React from 'react';
import './Button.css';

const Button = props => <button {...props} className="custombutton"><span>{props.text}</span></button>


export default Button;