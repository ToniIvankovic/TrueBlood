import axios from 'axios';

var baseURL = '';

if (!process.env.NODE_ENV || process.env.NODE_ENV === 'development') {
    // dev code
    baseURL = 'https://trueblood-be-dev.herokuapp.com';
    //baseURL = 'http://localhost:8080';
} else {
    // production code
    baseURL = 'https://trueblood-be.herokuapp.com';
}

const token = localStorage.getItem('token');
if (token) {
    axios.defaults.headers.common['Authorization'] = "Bearer " + token;
} else {
    axios.defaults.headers.common['Authorization'] = null;
}

const instance = axios.create({
    baseURL: baseURL,
    timeout: 1000
});

export default instance;