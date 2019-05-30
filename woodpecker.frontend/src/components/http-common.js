import axios from 'axios'


const apiserveraddress = 'http://192.168.1.14:8088'
const AXIOS = axios.create({
  baseURL: apiserveraddress +  `/api`,
  headers: {
    'Access-Control-Allow-Origin': '*',
    'Content-Type': 'application/json',
  }
});


export {
  apiserveraddress,
  AXIOS
}