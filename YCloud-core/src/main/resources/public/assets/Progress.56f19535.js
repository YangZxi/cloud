import{k as _,q as P,p as s,x,d as z,v as b,i as r,N,aV as q,aW as L,aX as O,aY as T,s as M,t as A,y as I,w as X}from"./index.6d4055d0.js";import{f as S}from"./use-merged-state.0840fd6e.js";const Y=e=>{const{infoColor:u,successColor:g,warningColor:n,errorColor:i,textColor2:o,progressRailColor:f,fontSize:l,fontWeight:c}=e;return{fontSize:l,fontSizeCircle:"28px",fontWeightCircle:c,railColor:f,railHeight:"8px",iconSizeCircle:"36px",iconSizeLine:"18px",iconColor:u,iconColorInfo:u,iconColorSuccess:g,iconColorWarning:n,iconColorError:i,textColorCircle:o,textColorLineInner:"rgb(255, 255, 255)",textColorLineOuter:o,fillColor:u,fillColorInfo:u,fillColorSuccess:g,fillColorWarning:n,fillColorError:i,lineBgProcessing:"linear-gradient(90deg, rgba(255, 255, 255, .3) 0%, rgba(255, 255, 255, .5) 100%)"}},j={name:"Progress",common:_,self:Y},G=j,H=P([s("progress",{display:"inline-block"},[s("progress-icon",`
 color: var(--n-icon-color);
 transition: color .3s var(--n-bezier);
 `),x("line",`
 width: 100%;
 display: block;
 `,[s("progress-content",`
 display: flex;
 align-items: center;
 `,[s("progress-graph",{flex:1})]),s("progress-custom-content",{marginLeft:"14px"}),s("progress-icon",`
 width: 30px;
 padding-left: 14px;
 height: var(--n-icon-size-line);
 line-height: var(--n-icon-size-line);
 font-size: var(--n-icon-size-line);
 `,[x("as-text",`
 color: var(--n-text-color-line-outer);
 text-align: center;
 width: 40px;
 font-size: var(--n-font-size);
 padding-left: 4px;
 transition: color .3s var(--n-bezier);
 `)])]),x("circle, dashboard",{width:"120px"},[s("progress-custom-content",`
 position: absolute;
 left: 50%;
 top: 50%;
 transform: translateX(-50%) translateY(-50%);
 display: flex;
 align-items: center;
 justify-content: center;
 `),s("progress-text",`
 position: absolute;
 left: 50%;
 top: 50%;
 transform: translateX(-50%) translateY(-50%);
 display: flex;
 align-items: center;
 color: inherit;
 font-size: var(--n-font-size-circle);
 color: var(--n-text-color-circle);
 font-weight: var(--n-font-weight-circle);
 transition: color .3s var(--n-bezier);
 white-space: nowrap;
 `),s("progress-icon",`
 position: absolute;
 left: 50%;
 top: 50%;
 transform: translateX(-50%) translateY(-50%);
 display: flex;
 align-items: center;
 color: var(--n-icon-color);
 font-size: var(--n-icon-size-circle);
 `)]),x("multiple-circle",`
 width: 200px;
 color: inherit;
 `,[s("progress-text",`
 font-weight: var(--n-font-weight-circle);
 color: var(--n-text-color-circle);
 position: absolute;
 left: 50%;
 top: 50%;
 transform: translateX(-50%) translateY(-50%);
 display: flex;
 align-items: center;
 justify-content: center;
 transition: color .3s var(--n-bezier);
 `)]),s("progress-content",{position:"relative"}),s("progress-graph",{position:"relative"},[s("progress-graph-circle",[P("svg",{verticalAlign:"bottom"}),s("progress-graph-circle-fill",`
 stroke: var(--n-fill-color);
 transition:
 opacity .3s var(--n-bezier),
 stroke .3s var(--n-bezier),
 stroke-dasharray .3s var(--n-bezier);
 `,[x("empty",{opacity:0})]),s("progress-graph-circle-rail",`
 transition: stroke .3s var(--n-bezier);
 overflow: hidden;
 stroke: var(--n-rail-color);
 `)]),s("progress-graph-line",[x("indicator-inside",[s("progress-graph-line-rail",`
 height: 16px;
 line-height: 16px;
 border-radius: 10px;
 `,[s("progress-graph-line-fill",`
 height: inherit;
 border-radius: 10px;
 `),s("progress-graph-line-indicator",`
 background: #0000;
 white-space: nowrap;
 text-align: right;
 margin-left: 14px;
 margin-right: 14px;
 height: inherit;
 font-size: 12px;
 color: var(--n-text-color-line-inner);
 transition: color .3s var(--n-bezier);
 `)])]),x("indicator-inside-label",`
 height: 16px;
 display: flex;
 align-items: center;
 `,[s("progress-graph-line-rail",`
 flex: 1;
 transition: background-color .3s var(--n-bezier);
 `),s("progress-graph-line-indicator",`
 background: var(--n-fill-color);
 font-size: 12px;
 transform: translateZ(0);
 display: flex;
 vertical-align: middle;
 height: 16px;
 line-height: 16px;
 padding: 0 10px;
 border-radius: 10px;
 position: absolute;
 white-space: nowrap;
 color: var(--n-text-color-line-inner);
 transition:
 right .2s var(--n-bezier),
 color .3s var(--n-bezier),
 background-color .3s var(--n-bezier);
 `)]),s("progress-graph-line-rail",`
 position: relative;
 overflow: hidden;
 height: var(--n-rail-height);
 border-radius: 5px;
 background-color: var(--n-rail-color);
 transition: background-color .3s var(--n-bezier);
 `,[s("progress-graph-line-fill",`
 background: var(--n-fill-color);
 position: relative;
 border-radius: 5px;
 height: inherit;
 width: 100%;
 max-width: 0%;
 transition:
 background-color .3s var(--n-bezier),
 max-width .2s var(--n-bezier);
 `,[x("processing",[P("&::after",`
 content: "";
 background-image: var(--n-line-bg-processing);
 animation: progress-processing-animation 2s var(--n-bezier) infinite;
 `)])])])])])]),P("@keyframes progress-processing-animation",`
 0% {
 position: absolute;
 left: 0;
 top: 0;
 bottom: 0;
 right: 100%;
 opacity: 1;
 }
 66% {
 position: absolute;
 left: 0;
 top: 0;
 bottom: 0;
 right: 0;
 opacity: 0;
 }
 100% {
 position: absolute;
 left: 0;
 top: 0;
 bottom: 0;
 right: 0;
 opacity: 0;
 }
 `)]),E={success:r(q,null),error:r(L,null),warning:r(O,null),info:r(T,null)},V=z({name:"ProgressLine",props:{clsPrefix:{type:String,required:!0},percentage:{type:Number,default:0},railColor:String,railStyle:[String,Object],fillColor:String,status:{type:String,required:!0},indicatorPlacement:{type:String,required:!0},indicatorTextColor:String,unit:{type:String,default:"%"},processing:{type:Boolean,required:!0},showIndicator:{type:Boolean,required:!0},height:[String,Number],railBorderRadius:[String,Number],fillBorderRadius:[String,Number]},setup(e,{slots:u}){const g=b(()=>S(e.height)),n=b(()=>e.railBorderRadius!==void 0?S(e.railBorderRadius):e.height!==void 0?S(e.height,{c:.5}):""),i=b(()=>e.fillBorderRadius!==void 0?S(e.fillBorderRadius):e.railBorderRadius!==void 0?S(e.railBorderRadius):e.height!==void 0?S(e.height,{c:.5}):"");return()=>{const{indicatorPlacement:o,railColor:f,railStyle:l,percentage:c,unit:d,indicatorTextColor:m,status:p,showIndicator:h,fillColor:t,processing:v,clsPrefix:a}=e;return r("div",{class:`${a}-progress-content`,role:"none"},r("div",{class:`${a}-progress-graph`,"aria-hidden":!0},r("div",{class:[`${a}-progress-graph-line`,{[`${a}-progress-graph-line--indicator-${o}`]:!0}]},r("div",{class:`${a}-progress-graph-line-rail`,style:[{backgroundColor:f,height:g.value,borderRadius:n.value},l]},r("div",{class:[`${a}-progress-graph-line-fill`,v&&`${a}-progress-graph-line-fill--processing`],style:{maxWidth:`${e.percentage}%`,backgroundColor:t,height:g.value,lineHeight:g.value,borderRadius:i.value}},o==="inside"?r("div",{class:`${a}-progress-graph-line-indicator`},c,d):null)))),h&&o==="outside"?r("div",null,u.default?r("div",{class:`${a}-progress-custom-content`,style:{color:m},role:"none"},u.default()):p==="default"?r("div",{role:"none",class:`${a}-progress-icon ${a}-progress-icon--as-text`,style:{color:m}},c,d):r("div",{class:`${a}-progress-icon`,"aria-hidden":!0},r(N,{clsPrefix:a},{default:()=>E[p]}))):null)}}}),F={success:r(q,null),error:r(L,null),warning:r(O,null),info:r(T,null)},K=z({name:"ProgressCircle",props:{clsPrefix:{type:String,required:!0},status:{type:String,required:!0},strokeWidth:{type:Number,required:!0},fillColor:String,railColor:String,railStyle:[String,Object],percentage:{type:Number,default:0},offsetDegree:{type:Number,default:0},showIndicator:{type:Boolean,required:!0},indicatorTextColor:String,unit:String,viewBoxWidth:{type:Number,required:!0},gapDegree:{type:Number,required:!0},gapOffsetDegree:{type:Number,default:0}},setup(e,{slots:u}){function g(n,i,o){const{gapDegree:f,viewBoxWidth:l,strokeWidth:c}=e,d=50,m=0,p=d,h=0,t=2*d,v=50+c/2,a=`M ${v},${v} m ${m},${p}
      a ${d},${d} 0 1 1 ${h},${-t}
      a ${d},${d} 0 1 1 ${-h},${t}`,C=Math.PI*2*d,$={stroke:o,strokeDasharray:`${n/100*(C-f)}px ${l*8}px`,strokeDashoffset:`-${f/2}px`,transformOrigin:i?"center":void 0,transform:i?`rotate(${i}deg)`:void 0};return{pathString:a,pathStyle:$}}return()=>{const{fillColor:n,railColor:i,strokeWidth:o,offsetDegree:f,status:l,percentage:c,showIndicator:d,indicatorTextColor:m,unit:p,gapOffsetDegree:h,clsPrefix:t}=e,{pathString:v,pathStyle:a}=g(100,0,i),{pathString:C,pathStyle:$}=g(c,f,n),y=100+o;return r("div",{class:`${t}-progress-content`,role:"none"},r("div",{class:`${t}-progress-graph`,"aria-hidden":!0},r("div",{class:`${t}-progress-graph-circle`,style:{transform:h?`rotate(${h}deg)`:void 0}},r("svg",{viewBox:`0 0 ${y} ${y}`},r("g",null,r("path",{class:`${t}-progress-graph-circle-rail`,d:v,"stroke-width":o,"stroke-linecap":"round",fill:"none",style:a})),r("g",null,r("path",{class:[`${t}-progress-graph-circle-fill`,c===0&&`${t}-progress-graph-circle-fill--empty`],d:C,"stroke-width":o,"stroke-linecap":"round",fill:"none",style:$}))))),d?r("div",null,u.default?r("div",{class:`${t}-progress-custom-content`,role:"none"},u.default()):l!=="default"?r("div",{class:`${t}-progress-icon`,"aria-hidden":!0},r(N,{clsPrefix:t},{default:()=>F[l]})):r("div",{class:`${t}-progress-text`,style:{color:m},role:"none"},r("span",{class:`${t}-progress-text__percentage`},c),r("span",{class:`${t}-progress-text__unit`},p))):null)}}});function D(e,u,g=100){return`m ${g/2} ${g/2-e} a ${e} ${e} 0 1 1 0 ${2*e} a ${e} ${e} 0 1 1 0 -${2*e}`}const Z=z({name:"ProgressMultipleCircle",props:{clsPrefix:{type:String,required:!0},viewBoxWidth:{type:Number,required:!0},percentage:{type:Array,default:[0]},strokeWidth:{type:Number,required:!0},circleGap:{type:Number,required:!0},showIndicator:{type:Boolean,required:!0},fillColor:{type:Array,default:()=>[]},railColor:{type:Array,default:()=>[]},railStyle:{type:Array,default:()=>[]}},setup(e,{slots:u}){const g=b(()=>e.percentage.map((i,o)=>`${Math.PI*i/100*(e.viewBoxWidth/2-e.strokeWidth/2*(1+2*o)-e.circleGap*o)*2}, ${e.viewBoxWidth*8}`));return()=>{const{viewBoxWidth:n,strokeWidth:i,circleGap:o,showIndicator:f,fillColor:l,railColor:c,railStyle:d,percentage:m,clsPrefix:p}=e;return r("div",{class:`${p}-progress-content`,role:"none"},r("div",{class:`${p}-progress-graph`,"aria-hidden":!0},r("div",{class:`${p}-progress-graph-circle`},r("svg",{viewBox:`0 0 ${n} ${n}`},m.map((h,t)=>r("g",{key:t},r("path",{class:`${p}-progress-graph-circle-rail`,d:D(n/2-i/2*(1+2*t)-o*t,i,n),"stroke-width":i,"stroke-linecap":"round",fill:"none",style:[{strokeDashoffset:0,stroke:c[t]},d[t]]}),r("path",{class:[`${p}-progress-graph-circle-fill`,h===0&&`${p}-progress-graph-circle-fill--empty`],d:D(n/2-i/2*(1+2*t)-o*t,i,n),"stroke-width":i,"stroke-linecap":"round",fill:"none",style:{strokeDasharray:g.value[t],strokeDashoffset:0,stroke:l[t]}})))))),f&&u.default?r("div",null,r("div",{class:`${p}-progress-text`},u.default())):null)}}}),J=Object.assign(Object.assign({},A.props),{processing:Boolean,type:{type:String,default:"line"},gapDegree:Number,gapOffsetDegree:Number,status:{type:String,default:"default"},railColor:[String,Array],railStyle:[String,Array],color:[String,Array],viewBoxWidth:{type:Number,default:100},strokeWidth:{type:Number,default:7},percentage:[Number,Array],unit:{type:String,default:"%"},showIndicator:{type:Boolean,default:!0},indicatorPosition:{type:String,default:"outside"},indicatorPlacement:{type:String,default:"outside"},indicatorTextColor:String,circleGap:{type:Number,default:1},height:Number,borderRadius:[String,Number],fillBorderRadius:[String,Number],offsetDegree:Number}),ee=z({name:"Progress",props:J,setup(e){const u=b(()=>e.indicatorPlacement||e.indicatorPosition),g=b(()=>{if(e.gapDegree||e.gapDegree===0)return e.gapDegree;if(e.type==="dashboard")return 75}),{mergedClsPrefixRef:n,inlineThemeDisabled:i}=M(e),o=A("Progress","-progress",H,G,e,n),f=b(()=>{const{status:c}=e,{common:{cubicBezierEaseInOut:d},self:{fontSize:m,fontSizeCircle:p,railColor:h,railHeight:t,iconSizeCircle:v,iconSizeLine:a,textColorCircle:C,textColorLineInner:$,textColorLineOuter:y,lineBgProcessing:k,fontWeightCircle:B,[I("iconColor",c)]:R,[I("fillColor",c)]:w}}=o.value;return{"--n-bezier":d,"--n-fill-color":w,"--n-font-size":m,"--n-font-size-circle":p,"--n-font-weight-circle":B,"--n-icon-color":R,"--n-icon-size-circle":v,"--n-icon-size-line":a,"--n-line-bg-processing":k,"--n-rail-color":h,"--n-rail-height":t,"--n-text-color-circle":C,"--n-text-color-line-inner":$,"--n-text-color-line-outer":y}}),l=i?X("progress",b(()=>e.status[0]),f,e):void 0;return{mergedClsPrefix:n,mergedIndicatorPlacement:u,gapDeg:g,cssVars:i?void 0:f,themeClass:l==null?void 0:l.themeClass,onRender:l==null?void 0:l.onRender}},render(){const{type:e,cssVars:u,indicatorTextColor:g,showIndicator:n,status:i,railColor:o,railStyle:f,color:l,percentage:c,viewBoxWidth:d,strokeWidth:m,mergedIndicatorPlacement:p,unit:h,borderRadius:t,fillBorderRadius:v,height:a,processing:C,circleGap:$,mergedClsPrefix:y,gapDeg:k,gapOffsetDegree:B,themeClass:R,$slots:w,onRender:W}=this;return W==null||W(),r("div",{class:[R,`${y}-progress`,`${y}-progress--${e}`,`${y}-progress--${i}`],style:u,"aria-valuemax":100,"aria-valuemin":0,"aria-valuenow":c,role:e==="circle"||e==="line"||e==="dashboard"?"progressbar":"none"},e==="circle"||e==="dashboard"?r(K,{clsPrefix:y,status:i,showIndicator:n,indicatorTextColor:g,railColor:o,fillColor:l,railStyle:f,offsetDegree:this.offsetDegree,percentage:c,viewBoxWidth:d,strokeWidth:m,gapDegree:k===void 0?e==="dashboard"?75:0:k,gapOffsetDegree:B,unit:h},w):e==="line"?r(V,{clsPrefix:y,status:i,showIndicator:n,indicatorTextColor:g,railColor:o,fillColor:l,railStyle:f,percentage:c,processing:C,indicatorPlacement:p,unit:h,fillBorderRadius:v,railBorderRadius:t,height:a},w):e==="multiple-circle"?r(Z,{clsPrefix:y,strokeWidth:m,railColor:o,fillColor:l,railStyle:f,viewBoxWidth:d,percentage:c,showIndicator:n,circleGap:$},w):null)}});export{ee as _,G as p};
