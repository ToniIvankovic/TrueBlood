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
import Trazilica from "./Trazilica";
import SlanjeKrvi from "./SlanjeKrvi";

import { getCurrentUserIdAndRole, getAccActivated, isEqualWithNull, userNone, userPublic, donorNone, workerNone, getDonorPermRejected, getDonorById, getWorkerById } from "./Util";
import _ from 'lodash';
import KreiranDjelatnik from "./KreiranDjelatnik";
import PostPokusajDoniranja from "./PostPokusajDoniranja";
import OptimalneGranice from "./OptimalneGranice";
import DeaktivirajRacun from "./DeaktivirajRacun";
import RacunDeaktiviran from "./RacunDeaktiviran";
import axios from "axios";
import RacunAktiviran from "./RacunAktiviran";

// TODO: global context for role and user data - done?

const App = () => {

    //Provjeriti tko je user i postaviti njegov role
    const [user, setUser] = useState(userNone);
    useEffect(() => {
        getCurrentUserIdAndRole(user, setUser);
    }, []);
    
    useEffect(() => {
        console.log(user)
        if(user.role == "DONOR"){
            getDonorById(user.userId,setUser);
        } else if(user.role == "BANK_WORKER"){
            getWorkerById(user.userId, setUser);
        }
    }, [user.role]);

    // const [donorPermRejected, setDonorPermRejected] = useState(null);
    // useEffect(() => {
    //     if(!isEqualWithNull(user,userNone)){
    //         getDonorPermRejected(user.userId, setDonorPermRejected);
    //     }
    // }, [user]);

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
                <Navbar showProfile={user != userPublic} onLogout={() => {
                            //setToken(null);
                            setUser(userPublic);
                            setDonor({});
                            setExistingDonor(false);
                            setExistingWorker(false);
                        }}/>
                <Switch>
                    <Route path="/" exact>
                        <Home loggedIn={user != userPublic} />
                    </Route>
                    <Route path="/prijava" exact>
                        <Login onLogin={() => {
                            getCurrentUserIdAndRole(user, setUser);
                        }}
                        setExistingDonor={setExistingDonor} />
                    </Route>
                    <Route path="/profil" exact>
                        <Profil onLogout={() => {
                            setDonor({});
                            setExistingDonor(false);
                            setExistingWorker(false);
                        }}
                            // donorPermRejected={donorPermRejected}
                            user={user}
                            setUser={setUser}
                            setExistingDonor={setExistingDonor}
                            setExistingWorker={setExistingWorker}
                            worker={worker}
                            donor={donor} />
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
                        donor={donor} 
                        setDonor={setDonor} 
                        existing={existingDonor} 
                        setExisting={setExistingDonor} />
                    </Route>
                    <Route path='/stvori_djelatnika' exact>
                        <StvoriDjelatnika 
                        user={user}
                        setUser={setUser}
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
                        <Trazilica
                        user={user} 
                        setFoundUser={setDonor} 
                        setExisting={setExistingDonor}
                        userClass={'donor'} />
                    </Route>
                    <Route path='/trazi_djelatnika' exact>
                        <Trazilica
                        user={user} 
                        setFoundUser={setWorker} 
                        setExisting={setExistingWorker}
                        userClass={'bank-worker'} />
                    </Route>
                    <Route path='/donirano' exact>
                        <PostPokusajDoniranja 
                        rejectReason={rejectReason}
                        permRejected={permRejected}
                        successfulDonation={successfulDonation} />
                    </Route>
                    <Route path='/slanje_krvi' exact>
                        <SlanjeKrvi 
                        user={user} />
                    </Route>
                    <Route path='/optimalne_granice' exact>
                        <OptimalneGranice
                        user={user} />
                    </Route>
                    <Route path='/povijest_doniranja' exact>
                        <Trazilica 
                        user={user} 
                        setFoundUser={()=>{}} 
                        setExisting={()=>{}}
                        userClass={'history'} />
                    </Route>
                    <Route path='/povijest_doniranja_donora' exact>
                        <Trazilica 
                        user={{userId: donor.donorId, role:'DONOR'}} 
                        setFoundUser={()=>{}} 
                        setExisting={()=>{}}
                        userClass={'history'} />
                    </Route>
                    <Route path='/deaktiviraj_racun' exact>
                        <DeaktivirajRacun 
                        user={user} 
                        donor={donor} 
                        setDonor={setDonor}
                        existingDonor={existingDonor} 
                        setExistingDonor={setExistingDonor} 
                        worker={worker} 
                        setWorker={setWorker}
                        existingWorker={existingWorker} 
                        setExistingWorker={setExistingWorker} />
                    </Route>
                    <Route path='/racun_deaktiviran' exact>
                        <RacunDeaktiviran 
                        setDonor={setDonor}
                        setExistingDonor={setExistingDonor} 
                        setWorker={setWorker}
                        setExistingWorker={setExistingWorker}
                        />
                    </Route>
                    <Route path='/aktiviran_racun' exact>
                        <RacunAktiviran />
                    </Route>
                </Switch>
            </Router>
        </div>
    );
}

export default App;