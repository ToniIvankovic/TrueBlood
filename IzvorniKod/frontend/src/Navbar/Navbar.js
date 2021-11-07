import React, {useState} from "react";
import {Link} from "react-router-dom";
import "./Navbar.css";

const Navbar = () => {

    const [isMob, setIsMob] = useState(false);
    return (
        <nav className="navbar">
            <Link to="/">
                <h3 className="logo">Trueblood</h3>
            </Link>
            <ul className={isMob ? "links-mob" : "links"} onClick={()=>setIsMob(false)}>
                <Link to="/profil" className="profil">
                    <li>Profil</li>
                </Link>
                <Link to="/faq" className="faq">
                    <li>FAQ</li>
                </Link>
                <Link to="/kontakt" className="kontakt">
                    <li>Kontakt</li>
                </Link>
            </ul>
            <button className = "mobile-menu" onClick={()=>setIsMob(!isMob)}>
                {isMob ? <i className="fas fa-times"></i> : <i className="fas fa-bars"></i>}
            </button>
        </nav>
    )
}

export default Navbar;