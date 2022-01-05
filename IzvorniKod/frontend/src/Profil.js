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
    const [daysUntilDonation, setDaysUntilDonation] = useState(undefined)
    useEffect(()=>{
        if(bloodSupply != undefined){
            if(daysUntilDonation != undefined && bloodType != undefined && props.user.role == 'DONOR'){
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
                let warningString = "Krvne grupe izvan optimalnih granica: ";
                for (let supply of bloodSupply){
                    if(supply.review == 'TOO LITTLE'){
                        anyOutOfBounds = true;
                        warningString += supply.bloodType + " (premalo), ";
                    } else if(supply.review == 'TOO MUCH'){
                        anyOutOfBounds = true;
                        warningString += supply.bloodType + " (previše), ";
                    }
                }
                if(anyOutOfBounds){
                    setWarningMessage(warningString)
                }
            }
        }
    },[bloodType, bloodSupply, daysUntilDonation])

    console.log(warningMessage)

    return (
        <div className="profile">
            <div className="ikona">
                <img src={Profilimg} alt="profileimg" />
            </div>
            <div className="basicInfo">
                <div>{props.user.userId}</div>
                <div>{props.user.role}</div>
            </div>
            <div className="uredi">
                {props.user.role == 'DONOR' ?
                    [
                    <Link key={5} to='/stvori_donora'>
                        <button className="registracija"  onClick={(event) => {props.setExistingDonor(true)}}>Uredi podatke</button>
                    </Link>,
                    <Link key={6} to='/povijest_doniranja'>
                        <button className="registracija">Prošle donacije</button>
                    </Link>,
                    <div key={7} className="image-alert">
                        {warningMessage? <p className="alert">{warningMessage}</p> : ''}
                    </div>
                    ]
                    : ''}
                {props.user.role == 'BANK_WORKER' ?
                    [
                    <Link key={2} to='/stvori_djelatnika'>
                        <button className="registracija" >Uredi podatke</button>
                    </Link>,
                    <Link key={0} to='/pokusaj_doniranja'>
                        <button className="registracija">Stvori pokušaj doniranja</button>
                    </Link>,
                    <div key={8} className="image-alert">
                        {warningMessage? <p className="alert">{warningMessage}</p> : ''}
                    </div>
                    ]
                    : ''}
                {props.user.role == 'ADMIN' ?
                    [
                    <Link key={3} to='/stvori_djelatnika'>
                        <button className="registracija">Stvori djelatnika</button>
                    </Link>,
                    <Link key={4} to='/deaktiviraj_racun'>
                        <button className="registracija">Deaktiviraj račun</button>
                    </Link>,
                    <Link key={5} to='/optimalne_granice'>
                        <button className="registracija">Postavi optimalne granice</button>
                    </Link>
                    ]
                    : ''}
            </div>
            
            <button onClick={() => props.setUser({
                    ...props.user,
                    role: 'DONOR'
                })}>Sad sam donor</button>
                
            <button onClick={() => props.setUser({
                    ...props.user,
                    role: 'BANK_WORKER'
                })}>Sad sam worker</button>
                
            <button onClick={() => props.setUser({
                    ...props.user,
                    role: 'ADMIN'
                })}>Sad sam admin</button>
        </div>
    )
}

export default Profil;