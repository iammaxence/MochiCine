import React from 'react';
import axios from "axios";
import EcrireMessage from "./EcrireMessage";



class Commentaires extends React.Component {
	constructor(props){
    	super(props); //login, id_message
    	this.state={comments:[], statut:"", textError:""};
    	this.deleteMessage=this.deleteMessage.bind(this);
	}

	//Mettre a jour s'il y a un changement dans la liste de commentaire
	componentDidMount(){
		this.setState({comments: this.props.comments});
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
		//console.log(rep.data)
		if(rep.data["code"]){
	  		this.setState ({statut: "error", textError: rep.data["commentaire"]});
	  		window.confirm(this.state.textError);
	  	}else{
	  		this.state.comments.unshift(rep.data["comment"]);
	  	}
	}


	deleteMessage(id_commentaire, index){
		if(window.confirm("Voulez-vous supprimer ce commentaire ?")){
			const url = new URLSearchParams();
            url.append('id_message', this.props.id_message);
            url.append('comId', id_commentaire);
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
		let box;
		(this.props.isConnected === true)? box = <EcrireMessage addComment={this.addComment.bind(this)} /> : box = <div></div>;
		return(<div className="liste_com">
			{box}
			<span></span>
			<ul className="list-group">
				{this.state.comments.map((item, index) => <div key={item._id} ></div>)}
			</ul>
		</div>);
	}
}

export default Commentaires