<template>
  <span @click="fetchNotice" class="header-notice">
    <!-- 未读通知数量 -->
    <a-badge class="notice-badge" :count="count">
      <a-icon :class="['header-notice-icon']" type="bell" />
    </a-badge>
  </span>
</template>

<script>
import { mapGetters, mapMutations } from "vuex";
import { unreadNotice } from "@/services/notice";
export default {
  name: "HeaderNotice",
  data() {
    return {
      loading: false,
      show: false,
    };
  },
  computed: {
    ...mapGetters("account", ["userId", "count"]),
  },
  beforeMount() {
    this.countUnreadNotice();
  },
  methods: {
    ...mapMutations("account", ["setCount"]),
    fetchNotice() {
      this.$router.push({ path: "/notice" });
    },
    // 获取未读消息数
    countUnreadNotice() {
      unreadNotice(this.userId).then((res) => {
        const couRes = res.data;
        if (couRes.code == 200) {
          this.setCount(couRes.data);
        }
      });
    },
  },
};
</script>

<style lang="less">
.header-notice {
  display: inline-block;
  transition: all 0.3s;
  span {
    vertical-align: initial;
  }
  .notice-badge {
    color: inherit;
    .header-notice-icon {
      font-size: 16px;
      padding: 4px;
    }
  }
}
.dropdown-tabs {
  background-color: @base-bg-color;
  box-shadow: 0 2px 8px @shadow-color;
  border-radius: 4px;
  .tab-pane {
    padding: 0 24px 12px;
    min-height: 250px;
  }
}
</style>
