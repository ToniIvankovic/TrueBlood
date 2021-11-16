import axios from './util/axios-instance';
import React, { useState } from "react";
import { useRef } from "react";
import {Link} from 'react-router-dom';

const Registracija = () => {
    const ref = useRef();

    const [donorInfo, setDonorInfo] = useState({
        firstName: '',
        lastName: '',
        oib: '',
        birthDate: '',
        birthPlace: '',
        address: '',
        workPlace: '',
        privateContact: '',
        workContact: '',
        email: ''
    });

    const handleChange = (event) => {
        let name = event.target.name;
        let value = event.target.value;
        setDonorInfo({
            ...donorInfo,
            [name]: value
        });
    }

    const handleSubmit = (event) => {
        event.preventDefault();
        console.log('Submitting!');
        console.log(donorInfo);
        const url = '/api/v1/donor/registration'
        axios.post(url, donorInfo)
        .then((response) => {
            if(response.ok) {
                console.log("User successfully created.");
            }
        })
        .catch((error) => {
            console.log('Error while creating user. Response: ' + error.response);
        });
    }

    return(
        <div className="reg">
            <form onSubmit={(event) => handleSubmit(event)} className='formular'>
                <div className="tekst">
                <p>Kreiraj korisnički račun!</p>
                </div>
                <div className="label">
                    <label>Osobni podaci</label>
                </div>
                <div className="dupli">
                    <input
                        onChange={(event) => handleChange(event)}
                        name='firstName' 
                        type="text"
                        placeholder="Ime *"
                        required></input>
                    <input
                        onChange={(event) => handleChange(event)}
                        name='lastName' 
                        type="text"
                        placeholder="Prezime *"
                        required></input>
                </div>
                <div className="single">
                    <input
                        onChange={(event) => handleChange(event)}
                        name='oib' 
                        type="text"
                        placeholder="OIB *"
                        maxLength='11'
                        required></input>
                </div>
                <div className="dupli">
                    <input
                        onChange={(event) => handleChange(event)}
                        name='birthDate' 
                        type = 'text'
                        ref={ref}
                        onFocus = {() => (ref.current.type = 'date')}
                        onBlur = {() => (ref.current.type = 'text')}
                        placeholder="Datum rođenja"></input>
                    <input
                        onChange={(event) => handleChange(event)}
                        name='birthPlace' 
                        type="text"
                        placeholder="Mjesto rođenja *"
                        required></input>
                </div>
                <div className="single">
                    <input
                        onChange={(event) => handleChange(event)}
                        name='address' 
                        type="text"
                        placeholder="Adresa stanovanja *"
                        required></input>
                </div>
                <div className="label">
                    <label>Kontakt podaci</label>
                </div>
                <div className="single">
                    <input
                        onChange={(event) => handleChange(event)}
                        name='email' 
                        type="text"
                        placeholder="Email *"
                        required></input>
                </div>
                <div className="single">
                    <input
                        onChange={(event) => handleChange(event)}
                        name='privateContact' 
                        type="text"
                        placeholder="Kontakt (osobni) *"
                        maxLength='10'
                        required></input>
                </div>
                <div className="dupli">
                    <input
                        onChange={(event) => handleChange(event)}
                        name='workplace' 
                        type="text"
                        placeholder="Mjesto zaposlenja (firma)"></input>
                    <input
                        onChange={(event) => handleChange(event)}
                        name='workContact' 
                        type="text"
                        placeholder="Kontakt (poslovni)"
                        maxLength='10'></input>                    
                </div>
                {/* <div className="label">
                    <label>Zdravstveni podaci*</label>
                </div> */}
                {/* <div className="krgrupe">
                    <label>Krvna grupa</label>
                    <select>
                        <option value="A+">A+</option>
                        <option value="A-">A-</option>
                        <option value="B+">B+</option>
                        <option value="B-">B-</option>
                        <option value="AB+">AB+</option>
                        <option value="AB-">AB-</option>
                        <option value="0+">0+</option>
                        <option value="0-">0-</option>
                    </select>       
                </div> */}
                <div className="gumbi">
                    <button className='kreiraj'>Kreiraj račun</button>
                </div>
                {/* <div className="napomena">
                    <p>*Vaše zdravstvene podatke popunjava djelatnik prije doniranja krvi.</p>
                </div> */}
            </form>
        </div>
    )
}

export default Registracija;