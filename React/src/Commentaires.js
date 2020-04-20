import React from 'react';
import axios from "axios";
import EcrireMessage from "./EcrireMessage";



class Message extends React.Component {
	constructor(props){
    	super(props); //login, titre
    	this.state={messages:[], statut:"", textError:""};
    	this.deleteMessage=this.deleteMessage.bind(this);
	}

	//Mettre a jour s'il y a un changement dans la liste de commentaire
	componentDidMount(){
		this.setState({messages: this.props.message});
	}

	//Gestion Commentaire:
	addComment(comment){
		if(comment.length >0){
			const url = new URLSearchParams();
 			url.append('user',this.props.login);
 			url.append('titre', this.props.titre);
 			url.append('comment', comment);
 			axios.get('http://localhost:8080/MochiCine/Comment/Add?' + url).then(response => this.updateComment(response));
		}
	}
	updateComment(rep){
		//console.log(rep.data)
		if(rep.data["code"]){
	  		this.setState ({statut: "error", textError: rep.data["commentaire"]});
	  		window.confirm(this.state.textError);
	  	}else{
	  		let newList = this.state.comments;
	  		newList.push(rep.data["commentaire"])
			this.setState({comments: newList});
	  	}
	}


	deleteMessage(id_commentaire, index){
		if(window.confirm("Voulez-vous supprimer ce commentaire ?")){
			const url = new URLSearchParams();
            url.append('user',this.props.login);
            url.append('titre', this.props.titre);
            url.append('comId', comment);
 			axios.get('http://localhost:8080/MochiCine/Comment/Delete?' + url).then(response => this.delete(response, index));
		}
	}
	delete(rep, index){
		if(rep.data["code"]){
	  		this.setState ({statut: "error", textError: rep.data["message"]});
	  		window.confirm(this.state.textError);
	  	}else{
	  		//Cela permet de faire une copie de notre liste de message:
	  		const newList = Object.assign([], this.state.comments);
	  		newList.splice(index,1);
	  		//console.log("deleteCommentaire: ",newList);
	  		this.setState({comments: newList});
	  	}
	}



	render(){
		return(<div className="liste_com">
			<EcrireMessage addComment={this.addComment.bind(this)} /> 
			<span></span>
			<ul className="list-group">
				{this.state.comments.map((item, index) => <div key={item.key} ></div>)}
			</ul>
		</div>);
	}
}

export default Commentaires