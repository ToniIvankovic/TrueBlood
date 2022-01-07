import axios from 'axios';

var baseURL = '';

if (!process.env.NODE_ENV || process.env.NODE_ENV === 'development') {
    // dev code
    // baseURL = 'https://trueblood-be-dev.herokuapp.com';
    baseURL = 'http://localhost:8080';
} else {
    // production code
    baseURL = 'https://trueblood-be-dev.herokuapp.com'; //PROMIJENITI PRIJE PREDAJE
}

const instance = axios.create({
    baseURL: baseURL,
    timeout: 5000,
    withCredentials: true
});

export default instance;