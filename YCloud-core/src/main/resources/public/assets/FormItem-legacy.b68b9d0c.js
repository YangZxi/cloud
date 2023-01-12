System.register(["./index-legacy.dff47cd5.js","./Icon-legacy.f717df39.js"],(function(e,r){"use strict";var t,n,i,a,o,l,s,u,d,f,c,p,m,g,h,b,v,y,x,w,k,q,F,O,S,P,$,j,A,z;return{setters:[e=>{t=e.R,n=e.Z,i=e.i,a=e.ac,o=e.v,l=e.x,s=e.y,u=e.z,d=e.Q,f=e.d,c=e.B,p=e.A,m=e.r,g=e.a1,h=e.s,b=e.ad,v=e.j,y=e.ae,x=e.G,w=e.J,k=e.af,q=e.ag,F=e.Y,O=e.C,S=e.T,P=e.a3,$=e.a8,j=e.D},e=>{A=e.f,z=e.g}],execute:function(){function r(e,r,o){var l;const s=t(e,null);if(null===s)return;const u=null===(l=n())||void 0===l?void 0:l.proxy;function d(e,t){const n=s[r];void 0!==t&&function(e,r){e[r]||(e[r]=[]),e[r].splice(e[r].findIndex((e=>e===u)),1)}(n,t),void 0!==e&&function(e,r){e[r]||(e[r]=[]),~e[r].findIndex((e=>e===u))||e[r].push(u)}(n,e)}i(o,d),d(o.value),a((()=>{d(void 0,o.value)}))}const _={feedbackPadding:"4px 0 0 2px",feedbackHeightSmall:"24px",feedbackHeightMedium:"24px",feedbackHeightLarge:"26px",feedbackFontSizeSmall:"13px",feedbackFontSizeMedium:"14px",feedbackFontSizeLarge:"14px",labelFontSizeLeftSmall:"14px",labelFontSizeLeftMedium:"14px",labelFontSizeLeftLarge:"15px",labelFontSizeTopSmall:"13px",labelFontSizeTopMedium:"14px",labelFontSizeTopLarge:"14px",labelHeightSmall:"24px",labelHeightMedium:"26px",labelHeightLarge:"28px",labelPaddingVertical:"0 0 6px 2px",labelPaddingHorizontal:"0 12px 0 0",labelTextAlignVertical:"left",labelTextAlignHorizontal:"right"},E={name:"Form",common:o,self:e=>{const{heightSmall:r,heightMedium:t,heightLarge:n,textColor1:i,errorColor:a,warningColor:o,lineHeight:l,textColor3:s}=e;return Object.assign(Object.assign({},_),{blankHeightSmall:r,blankHeightMedium:t,blankHeightLarge:n,lineHeight:l,labelTextColor:i,asteriskColor:a,feedbackTextColorError:a,feedbackTextColorWarning:o,feedbackTextColor:s})}},R=l("form",[s("inline","\n width: 100%;\n display: inline-flex;\n align-items: flex-start;\n align-content: space-around;\n ",[l("form-item",{width:"auto",marginRight:"18px"},[u("&:last-child",{marginRight:0})])])]),C=d("n-form"),M=d("n-form-item-insts");var L=globalThis&&globalThis.__awaiter||function(e,r,t,n){return new(t||(t=Promise))((function(i,a){function o(e){try{s(n.next(e))}catch(r){a(r)}}function l(e){try{s(n.throw(e))}catch(r){a(r)}}function s(e){var r;e.done?i(e.value):(r=e.value,r instanceof t?r:new t((function(e){e(r)}))).then(o,l)}s((n=n.apply(e,r||[])).next())}))};const T=Object.assign(Object.assign({},p.props),{inline:Boolean,labelWidth:[Number,String],labelAlign:String,labelPlacement:{type:String,default:"top"},model:{type:Object,default:()=>{}},rules:Object,disabled:Boolean,size:String,showRequireMark:{type:Boolean,default:void 0},requireMarkPlacement:String,showFeedback:{type:Boolean,default:!0},onSubmit:{type:Function,default:e=>e.preventDefault()},showLabel:{type:Boolean,default:void 0},validateMessages:Object});function V(){return V=Object.assign?Object.assign.bind():function(e){for(var r=1;r<arguments.length;r++){var t=arguments[r];for(var n in t)Object.prototype.hasOwnProperty.call(t,n)&&(e[n]=t[n])}return e},V.apply(this,arguments)}function B(e){return B=Object.setPrototypeOf?Object.getPrototypeOf.bind():function(e){return e.__proto__||Object.getPrototypeOf(e)},B(e)}function W(e,r){return W=Object.setPrototypeOf?Object.setPrototypeOf.bind():function(e,r){return e.__proto__=r,e},W(e,r)}function D(){if("undefined"==typeof Reflect||!Reflect.construct)return!1;if(Reflect.construct.sham)return!1;if("function"==typeof Proxy)return!0;try{return Boolean.prototype.valueOf.call(Reflect.construct(Boolean,[],(function(){}))),!0}catch(e){return!1}}function H(e,r,t){return H=D()?Reflect.construct.bind():function(e,r,t){var n=[null];n.push.apply(n,r);var i=new(Function.bind.apply(e,n));return t&&W(i,t.prototype),i},H.apply(null,arguments)}function I(e){var r="function"==typeof Map?new Map:void 0;return I=function(e){if(null===e||(t=e,-1===Function.toString.call(t).indexOf("[native code]")))return e;var t;if("function"!=typeof e)throw new TypeError("Super expression must either be null or a function");if(void 0!==r){if(r.has(e))return r.get(e);r.set(e,n)}function n(){return H(e,arguments,B(this).constructor)}return n.prototype=Object.create(e.prototype,{constructor:{value:n,enumerable:!1,writable:!0,configurable:!0}}),W(n,e)},I(e)}e("a",f({name:"Form",props:T,setup(e){const{mergedClsPrefixRef:r}=c(e);p("Form","-form",R,E,e,r);const t={},n=m(void 0);g(C,{props:e,maxChildLabelWidthRef:n,deriveMaxChildLabelWidth:e=>{const r=n.value;(void 0===r||e>=r)&&(n.value=e)}}),g(M,{formItems:t});const i={validate:function(e,r=(()=>!0)){return L(this,void 0,void 0,(function*(){return yield new Promise(((n,i)=>{const a=[];for(const e of b(t)){const n=t[e];for(const e of n)e.path&&a.push(e.internalValidate(null,r))}Promise.all(a).then((r=>{if(r.some((e=>!e.valid))){const t=r.filter((e=>e.errors)).map((e=>e.errors));e&&e(t),i(t)}else e&&e(),n()}))}))}))},restoreValidation:function(){for(const e of b(t)){const r=t[e];for(const e of r)e.restoreValidation()}}};return Object.assign(i,{mergedClsPrefix:r})},render(){const{mergedClsPrefix:e}=this;return h("form",{class:[`${e}-form`,this.inline&&`${e}-form--inline`],onSubmit:this.onSubmit},this.$slots)}}));var N=/%[sdj%]/g,Y=function(){};function J(e){if(!e||!e.length)return null;var r={};return e.forEach((function(e){var t=e.field;r[t]=r[t]||[],r[t].push(e)})),r}function Z(e){for(var r=arguments.length,t=new Array(r>1?r-1:0),n=1;n<r;n++)t[n-1]=arguments[n];var i=0,a=t.length;if("function"==typeof e)return e.apply(null,t);if("string"==typeof e){var o=e.replace(N,(function(e){if("%%"===e)return"%";if(i>=a)return e;switch(e){case"%s":return String(t[i++]);case"%d":return Number(t[i++]);case"%j":try{return JSON.stringify(t[i++])}catch(r){return"[Circular]"}break;default:return e}}));return o}return e}function G(e,r){return null==e||!("array"!==r||!Array.isArray(e)||e.length)||!(!function(e){return"string"===e||"url"===e||"hex"===e||"email"===e||"date"===e||"pattern"===e}(r)||"string"!=typeof e||e)}function Q(e,r,t){var n=0,i=e.length;!function a(o){if(o&&o.length)t(o);else{var l=n;n+=1,l<i?r(e[l],a):t([])}}([])}"undefined"!=typeof process&&process.env;var U=function(e){var r,t;function n(r,t){var n;return(n=e.call(this,"Async Validation Error")||this).errors=r,n.fields=t,n}return t=e,(r=n).prototype=Object.create(t.prototype),r.prototype.constructor=r,W(r,t),n}(I(Error));function K(e,r,t,n,i){if(r.first){var a=new Promise((function(r,a){var o=function(e){var r=[];return Object.keys(e).forEach((function(t){r.push.apply(r,e[t]||[])})),r}(e);Q(o,t,(function(e){return n(e),e.length?a(new U(e,J(e))):r(i)}))}));return a.catch((function(e){return e})),a}var o=!0===r.firstFields?Object.keys(e):r.firstFields||[],l=Object.keys(e),s=l.length,u=0,d=[],f=new Promise((function(r,a){var f=function(e){if(d.push.apply(d,e),++u===s)return n(d),d.length?a(new U(d,J(d))):r(i)};l.length||(n(d),r(i)),l.forEach((function(r){var n=e[r];-1!==o.indexOf(r)?Q(n,t,f):function(e,r,t){var n=[],i=0,a=e.length;function o(e){n.push.apply(n,e||[]),++i===a&&t(n)}e.forEach((function(e){r(e,o)}))}(n,t,f)}))}));return f.catch((function(e){return e})),f}function X(e,r){return function(t){var n,i;return n=e.fullFields?function(e,r){for(var t=e,n=0;n<r.length;n++){if(null==t)return t;t=t[r[n]]}return t}(r,e.fullFields):r[t.field||e.fullField],(i=t)&&void 0!==i.message?(t.field=t.field||e.fullField,t.fieldValue=n,t):{message:"function"==typeof t?t():t,fieldValue:n,field:t.field||e.fullField}}}function ee(e,r){if(r)for(var t in r)if(r.hasOwnProperty(t)){var n=r[t];"object"==typeof n&&"object"==typeof e[t]?e[t]=V({},e[t],n):e[t]=n}return e}var re,te=function(e,r,t,n,i,a){!e.required||t.hasOwnProperty(e.field)&&!G(r,a||e.type)||n.push(Z(i.messages.required,e.fullField))},ne=/^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]+\.)+[a-zA-Z\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]{2,}))$/,ie=/^#?([a-f0-9]{6}|[a-f0-9]{3})$/i,ae={integer:function(e){return ae.number(e)&&parseInt(e,10)===e},float:function(e){return ae.number(e)&&!ae.integer(e)},array:function(e){return Array.isArray(e)},regexp:function(e){if(e instanceof RegExp)return!0;try{return!!new RegExp(e)}catch(r){return!1}},date:function(e){return"function"==typeof e.getTime&&"function"==typeof e.getMonth&&"function"==typeof e.getYear&&!isNaN(e.getTime())},number:function(e){return!isNaN(e)&&"number"==typeof e},object:function(e){return"object"==typeof e&&!ae.array(e)},method:function(e){return"function"==typeof e},email:function(e){return"string"==typeof e&&e.length<=320&&!!e.match(ne)},url:function(e){return"string"==typeof e&&e.length<=2048&&!!e.match(function(){if(re)return re;var e="[a-fA-F\\d:]",r=function(r){return r&&r.includeBoundaries?"(?:(?<=\\s|^)(?="+e+")|(?<="+e+")(?=\\s|$))":""},t="(?:25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]\\d|\\d)(?:\\.(?:25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]\\d|\\d)){3}",n="[a-fA-F\\d]{1,4}",i=("\n(?:\n(?:"+n+":){7}(?:"+n+"|:)|                                    // 1:2:3:4:5:6:7::  1:2:3:4:5:6:7:8\n(?:"+n+":){6}(?:"+t+"|:"+n+"|:)|                             // 1:2:3:4:5:6::    1:2:3:4:5:6::8   1:2:3:4:5:6::8  1:2:3:4:5:6::1.2.3.4\n(?:"+n+":){5}(?::"+t+"|(?::"+n+"){1,2}|:)|                   // 1:2:3:4:5::      1:2:3:4:5::7:8   1:2:3:4:5::8    1:2:3:4:5::7:1.2.3.4\n(?:"+n+":){4}(?:(?::"+n+"){0,1}:"+t+"|(?::"+n+"){1,3}|:)| // 1:2:3:4::        1:2:3:4::6:7:8   1:2:3:4::8      1:2:3:4::6:7:1.2.3.4\n(?:"+n+":){3}(?:(?::"+n+"){0,2}:"+t+"|(?::"+n+"){1,4}|:)| // 1:2:3::          1:2:3::5:6:7:8   1:2:3::8        1:2:3::5:6:7:1.2.3.4\n(?:"+n+":){2}(?:(?::"+n+"){0,3}:"+t+"|(?::"+n+"){1,5}|:)| // 1:2::            1:2::4:5:6:7:8   1:2::8          1:2::4:5:6:7:1.2.3.4\n(?:"+n+":){1}(?:(?::"+n+"){0,4}:"+t+"|(?::"+n+"){1,6}|:)| // 1::              1::3:4:5:6:7:8   1::8            1::3:4:5:6:7:1.2.3.4\n(?::(?:(?::"+n+"){0,5}:"+t+"|(?::"+n+"){1,7}|:))             // ::2:3:4:5:6:7:8  ::2:3:4:5:6:7:8  ::8             ::1.2.3.4\n)(?:%[0-9a-zA-Z]{1,})?                                             // %eth0            %1\n").replace(/\s*\/\/.*$/gm,"").replace(/\n/g,"").trim(),a=new RegExp("(?:^"+t+"$)|(?:^"+i+"$)"),o=new RegExp("^"+t+"$"),l=new RegExp("^"+i+"$"),s=function(e){return e&&e.exact?a:new RegExp("(?:"+r(e)+t+r(e)+")|(?:"+r(e)+i+r(e)+")","g")};s.v4=function(e){return e&&e.exact?o:new RegExp(""+r(e)+t+r(e),"g")},s.v6=function(e){return e&&e.exact?l:new RegExp(""+r(e)+i+r(e),"g")};var u=s.v4().source,d=s.v6().source;return re=new RegExp("(?:^(?:(?:(?:[a-z]+:)?//)|www\\.)(?:\\S+(?::\\S*)?@)?(?:localhost|"+u+"|"+d+'|(?:(?:[a-z\\u00a1-\\uffff0-9][-_]*)*[a-z\\u00a1-\\uffff0-9]+)(?:\\.(?:[a-z\\u00a1-\\uffff0-9]-*)*[a-z\\u00a1-\\uffff0-9]+)*(?:\\.(?:[a-z\\u00a1-\\uffff]{2,})))(?::\\d{2,5})?(?:[/?#][^\\s"]*)?$)',"i")}())},hex:function(e){return"string"==typeof e&&!!e.match(ie)}},oe={required:te,whitespace:function(e,r,t,n,i){(/^\s+$/.test(r)||""===r)&&n.push(Z(i.messages.whitespace,e.fullField))},type:function(e,r,t,n,i){if(e.required&&void 0===r)te(e,r,t,n,i);else{var a=e.type;["integer","float","array","regexp","object","method","email","number","date","url","hex"].indexOf(a)>-1?ae[a](r)||n.push(Z(i.messages.types[a],e.fullField,e.type)):a&&typeof r!==e.type&&n.push(Z(i.messages.types[a],e.fullField,e.type))}},range:function(e,r,t,n,i){var a="number"==typeof e.len,o="number"==typeof e.min,l="number"==typeof e.max,s=r,u=null,d="number"==typeof r,f="string"==typeof r,c=Array.isArray(r);if(d?u="number":f?u="string":c&&(u="array"),!u)return!1;c&&(s=r.length),f&&(s=r.replace(/[\uD800-\uDBFF][\uDC00-\uDFFF]/g,"_").length),a?s!==e.len&&n.push(Z(i.messages[u].len,e.fullField,e.len)):o&&!l&&s<e.min?n.push(Z(i.messages[u].min,e.fullField,e.min)):l&&!o&&s>e.max?n.push(Z(i.messages[u].max,e.fullField,e.max)):o&&l&&(s<e.min||s>e.max)&&n.push(Z(i.messages[u].range,e.fullField,e.min,e.max))},enum:function(e,r,t,n,i){e.enum=Array.isArray(e.enum)?e.enum:[],-1===e.enum.indexOf(r)&&n.push(Z(i.messages.enum,e.fullField,e.enum.join(", ")))},pattern:function(e,r,t,n,i){e.pattern&&(e.pattern instanceof RegExp?(e.pattern.lastIndex=0,e.pattern.test(r)||n.push(Z(i.messages.pattern.mismatch,e.fullField,r,e.pattern))):"string"==typeof e.pattern&&(new RegExp(e.pattern).test(r)||n.push(Z(i.messages.pattern.mismatch,e.fullField,r,e.pattern))))}},le=function(e,r,t,n,i){var a=e.type,o=[];if(e.required||!e.required&&n.hasOwnProperty(e.field)){if(G(r,a)&&!e.required)return t();oe.required(e,r,n,o,i,a),G(r,a)||oe.type(e,r,n,o,i)}t(o)},se={string:function(e,r,t,n,i){var a=[];if(e.required||!e.required&&n.hasOwnProperty(e.field)){if(G(r,"string")&&!e.required)return t();oe.required(e,r,n,a,i,"string"),G(r,"string")||(oe.type(e,r,n,a,i),oe.range(e,r,n,a,i),oe.pattern(e,r,n,a,i),!0===e.whitespace&&oe.whitespace(e,r,n,a,i))}t(a)},method:function(e,r,t,n,i){var a=[];if(e.required||!e.required&&n.hasOwnProperty(e.field)){if(G(r)&&!e.required)return t();oe.required(e,r,n,a,i),void 0!==r&&oe.type(e,r,n,a,i)}t(a)},number:function(e,r,t,n,i){var a=[];if(e.required||!e.required&&n.hasOwnProperty(e.field)){if(""===r&&(r=void 0),G(r)&&!e.required)return t();oe.required(e,r,n,a,i),void 0!==r&&(oe.type(e,r,n,a,i),oe.range(e,r,n,a,i))}t(a)},boolean:function(e,r,t,n,i){var a=[];if(e.required||!e.required&&n.hasOwnProperty(e.field)){if(G(r)&&!e.required)return t();oe.required(e,r,n,a,i),void 0!==r&&oe.type(e,r,n,a,i)}t(a)},regexp:function(e,r,t,n,i){var a=[];if(e.required||!e.required&&n.hasOwnProperty(e.field)){if(G(r)&&!e.required)return t();oe.required(e,r,n,a,i),G(r)||oe.type(e,r,n,a,i)}t(a)},integer:function(e,r,t,n,i){var a=[];if(e.required||!e.required&&n.hasOwnProperty(e.field)){if(G(r)&&!e.required)return t();oe.required(e,r,n,a,i),void 0!==r&&(oe.type(e,r,n,a,i),oe.range(e,r,n,a,i))}t(a)},float:function(e,r,t,n,i){var a=[];if(e.required||!e.required&&n.hasOwnProperty(e.field)){if(G(r)&&!e.required)return t();oe.required(e,r,n,a,i),void 0!==r&&(oe.type(e,r,n,a,i),oe.range(e,r,n,a,i))}t(a)},array:function(e,r,t,n,i){var a=[];if(e.required||!e.required&&n.hasOwnProperty(e.field)){if(null==r&&!e.required)return t();oe.required(e,r,n,a,i,"array"),null!=r&&(oe.type(e,r,n,a,i),oe.range(e,r,n,a,i))}t(a)},object:function(e,r,t,n,i){var a=[];if(e.required||!e.required&&n.hasOwnProperty(e.field)){if(G(r)&&!e.required)return t();oe.required(e,r,n,a,i),void 0!==r&&oe.type(e,r,n,a,i)}t(a)},enum:function(e,r,t,n,i){var a=[];if(e.required||!e.required&&n.hasOwnProperty(e.field)){if(G(r)&&!e.required)return t();oe.required(e,r,n,a,i),void 0!==r&&oe.enum(e,r,n,a,i)}t(a)},pattern:function(e,r,t,n,i){var a=[];if(e.required||!e.required&&n.hasOwnProperty(e.field)){if(G(r,"string")&&!e.required)return t();oe.required(e,r,n,a,i),G(r,"string")||oe.pattern(e,r,n,a,i)}t(a)},date:function(e,r,t,n,i){var a=[];if(e.required||!e.required&&n.hasOwnProperty(e.field)){if(G(r,"date")&&!e.required)return t();var o;oe.required(e,r,n,a,i),G(r,"date")||(o=r instanceof Date?r:new Date(r),oe.type(e,o,n,a,i),o&&oe.range(e,o.getTime(),n,a,i))}t(a)},url:le,hex:le,email:le,required:function(e,r,t,n,i){var a=[],o=Array.isArray(r)?"array":typeof r;oe.required(e,r,n,a,i,o),t(a)},any:function(e,r,t,n,i){var a=[];if(e.required||!e.required&&n.hasOwnProperty(e.field)){if(G(r)&&!e.required)return t();oe.required(e,r,n,a,i)}t(a)}};function ue(){return{default:"Validation error on field %s",required:"%s is required",enum:"%s must be one of %s",whitespace:"%s cannot be empty",date:{format:"%s date %s is invalid for format %s",parse:"%s date could not be parsed, %s is invalid ",invalid:"%s date %s is invalid"},types:{string:"%s is not a %s",method:"%s is not a %s (function)",array:"%s is not an %s",object:"%s is not an %s",number:"%s is not a %s",date:"%s is not a %s",boolean:"%s is not a %s",integer:"%s is not an %s",float:"%s is not a %s",regexp:"%s is not a valid %s",email:"%s is not a valid %s",url:"%s is not a valid %s",hex:"%s is not a valid %s"},string:{len:"%s must be exactly %s characters",min:"%s must be at least %s characters",max:"%s cannot be longer than %s characters",range:"%s must be between %s and %s characters"},number:{len:"%s must equal %s",min:"%s cannot be less than %s",max:"%s cannot be greater than %s",range:"%s must be between %s and %s"},array:{len:"%s must be exactly %s in length",min:"%s cannot be less than %s in length",max:"%s cannot be greater than %s in length",range:"%s must be between %s and %s in length"},pattern:{mismatch:"%s value %s does not match pattern %s"},clone:function(){var e=JSON.parse(JSON.stringify(this));return e.clone=this.clone,e}}}var de=ue(),fe=function(){function e(e){this.rules=null,this._messages=de,this.define(e)}var r=e.prototype;return r.define=function(e){var r=this;if(!e)throw new Error("Cannot configure a schema with no rules");if("object"!=typeof e||Array.isArray(e))throw new Error("Rules must be an object");this.rules={},Object.keys(e).forEach((function(t){var n=e[t];r.rules[t]=Array.isArray(n)?n:[n]}))},r.messages=function(e){return e&&(this._messages=ee(ue(),e)),this._messages},r.validate=function(r,t,n){var i=this;void 0===t&&(t={}),void 0===n&&(n=function(){});var a=r,o=t,l=n;if("function"==typeof o&&(l=o,o={}),!this.rules||0===Object.keys(this.rules).length)return l&&l(null,a),Promise.resolve(a);if(o.messages){var s=this.messages();s===de&&(s=ue()),ee(s,o.messages),o.messages=s}else o.messages=this.messages();var u={};(o.keys||Object.keys(this.rules)).forEach((function(e){var t=i.rules[e],n=a[e];t.forEach((function(t){var o=t;"function"==typeof o.transform&&(a===r&&(a=V({},a)),n=a[e]=o.transform(n)),(o="function"==typeof o?{validator:o}:V({},o)).validator=i.getValidationMethod(o),o.validator&&(o.field=e,o.fullField=o.fullField||e,o.type=i.getType(o),u[e]=u[e]||[],u[e].push({rule:o,value:n,source:a,field:e}))}))}));var d={};return K(u,o,(function(r,t){var n,i=r.rule,l=!("object"!==i.type&&"array"!==i.type||"object"!=typeof i.fields&&"object"!=typeof i.defaultField);function s(e,r){return V({},r,{fullField:i.fullField+"."+e,fullFields:i.fullFields?[].concat(i.fullFields,[e]):[e]})}function u(n){void 0===n&&(n=[]);var u=Array.isArray(n)?n:[n];!o.suppressWarning&&u.length&&e.warning("async-validator:",u),u.length&&void 0!==i.message&&(u=[].concat(i.message));var f=u.map(X(i,a));if(o.first&&f.length)return d[i.field]=1,t(f);if(l){if(i.required&&!r.value)return void 0!==i.message?f=[].concat(i.message).map(X(i,a)):o.error&&(f=[o.error(i,Z(o.messages.required,i.field))]),t(f);var c={};i.defaultField&&Object.keys(r.value).map((function(e){c[e]=i.defaultField})),c=V({},c,r.rule.fields);var p={};Object.keys(c).forEach((function(e){var r=c[e],t=Array.isArray(r)?r:[r];p[e]=t.map(s.bind(null,e))}));var m=new e(p);m.messages(o.messages),r.rule.options&&(r.rule.options.messages=o.messages,r.rule.options.error=o.error),m.validate(r.value,r.rule.options||o,(function(e){var r=[];f&&f.length&&r.push.apply(r,f),e&&e.length&&r.push.apply(r,e),t(r.length?r:null)}))}else t(f)}if(l=l&&(i.required||!i.required&&r.value),i.field=r.field,i.asyncValidator)n=i.asyncValidator(i,r.value,u,r.source,o);else if(i.validator){try{n=i.validator(i,r.value,u,r.source,o)}catch(f){null==console.error||console.error(f),o.suppressValidatorError||setTimeout((function(){throw f}),0),u(f.message)}!0===n?u():!1===n?u("function"==typeof i.message?i.message(i.fullField||i.field):i.message||(i.fullField||i.field)+" fails"):n instanceof Array?u(n):n instanceof Error&&u(n.message)}n&&n.then&&n.then((function(){return u()}),(function(e){return u(e)}))}),(function(e){!function(e){for(var r,t,n=[],i={},o=0;o<e.length;o++)r=e[o],t=void 0,Array.isArray(r)?n=(t=n).concat.apply(t,r):n.push(r);n.length?(i=J(n),l(n,i)):l(null,a)}(e)}),a)},r.getType=function(e){if(void 0===e.type&&e.pattern instanceof RegExp&&(e.type="pattern"),"function"!=typeof e.validator&&e.type&&!se.hasOwnProperty(e.type))throw new Error(Z("Unknown rule type %s",e.type));return e.type||"string"},r.getValidationMethod=function(e){if("function"==typeof e.validator)return e.validator;var r=Object.keys(e),t=r.indexOf("message");return-1!==t&&r.splice(t,1),1===r.length&&"required"===r[0]?se.required:se[this.getType(e)]||void 0},e}();fe.register=function(e,r){if("function"!=typeof r)throw new Error("Cannot register a validator by type, validator is not a function");se[e]=r},fe.warning=Y,fe.messages=de,fe.validators=se;const{cubicBezierEaseInOut:ce}=y,pe=l("form-item","\n display: grid;\n line-height: var(--n-line-height);\n",[l("form-item-label","\n grid-area: label;\n align-items: center;\n line-height: 1.25;\n text-align: var(--n-label-text-align);\n font-size: var(--n-label-font-size);\n min-height: var(--n-label-height);\n padding: var(--n-label-padding);\n color: var(--n-label-text-color);\n transition: color .3s var(--n-bezier);\n box-sizing: border-box;\n ",[x("asterisk","\n white-space: nowrap;\n user-select: none;\n -webkit-user-select: none;\n color: var(--n-asterisk-color);\n transition: color .3s var(--n-bezier);\n "),x("asterisk-placeholder","\n grid-area: mark;\n user-select: none;\n -webkit-user-select: none;\n visibility: hidden; \n ")]),l("form-item-blank","\n grid-area: blank;\n min-height: var(--n-blank-height);\n "),s("auto-label-width",[l("form-item-label","white-space: nowrap;")]),s("left-labelled",'\n grid-template-areas:\n "label blank"\n "label feedback";\n grid-template-columns: auto minmax(0, 1fr);\n grid-template-rows: auto 1fr;\n align-items: start;\n ',[l("form-item-label","\n display: grid;\n grid-template-columns: 1fr auto;\n min-height: var(--n-blank-height);\n height: auto;\n box-sizing: border-box;\n flex-shrink: 0;\n flex-grow: 0;\n ",[s("left-mark",'\n grid-template-areas:\n "mark text"\n ". text";\n '),s("right-mark",'\n grid-template-areas: \n "text mark"\n "text .";\n '),s("right-hanging-mark",'\n grid-template-areas: \n "text mark"\n "text .";\n '),x("text","\n grid-area: text; \n "),x("asterisk","\n grid-area: mark; \n align-self: end;\n ")])]),s("top-labelled",'\n grid-template-areas:\n "label"\n "blank"\n "feedback";\n grid-template-rows: minmax(var(--n-label-height), auto) 1fr;\n grid-template-columns: minmax(0, 100%);\n ',[s("no-label",'\n grid-template-areas:\n "blank"\n "feedback";\n grid-template-rows: 1fr;\n '),l("form-item-label","\n display: flex;\n align-items: flex-start;\n justify-content: var(--n-label-text-align);\n ")]),l("form-item-blank","\n box-sizing: border-box;\n display: flex;\n align-items: center;\n position: relative;\n "),l("form-item-feedback-wrapper","\n grid-area: feedback;\n box-sizing: border-box;\n min-height: var(--n-feedback-height);\n font-size: var(--n-feedback-font-size);\n line-height: 1.25;\n transform-origin: top left;\n ",[u("&:not(:empty)","\n padding: var(--n-feedback-padding);\n "),l("form-item-feedback",{transition:"color .3s var(--n-bezier)",color:"var(--n-feedback-text-color)"},[s("warning",{color:"var(--n-feedback-text-color-warning)"}),s("error",{color:"var(--n-feedback-text-color-error)"}),function({name:e="fade-down",fromOffset:r="-4px",enterDuration:t=".3s",leaveDuration:n=".3s",enterCubicBezier:i=ce,leaveCubicBezier:a=ce}={}){return[u(`&.${e}-transition-enter-from, &.${e}-transition-leave-to`,{opacity:0,transform:`translateY(${r})`}),u(`&.${e}-transition-enter-to, &.${e}-transition-leave-from`,{opacity:1,transform:"translateY(0)"}),u(`&.${e}-transition-leave-active`,{transition:`opacity ${n} ${a}, transform ${n} ${a}`}),u(`&.${e}-transition-enter-active`,{transition:`opacity ${t} ${i}, transform ${t} ${i}`})]}({fromOffset:"-3px",enterDuration:".3s",leaveDuration:".2s"})])])]);var me=globalThis&&globalThis.__awaiter||function(e,r,t,n){return new(t||(t=Promise))((function(i,a){function o(e){try{s(n.next(e))}catch(r){a(r)}}function l(e){try{s(n.throw(e))}catch(r){a(r)}}function s(e){var r;e.done?i(e.value):(r=e.value,r instanceof t?r:new t((function(e){e(r)}))).then(o,l)}s((n=n.apply(e,r||[])).next())}))};const ge=Object.assign(Object.assign({},p.props),{label:String,labelWidth:[Number,String],labelStyle:[String,Object],labelAlign:String,labelPlacement:String,path:String,first:Boolean,rulePath:String,required:Boolean,showRequireMark:{type:Boolean,default:void 0},requireMarkPlacement:String,showFeedback:{type:Boolean,default:void 0},rule:[Object,Array],size:String,ignorePathChange:Boolean,validationStatus:String,feedback:String,showLabel:{type:Boolean,default:void 0},labelProps:Object});function he(e,r){return(...t)=>{try{const n=e(...t);return!r&&("boolean"==typeof n||n instanceof Error||Array.isArray(n))||(null==n?void 0:n.then)?n:(void 0===n||j("form-item/validate",`You return a ${typeof n} typed value in the validator method, which is not recommended. Please use `+(r?"`Promise`":"`boolean`, `Error` or `Promise`")+" typed value instead."),!0)}catch(n){return j("form-item/validate","An error is catched in the validation, so the validation won't be done. Your callback in `validate` method of `n-form` or `n-form-item` won't be called in this validation."),void console.error(n)}}}e("_",f({name:"FormItem",props:ge,setup(e){r(M,"formItems",w(e,"path"));const{mergedClsPrefixRef:n,inlineThemeDisabled:a}=c(e),o=t(C,null),l=function(e){const r=t(C,null);return{mergedSize:v((()=>void 0!==e.size?e.size:void 0!==(null==r?void 0:r.props.size)?r.props.size:"medium"))}}(e),s=function(e){const r=t(C,null),n=v((()=>{const{labelPlacement:t}=e;return void 0!==t?t:(null==r?void 0:r.props.labelPlacement)?r.props.labelPlacement:"top"})),i=v((()=>"left"===n.value&&("auto"===e.labelWidth||"auto"===(null==r?void 0:r.props.labelWidth)))),a=v((()=>{if("top"===n.value)return;const{labelWidth:t}=e;if(void 0!==t&&"auto"!==t)return A(t);if(i.value){const e=null==r?void 0:r.maxChildLabelWidthRef.value;return void 0!==e?A(e):void 0}return void 0!==(null==r?void 0:r.props.labelWidth)?A(r.props.labelWidth):void 0})),o=v((()=>{const{labelAlign:t}=e;return t||((null==r?void 0:r.props.labelAlign)?r.props.labelAlign:void 0)})),l=v((()=>{var r;return[null===(r=e.labelProps)||void 0===r?void 0:r.style,e.labelStyle,{width:a.value}]})),s=v((()=>{const{showRequireMark:t}=e;return void 0!==t?t:null==r?void 0:r.props.showRequireMark})),u=v((()=>{const{requireMarkPlacement:t}=e;return void 0!==t?t:(null==r?void 0:r.props.requireMarkPlacement)||"right"})),d=m(!1),f=v((()=>{const{validationStatus:r}=e;return void 0!==r?r:d.value?"error":void 0})),c=v((()=>{const{showFeedback:t}=e;return void 0!==t?t:void 0===(null==r?void 0:r.props.showFeedback)||r.props.showFeedback})),p=v((()=>{const{showLabel:t}=e;return void 0!==t?t:void 0===(null==r?void 0:r.props.showLabel)||r.props.showLabel}));return{validationErrored:d,mergedLabelStyle:l,mergedLabelPlacement:n,mergedLabelAlign:o,mergedShowRequireMark:s,mergedRequireMarkPlacement:u,mergedValidationStatus:f,mergedShowFeedback:c,mergedShowLabel:p,isAutoLabelWidth:i}}(e),{validationErrored:u}=s,{mergedRequired:d,mergedRules:f}=function(e){const r=t(C,null),n=v((()=>{const{rulePath:r}=e;if(void 0!==r)return r;const{path:t}=e;return void 0!==t?t:void 0})),i=v((()=>{const t=[],{rule:i}=e;if(void 0!==i&&(Array.isArray(i)?t.push(...i):t.push(i)),r){const{rules:e}=r.props,{value:i}=n;if(void 0!==e&&void 0!==i){const r=z(e,i);void 0!==r&&(Array.isArray(r)?t.push(...r):t.push(r))}}return t})),a=v((()=>i.value.some((e=>e.required)))),o=v((()=>a.value||e.required));return{mergedRules:i,mergedRequired:o}}(e),{mergedSize:h}=l,{mergedLabelPlacement:b,mergedLabelAlign:y}=s,x=m([]),S=m(k()),P=o?w(o.props,"disabled"):m(!1),j=p("Form","-form-item",pe,E,e,n);function _(){x.value=[],u.value=!1,e.feedback&&(S.value=k())}i(w(e,"path"),(()=>{e.ignorePathChange||_()}));const R=(r=null,t=(()=>!0),n={suppressWarning:!0})=>me(this,void 0,void 0,(function*(){const{path:i}=e;n?n.first||(n.first=e.first):n={};const{value:a}=f,l=o?z(o.props.model,i||""):void 0,s={},d={},c=(r?a.filter((e=>Array.isArray(e.trigger)?e.trigger.includes(r):e.trigger===r)):a).filter(t).map(((e,r)=>{const t=Object.assign({},e);if(t.validator&&(t.validator=he(t.validator,!1)),t.asyncValidator&&(t.asyncValidator=he(t.asyncValidator,!0)),t.renderMessage){const e=`__renderMessage__${r}`;d[e]=t.message,t.message=e,s[e]=t.renderMessage}return t}));if(!c.length)return{valid:!0};const p=null!=i?i:"__n_no_path__",m=new fe({[p]:c}),{validateMessages:g}=(null==o?void 0:o.props)||{};return g&&m.messages(g),yield new Promise((e=>{m.validate({[p]:l},n,(r=>{(null==r?void 0:r.length)?(x.value=r.map((e=>{const r=(null==e?void 0:e.message)||"";return{key:r,render:()=>r.startsWith("__renderMessage__")?s[r]():r}})),r.forEach((e=>{var r;(null===(r=e.message)||void 0===r?void 0:r.startsWith("__renderMessage__"))&&(e.message=d[e.message])})),u.value=!0,e({valid:!1,errors:r})):(_(),e({valid:!0}))}))}))}));g(q,{path:w(e,"path"),disabled:P,mergedSize:l.mergedSize,mergedValidationStatus:s.mergedValidationStatus,restoreValidation:_,handleContentBlur:function(){R("blur")},handleContentChange:function(){R("change")},handleContentFocus:function(){R("focus")},handleContentInput:function(){R("input")}});const L={validate:function(e,r){return me(this,void 0,void 0,(function*(){let t,n,i,a;return"string"==typeof e?(t=e,n=r):null!==e&&"object"==typeof e&&(t=e.trigger,n=e.callback,i=e.shouldRuleBeApplied,a=e.options),yield new Promise(((e,r)=>{R(t,i,a).then((({valid:t,errors:i})=>{t?(n&&n(),e()):(n&&n(i),r(i))}))}))}))},restoreValidation:_,internalValidate:R},T=m(null);F((()=>{if(!s.isAutoLabelWidth.value)return;const e=T.value;if(null!==e){const r=e.style.whiteSpace;e.style.whiteSpace="nowrap",e.style.width="",null==o||o.deriveMaxChildLabelWidth(Number(getComputedStyle(e).width.slice(0,-2))),e.style.whiteSpace=r}}));const V=v((()=>{var e;const{value:r}=h,{value:t}=b,n="top"===t?"vertical":"horizontal",{common:{cubicBezierEaseInOut:i},self:{labelTextColor:a,asteriskColor:o,lineHeight:l,feedbackTextColor:s,feedbackTextColorWarning:u,feedbackTextColorError:d,feedbackPadding:f,[$("labelHeight",r)]:c,[$("blankHeight",r)]:p,[$("feedbackFontSize",r)]:m,[$("feedbackHeight",r)]:g,[$("labelPadding",n)]:v,[$("labelTextAlign",n)]:x,[$($("labelFontSize",t),r)]:w}}=j.value;let k=null!==(e=y.value)&&void 0!==e?e:x;return"top"===t&&(k="right"===k?"flex-end":"flex-start"),{"--n-bezier":i,"--n-line-height":l,"--n-blank-height":p,"--n-label-font-size":w,"--n-label-text-align":k,"--n-label-height":c,"--n-label-padding":v,"--n-asterisk-color":o,"--n-label-text-color":a,"--n-feedback-padding":f,"--n-feedback-font-size":m,"--n-feedback-height":g,"--n-feedback-text-color":s,"--n-feedback-text-color-warning":u,"--n-feedback-text-color-error":d}})),B=O("form-item",v((()=>{var e;return`${h.value[0]}${b.value[0]}${(null===(e=y.value)||void 0===e?void 0:e[0])||""}`})),V,e);return Object.assign(Object.assign(Object.assign(Object.assign({labelElementRef:T,mergedClsPrefix:n,mergedRequired:d,feedbackId:S,renderExplains:x},s),l),L),{cssVars:a?void 0:V,themeClass:null==B?void 0:B.themeClass,onRender:null==B?void 0:B.onRender})},render(){const{$slots:e,mergedClsPrefix:r,mergedShowLabel:t,mergedShowRequireMark:n,mergedRequireMarkPlacement:i,onRender:a}=this,o=void 0!==n?n:this.mergedRequired;return null==a||a(),h("div",{class:[`${r}-form-item`,this.themeClass,`${r}-form-item--${this.mergedSize}-size`,`${r}-form-item--${this.mergedLabelPlacement}-labelled`,this.isAutoLabelWidth&&`${r}-form-item--auto-label-width`,!t&&`${r}-form-item--no-label`],style:this.cssVars},t&&(()=>{const e=this.$slots.label?this.$slots.label():this.label;if(!e)return null;const t=h("span",{class:`${r}-form-item-label__text`},e),n=o?h("span",{class:`${r}-form-item-label__asterisk`},"left"!==i?" *":"* "):"right-hanging"===i&&h("span",{class:`${r}-form-item-label__asterisk-placeholder`}," *"),{labelProps:a}=this;return h("label",Object.assign({},a,{class:[null==a?void 0:a.class,`${r}-form-item-label`,`${r}-form-item-label--${i}-mark`],style:this.mergedLabelStyle,ref:"labelElementRef"}),"left"===i?[n,t]:[t,n])})(),h("div",{class:[`${r}-form-item-blank`,this.mergedValidationStatus&&`${r}-form-item-blank--${this.mergedValidationStatus}`]},e),this.mergedShowFeedback?h("div",{key:this.feedbackId,class:`${r}-form-item-feedback-wrapper`},h(S,{name:"fade-down-transition",mode:"out-in"},{default:()=>{const{mergedValidationStatus:t}=this;return P(e.feedback,(e=>{var n;const{feedback:i}=this,a=e||i?h("div",{key:"__feedback__",class:`${r}-form-item-feedback__line`},e||i):this.renderExplains.length?null===(n=this.renderExplains)||void 0===n?void 0:n.map((({key:e,render:t})=>h("div",{key:e,class:`${r}-form-item-feedback__line`},t()))):null;return a?h("div","warning"===t?{key:"controlled-warning",class:`${r}-form-item-feedback ${r}-form-item-feedback--warning`}:"error"===t?{key:"controlled-error",class:`${r}-form-item-feedback ${r}-form-item-feedback--error`}:"success"===t?{key:"controlled-success",class:`${r}-form-item-feedback ${r}-form-item-feedback--success`}:{key:"controlled-default",class:`${r}-form-item-feedback`},a):null}))}})):null)}}))}}}));