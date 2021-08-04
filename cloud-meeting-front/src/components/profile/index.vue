<template>
  <div>
    <div class="outsider">
      <div class="border">
        <span>{{ firstName }}</span>
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters } from "vuex";

export default {
  name: 'Profile',
  data() {
    return {
    }
  },
  computed: {
    ...mapGetters("account", [
      "username"
    ]),
    firstName() {
      return this.username.charAt(0).toUpperCase()
    }
  },
}
</script>


<style scoped>
.border {
  box-sizing: content-box;
  width: 82px;
  height: 82px;
  line-height: 82px;
  text-align: center;
  border-radius: 45px;
  border: 4px solid white;
  background-color: dodgerblue;
  font-size: 45px;
  font-weight: bold;
  color: white;
}

.outsider {
  box-sizing: content-box;
  width: 90px;
  height: 90px;
  text-align: center;
  padding: 5px;
  border-radius: 50px;
  background-color: dodgerblue;
}
</style>