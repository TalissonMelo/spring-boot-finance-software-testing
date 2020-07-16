import axios from 'axios';

const httpClient = axios.create({
    baseURL: 'http://localhost:8080'
})

class ApiService {

    constructor(apiUrl) {
        this.apiUrl = apiUrl;
    }

    post(url, obj) {
        const requestUrl = `${this.apiUrl}${url}`
        return httpClient.post(requestUrl, obj);
    }

    put(url, obj) {
        const requestUrl = `${this.apiUrl}${url}`
        return httpClient.put(requestUrl, obj);
    }

    delete(url) {
        const requestUrl = `${this.apiUrl}${url}`
        return httpClient.delete(requestUrl);
    }

    get(url) {
        const requestUrl = `${this.apiUrl}${url}`
        return httpClient.get(requestUrl);
    }
}

export default ApiService