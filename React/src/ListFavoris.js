import React from 'react';
import axios from "axios";

class ListFavoris extends React.Component {
    constructor(props){
        super(props); //login
        this.state ={movies: [], series: []};

    }

    componentDidMount(){
        // Recupere les Favoris de l'utilisateur 
        const url = new URLSearchParams();
        url.append('login', this.props.login);
        axios.get('http://localhost:8080/MochiCine/Favoris/List?'+url).then(response => this.lister(response));
    }

    lister(rep){
        if(rep.data["code"]){
            this.setState ({statut: "error", textError: rep.data["message"]});
            window.confirm(this.state.textError);
        }else{
            this.setState({series: rep.data["data"], movies: rep.data["movie"]});
        } 
    }

    getSerieById(id){
        alert("Ici on affiche la description OU on renvoie vers une page html");
    }

    render(){
        let series;
        let movies;
        

        if(this.state.series.length > 0){
            series = this.state.series.map( item => {
                if(item.backdrop_path!==null && item.backdrop_path!==undefined){
                    return (
                        <div key={item.id}>
                            <div className="affiches">
                                <img src={"https://image.tmdb.org/t/p/w500/"+item.backdrop_path} alt={"pic_of_"+item.title} width="100%" height="150"/>
                            </div>
                            <div className="description">
                                <a id= "nameF" onClick={() => this.getSerieById(item.id)} >{item.title}</a>
                                <strong>Description : {item.overview} </strong>
                                <button id= "addfavF" onClick={() => alert("Delete to favoris")} >Delete</button>
                            </div>
                            
                        </div>
                    
                   )
                }
            });
        }

        if(this.state.movies.length > 0){
            movies = this.state.movies.map( item => {
                if(item.backdrop_path!==null && item.backdrop_path!==undefined){
                    return (
                    <div key={item.id}>
                        <div className="affiches">
                            <img src={"https://image.tmdb.org/t/p/w500/"+item.backdrop_path} alt={"pic_of_"+item.title} width="100%" height="150"/>
                        </div>
                        <div className="description">
                            <a id= "nameF" onClick={() => this.getSerieById(item.id)} >{item.title}</a>
                            <strong>Description : {item.overview} </strong>
                            <button id= "addfavF" onClick={() => alert("Delete to favoris")} >Delete</button>
                        </div>
                        
                    </div>)
                }
            });
        }

        return(
            <div className="ListFavoris">
                {series}
                {movies}
            </div>
        );
    }



}

export default ListFavoris;