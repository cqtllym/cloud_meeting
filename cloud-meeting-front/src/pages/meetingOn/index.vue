<template>
  <div class="content">
    <div class="chatroom">
      <div class="title">
        会议号:&nbsp; {{ meetingNumber }} ({{ meetingCount }})
      </div>
      <div class="fix"></div>
      <div class="chatbox" id="chat-box">
        <template v-for="(item, index) in msg">
          <!--                 item.username  -->
          <div style="text-align: right" :key="'1' + index"></div>
          <div :key="'2' + index" class="right" v-if="item.send">
            <span>{{ item.time }} &nbsp; {{ item.username }}</span>
          </div>
          <div :key="'3' + index" class="left" v-else>
            <span>{{ item.time }} &nbsp; {{ item.username }}</span>
          </div>
          <Msg
            :msg="item.msg"
            :send="item.send"
            :key="'4' + index"
            :username="item.username"
          />
        </template>
      </div>

      <div class="toolbar">
        <div>
          <template>
            <a-popover>
              <template slot="content">
                <div style="max-width: 500px">
                  <emoji-picker
                    class="emoji-width"
                    v-model="value"
                    style="background: #dddddd"
                    height="200px"
                  >
                  </emoji-picker>
                </div>
              </template>

              <a-button type="plain" style="border: 0px">
                <!--                                @mouseover="smile"-->
                <a-icon type="smile" />
              </a-button>
            </a-popover>
          </template>
        </div>

        <div>
          <!--                    后期实现的发送图片的功能-->
          <!--                    <a-icon type="folder-open" @click="upload" />-->
          <input id="upload" accept type="file" style="display: none" />
        </div>
      </div>
      <div class="messagebox">
        <a-textarea v-model="value" @keydown.enter="send"></a-textarea>
        <div class="send-btn">
          <a-button size="small" @click="send" type="primary"
            >发&nbsp; 送</a-button
          >
        </div>
      </div>
      <div class="userlist">
        <div class="list-title">成员列表</div>
        <div class="list">
          <template v-for="item in userlist">
            <User :name="item.username" :key="item.userId" :isHost="item.userId==hostId"/>
          </template>
        </div>
      </div>
      <div class="download" style="bottom: 9px">
        <div class="download-btn">
          <a-button size="small" @click="handleDownload">导出成员列表</a-button>
        </div>
      </div>
      <div class="download div2">
        <div class="download-btn">
          <template>
            <div id="components-a-popconfirm-demo-placement">
              <a-popconfirm
                placement="topRight"
                ok-text="退出"
                cancel-text="取消"
                @confirm="confirm"
              >
                <template slot="title">
                  <p>{{ text }}</p>
                </template>
                <a-button size="small" type="danger">退出会议</a-button>
              </a-popconfirm>
            </div>
          </template>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { message } from "ant-design-vue";
import Msg from "./chat/Msg.vue";
import User from "./chat/User.vue";
import Vue from "vue";
import { mapGetters } from "vuex";
import { getM } from "@/services/getmeetingstate";
// 引入安装包和样式
import WeixinEmojis from "vue-weixin-emojis";
import "vue-weixin-emojis/dist/vue-weixin-emojis.css";
Vue.use(WeixinEmojis);
import "@/Excel/Blob.js";

const TYPE_MSG = 1;
const TYPE_USERLIST = 2;
var userId = '';


export default {
  name: "App",
  components: {
    Msg,
    User,
  },
  data: function () {
    return {
      emoji: false,
      value: "",
      meetingCount: "",
      mettingNumber: "",
      msg: [],
      meetingNumber: "",
      userlist: [],
      buttonWidth: 70,
      text: "您确定要退出会议吗",
      downloadLoading: null,
      hostId: null
    };
  },
  created() {
    this.meetingNumber = this.$route.query.meetingNumber;
    getM(this.meetingNumber, this.userId).then(res => {
      if (res.data.code === 501) {//会议不存在
        this.$notification['error']({
          message: '会议号不存在！',
          description:
            '会议号不存在，无法加入会议，请核对会议号！',
          duration: null
        });
        this.meetingNumber = ''
      } else {
        if (res.data.data.status === 300) {
          this.$notification['error']({
            message: '会议已经结束！',
            description:
              '会议已经结束，无法加入会议！',
            duration: null
          });
          this.meetingNumber = ''

        } else if (res.data.data.status === 400) {
          this.$notification['error']({
            message: '无入会权限！',
            description:
              '无入会权限，无法加入会议！',
            duration: null
          });
          this.meetingNumber = ''
        } else {
          this.hostId = res.data.data.hostId
          this.msg = JSON.parse(sessionStorage.getItem("msg" + this.meetingNumber)) ? JSON.parse(sessionStorage.getItem("msg" + this.meetingNumber)) : []
        }
      }
    })

  },
  mounted() {
    window.addEventListener('beforeunload', e => {
      console.log(e);
      let key = "msg" + this.meetingNumber;
      sessionStorage.setItem(key, JSON.stringify(this.msg))
    })

    console.log(this.username)
    console.log('account的用户名获取' + this.username);
    userId = this.userId;
    console.log("account的用户ID获取" + userId);

    if (this.ws) console.log("已建立连接，不要重复建立");
    //普通会议 room改成meeting  
    this.ws = new WebSocket('ws://139.9.119.138:30000/meeting/' + this.meetingNumber + '/' + userId + '/' + this.username);
    this.ws.onopen = this.webscoketonopen;
    this.ws.onmessage = this.webscoketonmessage;
    //发生错误触发
    this.ws.onerror = function () {
      console.log("连接错误");
    };
    //正常关闭触发
    this.ws.onclose = function () {
      console.log("连接关闭");
    };

    this.scrollToBottom()
  },
  updated() {
    this.scrollToBottom();
  },
  destroyed() {
    window.removeEventListener('beforeunload')
  },
  computed: {
    ...mapGetters('account', ['username', 'userId'])
  },
  methods: {
    //导出的方法
    handleDownload() {
      this.downloadLoading = true;
      import("../../Excel/Export2Excel.js").then((excel) => {
        const tHeader = ["用户id", "用户名"];
        const filterVal = ["userId", "username"];
        const list = this.userlist;
        const data = this.formatJson(filterVal, list);
        const now = new Date();
        const title = now.getFullYear() + "年" + (now.getMonth() + 1) + "月" + now.getDate() + "日-" + now.getHours() + "时" + now.getMinutes() + "分" + now.getSeconds() + "秒-会议" + this.meetingNumber + "参会成员列表"
        excel.export_json_to_excel({
          header: tHeader,
          data,
          filename: title,
          autoWidth: true,
          bookType: "xlsx",
        });
        this.downloadLoading = false;
      });
    },
    formatJson(filterVal, jsonData) {
      return jsonData.map((v) =>
        filterVal.map((j) => {
          //     if (j === 'timestamp') {
          //         return parseTime(v[j])
          //     } else {
          return v[j];
          //     }
        })
      );
    },
    upload() {
      document.getElementById("upload").click();
    },
    send(e) {
      e.preventDefault();

      this.value = this.value.replace(/\r?\n|\r/g, "");

      if (!this.value) {
        message.info("不能发送空白信息");
        return;
      }
      //给socket服务器发送
      this.sendMsg();
      this.$nextTick(() => {
        document.getElementsByClassName("chatbox")[0].scrollTop =
          document.getElementsByClassName("chatbox")[0].scrollHeight;
        this.value = "";
      });
    },
    webscoketonopen() {
      console.log("与服务器成功建立连接");
    },
    //websocket监听
    webscoketonmessage(e) {
      console.log("进入监听函数");
      console.log("e" + e);
      let resData = JSON.parse(e.data);
      if (resData.type === TYPE_MSG) {
        console.log("是发送消息的类别！！！");
        console.log("time" + resData.time);

        this.msg.push({
          msg: this.$string2emoji(resData.msg),
          send: resData.userId === this.userId ? true : false,
          username: resData.userName,
          time: resData.time,
        });
      } else if (resData.type === TYPE_USERLIST) {
        console.log("是用户列表的消息！！！");
        console.log(resData);
        this.meetingCount = resData.userList.length;
        this.userlist = undefined;
        this.userlist = new Array();
        for (var i = 0; i < resData.userList.length; i++)
          if (resData.userList[i].userId == this.hostId) {
            this.userlist.unshift({
              userId: resData.userList[i].userId,
              username: resData.userList[i].userName,
            });
          } else {
            this.userlist.push({
              userId: resData.userList[i].userId,
              username: resData.userList[i].userName,
            });
          }
      }
    },
    exitMeeting() {
      this.closeWebSocket();
    },
    sendMsg() {
      if (!this.ws) {
        alert("你已经掉线，请重新加入");
        return;
      }
      if (this.ws.readyState === 1) {
        //发送消息
        console.log("想要发送" + this.value);
        //this.ws.send(this.value)
        this.ws.send(this.value);
        this.value = "";
      } else {
        alert("发送失败");
      }
    },
    closeWebSocket() {
      if (this.ws) {
        this.ws.close();
        this.ws = null;
      }
    },
    talking(content) {
      console.log(content);
    },
    getRandom() {
      var num = Math.random();
      num = num * 100;
      console.log("sa" + num);
      num = num.toString();
      console.log("sa" + num);
      return num;
    },
    smile() {
      console.log("输入表情啦");
      this.emoji = true;
    },
    scrollToBottom() {
      this.$nextTick(() => {
        var container = this.$el.querySelector("#chat-box");
        container.scrollTop = container.scrollHeight;
      })
    },
    confirm() {
      this.exitMeeting();
      window.close();
    },
  },
};
</script>
<style scoped>
.nicheng {
  height: 33px;
  width: 100%;
  border-radius: 3px;
}
.left {
  text-align: left;
}
.right {
  text-align: right;
}
#components-a-popconfirm-demo-placement .ant-btn {
  width: 70px;
  text-align: center;
  padding: 0;
  margin-right: 8px;
  margin-bottom: 8px;
}
.content {
  position: absolute;
  top: 10%;
  left: 10%;
  width: 80%;
  height: 80%;
}
.chatroom {
  display: grid;
  border: 1px solid #d6d6d6;
  grid-template-rows: 60px 1fr 40px 140px;
  grid-template-columns: 1fr 250px;
  width: 100%;
  height: 100%;
  background-color: #f5f5f5;
  color: #000000;
}
.title {
  grid-column: 1 / 2;
  grid-row: 1 / 2;
  border-bottom: 1px solid #d6d6d6;
  font-size: 18px;
  font-weight: 500; /* 字重 */
  display: flex;
  align-items: center;
  justify-content: center;
}
.fix {
  grid-column: 2 / 3;
  grid-row: 1 / 2;
  border-bottom: 1px solid #d6d6d6;
}
.chatbox {
  grid-column: 1 / 2;
  grid-row: 2 / 3;
  padding: 8px 30px;
  overflow-y: scroll;
}
.toolbar {
  grid-column: 1 / 2;
  grid-row: 3 / 4;
  padding: 0 30px;
  background-color: #ffffff;
  display: flex;
  align-items: center;
  color: #00000090;
}
.toolbar > div {
  font-size: 17px;
  padding-right: 16px;
}
.toolbar > div > span {
  cursor: pointer;
}

.messagebox {
  grid-column: 1 / 2;
  grid-row: 4 / 5;
  background-color: #ffffff;
  padding: 0 30px;
  padding-bottom: 40px;
}
.messagebox > .ant-input {
  padding: 0;
  height: 100%;
  border: unset;
  resize: none;
  color: #000000;
}
.messagebox > .ant-input:focus {
  border-right-width: 0;
  box-shadow: none;
}
.send-btn {
  padding-top: 6px;
  /* padding-bottom: 10px; */
  text-align: right;
}
.userlist {
  grid-column: 2 / 3;
  grid-row: 2 / 5;
  padding: 16px 20px;
  padding-top: 10px;
  height: 100%;
  overflow: auto;
  position: relative;
  background-color: #e4e4e4;
}
.list-title {
  background-color: #e4e4e4;
  text-align: center;
  font-size: 18px;
  margin-bottom: 8px;
}
.list {
  display: flex;
  flex-wrap: wrap;
}
.download {
  grid-column: 2 / 3;
  grid-row: 4 / 5;
  position: relative;
}
.download-btn {
  position: absolute;
  right: 4px;
  bottom: 4px;
}
.emoji-width {
  max-width: 100%;
}

.div1 {
  position: relative;
}
.div2 {
  width: 100px;
  right: 0px;
  bottom: 0px;
}
textarea {
  cursor: auto;
}
::-webkit-scrollbar {
  width: 6px;
}
::-webkit-scrollbar-thumb {
  cursor: pointer;
  background-color: #d2d2d2;
  border-radius: 5px;
}
::selection {
  background: #9bcaef !important;
  color: #000 !important;
}
.chatbox::-webkit-scrollbar-thumb {
  background-color: #f5f5f5;
}
.chatbox:hover::-webkit-scrollbar-thumb {
  background-color: #d2d2d2;
}
</style>
