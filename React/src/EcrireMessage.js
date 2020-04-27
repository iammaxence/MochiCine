import React from 'react';

class EcrireMessage extends React.Component {
  constructor(props){
    super(props);
    this.state={newMsg:""};

	}
	
	sendMessage(e){
		e.preventDefault();
		this.props.addMessage(this.state.newMsg);
		this.setState({newMsg:""});
	}
	
	handleMessage(e){
        this.setState({newMsg: e.target.value});
	}

	render(){
		return(
		<div className="new_message">
				<textarea className="form-control" rows="2" autoComplete="off" placeholder=' Add a Comment..' value={this.state.newMsg} onChange={(e) => this.handleMessage(e)} ></textarea>
		      	<button className="btn btn-sm btn-outline-info" onClick={(e) => this.sendMessage(e)}>Comment</button>
		</div>);
	}
}
export default EcrireMessage