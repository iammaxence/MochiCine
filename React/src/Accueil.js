import React from 'react';
import NavBar from './NavBar';

class Accueil extends React.Component {


    render(){
          return(
            <div className= "PageAccueil">

              <NavBar 
                isConnected={this.props.isConnected} 
                getLoginPage={this.props.getLoginPage}
                setLogout={this.props.setLogout}
                getProfilPage={this.props.getProfilPage}
                getAccueilPage={this.props.getAccueilPage} />


                <div className="container-fluid text-center">    
                <div className="row content">
                    <div className="col-sm-2 sidenav bg-secondary text-white">
                      <h2>Actualité</h2>
                    </div>
                    <div className="col-sm-8 text-left"> 
                        <h1>Calendrier</h1>
                    </div>
                </div>
                </div>
            </div>
          );
    }
}
export default Accueil