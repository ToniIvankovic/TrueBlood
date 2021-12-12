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
import { getCurrentUserIdAndRole } from "./Util";

// TODO: global context for role and user data

const App = () => {

    const roleNone = 'JAVNO';
    const userNone = {
        userId: null,
        role: roleNone
    };
    const [user, setUser] = useState(userNone);

    const [token, setToken] = useState("");

    const [loggedIn, setLoggedIn] = useState(false);

    useEffect(() => {
        setToken(window.localStorage.getItem('token'));
        if (token) {
            setLoggedIn(true);
            getCurrentUserIdAndRole(user, setUser);
        }
    }, [token]);


    const [userRole, setUserRole] = useState('JAVNO');
    useEffect(() => {
        setUserRole(user.role ? user.role : roleNone);
    }, [user]);


    const donorNone = {};
    const [donorToSeeChange, setDonorToSeeChange] = useState(donorNone);

    const [donor, setDonor] = useState({})

    useEffect(() => {
        let donorFromStorage = JSON.parse(window.localStorage.getItem('donor'));
        setDonor(donorFromStorage ? donorFromStorage : {});
        console.log("App vidi donora u storageu ovako:")
        console.log(donorFromStorage ? donorFromStorage.donorId ? donorFromStorage : "donor objekt prazan" : "donor doesnt exist in storage");
    }, [donorToSeeChange.donorId]);

    return (
        <div className='app'>
            <Router>
                <Navbar showProfile={loggedIn} />
                <Switch>
                    <Route path="/" component={Home} exact>
                        <Home loggedIn={loggedIn} />
                    </Route>
                    <Route path="/prijava" component={Login} exact>
                        <Login onLogin={() => {
                            setLoggedIn(true);
                            getCurrentUserIdAndRole(user, setUser);
                            setUserRole(user.role)
                        }} />
                    </Route>
                    <Route path="/profil" component={Profil} exact>
                        <Profil onLogout={() => {
                            setLoggedIn(false);
                            setUser(userNone);
                            setUserRole(roleNone);
                            setDonorToSeeChange({});
                        }}
                            user={user} />
                    </Route>
                    <Route path="/update" component={Update} exact>
                        <Update />
                    </Route>
                    <Route path="/faq" component={Faq} exact>
                        <Faq />
                    </Route>
                    <Route path="/kontakt" component={Kontakt} exact>
                        <Kontakt />
                    </Route>
                    <Route path='/stvori_donora' component={Registracija} exact>
                        <Registracija role={userRole} token={token} setDonor={setDonorToSeeChange} />
                    </Route>
                    <Route path='/kreiran_donor' component={KreiranDonor} exact>
                        <KreiranDonor user={user} />
                    </Route>
                    <Route path='/pokusaj_doniranja' component={PokusajDoniranja} exact>
                        <PokusajDoniranja user={user} donor={donor} />
                    </Route>
                    <Route path='/trazi_donora' component={TraziDonora} exact>
                        <TraziDonora user={user} setDonor={setDonorToSeeChange} />
                    </Route>
                </Switch>
            </Router>
        </div>
    );
}

export default App;