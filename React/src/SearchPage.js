import React from 'react';
import NavBar from './NavBar';
import axios from 'axios';
import './SearchPage.css'

class SearchPage extends React.Component{
    _isMounted = false

    constructor(props){
        super(props);
        this.state={listeRecherche: [], UserFavs: this.props.listFavoris};
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

    // GESTION DESCRIPTION PAGE
    handleDescriptionPage(id, isMovie){
        const url = new URLSearchParams();
        url.append('id',id);
        url.append('isMovie',isMovie);
        axios.get('http://localhost:8080/MochiCine/GetDescription?'+url).then(response => this.getDescription(response));
    
    }

    getDescription(rep){
        console.log("data of Description: ", rep.data);
        if(rep.data != null){
            if(rep.data["code"]){
                window.confirm(this.state.textError);
            }else{
                this.props.getDescriptionPage(rep.data["info"]);
            } 
        }
    }

    //FAVORIS
    handleAddFav(id, title, isSerie){
        this.props.addFavoris(id, title, isSerie)
        this.setState({UserFavs: this.props.listFavoris});
    }

    handleDeleteFav(id, title, isSerie){   
        const list = Object.assign([], this.state.UserFavs);
        var index = list.indexOf(title);
        if (index !== -1) {
            list.splice(index, 1);
            this.setState({UserFavs: list});
        }

        this.props.deleteFavoris(id, title, isSerie)
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
                
                let choix = ex.media_type;

                // Favoris button
                let btn;
                let isSerie;
                (choix === "movie")? isSerie = "false" : isSerie = "true";
                let titre = (ex.original_title || ex.original_name);
                if(this.state.UserFavs.includes(titre)){
                    btn =<p>
                            <button className="btn btn-sm btn-outline-danger" type="button" onClick={() => this.handleDeleteFav(ex.id, titre, isSerie)}>Delete Favoris</button>
                        </p>
                }
                else{
                    btn =<p>
                            <button className="btn btn-sm btn-outline-success" type="button" onClick={() => this.handleAddFav(ex.id, titre, isSerie)}>Add Favoris</button>
                        </p>
                }
                // Parfois c'est title parfois c'est name pour chaque séries/films
                if(ex.backdrop_path!==null && ex.backdrop_path!==undefined){
                    return (
                            <div className="affiches col-md-11 bg-white text-dark" key={ex.id}>
                            <div className="blog-entry  col-12">
                                <div id = "leftbox" >
                                    <img src={"https://image.tmdb.org/t/p/w500/"+ex.backdrop_path} alt={"pic_of_"+ex.title}   />
                                </div>
                                <div className= "description">
                                    <div className="text text-2 text-center pl-md-4">
                                        <h3 className="mb-2"  onClick={() => this.handleDescriptionPage(ex.id, !isSerie)} >{(ex.original_title || ex.original_name)}</h3>
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
                <div className="searchPage col">
                    <div className="row pt-md-3">
                        {tmp}
                    </div>
                </div>
            </div>
        )
    }
}

export default SearchPage;