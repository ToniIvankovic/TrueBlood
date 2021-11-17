import React, { createContext, useContext, useEffect, useState } from "react";
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
import axios from './util/axios-instance';
import { GlobalContext } from "./context/GlobalState";

const App = () => {

  const [loggedIn, setLoggedIn] = useState(false);

  useEffect(() => {
    setLoggedIn(JSON.parse(window.localStorage.getItem('loggedIn')));
  }, []);

  useEffect(() => {
    window.localStorage.setItem('loggedIn', loggedIn);
  }, [loggedIn]);

  const updateLoggedInEvent = (actionType, payload) => {
    switch(actionType) {
        case 'SET_LOGGED_IN':
            setLoggedIn(payload);
            return;
        default:
            return;
    }
 }

 const setLoginState = (value) => {
   updateLoggedInEvent('SET_LOGGED_IN', value);
 }

  // useEffect(() => {
  //   const getLoggedIn = async () => {
  //     const url = '/api/v1/login/logged_in';
  //     await axios.get(url)
  //     .then((response) => {
  //         if(response.data.loggedIn === true) {
  //             console.log('SUCCESS RESPONSE FROM /logged_in');
  //             setLoginState(true);
  //         } else {
  //           console.log('FAIL RESPONSE FROM /logged_in');
  //           setLoginState(false);
  //         }
  //     })
  //     .catch((error) => {
  //         // not logged in, redirect to home
  //         console.log('ERROR FROM /logged_in');
  //         console.log(error);
  //         setLoginState(false);
  //     });
  //   }

  //   getLoggedIn();
  // }, []);

  return(
    <div className='app'>
      <GlobalContext.Provider value={{ loggedIn, updateLoggedInEvent }}>
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
            <Profil />
          {/* {
            loggedIn ? (<Profil />) : (<Redirect to='/' />)
          } */}
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
      </GlobalContext.Provider>
    </div> 
  );
}

export default App;