import React from 'react';

import UserService from '../app/service/UserService'
import localStorageService from '../app/service/localStorageService'

class Home extends React.Component {

    state = {
        saldo: 0
    }

    constructor(){
        super();
        this.userService = new UserService();
    }

    componentDidMount(){
        const userLogin = localStorageService.getlocalStorage('_user')

        this.userService.balanceForUser(userLogin.id)
        .then(response =>{
            this.setState({ saldo : response.data})
        }).catch(erro =>{
            console.log(erro.response)
        })
    }

    render() {
        return (

            <div className="jumbotron">
                <h1 className="display-3">Bem vindo!</h1>
                <p className="lead">Esse é seu sistema de finanças.</p>
                <p className="lead">Seu saldo para o mês atual é de R$ {this.state.saldo} </p>
                <hr className="my-4" />
                    <p>E essa é sua área administrativa, utilize um dos menus ou botões abaixo para navegar pelo sistema.</p>
                    <p className="lead">
                        <a className="btn btn-primary btn-lg" href="#/consultaLancamento" role="button"><i className="fa fa-users"></i> Lançamentos</a>
                        <a className="btn btn-danger btn-lg" href="#/cadastraLancamento" role="button"><i className="fa fa-users"></i>  Cadastrar Lançamento</a>
                    </p>
          </div>
        )
    }
}

export default Home