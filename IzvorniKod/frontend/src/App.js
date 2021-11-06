import React from "react";
import {BrowserRouter as Router, Route, Switch} from "react-router-dom";
import Navbar from "./Navbar/Navbar";
import Home from "./Home";
import Faq from "./Faq";
import Kontakt from "./Kontakt";
import './index.css'
import Registracija from "./Registracija";
import Autorizacija from "./Autorizacija";

const App = () => {
  return(
    <div className='app'>
      <Router>
        <Navbar/>
        <Switch>
          <Route path = "/" component={Home} exact>
            <Home/>
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