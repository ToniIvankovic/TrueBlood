import axios from './util/axios-instance';
import React, { useEffect, useState } from "react";
import { useRef } from "react";
import { useHistory } from 'react-router';
import ErrorCard from './ErrorCard';
import { formatDate, formatDateToCro, formatDateToEng, formatDateToSlash, getWorkerById } from './Util';

const StvoriDjelatnika = (props) => {

    const history = useHistory();
    const ref = useRef();

    const [workerInfo, setWorkerInfo] = useState({
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
    });

    useEffect(()=>{
        if(!props.user.userId) 
            return;
        if (props.user.role == 'BANK_WORKER') {
            props.setExisting(true);
            getWorkerById(props.user.userId,setWorkerInfo);
        } else if(props.user.role == 'ADMIN'){
            props.setExisting(false);
        }
    },[props.user.userId])

    // useEffect(()=>{
    //     console.log("donorinfo u stvoridonora")
    //     console.log(donorInfo)
    // },[donorInfo])
    
    const [errorMessage, setErrorMessage] = useState('Greška');
    const [errorHidden, setErrorHidden] = useState(true);


    // useEffect(() => {
    //     if (props.user.role == 'DONOR') {
    //         history.push('/profil');
    //     }
    // }, [props.user.role]);


    const handleChange = (event) => {
        let name = event.target.name;
        let value = event.target.value;
        if(name == "birthDate"){
            value = formatDateToCro(value);
        }
        setWorkerInfo({
            ...workerInfo,
            [name]: value
        });
    }

    const handleSubmit = (event) => {
        event.preventDefault();
        console.log('Submitting!');
        console.log(workerInfo);

        var url
        if(props.existing){
            url='/api/v1/bank-worker/update';
        } else{
            url = '/api/v1/bank-worker/registration'
        }

        axios.post(url, workerInfo)
            .then((response) => {
                console.log('Worker successfully created:');
                console.log(response.data)

                if(props.user.role == "BANK_WORKER"){
                    getWorkerById(props.user.userId, props.setUser)
                }
                // props.setWorker(response.data) //Upitna potreba za stanjem worker
                if(props.existing){
                    history.goBack();
                }
                else{
                    props.setExisting(true);
                    history.push('/kreiran_djelatnik');
                }
            })
            .catch((error) => {
                console.log('Error while creating worker. Response: ' + error.response);
                console.log(error.response);
                if (error.response) {
                    if (error.response.status == 400) {
                        const message = error.response.data;
                        console.log(message);
                        if(!message){
                            setErrorMessage('Nepoznata greška...');
                        } else if (message.includes('oib')) {
                            if (message.includes('already exists')) {
                                setErrorMessage('Greška! OIB već postoji.');
                            } else {
                                setErrorMessage('Greška! Pogrešan format OIB-a.');
                            }
                        } 
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
                    defaultValue={"ID: " + props.user.userId}
                    disabled></input>
                </div>    
                :""}
                <div className="dupli">
                    <input
                        onChange={(event) => handleChange(event)}
                        name='firstName'
                        type="text"
                        placeholder="Ime *"
                        defaultValue={workerInfo.firstName}
                        required></input>
                    <input
                        onChange={(event) => handleChange(event)}
                        name='lastName'
                        type="text"
                        placeholder="Prezime *"
                        defaultValue={workerInfo.lastName}
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
                        defaultValue={workerInfo.oib}
                        required></input>
                </div>
                <div className="dupli">
                    <input
                        onChange={(event) => handleChange(event)}
                        name='birthDate'
                        type={workerInfo.birthDate ? 'date' : 'text'}
                        onFocus={() => {ref.current.type = 'date'}}
                        ref={ref}
                        placeholder="Datum rođenja *"
                        defaultValue={formatDateToEng(workerInfo.birthDate)}
                        required></input>
                    <input
                        onChange={(event) => handleChange(event)}
                        name='birthPlace'
                        type="text"
                        placeholder="Mjesto rođenja *"
                        defaultValue={workerInfo.birthPlace}
                        required></input>
                </div>
                <div className="single">
                    <input
                        onChange={(event) => handleChange(event)}
                        name='address'
                        type="text"
                        placeholder="Adresa stanovanja *"
                        defaultValue={workerInfo.address}
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
                        defaultValue={workerInfo.email}
                        required></input>
                </div>
                <div className="single">
                    <input
                        onChange={(event) => handleChange(event)}
                        name='privateContact'
                        type="text"
                        placeholder="Kontakt (osobni) *"
                        defaultValue={workerInfo.privateContact}
                        maxLength='10'
                        required></input>
                </div>
                <div className="dupli">
                    <input
                        onChange={(event) => handleChange(event)}
                        name='workPlace'
                        type="text"
                        defaultValue={workerInfo.workPlace}
                        placeholder="Mjesto zaposlenja (firma)*"
                        required></input>
                    <input
                        onChange={(event) => handleChange(event)}
                        name='workContact'
                        type="text"
                        placeholder="Kontakt (poslovni)*"
                        defaultValue={workerInfo.workContact}
                        maxLength='10'
                        required></input>
                </div>
                {errorHidden ? null : <ErrorCard message={errorMessage} />}
                <div className="gumbi">
                    <button className='kreiraj'>{props.existing? "Pohrani promjene":"Kreiraj račun"}</button>
                </div>
            </form>
        </div>
    )
}

export default StvoriDjelatnika;
