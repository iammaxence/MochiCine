import React from 'react';
import NavBar from './NavBar';
import Calendrier from './Calendrier';
import Actualite from './Actualite';

class Accueil extends React.Component {


    render(){
          return(
            <div className= "PageAccueil">

              <NavBar 
                isConnected={this.props.isConnected} 
                getLoginPage={this.props.getLoginPage}
                setLogout={this.props.setLogout}
                getProfilPage={this.props.getProfilPage}
                getAccueilPage={this.props.getAccueilPage} 
                getSearchPage={this.props.getSearchPage}/>        

              <Calendrier 
                getDescriptionPage={this.props.getDescriptionPage}
                addFavoris={this.props.addFavoris}
                deleteFavoris={this.props.deleteFavoris}
                isConnected={this.props.isConnected} 
                listFavoris={this.props.listFavoris} />

              <Actualite

              />

              <div className="container_page container-fluid text-center">    
                <div className="row">
                    <div className="col-3 sidenav text-dark" >
                      </div>
                    
                </div>
                </div>
             
            </div>
          );
    }
}
export default Accueil