import { axios } from "./XMLHttpRequest"
import API, { SERVER_BASE } from "./API"

const instance = axios.create({
  baseURL: SERVER_BASE,
});

/**
 * 获取文件类型配置
 * @returns Promise
 */
export const filetype = () => {
  console.log(1);
  
  return instance.get(`/props/filetype.json`).then(res => {
    return res.data;
  });
}

export default {
  filetype,
}