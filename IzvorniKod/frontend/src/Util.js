import React from "react";
import axios from './util/axios-instance';
//import { useHistory } from "react-router";

//globalne varijable za aplikaciju
const roleNone = 'JAVNO';
const donorNone = {};
const userNone = {};
const userPublic = {
    userId: null,
    role: roleNone
};

const getCurrentUserIdAndRole = async (user, setUser) => {

    const url = '/api/v1/user';
    const token = window.localStorage.getItem('token');
    if (!token) {
        setUser(userPublic);
        return;
    }

    const bearerAuth = 'Bearer ' + token;
    await axios.get(url, {
        headers: { 'Authorization': bearerAuth }
    })
        .then((response) => {
            if (response.data != null) {
                setUser({
                    userId: response.data.id,
                    role: response.data.role
                });
            } else {
                //Sto ovdje napraviti?
                setUser(userPublic);
                window.localStorage.removeItem('token');
                console.log("Prazan odgovor poslužitelja")
            }
        })
        .catch((error) => {
            console.log('Error retrieving user info: ' + error);
            //let history = useHistory();
            //history.push('/');
        })
}

const getAccActivated = async (userId, setActivated) => {

    const url = '/api/v1/user/activated'; //potencijalna promjena
    const token = window.localStorage.getItem('token');
    if (!token) {
        setActivated(false);
        return;
    }

    const bearerAuth = 'Bearer ' + token;
    if(!userId){
        console.log("nema usera kod trazenja aktivacije");
        return;
    } else{
        // console.log("Ima usera kod trazenja aktivacije")
    }

    setActivated(false);
    //ODKOMENTIRATI KADA SE NAPRAVI ENDPOINT
    /*
    await axios.get(url, {
        headers: { 'Authorization': bearerAuth },
        userId: user.userId,
        })
        .then((response) => {
            if (response.data != null) {
                setActivated(response.data.activated);
            } else {
                console.log("Server nije pronašao usera s id " + user.userId);
                setActivated(false);
            }
        })
        .catch((error) => {
            console.log('Error retrieving user info: ' + error);
        })*/
}


const isJSONEqual = (v1,v2) =>{
    if(JSON.stringify(v1) == JSON.stringify(v2))
        return true;
    else if(_.isEqual(v1,v2))
        return true;
    else return false;
}
const isEqualWithNull = (v1, v2) =>{
    if(v1 == v2
        || isJSONEqual(v1,v2)
        || !v1 && isJSONEqual(v2,{})
        || !v2 && isJSONEqual(v1,{})){
        return true;
    }
    else {
        return false;
    }
}


export { getCurrentUserIdAndRole };
export { getAccActivated };
export { isEqualWithNull };
export { roleNone };
export { userNone };
export { userPublic };
export { donorNone };