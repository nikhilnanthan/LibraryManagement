"use strict"
define("ember-content/app",["exports","ember-content/resolver","ember-load-initializers","ember-content/config/environment"],function(e,t,n,a){Object.defineProperty(e,"__esModule",{value:!0})
var r=Ember.Application.extend({modulePrefix:a.default.modulePrefix,podModulePrefix:a.default.podModulePrefix,Resolver:t.default});(0,n.default)(r,a.default.modulePrefix),e.default=r}),define("ember-content/helpers/app-version",["exports","ember-content/config/environment","ember-cli-app-version/utils/regexp"],function(e,t,n){function a(e){var a=arguments.length>1&&void 0!==arguments[1]?arguments[1]:{},r=t.default.APP.version,l=a.versionOnly||a.hideSha,i=a.shaOnly||a.hideVersion,o=null
return l&&(a.showExtended&&(o=r.match(n.versionExtendedRegExp)),o||(o=r.match(n.versionRegExp))),i&&(o=r.match(n.shaRegExp)),o?o[0]:r}Object.defineProperty(e,"__esModule",{value:!0}),e.appVersion=a,e.default=Ember.Helper.helper(a)}),define("ember-content/helpers/pluralize",["exports","ember-inflector/lib/helpers/pluralize"],function(e,t){Object.defineProperty(e,"__esModule",{value:!0}),e.default=t.default}),define("ember-content/helpers/singularize",["exports","ember-inflector/lib/helpers/singularize"],function(e,t){Object.defineProperty(e,"__esModule",{value:!0}),e.default=t.default}),define("ember-content/initializers/app-version",["exports","ember-cli-app-version/initializer-factory","ember-content/config/environment"],function(e,t,n){Object.defineProperty(e,"__esModule",{value:!0})
var a=void 0,r=void 0
n.default.APP&&(a=n.default.APP.name,r=n.default.APP.version),e.default={name:"App Version",initialize:(0,t.default)(a,r)}}),define("ember-content/initializers/container-debug-adapter",["exports","ember-resolver/resolvers/classic/container-debug-adapter"],function(e,t){Object.defineProperty(e,"__esModule",{value:!0}),e.default={name:"container-debug-adapter",initialize:function(){var e=arguments[1]||arguments[0]
e.register("container-debug-adapter:main",t.default),e.inject("container-debug-adapter:main","namespace","application:main")}}}),define("ember-content/initializers/data-adapter",["exports"],function(e){Object.defineProperty(e,"__esModule",{value:!0}),e.default={name:"data-adapter",before:"store",initialize:function(){}}}),define("ember-content/initializers/ember-data",["exports","ember-data/setup-container","ember-data"],function(e,t){Object.defineProperty(e,"__esModule",{value:!0}),e.default={name:"ember-data",initialize:t.default}}),define("ember-content/initializers/export-application-global",["exports","ember-content/config/environment"],function(e,t){function n(){var e=arguments[1]||arguments[0]
if(!1!==t.default.exportApplicationGlobal){var n
if("undefined"!=typeof window)n=window
else if("undefined"!=typeof global)n=global
else{if("undefined"==typeof self)return
n=self}var a,r=t.default.exportApplicationGlobal
a="string"==typeof r?r:Ember.String.classify(t.default.modulePrefix),n[a]||(n[a]=e,e.reopen({willDestroy:function(){this._super.apply(this,arguments),delete n[a]}}))}}Object.defineProperty(e,"__esModule",{value:!0}),e.initialize=n,e.default={name:"export-application-global",initialize:n}}),define("ember-content/initializers/injectStore",["exports"],function(e){Object.defineProperty(e,"__esModule",{value:!0}),e.default={name:"injectStore",before:"store",initialize:function(){}}}),define("ember-content/initializers/store",["exports"],function(e){Object.defineProperty(e,"__esModule",{value:!0}),e.default={name:"store",after:"ember-data",initialize:function(){}}}),define("ember-content/initializers/transforms",["exports"],function(e){Object.defineProperty(e,"__esModule",{value:!0}),e.default={name:"transforms",before:"store",initialize:function(){}}}),define("ember-content/instance-initializers/ember-data",["exports","ember-data/instance-initializers/initialize-store-service"],function(e,t){Object.defineProperty(e,"__esModule",{value:!0}),e.default={name:"ember-data",initialize:t.default}}),define("ember-content/resolver",["exports","ember-resolver"],function(e,t){Object.defineProperty(e,"__esModule",{value:!0}),e.default=t.default}),define("ember-content/router",["exports","ember-content/config/environment"],function(e,t){Object.defineProperty(e,"__esModule",{value:!0})
var n=Ember.Router.extend({location:t.default.locationType,rootURL:t.default.rootURL})
n.map(function(){this.route("sign-in"),this.route("sign-up")}),e.default=n}),define("ember-content/routes/sign-in",["exports"],function(e){Object.defineProperty(e,"__esModule",{value:!0}),e.default=Ember.Route.extend({model:function(){return"hi"}})}),define("ember-content/routes/sign-up",["exports"],function(e){Object.defineProperty(e,"__esModule",{value:!0}),e.default=Ember.Route.extend({})}),define("ember-content/services/ajax",["exports","ember-ajax/services/ajax"],function(e,t){Object.defineProperty(e,"__esModule",{value:!0}),Object.defineProperty(e,"default",{enumerable:!0,get:function(){return t.default}})}),define("ember-content/templates/application",["exports"],function(e){Object.defineProperty(e,"__esModule",{value:!0}),e.default=Ember.HTMLBars.template({id:"iIN0e0PB",block:'{"statements":[[1,[26,["outlet"]],false]],"locals":[],"named":[],"yields":[],"hasPartials":false}',meta:{moduleName:"ember-content/templates/application.hbs"}})}),define("ember-content/templates/sign-in",["exports"],function(e){Object.defineProperty(e,"__esModule",{value:!0}),e.default=Ember.HTMLBars.template({id:"FSPgeWWV",block:'{"statements":[[11,"div",[]],[15,"class","sign-in"],[13],[0,"\\n\\t"],[11,"form",[]],[15,"class","form-container"],[15,"method","get"],[15,"action","/user"],[13],[0,"\\n\\t\\t\\n\\t\\t"],[1,[33,["input"],null,[["type","class","name","placeholder"],["text","form-element","emailId","Email-Id"]]],false],[0," "],[11,"br",[]],[13],[14],[0,"\\n\\t\\t\\n\\t\\t"],[1,[33,["input"],null,[["type","class","name","placeholder"],["password","form-element","password","Password"]]],false],[0," "],[11,"br",[]],[13],[14],[0,"\\n\\n\\t\\t"],[1,[33,["input"],null,[["type","class","name","placeholder"],["text","form-element","organisation","Organisation"]]],false],[11,"br",[]],[13],[14],[0,"\\n\\t\\t"],[11,"select",[]],[15,"class","form-element"],[15,"id","role"],[13],[0,"\\n\\t\\t\\t"],[11,"option",[]],[15,"disabled",""],[15,"selected",""],[13],[0,"Role..."],[14],[0,"\\n\\t\\t\\t"],[11,"option",[]],[15,"class","form-element"],[15,"value","User"],[13],[0,"User"],[14],[0,"\\n\\t\\t\\t"],[11,"option",[]],[15,"class","form-element"],[15,"value","Publisher"],[13],[0,"Publisher"],[14],[0,"\\n\\t\\t\\t"],[11,"option",[]],[15,"class","form-element"],[15,"value","Staff"],[13],[0,"Staff"],[14],[0,"\\n\\t\\t"],[14],[0,"\\n\\t\\t"],[11,"br",[]],[13],[14],[0,"\\n\\t\\t"],[11,"button",[]],[15,"type","submit"],[13],[0,"Login"],[14],[11,"br",[]],[13],[14],[0,"\\n\\t\\t"],[11,"div",[]],[13],[0,"\\n\\t\\t\\tDon\'t have an account? "],[6,["link-to"],["sign-up"],null,{"statements":[[0,"Sign-Up"]],"locals":[]},null],[0,"\\n\\t\\t"],[14],[0,"\\n\\t"],[14],[0,"\\n"],[14]],"locals":[],"named":[],"yields":[],"hasPartials":false}',meta:{moduleName:"ember-content/templates/sign-in.hbs"}})}),define("ember-content/templates/sign-up",["exports"],function(e){Object.defineProperty(e,"__esModule",{value:!0}),e.default=Ember.HTMLBars.template({id:"aTW6Xwze",block:'{"statements":[[11,"div",[]],[15,"class","sign-up"],[13],[0,"\\n\\t"],[11,"form",[]],[15,"class","form-container"],[15,"method","post"],[15,"action","user"],[13],[0,"\\n\\t\\t"],[1,[33,["input"],null,[["type","class","name","placeholder"],["text","form-element","fname","First Name"]]],false],[11,"br",[]],[13],[14],[0,"\\n\\t\\t\\n\\t\\t"],[1,[33,["input"],null,[["type","class","name","placeholder"],["text","form-element","lname","Last Name"]]],false],[11,"br",[]],[13],[14],[0,"\\n\\t\\t\\n\\t\\t"],[1,[33,["input"],null,[["type","class","name","placeholder"],["text","form-element","emailId","Email-Id"]]],false],[11,"br",[]],[13],[14],[0,"\\n\\t\\t\\n\\t\\t"],[1,[33,["input"],null,[["type","class","name","placeholder"],["text","form-element","address","Address"]]],false],[11,"br",[]],[13],[14],[0,"\\n\\t\\t\\n\\t\\t"],[1,[33,["input"],null,[["type","class","name","placeholder"],["number","form-element","age","Age"]]],false],[11,"br",[]],[13],[14],[0,"\\n\\n\\t\\t"],[1,[33,["input"],null,[["type","class","name","placeholder"],["tel","form-element","phoneno1","Phone Number"]]],false],[11,"br",[]],[13],[14],[0,"\\n\\t\\t\\n\\t\\t"],[1,[33,["input"],null,[["type","class","name","placeholder"],["tel","form-element","phoneno2","Phone Number"]]],false],[11,"br",[]],[13],[14],[0,"\\n\\t\\t\\n\\t\\t"],[1,[33,["input"],null,[["type","class","name","placeholder"],["text","form-element","password","Password"]]],false],[11,"br",[]],[13],[14],[0,"\\n\\t\\n\\t\\t"],[1,[33,["input"],null,[["type","class","name","placeholder"],["password","form-element","con-password","Confirm Password"]]],false],[11,"br",[]],[13],[14],[0,"\\n\\n\\t\\t"],[1,[33,["input"],null,[["type","class","name","placeholder"],["text","form-element","organisation","Organisation"]]],false],[11,"br",[]],[13],[14],[0,"\\n\\t\\t"],[11,"select",[]],[15,"class","form-element"],[15,"id","role"],[13],[0,"\\n\\t\\t\\t"],[11,"option",[]],[15,"disabled",""],[15,"selected",""],[13],[0,"Role..."],[14],[0,"\\n\\t\\t\\t"],[11,"option",[]],[15,"class","form-element"],[15,"value","User"],[13],[0,"User"],[14],[0,"\\n\\t\\t\\t"],[11,"option",[]],[15,"class","form-element"],[15,"value","Publisher"],[13],[0,"Publisher"],[14],[0,"\\n\\t\\t\\t"],[11,"option",[]],[15,"class","form-element"],[15,"value","Staff"],[13],[0,"Staff"],[14],[0,"\\n\\t\\t"],[14],[0,"\\n\\t\\t"],[11,"button",[]],[15,"type","submit"],[13],[0,"Login"],[14],[11,"br",[]],[13],[14],[0,"\\n\\t\\t"],[11,"div",[]],[13],[0,"\\n\\t\\t\\tAlready have an account?"],[6,["link-to"],["sign-in"],null,{"statements":[[0,"Sign-in"]],"locals":[]},null],[0,"\\n\\t\\t"],[14],[0,"\\n\\t"],[14],[0,"\\n"],[14],[0,"\\n"]],"locals":[],"named":[],"yields":[],"hasPartials":false}',meta:{moduleName:"ember-content/templates/sign-up.hbs"}})}),define("ember-content/config/environment",["ember"],function(e){try{var t="ember-content/config/environment",n=document.querySelector('meta[name="'+t+'"]').getAttribute("content"),a=JSON.parse(unescape(n)),r={default:a}
return Object.defineProperty(r,"__esModule",{value:!0}),r}catch(e){throw new Error('Could not read config from meta tag with name "'+t+'".')}}),runningTests||require("ember-content/app").default.create({name:"ember-content",version:"0.0.0+d7d6bf56"})
