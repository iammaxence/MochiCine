import React from 'react';

class NavBar extends React.Component {
    constructor(props){
      super(props);
      this.sendaccueil=this.sendaccueil.bind(this);
      this.sendprofil=this.sendprofil.bind(this);
      this.sendlogin=this.sendlogin.bind(this);
      this.sendSearch=this.sendSearch.bind(this);
    }

    sendaccueil = () => {
        this.props.getAccueilPage();
    }

    sendprofil = () => {
        this.props.getProfilPage();
    }

    sendlogin = () => {
        this.props.getLoginPage();
    }

    //---------Ajout-----------
    sendSearch = () => {
        this.props.getSearchPage(document.getElementById("recherche").value);
    }
    
    render(){
        let option;
        if(this.props.isConnected === true){ //Profil + Logout
            option= <div className="nav_item">
                        <button className="btn btn-sm btn-outline-secondary" type="button" onClick={this.sendprofil}>Profil</button>
                        &nbsp; &nbsp;
                        <button className="btn btn-sm btn-outline-secondary" type="button" onClick={() => this.props.setLogout()}>Logout</button>
                    </div>;
                   
        }else{ //Login 
            option= <div className="nav_item">
                        <button className="btn btn-sm btn-outline-secondary" type="button" onClick={this.sendlogin}>Login/Register</button>
                    </div>;
        }
        return(
            <nav className="navbar navbar-light bg-dark justify-content-between">
                <a className="navbar-brand text-light" onClick={this.sendaccueil}> MochiCine </a>
                <form className="form-inline">
                    <input className="form-control mr-sm-2" type="search" placeholder="Search" id="recherche" aria-label="Search"/>
                    <button className="btn btn-outline-success my-2 my-sm-0" onClick={this.sendSearch} type="button" >Search</button>
                </form>
                {option}
            </nav>
        );
    }
}
export default NavBar