import React from 'react';
import EcrireMessage from './EcrireMessage';
import axios from "axios";


class ListMessage extends React.Component {
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
	  	if(rep.data["code"]){
	  		this.setState ({statut: "error", textError: rep.data["message"]});
	  		window.confirm(this.state.textError);
	  	}else{
	  	  	this.setState({messages: rep.data["message_profil"]});
	  	  	this.setState({taille : this.state.messages.length})
	  	} 
 	}


 	//Ajout d'un message:
	addMessage(msg){
		if(msg.length >0){
			const url = new URLSearchParams();
 			url.append('login',this.props.login);
 			url.append('titre', this.props.titre);
 			url.append('text', msg);
 			axios.get('http://localhost:8080/MochiCine/Message/Add?' + url).then(response => this.updateMessage(response));
		
		}
	}
	updateMessage(rep){
		console.log("addMessage: ", rep.data);
		if(rep.data["code"]){
	  		this.setState ({statut: "error", textError: rep.data["message"]});
	  		window.confirm(this.state.textError);
	  	}else{
	  		//Ajout en tete de liste
	  		let newList = [];
	  		newList.push(rep.data["message"])
	  		this.state.messages.map((item) => newList.push(item));
			this.setState({messages: newList});
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
	  		this.setState({messages: newList});
	  	}
	}


	render(){
		return(
        <div className="liste_message">
			<EcrireMessage addMessage={this.addMessage} /> 

			{this.state.messages.map((item, index) => 
                <div>

                </div>)}

        </div>
        );
	}
}

export default ListMessage;