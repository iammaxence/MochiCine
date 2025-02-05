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
       
        let key = "a3be1be132d237a0716cc27bdae1b2f0";
        //Future release
        axios.get("http://localhost:8080/MochiCine/SerieOfTheWeek?key="+key).then(res=> this.setState({seriesListe: res.data.data}));

        axios.get("http://localhost:8080/MochiCine/FilmsOnAir?key="+key).then(res=> this.setState({filmsListe: res.data.data}));
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
        this.props.addFavoris(id, title, isSerie)
        this.setState({UserFavs: this.props.listFavoris});
        //console.log("addCalendrier", this.state.UserFavs);
    }

    handleDeleteFav(id, title, isSerie){   
        const list = Object.assign([], this.state.UserFavs);
        var index = list.indexOf(title);
        if (index !== -1) {
            list.splice(index, 1);
            this.setState({UserFavs: list});
        }

        this.props.deleteFavoris(id, title, isSerie)
        //console.log("deleteCalendrier", list);
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
        //On affiche les séries
        let series = this.state.seriesListe.map(ex => {
            //console.log(ex);
            //console.log(ex.id);

            //Pour le button des favoris afficher si seulement si l'utilisateur est connecter
            let boxFav=<div></div>
            if(this.props.isConnected === true){
                // Pour la mise a jour fav : Si pas un favoris de l'utilisateur favEmpty logo sinon favFull
                let favImg=<img src={favEmpty} alt="favEmpty" width="10%" height="10%" />;

                //Array.isArray(this.state.UserFavs) && this.state.UserFavs.includes(ex.id)
                if (Array.isArray(this.state.UserFavs) && this.state.UserFavs.includes(ex.original_name)){
                    favImg=<img src={favFull} alt="favFull" width="10%" height="10%" />;
                    boxFav = <div> {favImg}
                                <button id= "deletefavS" className=" btnfav btn btn-rounded waves-effect" onClick={() => this.handleDeleteFav(ex.id, ex.original_name, "true")} >Delete</button>
                            </div>
                }else{
                    boxFav = <div> {favImg}
                                <button id= "addfavS" className=" btnfav btn btn-rounded waves-effect" onClick={() => this.handleAddFav(ex.id,ex.original_name, "true")} >Add</button>
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
                        <img src={"https://image.tmdb.org/t/p/w500/"+(ex.backdrop_path || ex.poster_path)} alt={"picture_of_"+ex.name}  onClick={() =>  this.handleDescriptionPage(ex.id, "false")}  width="100%" height="150"/>
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
                let favImg=<img src={favEmpty} alt="favEmpty" width="10%" height="10%" />;
                if (Array.isArray(this.state.UserFavs) && this.state.UserFavs.includes(ex.original_title)){
                    favImg=<img src={favFull} alt="favFull" width="10%" height="10%" />;

                    boxFav = <div>{favImg}
                                <button id= "addfavS" className=" btnfav btn btn-rounded waves-effect" onClick={() => this.handleDeleteFav(ex.id, ex.original_title, "false")} >Delete</button>
                            </div>
                }else{
                    boxFav = <div>{favImg}
                                <button id= "addfavS" className=" btnfav btn btn-rounded waves-effect" onClick={() => this.handleAddFav(ex.id, ex.original_title, "false")} >Add</button>
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
                        <img src={"https://image.tmdb.org/t/p/w500/"+(ex.backdrop_path || ex.poster_path)} alt={"picture_of_"+ex.title} width="100%" height="150" onClick={() => this.handleDescriptionPage(ex.id, "true")}/>
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
                <div className="filmSortie">
                    <h2> Films sorties en 2020 </h2>
                </div>
                {films}
                <div className="serieSortie">
                    <h2> Sortie des series des 7 derniers jours</h2>
                </div>
               {series}

            </div>  
        )
    }
}

export default Calendrier;