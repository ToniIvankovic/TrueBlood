import React, { useEffect, useState } from "react";
import { BrowserRouter as Router, Route, Switch } from "react-router-dom";

import Navbar from "./Navbar/Navbar";
import Login from "./Login";
import Faq from "./Faq";
import Kontakt from "./Kontakt";
import './index.css'
import StvoriDonora from "./StvoriDonora";
import StvoriDjelatnika from "./StvoriDjelatnika";
import KreiranDonor from "./KreiranDonor";
import Home from "./Home";
import Profil from "./Profil";
import Update from "./Update";
import PokusajDoniranja from "./PokusajDoniranja";
import TraziDonora from "./TraziDonora";
import RacunNeaktiviran from "./RacunNeaktiviran";

import { getCurrentUserIdAndRole, getAccActivated, isEqualWithNull, userNone, userPublic, donorNone, workerNone } from "./Util";
import _ from 'lodash';
import KreiranDjelatnik from "./KreiranDjelatnik";
import PostPokusajDoniranja from "./PostPokusajDoniranja";

// TODO: global context for role and user data - done?

const App = () => {

    //Provjeri je li itko logiran
    const [user, setUser] = useState(userNone);
    const [token, setToken] = useState("");

    useEffect(() => {
        setToken(window.localStorage.getItem('token'));
        if (token) {
            getCurrentUserIdAndRole(user, setUser);
        } else{
            setUser(userPublic);
        }
    }, [token]);
    console.log(user)


    //Kada se nađe user, provjeriti je li aktiviran i postaviti njegov role
    const [accActivated, setAccActivated] = useState(null);
    useEffect(() => {
        if(!isEqualWithNull(user,userNone)){
            getAccActivated(user.userId, setAccActivated);
        }
    }, [user]);

    const [existingDonor, setExistingDonor] = useState(false);
    const [existingWorker, setExistingWorker] = useState(false);
    
    const [donor, setDonor] = useState(donorNone);
    const [worker, setWorker] = useState(workerNone);

    
    const [donationPlace, setDonationPlace] = useState(undefined);
    const [successfulDonation, setSuccessfulDonation] = useState(false);
    const [rejectReason, setRejectReason] = useState(undefined);    
    const [permRejected, setPermRejected] = useState(false);    
    
    //page ready
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
                        }}
                        setExistingDonor={setExistingDonor} />
                    </Route>
                    <Route path='/racun_neaktiviran' exact>
                        <RacunNeaktiviran user={user} accActivated={accActivated}/>
                    </Route>
                    <Route path="/profil" exact>
                        <Profil onLogout={() => {
                            setToken(null);
                            setDonor({});
                            setExistingDonor(false);
                            setExistingWorker(false);
                        }}
                            accActivated={accActivated}
                            user={user}
                            setUser={setUser}
                            setExistingDonor={setExistingDonor}
                            setExistingWorker={setExistingWorker} />
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
                        <StvoriDonora 
                        user={user} 
                        token={token} 
                        donor={donor} 
                        setDonor={setDonor} 
                        existing={existingDonor} 
                        setExisting={setExistingDonor} />
                    </Route>
                    <Route path='/stvori_djelatnika' exact>
                        <StvoriDjelatnika 
                        user={user} 
                        token={token} 
                        worker={worker} 
                        setWorker={setWorker} 
                        existing={existingWorker} 
                        setExisting={setExistingWorker} />
                    </Route>
                    <Route path='/kreiran_donor' exact>
                        <KreiranDonor user={user} />
                    </Route>
                    <Route path='/kreiran_djelatnik' exact>
                        <KreiranDjelatnik />
                    </Route>
                    <Route path='/pokusaj_doniranja' exact>
                        <PokusajDoniranja 
                        donationPlace={donationPlace} 
                        setDonationPlace={setDonationPlace} 
                        token={token} 
                        user={user} 
                        donor={donor} 
                        existingDonor={existingDonor} 
                        setExistingDonor={setExistingDonor} 
                        setDonor={setDonor}
                        setRejectReason={setRejectReason}
                        setPermRejected={setPermRejected}
                        setSuccessfulDonation={setSuccessfulDonation}/>
                    </Route>
                    <Route path='/trazi_donora' exact>
                        <TraziDonora 
                        token={token} 
                        user={user} 
                        setDonor={setDonor} 
                        setExisting={setExistingDonor} />
                    </Route>
                    <Route path='/donirano' exact>
                        <PostPokusajDoniranja 
                        rejectReason={rejectReason}
                        permRejected={permRejected}
                        successfulDonation={successfulDonation} />
                    </Route>
                </Switch>
            </Router>
        </div>
    );
}

export default App;