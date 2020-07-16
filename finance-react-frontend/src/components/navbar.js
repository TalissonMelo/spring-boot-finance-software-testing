import React from 'react';
import NavBarItem from './navbarItem';

import AuthService from '../app/service/authService';

const logout = () => {
    AuthService.removeUserAuthenticate();
}

const userlogin = () => {
    return AuthService.isUserAuthenticate()
}

function NavBar() {
    return (
        <div className="navbar navbar-expand-lg fixed-top navbar-dark bg-primary">
            <div className="container">
                <a href="#/home" className="navbar-brand">Minhas Finanças</a>
                <button className="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
                    <span className="navbar-toggler-icon"></span>
                </button>
                <div className="collapse navbar-collapse" id="navbarResponsive">
                    <ul className="navbar-nav">
                        <NavBarItem render={userlogin} href="#/home" label="Home" />
                        <NavBarItem render={userlogin} href="#/consultaLancamento" label="Lançamentos" />
                        <NavBarItem render={userlogin} onClick={logout} href="#/" label="Sair" />
                    </ul>
                </div>
            </div>
        </div>
    )
}

export default NavBar