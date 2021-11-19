import React from "react";
import {Link} from 'react-router-dom';
import Loginimg from './Loginimg.png';

const Autorizacija = () => {
    return (
        <div className="sent">
            <div className="smile">
                <img src={Loginimg} alt="smileface"/>
            </div>
            <div className="big">
                <p>Vaš korisnički račun je uspješno kreiran!</p>
            </div>
            <div className="small">
                <p>Na Vašu email adresu poslan je link za <span>aktivaciju</span> računa i odabir <span>lozinke</span>.</p>
            </div>
            <div className="idi">
                <Link to='/'>
                    <button className="registracija">Idi na naslovnicu</button>
                </Link>
            </div>
        </div>
    )
}

export default Autorizacija;