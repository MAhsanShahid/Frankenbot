import React, {Component} from 'react';
import Button from '../UI/Button/Button';
import './Dialogs.css';
import DialogSum from './DialogSum/DialogSum';
import {connect} from "react-redux";
import {loadDialog, resetDialog} from '../store/actions';
import axios from "axios";
class Dialogs extends Component { 
    state={
        dialog:[]
    }
    recursiveChildren = (child, parentId) => {
        if(child["children"].length > 0)
        return (<div className="Block">
            <div className="verticallyCentered">
            <DialogSum
            History={this.props.history}
            Name={child['name']} 
            id={child['id']}
            parentId={parentId}
            condition={child['dialogNodeIntentConditions'].length > 0 ? child["dialogNodeIntentConditions"][0]["intent"]["name"] : []}
            conditionId={child['dialogNodeIntentConditions'].length > 0 ? child['dialogNodeIntentConditions'][0]["id"] : []}
            answer={child['dialogNodeTextAnswers'].length > 0 ? child['dialogNodeTextAnswers'][0]["text"] : []}
            answerId={child['dialogNodeTextAnswers'].length > 0 ? child['dialogNodeTextAnswers'][0]["id"] : []} />
            <div className="EmptySpace">
            {child["children"].map(i => this.recursiveChildren(i))}
            </div>
        </div></div>)
        else return (<DialogSum
            History={this.props.history}
            Name={child['name']} 
            id={child['id']}
            parentId={parentId}
            condition={child['dialogNodeIntentConditions'].length > 0 ? child["dialogNodeIntentConditions"][0]["intent"]["name"] : []}
            conditionId={child['dialogNodeIntentConditions'].length > 0 ? child['dialogNodeIntentConditions'][0]["id"] : []}
            answer={child['dialogNodeTextAnswers'].length > 0 ? child['dialogNodeTextAnswers'][0]["text"] : []}
            answerId={child['dialogNodeTextAnswers'].length > 0 ? child['dialogNodeTextAnswers'][0]["id"] : []} />)
    }
    
render() {
        
    const thisButtonShouldGoLeft={
        position:'relative',
        // left:'-70%',
        // top:'-38%',
        height: '70px',
        backgroundColor:"rgb(66, 172, 66)",
    }

    

    var IntensElems = this.props.Dialog.map(i => {
        if(i["children"].length !== 0){
        return(
            <div className="Block">
            <div className="verticallyCentered">
            <DialogSum
                History={this.props.history}
                Name={i['Name']} 
                id={i['id']}
                parentId={null}
                condition={i['condition'].length > 0 && i['condition'][0]["intent"] ? i["condition"][0]["intent"]["name"] : []}
                conditionId={i['condition'].length > 0 ? i["condition"][0]["id"] : []}
                answer={i['answer'].length > 0  ? i['answer'][0]["text"] : []}
                answerId={i['answer'].length > 0 ? i['answer'][0]["id"] : []} />
                </div>
                <div className="EmptySpace">
                {i["children"].map(child => (this.recursiveChildren(child, i['id'])))}
                </div>
                </div>
                )
        } else {
        return(
            <div className="Block3">
        <DialogSum
                History={this.props.history}
                Name={i['Name']} 
                id={i['id']}
                parentId={null}
                condition={i['condition'].length > 0 && i['condition'][0]["intent"] ? i["condition"][0]["intent"]["name"] : []}
                conditionId={i['condition'].length > 0 ? i["condition"][0]["id"] : []}
                answer={i['answer'].length > 0 ? i['answer'][0]["text"] : []}
                answerId={i['answer'].length > 0 ? i['answer'][0]["id"] : []} /></div>
        )
    };
})
    
    
    return(
        <div>
            <div className="row DialogTitle">
            <div className="col-10">
                
            <h2>Dialog</h2>
            </div>
            </div>
            <div className="row">
            <div className="col-11 IntDivStyle2">

            <Button 
                style={thisButtonShouldGoLeft} 
                text="Add new node"
                onClick={() =>
                    axios.post("/api/dialog/node", {
                        "defaultModule": {
                            "id": this.props.module_id
                        },
                        "name": "New Node",
                    }
                ).then(res => {
                    this.props.resetDialog();
                    this.props.loadD(this.props.module_id);
                //   
                    this.props.history.push(
                    {
                    pathname: 'Dialogs:' + res.data.id,
                    search: '',
                    state: { 
                      name: "",
                      condition:"",
                      conditionId:"",
                      answer: "",
                      answerId: null,
                      parentId: null,
                      new : false }
                  }
                  );
                  })
                    
                } />
                                
                                </div>
            </div>
            <br></br>
            <br></br>
           
                {/* <div className="row">    
                <div className="col-1 IntDivStyle">
                
                </div>
                </div> */}

            <div className="Block">
            <img 
            src="https://img.icons8.com/dotty/80/000000/broken-robot.png"
            height="45"
            width="45"
            />
                <ul>
                {IntensElems}
                </ul>
            </div>
        </div>
        )
}
}
const mapStateToProps = state => {
    return {
      Dialog: state.Dialog,
      module_id: state.module_id,
    };
  }
  const mapDispatchToProps = dispatch => {
    return {
        loadD: id => dispatch(loadDialog(id)),
        resetDialog : () => dispatch(resetDialog()),
    };
    };
  export default connect(mapStateToProps, mapDispatchToProps)(Dialogs);
  