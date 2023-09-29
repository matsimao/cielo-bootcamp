import axios from "axios"

const setConfigApi = _ => {
    let url = `${import.meta.env.VITE_API_URL}/api`;
    
    axios.defaults.baseURL = url;
}

const useFetch = async ({path, method='get', data={}}) => {
    setConfigApi();
    
    return new Promise((resolve, reject) => {
        axios[method](path, data)
        .then(resolve)
        .catch(reject)
    });
}

export {useFetch}