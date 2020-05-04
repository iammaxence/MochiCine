import React from 'react';
import Accueil from './Accueil';
import Profil from './Profil';
import Login from './Login';
import DescriptionPage from './DescriptionPage';
import axios from 'axios';
import SearchPage from './SearchPage';


class MainPage extends React.Component {
  constructor(props){
    super(props);
    this.state= {pagecourante: "Profil", isConnected: true, login: "bob", keyword: "", data: null, isFavoris: []};
    
    //all the bind
    this.setLogin = this.setLogin.bind(this);
    this.setLogout = this.setLogout.bind(this);
    this.getLoginPage = this.getLoginPage.bind(this);
    this.getAccueilPage = this.getAccueilPage.bind(this);
    this.getProfilPage = this.getProfilPage.bind(this);
    this.getSearchPage= this.getSearchPage.bind(this);
    this.getDescriptionPage= this.getDescriptionPage.bind(this);
    this.addFavoris= this.addFavoris.bind(this);
    this.deleteFavoris= this.deleteFavoris.bind(this);
    this.setListFavoris=this.setListFavoris.bind(this);
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
    this.setState({pagecourante: "Accueil", data: null});
  }

  //Envoie la page de profil
  getProfilPage(){
    this.setState({pagecourante: "Profil", data: null});
  }
  
  getSearchPage(keywordvalue){
    this.setState({pagecourante: "SearchPage", keyword: keywordvalue});
    //console.log(this.state.keyword);
  }

  getDescriptionPage(data){
    this.setState({pagecourante: "DescriptionPage", data: data});
  }


  //--------- GESTION DES FAVORIS---------------
  setListFavoris(list){
    this.setState({isFavoris: list});
  }

  addFavoris(id, titre, isSerie){
    this.state.isFavoris.push(titre);
    console.log("isFavoris : ", this.state.isFavoris);
    try{
      const url = new URLSearchParams();
      url.append('id', id);
      url.append('isSerie', isSerie);
      url.append('login', this.state.login);
      axios.get('http://localhost:8080/MochiCine/Favoris/Add?' + url).then(response => this.handleRep(response));
    }catch(error){
      console.log("Error addFavoris")
    }
  }

  deleteFavoris(id, titre, isSerie){
    const list = Object.assign([], this.state.isFavoris);
    list.filter(item => item !== titre);
    this.setState({isFavoris: list})

    const url = new URLSearchParams();
    url.append('id', id);
    url.append('isSerie', isSerie);
    url.append('login', this.state.login);
 		axios.get('http://localhost:8080/MochiCine/Favoris/Delete?' + url).then(response => this.handleRep(response));
  }

  handleRep(rep){
    if(rep.data["code"]){
        window.confirm(this.state.textError);
    }else{
      console.log(rep.data);
    }
  }


//--------------------------------------------------------

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
        setLogout={this.setLogout}
        getSearchPage={this.getSearchPage}
        getDescriptionPage={this.getDescriptionPage} 
        setListFavoris={this.setListFavoris} />;


    }else if(this.state.pagecourante === "Accueil"){
      page = 
      <Accueil 
        isConnected={this.state.isConnected}
        login={this.state.login}
        getLoginPage={this.getLoginPage}
        getProfilPage={this.getProfilPage}
        getAccueilPage={this.getAccueilPage} 
        setLogout={this.setLogout}
        getSearchPage={this.getSearchPage} 
        getDescriptionPage={this.getDescriptionPage}
        addFavoris={this.addFavoris}
        deleteFavoris={this.deleteFavoris}
        listFavoris={this.state.isFavoris}  /> ;


    }else if(this.state.pagecourante === "Login"){
      page = 
      <Login 
        setLogin={this.setLogin}  
        getAccueilPage={this.getAccueilPage}/>;


    }else if(this.state.pagecourante === "DescriptionPage"){
      page = 
        <DescriptionPage  
          isConnected={this.state.isConnected} 
          data={this.state.data}
          login={this.state.login}
          getLoginPage={this.getLoginPage}
          getProfilPage={this.getProfilPage}
          getAccueilPage={this.getAccueilPage} 
          setLogout={this.setLogout}
          addFavoris={this.addFavoris}
          deleteFavoris={this.deleteFavoris} 
          getSearchPage={this.getSearchPage}
          listFavoris={this.state.isFavoris} />;


    }
    else if(this.state.pagecourante === "SearchPage"){
      page=
        <SearchPage
          isConnected={this.state.isConnected} 
          login={this.state.login}
          getLoginPage={this.getLoginPage}
          getProfilPage={this.getProfilPage}
          getAccueilPage={this.getAccueilPage} 
          setLogout={this.setLogout}
          getSearchPage={this.getSearchPage}
          keyword={this.state.keyword} 
          getDescriptionPage={this.getDescriptionPage}
          addFavoris={this.addFavoris}
          deleteFavoris={this.deleteFavoris}
          listFavoris={this.state.isFavoris}
           />
    }
    

    return(page);
  }

}


export default MainPage;
