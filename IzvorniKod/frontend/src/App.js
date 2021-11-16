import React from "react";
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

const App = () => {
  return(
    <div className='app'>
      <Router>
        <Navbar/>
        <Switch>
          <Route path = "/" component={Home} exact>
            <Home/>
          </Route>
          <Route path = "/prijava" component={Login} exact>
            <Login/>
          </Route>
          <Route path = "/profil" component={Profil} exact>
            <Profil/>
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