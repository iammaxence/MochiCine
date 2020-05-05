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
        if(rep.data != null){
            if(rep.data["code"]){
                this.setState ({statut: "error", textError: rep.data["message"]});
                window.confirm(this.state.textError);
            }else{
                let films =rep.data["movie"];
                this.setState({series: rep.data["data"], movies: films[0]});
                this.updateMain();
            } 
        }
    }

    formatDate(string){
        var options = { year: 'numeric', month: 'long', day: 'numeric' };
        return new Date(string).toLocaleDateString([],options);
    }

    deleteFavoris(id, nb, index){
        const url = new URLSearchParams();
        url.append('login', this.props.login);
        url.append('id', id);
        (nb === 0)? url.append('isSerie', "false"): url.append('isSerie', 'true');
        axios.get('http://localhost:8080/MochiCine/Favoris/Delete?'+url).then(response => this.result(response, nb, index));
    }

    result(rep, nb, index){
        if(rep.data["code"]){
            this.setState ({statut: "error", textError: rep.data["message"]});
            window.confirm(this.state.textError);
        }else{
            console.log("deleteFavoris : ok");
            
            if(nb === 0){
                const list= Object.assign([], this.state.movies);
                list.splice(index,1);
                this.setState({movies: list});
                console.log("movies : ", this.state.movies );
            }else{
                const newList= Object.assign([], this.state.series);
                newList.splice(index,1);
                this.setState({series: newList});
            }
            this.updateMain();
        } 
   }

   updateMain(){
    const list = [];

    this.state.movies.forEach(function(item, index, array){
        list.push(item.original_title);
    });
    this.state.series.forEach(function(item, index, array){
        list.push(item.original_name);
    });

    //console.log(list);

    this.props.setListFavoris(list);

   }



    getBox(item, index){
        let nb = (item.number_of_seasons || 0);
        let choix;
        if(nb === 0){
            choix="film";
        }else{
            choix=nb+" saisons";
        }

        return(
                <div className="col-md-12 bg-white text-dark" key={item.id}>
                <div className="blog-entry  col-12">
                    <div id = "leftbox">
                        <img src={"https://image.tmdb.org/t/p/w500/"+item.backdrop_path} alt={"pic_of_"+item.title} width="100%"  />
                    </div>
                    <div id = "rightbox">
                        <div className="text text-2 text-center pl-md-4">
                            <h3 className="mb-2"  onClick={() => this.props.getDescriptionPage(item)} >{(item.original_title || item.original_name)}</h3>
                        </div>
                        <div className="meta-wrap">
                            <p className="meta">
                                <span><i className="far fa-calendar-alt mr-2"></i>{this.formatDate((item.first_air_date || item.release_date))}</span>
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                <span><i className="fas fa-fire-alt mr-2"></i>{item.popularity}</span>
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                <span><i className="fas fa-tv mr-2"></i>{ choix } </span>
                            </p>
                        </div>
                        <p className="mb-4">
                            {item.overview}
                        </p>
                        <p>
                        <button className="btn btn-sm btn-outline-danger" type="button" onClick={() => this.deleteFavoris(item.id, nb, index)}>Delete Favoris</button>
                        </p>
                    </div>
                </div>
            </div>
            );
        
    }
    

    render(){
        let series;
        let movies;
        

        if(this.state.series.length > 0){
            let cpt=0;
            series = this.state.series.map( item => {
                if(item.backdrop_path!==null && item.backdrop_path!==undefined){
                    let box = (this.getBox(item, cpt));
                    cpt=cpt+1;
                    return box;
                }
            });
        }

        if(this.state.movies.length > 0){
            let cpt2=0;
            movies = this.state.movies.map( item => {
                if(item.backdrop_path!==null && item.backdrop_path!==undefined){
                    let box = (this.getBox(item, cpt2));
                    cpt2=cpt2+1;
                    return box;
                }
            });
        }

        return(
            <div className="ListFavoris col">
                <div className="row pt-md-3">
                    {series}
                    {movies}

                </div>
            </div>
        );
    }



}

export default ListFavoris;