import React from 'react';
import NavBar from './NavBar';
import ListComponent from './ListComponent';
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
                    <div className="col-3 sidenav bg-secondary text-dark" >
                      <h2>Actualité</h2>
                      <ListComponent pagecourante={this.props.pagecourante} login={this.props.login} />
                    </div>
                    
                </div>
                </div>
            </div>
          );
    }
}
export default Accueil