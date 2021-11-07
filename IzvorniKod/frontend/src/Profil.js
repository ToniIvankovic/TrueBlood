import React from "react";
import { Link } from "react-router-dom";
import Profilimg from './Profile.png'

const Profil = () => {
    return(
        <div className="profile">
            <div className="ikona">
                <img src={Profilimg} alt="profileimg" />
            </div>
            <div className="uredi">
                <Link to='/update'>
                    <button className="registracija">Uredi podatke</button>
                </Link>    
            </div>
            <div className="donacije">
                <p>Moje donacije</p>
                <div className="lista">...</div>
            </div>
        </div>
    )
}

export default Profil;