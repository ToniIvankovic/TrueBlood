import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import Profilimg from './Profile.png';
import { getBloodSupply, getDonorBloodType, getDonorNextDonation } from "./Util";

const Profil = (props) => {

    const [bloodType, setBloodType] = useState(undefined)
    const [bloodSupply, setBloodSupply] = useState(undefined);
    useEffect(()=>{

        getBloodSupply(setBloodSupply);
        
        if(props.user.role == 'DONOR'){
            getDonorBloodType(props.user.userId, setBloodType)
            getDonorNextDonation (props.user.userId, setDaysUntilDonation)
        }
    },[props.user.role])    
    
    const [warningMessage, setWarningMessage] = useState(undefined)
    const [warningArray, setWarningArray] = useState([])
    const [daysUntilDonation, setDaysUntilDonation] = useState(undefined)
    useEffect(()=>{
        if(bloodSupply != undefined){
            if(daysUntilDonation !== undefined && bloodType != undefined && props.user.role == 'DONOR'){
                if(daysUntilDonation != 0){
                    setWarningMessage("Hvala na nedavnoj donaciji krvi - ponovno ćete moći donirati za " + daysUntilDonation + " dana")
                }
                else{
                    for (let supply of bloodSupply){
                        if(supply.bloodType.trim() == bloodType.trim() && supply.review == 'TOO LITTLE'){
                            setWarningMessage("Nedostaje krvi vaše krvne grupe (" + bloodType 
                            + ")\nDonirajte ako ste u mogućnosti")
                            break;
                        }
                    }
                }
            } else if(props.user.role == 'BANK_WORKER'){
                let anyOutOfBounds = false;
                let warningArrayLocal = [];
                let warningString = "Krvne grupe izvan optimalnih granica: ";
                for (let supply of bloodSupply){
                    if(supply.review == 'TOO LITTLE'){
                        anyOutOfBounds = true;
                        warningString += supply.bloodType + " (premalo), ";
                        warningArrayLocal.push(supply.bloodType + " (premalo)")
                    } else if(supply.review == 'TOO MUCH'){
                        anyOutOfBounds = true;
                        warningString += supply.bloodType + " (previše), ";
                        warningArrayLocal.push(supply.bloodType + " (previše)")
                    }
                }
                if(anyOutOfBounds){
                    setWarningMessage(warningString)
                    setWarningArray(warningArrayLocal)
                }
            }
        }
    },[bloodType, bloodSupply, daysUntilDonation])

    return (
        <div className="profile">



            <div className="profile-akcije">
                {props.user.role == 'DONOR' ?
                    [
                    <Link key={5} to='/stvori_donora'>
                        <button className="secondary"  onClick={(event) => {props.setExistingDonor(true)}}>Uredi podatke</button>
                    </Link>,
                    <Link key={6} to='/povijest_doniranja'>
                        <button className="secondary">Moje donacije</button>
                    </Link>,

                    ]
                    : ''}
                {props.user.role == 'BANK_WORKER' ?
                    [
                    <Link key={2} to='/stvori_djelatnika'>
                        <button className="secondary" >Uredi podatke</button>
                    </Link>,
                    <Link key={0} to='/pokusaj_doniranja'>
                        <button className="secondary">Nova donacija</button>
                    </Link>,
                    <Link key={9} to='/slanje_krvi'>
                        <button className="secondary">Slanje krvi</button>
                    </Link>,

                    ]
                    : ''}
                {props.user.role == 'ADMIN' ?
                    [
                    <Link key={3} to='/stvori_djelatnika'>
                        <button className="secondary">Novi djelatnik</button>
                    </Link>,
                    <Link key={4} to='/deaktiviraj_racun'>
                        <button className="secondary">Upravljaj računima</button>
                    </Link>,
                    <Link key={5} to='/optimalne_granice'>
                        <button className="secondary">Uredi granice</button>
                    </Link>
                    ]
                    : ''}
            </div>

            <div className="profile-card">
                <div className="ikona-ID">
                    <div className="ikona">
                        <img src={Profilimg} alt="profileimg" />
                    </div>
                    <div className="ident">
                        <div>ID: {props.user.userId}</div>
                    </div>
                </div>
                {(props.user.role == "DONOR" || props.user.role == "BANK_WORKER")?
                <div className="podaci-role">
                    <div>Uloga: {props.user.role == "BANK_WORKER" ? "DJELATNIK" : props.user.role}</div>
                    <div></div>
                    <div>Ime: {props.user.firstName}</div>
                    <div>Prezime: {props.user.lastName}</div>
                    <div>Datum rođenja: {props.user.birthDate}</div>
                    <div>OIB: {props.user.oib}</div>
                </div>
                : 
                <div className="podaci-role">
                    <div>Uloga: {props.user.role}</div>
                </div>
                }
            </div>
            
            {props.user.role == "DONOR" ? 
                <div className="image-alert">
                    {warningMessage? <p className="alert">{warningMessage}</p> : ''}
                </div>
                : ''}

            {props.user.role == 'BANK_WORKER' && warningMessage? 
                <div className="image-alert">
                    <div className="alert">
                        <div className="alert">Krvne grupe izvan optimalnih granica:</div>
                {warningArray.map((message, i) => {
                    if(message.search("premalo") != -1){
                        return (<div key={i} className="alert alert-red">{message}</div>)
                    } else{
                        return (<div key={i} className="alert">{message}</div>)
                    }
                })}
                    </div>
                </div>
                   
                : ''}
                
        </div>
    )
}

export default Profil;