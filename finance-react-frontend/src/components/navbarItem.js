import React from 'react';
import NavBar from './navbar';

function NavBarItem({ render, ...props }) {

  if (render) {
    return (
      <li className="nav-item">
        <a onClick={props.onClick} className="nav-link" href={props.href}>{props.label}</a>
      </li>
    )
  } else {
    return false;
  }
}

export default NavBarItem