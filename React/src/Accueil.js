import React from 'react';
import NavBar from './NavBar';
import Calendrier from './Calendrier';

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

              <Calendrier/>
              <div className="container_page container-fluid text-center">    
                <div className="row">
                    <div className="col-3 sidenav text-dark" >
                      <h2>Actualité</h2>
                      </div>
                    
                </div>
                </div>
             
            </div>
          );
    }
}
export default Accueil