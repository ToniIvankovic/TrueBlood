import axios from './util/axios-instance';
import React, { useEffect, useState } from "react";
import { useRef } from "react";
import { useHistory } from 'react-router';
import ErrorCard from './ErrorCard';
import { Link } from 'react-router-dom';

const TraziDonora = (props) => {

    const history = useHistory();
    const ref = useRef();

    const [errorMessage, setErrorMessage] = useState('Greška');
    const [errorHidden, setErrorHidden] = useState(true);

    useEffect(() => {
        // if (props.user.userId && props.user.role != 'BANK_WORKER') {
        //     history.push('/');
        // }
    });

    const donorNone = {};
    const [donor, setDonor] = useState(donorNone);

    const handleSubmit = (event) => {
        event.preventDefault();
        window.localStorage.setItem('donor', JSON.stringify(donor));
        props.setDonor(donor)
        history.push('/pokusaj_doniranja');
    }

    const findDonor = (event) => {
        //treba slati upit na endpoint i dohvattiti preostale podatke - ovo je samo fake placeholder
        setDonor({
            donorId: 1234567,
            firstName: "toni",
            lastName: "ivankovic",
            oib: "24144225112",
            bloodType: "0+"
        })
    }

    useEffect(() => {
        console.log("Donor u trazidonora:")
        console.log(donor)
    }, [donor]);

    return (
        <div className="reg">
            <form onSubmit={(event) => handleSubmit(event)} className='formular'>
                ({props.user.role})
                <div className="tekst">
                    <p>Pronađi donora</p>
                </div>
                <br />
                <div className="label">
                    <label>Osobni podaci</label>
                </div>
                <div className="single">
                    <input
                        name='donorId'
                        type="text"
                        placeholder={"donorId: " + donor.donorId}
                    ></input>
                </div>
                <div className="dupli">
                    <input
                        name='firstName'
                        type="text"
                        placeholder={"ime: " + donor.firstName}
                    ></input>
                    <input
                        name='lastName'
                        type="text"
                        placeholder={"prezime: " + donor.lastName}
                    ></input>
                </div>
                <div className="single">
                    <input
                        name='oib'
                        type="text"
                        placeholder={"OIB: " + donor.oib}
                    ></input>
                </div>

                {errorHidden ? null : <ErrorCard message={errorMessage} />}
                <div className="gumbi">
                    <button type="button" onClick={(event) => findDonor(event)} className='kreiraj'>Pronađi</button>
                </div>
                <br />
                <div className="gumbi">
                    <button className='kreiraj'>Uzmi ovog donora</button>
                </div>
            </form>
        </div>
    )
}

export default TraziDonora;
