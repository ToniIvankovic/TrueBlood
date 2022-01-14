import React, { useReducer } from "react";
import { Link } from 'react-router-dom';
import Loginimg from './Loginimg.png';

const KreiranDonor = (props) => {
    return (
        <div className="sent">
            <div className="smile">
                <img src={Loginimg} alt="smileface" />
            </div>
            <div className="big">
                <p>Korisnički račun uspješno je kreiran!</p>
            </div>
            <div className="small">

                <p>
                    {props.user.role != 'BANK_WORKER' ? 'Na Vašu e-adresu ' : 'Na e-adresu donora '}
                    poslani su <span>donorId</span>, inicijalna <span>lozinka</span> i poveznica za <span>aktivaciju</span> računa
                </p>
            </div>
            <div className="idi">
                <Link to='/'>
                    <button className="registracija">Idi na naslovnicu</button>
                </Link>
            </div>
            {props.user.role == 'BANK_WORKER' ?
                <div className="idi">
                    <Link to='/pokusaj_doniranja'>
                        <button className="registracija">Nova donacija</button>
                    </Link>
                </div>
                : ''}
        </div>
    )
}

export default KreiranDonor;