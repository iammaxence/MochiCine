import React from 'react';
import NavBar from './NavBar';
import axios from 'axios';

class SearchPage extends React.Component{

    constructor(props){
        super(props);
        this.state={listeRecherche: []};
        this.addToList=this.addToList.bind(this);
    }

   
    componentDidMount(){
        axios.get("http://localhost:8080/MochiCine/Search?key=a3be1be132d237a0716cc27bdae1b2f0&keyword="+this.props.keyword).then(res => this.addToList(res.data.data));
    }

    
    componentDidUpdate(prevProps){
        console.log(this.props.keyword+" == "+prevProps.keyword);
        if(this.props.keyword !== prevProps.keyword){
            this.setState({listeRecherche: this.props.keyword});
            axios.get("http://localhost:8080/MochiCine/Search?key=a3be1be132d237a0716cc27bdae1b2f0&keyword="+this.props.keyword).then(res => this.addToList(res.data.data));
        }
    }

    addToList(res){
        console.log(res);
        this.setState({listeRecherche: res});
    }

    render(){
        //console.log(document.getElementById("recherche").value);
        return(
            <div className="searchPage">
                <NavBar 
                    isConnected={this.props.isConnected} 
                    getLoginPage={this.props.getLoginPage}
                    setLogout={this.props.setLogout}
                    getProfilPage={this.props.getProfilPage}
                    getAccueilPage={this.props.getAccueilPage} 
                    getSearchPage={this.props.getSearchPage}
                    keyword={this.props.keyword} />

                
            </div>
        )
    }
}

export default SearchPage;