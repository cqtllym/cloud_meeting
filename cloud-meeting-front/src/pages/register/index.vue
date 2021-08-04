<template>
  <common-layout>
    <div class="top">
      <div class="header">
        <img alt="logo" class="logo" src="@/assets/img/huawei.png" />
        <span class="title">{{ systemName }}</span>
      </div>
      <div class="desc">SaaS云服务系统开发—云会议系统</div>
    </div>
    <div class="register">
      <a-form-model :model="form" :rules="rules" ref="registerForm">
        <a-form-model-item prop="name">
          <a-input
            type="text"
            size="large"
            placeholder="请输入账户名称"
            v-model="form.name"
            autocomplete="new-password"
            addon-before="账户名称"
          >
            <a-icon slot="suffix" type="user" />
          </a-input>
        </a-form-model-item>
        <a-form-model-item prop="telephone">
          <a-input
            size="large"
            placeholder="请输入手机号码"
            v-model="form.telephone"
            autocomplete="new-password"
            addon-before="手机号码"
          >
            <a-icon slot="suffix" type="phone" />
          </a-input>
        </a-form-model-item>
        <a-form-model-item prop="email">
          <a-input
            size="large"
            placeholder="请输入邮箱"
            v-model="form.email"
            autocomplete="new-password"
            addon-before="电子邮箱"
          >
            <a-icon slot="suffix" type="mail" />
          </a-input>
        </a-form-model-item>
        <a-form-model-item prop="sex">
          <a-radio-group v-model="form.sex" size="large" style="width: 100%">
            <a-radio-button value="男性" style="width: 50%; text-align: center">
              男性 <a-icon type="man"
            /></a-radio-button>
            <a-radio-button value="女性" style="width: 50%; text-align: center">
              女性 <a-icon type="woman"
            /></a-radio-button>
          </a-radio-group>
        </a-form-model-item>
        <a-form-model-item prop="password">
          <a-input
            size="large"
            placeholder="请输入密码"
            :type="showPwd2 ? 'text' : 'password'"
            v-model="form.password"
            autocomplete="new-password"
            addon-before="输入密码"
          >
            <a-icon
              slot="suffix"
              type="eye"
              v-show="showPwd2"
              @click="showPwd2 = !showPwd2"
            />
            <a-icon
              slot="suffix"
              type="eye-invisible"
              v-show="!showPwd2"
              @click="showPwd2 = !showPwd2"
            />
          </a-input>
        </a-form-model-item>
        <a-form-model-item prop="confirmPassword">
          <a-input
            size="large"
            placeholder="请确认密码"
            :type="showPwd1 ? 'text' : 'password'"
            v-model="form.confirmPassword"
            autocomplete="autocomplete"
            addon-before="确认密码"
          >
            <a-icon
              slot="suffix"
              type="eye"
              v-show="showPwd1"
              @click="showPwd1 = !showPwd1"
            />
            <a-icon
              slot="suffix"
              type="eye-invisible"
              v-show="!showPwd1"
              @click="showPwd1 = !showPwd1"
            />
          </a-input>
        </a-form-model-item>
        <a-form-model-item>
          <a-button
            :loading="registering"
            style="width: 100%"
            size="large"
            type="primary"
            @click="handleSubmit()"
            >注册账户</a-button
          >
          <div>
            <span>注册完毕即可</span>
            <router-link to="/login">前往登录</router-link>
          </div>
        </a-form-model-item>
      </a-form-model>
    </div>
  </common-layout>
</template>

<script>
import CommonLayout from '@/layouts/CommonLayout'
import { register } from '@/services/user'
import md5 from 'js-md5';

export default {
  name: 'Register',
  components: { CommonLayout },
  data() {
    return {
      showPwd1: false,
      showPwd2: false,
      registering: false,
      form: {
        name: null,
        password: null,
        confirmPassword: null,
        telephone: null,
        email: null,
        sex: '男性'
      },
      rules: {
        name: [
          { required: true, message: '请输入账户名称', trigger: 'blur' },
          { min: 3, message: '账户名长度至少为3个字符', trigger: 'blur' },
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 6, max: 16, message: '密码长度为6-16个字符', trigger: 'blur' },
        ],
        confirmPassword: [
          { required: true, message: '请确认密码', trigger: 'blur' },
          { min: 6, max: 16, message: '密码长度为6-16个字符', trigger: 'blur' },
        ],
        telephone: [
          { required: true, message: '请输入手机号码', trigger: 'blur' },
          { pattern: /^[1][3,4,5,6,7,8,9][0-9]{9}$/, message: '请填写正确的手机号', trigger: 'blur' },
        ],
        email: [
          { required: true, message: '请填写邮箱', trigger: 'blur' },
          {
            pattern: /^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*\.[a-zA-Z0-9]{2,6}$/,
            message: '邮箱格式不正确',
            trigger: 'blur',
          },
          { type: 'string', message: '长度不能超过30位', trigger: 'blur', max: 30 }
        ],
        sex: [
          { required: true, message: '请选择性别', trigger: 'blur' },
        ],
      }
    }
  },
  computed: {
    systemName() {
      return this.$store.state.setting.systemName
    }
  },
  methods: {
    handleSubmit() {
      this.$refs.registerForm.validate(valid => {
        if (valid) {
          if (this.form.password !== this.form.confirmPassword) {
            this.$message.error('密码与确认密码不一致，请重新输入！');
          } else {
            //注册的操作
            register(this.form.name, md5(this.form.password), this.form.telephone, this.form.sex, this.form.email).then((res) => {
              const registerRes = res.data
              if (registerRes.code == 200) {
                this.$message.success('注册成功,请点击下方按钮前往登录！');
                this.form.name = null
                this.form.password = null
                this.form.confirmPassword = null
                this.form.telephone = null
                this.form.email = null
                this.form.sex = '男性'
              } else if (registerRes.code == 402) {
                this.$message.error(registerRes.errMsg);
              }
            })
          }
        } else {
          return false;
        }
      });
    }
  }
}
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
  .register {
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

  .ant-input-disabled {
    width: 0px;
  }
}
</style>
