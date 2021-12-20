import axios from './util/axios-instance';
import React, { useEffect, useState } from "react";
import { useRef } from "react";
import { useHistory } from 'react-router';
import ErrorCard from './ErrorCard';
import { Link } from 'react-router-dom';
import ZdravstveniPodaci from './ZdravstveniPodaci';

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

    const [errorMessage, setErrorMessage] = useState('Greška');
    const [errorHidden, setErrorHidden] = useState(true);

    // useEffect(() => {
    //     if (props.user.role && props.user.role != 'BANK_WORKER' && props.user.role != 'ADMIN') {
    //         history.push('/profil');
    //     }
    // }, [props.role]);

    const [donationTryInfo, setDonationTryInfo] = useState({});
    const [yesFields, setYesFields] = useState({});

    useEffect(()=>{
        setDonationTryInfo({
            ...donationTryInfo,
            donorId: props.donor.donorId
        });
    },[props.donor.donorId])

    const handleChange = (event) => {
        let name = event.target.name;
        let value = event.target.value;
        // console.log(name, value);
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
                if(number >= 10)
                    permRejectedReasons += questions[number] + "; ";
                if(rejectedReasons == "") //Radi evidentiranja samo jednoga
                    rejectedReasons += questions[number] + "; ";
            }
        }
        if(permRejectedReasons != "")   //Ako se evidentira samo jedan razlog, onda perm pobjeđuje temp
            rejectedReasons = permRejectedReasons.split(";")[0];
        
        console.log(rejectedReasons);

        const retVal = {
            permRejectReason: permRejectedReasons,
            rejectReason: rejectedReasons,
            donorId: donationTryInfo.donorId,
            bankWorkerId: props.user.userId
        }
        console.log(retVal)
        const url = "/api/v1/donation-try";
        axios.post(url, retVal, { headers: { "Authorization": `Bearer ${props.token}` } })
            .then((response) => {
                console.log('Donation try successfully created:');
                console.log(response.data)

                history.push('/profil');
            })
            .catch((error) => {
                console.log('Error while creating donor. Response: ' + error.response);
                console.log(error.response);
                if (error.response) {
                    if (error.response.status == 400) {
                        const message = error.response.data;
                        console.log(error.response.data);
                        if (message.includes('SQL')) {
                            setErrorMessage('Predug razlog odbijanja!');
                        } else if (message.includes('blood')) {
                            setErrorMessage('Greška! Krvna grupa mora se postaviti.');
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
                <div className='roledesplay'>
                    ({props.user.role})
                </div>
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
                { props.existingDonor? 
                <div>
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
                        <select value={props.donor.bloodType} disabled> {/*Možda treba trimmati bloodtype ako postoji*/ }
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
