import axios from 'axios';

var baseURL = '';

if (!process.env.NODE_ENV || process.env.NODE_ENV === 'development') {
    // dev code
    baseURL = 'http://localhost:8080';
} else {
    // production code
    baseURL = 'https://trueblood-be-dev.herokuapp.com'; //PROMIJENITI PRIJE PREDAJE
}

const instance = axios.create({
    baseURL: baseURL,
    timeout: 1000
});

export default instance;