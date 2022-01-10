import axios from './util/axios-instance';
import React, { useEffect, useState } from "react";
import { useRef } from "react";
import { useHistory } from 'react-router';
import ErrorCard from './ErrorCard';
import { formatDateToCro, formatDateToEng, getDonorById } from './Util';

const StvoriDonora = (props) => {

    const history = useHistory();
    const ref = useRef();

    const [donorInfo, setDonorInfo] = useState({
        donorId: '',
        firstName: '',
        lastName: '',
        oib: '',
        gender: '',
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
        if (props.user.role == 'DONOR') {
            props.setExisting(true);
            getDonorById(props.user.userId,setDonorInfo);
        }
        else if(props.existing){
            getDonorById(props.donor.donorId,setDonorInfo);
        }
    },[props.user.userId])

    
    const [errorMessage, setErrorMessage] = useState('Greška');
    const [errorHidden, setErrorHidden] = useState(true);


    const handleChange = (event) => {
        let name = event.target.name;
        let value = event.target.value;
        if(name == "birthDate"){
            value = formatDateToCro(value);
        }
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
            if(props.user.role == 'DONOR'){
                url='/api/v1/donor/update';
            }
            else{
                url='/api/v1/donor/update-donor';
            }
        } else{
            if (props.user.role == 'BANK_WORKER') {
                url = '/api/v1/donor/add-donor'
            } else {
                url = '/api/v1/donor/registration'
            }
        }

        //Zabraniti workeru da updatea donora bez da postavi krvnu grupu (ako ju ne dira) 
        axios.post(url, donorInfo)
            .then((response) => {
                console.log('Donor successfully created:');
                console.log(response.data)

                props.setDonor(response.data)
                if(props.existing){
                    history.goBack();
                }
                else{
                    props.setExisting(true);
                    history.push('/kreiran_donor');
                }
            })
            .catch((error) => {
                console.log('Error while creating donor. Response: ' + error.response);
                console.log(error.response);
                if (error.response) {
                    if (error.response.status == 400) {
                        const message = error.response.data;
                        if (message == undefined) {
                            setErrorMessage('Nepoznata greška...');
                        } else if (message.includes('oib')) {
                            if (message.includes('already exists')) {
                                setErrorMessage('Greška! OIB već postoji.');
                            } else {
                                setErrorMessage('Greška! Pogrešan format OIB-a.');
                            }
                        } else if (message.includes('blood')) {
                            setErrorMessage('Greška! Krvna grupa mora se postaviti.');
                        } else{
                            setErrorMessage(message);
                        }
                        console.log(error.response.data);
                    } else {
                        setErrorMessage('Greška pri registraciji!');
                    }
                } else if(error.request) {
                    setErrorMessage('Nije primljen odgovor od poslužitelja')
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
                        defaultValue={donorInfo.donorId? "ID: " + donorInfo.donorId : ''}
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
                <div className="redak">
                    <div className="question">
                        Spol:
                    </div>
                    <div className="odgovor">
                        <label htmlFor="M">Muški</label>
                        <input 
                        id="M" 
                        type="radio" 
                        name = "gender"
                        value = 'M' 
                        onChange={(event) => handleChange(event)}
                        checked = {donorInfo.gender == 'M'}
                        required/>
                    </div>
                    <div className="odgovor">
                        <label htmlFor="F">Ženski</label>
                        <input 
                        id="F" 
                        type="radio" 
                        name ="gender" 
                        onChange={(event) => handleChange(event)}
                        checked = {donorInfo.gender == 'F'}
                        value = 'F'
                        required/>
                    </div>
                </div>  
                <div className="dupli">
                    <input
                        onChange={(event) => handleChange(event)}
                        name='birthDate'
                        type={donorInfo.birthDate ? 'date' : 'text'}
                        onFocus={() => {ref.current.type = 'date'}}
                        ref={ref}
                        placeholder="Datum rođenja *"
                        defaultValue={formatDateToEng(donorInfo.birthDate)}
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
                        value={donorInfo.bloodType? donorInfo.bloodType.trim() : '---'}
                        onChange={(event) => {
                            event.target.name = "bloodType";
                            handleChange(event);
                        }}>
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
