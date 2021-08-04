<template>
  <a-card
    :body-style="{
      padding: '24px 32px',
      paddingBottom: '64px',
    }"
    :bordered="false"
  >
    <a-tabs default-active-key="1" v-if="isRouterAlive">
      <a-tab-pane key="1" v-if="role === 'founder' || role === 'admin'">
        <span slot="tab" @click="searchCompanyNotice">企业消息 </span>
        <!-- 管理员消息列表 -->
        <a-list
          item-layout="vertical"
          size="large"
          :pagination="pagination"
          :data-source="list"
        >
          <a-list-item slot="renderItem" key="item.title" slot-scope="item">
            <a-list-item-meta>
              <span slot="title">
                <a-icon v-if="item.isDealt == '已处理'" type="pushpin" />
                <a-icon v-if="item.isDealt != '已处理'" type="alert" />
                {{ item.theme }}
                <a-button
                  type="link"
                  style="float: right"
                  v-if="item.isDealt != '已处理' && item.type == '同意拒绝'"
                  @click="deal(item.id, false, item.fromType)"
                >
                  拒绝
                </a-button>
                <a-button
                  type="link"
                  style="float: right"
                  v-if="item.isDealt != '已处理' && item.type == '同意拒绝'"
                  @click="deal(item.id, true, item.fromType)"
                >
                  接受
                </a-button>
                <a-button
                  type="link"
                  style="float: right"
                  v-if="item.isDealt != '已处理' && item.type == '只读通知'"
                  @click="deal(item.id, true, item.fromType)"
                >
                  确认
                </a-button>
              </span>
            </a-list-item-meta>
            发送方：{{ item.fromName }} &nbsp;&nbsp; 时间：{{ item.createTime }}
          </a-list-item>
        </a-list>
      </a-tab-pane>
      <!-- 个人消息列表 -->
      <a-tab-pane key="2">
        <span slot="tab" @click="searchUserNotice">个人消息 </span>
        <a-list
          item-layout="vertical"
          size="large"
          :pagination="pagination"
          :data-source="listData"
        >
          <a-list-item slot="renderItem" key="item.title" slot-scope="item">
            <a-list-item-meta>
              <span slot="title">
                <a-icon v-if="item.isDealt == '已处理'" type="pushpin" />
                <a-icon v-if="item.isDealt != '已处理'" type="alert" />
                {{ item.theme }}
                <a-button
                  type="link"
                  style="float: right"
                  v-if="
                    item.isDealt != '已处理' &&
                    item.type == '同意拒绝' &&
                    role == 'user'
                  "
                  @click="
                    deal(
                      item.id,
                      false,
                      item.fromType,
                      item.fromName,
                      item.fromId
                    )
                  "
                  >拒绝
                </a-button>
                <a-button
                  type="link"
                  style="float: right"
                  v-if="
                    item.isDealt != '已处理' &&
                    item.type == '同意拒绝' &&
                    role == 'user'
                  "
                  @click="
                    deal(
                      item.id,
                      true,
                      item.fromType,
                      item.fromName,
                      item.fromId
                    )
                  "
                  >接受
                </a-button>
                <a-button
                  type="link"
                  style="float: right"
                  v-if="item.isDealt != '已处理' && item.type == '只读通知'"
                  @click="
                    dealRead(
                      item.id,
                      true,
                      item.fromId,
                      item.fromName,
                      item.theme
                    )
                  "
                  >确认
                </a-button>
                <a-button
                  type="link"
                  style="float: right"
                  v-if="item.isDealt != '已处理' && item.type == '加入会议'"
                  @click="deal(item.id, true, item.fromType)"
                >
                  去开会
                </a-button>
                <a-button
                  type="link"
                  style="float: right"
                  v-if="item.isDealt != '已处理' && item.type == '加入会议'"
                  @click="deal(item.id, false, item.fromType)"
                  >忽略
                </a-button>
              </span>
            </a-list-item-meta>
            发送方：{{ item.fromName }} &nbsp;&nbsp;时间：{{ item.createTime }}
          </a-list-item>
        </a-list>
      </a-tab-pane>
    </a-tabs>
  </a-card>
</template>

<script>
import {
  userNotice,
  companyNotice,
  dealInvitation,
  dealApplication,
  dealRead,
  unreadNotice,
} from "@/services/notice";
import { mapGetters, mapMutations } from "vuex";

export default {
  provide() {
    return {
      reload: this.reload,
    };
  },
  data() {
    return {
      listData: [],
      list: [],
      pagination: {
        onChange: (page) => {
          console.log(page);
        },
        pageSize: 5,
      },
      role: "",
      isRouterAlive: true,
      num: 0,
    };
  },
  computed: {
    ...mapGetters("account", [
      "username",
      "userId",
      "roles",
      "companyId",
      "count",
    ]),
  },
  beforeMount() {
    this.searchUserNotice();
    this.searchCompanyNotice();
    this.countUnreadNotice();
  },
  created() {
    this.role = this.roles[0].id;
  },
  methods: {
    reload() {
      this.isRouterAlive = false;
      this.$nextTick(function () {
        this.isRouterAlive = true;
      });
    },
    ...mapMutations("account", [
      "setUserName",
      "setUserId",
      "setCompanyName",
      "setCompanyId",
      "setSex",
      "setTelephone",
      "setRoles",
      "setCount",
    ]),
    searchUserNotice() {
      userNotice(this.userId).then(this.showListData);
    },
    showListData(res) {
      this.listData = res.data.data;
    },
    searchCompanyNotice() {
      if (this.companyId != null) {
        companyNotice(this.companyId).then(this.showList);
      }
    },
    showList(res) {
      this.list = res.data.data;
    },
    // 处理需返回结果的消息
    deal(id, result, fromType, name, fromId) {
      if (fromType == "个人") {
        dealApplication(id, result).then(this.afterDealApplication);
      } else {
        dealInvitation(id, result).then((res) => {
          const dealRes = res.data;
          if (dealRes.code == 200) {
            this.$message.success("已处理该消息！");
            this.countUnreadNotice();
            if (result == true) {
              let roles = [{ id: "employee" }];
              this.setCompanyId(fromId);
              this.setCompanyName(name);
              this.setRoles(roles);
              this.$router.push({ path: "/resourceManagement/information" });
            } else {
              //刷新通知界面
              this.searchUserNotice();
            }
          }
        });
      }
    },
    afterDealApplication(res) {
      const dealRes = res.data;
      if (dealRes.code == 200) {
        this.$message.success("已处理该消息！");
        this.searchCompanyNotice();
        this.countUnreadNotice();
      }
    },
    // 处理只读消息
    dealRead(id, result, fromId, fromName, theme) {
      dealRead(id, result).then((res) => {
        const dealRes = res.data;
        console.log(theme);
        if (dealRes.code == 200) {
          this.$message.success("已处理该消息！");
          var reg1 = new RegExp("同意"); //同意加入企业
          var reg2 = new RegExp("开除"); //被企业开除
          var reg3 = new RegExp("设置"); //设置为管理员
          var reg4 = new RegExp("移除"); //管理员被移除
          var reg5 = new RegExp("移出"); //企业注销
          if (theme.match(reg1)) {
            this.setCompanyId(fromId);
            this.setCompanyName(fromName);
            let roles = [{ id: "employee" }];
            this.setRoles(roles);
            const route = "/dashboard";
            this.$refreshPage(route);
          } else if (theme.match(reg2) || theme.match(reg5)) {
            this.setCompanyId("");
            this.setCompanyName("");
            let roles = [{ id: "user" }];
            this.setRoles(roles);
            const noticeRoute = "/notice";
            this.$refreshPage(noticeRoute);
            const route = "/dashboard";
            this.$refreshPage(route);
          } else if (theme.match(reg3)) {
            let roles = [{ id: "admin" }];
            this.setRoles(roles);
            const route = "/notice";
            this.$refreshPage(route);
          } else if (theme.match(reg4)) {
            let roles = [{ id: "employee" }];
            this.setRoles(roles);
            const route = "/notice";
            this.$refreshPage(route);
          }
          this.searchUserNotice();
          this.countUnreadNotice();
        }
      });
    },

    // 获取未读消息数
    countUnreadNotice() {
      unreadNotice(this.userId).then((res) => {
        const couRes = res.data;
        if (couRes.code == 200) {
          this.num = couRes.data;
          this.setCount(this.num);
        }
      });
    },
  },
};
</script>

<style scoped>
</style>