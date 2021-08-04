<template>
  <page-layout desc="">
    <div v-if="!isMobile" slot="extra" class="extraImg"></div>

    <template slot="extra">
      <Profile
        style="margin-left: 20px; display: inline-block; vertical-align: middle"
      ></Profile>
      <div style="display: inline-block; vertical-align: middle">
        <span
          style="
            font-size: 40px;
            font-weight: 500;
            margin-left: 20px;
            width: 500px;
          "
          >{{ username }}</span
        >
        <br />
        <span style="font-size: 16px; margin-left: 20px; width: 500px">{{
          description
        }}</span>
      </div>
    </template>

    <page-toggle-transition
      :disabled="animate.disabled"
      :animate="animate.name"
      :direction="animate.direction"
    >
      <a-card
        :body-style="{
          padding: '48px 62px',
          textAlign: 'left',
        }"
      >
        <a-form-model
          ref="form"
          :model="form"
          :rules="rules"
          :label-col="labelCol"
          :wrapper-col="wrapperCol"
          style="min-weight: 500px"
        >
          <a-form-model-item ref="name" prop="name" label="用户名">
            <a-input
              v-bind:disabled="!isEdit[0]"
              v-model="form.name"
              @blur="
                () => {
                  $refs.name.onFieldBlur();
                }
              "
            />
            <a-button v-show="!isShow[0]" type="link" @click="edit(1)">
              修改
            </a-button>
            <a-button v-show="isShow[0]" type="link" @click="confirm()">
              确认
            </a-button>
            <a-button
              v-show="isShow[0]"
              type="link"
              @click="cancel(1)"
              style="margin-left: 3%"
            >
              取消
            </a-button>
          </a-form-model-item>
          <a-form-model-item ref="sex" label="性别" prop="sex">
            <a-input
              v-bind:disabled="!isEdit[3]"
              v-if="!isShow[3]"
              v-model="form.sex"
            />
            <a-radio-group v-model="form.sex" v-if="isShow[3]">
              <a-radio-button value="男性"> 男性 </a-radio-button>
              <a-radio-button value="女性"> 女性 </a-radio-button>
            </a-radio-group>
            <a-button v-show="!isShow[3]" type="link" @click="edit(4)">
              修改
            </a-button>
            <a-button v-show="isShow[3]" type="link" @click="confirm()">
              确认
            </a-button>
            <a-button
              v-show="isShow[3]"
              type="link"
              @click="cancel(4)"
              style="margin-left: 3%"
            >
              取消
            </a-button>
          </a-form-model-item>
          <a-form-model-item ref="phone" label="手机号" required prop="phone">
            <a-input
              v-bind:disabled="!isEdit[1]"
              v-model="form.phone"
              @blur="
                () => {
                  $refs.phone.onFieldBlur();
                }
              "
            />
            <a-button v-show="!isShow[1]" type="link" @click="edit(2)">
              修改
            </a-button>
            <a-button v-show="isShow[1]" type="link" @click="confirm()">
              确认
            </a-button>
            <a-button
              v-show="isShow[1]"
              type="link"
              @click="cancel(2)"
              style="margin-left: 3%"
            >
              取消
            </a-button>
          </a-form-model-item>
          <a-form-model-item ref="email" label="邮箱" required prop="email">
            <a-input
              v-bind:disabled="!isEdit[2]"
              v-model="form.email"
              @blur="
                () => {
                  $refs.email.onFieldBlur();
                }
              "
            />
            <a-button v-show="!isShow[2]" type="link" @click="edit(3)">
              修改
            </a-button>
            <a-button v-show="isShow[2]" type="link" @click="confirm()">
              确认
            </a-button>
            <a-button
              v-show="isShow[2]"
              type="link"
              @click="cancel(3)"
              style="margin-left: 3%"
            >
              取消
            </a-button>
          </a-form-model-item>
          <a-form-model-item
            ref="password"
            label="密码"
            required
            prop="password"
          >
            <a-input
              disabled
              v-model="form.password"
              @blur="
                () => {
                  $refs.password.onFieldBlur();
                }
              "
            />
            <a-button type="link" @click="editPassword"> 修改 </a-button>
          </a-form-model-item>
          <a-form-model-item ref="companyName" label="企业" prop="companyName">
            <a-input
              v-bind:disabled="true"
              v-model="form.companyName"
              @blur="
                () => {
                  $refs.companyName.onFieldBlur();
                }
              "
              placeholder="暂未加入企业"
            />
            <a-button
              v-if="!form.companyName"
              type="link"
              @click="createCompany"
            >
              创建企业
            </a-button>
            <a-button
              v-if="!form.companyName"
              type="link"
              @click="addCompany"
              style="margin-left: 3%"
            >
              加入企业
            </a-button>
            <!-- 退出企业及气泡确认框 -->
            <a-popconfirm
              v-if="role == 'employee' || role === 'admin'"
              title="您确定要退出企业吗？"
              ok-text="确认"
              cancel-text="取消"
              @confirm="quitCompany"
            >
              <a-button type="link">退出企业</a-button>
            </a-popconfirm>
          </a-form-model-item>

          <a-form-model-item>
            <!-- 注销用户 -->
            <a-button
              type="link"
              style="float: left; margin-left: 15%"
              @click="deleteUser"
            >
              注销账号
            </a-button>
          </a-form-model-item>
          <!-- 修改密码的modal -->
          <a-modal
            title="修改密码"
            :visible="visible"
            @ok="handleOk"
            @cancel="handleCancel"
          >
            <a-form-model
              :model="passwordForm"
              ref="passwordForm"
              :rules="passwordRule"
            >
              <a-form-model-item prop="oldPassword">
                <a-input
                  placeholder="旧密码"
                  v-model="passwordForm.oldPassword"
                  addon-before="旧密码"
                >
                </a-input>
              </a-form-model-item>
              <a-form-model-item prop="newPassword">
                <a-input
                  placeholder="请输入密码"
                  :type="showPwd2 ? 'text' : 'password'"
                  v-model="passwordForm.newPassword"
                  addon-before="新密码"
                >
                  <a-icon
                    slot="suffix"
                    type="eye"
                    v-show="showPwd2"
                    @click="showPwd2 = !showPwd2"
                  />
                  <a-icon
                    slot="suffix"
                    type="eye-invisible"
                    v-show="!showPwd2"
                    @click="showPwd2 = !showPwd2"
                  />
                </a-input>
              </a-form-model-item>
              <a-form-model-item prop="confirmPassword">
                <a-input
                  placeholder="请确认密码"
                  :type="showPwd1 ? 'text' : 'password'"
                  v-model="passwordForm.confirmPassword"
                  addon-before="确认新密码"
                >
                  <a-icon
                    slot="suffix"
                    type="eye"
                    v-show="showPwd1"
                    @click="showPwd1 = !showPwd1"
                  />
                  <a-icon
                    slot="suffix"
                    type="eye-invisible"
                    v-show="!showPwd1"
                    @click="showPwd1 = !showPwd1"
                  />
                </a-input>
              </a-form-model-item>
            </a-form-model>
          </a-modal>
        </a-form-model>
      </a-card>
    </page-toggle-transition>
  </page-layout>
</template>

<script>
import { mapGetters, mapMutations, mapState } from "vuex";
import { quitCompany } from "@/services/company";
import { modifyUser, userPassword, deleteUser } from "@/services/user";
import { removeAuthorization } from "@/utils/request";
import md5 from "js-md5";
import Profile from "@/components/profile/index.vue";
import PageLayout from "@/layouts/PageLayout";
import PageToggleTransition from "@/components/transition/PageToggleTransition";
export default {
  components: {
    Profile,
    PageLayout,
    PageToggleTransition,
  },
  data() {
    return {
      page: {},
      discription: "",
      visible: false,
      showPwd1: false,
      showPwd2: false,
      isEdit: [false, false, false, false],
      isShow: [false, false, false, false],
      context: [1, 2, 3, 4],
      labelCol: { span: 4 },
      wrapperCol: { span: 14 },
      other: "",
      passwordForm: {
        oldPassword: "",
        newPassword: "",
        confirmPassword: "",
      },
      form: {
        name: "",
        phone: "",
        sex: "",
        email: "",
        password: "******",
        companyName: "",
        companyId: "",
      },
      role: "",
      passwordRule: {
        newPassword: [
          { required: true, message: "请输入密码", trigger: "blur" },
          { min: 6, max: 16, message: "密码长度为6-16个字符", trigger: "blur" },
        ],
        confirmPassword: [
          { required: true, message: "请确认密码", trigger: "blur" },
          { min: 6, max: 16, message: "密码长度为6-16个字符", trigger: "blur" },
        ],
      },
      rules: {
        name: [
          { required: true, message: "请输入账户名称", trigger: "blur" },
          { min: 3, message: "账户名长度至少为3个字符", trigger: "blur" },
        ],
        phone: [
          { required: true, message: "请输入手机号码", trigger: "blur" },
          {
            pattern: /^[1][3,4,5,6,7,8,9][0-9]{9}$/,
            message: "请填写正确的手机号",
            trigger: "blur",
          },
        ],
        email: [
          { required: true, message: "请填写邮箱", trigger: "blur" },
          {
            pattern:
              /^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*\.[a-zA-Z0-9]{2,6}$/,
            message: "邮箱格式不正确",
            trigger: "blur",
          },
          {
            type: "string",
            message: "长度不能超过30位",
            trigger: "blur",
            max: 30,
          },
        ],
        sex: [{ required: true, message: "请选择性别", trigger: "blur" }],
      },
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
    ...mapState("setting", ["isMobile", "multiPage", "animate"]),
    desc() {
      return this.page.desc;
    },
    linkList() {
      return this.page.linkList;
    },
    extraImage() {
      return this.page.extraImage;
    },
    description() {
      if (this.companyId) {
        return "亲爱的用户，您已加入企业" + this.companyName + "。";
      } else {
        return "亲爱的用户，您尚未加入企业，加入企业可以使用会议室预约服务噢~";
      }
    },
  },
  beforeMount() {
    this.show();
  },

  mounted() {
    this.page = this.$refs.page;
  },
  updated() {
    this.page = this.$refs.page;
  },
  methods: {
    // 渲染界面
    show() {
      this.form.name = this.username;
      this.form.phone = this.telephone;
      this.form.sex = this.sex;
      this.form.email = this.email;
      this.form.companyId = this.companyId;
      this.form.companyName = this.companyName;
      this.role = this.roles[0].id;
    },
    ...mapMutations("account", [
      "setUserName",
      "setSex",
      "setTelephone",
      "setEmail",
      "setRoles",
      "setCompanyName",
    ]),
    // 编辑按钮方法
    edit(num) {
      this.$set(this.isEdit, num - 1, !this.isEdit[num - 1]);
      this.$set(this.isShow, num - 1, !this.isShow[num - 1]);
      switch (num) {
        case 1:
          this.context[0] = this.form.name;
          break;
        case 2:
          this.context[1] = this.form.phone;
          break;
        case 3:
          this.context[2] = this.form.email;
          break;
        case 4:
          this.context[3] = this.form.sex;
          break;
      }
    },
    // 显示修改密码model
    editPassword() {
      this.visible = true;
    },
    // 提交修改密码请求
    handleOk() {
      this.$refs.passwordForm.validate((valid) => {
        if (valid) {
          if (
            this.passwordForm.newPassword !== this.passwordForm.confirmPassword
          ) {
            this.$message.error("密码与确认密码不一致，请重新输入！");
          } else {
            userPassword(
              this.userId,
              md5(this.passwordForm.oldPassword),
              md5(this.passwordForm.newPassword)
            ).then(this.afterUpdate);
          }
        } else {
          return false;
        }
      });
    },
    // 提交请求后
    afterUpdate(res) {
      const psdRes = res.data;
      if (psdRes.code == 301) {
        this.$message.error("原密码不正确，请重新输入！");
      } else {
        this.$message.success("修改密码成功，请重新登录！");
        localStorage.removeItem(process.env.VUE_APP_ROUTES_KEY);
        localStorage.removeItem(process.env.VUE_APP_ROLES_KEY);
        removeAuthorization();
        this.$router.push("/login");
      }
    },

    handleCancel() {
      this.visible = false;
    },
    // 确认修改请求提交
    confirm: function () {
      this.$refs.form.validate((valid) => {
        if (valid) {
          modifyUser(
            this.userId,
            this.form.name,
            this.form.phone,
            this.form.sex,
            this.form.email
          ).then(this.afterModity);
        } else {
          return false;
        }
      });
    },
    // 修改后的回显
    afterModity(res) {
      const modifyRse = res.data;
      if (modifyRse.code == 200) {
        this.setUserName(this.form.name);
        this.setTelephone(this.form.phone);
        this.setEmail(this.form.email);
        this.setSex(this.form.sex);
        this.$message.success("修改成功！");
        for (let i = 0; i < 4; i++) {
          this.$set(this.isEdit, i, false);
          this.$set(this.isShow, i, false);
        }
      }
    },
    // 取消按钮 输入框变为不可编辑状态
    cancel: function (num) {
      this.$set(this.isEdit, num - 1, !this.isEdit[num - 1]);
      this.$set(this.isShow, num - 1, !this.isShow[num - 1]);
      switch (num) {
        case 1:
          this.form.name = this.context[0];
          break;
        case 2:
          this.form.phone = this.context[1];
          break;
        case 3:
          this.form.email = this.context[2];
          break;
        case 4:
          this.form.sex = this.context[3];
          break;
      }
    },
    createCompany: function () {
      this.$router.push({ path: "/createcompany" });
    },
    addCompany: function () {
      this.$router.push({ path: "/addcompany" });
    },
    // 退出企业
    quitCompany: function () {
      quitCompany(this.userId, this.companyId).then(this.afterQuit);
    },
    afterQuit(res) {
      const quitRes = res.data;
      if (quitRes.code == 200) {
        let roles = [{ id: "user" }];
        let companyName = "";
        this.setCompanyName(companyName);
        this.setRoles(roles);
        this.$message.success("退出成功！");
        this.show(); // 局部刷新
      }
    },
    // 注销账号
    deleteUser() {
      this.$confirm({
        title: "你确定要注销账号吗？",
        content:
          "注销帐号是不可恢复的操作,在注销帐号后，您将无法登录使用该帐号,请谨慎操作！",
        onOk: () => {
          deleteUser(this.userId).then(this.afterDelete);
        },
        onCancel() {},
      });
    },

    afterDelete(res) {
      const delRes = res.data;
      if (delRes.code == 200) {
        this.$message.success("注销成功！");
        localStorage.removeItem(process.env.VUE_APP_ROUTES_KEY);
        localStorage.removeItem(process.env.VUE_APP_ROLES_KEY);
        removeAuthorization();
        this.$router.push({ path: "/login" });
      }
    },
  },
};
</script>

<style lang="less" scoped>
@import "index";
</style>