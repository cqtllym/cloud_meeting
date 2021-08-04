<template>
  <common-layout>
    <div class="top">
      <div class="header">
        <img alt="logo" class="logo" src="@/assets/img/huawei.png" />
        <span class="title">{{ systemName }}</span>
      </div>
      <div class="desc">SaaS云服务系统开发—云会议系统</div>
    </div>
    <div class="login">
      <a-form @submit="onSubmit" :form="form">
        <!-- <a-tabs size="large" :tabBarStyle="{textAlign: 'center'}" style="padding: 0 2px;">
          <a-tab-pane tab="账户密码登录" key="1"> -->
        <a-alert
          type="error"
          :closable="true"
          v-show="error"
          :message="error"
          showIcon
          style="margin-bottom: 24px"
        />
        <a-form-item>
          <a-input
            autocomplete="autocomplete"
            size="large"
            placeholder="请输入用户名"
            v-decorator="[
              'username',
              {
                rules: [
                  { required: true, message: '请输入账户名', whitespace: true },
                ],
              },
            ]"
          >
            <a-icon slot="prefix" type="user" />
          </a-input>
        </a-form-item>
        <a-form-item>
          <a-input
            size="large"
            placeholder="请输入密码"
            autocomplete="autocomplete"
            type="password"
            v-decorator="[
              'password',
              {
                rules: [
                  { required: true, message: '请输入密码', whitespace: true },
                ],
              },
            ]"
          >
            <a-icon slot="prefix" type="lock" />
          </a-input>
        </a-form-item>
        <a-form-item>
          <a-button
            :loading="logging"
            style="width: 100%"
            size="large"
            htmlType="submit"
            type="primary"
            >登录</a-button
          >
          <div>
            <span>没有账户？</span>
            <router-link to="/register">注册账户</router-link>
          </div>
        </a-form-item>
        <div></div>
      </a-form>
    </div>
  </common-layout>
</template>

<script>
import CommonLayout from "@/layouts/CommonLayout";
import { login } from "@/services/user";
// import { setAuthorization } from '@/utils/request'
import { mapMutations } from "vuex";
// import {loadRoutes} from '@/utils/routerUtil'
import md5 from "js-md5";

export default {
  name: "Login",
  components: { CommonLayout },
  data() {
    return {
      logging: false,
      error: "",
      form: this.$form.createForm(this),
    };
  },
  computed: {
    systemName() {
      return this.$store.state.setting.systemName;
    },
  },
  methods: {
    ...mapMutations("account", [
      "setUserName",
      "setUserId",
      "setCompanyName",
      "setCompanyId",
      "setSex",
      "setTelephone",
      "setEmail",
      "setRoles",
    ]),
    onSubmit(e) {
      e.preventDefault();
      this.form.validateFields((err) => {
        if (!err) {
          this.logging = true;
          const username = this.form.getFieldValue("username");
          const password = md5(this.form.getFieldValue("password"));
          login(username, password).then(this.afterLogin);

          // this.logging = false
          // this.setUserName("sam")
          // this.setUserId("123")
          // this.setCompanyName("")
          // this.setCompanyId("")
          // this.setSex("女性")
          // this.setTelephone("15050851139")
          // this.setRoles([{id:'admin'}])
          // setAuthorization({ JSESSIONID: 'sssssssaaaaaaaaaaaaaaaaaa'})
          // this.$router.push('/dashboard')
        }
      });
    },
    afterLogin(res) {
      this.logging = false;
      const loginRes = res.data;
      if (loginRes.code == 200) {
        const {
          username,
          userId,
          companyName,
          companyId,
          roles,
          telephone,
          email,
          sex,
        } = loginRes.data;
        this.setUserName(username);
        this.setUserId(userId);
        this.setCompanyName(companyName);
        this.setCompanyId(companyId);
        this.setSex(sex);
        this.setTelephone(telephone);
        this.setEmail(email);
        this.setRoles(roles);
        // setAuthorization({ sessionId: sessionId, expireAt: new Date(loginRes.data.expireAt) })
        this.$router.push("/dashboard");
        this.$message.success(username + "登录成功", 3);
      } else {
        this.$message.error(loginRes.errMsg);
      }
    },
  },
};
</script>

<style lang="less" scoped>
.common-layout {
  .top {
    text-align: center;
    .header {
      height: 44px;
      line-height: 44px;
      a {
        text-decoration: none;
      }
      .logo {
        height: 44px;
        vertical-align: top;
        margin-right: 16px;
      }
      .title {
        font-size: 33px;
        color: @title-color;
        font-family: "Myriad Pro", "Helvetica Neue", Arial, Helvetica,
          sans-serif;
        font-weight: 600;
        position: relative;
        top: 2px;
      }
    }
    .desc {
      font-size: 14px;
      color: @text-color-second;
      margin-top: 12px;
      margin-bottom: 40px;
    }
  }
  .login {
    width: 368px;
    margin: 0 auto;
    @media screen and (max-width: 576px) {
      width: 95%;
    }
    @media screen and (max-width: 320px) {
      .captcha-button {
        font-size: 14px;
      }
    }
    .icon {
      font-size: 24px;
      color: @text-color-second;
      margin-left: 16px;
      vertical-align: middle;
      cursor: pointer;
      transition: color 0.3s;

      &:hover {
        color: @primary-color;
      }
    }
  }
}
</style>
