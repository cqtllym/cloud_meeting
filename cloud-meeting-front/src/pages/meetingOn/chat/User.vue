<template>
  <div class="userinfo">
    <ProfileSquare class="useravatar" :name="name"/>
    <div class="username">{{ name }}
      <span v-if="isHost">（主持人）</span>
    </div>
  </div>
</template>

<script>
import ProfileSquare from '@/components/profileSquare/index.vue'
export default {
  name: 'User',
  props: ['name','isHost'],
  components: {
    ProfileSquare
  }
};
</script>

<style scoped>
.userinfo {
  width: 100%;
  margin-bottom: 20px;
  display: flex;
  align-items: center;
}
.useravatar {
  height: 33px;
  width: 33px;
  border-radius: 3px;
  user-select: none;
  margin-right: 10px;
}

.username{
    font-size:18px
}
</style>
