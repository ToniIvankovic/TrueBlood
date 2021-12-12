import React from "react";
import axios from './util/axios-instance';
//import { useHistory } from "react-router";

const getCurrentUserIdAndRole = async (user, setUser) => {

    const roleNone = 'JAVNO';
    const userNone = {
        userId: null,
        role: roleNone
    };

    const url = '/api/v1/user';
    const token = window.localStorage.getItem('token');
    if (!token) {
        setUser(userNone);
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
                setUser(userNone);
            }
        })
        .catch((error) => {
            console.log('Error retrieving user info: ' + error);
            //let history = useHistory();
            //history.push('/');
        })
}

export { getCurrentUserIdAndRole };