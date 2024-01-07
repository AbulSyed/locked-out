import axios from 'axios'

export default axios.create({
  baseURL: import.meta.env.VITE_IDENTITY_SERVICE_HOST
})
