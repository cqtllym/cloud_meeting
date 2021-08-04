<template>
  <div>
    <div :class="name==username?'border-self':'border-other'">
      <span>{{ name.charAt(0).toUpperCase() }}</span>
    </div>
  </div>
</template>

<script>
import { mapGetters } from "vuex";

export default {
  props: ['name'],
  name: 'ProfileSquare',
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
.border-other {
  box-sizing: content-box;
  width: 33px;
  height: 33px;
  line-height: 33px;
  text-align: center;
  border-radius: 3px;
  background-color: dodgerblue;
  font-size: 20px;
  font-weight: bold;
  color: white;
}

.border-self {
  box-sizing: content-box;
  width: 33px;
  height: 33px;
  line-height: 33px;
  text-align: center;
  border-radius: 3px;
  background-color: rgb(33, 35, 37);
  font-size: 20px;
  font-weight: bold;
  color: white;
}

/* .outsider {
  box-sizing: content-box;
  width: 100px;
  height: 100px;
  text-align: center;
  padding: 5px;
  border-radius: 55px;
  background-color: dodgerblue;
} */
</style>