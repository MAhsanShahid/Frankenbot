import React, { Component } from 'react';
import './Entity.css';
import Input from './../../UI/Input/Input';
import Button from '../../UI/Button/Button';
import { DropdownButton, Dropdown } from 'react-bootstrap';

class Entity extends Component {
    state = {
        fieldCount: 0
    }
    addField = () => {
        this.setState({
            fieldCount: this.state.fieldCount + 1
        });
    }
    render() {
        let card = [];
        const fullWidth2 = { 
            width: '150%',
            position: 'relative',
            left: '-30%',
            top: '7px' 
        }
        for (var i = 0; i < this.state.fieldCount; i += 1) {
            card.push(<div className="col-1"><Input style={fullWidth2} key={i}></Input></div>);
        };
        const id = this.props.id;
        const fullWidth = { width: '100%' }
        const thisButtonShouldGoLeft = {
            position: 'relative',
            left: '-90%',
            top: '26%'
        }
        const goGreen = { 
            position: 'relative',
            left: '10%',
            backgroundColor:"rgb(66, 172, 66)",
        }
        const thisButtonShouldGoLeft2 = {
            position: 'relative',
            left: '-5%',
            top: '12px',
            margin: "5px",
        }
        const thisButtonShouldGoLeftandRed = {
            position: 'relative',
            left: '-90%',
            top: '26%',
            backgroundColor: 'rgb(228, 91, 91)',
        }
        const bordered = {
            border: '1px solid black',
        }
        // let fields = Array.from(Array(fieldCount), () => {
        //     return(
        //         <div className="col-1" style={thisButtonShouldGoLeft2}>
        //             <Input>asds</Input>
        //         </div>
        //     )
        // });


        return (
            <div className="EntityReposition">
                <div className="row">
                    <div className="col-10">
                        <Input style={fullWidth} label="Entity Name" value="Example"></Input>
                    </div>
                    <div className="col-1">
                        <Button style={thisButtonShouldGoLeft} text="Save Entity"></Button>
                    </div>
                    <div className="col-1">
                        <Button style={thisButtonShouldGoLeftandRed} text="Delete"></Button>
                    </div>
                </div>
                <div className="row">
                    <div className="col-2">
                        <Input style={fullWidth} label="Value Name"></Input>
                    </div>
                    <div style={thisButtonShouldGoLeft2} className="col-1">
                        <DropdownButton bsPrefix="CustomDropdown" id="dropdown-basic-button" title="Synonym">
                            <Dropdown.Item href="#/action-1">Synonym</Dropdown.Item>
                            <Dropdown.Item href="#/action-2">Antagonym</Dropdown.Item>
                        </DropdownButton>
                    </div>
                    {card}
                    <div className="col-1" style={thisButtonShouldGoLeft2}>
                        <Button text="Add Field" onClick={this.addField}></Button>
                    </div>
                    <div style={thisButtonShouldGoLeft2}>
                    <Button text="Add Value" style={goGreen}></Button>
                    </div>
                    
                </div>
                <br />
                <div className="row">
                    <div className="col-2">
                        <h4>Dictionary</h4>
                    </div>
                    <div className="col-1">
                        <h4>Type</h4>
                    </div>

                </div>
                <hr />
                <div className="row">
                    <div className="col-2">
                <ul>
                    <li className="goABitLeftE">Some example</li>
                    <li className="goABitLeftE">Some longer example1</li>
                    <li className="goABitLeftE">Some much much much much much longer example2</li>
                    <li className="goABitLeftE">Some example3</li>
                    <li className="goABitLeftE">Some example4</li>
                    <li className="goABitLeftE">Some example5</li>
                    <li className="goABitLeftE">Some example6</li>
                </ul>
                </div>
                <div className="col-2">
                <ul>
                    <li className="goABitLeftT">Synonym</li>
                    <li className="goABitLeftT">Synonym</li>
                    <li className="goABitLeftT">Synonym</li>
                    <li className="goABitLeftT">Synonym</li>
                    <br></br>
                    <li className="goABitLeftT">Synonym</li>
                    <li className="goABitLeftT">Synonym</li>
                    <li className="goABitLeftT">Synonym</li>
                </ul>
                </div>
                <div className="col-8">
                <ul>
                    <li className="goABitLeftT">example synonym 1, example synonym 2</li>
                    <li className="goABitLeftT">example synonym </li>
                    <li className="goABitLeftT">example synonym </li>
                    <li className="goABitLeftT">Synonym, bla bla bal, bla</li>
                    <br></br>
                    <li className="goABitLeftT">example synonym </li>
                    <li className="goABitLeftT">Synexample synonym onym</li>
                    <li className="goABitLeftT">Synonym</li>
                </ul>
                </div>
            </div>
            </div>
        )
    }
}

export default Entity;