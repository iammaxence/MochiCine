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

              <div className="container-fluid text-center">  
              &nbsp;  
                <div className="row content">
                    <div className="col-sm-3 sidenav text-dark text-center">
                      <h2>Favoris</h2>
                    </div>
                    <div className="col-sm-7 text-center"> 
                      <h2>Profil Page</h2>
                    </div>
                </div>
              </div>
              
            </div>
            
          );
    }
}
export default Profil