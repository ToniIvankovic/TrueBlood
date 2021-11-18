import React, { useEffect, useState } from "react";
import { BrowserRouter as Router, Route, Switch, Redirect } from "react-router-dom";
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

// TODO: global context for role and user data

const App = () => {

  const [loggedIn, setLoggedIn] = useState(false);

  useEffect(() => {
    const token = window.localStorage.getItem('token');
    if(token != null && token != undefined) {
      setLoggedIn(true);
    }
  });

  return(
    <div className='app'>
      <Router>
        <Navbar showProfile={loggedIn}/>
        <Switch>
          <Route path = "/" component={Home} exact>
            <Home/>
          </Route>
          <Route path = "/prijava" component={Login} exact>
            <Login onLogin={() => setLoggedIn(true)}/>
          </Route>
          <Route path = "/profil" component={Profil} exact>
            <Profil onLogout={() => setLoggedIn(false)}/>
          </Route>
          <Route path = "/update" component={Update} exact>
            <Update/>
          </Route>
          <Route path = "/faq" component={Faq} exact>
            <Faq/>
          </Route>
          <Route path = "/kontakt" component={Kontakt} exact>
            <Kontakt/>
          </Route>
          <Route path='/registracija' component={Registracija} exact>
            <Registracija/>
          </Route>
          <Route path ='/autorizacija' component = {Autorizacija} exact>
            <Autorizacija/>
          </Route>
        </Switch>
      </Router>
    </div> 
  );
}

export default App;