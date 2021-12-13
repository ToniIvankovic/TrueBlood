import React, { useEffect, useState } from "react";
import { BrowserRouter as Router, Route, Switch } from "react-router-dom";

import Navbar from "./Navbar/Navbar";
import Login from "./Login";
import Faq from "./Faq";
import Kontakt from "./Kontakt";
import './index.css'
import Registracija from "./Registracija";
import KreiranDonor from "./KreiranDonor";
import Home from "./Home";
import Profil from "./Profil";
import Update from "./Update";
import PokusajDoniranja from "./PokusajDoniranja";
import TraziDonora from "./TraziDonora";
import RacunNeaktiviran from "./RacunNeaktiviran";

import { getCurrentUserIdAndRole, getAccActivated, isEqualWithNull, roleNone, userNone, userPublic } from "./Util";
import _ from 'lodash';

// TODO: global context for role and user data


const App = () => {

    //Provjeri je li itko logiran
    const [user, setUser] = useState(userNone);
    const [token, setToken] = useState("");

    useEffect(() => {
        console.log(token)
        setToken(window.localStorage.getItem('token'));
        if (token) {
            getCurrentUserIdAndRole(user, setUser);
        } else{
            setUser(userPublic);
        }
    }, [token]);


    //Kada se nađe user, provjeriti je li aktiviran i postaviti njegov role
    const [accActivated, setAccActivated] = useState(null);
    useEffect(() => {
        if(!isEqualWithNull(user,userNone)){
            getAccActivated(user.userId, setAccActivated);
        }
    }, [user]);

    
    //Uzimanje donora iz localStoragea (makar ga i nema)
    const [donor, setDonor] = useState(undefined)
    
    useEffect(() => {
        let donorFromStorage = JSON.parse(window.localStorage.getItem('donor'));
        if(donor == undefined || ! isEqualWithNull(donor, donorFromStorage) ){
            setDonor(donorFromStorage ? donorFromStorage : {});
        }
    },[donor]);


    //Samo ako su sva stanja postavljena, stranica se može renderirati - sprječava preuranjeni history.push i slično
    const[pageReady, setPageReady] = useState(false);
    
    useEffect(()=>{
        if( !isEqualWithNull(user, userNone) && accActivated != null && donor != undefined){
            setPageReady(true);
        }
    },[user, accActivated, donor]);
    
    
    if(!pageReady){
        return(<div>Loading</div>)
    }
    return (
        <div className='app'>
            <Router>
                <Navbar showProfile={token != null} />
                <Switch>
                    <Route path="/" exact>
                        <Home loggedIn={token != null} />
                    </Route>
                    <Route path="/prijava" exact>
                        <Login onLogin={() => {
                            getCurrentUserIdAndRole(user, setUser);
                            setToken(window.localStorage.getItem('token'));
                        }} />
                    </Route>
                    <Route path='/racun_neaktiviran' exact>
                        <RacunNeaktiviran user={user} accActivated={accActivated}/>
                    </Route>
                    <Route path="/profil" exact>
                        <Profil onLogout={() => {
                            setToken(null);
                            setDonor({});
                        }}
                            accActivated={accActivated}
                            user={user} />
                    </Route>
                    <Route path="/update" exact>
                        <Update />
                    </Route>
                    <Route path="/faq" exact>
                        <Faq />
                    </Route>
                    <Route path="/kontakt" exact>
                        <Kontakt />
                    </Route>
                    <Route path='/stvori_donora' exact>
                        <Registracija role={user.role} token={token} setDonor={setDonor} />
                    </Route>
                    <Route path='/kreiran_donor' exact>
                        <KreiranDonor user={user} />
                    </Route>
                    <Route path='/pokusaj_doniranja' exact>
                        <PokusajDoniranja user={user} donor={donor} />
                    </Route>
                    <Route path='/trazi_donora' exact>
                        <TraziDonora user={user} setDonor={setDonor} />
                    </Route>
                </Switch>
            </Router>
        </div>
    );
}

export default App;