import{k as I,p as m,q as l,x as V,L as h,d as k,s as $,t as L,Q as j,P as E,v as C,w as A,i as v,z as N,F as D,e as M,bV as O,C as q,A as K,D as Q,g as w,W as u,o as x,f as _,b as p,a as B,bC as y,c as U,c2 as G,R as J,bx as X,by as Y}from"./index.6d4055d0.js";import{N as Z}from"./Icon.531e1a0b.js";import{_ as W}from"./_plugin-vue_export-helper.cdc0426e.js";const ee={fontWeightActive:"400"},te=e=>{const{fontSize:r,textColor3:t,textColor2:n,borderRadius:a,buttonColor2Hover:o,buttonColor2Pressed:s}=e;return Object.assign(Object.assign({},ee),{fontSize:r,itemLineHeight:"1.25",itemTextColor:t,itemTextColorHover:n,itemTextColorPressed:n,itemTextColorActive:n,itemBorderRadius:a,itemColorHover:o,itemColorPressed:s,separatorColor:t})},re={name:"Breadcrumb",common:I,self:te},oe=re,ne=m("breadcrumb",`
 white-space: nowrap;
 cursor: default;
 line-height: var(--n-item-line-height);
`,[l("ul",`
 list-style: none;
 padding: 0;
 margin: 0;
 `),l("a",`
 color: inherit;
 text-decoration: inherit;
 `),m("breadcrumb-item",`
 font-size: var(--n-font-size);
 transition: color .3s var(--n-bezier);
 display: inline-flex;
 align-items: center;
 `,[m("icon",`
 font-size: 18px;
 vertical-align: -.2em;
 transition: color .3s var(--n-bezier);
 color: var(--n-item-text-color);
 `),l("&:not(:last-child)",[V("clickable",[h("link",`
 cursor: pointer;
 `,[l("&:hover",`
 background-color: var(--n-item-color-hover);
 `),l("&:active",`
 background-color: var(--n-item-color-pressed); 
 `)])])]),h("link",`
 padding: 4px;
 border-radius: var(--n-item-border-radius);
 transition:
 background-color .3s var(--n-bezier),
 color .3s var(--n-bezier);
 color: var(--n-item-text-color);
 position: relative;
 `,[l("&:hover",`
 color: var(--n-item-text-color-hover);
 `,[m("icon",`
 color: var(--n-item-text-color-hover);
 `)]),l("&:active",`
 color: var(--n-item-text-color-pressed);
 `,[m("icon",`
 color: var(--n-item-text-color-pressed);
 `)])]),h("separator",`
 margin: 0 8px;
 color: var(--n-separator-color);
 transition: color .3s var(--n-bezier);
 user-select: none;
 -webkit-user-select: none;
 `),l("&:last-child",[h("link",`
 font-weight: var(--n-font-weight-active);
 cursor: unset;
 color: var(--n-item-text-color-active);
 `,[m("icon",`
 color: var(--n-item-text-color-active);
 `)]),h("separator",`
 display: none;
 `)])])]),z=N("n-breadcrumb"),ae=Object.assign(Object.assign({},L.props),{separator:{type:String,default:"/"}}),ie=k({name:"Breadcrumb",props:ae,setup(e){const{mergedClsPrefixRef:r,inlineThemeDisabled:t}=$(e),n=L("Breadcrumb","-breadcrumb",ne,oe,e,r);j(z,{separatorRef:E(e,"separator"),mergedClsPrefixRef:r});const a=C(()=>{const{common:{cubicBezierEaseInOut:s},self:{separatorColor:d,itemTextColor:i,itemTextColorHover:c,itemTextColorPressed:b,itemTextColorActive:f,fontSize:g,fontWeightActive:P,itemBorderRadius:T,itemColorHover:S,itemColorPressed:H,itemLineHeight:F}}=n.value;return{"--n-font-size":g,"--n-bezier":s,"--n-item-text-color":i,"--n-item-text-color-hover":c,"--n-item-text-color-pressed":b,"--n-item-text-color-active":f,"--n-separator-color":d,"--n-item-color-hover":S,"--n-item-color-pressed":H,"--n-item-border-radius":T,"--n-font-weight-active":P,"--n-item-line-height":F}}),o=t?A("breadcrumb",void 0,a,e):void 0;return{mergedClsPrefix:r,cssVars:t?void 0:a,themeClass:o==null?void 0:o.themeClass,onRender:o==null?void 0:o.onRender}},render(){var e;return(e=this.onRender)===null||e===void 0||e.call(this),v("nav",{class:[`${this.mergedClsPrefix}-breadcrumb`,this.themeClass],style:this.cssVars,"aria-label":"Breadcrumb"},v("ul",null,this.$slots))}}),se=q?window:null,ce=(e=se)=>{const r=()=>{const{hash:a,host:o,hostname:s,href:d,origin:i,pathname:c,port:b,protocol:f,search:g}=(e==null?void 0:e.location)||{};return{hash:a,host:o,hostname:s,href:d,origin:i,pathname:c,port:b,protocol:f,search:g}},t=()=>{n.value=r()},n=D(r());return M(()=>{e&&(e.addEventListener("popstate",t),e.addEventListener("hashchange",t))}),O(()=>{e&&(e.removeEventListener("popstate",t),e.removeEventListener("hashchange",t))}),n},le={separator:String,href:String,clickable:{type:Boolean,default:!0},onClick:Function},de=k({name:"BreadcrumbItem",props:le,setup(e,{slots:r}){const t=K(z,null);if(!t)return()=>null;const{separatorRef:n,mergedClsPrefixRef:a}=t,o=ce(),s=C(()=>e.href?"a":"span"),d=C(()=>o.value.href===e.href?"location":null);return()=>{const{value:i}=a;return v("li",{class:[`${i}-breadcrumb-item`,e.clickable&&`${i}-breadcrumb-item--clickable`]},v(s.value,{class:`${i}-breadcrumb-item__link`,"aria-current":d.value,href:e.href,onClick:e.onClick},r),v("span",{class:`${i}-breadcrumb-item__separator`,"aria-hidden":"true"},Q(r.separator,()=>{var c;return[(c=e.separator)!==null&&c!==void 0?c:n.value]})))}}}),R=e=>(X("data-v-5edcb46f"),e=e(),Y(),e),me={style:{"background-color":"#91DDFF",padding:"3px 7px"}},ue=R(()=>p("svg",{xmlns:"http://www.w3.org/2000/svg","xmlns:xlink":"http://www.w3.org/1999/xlink",viewBox:"0 0 24 24"},[p("path",{d:"M14.59 7.41L18.17 11H6v2h12.17l-3.59 3.59L16 18l6-6l-6-6l-1.41 1.41zM2 6v12h2V6H2z",fill:"currentColor"})],-1)),pe=R(()=>p("svg",{xmlns:"http://www.w3.org/2000/svg","xmlns:xlink":"http://www.w3.org/1999/xlink",viewBox:"0 0 12 12"},[p("g",{fill:"none"},[p("path",{d:"M6.786 1.459a.903.903 0 0 0-1.572 0L1.122 8.628C.774 9.238 1.211 10 1.91 10H10.09c.698 0 1.135-.762.787-1.372l-4.092-7.17z",fill:"currentColor"})])],-1)),he=k({__name:"FilePath",props:{name:{type:String,required:!0},path:{type:Array,default(e){return e}},clickBread:{type:Function,default(e){return console.info("unchange"),e}}},setup(e){const r=e,t=function(n){r.clickBread(n)};return(n,a)=>{const o=Z,s=de,d=ie;return x(),w(d,{style:{height:"30px","line-height":"30px","margin-top":"10px"}},{default:u(()=>[_(s,{onClick:a[0]||(a[0]=i=>t(0))},{separator:u(()=>[_(o,{class:"triangle-icon"},{default:u(()=>[pe]),_:1})]),default:u(()=>[p("span",me,[_(o,null,{default:u(()=>[ue]),_:1}),B(" "+y(r.name),1)])]),_:1}),(x(!0),U(J,null,G(r.path,(i,c)=>(x(),w(s,{key:c,onClick:b=>t(c+1)},{default:u(()=>[B(y(i),1)]),_:2},1032,["onClick"]))),128))]),_:1})}}});const ge=W(he,[["__scopeId","data-v-5edcb46f"]]);export{ge as F};
