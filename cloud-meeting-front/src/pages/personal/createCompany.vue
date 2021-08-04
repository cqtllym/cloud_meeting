<template>
  <a-card
    :body-style="{
      padding: '24px 32px',
      paddingBottom: '64px',
      textAlign: 'left',
    }"
    :bordered="false"
  >
    <img
      src="../../assets/img/createCompany.png"
      style="width: 28%; min-width: 150px; margin-left: 30%"
    />
    <a-form :form="form" :label-col="labelCol" :wrapper-col="wrapperCol">
      <a-form-item
        ref="companyName"
        label="企业名称"
        required
        prop="companyName"
      >
        <a-input
          placeholder="请输入企业名称"
          v-decorator="[
            'companyName',
            {
              rules: [
                {
                  required: true,
                  message: '企业名称不能为空！',
                },
              ],
            },
          ]"
        />
      </a-form-item>
      <a-form-item ref="address" label="所在地区" prop="address">
        <a-input placeholder="请输入企业地区" v-decorator="['address']" />
      </a-form-item>
      <a-form-item ref="type" label="行业类型" prop="type">
        <a-select placeholder="请选择行业类型" v-decorator="['type']">
          <a-select-option value="大数据"> 大数据 </a-select-option>
          <a-select-option value="电子商务"> 电子商务 </a-select-option>
          <a-select-option value="服务业"> 服务业 </a-select-option>
          <a-select-option value="个人用户"> 个人用户 </a-select-option>
          <a-select-option value="互联网"> 互联网 </a-select-option>
          <a-select-option value="建筑和地产"> 建筑和地产 </a-select-option>
          <a-select-option value="教育"> 教育 </a-select-option>
          <a-select-option value="金融"> 金融 </a-select-option>
          <a-select-option value="医疗健康"> 医疗健康 </a-select-option>
          <a-select-option value="游戏媒体"> 游戏媒体 </a-select-option>
          <a-select-option value="能源"> 能源 </a-select-option>
          <a-select-option value="生产制造"> 生产制造 </a-select-option>
          <a-select-option value="IT通信"> IT通信 </a-select-option>
          <a-select-option value="政府和政务"> 政府和政务 </a-select-option>
        </a-select>
      </a-form-item>

      <a-form-item :wrapper-col="{ span: 14, offset: 4 }">
        <a-button type="primary" @click="onSubmit" style="margin-left: 15%">
          创建
        </a-button>
        <a-button @click="cancle" style="margin-left: 15%"> 取消 </a-button>
      </a-form-item>
    </a-form>
  </a-card>
</template>

<script>
import { createCompany } from "@/services/company";
import { mapGetters, mapMutations } from "vuex";
export default {
  data() {
    return {
      labelCol: { span: 4 },
      wrapperCol: { span: 14 },
    };
  },
  beforeCreate() {
    this.form = this.$form.createForm(this);
  },
  computed: {
    ...mapGetters("account", ["username", "userId", "roles"]),
  },
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
    // 提交创建请求
    onSubmit(e) {
      e.preventDefault();
      this.form.validateFieldsAndScroll((err) => {
        if (!err) {
          //表格数据无误
          const companyName = this.form.getFieldValue("companyName");
          const companyAddress = this.form.getFieldValue("address");
          const companyType = this.form.getFieldValue("type");
          createCompany(
            this.userId,
            companyName,
            companyAddress,
            companyType
          ).then(() => {
            let roles = [{ id: "founder" }];
            this.setCompanyName(companyName);
            this.setRoles(roles);
            this.$message.success("创建成功！");

            const closeRoute = "/createcompany";
            const newRoute = "/resourceManagement/information";
            const route = "/dashboard";
            //创建后则不能再次进入该界面，必须关闭当前页面再跳转
            this.$closePage(closeRoute, newRoute);

            //刷新个人信息界面
            this.$refreshPage(route);
          });
        }
      });
    },
    cancle() {
      const closeRoute = "/createcompany";
      const newRoute = "/dashboard";
      //关闭当前页面并跳转
      this.$closePage(closeRoute, newRoute);
    },
  },
};
</script>

<style scoped>
.ant-form {
  width: 80%;
  margin: auto;
}
</style>