import axios from 'axios';

var baseURL = '';

if (!process.env.NODE_ENV || process.env.NODE_ENV === 'development') {
    // dev code
    baseURL = 'https://trueblood-be-dev.herokuapp.com';
} else {
    // production code
    baseURL = 'https://trueblood-be.herokuapp.com';
}

const instance = axios.create({
    baseURL: baseURL,
    timeout: 1000
});

export default instance;