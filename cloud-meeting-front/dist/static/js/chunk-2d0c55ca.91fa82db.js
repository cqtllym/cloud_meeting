(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-2d0c55ca"],{"3f89":function(e,t,r){"use strict";r.r(t);var n=function(){var e=this,t=e.$createElement,r=e._self._c||t;return r("a-card",{attrs:{"body-style":{padding:"24px 32px",paddingBottom:"64px"},bordered:!1}},["founder"==e.role||"admin"==e.role?r("a-tooltip",{attrs:{placement:"topLeft",title:"添加会议室","arrow-point-at-center":""}},[r("a-button",{attrs:{type:"primary",icon:"plus-circle"},on:{click:e.add}})],1):e._e(),r("a-row",{attrs:{gutter:16}},e._l(e.data,(function(e,t){return r("roomCom",{key:t,attrs:{"res-info":e}})})),1),r("a-modal",{attrs:{title:"更改会议室名字",visible:e.visible},on:{ok:e.handleOk,cancel:e.handleCancel}},[r("a-form",{attrs:{form:e.form}},[r("a-form-item",{ref:"meeting-name",attrs:{label:"更改会议室名字",prop:"meeting-name"}},[r("a-input",{directives:[{name:"decorator",rawName:"v-decorator",value:["meeting-name",{rules:[{required:!0,message:"名字不能为空！"}]}],expression:"[\n            'meeting-name',\n            {\n              rules: [\n                {\n                  required: true,\n                  message: '名字不能为空！',\n                },\n              ],\n            },\n          ]"}]})],1)],1)],1),r("a-modal",{attrs:{title:"添加会议室",visible:e.visableRoom},on:{ok:e.handleAdd,cancel:e.handleCancel}},[r("a-form",{attrs:{form:e.formRoom}},[r("a-form-item",{ref:"roomName",attrs:{label:"输入会议室名字",prop:"roomName"}},[r("a-input",{directives:[{name:"decorator",rawName:"v-decorator",value:["roomName",{rules:[{required:!0,message:"名字不能为空！"}]}],expression:"[\n            'roomName',\n            {\n              rules: [\n                {\n                  required: true,\n                  message: '名字不能为空！',\n                },\n              ],\n            },\n          ]"}]})],1)],1)],1)],1)},a=[],o=r("f3f3"),s=function(){var e=this,t=e.$createElement,r=e._self._c||t;return r("a-col",{attrs:{span:8}},[r("a-card",{staticStyle:{width:"300px","margin-top":"2%"},attrs:{hoverable:""}},[r("template",{staticClass:"ant-card-actions",slot:"actions"},[e.resInfo.status?e._e():r("a-tooltip",{attrs:{placement:"topLeft",title:"更改名称","arrow-point-at-center":""}},[r("a-icon",{key:"edit",attrs:{type:"edit"},on:{click:e.edit}})],1),"founder"!=e.role&&"admin"!=e.role||e.resInfo.isActive||e.resInfo.status?e._e():r("a-tooltip",{attrs:{placement:"topLeft",title:"启用","arrow-point-at-center":""},on:{click:function(t){return e.change(e.resInfo.id,!0)}}},[r("a-icon",{attrs:{type:"check-circle"}})],1),"founder"!=e.role&&"admin"!=e.role||!e.resInfo.isActive||e.resInfo.status?e._e():r("a-tooltip",{attrs:{placement:"topLeft",title:"停用","arrow-point-at-center":""},on:{click:function(t){return e.change(e.resInfo.id,!1)}}},[r("a-icon",{attrs:{type:"stop"}})],1),"founder"!=e.role&&"admin"!=e.role||e.resInfo.status?e._e():r("a-popconfirm",{attrs:{title:"你要删除这个会议室吗？","ok-text":"确认","cancel-text":"取消"},on:{confirm:function(t){return e.deleteRoom(e.resInfo.id)},cancel:e.cancel}},[r("a-tooltip",{attrs:{placement:"topLeft",title:"删除","arrow-point-at-center":""}},[r("a-icon",{attrs:{type:"close-circle"}})],1)],1),e.resInfo.status?r("a-tooltip",{attrs:{placement:"topLeft",title:"暂无可进行操作","arrow-point-at-center":""}},[r("a-icon",{attrs:{type:"bulb"}})],1):e._e()],1),r("a-card-meta",{attrs:{title:e.resInfo.name}}),e.resInfo.status?r("a-result",{attrs:{title:"正在进行会议","sub-title":"剩余时间："},scopedSlots:e._u([e.resInfo.status?{key:"icon",fn:function(){return[r("a-icon",{attrs:{type:"smile",theme:"twoTone"}})]},proxy:!0}:null],null,!0)}):e._e(),!e.resInfo.status&&e.resInfo.isActive?r("a-result",{attrs:{status:"success",title:"会议室空闲","sub-title":"可进行会议活动"}}):e._e(),e.resInfo.status||e.resInfo.isActive?e._e():r("a-result",{attrs:{status:"error",title:"会议室停用","sub-title":"管理员可启用"}})],2)],1)},i=[],c=r("5880"),u=r("c964"),l=(r("96cf"),r("7424")),m=r("b775"),d=r("4328"),p=r.n(d);function f(e,t){return h.apply(this,arguments)}function h(){return h=Object(u["a"])(regeneratorRuntime.mark((function e(t,r){return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:return e.abrupt("return",Object(m["e"])(l["ROOM_LIST"],m["a"].GET,{companyId:t,departmentId:r}));case 1:case"end":return e.stop()}}),e)}))),h.apply(this,arguments)}function b(e,t,r){return v.apply(this,arguments)}function v(){return v=Object(u["a"])(regeneratorRuntime.mark((function e(t,r,n){return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:return e.abrupt("return",Object(m["e"])(l["CREATE_ROOM"],m["a"].POST,p.a.stringify({companyId:t,departmentId:r,roomName:n}),{headers:{"Content-Type":"application/x-www-form-urlencoded"}}));case 1:case"end":return e.stop()}}),e)}))),v.apply(this,arguments)}function w(e){return y.apply(this,arguments)}function y(){return y=Object(u["a"])(regeneratorRuntime.mark((function e(t){return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:return e.abrupt("return",Object(m["e"])(l["DELETE_ROOM"],m["a"].PUT,p.a.stringify({roomId:t}),{headers:{"Content-Type":"application/x-www-form-urlencoded"}}));case 1:case"end":return e.stop()}}),e)}))),y.apply(this,arguments)}function g(e,t){return I.apply(this,arguments)}function I(){return I=Object(u["a"])(regeneratorRuntime.mark((function e(t,r){return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:return e.abrupt("return",Object(m["e"])(l["CHANGE_ROOM"],m["a"].PUT,p.a.stringify({roomId:t,isActive:r}),{headers:{"Content-Type":"application/x-www-form-urlencoded"}}));case 1:case"end":return e.stop()}}),e)}))),I.apply(this,arguments)}var O={name:"roomCom",props:{resInfo:{id:"",name:"",companyId:"",departmentId:"",status:!1,isActive:!1,deleted:!1}},data:function(){return{role:""}},computed:Object(o["a"])({},Object(c["mapGetters"])("account",["username","userId","companyId","roles"])),created:function(){this.role=this.roles[0].id},methods:{edit:function(){this.visible=!0},deleteRoom:function(e){var t=this;w(e).then((function(e){var r=e.data;if(200==r.code){t.$message.success("删除成功！");var n="/resourceManagement/room";t.$refreshPage(n)}}))},change:function(e,t){var r=this,n="";n=1==t?"启用会议室成功！":"停用会议室成功！",g(e,t).then((function(e){var t=e.data;if(200==t.code){r.$message.success(n);var a="/resourceManagement/room";r.$refreshPage(a)}}))}}},R=O,k=r("2877"),_=Object(k["a"])(R,s,i,!1,null,"5ad0f76c",null),x=_.exports,j={data:function(){return{isShow:!1,visible:!1,visableRoom:!1,data:[],role:""}},components:{roomCom:x},beforeCreate:function(){this.form=this.$form.createForm(this,"form"),this.formRoom=this.$form.createForm(this,"formRoom")},created:function(){this.showList(),this.role=this.roles[0].id},computed:Object(o["a"])({},Object(c["mapGetters"])("account",["username","userId","companyId","roles"])),methods:{showList:function(){var e=this;f(this.companyId).then((function(t){e.data=t.data.data}))},add:function(){this.visableRoom=!0},handleOk:function(){this.form.validateFieldsAndScroll((function(e,t){}))},handleAdd:function(){var e=this;this.formRoom.validateFieldsAndScroll((function(t){if(!t){var r="",n=e.formRoom.getFieldValue("roomName");b(e.companyId,r,n).then((function(t){var r=t.data;200==r.code&&(e.$message.success("新增会议室成功！"),e.visableRoom=!1,e.showList())}))}}))},handleCancel:function(){this.visible=!1,this.visableRoom=!1}}},C=j,A=Object(k["a"])(C,n,a,!1,null,"554cbf25",null);t["default"]=A.exports}}]);