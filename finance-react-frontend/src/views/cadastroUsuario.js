import React from 'react';
import Card from '../components/card';
import FormGroup from '../components/form-group';
import UserService from '../app/service/UserService';

import { withRouter } from 'react-router-dom'

import { messageSuccess, messageError } from '../components/toastr'

class CadastroUsuario extends React.Component {

    state = {
        nome: '',
        email: '',
        senha: '',
        senhaConfirmacao: ''
    }

    constructor() {
        super();
        this.service = new UserService();
    }

    validar() {
        const msgs = [];

        if (!this.state.nome) {
            msgs.push('O campo Nome é Obrigatório.')
        }

        if (!this.state.email) {
            msgs.push('O campo Email é Obrigatório.')
        } else if (!this.state.email.match(/^[a-z0-9.]+@[a-z0-9]+\.[a-z]/)) {
            msgs.push('Informe um Email válido.')
        }

        if (!this.state.senha || !this.state.senhaConfirmacao) {
            msgs.push('Digite a  Senha é Obrigatório.')
        } else if (this.state.senha !== this.state.senhaConfirmacao) {
            msgs.push('As Senhas não são iguais...')
        }


        return msgs;
    }

    cadastrar = () => {

        const msgs = this.validar();
        if (msgs && msgs.length > 0) {
            msgs.forEach((msg, index) => {
                messageError(msg);
            })

            return false;
        }
        const user = {
            name: this.state.nome,
            email: this.state.email,
            password: this.state.senha
        }

        this.service.insert(user)
            .then(response => {
                messageSuccess("Usuário Cadastrado : " + this.state.nome)
                this.props.history.push('/')

            }).catch(error => {

                messageError(error.response.data)
            })
    }

    cancelar = () => {
        this.props.history.push('/')
    }

    render() {
        return (
            <Card title="Cadastro de Usuário">
                <div className="row">
                    <div className="col-lg-12">
                        <div className="bs-component">
                            <FormGroup label="Nome * " htmlForm="inputNome">
                                <input type="text" className="form-control" id="inputNome" name="nome" onChange={e => this.setState({ nome: e.target.value })} />

                            </FormGroup>
                            <FormGroup label="Email * " htmlForm="inputEmail">
                                <input type="email" className="form-control" id="inputEmail" name="email" onChange={e => this.setState({ email: e.target.value })} />

                            </FormGroup>
                            <FormGroup label="Senha * " htmlForm="inputSenha">
                                <input type="password" className="form-control" id="inputSenha" name="senha" onChange={e => this.setState({ senha: e.target.value })} />

                            </FormGroup>
                            <FormGroup label="Confirmar Senha * " htmlForm="inputConfirmarSenha">
                                <input type="password" className="form-control" id="inputConfirmarSenha" name="senhaConfirmacao" onChange={e => this.setState({ senhaConfirmacao: e.target.value })} />

                            </FormGroup>

                            <button onClick={this.cadastrar} type="button" class="btn btn-success">Salvar</button>
                            <button onClick={this.cancelar} type="button" class="btn btn-danger">Voltar</button>

                        </div>
                    </div>
                </div>
            </Card>
        )
    }
}

export default withRouter(CadastroUsuario)