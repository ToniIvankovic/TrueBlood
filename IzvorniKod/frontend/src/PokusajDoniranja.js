import axios from "./util/axios-instance";
import React, { useEffect, useState } from "react";
import { useRef } from "react";
import { Route, Link } from "react-router-dom";
import SearchBar from "./components/SearchBar";
import { searchDonorColumns } from "./model/SearchDonorColumns";
import Trazilica from "./Trazilica";
import DonorSearchIntegrated from "./components/DonorSearchIntegrated";
import { Box, Button, Divider, Grid } from "@mui/material";
import { useHistory } from 'react-router';
import ErrorCard from './ErrorCard';
import ZdravstveniPodaci from './ZdravstveniPodaci';
import { donorNone } from './Util';

const PokusajDoniranja = (props) => {

    const questions = [
        "Tjelesna masa ispod 55 kg?",
        "Tjelesna temperatura iznad 37°C?",
        "Povišen ili nizak krvni tlak?",
        "Osoba ima ubrzan rad srca?",
        "Osoba ima povišenu razinu hemoglobina u krvi?",
        "Osoba trenutno uzima antibiotike ili druge lijekove?",
        "Osoba je konzumirala alkoholna pića unutar 8 sati prije darivanja krvi?",
        "Osoba boluje od lakše aktune bolesti?",
        "Osoba (žena) je trudna, doji ili ima menstruaciju?",
        "Osoba tog dana obavlja opasne poslove (visinski / dubinski radovi)?",

        "Osoba je bolovala ili boluje od teških kroničnih bolesti dišnog i/ili probavnog sustava?",
        "Osoba boluje od bolesti srca i krvnih žila, zloćudnih bolesti, bolesti jetre, AIDS-a, šećerne bolesti te živčanih i duševnih bolesti?",
        "Osoba je ovisnik o alkoholu ili drogama?",
        "Osoba (muškarac) je u životu imala spolni odnos s drugim muškarcima?",
        "Osoba je imala spolni odnos s prostitutkama?",
        "Osoba često mijenja seksualne partnere (promiskuitetna osoba)?",
        "Osoba je uzimala drogu intravenskim putem?",
        "Osoba je liječana od spolno prenosivih bolesti?",
        "Osoba je HIV-pozitivna?",
        "Osoba je seksualni partner gore navedenih osoba?"
    ]

    const questionInputs = [];
    useEffect(()=>{
        for(let i = 0; i < questions.length; i++){
            questionInputs.push()
        }
    },[])
    const history = useHistory();
    const ref = useRef();

    const [errorMessage, setErrorMessage] = useState("Greška");
    const [errorHidden, setErrorHidden] = useState(true);
    const [selectedDonor, setSelectedDonor] = useState(null);
    const [shouldUpdateDonor, setShouldUpdateDonor] = useState(true);


    const [donationTryInfo, setDonationTryInfo] = useState({
        donationPlace: props.donationPlace
    });

    // hide error message on donor change
    useEffect(() => {
        setErrorHidden(true);
    }, [selectedDonor]);

    useEffect(()=>{
        setDonationTryInfo({
            ...donationTryInfo,
            donorId: props.donor.donorId
        });
    },[props.donor.donorId])

    const handleChange = (event) => {
        let name = event.target.name;
        let value = event.target.value;
        setDonationTryInfo({
            ...donationTryInfo,
            [name]: value
        });
    }

    const handleSubmit = (event) => {
        event.preventDefault();
        let rejectedReasons = "";
        let permRejectedReasons = "";
        for(let key in donationTryInfo){
            if(donationTryInfo[key] == 'da'){
                let number = key.substring(1);
                if(number >= 10){
                    permRejectedReasons += questions[number] + "; ";
                }
                if(rejectedReasons == "") //Radi evidentiranja samo jednoga
                    rejectedReasons += questions[number] + "; ";
            }
        }
        if(permRejectedReasons != "")   //Ako se evidentira samo jedan razlog, onda perm pobjeđuje temp
            rejectedReasons = permRejectedReasons.split(";")[0];
        

        let retVal = {
            donorId: donationTryInfo.donorId,
            bankWorkerId: props.user.userId,
            donationPlace: donationTryInfo.donationPlace
        }

        if(permRejectedReasons != ''){
            props.setPermRejected(true);
            retVal['isReasonPerm'] = true;
        } else{
            props.setPermRejected(false);
        }
        if(rejectedReasons != ''){
            retVal['rejectReason'] = rejectedReasons;
            props.setRejectReason(rejectedReasons.split('?')[0]); //Pazi na ovo
        }

        if(permRejectedReasons == '' && rejectedReasons == ''){
            props.setRejectReason(undefined);
        }
        
        const url = "/api/v1/donation-try";
        axios.post(url, retVal)
            .then((response) => {
                props.setDonationPlace(donationTryInfo.donationPlace); //Čuva mjesto za nova doniranja
                props.setDonor(donorNone);
                props.setExistingDonor(false);
                props.setSuccessfulDonation(response.data.successful)
                if(!response.data.successful){
                    props.setRejectReason(response.data.rejectedReason);
                }

                history.push('/donirano');
            })
            .catch((error) => {
                console.log('Error while creating donor. Response: ' + error.response);
                console.log(error.response);
                if (error.response) {
                    if (error.response.status == 400) {
                        const message = error.response.data;
                        if (message.includes('SQL') && message.includes('place')) {
                            setErrorMessage('Neispravno mjesto donacije');
                        } else if (message.includes('pričekat')) {
                            setErrorMessage(message);
                        } else if (message.includes('blood')) {
                            setErrorMessage('Greška! Krvna grupa mora se postaviti.');
                        } else if(message.includes('Blood type')){
                            setErrorMessage('Greška! Donoru se mora odrediti krvna grupa prije donacije!');
                        } else{
                            setErrorMessage(message);
                        }
                    } else {
                        setErrorMessage('Greška u autorizaciji!');
                    }
                } else{
                    setErrorMessage('Unutarnja greška');
                }
                setErrorHidden(false);
            });
    }

    return (
        <div className="reg">
            <form onSubmit={(event) => handleSubmit(event)} className='formular'>
                <div className="tekst">
                    <p>Nova donacija </p>
                </div>
                <div className="gumb-traka">
                    
                    <Link to='/stvori_donora'>
                        <button className='maligumb' onClick={(event) => {props.setExistingDonor(false); props.setDonor(donorNone)}}>Stvori donora</button>
                    </Link>
                    <Link to='/trazi_donora'>
                        <button className='maligumb' onClick={(event) => {props.setExistingDonor(false); props.setDonor(donorNone)}}>Pronađi donora</button>
                    </Link>
                    
                    {props.existingDonor?
                    [
                        <Link key={1} to='/stvori_donora'>
                            <button className='maligumb' onClick={(event) => {props.setExistingDonor(true)}}>Uredi donora</button>
                        </Link>,
                        <Link key={2} to='/povijest_doniranja_donora'>
                            <button className='maligumb'>Prošle donacije</button>
                        </Link>
                    ]
                    :''
                    }
                </div>
                { props.existingDonor? 
                <div>
                    <div className="single">
                        <input
                        name='donationPlace'
                        type='text'
                        defaultValue={props.donationPlace}
                        placeholder='Mjesto doniranja'
                        onChange={(event) => handleChange(event)}
                        required></input>
                    </div>
                    <div className="label">
                        <label>Osobni podaci</label>
                    </div>
                    <div className="single">
                        <input
                            name='donorId'
                            type="text"
                            defaultValue={"donorId: " + ((props.donor.donorId)? props.donor.donorId:'')}
                            disabled></input>
                    </div>
                    <div className="dupli">
                        <input
                            name='firstName'
                            type="text"
                            defaultValue={"Ime: " + ((props.donor.firstName)? props.donor.firstName:'')}
                            disabled></input>
                        <input
                            name='lastName'
                            type="text"
                            defaultValue={"Prezime: " + ((props.donor.lastName)? props.donor.lastName:'')}
                            disabled></input>
                    </div>
                    <div className="single">
                        <input
                            name='oib'
                            type="text"
                            defaultValue={"OIB: " + ((props.donor.oib)? props.donor.oib:'')}
                            disabled></input>
                    </div>
                    <div className="krgrupe">
                        <label>Krvna grupa</label>
                        <select value={props.donor.bloodType?.trim()} disabled> {/*Možda treba trimmati bloodtype ako postoji*/ }
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

                    {questions.map((question, i) => {
                        if(i==10) {
                            return <div  key={i}>
                                <hr className='label' />
                                <ZdravstveniPodaci question={question}  key={i} id={"q"+i} handleChange={handleChange} />
                            </div>
                        }
                        return <ZdravstveniPodaci question={question} key={i} id={"q"+i} handleChange={handleChange} />
                    })}
                    
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
