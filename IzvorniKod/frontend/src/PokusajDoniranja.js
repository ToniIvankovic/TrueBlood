import axios from './util/axios-instance';
import React, { useEffect, useState } from "react";
import { useRef } from "react";
import { useHistory } from 'react-router';
import ErrorCard from './ErrorCard';
import { Link } from 'react-router-dom';
import ZdravstveniPodaci from './ZdravstveniPodaci';

const PokusajDoniranja = (props) => {

    const history = useHistory();
    const ref = useRef();

    const [errorMessage, setErrorMessage] = useState('Greška');
    const [errorHidden, setErrorHidden] = useState(true);

    useEffect(() => {
        if (props.user.role && props.user.role != 'BANK_WORKER' && props.user.role != 'ADMIN') {
            history.push('/profil');
        }
    }, [props.role]);

    const handleSubmit = (event) => {
        console.log("submit")
    }

    return (
        <div className="reg">
            <form onSubmit={(event) => handleSubmit(event)} className='formular'>
                ({props.user.role})
                <div className="tekst">
                    <p>Nova donacija </p>
                </div>
                <div className="gumbi">
                    <br />
                    <Link to='/trazi_donora'>
                        <button className='maligumb' onClick={(event) => {props.setExistingDonor(false); props.setDonor(false)}}>Pronađi donora</button>
                    </Link>
                    {props.existingDonor?
                    <Link to='/stvori_donora'>
                        <button className='maligumb' onClick={(event) => {props.setExistingDonor(true)}}>Uredi donora</button>
                    </Link>
                    :''
                    }
                </div>
                {props.existingDonor? 
                <div>
                    <div className="label">
                        <label>Osobni podaci</label>
                    </div>
                    <div className="single">
                        <input
                            name='donorId'
                            type="text"
                            placeholder={"donorId: " + props.donor.donorId}
                            ></input>
                    </div>
                    <div className="dupli">
                        <input
                            name='firstName'
                            type="text"
                            placeholder={"ime: " + props.donor.firstName}
                            ></input>
                        <input
                            name='lastName'
                            type="text"
                            placeholder={"prezime: " + props.donor.lastName}
                            ></input>
                    </div>
                    <div className="single">
                        <input
                            name='oib'
                            type="text"
                            placeholder={"OIB: " + props.donor.oib}
                            ></input>
                    </div>
                    <div className="krgrupe">
                        <label>Krvna grupa</label>
                        <select value={props.donor.bloodType}>
                            <option value="---">---</option>
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
                    
                    <ZdravstveniPodaci question={"Tjelesna masa ispod 55 kg?"} id="q1"/>
                    <ZdravstveniPodaci question={"Tjelesna temperatura iznad 37°C?"} id="q2"/>
                    <ZdravstveniPodaci question={"Povišen ili nizak krvni tlak?"} id="q3"/>
                    <ZdravstveniPodaci question={"Osoba ima ubrzan rad srca?"} id="q4"/>
                    <ZdravstveniPodaci question={"Osoba ima povišenu razinu hemoglobina u krvi?"} id="q5"/>
                    <ZdravstveniPodaci question={"Osoba trenutno uzima antibiotike ili druge lijekove?"} id="q6"/>
                    <ZdravstveniPodaci question={"Osoba je konzumirala alkoholna pića unutar 8 sati prije darivanja krvi?"} id="q7"/>
                    <ZdravstveniPodaci question={"Osoba boluje od lakše aktune bolesti?"} id="q8"/>
                    <ZdravstveniPodaci question={"Osoba (žena) je trudna, doji ili ima menstruaciju?"} id="q9"/>
                    <ZdravstveniPodaci question={"Osoba tog dana obavlja opasne poslove (visinski / dubinski radovi)?"} id="q10"/>

                    <hr className='label' />

                    <ZdravstveniPodaci question={"Osoba je bolovala ili boluje od teških kroničnih bolesti dišnog i/ili probavnog sustava?"} id="q11"/>
                    <ZdravstveniPodaci question={"Osoba boluje od bolesti srca i krvnih žila, zloćudnih bolesti, bolesti jetre, AIDS-a, šećerne bolesti te živčanih i duševnih bolesti?"} id="q12"/>
                    <ZdravstveniPodaci question={"Osoba je ovisnik o alkoholu ili drogama?"} id="q13"/>
                    <ZdravstveniPodaci question={"Osoba (muškarac) je u životu imala spolni odnos s drugim muškarcima?"} id="q14"/>
                    <ZdravstveniPodaci question={"Osoba je imala spolni odnos s prostitutkama?"} id="q15"/>
                    <ZdravstveniPodaci question={"Osoba često mijenja seksualne partnere (promiskuitetna osoba)?"} id="q16"/>
                    <ZdravstveniPodaci question={"Osoba je uzimala drogu intravenskim putem?"} id="q17"/>
                    <ZdravstveniPodaci question={"Osoba je liječana od spolno prenosivih bolesti?"} id="q18"/>
                    <ZdravstveniPodaci question={"Osoba je HIV-pozitivna?"} id="q19"/>
                    <ZdravstveniPodaci question={"Osoba je seksualni partner gore navedenih osoba?"} id="q20"/>

                    {errorHidden ? null : <ErrorCard message={errorMessage} />}
                    <div className="gumbi">
                        <button className='kreiraj'>Doniraj</button>
                    </div>
                </div>
                :''}
                
            </form>
        </div>
    )
}

export default PokusajDoniranja;
