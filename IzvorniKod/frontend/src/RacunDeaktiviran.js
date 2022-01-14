import React from "react";
import { Link } from 'react-router-dom';
import Loginimg from './Loginimg.png';
import { donorNone, workerNone } from "./Util";


const RacunDeaktiviran = (props) => {

    const reset = () => {
        props.setExistingDonor(false); 
        props.setDonor(donorNone)
        props.setExistingWorker(false); 
        props.setWorker(workerNone)
    }

    return (
        <div className="sent">
            <div className="smile">
                <img src={Loginimg} alt="smileface" />
            </div>
            <div className="big">
                <p>Korisnički račun deaktiviran</p>
            </div>
            <div className="idi">
                <Link to='/profil'>
                    <button className="registracija">Profil</button>
                </Link>
                <Link to='/deaktiviraj_racun'>
                    <button className="registracija" onClick={reset}>Deaktiviraj još jedan račun</button> 
                </Link>
            </div>
        </div>
    )
}

export default RacunDeaktiviran;