import React, { useState } from "react";
import {Link} from "react-router-dom";
import "./Navbar.css";
import { useHistory } from "react-router";
import axios from '../util/axios-instance';

const Navbar = (props) => {

    const [isMob, setIsMob] = useState(false);
    const handleClick = () => setIsMob(!isMob)
    const history = useHistory();

    const logout = () => {
        const url = '/api/v1/logout';
        axios.get(url)
            .then((response) => {
                console.log('LOGOUT SUCCESS');
            })
            .catch((error) => {
                console.log('LOGOUT ERROR: ' + error);
            })
            .finally(() => {
                window.localStorage.clear();
                props.onLogout();
                history.push('/');
            });
    }

    return (
        <nav className="navbar">
            <Link to="/">
                <h3 className="logo">Trueblood</h3>
            </Link>
            <ul className={isMob ? "links-mob" : "links"}>
                { props.showProfile ?
                    <Link to="/profil" className="profil" onClick={()=>setIsMob(false)}>
                        <li key="1">Profil</li>
                    </Link>
                : null }
                <Link to="/faq" className="faq" onClick={()=>setIsMob(false)}>
                    <li key="3">FAQ</li>
                </Link>
                <Link to="/kontakt" className="kontakt" onClick={()=>setIsMob(false)}>
                    <li key="4">Kontakt</li>
                </Link>
                { props.showProfile ?
                
                <Link to='/' className="kontakt" onClick={()=>setIsMob(false)}>
                    <li onClick={()=>{logout(); setIsMob(false); }} key="2">Odjava</li>
                </Link>
                : null }
            </ul>
            <button className = "mobile-menu" onClick={handleClick}>
                {isMob ? <i className="fas fa-times"></i> : <i className="fas fa-bars"></i>}
            </button>
        </nav>
    )
}

export default Navbar;