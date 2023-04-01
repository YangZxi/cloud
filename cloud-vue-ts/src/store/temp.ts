import { defineStore } from 'pinia';
import { Bucket, Resource } from "@/type/type";
import http from "@/http/Other"

type stateType = {
    bucket: Bucket | null,
    resource: Resource | null,
    filetype: String | null
} 

//创建VueX对象
export const otherPinia = defineStore("temp", {
    state: () => {
        const state: stateType = {
            bucket: null,
            resource: window.sessionStorage.getItem("resource") ? JSON.parse(window.sessionStorage.getItem("resource") as string) : null,
            filetype: null
        }
        return state;
    },
    actions: {
        setBucket(bucket: Bucket) {
            this.bucket = bucket;
        },
        setResource(resource: Resource) {
            this.resource = resource;
            window.sessionStorage.setItem("resource", JSON.stringify(resource));
        },
        async getFiletype() {
            if (!this.filetype) {
                await http.filetype().then(res => {
                    console.log(res);
                    this.filetype = res;
                });
            }
            return this.filetype;
        },
    }
})

export default otherPinia