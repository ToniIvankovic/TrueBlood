import React, { useContext, useState } from "react";
import Image from './Image.png';
import {Link} from 'react-router-dom';
import axios from './util/axios-instance';
import { Buffer } from 'buffer';
import { useHistory } from "react-router";
import ErrorCard from "./ErrorCard";
import { GlobalContext } from "./context/GlobalState";


const Login = () => {

    let history = useHistory();

    const { updateLoggedInEvent } = useContext(GlobalContext);

    const [userId, setUserId] = useState('');
    const [password, setPassword] = useState('');
    const [errorHidden, setErrorHidden] = useState(true);

    const range = (lower, upper) => {
        if(upper - lower < 1) { return null; }
        return Array.from(new Array(upper - lower), (x, i) => i + lower);
    }

    const setLoginState = (value) => {
        updateLoggedInEvent('SET_LOGGED_IN', value);
    }
    
    const handleSubmit = (event) => {
        event.preventDefault();
        const url = '/api/v1/login';
        const credentials = Buffer.from(userId + ':' + password).toString('base64');
        const basicAuth = 'Basic ' + credentials;
        axios.get(url, {
            headers: {'Authorization': basicAuth}
        })
        .then((response) => {
            // if response.ok then route to profile
            setErrorHidden(true);
            setLoginState(true);
            console.log('LOGIN SUCCESS');
            history.push('/profil');
        })
        .catch((error) => {
            if(!error.response) {
                console.log(error.statusText);
                return;
            }
            const response = error.response;
            if(range(300, 400).includes(response.status)) {
                console.log('300 error: ' + response.status);
            } else if(range(400, 500).includes(response.status)) {
                if(response.status == 401) {
                    // authentication error, userid or password incorrect
                    console.log('userid and/or password incorrect');
                    setErrorHidden(false);
                } else if(response.status == 404) {
                    // hmm
                } else {
                    //other 400-range errors
                    console.log('400 error: ' + response.status)
                }
            } else if(range(500, 600).includes(response.status)) {
                console.log('Internal server error: ' + response.statusText);
            }
        });
    }

    return(
        <div className="homepage">
            <div className="text-login">
                <div className="text">
                    <div><p>Doniraj krv, spasi život!</p></div>
                    <Link to='/registracija'>
                        <button className="registracija"> Registriraj se</button>
                    </Link>
                </div>
                { errorHidden ? null : <ErrorCard /> }
                <form onSubmit={(event) => handleSubmit(event)} className='login'>
                    <p>Prijavi se!</p>
                    <input 
                        onChange={(e) => setUserId(e.target.value)}
                        className='input'
                        type='text'
                        placeholder="Email"
                        required
                    ></input>
                    <input
                        onChange={(e) => setPassword(e.target.value)}
                        className='input'
                        type='password'
                        placeholder="Lozinka"
                        required
                    ></input>
                    <button
                        className='submit'
                        type="submit"
                    >
                        Prijava
                    </button>
                </form>
            </div>
            <div className="image-alert">
                <p className="alert">Nedostaje krvi krvne grupe x</p>
                <img src={Image} alt="image1" className="image"/>
            </div>
        </div>
    )
}
export default Login;