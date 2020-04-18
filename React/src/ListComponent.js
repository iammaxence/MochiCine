import React from 'react';
import axios from "axios";

class ListComponent extends React.Component {
    constructor(props){
        super(props); //login, pagecourante
        this.state ={items: [], taille: 0};

    }

    componentDidMount(){
        // Recupere les Favoris de l'utilisateur 
        if(this.props.pagecourante === "Profil"){
            const url = new URLSearchParams();
            url.append('login', this.props.login);
            axios.get('http://localhost:8080/MochiCine/Favoris/List?'+url).then(response => this.lister(response));
        }else if(this.props.pagecourante === "Accueil"){
            //Recupere les films et series populaires en ce moment (actualité ou top 10)
            let list = [{id: 'a', title:'film1', picture:'film1.png'}, {id: 'b', title:'film2', picture:'film2.png'}]
            this.setState({items: list, taille: 2});
        }
    }

    lister(rep){
        if(rep.data["code"]){
            this.setState ({statut: "error", textError: rep.data["message"]});
            window.confirm(this.state.textError);
        }else{

            //this.setState({items: rep.data["message_profil"]});
            //this.setState({taille : this.state.messages.length})


        } 
    }


    render(){
        return(
            <div className="ListComponent">
                <ul className="list-group">
                    {this.state.items.map( item => (
                        <li className="list-group-item list-group-item-dark" key = {item.id}>
                            <div>{item.title}</div>
                            <div>picture</div>
                        </li>
                    ))}
                </ul>
            </div>
        );
    }



}

export default ListComponent;