import React, { Component } from 'react';
import './Calendrier.css';
import axios from 'axios';
import StarRatings from '../node_modules/react-star-ratings';
import favEmpty from './icons/favEmpty.png';
import favFull from './icons/favFull.png';

class Calendrier extends Component{

    constructor(props){
        super(props);
        this.state={seriesListe : [], filmsListe: [], UserFavs:this.props.listFavoris};
        //this.addToFavoris=this.addToFavoris.bind();
    }

    componentDidMount(){
        console.log("Amount Calendrier", this.state.UserFavs);
        
        //Today release
        //axios.get("https://api.themoviedb.org/3/tv/airing_today?api_key=a3be1be132d237a0716cc27bdae1b2f0&language=en-US&page=1").then(res=> this.constr(res));
        
        //Future release
        axios.get("http://localhost:8080/MochiCine/SerieOfTheWeek").then(res=> this.setState({seriesListe: res.data.data}));

        axios.get("http://localhost:8080/MochiCine/FilmsOnAir").then(res=> this.setState({filmsListe: res.data.data}));
        /*
        axios.get("https://api.themoviedb.org/3/tv/on_the_air?api_key=a3be1be132d237a0716cc27bdae1b2f0&language=en-US&page=1").then(res=> res.data.results).then(
            ex => {
                
                //Find the serie to find the date of the last episode
                //Cette partie est importante car on récupère beaucoup plus d'informations sur chacune des séries, que la requete précédente.
                ex.map( key =>
                    axios.get("https://api.themoviedb.org/3/tv/"+key.id+"?api_key=a3be1be132d237a0716cc27bdae1b2f0&language=en-US").then(
                    v => {
                        
                    let tvserie=v.data;
                    let exdate= new Date(tvserie.last_air_date);
                    exdate.setHours(0,0,0,0);
                    //console.log(exdate);
                    //console.log(today.getTime()+"=="+exdate.getTime());
                    //if (today.getTime()===exdate.getTime()) 
                        //{   
                            //Black Magic
                            this.setState({lundiListe: [...this.state.lundiListe, tvserie]});
                        //} 
                    })
                )
                    
                });
        //console.log(this.state.lundiListe);
        //this.setState({lundiListe: mo});
        */
    }

    
    //Si series dans les favoris d'une utilisateur, coeur rempli sinon vide
    handleAddFav(id, title, isSerie){
        this.state.UserFavs.push(title);
        console.log("addCalendrier", this.state.UserFavs);
        this.props.addFavoris(id, title, isSerie)
    }

    handleDeleteFav(id, title, isSerie){
        /*
        const list = Object.assign([], this.state.isFavoris);
        list.filter(item => item !== title);
        this.setState({UserFavs: list})*/

        this.props.addFavoris(id, title, isSerie)
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

    
    render(){

     
               /* /!\ POUR LAETITIA 
                    Re bonjour Laeti (ou re bonsoir), j'ai mit en place le bouton favoris ligne 123 et ligne 167. Le onClick est juste une alert,
                    tu peux le retirer pour y mettre les fonctions dont tu as besoin pour l'ajout ou la supression de favoris. La condition du if 
                    est à false (C'était pour des test). La vrai condition pour ligne 152 serait quelque chose du genre : if (this.props.isFavoris.includes (ex.id)).
                    Ca verfie que l'id est présent (ou non) dans la liste des favoris.
                    Bon courage :doublepoucelevé:
                */
        
        //On affiche les séries
        let series = this.state.seriesListe.map(ex => {
            //console.log(ex);
            //console.log(ex.id);

            //Pour le button des favoris afficher si seulement si l'utilisateur est connecter
            let boxFav=<div></div>
            if(this.props.isConnected === true){
                // Pour la mise a jour fav : Si pas un favoris de l'utilisateur favEmpty logo sinon favFull
                let favImg=<img src={favEmpty} alt="favEmpty" width="20%" height="20%" />;

                //Array.isArray(this.state.UserFavs) && this.state.UserFavs.includes(ex.id)
                if (this.state.UserFavs.includes(ex.original_name)){
                    favImg=<img src={favFull} alt="favFull" width="20%" height="20%" />;
                    boxFav = <div> {favImg}
                                <button id= "deletefavS" className=" btnfav btn btn-rounded waves-effect" onClick={() => this.handleDeleteFav(ex.id, ex.original_name, "true")} >Ajouter</button>
                            </div>
                }else{
                    boxFav = <div> {favImg}
                                <button id= "addfavS" className=" btnfav btn btn-rounded waves-effect" onClick={() => this.handleAddFav(ex.id,ex.original_name, "true")} >Ajouter</button>
                            </div>
                }
             } 





            //Pour les nom de plus de 20 caractere (Evite de surcharger l'écran d'informations)
            let nomRestreint=ex.original_name;
             if (ex.original_name.length>20){
                nomRestreint=ex.original_name.substring(0,15)+"...";
            }


        


            const note=(ex.vote_average*5)/10;
                return(
                    
                    <div className="bloc rounded-lg" key={ex.id} > 
                        <img src={"https://image.tmdb.org/t/p/w500/"+ex.backdrop_path} alt={"pic_of_"+ex.name}  onClick={() =>  this.handleDescriptionPage(ex.id, "false")}  width="100%" height="150"/>
                        <div className="stars">
                            <StarRatings rating={note} starRatedColor="yellow" numberOfStars={5} name='rating' starDimension="20px"starSpacing="1px">
                            </StarRatings>
                        </div>
                        <div className="titre">
                            <a id={ex.id} onClick={() => this.handleDescriptionPage(ex.id, "false")} >{nomRestreint}</a>
                            {boxFav}
                        </div>
                    </div>
                   
                )
                
        })
        //On affiche les films
        let films = this.state.filmsListe.map(ex => {

            let boxFav=<div></div>
            if(this.props.isConnected === true){
                // Pour la mise a jour fav : Si pas un favoris de l'utilisateur favEmpty logo sinon favFull
                let favImg=<img src={favEmpty} alt="favEmpty" width="20%" height="20%" />;
                if (this.state.UserFavs.includes(ex.original_title)){
                    favImg=<img src={favFull} alt="favFull" width="20%" height="20%" />;

                    boxFav = <div>{favImg}
                                <button id= "addfavS" className=" btnfav btn btn-rounded waves-effect" onClick={() => this.handleDeleteFav(ex.id, ex.original_title, "false")} >Ajouter</button>
                            </div>
                }else{
                    boxFav = <div>{favImg}
                                <button id= "addfavS" className=" btnfav btn btn-rounded waves-effect" onClick={() => this.handleAddFav(ex.id, ex.original_title, "false")} >Ajouter</button>
                            </div>
                }

            } 



            //Pour les nom de plus de 20 caractere (Evite de surcharger l'écran d'informations)
            let nomRestreint=ex.original_title;
             if (ex.original_title.length>20){
                nomRestreint=ex.original_title.substring(0,15)+"...";
            }




            const note= (ex.vote_average*5)/10;

            if(ex.backdrop_path!==null && ex.backdrop_path!==undefined){
                return(
                    
                    <div className="bloc rounded-lg" key={ex.id}> 
                        <img src={"https://image.tmdb.org/t/p/w500/"+ex.backdrop_path} alt={"pic_of_"+ex.title} width="100%" height="150" onClick={() => this.handleDescriptionPage(ex.id, "true")}/>
                        <div className="stars">
                            <StarRatings rating={note} starRatedColor="yellow" numberOfStars={5} name='rating' starDimension="20px"starSpacing="1px">
                            </StarRatings>
                        </div>
                        <div className="titre">
                            <a id={ex.id} onClick={() => this.handleDescriptionPage(ex.id, "true")} >{nomRestreint}</a>
                            {boxFav}
                        </div>
                    </div>
                   
                )
            }    
        })


        
        //console.log(tmp);
        //axios.get("https://api.themoviedb.org/3/tv/airing_today?api_key=a3be1be132d237a0716cc27bdae1b2f0&language=en-US&page=1").then(res=> this.constr(res));
        return (
            <div className="Rectangle">
                <div className="Jours">
                    <strong> Films sorties en 2020 </strong>
                </div>
                {films}
                <div className="Jours">
                    <strong> Sortie des series des 7 derniers jours</strong>
                </div>
               {series}

            </div>  
        )
    }
}

export default Calendrier;