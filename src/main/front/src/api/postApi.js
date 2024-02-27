import axios from 'axios';

export const  API_SERVER_HOST = 'http://127.0.0.1:8080'

const prefix = `${API_SERVER_HOST}/post/detail`


export const getOne = async (id) => {

  const response = await axios.get(`${prefix}/${id}`)
  
  return response.data; 
 
}
// ?paga =  , ?size =
export const getList = async (pageParam) => {

  const {page,size}  = pageParam;

  //쿼리스트링
  const response = await axios.get(`${prefix}/list`,{params:{...pageParam}})

  return response.data;
}