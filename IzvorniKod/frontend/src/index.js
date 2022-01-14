import React from "react";
import ReactDOM from "react-dom";
import App from "./App";
import './index.css'
import axios from 'axios';

var baseURL = "";

if (!process.env.NODE_ENV || process.env.NODE_ENV === "development") {
    // dev code
    // baseURL = 'https://trueblood-be-dev.herokuapp.com';
    baseURL = "http://localhost:8080";
} else {
    // production code
    baseURL = "https://trueblood-be-dev.herokuapp.com"; //PROMIJENITI PRIJE PREDAJE
}

axios.defaults.baseURL = baseURL;
axios.defaults.timeout = 5000;
axios.defaults.withCredentials = true;
//axios.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';


ReactDOM.render(
    <React.StrictMode>
        <App />
    </React.StrictMode>,
    document.getElementById("root")
);