import React, { useEffect, useState } from "react";
import { BrowserRouter as Router, Route, Switch } from "react-router-dom";
import Navbar from "./Navbar/Navbar";
import Login from "./Login";
import Faq from "./Faq";
import Kontakt from "./Kontakt";
import './index.css'
import Registracija from "./Registracija";
import Autorizacija from "./Autorizacija";
import Home from "./Home";
import Profil from "./Profil";
import Update from "./Update";
import { getCurrentUserIdAndRole } from "./Util";

// TODO: global context for role and user data

const App = () => {

    const roleNone = 'JAVNO';
    const userNone = {};
    const [user, setUser] = useState(userNone);

    const [loggedIn, setLoggedIn] = useState(false);

    useEffect(() => {
        const token = window.localStorage.getItem('token');
        if (token) {
            setLoggedIn(true);
            getCurrentUserIdAndRole(user, setUser);
        }
    }, []);

    const [userRole, setUserRole] = useState('javno');

    useEffect(() => {
        setUserRole(user.role ? user.role : roleNone);
    }, [user]);
    // console.log("role in app: " + userRole);
    // console.log("PROMJENA")

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
                        <Registracija role={userRole} />
                    </Route>
                    <Route path='/autorizacija' component={Autorizacija} exact>
                        <Autorizacija />
                    </Route>
                </Switch>
            </Router>
        </div>
    );
}

export default App;