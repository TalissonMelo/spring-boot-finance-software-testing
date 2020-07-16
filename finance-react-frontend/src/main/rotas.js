import React from 'react';

import Login from '../views/login'
import CadastroUsuario from '../views/cadastroUsuario'
import Home from '../views/home'
import ConsultaLancamento from '../views/consulta-lancamento'
import CadastroLancamento from '../views/cadastroLancamento'

import { Route, Switch, HashRouter, Redirect } from 'react-router-dom'
import AuthService from '../app/service/authService'



function RouteAuthenticate({ component: Component, ...props }) {
    return (
        <Route {...props} render={(componentProps) => {
            if (AuthService.isUserAuthenticate()) {
                return (
                    <Component {...componentProps} />
                )
            } else {
                return (
                    <Redirect to={{ pathname: '/', state: { from: componentProps.location } }} />
                )
            }
        }} />
    )
}

function Rotas() {
    return (
        <HashRouter>
            <Switch>
                <Route path="/" exact component={Login} />
                <Route path="/cadastroUsuario" component={CadastroUsuario} />

                <RouteAuthenticate path="/home" component={Home} />
                <RouteAuthenticate path="/consultaLancamento" component={ConsultaLancamento} />
                <RouteAuthenticate path="/cadastraLancamento/:id?" component={CadastroLancamento} />
            </Switch>
        </HashRouter>
    )
}

export default Rotas