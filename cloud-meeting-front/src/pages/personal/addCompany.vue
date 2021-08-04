<template>
  <a-card
    :body-style="{
      padding: '24px 32px',
      paddingBottom: '64px',
      textAlign: 'left',
    }"
    :bordered="false"
  >
    <a-steps :current="current">
      <a-step v-for="item in steps" :key="item.title" :title="item.title" />
    </a-steps>

    <div class="steps-content">
      <!-- 企业名搜索框 -->
      <a-input-search
        v-if="current == 0"
        placeholder="请输入企业名称"
        enter-button
        @search="search"
        style="width: 50%; margin-top: 5%"
        ref="companyName"
        required
        prop="companyName"
        v-model="companyName"
      />
      <!-- 搜索结果 -->
      <a-table
        v-if="current == 1"
        :columns="columns"
        :data-source="data"
        :scroll="{ x: 1500, y: 300 }"
        :rowKey="(record, index) => record.id"
      >
        <template slot="action" slot-scope="text, record">
          <a slot="action" @click="add(record.id)">申请加入</a>
        </template>
      </a-table>
      <!-- 结果页 -->
      <a-result
        v-if="isShow"
        status="success"
        title="已经成功提交申请！"
        v-bind:sub-title="context"
      >
      </a-result>
    </div>
    <div class="steps-action" v-show="!isShow">
      <a-button v-if="current == 0" type="primary" @click="next">
        下一步
      </a-button>
      <a-button v-if="current > 0" @click="prev"> 上一步 </a-button>
    </div>
  </a-card>
</template>
<script>
const columns = [
  {
    title: "ID",
    width: 70,
    dataIndex: "id",
    fixed: "left",
    ellipsis: true,
  },
  {
    title: "企业名称",
    width: 200,
    dataIndex: "name",
    fixed: "left",
    ellipsis: true,
  },
  {
    title: "企业类型",
    width: 100,
    dataIndex: "type",
    fixed: "left",
    ellipsis: true,
  },
  {
    title: "创建人",
    dataIndex: "founderName",
    width: 100,
    ellipsis: true,
  },
  {
    title: "创建人邮箱",
    dataIndex: "founderEmail",
    width: 150,
    ellipsis: true,
  },
  {
    title: "创建人电话",
    dataIndex: "founderPhone",
    width: 100,
    ellipsis: true,
  },
  { title: "地址", dataIndex: "address", width: 100, ellipsis: true },
  {
    title: "操作",
    key: "operation",
    fixed: "right",
    width: 100,
    scopedSlots: { customRender: "action" },
    ellipsis: true,
  },
];

import { searchCompany, joinCompany } from "@/services/company";
import { mapGetters } from "vuex";
export default {
  data() {
    return {
      current: 0,
      isShow: false,
      count: 4,
      context: "",
      data: [],
      companyName: "",
      columns,
      steps: [
        {
          title: "搜索企业",
        },
        {
          title: "申请加入",
        },
        {
          title: "完成",
        },
      ],
      company: [],
    };
  },
  computed: {
    ...mapGetters("account", ["userId"]),
  },
  methods: {
    // 下一页
    next() {
      const companyName = this.companyName;
      searchCompany(companyName).then(this.afterSearch);
      this.current++;
    },
    // 上一页
    prev() {
      this.current--;
    },
    // 模糊搜索企业名
    search() {
      const companyName = this.companyName;
      searchCompany(companyName).then((res) => {
        this.data = res.data.data;
      });
      this.current++;
    },
    // 选择企业加入
    add(companyId) {
      joinCompany(this.userId, companyId).then(this.afterJoin);
    },
    // 点击加入后5秒跳转回首页
    afterJoin(res) {
      let joinRes = res.data;
      if (joinRes.code == 200) {
        this.current++;
        this.isShow = true;
        var TIME_COUNT = this.count;
        if (!this.timer) {
          this.show = false;
          this.context =
            "请耐心等候企业的回复，页面将在" + (this.count + 1) + "秒后返回！";
          this.timer = setInterval(() => {
            if (this.count > 0 && this.count <= TIME_COUNT) {
              this.context =
                "请耐心等候企业的回复，页面将在" + this.count + "秒后返回！";
              this.count--;
            } else {
              this.show = true;
              clearInterval(this.timer);
              this.timer = null;
              //跳转的页面写在此处
              const closeRoute = "/addcompany";
              const route = "/dashboard";
              //关闭当前页面并跳转
              this.$closePage(closeRoute, route);
            }
          }, 1000);
        }
      }
    },
  },
};
</script>
<style scoped>
.steps-content {
  margin-top: 30px;
  border: 1px dashed #e9e9e9;
  border-radius: 6px;
  background-color: #fafafa;
  min-height: 200px;
  text-align: center;
  padding-top: 0px;
}

.steps-action {
  margin-top: 24px;
}

.ant-btn {
  left: 40%;
}
</style>
