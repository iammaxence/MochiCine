import React from 'react';
import axios from "axios";
import EcrireMessage from "./EcrireMessage";



class Commentaires extends React.Component {
	constructor(props){
    	super(props); //login, id_message, comments, isconnected
    	this.state={comments:[], statut:"", textError:"", taille: 0};
    	this.deleteMessage=this.deleteMessage.bind(this);
	}

	//Mettre a jour s'il y a un changement dans la liste de commentaire
	componentDidMount(){
		this.setState({comments: this.props.comments, taille: this.props.comments.length});
	}

	//Gestion Commentaire:
	addComment(comment){
		if(comment.length >0){
			const url = new URLSearchParams();
 			url.append('user',this.props.login);
 			url.append('id_message', this.props.id_message);
 			url.append('comment', comment);
 			axios.get('http://localhost:8080/MochiCine/Comment/Add?' + url).then(response => this.updateComment(response));
		}
	}
	updateComment(rep){
		console.log("addComment",rep.data)
		if(rep.data["code"]){
	  		this.setState ({statut: "error", textError: rep.data["commentaire"]});
	  		window.confirm(this.state.textError);
	  	}else{
			  this.state.comments.push(rep.data["comment"]);
			  this.setState({taille: (this.state.taille + 1)});
	  	}
	}

	//String id_commentaire, String id_message
	deleteMessage(id_commentaire, index){
		if(window.confirm("Voulez-vous supprimer ce commentaire ?")){
			const url = new URLSearchParams();
            url.append('id_message', this.props.id_message);
            url.append('idCom', id_commentaire);
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

	getListCommentaires(){
		if(this.state.taille !== 0){
			return(
				this.state.comments.map((item, index) => 
				<div  className="list-group-item" key={item._id}>
            		<small className="date text-muted">{item.date}</small>
					<div>
						<small className="list-group-item-heading text-primary">{item.login}</small>
						<p className="list-group-item-text">
							{item.commentaire}
						</p>
						{(this.props.login === item.login)? <button className="btn btn-sm " onClick={()=> this.deleteMessage(item._id, index)}><i className="fas fa-trash-alt"></i></button> : <p></p>}
					</div>

				</div>
					));
			}
	}



	render(){
		let name = "collapseCom"+this.props.id_message;
		let box;
		(this.props.isConnected === true)? box = <EcrireMessage addMessage={this.addComment.bind(this)} /> : box = <div></div>;
		return(
		<div className="comments-group" >
			<button className="btn fas fa-comment" data-toggle="collapse" data-target={"#"+name} aria-expanded="false">
			</button>
			&nbsp;&nbsp;&nbsp;&nbsp;
			<div className="collapse" id={name}>
				{this.getListCommentaires()}
				{box}
			</div>
		</div>
);
	}
}

export default Commentaires