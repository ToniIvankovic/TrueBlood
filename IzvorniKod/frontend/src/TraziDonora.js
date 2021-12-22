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

    const donorNone = {};
    const [foundDonor, setFoundDonor] = useState(donorNone);

    // useEffect(() => {
    //     if (props.user.role && props.user.role != 'BANK_WORKER' && props.user.role != 'ADMIN') {
    //         history.push('/profil');
    //     }
    // }, [props.role]);

    const handleSubmit = (event) => {
        event.preventDefault();
        // window.localStorage.setItem('foundDonor', JSON.stringify(foundDonor));
        props.setDonor(foundDonor)   //Dojavljuje app.js-u da je foundDonor postavljen u localstorage
        props.setExisting(true)
        history.push('/pokusaj_doniranja');
    }

    const findDonor = (event) => {
        //treba slati upit na endpoint i dohvatiti preostale podatke - ovo je samo fake placeholder
        //TODO: u tražilici napraviti onchange koji će mijenjati neke stateove i oni se šalju u requestu
        setFoundDonor({
            donorId: 1000003,
            firstName: "toni",
            lastName: "ivankovic",
            oib: "24144225112",
            bloodType: "0+"
        })
    }

    useEffect(() => {
        console.log("Donor u trazidonora:")
        console.log(foundDonor)
    }, [foundDonor]);

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
                        placeholder={"donorId: " + foundDonor.donorId}
                    ></input>
                </div>
                <div className="dupli">
                    <input
                        name='firstName'
                        type="text"
                        placeholder={"ime: " + foundDonor.firstName}
                    ></input>
                    <input
                        name='lastName'
                        type="text"
                        placeholder={"prezime: " + foundDonor.lastName}
                    ></input>
                </div>
                <div className="single">
                    <input
                        name='oib'
                        type="text"
                        placeholder={"OIB: " + foundDonor.oib}
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
