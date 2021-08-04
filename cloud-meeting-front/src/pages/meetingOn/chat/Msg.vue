<template>
  <div :class="`msg-content ${send ? 'send' : 'receive'}`">
    <!-- <img class="avatar" :src="avatar" /> -->
    <ProfileSquare class="avatar"  :name='username' />

    <div class="msg" v-html="msg">
      <!--            {{ msg }}-->
    </div>
  </div>
</template>



<script>
import ProfileSquare from '@/components/profileSquare/index.vue'

export default {
  name: 'Msg',
  props: ['msg', 'send','username'],
  components:{
      ProfileSquare
  }
};
</script>

<style scoped>
.msg-content {
  display: flex;
  margin-bottom: 10px;
}
.avatar {
  height: 33px;
  width: 33px;
  border-radius: 3px;
  user-select: none;
}

.receive > .avatar {
  margin-right: 10px;
}

.receive > .msg {
  background-color: #ffffff;
}

.receive > .msg:before {
  content: "";
  border: 4px solid transparent;
  border-right: 4px solid #ffffff;
  position: absolute;
  top: 12.5px;
  left: -8px;
}

.msg {
  max-width: 340px;
  background-color: #1fa2e8;
  padding: 6px 8px;
  color: #000;
  border-radius: 3px;
  word-wrap: break-word;
  position: relative;
}
.send > .msg:after {
  content: "";
  border: 4px solid transparent;
  border-left: 4px solid #1fa2e8;
  position: absolute;
  top: 12.5px;
  right: -8px;
}
.send {
  flex-direction: row-reverse;
}
.send > .avatar {
  margin-left: 10px;
}
</style>