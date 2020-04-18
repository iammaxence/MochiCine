import React from 'react';
import NavBar from './NavBar';
import axios from 'axios';
import './SearchPage.css'

class SearchPage extends React.Component{
    _isMounted = false

    constructor(props){
        super(props);
        this.state={listeRecherche: []};
        this.addToList=this.addToList.bind(this);
    }

   
    componentDidMount(){
       this._isMounted=true;
        axios.get("http://localhost:8080/MochiCine/Search?key=a3be1be132d237a0716cc27bdae1b2f0&keyword="+this.props.keyword).then(res => this.addToList(res.data.data));
     }

    
    componentDidUpdate(prevProps){
        //console.log(this.props.keyword+" == "+prevProps.keyword);
        if(this.props.keyword !== prevProps.keyword && this._isMounted===true){
            this.setState({listeRecherche: this.props.keyword});
            axios.get("http://localhost:8080/MochiCine/Search?key=a3be1be132d237a0716cc27bdae1b2f0&keyword="+this.props.keyword).then(res => this.addToList(res.data.data));
        }
    }
    

    componentWillUnmount(){
       this._isMounted =false
       this.setState({listeRecherche: []});
    }

    addToList(res){
        //console.log(res);
        if(this._isMounted)
            this.setState({listeRecherche: res});
        else
            alert("nope");
    }

    render(){
        //console.log(document.getElementById("recherche").value);
        let tmp;
        console.log(this.state.listeRecherche);
        
        if (this.state.listeRecherche.length === 0 ){
            tmp= <div><strong>Nothing found..</strong></div>
        }
        else if (!Array.isArray(this.state.listeRecherche)) { //Cette condition est obligatoire car lorsque la navbar appel ce composant, il est démonté puis remonté. Il n'est pas monté assez rapidement pour laisser le temps à la requête d'être terminé
            tmp= <div><strong>Chargement en cours..</strong></div>
        } 
        else {
            tmp= this.state.listeRecherche.map(ex => {
                //console.log(ex);
                // Parfois c'est title parfois c'est name pour chaque séries/films
                if(ex.backdrop_path!==null && ex.backdrop_path!==undefined){
                    return (
                        <div>
                            <div className="affiches">
                                <img src={"https://image.tmdb.org/t/p/w500/"+ex.backdrop_path} alt={"pic_of_"+ex.name} width="100%" height="150"/>
                            </div>
                            <div className="description">
                                <strong>Titre : {ex.title} </strong>
                                <strong>Description : {ex.overview} </strong>
                            </div>
                            
                        </div>
                    )
                }
            })
        }
        
        return(
            <div className="searchPage">
                <NavBar 
                    isConnected={this.props.isConnected} 
                    getLoginPage={this.props.getLoginPage}
                    setLogout={this.props.setLogout}
                    getProfilPage={this.props.getProfilPage}
                    getAccueilPage={this.props.getAccueilPage} 
                    getSearchPage={this.props.getSearchPage}/>
                <div className="divsearch">
                    {tmp}
                </div>
            </div>
        )
    }
}

export default SearchPage;