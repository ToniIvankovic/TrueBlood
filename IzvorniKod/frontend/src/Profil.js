import React, { useContext, useEffect } from "react";
import { Link } from "react-router-dom";
import Profilimg from './Profile.png';
import axios from './util/axios-instance';
import { useHistory } from "react-router";
import { GlobalContext } from "./context/GlobalState";

const Profil = () => {

    let history = useHistory();

    const { loggedIn, updateLoggedInEvent } = useContext(GlobalContext);

    useEffect(() => {
        if(!loggedIn) {
            history.push('/');
        }
    })

    const logout = (event) => {
        const url = '/api/v1/logout';
        axios.get(url)
        .then((response) => {
            updateLoggedInEvent('SET_LOGGED_IN', false);
            console.log('LOGOUT SUCCESS');
            history.push('/');
        })
        .catch((error) => {
            console.log('LOGOUT ERROR: ' + error);
        });
    }

    return(
        <div className="profile">
            <div className="ikona">
                <img src={Profilimg} alt="profileimg" />
            </div>
            <div className="uredi">
                <Link to='/update'>
                    <button className="registracija">Uredi podatke</button>
                </Link>
                <Link to='/'>
                    <button onClick={(event) => logout(event)} className="submit">Odjava</button>
                </Link>      
            </div>
            <div className="donacije">
                <p>Moje donacije</p>
                <div className="lista">...</div>
            </div>
        </div>
    )
}

export default Profil;