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

    formatDate(string){
        var options = { year: 'numeric', month: 'long', day: 'numeric' };
        return new Date(string).toLocaleDateString([],options);
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
        
        if (this.state.listeRecherche === undefined || this.state.listeRecherche.length === 0 ){
            tmp= <div><strong>Nothing found..</strong></div>
        }
        else if (!Array.isArray(this.state.listeRecherche)) { //Cette condition est obligatoire car lorsque la navbar appel ce composant, il est démonté puis remonté. Il n'est pas monté assez rapidement pour laisser le temps à la requête d'être terminé
            tmp= <div><strong>Chargement en cours..</strong></div>
        } 
        else {
            tmp= this.state.listeRecherche.map(ex => {
                //console.log(ex);
                
                let nb = (ex.number_of_seasons || 0);
                let choix;
                if(nb === 0){
                    choix="film";
                }else{
                    choix=nb+" saisons";
                }

                // Favoris button

                /* /!\ POUR LAETITIA 
                    Bonjour Laeti (ou bonsoir), j'ai mit en place le bouton favoris juste en dessous de ce texte. Le onClick est juste une alert,
                    tu peux le retirer pour y mettre les fonctions dont tu as besoin pour l'ajout ou la supression de favoris. La condition du if 
                    est à false (C'était pour des test). La vrai condition serait quelque chose du genre : if (this.props.isFavoris.includes (ex.id)).
                    Ca verfie que l'id est présent (ou non) dans la liste des favoris.
                    Bon courage :poucelevé:
                */
                let btn;
                if(false){
                    btn =<p>
                            <button className="btn btn-sm btn-outline-danger" type="button" onClick={() => alert("deletefav")}>Delete Favoris</button>
                        </p>
                }
                else{
                    btn =<p>
                            <button className="btn btn-sm btn-outline-success" type="button" onClick={() => alert("addfav")}>Add Favoris</button>
                        </p>
                }
                // Parfois c'est title parfois c'est name pour chaque séries/films
                if(ex.backdrop_path!==null && ex.backdrop_path!==undefined){
                    return (
                        <div className="affiches">
                            <div className="col-md-12 bg-white text-dark" key={ex.id}>
                            <div className="blog-entry  col-12">
                                <div id = "leftbox" >
                                    <img src={"https://image.tmdb.org/t/p/w500/"+ex.backdrop_path} alt={"pic_of_"+ex.title}   />
                                </div>
                                <div className= "description">
                                    <div className="text text-2 text-center pl-md-4">
                                        <h3 className="mb-2"  onClick={() => this.props.getDescriptionPage(ex)} >{(ex.original_title || ex.original_name)}</h3>
                                    </div>
                                    <div className="meta-wrap">
                                        <p className="meta">
                                            <span><i className="far fa-calendar-alt mr-2"></i>{this.formatDate((ex.first_air_date || ex.release_date))}</span>
                                            &nbsp;&nbsp;&nbsp;&nbsp;
                                            <span><i className="fas fa-fire-alt mr-2"></i>{ex.popularity}</span>
                                            &nbsp;&nbsp;&nbsp;&nbsp;
                                            <span><i className="fas fa-tv mr-2"></i>{ choix } </span>
                                        </p>
                                    </div>
                                    <p className="mb-4">
                                        {ex.overview}
                                    </p>
                                    {btn}
                                </div>
                            </div>
                            </div>
                        </div>
                    )
                }
            })
        }
        
        return(
            <div >
                <NavBar 
                    isConnected={this.props.isConnected} 
                    getLoginPage={this.props.getLoginPage}
                    setLogout={this.props.setLogout}
                    getProfilPage={this.props.getProfilPage}
                    getAccueilPage={this.props.getAccueilPage} 
                    getSearchPage={this.props.getSearchPage}/>
                <div className="searchPage">
                    {tmp}
                </div>
            </div>
        )
    }
}

export default SearchPage;