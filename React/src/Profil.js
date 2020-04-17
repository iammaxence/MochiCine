import React from 'react';
import NavBar from './NavBar';

class Profil extends React.Component {

  
    render(){
          return(
            <div className= "PageProfil">
              <NavBar 
                isConnected={this.props.isConnected} 
                getLoginPage={this.props.getLoginPage}
                setLogout={this.props.setLogout}
                getProfilPage={this.props.getProfilPage}
                getAccueilPage={this.props.getAccueilPage} />


              <h2>Profil Page</h2>
            </div>
            
          );
    }
}
export default Profil