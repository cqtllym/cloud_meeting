<template>
  <a-dropdown>
    <div class="header-avatar" style="cursor: pointer">
      <!-- <a-avatar
        class="avatar"
        size="small"
        shape="circle"
        src="https://gw.alipayobjects.com/zos/rmsportal/cnrhVkzwxjPwAaCfPbdc.png"
      /> -->
      <div class="border-self">
        <span>{{ username.charAt(0).toUpperCase() }}</span>
      </div>
      <span class="name">{{ username }}</span>
    </div>
    <a-menu :class="['avatar-menu']" slot="overlay">
      <a-menu-item>
        <a-icon type="user" />
        <span @click="goTo('/dashboard')">个人中心</span>
      </a-menu-item>
      <a-menu-divider />
      <a-menu-item @click="logout">
        <a-icon style="margin-right: 8px" type="poweroff" />
        <span>退出登录</span>
      </a-menu-item>
    </a-menu>
  </a-dropdown>
</template>

<script>
import { mapGetters } from 'vuex'
// import { logout } from '@/services/user'
import { removeAuthorization } from '@/utils/request'

export default {
  name: 'HeaderAvatar',
  computed: {
    ...mapGetters('account', ['username', 'telephone', 'userId']),
  },
  methods: {
    goTo(path) {
      this.$router.push(path)
    },
    logout() {
      localStorage.removeItem(process.env.VUE_APP_ROUTES_KEY)
      localStorage.removeItem(process.env.VUE_APP_ROLES_KEY)
      removeAuthorization()
      this.$router.push('/login')
    }
  }
}
</script>

<style lang="less">
.header-avatar {
  display: inline-flex;
  .avatar,
  .name {
    align-self: center;
  }
  .avatar {
    margin-right: 8px;
  }
  .border-self {
    box-sizing: content-box;
    width: 24px;
    height: 24px;
    line-height: 24px;
    text-align: center;
    border-radius: 24px;
    background-color: dodgerblue;
    font-size: 14px;
    font-weight: bold;
    color: white;
    margin-right: 8px;
    margin-top: 20px;
  }
  .name {
    font-weight: 500;
  }
}
.avatar-menu {
  width: 150px;
}
</style>
