import React from 'react';
import Accueil from './Accueil';
import Profil from './Profil';
import Login from './Login';
import DescriptionPage from './DescriptionPage';
import axios from 'axios';


class MainPage extends React.Component {
  constructor(props){
    super(props);
    this.state= {pagecourante: "Accueil", isConnected: false, login: ""};
  
    //all the bind
    this.setLogin = this.setLogin.bind(this);
    this.setLogout = this.setLogout.bind(this);
    this.getLoginPage = this.getLoginPage.bind(this);
    this.getAccueilPage = this.getAccueilPage.bind(this);
    this.getProfilPage = this.getProfilPage.bind(this);
    
  }

  setLogout(){
    console.log("Logout : " + this.state.login);
    if(this.state.isConnected === true ){
      const url= new URLSearchParams();
      url.append("login", this.state.login);
      axios.get("http://localhost:8080/MochiCine/User/Logout?"+url).then(res=> this.result(res));
    }
  }

  result(rep){
    console.log(rep.data);
    if(rep.data["code"]){
        this.setState ({statut: "error", textError: rep.data["message"]});
        window.confirm(this.state.textError);
    }else{
        console.log("result : OK");
        this.setState({pagecourante: "Accueil", login: "", isConnected: false});
    }
  }

  setLogin(login){
    this.setState({pagecourante: "Profil", login:login, isConnected: true});
  }

  //Envoie la page de login
  getLoginPage(){
    this.setState({pagecourante: "Login"});
  }

  //Envoie la page d'accueil
  getAccueilPage(){
    this.setState({pagecourante: "Accueil"});
  }

  //Envoie la page de profil
  getProfilPage(){
    this.setState({pagecourante: "Profil"});
  }
 



  render(){
    let page = <div><h2>Page not found</h2></div> ;  

    if(this.state.pagecourante === "Profil" && this.state.isConnected === true){
      page = 
      <Profil 
        isConnected={this.state.isConnected} 
        login={this.state.login}
        getLoginPage={this.getLoginPage}
        getProfilPage={this.getProfilPage}
        getAccueilPage={this.getAccueilPage} 
        setLogout={this.setLogout}/>;


    }else if(this.state.pagecourante === "Accueil"){
      page = 
      <Accueil 
        isConnected={this.state.isConnected}
        getLoginPage={this.getLoginPage}
        getProfilPage={this.getProfilPage}
        getAccueilPage={this.getAccueilPage} 
        setLogout={this.setLogout}/> ;


    }else if(this.state.pagecourante === "Login"){
      page = 
      <Login 
        setLogin={this.setLogin}  
        getAccueilPage={this.getAccueilPage}/>;


    }else if(this.state.pagecourante === "DescriptionPage"){
      page = 
        <DescriptionPage  isConnected={this.state.isConnected} />;
    }

    return(page);
  }

}


export default MainPage;
