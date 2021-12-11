import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import Profilimg from './Profile.png';
import axios from './util/axios-instance';
import { useHistory } from "react-router";

const Profil = (props) => {

    const user = props.user;

    const history = useHistory();
    
    useEffect(() => {
        const token = window.localStorage.getItem('token');
        if(token == null) {
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

    return(
        <div className="profile">
            <div className="ikona">
                <img src={Profilimg} alt="profileimg" />
            </div>
            <div className="basicInfo">
                <div>{user.userId}</div>
                <div>{user.role}</div>
            </div>
            <div className="uredi">
                <Link to='/update'>
                    <button className="registracija">Uredi podatke</button>
                </Link>
                <button onClick={(event) => logout(event)} className="submit">Odjava</button>
            </div>
            <div className="donacije">
                <p>Moje donacije</p>
                <div className="lista">...</div>
            </div>
        </div>
    )
}

export default Profil;