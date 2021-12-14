import axios from './util/axios-instance';
import React, { useEffect, useState } from "react";
import { useRef } from "react";
import { useHistory } from 'react-router';
import ErrorCard from './ErrorCard';
import { Link } from 'react-router-dom';

const PokusajDoniranja = (props) => {

    const history = useHistory();
    const ref = useRef();

    const [errorMessage, setErrorMessage] = useState('Greška');
    const [errorHidden, setErrorHidden] = useState(true);

    useEffect(() => {
        // if (props.user.userId && props.user.role != 'BANK_WORKER') {
        //     history.push('/');
        // }
    });

    const handleSubmit = (event) => {
        console.log("submit")
    }

    return (
        <div className="reg">
            <form onSubmit={(event) => handleSubmit(event)} className='formular'>
                ({props.user.role})
                <div className="tekst">
                    <p>Stvori pokušaj doniranja </p>
                </div>
                <br />
                <Link to='/trazi_donora'>
                    <button className='kreiraj'>Pronađi donora</button>
                </Link>
                <div className="label">
                    <label>Osobni podaci</label>
                </div>
                <div className="single">
                    <input
                        name='donorId'
                        type="text"
                        placeholder={"donorId: " + props.donor.donorId}
                        disabled></input>
                </div>
                <div className="dupli">
                    <input
                        name='firstName'
                        type="text"
                        placeholder={"ime: " + props.donor.firstName}
                        disabled></input>
                    <input
                        name='lastName'
                        type="text"
                        placeholder={"prezime: " + props.donor.lastName}
                        disabled></input>
                </div>
                <div className="single">
                    <input
                        name='oib'
                        type="text"
                        placeholder={"OIB: " + props.donor.oib}
                        disabled></input>
                </div>
                <div className="krgrupe">
                    <label>Krvna grupa</label>
                    <select value={props.donor.bloodType} disabled>
                        <option value="---">Nema</option>
                        <option value="A+">A+</option>
                        <option value="A-">A-</option>
                        <option value="B+">B+</option>
                        <option value="B-">B-</option>
                        <option value="AB+">AB+</option>
                        <option value="AB-">AB-</option>
                        <option value="0+">0+</option>
                        <option value="0-">0-</option>
                    </select>
                </div>

                <div className="label">
                    <label>Zdravstveni podaci</label>
                </div>

                {errorHidden ? null : <ErrorCard message={errorMessage} />}
                <div className="gumbi">
                    <button className='kreiraj'>Doniraj</button>
                </div>
            </form>
        </div>
    )
}

export default PokusajDoniranja;
