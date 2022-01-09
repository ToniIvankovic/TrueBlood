import axios from './util/axios-instance';
import React, { useEffect, useState } from "react";
import { useRef } from "react";
import { useHistory } from 'react-router';
import ErrorCard from './ErrorCard';
import { getDonorById } from './Util';

const SlanjeKrvi = (props) => {

    const history = useHistory();
    const ref = useRef();

    const [bloodSupplyInfo, setBloodSupplyInfo] = useState({
        '0+': 0,
        '0-': 0,
        'A+': 0,
        'A-': 0,
        'B+': 0,
        'B-': 0,
        'AB+': 0,
        'AB-': 0
    });
    
    const [errorMessage, setErrorMessage] = useState('Greška');
    const [errorHidden, setErrorHidden] = useState(true);

    const handleChange = (event) => {
        let name = event.target.name;
        let value = event.target.value;
        setBloodSupplyInfo({
            ...bloodSupplyInfo,
            [name]: value
        });
    }

    const handleSubmit = (event) => {
        event.preventDefault();
        let bloodTypes = ['0+', '0-', 'A+', 'A-', 'B+', 'B-', 'AB+', 'AB-']
        let bloodSupplies = []
        for(let bloodType of bloodTypes){
            bloodSupplies.push(bloodSupplyInfo[bloodType]);
        }
        let arrayBloodSupplyInfo = {'bloodTypes': bloodTypes, 'numbersOfUnits': bloodSupplies}
        console.log('Submitting!');
        console.log(arrayBloodSupplyInfo);

        const url = '/api/v1/blood-supply/decrease'

        axios.post(url, arrayBloodSupplyInfo)
            .then((response) => {
                console.log('Blood supply succesfully decreased:');
                console.log(response.data)
                history.goBack();
            })
            .catch((error) => {
                console.log('Error while sending blood. Response: ' + error.response);
                console.log(error.response);
                //edit this
                if (error.response) {
                    if (error.response.status == 400) {
                        const message = error.response.data;
                        if (message.includes('veći je od dostupnog broja')) {
                            setErrorMessage('Greška! Nema toliko jedinica krvi u zalihi.');
                        } 
                        console.log(error.response.data);
                    } else {
                        setErrorMessage('Greška pri registraciji!');
                    }
                } else{
                    setErrorMessage('Unutarnja greška');
                }
                setErrorHidden(false);
            });
    }

    return (
        <div className="reg">
            <div className="roledesplay">({props.user.role})</div>
            <form onSubmit={(event) => handleSubmit(event)} className='formular'>
                <div className="tekst-secondary">
                    <p>Evidencija slanja krvi</p>
                </div>
                <div className="label-secondary">
                    Definiraj količine za slanje
                </div>
                <div className="dupli">
                    <div>
                        <label htmlFor='0+'>0+</label>
                        <input
                            onChange={(event) => handleChange(event)}
                            name='0+'
                            type="text"
                            defaultValue="0"
                            ></input>
                    </div>
                    <div>
                        <label htmlFor='0-'>0-</label>
                        <input
                            onChange={(event) => handleChange(event)}
                            name='0-'
                            type="text"
                            defaultValue="0"
                            ></input>
                    </div>
                </div>
                <div className="dupli">
                    <div>
                        <label htmlFor='A+'>A+</label>
                        <input
                            onChange={(event) => handleChange(event)}
                            name='A+'
                            type="text"
                            defaultValue="0"
                            ></input>
                    </div>
                    <div>
                        <label htmlFor='A-'>A-</label>
                        <input
                            onChange={(event) => handleChange(event)}
                            name='A-'
                            type="text"
                            defaultValue="0"
                            ></input>
                    </div>
                </div>
                <div className="dupli">
                    <div>
                        <label htmlFor='B+'>B+</label>
                        <input
                            onChange={(event) => handleChange(event)}
                            name='B+'
                            type="text"
                            defaultValue="0"
                            ></input>
                    </div>
                    <div>
                        <label htmlFor='B-'>B-</label>
                        <input
                            onChange={(event) => handleChange(event)}
                            name='B-'
                            type="text"
                            defaultValue="0"
                            ></input>
                    </div>
                </div>
                <div className="dupli">
                    <div>
                        <label htmlFor='AB+'>AB+</label>
                        <input
                            onChange={(event) => handleChange(event)}
                            name='AB+'
                            type="text"
                            defaultValue="0"
                            ></input>
                    </div>
                    <div>
                        <label htmlFor='AB-'>AB-</label>
                        <input
                            onChange={(event) => handleChange(event)}
                            name='AB-'
                            type="text"
                            defaultValue="0"
                            ></input>
                    </div>
                </div>
                {errorHidden ? null : <ErrorCard message={errorMessage} />}
                <div className="gumbi">
                    <button className='kreiraj'>Pošalji</button>
                </div>
            </form>
        </div>
    )
}

export default SlanjeKrvi;
