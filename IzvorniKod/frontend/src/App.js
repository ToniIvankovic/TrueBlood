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
import PokusajDoniranja from "./PokusajDoniranja";
import Trazilica from "./Trazilica";
import SlanjeKrvi from "./SlanjeKrvi";

import { getCurrentUserIdAndRole, 
    userNone, 
    userPublic,
    donorNone, 
    workerNone, 
    getDonorById, 
    getWorkerById, 
    isRoleAnyOfThese} from "./Util";
import _ from 'lodash';
import KreiranDjelatnik from "./KreiranDjelatnik";
import PostPokusajDoniranja from "./PostPokusajDoniranja";
import OptimalneGranice from "./OptimalneGranice";
import DeaktivirajRacun from "./DeaktivirajRacun";
import RacunDeaktiviran from "./RacunDeaktiviran";
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
                    <Route path="/faq" exact>
                        <Faq />
                    </Route>
                    <Route path="/kontakt" exact>
                        <Kontakt />
                    </Route>
                    <Route path='/aktiviran_racun' exact>
                        <RacunAktiviran />
                    </Route>

                    {isRoleAnyOfThese(user.role, [userPublic.role]) ?
                    <Route key={1} path="/prijava" exact>
                        <Login onLogin={() => {
                            getCurrentUserIdAndRole(user, setUser);
                        }}
                        setExistingDonor={setExistingDonor} />
                    </Route>
                    :''}
                    {user.role && user.role != "ADMIN" ?
                    <Route path='/stvori_donora' exact>
                        <StvoriDonora 
                        user={user}
                        donor={donor} 
                        setDonor={setDonor} 
                        existing={existingDonor} 
                        setExisting={setExistingDonor} />
                    </Route>
                    :''}

                    {user.role && userPublic.role != user.role ?
                    <Route path="/profil" exact>
                        <Profil 
                        onLogout={() => {
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
                    :''}

                    {isRoleAnyOfThese(user.role, ["BANK_WORKER"]) ?
                    [
                    <Route key={1} path='/pokusaj_doniranja' exact>
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
                    </Route>,
                    <Route key={2} path='/donirano' exact>
                        <PostPokusajDoniranja 
                        rejectReason={rejectReason}
                        permRejected={permRejected}
                        successfulDonation={successfulDonation} />
                    </Route>,
                    <Route key={3} path='/slanje_krvi' exact>
                        <SlanjeKrvi/>
                    </Route>,
                    <Route key={4} path='/povijest_doniranja_donora' exact>
                        <Trazilica 
                        user={{userId: donor.donorId, role:'DONOR'}} 
                        setFoundUser={()=>{}} 
                        setExisting={()=>{}}
                        userClass={'history'} />
                    </Route>
                    ]
                    :''}
                    {isRoleAnyOfThese(user.role, ["ADMIN"]) ?
                    [
                        <Route key={0} path='/kreiran_djelatnik' exact>
                            <KreiranDjelatnik />
                        </Route>,
                        <Route key={1} path='/trazi_djelatnika' exact>
                            <Trazilica
                            user={user} 
                            setFoundUser={setWorker} 
                            setExisting={setExistingWorker}
                            userClass={'bank-worker'} />
                        </Route>,
                        <Route key={2} path='/optimalne_granice' exact>
                            <OptimalneGranice />
                        </Route>,
                        <Route key={3} path='/deaktiviraj_racun' exact>
                            <DeaktivirajRacun 
                            donor={donor} 
                            setDonor={setDonor}
                            existingDonor={existingDonor} 
                            setExistingDonor={setExistingDonor} 
                            worker={worker} 
                            setWorker={setWorker}
                            existingWorker={existingWorker} 
                            setExistingWorker={setExistingWorker} />
                        </Route>,
                        <Route key={4} path='/racun_deaktiviran' exact>
                            <RacunDeaktiviran 
                            setDonor={setDonor}
                            setExistingDonor={setExistingDonor} 
                            setWorker={setWorker}
                            setExistingWorker={setExistingWorker}
                            />
                        </Route>

                    ]
                    :''}
                    {isRoleAnyOfThese(user.role, ["DONOR"])?
                    <Route path='/povijest_doniranja' exact>
                        <Trazilica 
                        user={user} 
                        setFoundUser={()=>{}} 
                        setExisting={()=>{}}
                        userClass={'history'} />
                    </Route>
                    :''}
                    
                    {isRoleAnyOfThese(user.role, ["ADMIN", "BANK_WORKER"]) ?
                    [
                    <Route key={1} path='/stvori_djelatnika' exact>
                        <StvoriDjelatnika 
                        user={user}
                        setUser={setUser}
                        existing={existingWorker} 
                        setExisting={setExistingWorker} />
                    </Route>,
                    <Route key={2} path='/trazi_donora' exact>
                        <Trazilica
                        user={user} 
                        setFoundUser={setDonor} 
                        setExisting={setExistingDonor}
                        userClass={'donor'} />
                    </Route>
                    ]
                    :''}
                    {isRoleAnyOfThese(user.role, ["DONOR", "BANK_WORKER"]) ?
                    <Route path='/kreiran_donor' exact>
                        <KreiranDonor user={user} />
                    </Route>
                    :''}
                </Switch>
            </Router>
        </div>
    );
}

export default App;