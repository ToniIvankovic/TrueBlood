import React, { createContext, useReducer } from 'react';
import AppReducer from './AppReducer';

const initialState = {
   loggedIn: false
}

export const GlobalContext = createContext(initialState);

export const GlobalProvider = ({ children }) => {
   const [state, dispatch] = useReducer(AppReducer, initialState);

   // Actions for changing state

   function setLoggedIn(value) {
       dispatch({
           type: 'SET_LOGGED_IN',
           payload: value
       });
   }

   return(
      <GlobalContext.Provider value = {{loggedIn : state.loggedIn, setLoggedIn}}> 
        {children} 
    </GlobalContext.Provider>
   );
}