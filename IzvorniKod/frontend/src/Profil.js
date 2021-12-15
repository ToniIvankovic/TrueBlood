import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import Profilimg from './Profile.png';
import axios from './util/axios-instance';
import { useHistory } from "react-router";

const Profil = (props) => {

    const history = useHistory();

    useEffect(() => {
        const token = window.localStorage.getItem('token');
        if (token == null) {
            history.push('/');
        }
    }, []);

    const logout = (event) => {
        const url = '/api/v1/logout';
        axios.get(url)
            .then((response) => {
                console.log('LOGOUT SUCCESS');
                history.push('/');
            })
            .catch((error) => {
                console.log('LOGOUT ERROR: ' + error);
            })
            .finally(() => {
                window.localStorage.clear();
                props.onLogout();
            });
    }

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
                    <Link to='/stvori_donora'>
                        <button className="registracija">Uredi podatke</button>
                    </Link>
                    : ''}
                <button onClick={(event) => logout(event)} className="submit">Odjava</button>
                {props.user.role == 'BANK_WORKER' ?
                [
                    <Link key={0} to='/pokusaj_doniranja'>
                        <button className="registracija">Stvori pokušaj doniranja</button>
                    </Link>,
                    <Link key={1} to='/stvori_donora'>
                        <button className="registracija">Stvori račun donora</button>
                    </Link>
                ]
                    
                    : ''}
            </div>
            {props.user.role == 'DONOR' ?
                <div className="donacije">
                    <p>Moje donacije</p>
                    <div className="lista">...</div>
                </div>
                : ''}
            
            {
            //privremeni indikator je li račun aktiviran i vodi na stranicu za neaktivirane
            }
            {props.accActivated ? 
            '' :
            <div>
                <Link to='/racun_neaktiviran'>
                    <button className="registracija">neaktiviran</button>
                </Link>
            </div>
            }
            
        </div>
    )
}

export default Profil;