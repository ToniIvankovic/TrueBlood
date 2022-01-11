import React, { useEffect, useState } from "react";
import Image from './Image.png';
import { Link } from 'react-router-dom';
import axios from './util/axios-instance';
import { useHistory } from "react-router";
import ErrorCard from "./ErrorCard";
import { generateBasicAuthHeader } from "./Util"; 

// TODO: Clean up logs

const Login = (props) => {

    let history = useHistory();

    // useEffect(() => {
    //     const token = window.localStorage.getItem('token');
    //     if (token != null) {
    //         history.push('/');
    //     }
    // }, []);

    const [userId, setUserId] = useState('');
    const [password, setPassword] = useState('');
    const [errorHidden, setErrorHidden] = useState(true);
    const [errorMessage, setErrorMessage] = useState('Netočan ID ili lozinka!');

    const range = (lower, upper) => {
        if (upper - lower < 1) { return null; }
        return Array.from(new Array(upper - lower), (x, i) => i + lower);
    }

    const handleSubmit = (event) => {
        event.preventDefault();
        const url = '/api/v1/login';
        // const basicAuthHeader = generateBasicAuthHeader(userId, password);
        axios.get(url, {
            auth: {
                username: userId,
                password: password
            }
        })
            .then((response) => {
                setErrorHidden(true);
                //window.localStorage.setItem('token', response.headers.authorization);
                props.onLogin();
                history.push('/profil');
            })
            .catch((error) => {
                if (!error.response) {
                    setErrorMessage('Greška! Nije moguće dohvatiti server. Molimo pričekajte nekoliko trenutaka...');
                    setErrorHidden(false);
                    console.log(error.statusText);
                    return;
                }
                const response = error.response;
                if (range(400, 500).includes(response.status)) {
                    if (response.status == 401) {
                        // authentication error, userid or password incorrect
                        console.log('userid and/or password incorrect');
                        setErrorMessage('Netočan ID ili lozinka!');
                        setErrorHidden(false);
                    } else {
                        if(response.includes("not activated")){
                            setErrorMessage('Račun nije aktiviran - molimo prije korištenja aktivirajte račun poveznicom u e-pošti');
                            setErrorHidden(false);
                        } else if (response.includes("permanently deactivated")){
                            setErrorMessage('Račun je deaktiviran - ako mislite da je ovo greška, molimo kontaktirajte administratora sustava');
                            setErrorHidden(false);
                        }
                    }
                } else if (range(500, 600).includes(response.status)) {
                    console.log('Internal server error: ' + response.statusText);
                }
            });
    }

    return (
        <div className="homepage">
            <div className="text-login">
                <div className="text">
                    <p>Doniraj krv, spasi život!</p>
                    <Link to='/stvori_donora'>
                        <button className="registracija" onClick={(event) => props.setExistingDonor(false)}> Registriraj se</button>
                    </Link>
                </div>
                {errorHidden ? null : <ErrorCard message={errorMessage} />}
                <form onSubmit={(event) => handleSubmit(event)} className='login'>
                    <p>Prijavi se!</p>
                    <input
                        onChange={(e) => setUserId(e.target.value)}
                        className='input'
                        type='text'
                        placeholder="Identifikacijski broj"
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
                <img src={Image} alt="image1" className="image" />
            </div>
        </div>
    )
}
export default Login;