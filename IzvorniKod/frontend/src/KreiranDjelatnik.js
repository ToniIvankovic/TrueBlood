import React, { useReducer } from "react";
import { Link } from 'react-router-dom';
import Loginimg from './Loginimg.png';

const KreiranDjelatnik = (props) => {
    return (
        <div className="sent">
            <div className="smile">
                <img src={Loginimg} alt="smileface" />
            </div>
            <div className="big">
                <p>Korisnički račun djelatnika uspješno je kreiran!</p>
            </div>
            <div className="small">

                <p>
                    Na e-adresu djelatnika poslani su <span>bankWorkerId</span>, inicijalna <span>lozinka</span> i poveznica za <span>aktivaciju</span> računa
                </p>
            </div>
            <div className="idi">
                <Link to='/'>
                    <button className="registracija">Idi na naslovnicu</button>
                </Link>
                <Link to='/stvori_djelatnika'>
                    <button className="registracija">Stvori još jednog djelatnika</button>
                </Link>
            </div>
        </div>
    )
}

export default KreiranDjelatnik;