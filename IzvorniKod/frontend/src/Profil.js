import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import Profilimg from './Profile.png';

const Profil = (props) => {


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
                    </Link>
                    ]
                    : ''}
                {props.user.role == 'BANK_WORKER' ?
                    [
                    <Link key={2} to='/stvori_djelatnika'>
                        <button className="registracija" >Uredi podatke</button>
                    </Link>,
                    <Link key={0} to='/pokusaj_doniranja'>
                        <button className="registracija">Stvori pokušaj doniranja</button>
                    </Link>
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