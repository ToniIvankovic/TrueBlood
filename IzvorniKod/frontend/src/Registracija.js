import axios from './util/axios-instance';
import React, { useEffect, useState } from "react";
import { useRef } from "react";
import { useHistory } from 'react-router';
import ErrorCard from './ErrorCard';

const Registracija = (props) => {

    const history = useHistory();
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

    const [errorMessage, setErrorMessage] = useState('Greška');
    const [errorHidden, setErrorHidden] = useState(true);

    const [user, setUser] = useState({});
    
    useEffect(() => {
        if(props.role === 'DONOR'){
            history.push('/');
        }
     },[props.role]);
 
    

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
            console.log('User successfully created.');
            history.push('/autorizacija');
        })
        .catch((error) => {
            console.log('Error while creating user. Response: ' + error.response);
            if(error.response) {
                if(error.response.status == 400) {
                    setErrorMessage('Greška! Neispravan OIB (već postoji ili neispravan format).');
                } else {
                    setErrorMessage('Greška pri registraciji!');
                }
            }
            setErrorHidden(false);
        });
    }

    return(
        <div className="reg">
            <form onSubmit={(event) => handleSubmit(event)} className='formular'>
                <div className="tekst">
                <p>Kreiraj korisnički račun! ({props.role})</p>
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
                <div className="label">
                    <label>Zdravstveni podaci*</label>
                </div>
                <div className="krgrupe">
                    <label>Krvna grupa</label>
                    <select  disabled={props.role!="WORKER"}> {/*Possibly treba izmijeniti ovisno o backend implementaciji rolea*/ }
                        <option selected value="---">Nema</option>
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
                <div className="napomena">
                    <p>*Vašu krvnu grupu popunjava djelatnik pri prvom doniranju krvi.</p>
                </div>
                { errorHidden ? null : <ErrorCard message={errorMessage}/> }
                <div className="gumbi">
                    <button className='kreiraj'>Kreiraj račun</button>
                </div>
            </form>
        </div>
    )
}

export default Registracija;