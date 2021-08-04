<template>
  <a-card
    :body-style="{
      padding: '24px 32px',
      textAlign: 'left',
    }"
  >
    <img
      src="../../../assets/img/invite.png"
      style="width: 25%; min-width: 150px; margin: 0 auto; margin-left: 34%"
    />
    <a-form :form="form" :label-col="labelCol" :wrapper-col="wrapperCol">
      <a-form-item ref="name" label="姓名或电话" required prop="name">
        <a-input
          v-decorator="[
            'name',
            {
              rules: [
                {
                  required: true,
                  message: '不能为空！',
                },
              ],
            },
          ]"
        />
      </a-form-item>

      <a-form-item>
        <a-button @click="cancel" style="float: right; margin-left: 15%">
          取消
        </a-button>
        <a-button
          @click="submit"
          type="primary"
          style="float: right; margin-left: 15%"
        >
          邀请
        </a-button>
      </a-form-item>
    </a-form>
  </a-card>
</template>
<script>
import { mapGetters } from "vuex";
import { invitePerson, searchPerson } from "@/services/company";
export default {
  data() {
    return {
      context: "",
      labelCol: { span: 4 },
      wrapperCol: { span: 14 },
      other: "",
    };
  },
  beforeCreate() {
    this.form = this.$form.createForm(this);
  },
  computed: {
    ...mapGetters("account", ["username", "userId", "companyId"]),
  },
  methods: {
    submit() {
      this.form.validateFieldsAndScroll((err) => {
        if (!err) {
          const name = this.form.getFieldValue("name");
          searchPerson(name).then(this.afterSearch);
        }
      });
    },
    afterSearch(res) {
      if (res.data.data[0] == null) {
        this.$message.error("用户不存在！");
      } else {
        const id = res.data.data[0].userId;
        const companyId = res.data.data[0].companyId;
        if (companyId == null) {
          invitePerson(id, this.companyId);
          const inviteRes = res.data;
          if (inviteRes.code == 200) {
            this.$message.success("已发送邀请！");
            this.$router.push({ path: "/resourceManagement/person" });
          }
        } else {
          this.$message.error("该用户已经在其他企业！");
        }
      }
    },
    cancel() {
      this.$router.push({ path: "/resourceManagement/person" });
    },
  },
};
</script>

<style scoped>
.ant-form {
  width: 60%;
  margin: auto;
}
</style>