import http from "./XMLHttpRequest"
import API, {SERVER_DOWNLOAD, SERVER_PREVIEW} from "./API"
import {download as DL} from "@/utils/Tools"
import $router from "@/router/index";
import {Resource} from "@/type/type";

const isMobile = window.sessionStorage.getItem("isMobile");

/**
 * 获取指定目录下的所有文件
 * @param data 
 * @returns Promise
 */
export const listResource = async (data: any) => {
  await $router.push({
    path: `/explorer/${data.bucketName}`,
    query: {
      path: data.path
    }
  })
  history.replaceState({ ...history.state }, "")
  return http.get(API("/resource"), data).then(res => {
    if (res.code == 200) {
      return res.data;
    }
  });
}

/**
 * 文件创建
 * @param data 
 * @returns 
 */
export const createFile = (data: any) => {
  return http.post(API("/resource/create"), data).then(res => {
    if (res.code == 200) {
      window.$message.success(res.msg);
      return res.data;
    }
  });
}

/**
 * 文件删除
 * @param data 
 * @returns 
 */
export const deleteFile = (id: string) => {
  return http.post(API("/resource/delete"), {
    id: id
  }).then(res => {
    if (res.code == 200) {
      return res.data;
    }
  });
}

/**
 * 文件重命名
 * @param data 
 * @returns 
 */
export const renameFile = (id: string, name: string) => {
  return http.post(API("/resource/rename"), {
    id,
    name
  }).then(res => {
    if (res.code == 200) {
      return res.data;
    }
  });
}

/**
 * 
 * @param originId 被复制的资源 id
 * @param targetId 被选择存放的目录 id
 * @returns 
 */
export const moveOrCopyFile = (originId: string, targetId: string, type: string = "copy") => {
  return http.post(API(`/resource/${type}`), {
    id: originId,
    targetId
  }).then(res => {
    if (res.code == 200) {
      window.$message.success(res.msg);
      return res.data;
    }
  });
}

/**
 * 保存文件内容
 * @param id 
 * @param content 
 * @returns 
 */
export const saveContent = (id: string, content: string) => {
  return http.post(API("/resource/saveContent"), {
    id,
    content
  }).then(res => {
    if (res.code == 200) {
      return res.data;
    }
  });
}

function getLink(id: string) {
  return http.post(API(`/resource/link/${id}`)).then(res => res.data);
}

export const download = (id: string) => {
  return getLink(id).then((url) => {
    if (!url.startsWith("http")) {
      url = `${SERVER_DOWNLOAD}${url}`;
    }
    DL(url);
  });
}

export const preview = (id: string) => {
  return getLink(id).then(url => {
    if (url.startsWith("http")) {
      return url;
    } else if (url.startsWith("//")) {
      url = location.protocol + url;
      console.log(url);
      return url;
    } else {
      url = `${SERVER_PREVIEW}${url}`;
      console.log(url);
      return url;
    }  
  });
}

export function uploadCheck(data: any) {
  return http.get(API("/resource/upload"), data).then((res) => {
    return res;
  });
}

export function mergeChunk(data: any) {
  return http.post(API("/resource/upload/merge"), data).then((res) => {
    return res;
  });
}

/**
 * 文件搜索
 * @param data 
 */
export function search(data: any): Promise<Resource[]> {
  return http.post(API("/resource/search"), data).then(res => {
    return res.data;
  });
}

/**
 * 开启或关闭 cdn
 */
export function toggleCdn(id: string): Promise<Resource[]> {
  return http.post(API(`/resource/toggle_cdn/${id}`)).then(res => {
    return res.data;
  });
}


export default {
  listResource,
  createFile,
  deleteFile,
  renameFile,
  moveOrCopyFile,
  saveContent,
  download,
  preview,
  toggleCdn
}