import './Header.css';
import React, { Component } from 'react'


class Header extends Component{
    render(){
        
        return(
            
            <nav className="navbar navbar-expand-sm navbar-dark mb-4 bg-dark navbar-custom">
                <div className="container">
                    <a className="navbar-brand" href="Dashboard.html">
                            AKR Finance Model
                    </a>

                    <button className="navbar-toggler" type="button" data-toggle="collapse" data-target="#mobile-nav">
                        <span className="navbar-toggler-icon" />
                    </button>

                    <div className="collapse navbar-collapse" id="mobile-nav">
                        <ul className="navbar-nav mr-auto">
                            <li className="nav-item">
                                <a className="nav-link" href="/expenses">
                                    Expenses
                                </a>
                            </li>
                        </ul>

                        <ul className="navbar-nav ul-header-custom">
                            <li className="nav-item">
                                <a className="nav-link" href="/login">
                                    Login
                                </a>
                            </li>
                            <li className="nav-item">
                                <a className="nav-link" href="/login">
                                    Register
                                </a>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>
        )
    }
}

export default Header;