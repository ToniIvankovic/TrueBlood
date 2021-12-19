import axios from './util/axios-instance';
import React, { useEffect, useState } from "react";
import { useRef } from "react";
import { useHistory } from 'react-router';
import ErrorCard from './ErrorCard';
import { getDonorById } from './Util';

const StvoriDonora = (props) => {

    const history = useHistory();
    const ref = useRef();

    const [donorInfo, setDonorInfo] = useState({
        donorId: '',
        firstName: '',
        lastName: '',
        oib: '',
        birthDate: '',
        birthPlace: '',
        address: '',
        workPlace: '',
        privateContact: '',
        workContact: '',
        email: '',
        bloodType: ''
    });

    //Razmisliti o unificiranju dohvata podataka sa backenda i da existing služi samo za naslov
    useEffect(()=>{
        console.log('Existing:' + props.existing)
        if(!props.user.userId) 
            return;
        if(props.existing){
            getDonorById(props.donor.donorId,setDonorInfo);
        }
        else if (props.user.role == 'DONOR') {
            props.setExisting(true);
            getDonorById(props.user.userId,setDonorInfo);
        }
    },[props.user.userId])

    
    const [errorMessage, setErrorMessage] = useState('Greška');
    const [errorHidden, setErrorHidden] = useState(true);


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

        var url
        if(props.existing){
            if(props.user.userRole == 'DONOR'){
                console.log("Doesnt work yet :(")
                return;
            }
            else{
                url='/api/v1/donor/update';
            }
        } else{
            if (props.user.role == 'BANK_WORKER') {
                url = '/api/v1/donor/add-donor'
            } else {
                url = '/api/v1/donor/registration'
            }
        }

        axios.post(url, donorInfo, { headers: { "Authorization": `Bearer ${props.token}` } })
            .then((response) => {
                console.log('Donor successfully created:');
                console.log(response.data)

                props.setDonor(response.data)
                if(props.existing){
                    history.goBack();
                }
                else
                    history.push('/kreiran_donor');
            })
            .catch((error) => {
                console.log('Error while creating donor. Response: ' + error.response);
                console.log(error.response);
                if (error.response) {
                    if (error.response.status == 400) {
                        const message = error.response.data;
                        if (message.includes('oib')) {
                            if (message.includes('already exists')) {
                                setErrorMessage('Greška! OIB već postoji.');
                            } else {
                                setErrorMessage('Greška! Pogrešan format OIB-a.');
                            }
                        } else if (message.includes('blood')) {
                            setErrorMessage('Greška! Krvna grupa mora se postaviti.');
                        }
                        console.log(error.response.data);
                    } else {
                        setErrorMessage('Greška pri registraciji!');
                    }
                } else{
                    setErrorMessage('Unutarnja greška');s
                }
                setErrorHidden(false);
            });
    }

    return (
        <div className="reg">
            ({props.user.role})
            <form onSubmit={(event) => handleSubmit(event)} className='formular'>
                <div className="tekst">
                    <p>{props.existing?"Uredi ":"Kreiraj "}korisnički račun!</p>
                </div>
                <div className="label">
                    <label>Osobni podaci</label>
                </div>
                {props.existing? //OVO POLJE AKO SE MOŽE ZASIVITI
                <div className="single">
                <input
                    onChange={(event) => handleChange(event)}
                    name='donorId'
                    type="text"
                    defaultValue={donorInfo.donorId}
                    disabled></input>
                </div>    
                :""}
                <div className="dupli">
                    <input
                        onChange={(event) => handleChange(event)}
                        name='firstName'
                        type="text"
                        placeholder="Ime *"
                        defaultValue={donorInfo.firstName}
                        required></input>
                    <input
                        onChange={(event) => handleChange(event)}
                        name='lastName'
                        type="text"
                        placeholder="Prezime *"
                        defaultValue={donorInfo.lastName}
                        required></input>
                </div>
                <div className="single">
                    <input
                        onChange={(event) => handleChange(event)}
                        name='oib'
                        type="text"
                        placeholder="OIB *"
                        minLength='11'
                        maxLength='11'
                        defaultValue={donorInfo.oib}
                        required></input>
                </div>
                <div className="dupli">
                    <input
                        onChange={(event) => handleChange(event)}
                        name='birthDate'
                        type='text'
                        ref={ref}
                        onFocus={() => (ref.current.type = 'date')}
                        onBlur={() => (ref.current.type = 'text')}
                        placeholder="Datum rođenja *"
                        defaultValue={donorInfo.birthDate}
                        required></input>
                    <input
                        onChange={(event) => handleChange(event)}
                        name='birthPlace'
                        type="text"
                        placeholder="Mjesto rođenja *"
                        defaultValue={donorInfo.birthPlace}
                        required></input>
                </div>
                <div className="single">
                    <input
                        onChange={(event) => handleChange(event)}
                        name='address'
                        type="text"
                        placeholder="Adresa stanovanja *"
                        defaultValue={donorInfo.address}
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
                        defaultValue={donorInfo.email}
                        required></input>
                </div>
                <div className="single">
                    <input
                        onChange={(event) => handleChange(event)}
                        name='privateContact'
                        type="text"
                        placeholder="Kontakt (osobni) *"
                        defaultValue={donorInfo.privateContact}
                        maxLength='10'
                        required></input>
                </div>
                <div className="dupli">
                    <input
                        onChange={(event) => handleChange(event)}
                        name='workPlace'
                        type="text"
                        defaultValue={donorInfo.workPlace}
                        placeholder="Mjesto zaposlenja (firma)"></input>
                    <input
                        onChange={(event) => handleChange(event)}
                        name='workContact'
                        type="text"
                        placeholder="Kontakt (poslovni)"
                        defaultValue={donorInfo.workContact}
                        maxLength='10'></input>
                </div>
                <div className="label">
                    <label>Zdravstveni podaci*</label>
                </div>
                <div className="krgrupe">
                    <label>Krvna grupa</label>
                    <select
                        disabled={props.user.role != "BANK_WORKER"}
                        value={donorInfo.bloodType.trim()}
                        onChange={(event) => {
                            event.target.name = "bloodType";
                            handleChange(event);
                        }}>
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
                <div className="napomena">
                    <p>*Vašu krvnu grupu popunjava djelatnik pri prvom doniranju krvi.</p>
                </div>
                {errorHidden ? null : <ErrorCard message={errorMessage} />}
                <div className="gumbi">
                    <button className='kreiraj'>{props.existing? "Pohrani promjene":"Kreiraj račun"}</button>
                </div>
            </form>
        </div>
    )
}

export default StvoriDonora;