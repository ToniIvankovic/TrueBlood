import React from 'react';
 
export default (state, action) => {
   switch(action.type) {
       case 'SET_LOGGED_IN':
           return {
                loggedIn: action.payload
           }
       default:
           return state;
   }
}