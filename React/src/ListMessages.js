import React from 'react';
import EcrireMessage from './EcrireMessage';
import axios from "axios";
import Commentaires from './Commentaires';


class ListMessages extends React.Component {
	constructor(props){
    	super(props); //titre, login
    	this.state={messages:[], statut: "", textError: "", taille : 0};
    	this.addMessage = this.addMessage.bind(this);
    	this.supprimerMessage = this.supprimerMessage.bind(this);

	}
	componentDidMount(){
		const url = new URLSearchParams();
 		url.append('titre', this.props.titre);
 		axios.get('http://localhost:8080/MochiCine/Message/List?' + url).then(response => this.lister(response));
	}

	lister(rep){
		  console.log(rep.data);
		  if(rep.data !== null){
			if(rep.data["code"]){
				this.setState ({statut: "error", textError: rep.data["message"]});
				window.confirm(this.state.textError);
			}else{
				this.setState({messages: rep.data["data"], taille : rep.data["data"].length});
			} 
		}
 	}


 	//Ajout d'un message:
	addMessage(msg){
		if(msg.length >0){
			try{
				const url = new URLSearchParams();
				url.append('login',this.props.login);
				url.append('titre', this.props.titre);
				url.append('text', msg);
				axios.get('http://localhost:8080/MochiCine/Message/Add?' + url).then(response => this.updateMessage(response));
			}catch(error){
				console.log("Axios Ajout de message")
			}
		}
	}
	updateMessage(rep){
		console.log("addMessage: ", rep.data);
		if(rep.data["code"]){
	  		this.setState ({statut: "error", textError: rep.data["message"]});
	  		window.confirm(this.state.textError);
	  	}else{
			this.state.messages.push(rep.data["message"]);
			this.setState({ taille : (this.state.taille+1)});		
	  	}

	}

	//Suppression d'un message:
	supprimerMessage(id_message, index){
		if(window.confirm("Voulez-vous supprimer ce message ?")){
			const url = new URLSearchParams();
 			url.append('id_message', id_message);
 			axios.get('http://localhost:8080/MochiCine/Message/Delete?' + url).then(response => this.delete(response, index));
		}
    }
    
	delete(rep, index){
		if(rep.data["code"]){
	  		this.setState ({statut: "error", textError: rep.data["message"]});
	  		window.confirm(this.state.textError);
	  	}else{
	  		const newList = Object.assign([], this.state.messages);
	  		newList.splice(index,1);
	  		this.setState({messages: newList, taille : (this.state.taille-1)});
	  	}
	}


	getListMessage(){
		if(this.state.taille !== 0){
			return(
				this.state.messages.map((item, index) => 
				<li className="list-group-item" key={item._id}>
            		<small className="date text-muted">{item.date}</small>
					<div>
						<small className="list-group-item-heading text-primary">{item.login}</small>
						<p className="list-group-item-text">
							{item.text}
						</p>
						{(this.props.login === item.login)? <button className="btn btn-sm corbeille" onClick={()=> this.supprimerMessage(item._id, index)}><i className="fas fa-trash-alt"></i></button> : <p></p>}
						
						<Commentaires login={this.props.login} id_message={item._id} comments={item.comments} isConnected={this.props.isConnected} />
					</div>

				</li>
					));
		}
	}

	render(){
		let box;
        (this.props.isConnected === true)? box =<EcrireMessage  addMessage={this.addMessage} /> : box = <div></div>;

		return(
			<div className="liste_message">
				{box}
				<ul className="list-group">
					{this.getListMessage()}
				</ul>
				

			</div>	
        );
	}
}

export default ListMessages;