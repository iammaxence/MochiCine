import React from 'react';
import NavBar from './NavBar';
import ListFavoris from './ListFavoris';

class Profil extends React.Component {

  
    render(){
          return(
            <div className= "PageProfil text-center">
              <NavBar 
                isConnected={this.props.isConnected} 
                getLoginPage={this.props.getLoginPage}
                setLogout={this.props.setLogout}
                getProfilPage={this.props.getProfilPage}
                getAccueilPage={this.props.getAccueilPage} />


              <ListFavoris  login={this.props.login} />

              
            </div>
            
          );
    }
}
export default Profil