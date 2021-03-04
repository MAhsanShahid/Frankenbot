import React, {Component} from 'react';
import Button from '../UI/Button/Button';
import './Dialogs.css';
import DialogSum from './DialogSum/DialogSum';
import {BrowserRouter} from 'react-router-dom';

class Dialogs extends Component { 
    
render() {
        
    const thisButtonShouldGoLeft={
        position:'relative',
        left:'-70%',
        top:'-90%',
        height: '140%',
        backgroundColor:"rgb(66, 172, 66)",
    }
    const dummy=[
        {
            'Name': "example",
            'Desc': "Dummy dialog for testing",
            'ExCount':"4",
            'id':'1'
        },
        {
            'Name': "example2",
            'Desc': "More Dummy dialog for testing",
            'ExCount':"3",
            'id':'2'
        },
        {
            'Name': "example3",
            'Desc': "This one has a very very very very very very very very very very very very very very very very very very very very long description",
            'ExCount':"1",
            'id':'3'
        },
        {
            'Name': "example4",
            'Desc': "Even more Dummy dialog for testing",
            'ExCount':"5",
            'id':'4'
        }
    ];
    var IntensElems= dummy.map((i => {return(<DialogSum 
        History={this.props.history}
        Name={i['Name']} 
        Desc={i['Desc']} 
        responseCount={i['ExCount']} 
        id={i['id']} />);}))
    
    
    return(
        <BrowserRouter>
            <br></br>
            <br></br>
           
                <div className="row">    
                <div className="col-1 IntDivStyle">
                <Button style={thisButtonShouldGoLeft} text="Add New +" />
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
export default Dialogs;