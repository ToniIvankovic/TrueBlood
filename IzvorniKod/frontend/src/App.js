import React from "react";
import {BrowserRouter as Router, Route, Switch} from "react-router-dom";
import Navbar from "./Navbar/Navbar";
import Home from "./Home";
import Faq from "./Faq";
import Kontakt from "./Kontakt";
import './index.css'

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
        </Switch>
      </Router> 
    </div> 
  );
}

export default App;