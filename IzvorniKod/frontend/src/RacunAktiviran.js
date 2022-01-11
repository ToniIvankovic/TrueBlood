import React, { useReducer } from "react";
import { Link } from 'react-router-dom';
import Loginimg from './Loginimg.png';

const RacunAktiviran = (props) => {
    return (
        <div className="sent">
            <div className="smile">
                <img src={Loginimg} alt="smileface" />
            </div>
            <div className="big">
                <p>Korisnički račun uspješno je aktiviran!</p>
            </div>
            <div className="small">
                <p>
                    Prijavite se u sustav pomoću podataka dostavljenih u aktivacijskoj e-poruci.
                </p>
            </div>
            <div className="idi">
                <Link to='/prijava'>
                    <button className="registracija">Prijava</button>
                </Link>
            </div>
            <div className="idi">
                <Link to='/'>
                    <button className="registracija">Idi na naslovnicu</button>
                </Link>
            </div>
        </div>
    )
}

export default RacunAktiviran;