<template>
  <div>
    <div>
      <a-card
        :bordered="false"
        style="padding: 0; margin: 0 auto; min-height: 490px"
      >
        <img
          src="../../assets/img/joinMeeting.png"
          style="width: 25%; min-width: 150px; margin: 0 auto; margin-left: 34%"
        />
        <a-row>
          <a-col :span="24">
            <a-form-model
              ref="ruleForm"
              :model="form"
              :rules="rules"
              :label-col="labelCol"
              :wrapper-col="wrapperCol"
              style="margin-left: 30%"
              @submit="onSubmit"
            >
              <a-form-model-item
                ref="Mnumber"
                label="会议号"
                prop="Mnumber"
                :label-col="{ span: 4, offset: -4 }"
                :wrapper-col="{ span: 7 }"
              >
                <a-input
                  placeholder="输入会议号"
                  v-model="form.Mnumber"
                  style="width: 150px"
                />
              </a-form-model-item>
              <a-form-model-item :wrapper-col="{ span: 14, offset: 4 }">
                <a-button
                  :loading="enterloading"
                  type="primary"
                  style=""
                  htmlType="submit"
                >
                  加入
                </a-button>
                <a-button style="margin-left: 10px" @click="resetForm">
                  重置
                </a-button>
              </a-form-model-item>
            </a-form-model>
          </a-col>
        </a-row>
      </a-card>
    </div>
    <div>
      <a-modal
        ref="attention"
        title="提醒"
        :visible="attention_visible"
        @cancel="handleCancel"
        :footer="null"
      >
        <p>{{ ModalText }}</p>
        <a-form-model
          :model="form"
          :rules="rules"
          :label-col="labelCol"
          :wrapper-col="wrapperCol"
          v-if="this.needpassword"
        >
          <a-form-model-item ref="Mpassword" prop="Mpassword">
            <a-input
              placeholder="输入密码"
              type="password"
              addon-before="密码"
              v-model="form.Mpassword"
            />
          </a-form-model-item>
        </a-form-model>
        <a-button
          key="check"
          type="primary"
          @click="handleOk"
          style="margin-right: 10px"
        >
          提前加入会议
        </a-button>
        <a-button
          key="reReserve"
          type="primary"
          @click="handleAdd"
          style="margin-right: 10px"
        >
          添至我的会议
        </a-button>
        <a-button @click="handleCancel">取消</a-button>
      </a-modal>
    </div>
    <div>
      <a-modal
        ref="needpassword"
        title="请输入密码"
        :visible="needpassword_visible"
        :footer="null"
        @ok="handleOk2"
        @cancel="handleCancel"
      >
        <p>会议正在进行中,请输入密码加入会议</p>
        <a-form-model
          ref="ruleForm"
          :model="form"
          :rules="rules"
          :label-col="labelCol"
          :wrapper-col="wrapperCol"
        >
          <a-form-model-item ref="Mpassword" prop="Mpassword">
            <a-input
              placeholder="输入密码"
              type="password"
              addon-before="密码"
              v-model="form.Mpassword"
            />
          </a-form-model-item>
        </a-form-model>
        <a-button
          key="check"
          type="primary"
          @click="handleOk2"
          style="margin-right: 10px"
        >
          加入会议
        </a-button>
        <a-button @click="handleCancel">取消</a-button>
      </a-modal>
    </div>
  </div>
</template>

<script>
import { getM } from "@/services/getmeetingstate";
import { ConfirmMpwd, AddMeeting } from "@/services/confirm_mpwd";
import { mapGetters } from 'vuex'
export default {
  data() {
    return {
      ModalText: "会议尚未开始，【提前进入会议】还是【加入至预约的会议】？",
      attention_visible: false,
      needpassword_visible: false,
      confirmLoading: false,
      enterloading: false,
      labelCol: { span: 4 },
      wrapperCol: { span: 14 },
      other: "",
      needpassword: false,
      form: {
        Mnumber: "",
        Mpassword: "",
        name: "",
        region: undefined,
        date1: undefined,
        delivery: false,
        type: [],
        resource: "",
        desc: "",
      },
      rules: {
        Mnumber: [
          { required: true, message: "请输入会议号", trigger: "blur" },
        ],
        Mpassword: [
          { required: false, message: "请输入密码", trigger: "blur" },
        ],
        name: [
          {
            required: false,
            message: "请输入昵称，默认为用户名",
            trigger: "blur",
          },
        ],
      },
    };
  },
  computed: {
    ...mapGetters("account", [
      "username",
      "userId"
    ]),
  },
  methods: {
    /**
     * 第二次迭代增加 ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓
     */
    showModal1() {
      this.attention_visible = true;
      this.form.Mpassword = ''
    },
    showModal2() {
      this.needpassword_visible = true;
      this.form.Mpassword = ''
    },
    handleOk() {//加入会议
      if (this.needpassword) {
        //调用判断密码是否正确的接口，正确则进入会议，错误则提示密码错误
        ConfirmMpwd(this.form.Mnumber, this.form.Mpassword).then(res => {
          let meetingNumber = this.form.Mnumber;
          if (res.data.data.confirmResult == false) {
            this.form.Mpassword = ''
            this.$message.error('密码错误,请重新输入!')
          } else if (res.data.data.confirmResult == true) {
            this.$message.success('密码正确，成功加入会议：' + meetingNumber + '！')
            this.resetForm()
            this.attention_visible = false
            this.needpassword_visible = false
            this.needpassword = false
            this.enterMeeting()
          }
        })
      } else {
        this.enterMeeting()
      }
    },
    handleAdd() {
      //将会议加入【我的会议】方法在这里写
      if (this.needpassword) {
        //以会议号和密码调用添加会议的接口，正确则将该会议加入到我的会议，错误则提示密码错误
        console.log('ok')

        AddMeeting(this.form.Mnumber, this.needpassword, this.form.Mpassword).then(res => {
          if (res.data.data.confirmResult == true) {
            this.attention_visible = false;
            this.needpassword_visible = false;
            this.resetForm();
            this.$message.success('已添加会议，可前往【我的会议】查看！')
          } else {
            this.form.Mpassword = ''
            this.$message.error('会议密码错误，请重新输入！')
          }
        })
      } else {
        AddMeeting(this.form.Mnumber, this.needpassword, this.form.Mpassword).then(res => {
          if (res.data.data.confirmResult == true) {
            let meetingNumber=this.form.Mnumber
            this.attention_visible = false;
            this.needpassword_visible = false;
            this.resetForm();
            this.$message.success('已添加会议 '+meetingNumber+' ，可前往【我的会议】查看！')
          } 
        })
      }
    },
    handleCancel() {
      this.attention_visible = false;
      this.needpassword_visible = false;
      this.resetForm();
    },
    handleOk2() {
      //调用判断密码是否正确的接口，正确则进入会议，错误则提示密码错误
      ConfirmMpwd(this.form.Mnumber, this.form.Mpassword).then(res => {
        let meetingNumber = this.form.Mnumber;
        if (res.data.data.confirmResult == false) {
          this.form.Mpassword = ''
          this.$message.error('密码错误,请重新输入!')
        } else if (res.data.data.confirmResult == true) {
          this.$message.success('密码正确，成功加入会议：' + meetingNumber + '！')
          this.resetForm()
          this.attention_visible = false
          this.needpassword_visible = false
          this.needpassword = false
          this.enterMeeting()
        }
      })
    },
    onSubmit(e) {
      e.preventDefault()
      this.$refs.ruleForm.validate((valid) => {
        if (valid) {
          this.enterloading = true;
          let meetingNumber = this.form.Mnumber;
          getM(meetingNumber, this.userId).then(this.confirmMeetingState)
          this.enterloading = false;
        } else {
          console.log("输入错误");
          this.enterloading = false;
          return false;
        }
      });
    },
    //判断密码是否正确
    confirmMeetingPassword(res) {
      let meetingNumber = this.form.Mnumber;
      if (res.data.data.confirmResult === false) {
        this.$message.error('密码错误')
      } else if (res.data.data.confirmResult === true) {
        this.needpassword = false
        this.enterMeeting();
        this.$message.success('成功加入会议，会议号为：' + meetingNumber + '！')
      }
    },
    // 判断会议状态
    confirmMeetingState(res) {
      if (res.data.code === 501) {//会议不存在
        this.$message.error('会议不存在，请核对会议号！')
        this.enterloading = false;
      } else {
        if (res.data.data.status === 100) {//会议未开始
          this.enterloading = false;
          this.needpassword = res.data.data.needpassword;
          this.showModal1();
        } else if (res.data.data.status === 200) {
          //调用判断是否需要密码的接口，如果需要密码则显示密码框
          this.needpassword = res.data.data.needpassword
          if (this.needpassword == true) {
            this.showModal2();
          } else if (this.needpassword == false) {
            this.enterMeeting()
          }
        } else if (res.data.data.status === 300) {
          this.enterloading = false;
          this.$message.error('会议已经结束，无法加入！')
        } else if (res.data.data.status === 400) {
          this.enterloading = false;
          this.$message.error('没有权限，无法加入会议')
        }
      }
    },
    enterMeeting() {
      let meetingNumber = this.form.Mnumber;
      let routerurl = this.$router.resolve({
        path: '/meetingOn', query: { meetingNumber: meetingNumber }
      });
      window.open(routerurl.href, '_blank');
      this.$message.success('成功加入会议，会议号为：' + meetingNumber + '！')
    },
    resetForm() {
      this.$refs.ruleForm.resetFields();
    },

  },
};
</script>



