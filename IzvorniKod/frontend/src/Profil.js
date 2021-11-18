import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import Profilimg from './Profile.png';
import axios from './util/axios-instance';
import { useHistory } from "react-router";

const Profil = (props) => {

    const [user, setUser] = useState({});

    let history = useHistory();

    const getUserInfo = async () => {
        const url = '/api/v1/user';
        const token = window.localStorage.getItem('token');
        console.log("GETTING USER INFO: " + token);
        const bearerAuth = 'Bearer ' + token;
        await axios.get(url, {
            headers: {'Authorization': bearerAuth}
        })
        .then((response) => {
            if(response.data != null) {
                setUser({
                    userId: response.data.id,
                    role: response.data.role
                });
            }
        })
        .catch((error) => {
            console.log('Error retrieving user info: ' + error);
            history.push('/');
        })
    }

    useEffect(() => {
        getUserInfo();
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
                <div>{user ? user.userId : null}</div>
                <div>{user ? user.role : null}</div>
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