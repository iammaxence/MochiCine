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

              

              
              <div className="container-fluid text-center">    
                <div className="row content">
                    <div className="actu col-sm-3 bg-warning">
                      <Actualite
                          getDescriptionPage={this.props.getDescriptionPage} />
                    </div>
                    <div className="col-sm-8 text-left"> 
                      <Calendrier 
                        getDescriptionPage={this.props.getDescriptionPage}
                        addFavoris={this.props.addFavoris}
                        deleteFavoris={this.props.deleteFavoris}
                        isConnected={this.props.isConnected} 
                        listFavoris={this.props.listFavoris} />
                    </div>
                </div>
              </div>
             
            </div>
          );
    }
}
export default Accueil