import{v as N,x as v,z as d,y as O,G as x,d as h,B as D,A as T,a1 as K,J as G,j as $,C as J,s as p,Q as U,r as q,Y as Q,bS as Y,aI as X,R as Z,K as W,o as u,c as f,a as i,ap as I,w as _,b as B,e as P,t as H,bT as ee,a5 as te,p as oe,g as re,bU as ne,bM as se,bV as S,$ as ae,bI as ie,bJ as w}from"./index.46d84eaa.js";import{N as V}from"./Icon.260316a3.js";import{_ as ce}from"./_plugin-vue_export-helper.cdc0426e.js";const le={fontWeightActive:"400"},de=e=>{const{fontSize:t,textColor3:o,textColor2:r,borderRadius:s,buttonColor2Hover:n,buttonColor2Pressed:c}=e;return Object.assign(Object.assign({},le),{fontSize:t,itemLineHeight:"1.25",itemTextColor:o,itemTextColorHover:r,itemTextColorPressed:r,itemTextColorActive:r,itemBorderRadius:s,itemColorHover:n,itemColorPressed:c,separatorColor:o})},ue={name:"Breadcrumb",common:N,self:de},he=ue,me=v("breadcrumb",`
 white-space: nowrap;
 cursor: default;
 line-height: var(--n-item-line-height);
`,[d("ul",`
 list-style: none;
 padding: 0;
 margin: 0;
 `),d("a",`
 color: inherit;
 text-decoration: inherit;
 `),v("breadcrumb-item",`
 font-size: var(--n-font-size);
 transition: color .3s var(--n-bezier);
 display: inline-flex;
 align-items: center;
 `,[v("icon",`
 font-size: 18px;
 vertical-align: -.2em;
 transition: color .3s var(--n-bezier);
 color: var(--n-item-text-color);
 `),d("&:not(:last-child)",[O("clickable",[x("link",`
 cursor: pointer;
 `,[d("&:hover",`
 background-color: var(--n-item-color-hover);
 `),d("&:active",`
 background-color: var(--n-item-color-pressed); 
 `)])])]),x("link",`
 padding: 4px;
 border-radius: var(--n-item-border-radius);
 transition:
 background-color .3s var(--n-bezier),
 color .3s var(--n-bezier);
 color: var(--n-item-text-color);
 position: relative;
 `,[d("&:hover",`
 color: var(--n-item-text-color-hover);
 `,[v("icon",`
 color: var(--n-item-text-color-hover);
 `)]),d("&:active",`
 color: var(--n-item-text-color-pressed);
 `,[v("icon",`
 color: var(--n-item-text-color-pressed);
 `)])]),x("separator",`
 margin: 0 8px;
 color: var(--n-separator-color);
 transition: color .3s var(--n-bezier);
 user-select: none;
 -webkit-user-select: none;
 `),d("&:last-child",[x("link",`
 font-weight: var(--n-font-weight-active);
 cursor: unset;
 color: var(--n-item-text-color-active);
 `,[v("icon",`
 color: var(--n-item-text-color-active);
 `)]),x("separator",`
 display: none;
 `)])])]),R=U("n-breadcrumb"),pe=Object.assign(Object.assign({},T.props),{separator:{type:String,default:"/"}}),ve=h({name:"Breadcrumb",props:pe,setup(e){const{mergedClsPrefixRef:t,inlineThemeDisabled:o}=D(e),r=T("Breadcrumb","-breadcrumb",me,he,e,t);K(R,{separatorRef:G(e,"separator"),mergedClsPrefixRef:t});const s=$(()=>{const{common:{cubicBezierEaseInOut:c},self:{separatorColor:m,itemTextColor:a,itemTextColorHover:l,itemTextColorPressed:b,itemTextColorActive:k,fontSize:z,fontWeightActive:y,itemBorderRadius:E,itemColorHover:M,itemColorPressed:j,itemLineHeight:A}}=r.value;return{"--n-font-size":z,"--n-bezier":c,"--n-item-text-color":a,"--n-item-text-color-hover":l,"--n-item-text-color-pressed":b,"--n-item-text-color-active":k,"--n-separator-color":m,"--n-item-color-hover":M,"--n-item-color-pressed":j,"--n-item-border-radius":E,"--n-font-weight-active":y,"--n-item-line-height":A}}),n=o?J("breadcrumb",void 0,s,e):void 0;return{mergedClsPrefix:t,cssVars:o?void 0:s,themeClass:n==null?void 0:n.themeClass,onRender:n==null?void 0:n.onRender}},render(){var e;return(e=this.onRender)===null||e===void 0||e.call(this),p("nav",{class:[`${this.mergedClsPrefix}-breadcrumb`,this.themeClass],style:this.cssVars,"aria-label":"Breadcrumb"},p("ul",null,this.$slots))}}),_e=X?window:null,fe=(e=_e)=>{const t=()=>{const{hash:s,host:n,hostname:c,href:m,origin:a,pathname:l,port:b,protocol:k,search:z}=(e==null?void 0:e.location)||{};return{hash:s,host:n,hostname:c,href:m,origin:a,pathname:l,port:b,protocol:k,search:z}},o=()=>{r.value=t()},r=q(t());return Q(()=>{e&&(e.addEventListener("popstate",o),e.addEventListener("hashchange",o))}),Y(()=>{e&&(e.removeEventListener("popstate",o),e.removeEventListener("hashchange",o))}),r},ge={separator:String,href:String,clickable:{type:Boolean,default:!0},onClick:Function},xe=h({name:"BreadcrumbItem",props:ge,setup(e,{slots:t}){const o=Z(R,null);if(!o)return()=>null;const{separatorRef:r,mergedClsPrefixRef:s}=o,n=fe(),c=$(()=>e.href?"a":"span"),m=$(()=>n.value.href===e.href?"location":null);return()=>{const{value:a}=s;return p("li",{class:[`${a}-breadcrumb-item`,e.clickable&&`${a}-breadcrumb-item--clickable`]},p(c.value,{class:`${a}-breadcrumb-item__link`,"aria-current":m.value,href:e.href,onClick:e.onClick},t),p("span",{class:`${a}-breadcrumb-item__separator`,"aria-hidden":"true"},W(t.separator,()=>{var l;return[(l=e.separator)!==null&&l!==void 0?l:r.value]})))}}}),we={xmlns:"http://www.w3.org/2000/svg","xmlns:xlink":"http://www.w3.org/1999/xlink",viewBox:"0 0 384 512"},be=i("path",{d:"M224 136V0H24C10.7 0 0 10.7 0 24v464c0 13.3 10.7 24 24 24h336c13.3 0 24-10.7 24-24V160H248c-13.2 0-24-10.8-24-24zm160-14.1v6.1H256V0h6.1c6.4 0 12.5 2.5 17 7l97.9 98c4.5 4.5 7 10.6 7 16.9z",fill:"currentColor"},null,-1),Ce=[be],ke=h({name:"File",render:function(t,o){return u(),f("svg",we,Ce)}}),ze={xmlns:"http://www.w3.org/2000/svg","xmlns:xlink":"http://www.w3.org/1999/xlink",viewBox:"0 0 384 512"},Be=i("path",{d:"M384 121.941V128H256V0h6.059c6.365 0 12.47 2.529 16.971 7.029l97.941 97.941A24.005 24.005 0 0 1 384 121.941zM248 160c-13.2 0-24-10.8-24-24V0H24C10.745 0 0 10.745 0 24v464c0 13.255 10.745 24 24 24h336c13.255 0 24-10.745 24-24V160H248zM123.206 400.505a5.4 5.4 0 0 1-7.633.246l-64.866-60.812a5.4 5.4 0 0 1 0-7.879l64.866-60.812a5.4 5.4 0 0 1 7.633.246l19.579 20.885a5.4 5.4 0 0 1-.372 7.747L101.65 336l40.763 35.874a5.4 5.4 0 0 1 .372 7.747l-19.579 20.884zm51.295 50.479l-27.453-7.97a5.402 5.402 0 0 1-3.681-6.692l61.44-211.626a5.402 5.402 0 0 1 6.692-3.681l27.452 7.97a5.4 5.4 0 0 1 3.68 6.692l-61.44 211.626a5.397 5.397 0 0 1-6.69 3.681zm160.792-111.045l-64.866 60.812a5.4 5.4 0 0 1-7.633-.246l-19.58-20.885a5.4 5.4 0 0 1 .372-7.747L284.35 336l-40.763-35.874a5.4 5.4 0 0 1-.372-7.747l19.58-20.885a5.4 5.4 0 0 1 7.633-.246l64.866 60.812a5.4 5.4 0 0 1-.001 7.879z",fill:"currentColor"},null,-1),$e=[Be],Le=h({name:"FileCode",render:function(t,o){return u(),f("svg",ze,$e)}}),Ie={xmlns:"http://www.w3.org/2000/svg","xmlns:xlink":"http://www.w3.org/1999/xlink",viewBox:"0 0 384 512"},Pe=i("path",{d:"M384 121.941V128H256V0h6.059a24 24 0 0 1 16.97 7.029l97.941 97.941a24.002 24.002 0 0 1 7.03 16.971zM248 160c-13.2 0-24-10.8-24-24V0H24C10.745 0 0 10.745 0 24v464c0 13.255 10.745 24 24 24h336c13.255 0 24-10.745 24-24V160H248zm-135.455 16c26.51 0 48 21.49 48 48s-21.49 48-48 48s-48-21.49-48-48s21.491-48 48-48zm208 240h-256l.485-48.485L104.545 328c4.686-4.686 11.799-4.201 16.485.485L160.545 368L264.06 264.485c4.686-4.686 12.284-4.686 16.971 0L320.545 304v112z",fill:"currentColor"},null,-1),He=[Pe],Se=h({name:"FileImage",render:function(t,o){return u(),f("svg",Ie,He)}}),Te={xmlns:"http://www.w3.org/2000/svg","xmlns:xlink":"http://www.w3.org/1999/xlink",viewBox:"0 0 512 512"},Ve=i("path",{d:"M464 128H272l-64-64H48C21.49 64 0 85.49 0 112v288c0 26.51 21.49 48 48 48h416c26.51 0 48-21.49 48-48V176c0-26.51-21.49-48-48-48z",fill:"currentColor"},null,-1),Re=[Ve],Fe=h({name:"Folder",render:function(t,o){return u(),f("svg",Te,Re)}}),ye={xmlns:"http://www.w3.org/2000/svg","xmlns:xlink":"http://www.w3.org/1999/xlink",viewBox:"0 0 512 512"},Ee=i("path",{d:"M472.29 195.89l-67.06-22.95c-19.28-6.6-33.54-20.92-38.14-38.3L351.1 74.19c-11.58-43.77-76.57-57.13-109.98-22.62l-46.14 47.67c-13.26 13.71-33.54 20.93-54.2 19.31l-71.88-5.62c-52.05-4.07-86.93 44.88-59.03 82.83l38.54 52.42c11.08 15.07 12.82 33.86 4.64 50.24L24.62 355.4c-20.59 41.25 22.84 84.87 73.49 73.81l69.96-15.28c20.11-4.39 41.45 0 57.07 11.73l54.32 40.83c39.32 29.56 101.04 7.57 104.45-37.22l4.7-61.86c1.35-17.79 12.8-33.86 30.63-42.99l62-31.74c44.88-22.96 39.59-80.17-8.95-96.79z",fill:"currentColor"},null,-1),Me=[Ee],je=h({name:"Splotch",render:function(t,o){return u(),f("svg",ye,Me)}}),F=e=>(oe("data-v-576a4c5d"),e=e(),re(),e),Ae={style:{"background-color":"#91DDFF",padding:"3px 7px"}},Ne=F(()=>i("svg",{xmlns:"http://www.w3.org/2000/svg","xmlns:xlink":"http://www.w3.org/1999/xlink",viewBox:"0 0 24 24"},[i("path",{d:"M14.59 7.41L18.17 11H6v2h12.17l-3.59 3.59L16 18l6-6l-6-6l-1.41 1.41zM2 6v12h2V6H2z",fill:"currentColor"})],-1)),Oe=F(()=>i("svg",{xmlns:"http://www.w3.org/2000/svg","xmlns:xlink":"http://www.w3.org/1999/xlink",viewBox:"0 0 12 12"},[i("g",{fill:"none"},[i("path",{d:"M6.786 1.459a.903.903 0 0 0-1.572 0L1.122 8.628C.774 9.238 1.211 10 1.91 10H10.09c.698 0 1.135-.762.787-1.372l-4.092-7.17z",fill:"currentColor"})])],-1)),De=h({__name:"FilePath",props:{name:{type:String,required:!0},path:{type:Array,default(e){return e}},clickBread:{type:Function,default(e){return console.info("unchange"),e}}},setup(e){const t=e,o=function(r){t.clickBread(r)};return(r,s)=>{const n=V,c=xe,m=ve;return u(),I(m,{style:{height:"30px","line-height":"30px","margin-top":"10px"}},{default:_(()=>[B(c,{onClick:s[0]||(s[0]=a=>o(-1))},{separator:_(()=>[B(n,{class:"triangle-icon"},{default:_(()=>[Oe]),_:1})]),default:_(()=>[i("span",Ae,[B(n,null,{default:_(()=>[Ne]),_:1}),P(" "+H(t.name),1)])]),_:1}),(u(!0),f(te,null,ee(t.path,(a,l)=>(u(),I(c,{key:l,onClick:b=>o(l)},{default:_(()=>[P(H(a),1)]),_:2},1032,["onClick"]))),128))]),_:1})}}});const Xe=ce(De,[["__scopeId","data-v-576a4c5d"]]),C="SHARE_TOKEN",L=ne("share",{state:()=>({token:window.sessionStorage.getItem(C)?window.sessionStorage.getItem(C):null}),actions:{setToken(e,t){this.token=t,window.sessionStorage.setItem(C,t)},clearToken(){this.token=null,window.sessionStorage.removeItem(C)}}}),g=se.create({headers:{"Content-Type":"application/json;charset=utf-8"}});g.defaults.withCredentials=!1;g.interceptors.request.use(function(e){return e.headers||(e.headers={}),e.headers.Authorization="Bearer "+L().token,e});g.interceptors.response.use(function(e){return e.data.code===200?e.data:(S(e),Promise.reject(e))},function(e){return e.response.status==401&&(L().clearToken(),ae.go(0)),S(e.response),Promise.reject(e.response)});const Ke=e=>ie.post(w("/share/create"),e).then(t=>{if(t.code==200)return t.data}),Ge=(e,t,o)=>g.post(w("/share/pass"),{id:e,password:t},o).then(r=>{if(r.code==200&&r.token)L().setToken(e,r.token);else return Promise.reject(r);return r}),Je=(e,t)=>g.post(w("/share/list"),{id:e,path:t}).then(o=>{if(o.code==200)return o.data}),Ue=(e,t)=>g.post(w("/share/pre_download"),{resourceId:e,path:t}).then(o=>{o.code==200&&window.open(`${w("/resource/download",!1)}?entry=${o.data}`)}),Ze={createShare:Ke,pass:Ge,getShareList:Je,download:Ue},We=function(e="FILE"){let t=ke;return e=e.toUpperCase(),e==="DIR"?t=Fe:["JPG","PNG","JPEG"].includes(e)?t=Se:["txt","js","ts","java","md"].includes(e)?t=Le:["exe"].includes(e)&&(t=je),p(V,{size:13,color:e==="DIR"?"#E66E05":"#333",class:"fileIcon"},()=>p(t))};export{Ze as A,Xe as F,Fe as a,We as f,L as s};
