import React from 'react';
import './Login.css';
import axios from 'axios';

class Login extends React.Component {
    constructor(props){
        super(props);
        this.login = React.createRef();
        this.mdp = React.createRef();

        this.sendregister = this.sendregister.bind(this);
        this.sendaccueil = this.sendaccueil.bind(this);
        this.requestCreateUser = this.requestCreateUser.bind(this);
    }

    sendregister = () => {
        this.props.getRegisterPage();
    }

    sendaccueil = () => {
        this.props.getAccueilPage();
    }


    requestCreateUser = () => {
        console.log("createUser : " + this.login.current.value +" ; " + this.mdp.current.value);
        const url= new URLSearchParams();
        url.append("login", this.login.current.value);
        url.append("mdp", this.mdp.current.value);
        axios.get("http://localhost:8080/MochiCine/User/Login?"+url).then(res=> this.result(res));
    }

    result(rep){
        console.log(rep.data);
        if(rep.data["code"]){
            this.setState ({statut: "error", textError: rep.data["message"]});
            window.confirm(this.state.textError);
        }else{
            console.log("createUser : OK");
            this.props.setLogin(this.login.current.value);
        }
    }


    render(){
          return(
            <div className="container h-100">
                <div className="d-flex justify-content-center h-100">
                    <div className="user_card">
                        <h1 className="text-center" onClick={this.sendaccueil} >MochiCine</h1>
                        <div className="d-flex justify-content-center form_container">
                            <form>
                                <div className="input-group mb-3">
                                    <div className="input-group-append">
                                        <span className="input-group-text"><i className="fas fa-user"></i></span>
                                    </div>
                                    <input type="text" name="login" className="form-control input_user" placeholder="login" ref={this.login} required/>
                                </div>
                                <div className="input-group mb-2">
                                    <div className="input-group-append">
                                        <span className="input-group-text"><i className="fas fa-key"></i></span>
                                    </div>
                                    <input type="password" name="" className="form-control input_pass" placeholder="password" ref={this.mdp} required/>
                                </div>
                                <div className="d-flex justify-content-center mt-3 login_container">
                                    <button type="button" className="btn btn-dark" onClick={this.requestCreateUser} >Login</button>
                                    &nbsp; &nbsp;
                                    <button className="btn btn-outline-dark" type="button" onClick={this.sendregister}>Register</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
          );
    }
}
export default Login



            