import React, { Component } from 'react';
import './Calendrier.css';
import axios from 'axios';
import StarRatings from '../node_modules/react-star-ratings';

class Calendrier extends Component{

    constructor(props){
        super(props);
        this.state={seriesListe : [], filmsListe: []};
    }

    componentDidMount(){

        
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

    
    render(){

        
        //On affiche les séries
        let series = this.state.seriesListe.map(ex => {
            //console.log(ex);
            //console.log(ex.id);
            const note=(ex.vote_average*5)/10;
                return(
                    
                    <div className="bloc rounded-lg" key={ex.id} > 
                        <img src={"https://image.tmdb.org/t/p/w500/"+ex.backdrop_path} alt={"pic_of_"+ex.name} width="100%" height="150"/>
                        <div className="stars">
                            <StarRatings rating={note} starRatedColor="yellow" numberOfStars={5} name='rating' starDimension="20px"starSpacing="1px">
                            </StarRatings>
                        </div>
                        <div className="titre">
                            <a id={ex.id} onClick={() => this.props.getDescriptionPage(ex)} >{ex.name}</a>
                            <div>
                                <button id= "addfavS" onClick={() => alert("Add to favoris")} >add</button>
                            </div>
                        </div>
                    </div>
                   
                )
                
        })
        //On affiche les films
        let films = this.state.filmsListe.map(ex => {

            const note= (ex.vote_average*5)/10;

            if(ex.backdrop_path!==null && ex.backdrop_path!==undefined){
                return(
                    
                    <div className="bloc rounded-lg" key={ex.id}> 
                        <img src={"https://image.tmdb.org/t/p/w500/"+ex.backdrop_path} alt={"pic_of_"+ex.title} width="100%" height="150"/>
                        <div className="stars">
                            <StarRatings rating={note} starRatedColor="yellow" numberOfStars={5} name='rating' starDimension="20px"starSpacing="1px">
                            </StarRatings>
                        </div>
                        <div className="titre">
                            <a id={ex.id} onClick={() => this.props.getDescriptionPage(ex)} >{ex.title}</a>
                            <div>
                                <button id= "addfavF" onClick={() => alert("Add to favoris")} >add</button>
                            </div>
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
                    <strong> Sortie des series de la semaine </strong>
                </div>
               {series}
                <div className="Jours">
                    <strong> Films sorties en 2020 </strong>
                </div>
                {films}
            </div>  
        )
    }
}

export default Calendrier;