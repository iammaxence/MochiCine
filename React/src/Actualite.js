import React from 'react';
import axios from 'axios';
import "./Actualite.css"
import StarRatings from '../node_modules/react-star-ratings';
import favEmpty from './icons/favEmpty.png';
import favFull from './icons/favFull.png';

class Actualite extends React.Component {
    constructor(props){
        super(props);
        this.state= {topmovies : []}
    }


componentDidMount(){
    // Recuperation top series et films
    let key = "a3be1be132d237a0716cc27bdae1b2f0";
    let nbfilms= 6;
    axios.get("http://localhost:8080/MochiCine/TendanceFilms?key="+key+"&count="+nbfilms).then(res=> this.setState({topmovies: res.data.data}));

       
    }    



    render(){
        //On affiche les films
        let films = this.state.topmovies.map(ex => {

            let boxFav=<div></div>
            if(this.props.isConnected === true){
                // Pour la mise a jour fav : Si pas un favoris de l'utilisateur favEmpty logo sinon favFull
                let favImg=<img src={favEmpty} alt="favEmpty" width="20%" height="20%" />;
                if (Array.isArray(this.state.UserFavs) && this.state.UserFavs.includes(ex.original_title)){
                    favImg=<img src={favFull} alt="favFull" width="20%" height="20%" />;

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
                            {boxFav}
                        </div>
                    </div>
                   
                )
            }    
        })
          return(
            <div className="ActualitePage rounded">
               <div>
                   <div className="topmovie">
                       <strong>TOP FILMS</strong>
                   </div>
                   {films}
               </div>
               <div>
                   TOP SERIES
               </div>

            </div>
          );
    }
}
export default Actualite