import React from 'react';
import axios from 'axios';
import "./Actualite.css"
import StarRatings from '../node_modules/react-star-ratings';


class Actualite extends React.Component {
    constructor(props){
        super(props);
        this.state= {topmovies : [],topseries : []}
    }


    componentDidMount(){
        // Recuperation top series et films
        let key = "a3be1be132d237a0716cc27bdae1b2f0";
        let count= 6;
        let type= "movie";
        axios.get("http://localhost:8080/MochiCine/Tendances?key="+key+"&count="+count+"&type="+type).then(res=> this.setState({topmovies: res.data.data}));

        type= "tv";
        axios.get("http://localhost:8080/MochiCine/Tendances?key="+key+"&count="+count+"&type="+type).then(res=> this.setState({topseries: res.data.data}));
        
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
        //On affiche les films
        let films;

        if (this.state.topmovies === undefined || this.state.topmovies.length === 0 ){
            films= <div><strong>Nothing Found</strong></div>
        }
        else if (!Array.isArray(this.state.topmovies)) { //Cette condition est obligatoire car lorsque la navbar appel ce composant, il est démonté puis remonté. Il n'est pas monté assez rapidement pour laisser le temps à la requête d'être terminé
            films= <div><strong>Nothing Found</strong></div>
        } 
        else {
            films = this.state.topmovies.map(ex => {

            //Pour les nom de plus de 20 caractere (Evite de surcharger l'écran d'informations)
            let nomRestreint=ex.original_title;
             if (ex.original_title.length>13){
                nomRestreint=ex.original_title.substring(0,13)+"...";
            }




            const note= (ex.vote_average*5)/10;
            
            if(ex.backdrop_path!==null && ex.backdrop_path!==undefined){
                return(
                    
                    <div className="bloctop rounded-lg" key={ex.id}> 
                        <img src={"https://image.tmdb.org/t/p/w500/"+ex.backdrop_path} alt={"pic_of_"+ex.title} width="100%" height="150" onClick={() => this.handleDescriptionPage(ex.id, "true")}/>
                        <div className="stars">
                            <StarRatings rating={note} starRatedColor="yellow" numberOfStars={5} name='rating' starDimension="20px"starSpacing="1px">
                            </StarRatings>
                        </div>
                        <div className="titre">
                            <a id={ex.id} onClick={() => this.handleDescriptionPage(ex.id, "true")} >{nomRestreint}</a>
                        </div>
                    </div>
                   
                )
            }    
            })
        }

        let series; 
        if (this.state.topseries === undefined || this.state.topseries.length === 0 ){
            series= <div><strong>Nothing Found</strong></div>
        }
        else if (!Array.isArray(this.state.topmovies)) { //Cette condition est obligatoire car lorsque la navbar appel ce composant, il est démonté puis remonté. Il n'est pas monté assez rapidement pour laisser le temps à la requête d'être terminé
        series= <div><strong>Nothing Found</strong></div>
        } 
        else {
        //On affiche les series
            series = this.state.topseries.map(ex => {

            //Pour les nom de plus de 20 caractere (Evite de surcharger l'écran d'informations)
            let nomRestreint=ex.original_name;
             if (ex.original_name.length>13){
                nomRestreint=ex.original_name.substring(0,13)+"...";
            }




            const note= (ex.vote_average*5)/10;
            
            if(ex.backdrop_path!==null && ex.backdrop_path!==undefined){
                return(
                    
                    <div className="bloctop rounded-lg" key={ex.id}> 
                        <img src={"https://image.tmdb.org/t/p/w500/"+ex.backdrop_path} alt={"pic_of_"+ex.name} width="100%" height="150" onClick={() => this.handleDescriptionPage(ex.id, "false")}/>
                        <div className="stars">
                            <StarRatings rating={note} starRatedColor="yellow" numberOfStars={5} name='rating' starDimension="20px"starSpacing="1px">
                            </StarRatings>
                        </div>
                        <div className="titre">
                            <a id={ex.id} onClick={() => this.handleDescriptionPage(ex.id, "true")} >{nomRestreint}</a>
                        </div>
                    </div>
                   
                )
            }    
            })
        }
          return(
            <div className="ActualitePage rounded">
               <div>
                   <div className="topmovie">
                       <strong>TOP FILMS</strong>
                   </div>
                   {films}
               </div>
               <div>
                 <div className="topmovie">
                       <strong>TOP SERIES</strong>
                 </div>
                 {series}
               </div>

            </div>
          );
    }
}
export default Actualite