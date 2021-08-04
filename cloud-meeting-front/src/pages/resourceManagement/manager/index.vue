<template>
  <a-card
    :body-style="{
      padding: '24px 32px',
      paddingBottom: '64px',
      textAlign: 'left',
    }"
    :bordered="false"
  >
    <template slot="title"> </template>
    <a-table bordered :columns="columns" :data-source="dataSource">
      <template slot="operation" slot-scope="text, record">
        <a-popconfirm
          v-if="dataSource.length"
          title="确认设为管理员 ？"
          @confirm="() => handleAdd(record.userId)"
        >
          <a-button type="link">设为管理员</a-button>
        </a-popconfirm>
      </template>
    </a-table>
  </a-card>
</template>

<script>
import { mapGetters, mapMutations } from "vuex";
import { fetchEmployee, setAdmin } from "@/services/company";
export default {
  data() {
    return {
      dataSource: [],
      columns: [
        {
          title: "姓名",
          dataIndex: "username",
          key: "username",
          scopedSlots: { customRender: "username" },
        },
        {
          title: "性别",
          dataIndex: "sex",
          key: "sex",
          ellipsis: true,
        },
        {
          title: "手机",
          dataIndex: "telephone",
          key: "telephone",
          ellipsis: true,
        },
        {
          title: "邮箱",
          dataIndex: "email",
          key: "email",
          ellipsis: true,
        },
        {
          title: "操作",
          key: "operation",
          scopedSlots: { customRender: "operation" },
        },
      ],
    };
  },
  created() {
    this.fetchEmployee();
  },
  computed: {
    ...mapGetters("account", ["username", "userId", "companyId", "roles"]),
  },
  methods: {
    ...mapMutations("account", ["setRoles"]),
    fetchEmployee() {
      fetchEmployee(this.companyId).then(this.afterFetch);
    },
    afterFetch(res) {
      this.dataSource = res.data.data;
    },
    handleAdd(id) {
      setAdmin(id, this.companyId).then((res) => {
        const afterSet = res.data;
        if (afterSet.code == 200) {
          this.$message.success("设置成功！");
          const thisRoute = "/resourceManagement/manager";
          this.$refreshPage(thisRoute);
        }
      });
    },
  },
};
</script>

<style>
</style>