import React from 'react';
import NavBar from './NavBar';

class DescriptionPage extends React.Component {

  
    render(){
          return(
            <div className= "DescriptionPage">
                <NavBar isConnected={this.props.isConnected} />
                &nbsp;
                <div class="container-fluid text-center">    
                <div class="row content">
                    <div class="col-sm-2 sidenav">
                        <p>Realisateurs : </p>
                        <p><a href="#">A</a></p>
                        <p>Genre : </p>
                        <p><a href="#">Action</a></p>
                    </div>
                    <div class="col-sm-8 text-left"> 
                        <h1>Nom du Film</h1>
                        <p>Ceci est le résumé du film... gcqucgbhjqvhjajzdcghjdbc gqsxgbjcbhq cqkjcbqkshcqa</p>
                        <hr/>
                        <h3>Commentaires </h3>
                        <p>A faire</p>
                    </div>
                </div>
                </div>
            </div>
          );
    }
}
export default DescriptionPage