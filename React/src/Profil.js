import React from 'react';
import NavBar from './NavBar';

class Profil extends React.Component {

  
    render(){
          return(
            <div className= "PageProfil">
            <NavBar isConnected={this.props.isConnected} />

            </div>
          );
    }
}
export default Profil