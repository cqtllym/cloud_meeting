<template>
  <a-card
    :body-style="{
      padding: '24px 32px',
      paddingBottom: '64px',
      textAlign: 'left',
    }"
    :bordered="false"
  >
    <a-form-model
      ref="form"
      :model="form"
      :rules="rules"
      :label-col="labelCol"
      :wrapper-col="wrapperCol"
      style="margin-top: 30px; margin-left: 38%"
    >
      <a-form-model-item ref="name" label="企业名称" prop="name">
        <a-input v-bind:disabled="true" v-model="form.name" />
        <a-button type="link" @click="dismiss" v-if="role == 'founder'">
          解散企业
        </a-button>
      </a-form-model-item>
      <a-form-model-item ref="id" label="企业ID" prop="id">
        <a-input v-bind:disabled="true" v-model="form.id" />
      </a-form-model-item>
      <a-form-model-item ref="type" label="企业类型" prop="type">
        <a-input v-bind:disabled="true" v-model="form.type" />
      </a-form-model-item>
      <a-form-model-item ref="address" label="地址" prop="address">
        <a-input
          v-bind:disabled="!isEdit"
          v-model="form.address"
          @blur="
            () => {
              $refs.address.onFieldBlur();
            }
          "
        />
        <a-button v-if="!isShow && role == 'founder'" type="link" @click="edit">
          修改
        </a-button>
        <a-button v-if="isShow" type="link" @click="confirm"> 确认 </a-button>
        <a-button
          v-if="isShow"
          type="link"
          @click="cancel"
          style="margin-left: 3%"
        >
          取消
        </a-button>
      </a-form-model-item>
      <a-form-model-item ref="founder" label="创建人" prop="founder">
        <a-input v-bind:disabled="true" v-model="form.founder" />
      </a-form-model-item>
      <a-form-model-item ref="phone" label="创建人手机" prop="phone">
        <a-input v-bind:disabled="true" v-model="form.phone" />
      </a-form-model-item>
      <a-form-model-item ref="email" label="创建人邮箱" prop="email">
        <a-input v-bind:disabled="true" v-model="form.email" />
      </a-form-model-item>
    </a-form-model>

    <img
      src="../../../assets/img/company.png"
      style="width: 32%; position: absolute; left: 40px; top: 40px"
    />
    <!-- </div> -->
  </a-card>
</template>
<script>
import {
  dismissCompany,
  searchCompanyInformation,
  editAddress,
} from "@/services/company";
import { mapGetters, mapMutations } from "vuex";
export default {
  data() {
    return {
      isEdit: false,
      isShow: false,
      context: "",
      labelCol: { span: 4 },
      wrapperCol: { span: 12 },
      other: "",
      form: {
        name: "",
        id: "",
        type: "",
        address: "",
        founder: "",
        phone: "",
        email: "",
      },
      rules: {
        address: [{ required: true, message: "地址不能为空", trigger: "blur" }],
      },
      role: "",
    };
  },
  computed: {
    ...mapGetters("account", [
      "userId",
      "username",
      "telephone",
      "sex",
      "companyId",
      "email",
      "companyName",
      "roles",
    ]),
  },
  beforeMount() {
    this.show();
    this.role = this.roles[0].id;
  },
  created() {},
  methods: {
    ...mapMutations("account", [
      "setUserName",
      "setUserId",
      "setCompanyName",
      "setCompanyId",
      "setSex",
      "setTelephone",
      "setRoles",
    ]),
    show() {
      searchCompanyInformation(this.userId).then((res) => {
        const afterRes = res.data.data;
        this.form.id = afterRes[0].id;
        this.form.name = afterRes[0].name;
        this.form.type = afterRes[0].type;
        this.form.address = afterRes[0].address;
        this.form.founder = afterRes[0].founderName;
        this.form.phone = afterRes[0].founderPhone;
        this.form.email = afterRes[0].founderEmail;
      });
    },
    edit: function () {
      this.isEdit = !this.isEdit;
      this.isShow = !this.isShow;
      this.context = this.form.address;
    },

    confirm: function () {
      this.isEdit = !this.isEdit;
      this.isShow = !this.isShow;
      this.$refs.form.validate((valid) => {
        if (valid) {
          editAddress(this.companyId, this.form.address).then((res) => {
            const editRes = res.data;
            if (editRes.code == 200) {
              this.$message.success("修改成功");
            }
          });
        } else {
          return false;
        }
      });
    },
    cancel: function () {
      this.isEdit = !this.isEdit;
      this.isShow = !this.isShow;
      this.form.address = this.context;
    },
    dismiss() {
      this.$confirm({
        title: "你确定要解散企业吗？",
        content: "解散企业后，你将失去和员工的联系，请谨慎操作！",
        onOk: () => {
          dismissCompany(this.form.id).then(this.afterDismiss);
        },
        onCancel() {},
      });
    },
    afterDismiss(res) {
      const dismissRes = res.data;
      if (dismissRes.code == 200) {
        let roles = [{ id: "user" }];
        let companyName = "";
        this.setCompanyName(companyName);
        this.setRoles(roles);
        this.$message.success("解散成功！");

        const closeRoute = "/resourceManagement/information";
        const route = "/dashboard";

        //关闭当前页面并跳转
        this.$closePage(closeRoute, route);

        //跳转后刷新个人信息界面
        this.$refreshPage(route);
      }
    },
  },
};
</script>

<style lang="less" scoped>
@import "index";
</style>