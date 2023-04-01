

export interface User {
    id: number,
    username: string
}

export interface Bucket {
    id: number,
    name: string,
    nameZh: string
}

export interface Resource {
    id: string,
    name: string,
    hash: string,
    path?: string,
    dir: boolean,
    type: string,
    url: string,
    size: number,
    userId: number,
    updateTime: string,
    craeteTime: string
}