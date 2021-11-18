import React, { useState } from "react";
import {Link} from "react-router-dom";
import "./Navbar.css";

const Navbar = (props) => {

    const [isMob, setIsMob] = useState(false);
    const handleClick = () => setIsMob(!isMob)

    return (
        <nav className="navbar">
            <Link to="/">
                <h3 className="logo">Trueblood</h3>
            </Link>
            <ul className={isMob ? "links-mob" : "links"}>
                { props.showProfile ?
                    <Link to="/profil" className="profil" onClick={()=>setIsMob(false)}>
                        <li>Profil</li>
                    </Link>
                : null }
                <Link to="/faq" className="faq" onClick={()=>setIsMob(false)}>
                    <li>FAQ</li>
                </Link>
                <Link to="/kontakt" className="kontakt" onClick={()=>setIsMob(false)}>
                    <li>Kontakt</li>
                </Link>
            </ul>
            <button className = "mobile-menu" onClick={handleClick}>
                {isMob ? <i className="fas fa-times"></i> : <i className="fas fa-bars"></i>}
            </button>
        </nav>
    )
}

export default Navbar;