(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-175311ce"],{"4e7a":function(e,t,r){"use strict";r.d(t,"a",(function(){return o}));var a=r("c964"),s=(r("96cf"),r("b775")),n=r("7424");function o(e,t){return i.apply(this,arguments)}function i(){return i=Object(a["a"])(regeneratorRuntime.mark((function e(t,r){return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:return e.abrupt("return",Object(s["e"])(n["GET_MEETINGSTATE"],s["a"].GET,{meetingNumber:t,user_id:r}));case 1:case"end":return e.stop()}}),e)}))),i.apply(this,arguments)}},8946:function(e,t,r){e.exports=r.p+"static/img/joinMeeting.27d32567.png"},f77a:function(e,t,r){"use strict";r.r(t);var a=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",[a("div",[a("a-card",{staticStyle:{padding:"0",margin:"0 auto","min-height":"490px"},attrs:{bordered:!1}},[a("img",{staticStyle:{width:"25%","min-width":"150px",margin:"0 auto","margin-left":"34%"},attrs:{src:r("8946")}}),a("a-row",[a("a-col",{attrs:{span:24}},[a("a-form-model",{ref:"ruleForm",staticStyle:{"margin-left":"30%"},attrs:{model:e.form,rules:e.rules,"label-col":e.labelCol,"wrapper-col":e.wrapperCol},on:{submit:e.onSubmit}},[a("a-form-model-item",{ref:"Mnumber",attrs:{label:"会议号",prop:"Mnumber","label-col":{span:4,offset:-4},"wrapper-col":{span:7}}},[a("a-input",{staticStyle:{width:"150px"},attrs:{placeholder:"输入会议号"},model:{value:e.form.Mnumber,callback:function(t){e.$set(e.form,"Mnumber",t)},expression:"form.Mnumber"}})],1),a("a-form-model-item",{attrs:{"wrapper-col":{span:14,offset:4}}},[a("a-button",{attrs:{loading:e.enterloading,type:"primary",htmlType:"submit"}},[e._v(" 加入 ")]),a("a-button",{staticStyle:{"margin-left":"10px"},on:{click:e.resetForm}},[e._v(" 重置 ")])],1)],1)],1)],1)],1)],1),a("div",[a("a-modal",{ref:"attention",attrs:{title:"提醒",visible:e.attention_visible,footer:null},on:{cancel:e.handleCancel}},[a("p",[e._v(e._s(e.ModalText))]),this.needpassword?a("a-form-model",{ref:"ruleForm",attrs:{model:e.form,rules:e.rules,"label-col":e.labelCol,"wrapper-col":e.wrapperCol}},[a("a-form-model-item",{ref:"Mpassword",attrs:{prop:"Mpassword"}},[a("a-input",{attrs:{placeholder:"输入密码",type:"password","addon-before":"密码"},model:{value:e.form.Mpassword,callback:function(t){e.$set(e.form,"Mpassword",t)},expression:"form.Mpassword"}})],1)],1):e._e(),a("a-button",{key:"check",staticStyle:{"margin-right":"10px"},attrs:{type:"primary"},on:{click:e.handleOk}},[e._v(" 提前加入会议 ")]),a("a-button",{key:"reReserve",staticStyle:{"margin-right":"10px"},attrs:{type:"primary"},on:{click:e.handleAdd}},[e._v(" 添至我的会议 ")]),a("a-button",{on:{click:e.handleCancel}},[e._v("取消")])],1)],1),a("div",[a("a-modal",{ref:"needpassword",attrs:{title:"请输入密码",visible:e.needpassword_visible,footer:null},on:{ok:e.handleOk2,cancel:e.handleCancel}},[a("p",[e._v("会议正在进行中,请输入密码加入会议")]),a("a-form-model",{ref:"ruleForm",attrs:{model:e.form,rules:e.rules,"label-col":e.labelCol,"wrapper-col":e.wrapperCol}},[a("a-form-model-item",{ref:"Mpassword",attrs:{prop:"Mpassword"}},[a("a-input",{attrs:{placeholder:"输入密码",type:"password","addon-before":"密码"},model:{value:e.form.Mpassword,callback:function(t){e.$set(e.form,"Mpassword",t)},expression:"form.Mpassword"}})],1)],1),a("a-button",{key:"check",staticStyle:{"margin-right":"10px"},attrs:{type:"primary"},on:{click:e.handleOk2}},[e._v(" 加入会议 ")]),a("a-button",{on:{click:e.handleCancel}},[e._v("取消")])],1)],1)])},s=[],n=r("f3f3"),o=r("4e7a"),i=r("c964"),d=(r("96cf"),r("b775")),l=r("7424"),u=r("4328"),m=r.n(u);function c(e,t){return p.apply(this,arguments)}function p(){return p=Object(i["a"])(regeneratorRuntime.mark((function e(t,r){return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:return e.abrupt("return",Object(d["e"])(l["CONFIRM_MEETINGPASSWORD"],d["a"].GET,{meetingNumber:t,meetingPassword:r}));case 1:case"end":return e.stop()}}),e)}))),p.apply(this,arguments)}function f(e,t,r){return h.apply(this,arguments)}function h(){return h=Object(i["a"])(regeneratorRuntime.mark((function e(t,r,a){return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:return e.abrupt("return",Object(d["e"])(l["ADD_MEETING"],d["a"].PUT,m.a.stringify({meetingNumber:t,requirePassword:r,meetingPassword:a}),{headers:{"Content-Type":"application/x-www-form-urlencoded"}}));case 1:case"end":return e.stop()}}),e)}))),h.apply(this,arguments)}var w=r("5880"),b={data:function(){return{ModalText:"会议尚未开始，【提前进入会议】还是【加入至预约的会议】？",attention_visible:!1,needpassword_visible:!1,confirmLoading:!1,enterloading:!1,labelCol:{span:4},wrapperCol:{span:14},other:"",needpassword:!1,form:{Mnumber:"",Mpassword:"",name:"",region:void 0,date1:void 0,delivery:!1,type:[],resource:"",desc:""},rules:{Mnumber:[{required:!0,message:"请输入会议号",trigger:"blur"}],Mpassword:[{required:!1,message:"请输入密码",trigger:"blur"}],name:[{required:!1,message:"请输入昵称，默认为用户名",trigger:"blur"}]}}},computed:Object(n["a"])({},Object(w["mapGetters"])("account",["username","userId"])),methods:{showModal1:function(){this.attention_visible=!0,this.form.Mpassword=""},showModal2:function(){this.needpassword_visible=!0,this.form.Mpassword=""},handleOk:function(){var e=this;this.needpassword?c(this.form.Mnumber,this.form.Massword).then((function(t){var r=e.form.Mnumber;0==t.data.data.confirmResult?(e.form.Mpassword="",e.$message.error("密码错误,请重新输入!")):1==t.data.data.confirmResult&&(e.$message.success("密码正确，成功加入会议："+r+"！"),e.resetForm(),e.attention_visible=!1,e.needpassword=!1,e.enterMeeting())})):this.enterMeeting()},handleAdd:function(){var e=this;this.needpassword?f(this.form.Mnumber,this.needpassword,this.form.Mpassword).then((function(t){1==t.data.data.confirmResult?(e.attention_visible=!1,e.needpassword_visible=!1,e.resetForm(),e.$message.success("已添加会议，可前往【我的会议】查看！")):(e.form.Mpassword="",e.$message.error("会议密码错误，请重新输入！"))})):f(this.form.Mnumber,this.needpassword,this.form.Mpassword).then((function(t){if(1==t.data.data.confirmResult){var r=e.form.Mnumber;e.attention_visible=!1,e.needpassword_visible=!1,e.resetForm(),e.$message.success("已添加会议 "+r+" ，可前往【我的会议】查看！")}}))},handleCancel:function(){this.attention_visible=!1,this.needpassword_visible=!1,this.resetForm()},handleOk2:function(){var e=this;c(this.form.Mnumber,this.form.Mpassword).then((function(t){var r=e.form.Mnumber;0==t.data.data.confirmResult?(e.form.Mpassword="",e.$message.error("密码错误,请重新输入!")):1==t.data.data.confirmResult&&(e.$message.success("密码正确，成功加入会议："+r+"！"),e.resetForm(),e.attention_visible=!1,e.needpassword_visible=!1,e.needpassword=!1,e.enterMeeting())}))},onSubmit:function(e){var t=this;e.preventDefault(),this.$refs.ruleForm.validate((function(e){if(!e)return t.enterloading=!1,!1;t.enterloading=!0;var r=t.form.Mnumber;Object(o["a"])(r,t.userId).then(t.confirmMeetingState),t.enterloading=!1}))},confirmMeetingPassword:function(e){var t=this.form.Mnumber;!1===e.data.data.confirmResult?this.$message.error("密码错误"):!0===e.data.data.confirmResult&&(this.needpassword=!1,this.enterMeeting(),this.$message.success("成功加入会议，会议号为："+t+"！"))},confirmMeetingState:function(e){501===e.data.code?(this.$message.error("会议不存在，请核对会议号！"),this.enterloading=!1):100===e.data.data.status?(this.enterloading=!1,this.needpassword=e.data.data.needpassword,this.showModal1()):200===e.data.data.status?(this.needpassword=e.data.data.needpassword,1==this.needpassword?this.showModal2():0==this.needpassword&&this.enterMeeting()):300===e.data.data.status?(this.enterloading=!1,this.$message.error("会议已经结束，无法加入！")):400===e.data.data.status&&(this.enterloading=!1,this.$message.error("没有权限，无法加入会议"))},enterMeeting:function(){var e=this.form.Mnumber,t=this.$router.resolve({path:"/meetingOn",query:{meetingNumber:e}});window.open(t.href,"_blank"),this.$message.success("成功加入会议，会议号为："+e+"！")},resetForm:function(){this.$refs.ruleForm.resetFields()}}},g=b,M=r("2877"),v=Object(M["a"])(g,a,s,!1,null,null,null);t["default"]=v.exports}}]);