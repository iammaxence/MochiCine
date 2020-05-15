import React from 'react';
import NavBar from './NavBar';
import ListMessages from './ListMessages'

class DescriptionPage extends React.Component {
    constructor(props){
        super(props);
        let title = (this.props.data.original_name || this.props.data.original_title);
        this.state = {titre: title, isFavoris: this.props.listFavoris.includes(title)};
    }

    formatDate(string){
        var options = { year: 'numeric', month: 'long', day: 'numeric' };
        return new Date(string).toLocaleDateString([],options);
    }

    getInfo(type){
        if(this.props.data[type]){
            let info = this.props.data[type];
            let result = info.map( item => {
                return <p key={item.id}>{item.name}</p>;
            });
            return result;
        }else{
            return <div></div>;
        }
    }

    handleAddFav(id, title, isSerie){
        this.setState({isFavoris: true});
        this.props.addFavoris(id, title, isSerie)
    }

    handleDeleteFav(id, title, isSerie){
        this.setState({isFavoris: false});
        this.props.deleteFavoris(id, title, isSerie)
    }

    render(){
        let item = this.props.data;
        let nb = (item.number_of_seasons || 0);
        let choix;
        if(nb === 0){
            choix="film";
        }else{
            choix=nb+" saisons";
        }
        let add=<div></div>;
        if(this.props.isConnected === true){
            let isSerie;
            (choix === "film")? isSerie = "false" : isSerie = "true";

            if(this.state.isFavoris){
                add = <button className="btn btn-sm btn-outline-danger" type="button" onClick={() =>  this.handleDeleteFav(item.id, this.state.titre, isSerie)}>Delete Favoris</button>;
            }else{
                add = <button className="btn btn-sm btn-outline-success" type="button" onClick={() =>  this.handleAddFav(item.id, this.state.titre, isSerie)} >Add Favoris</button>;
            }

        }

          return(
            <div className= "DescriptionPage">
                <NavBar
                    isConnected={this.props.isConnected} 
                    getLoginPage={this.props.getLoginPage}
                    setLogout={this.props.setLogout}
                    getProfilPage={this.props.getProfilPage}
                    getAccueilPage={this.props.getAccueilPage} 
                    getSearchPage={this.props.getSearchPage}/>
                &nbsp;

                <div className="container-fluid text-left">    
                <div className="row content">
                    
                    <div className="col-sm-4 sidenav">
                    <img src={"https://image.tmdb.org/t/p/w500/"+(item.backdrop_path || item.poster_path)} alt={"picture_of_"+(item.original_title || item.original_name)} width="100%"  />
                    
                    &nbsp;&nbsp;
                        <div>
                            <strong>Realisateur(s) : </strong>
                            <div className="text-center" >
                                {this.getInfo("created_by") || this.getInfo("production_companies")}
                            </div>
                            <hr/>

                            <strong>Genre(s) : </strong>
                            <div className="text-center" >
                                {this.getInfo("genres")}
                            </div>
                            <hr/>
                            <strong>Status :</strong> <p className="text-center" >{item.status}</p>
                        </div>
                    </div>
                    <div className="col-sm-8 text-left"> 
                        <h1>{(item.original_title || item.original_name)}</h1>


                        <p className="meta">
                                <span><i className="far fa-calendar-alt mr-2"></i>{this.formatDate((item.first_air_date || this.props.data.release_date))}</span>
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                <span><i className="fas fa-fire-alt mr-2"></i>{item.popularity}</span>
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                <span><i className="fas fa-tv mr-2"></i>{ choix } </span>
                        </p>

                        <p>{item.overview}</p>

                        &nbsp;&nbsp;&nbsp;&nbsp;
                        {add}
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <hr/>
                        <h3>Commentaires </h3>
                        
                        <ListMessages titre={this.state.titre} login={this.props.login} isConnected={this.props.isConnected}/>
                    </div>
                </div>
                </div>
            </div>
          );
    }
}
export default DescriptionPage