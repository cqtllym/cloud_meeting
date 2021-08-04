<template>
  <a-card
    :body-style="{
      padding: '24px 32px',
      paddingBottom: '64px',
      textAlign: 'center',
    }"
    :bordered="false"
  >
    <!-- template下面必须仅仅有一个根元素如上面的div，其他html代码在这里面写，因为vue是挂载在一个根元素上的~-->
    <img
      src="../../assets/img/instantMeeting.png"
      style="width: 30%; min-width: 150px; margin: 0 auto"
    />
    <a-form-model
      :model="form"
      :label-col="{ span: 8 }"
      :wrapper-col="{ span: 10 }"
      :selfUpdate="true"
      :rules="rules"
      ref="form"
      style="margin: 0 auto; text-align: center"
    >
      <a-form-model-item label="会议主题" prop="topic">
        <a-input
          v-model="form.topic"
          :placeholder="'默认为' + username + '发起的立即会议'"
        ></a-input>
      </a-form-model-item>
      <!-- <a-form-model-item
        label="使用密码"
        prop="requirePwd"
        style="text-align: left"
      >
        <a-switch v-model="form.requirePwd"> </a-switch>
      </a-form-model-item> -->
      <a-form-model-item
        label="会议密码"
        prop="password"
      >
        <a-input v-model="form.password" placeholder="默认无密码">
        </a-input>
      </a-form-model-item>
    </a-form-model>

    <a-button type="primary" style="margin: 0 auto" @click="handleSubmit"
      >立即开始会议</a-button
    >
  </a-card>
</template>

<script>
import { InitInstantMeeting } from '@/services/instantMeeting.js'
import { mapGetters } from 'vuex'

export default {
  data() {
    return {
      form: {
        topic: '',
        requirePwd: false,
        password: ''
      },
      rules: {
        topic: [
          // { required: true, message: '请填写会议主题', trigger: 'blur' },
          { max: 20, message: '会议主题不要超过20字', trigger: 'blur' },
        ],
        password: [
          { pattern: new RegExp(/^[1-9]\d*$/, "g"), message: '会议密码必须为数字', trigger: 'blur' },
          { max: 8, message: '会议密码必须为8位以内', trigger: 'blur' },
          // { required: true, message: '请填写会议密码', trigger: 'blur' },
        ],
      },
    }
  },
  created() {
    //页面初始化时的操作可以写在这
  },
  watch: {
    // 'form.requirePwd': {
    //   handler: function (val) {
    //     if (!val) {
    //       this.form.password = ''
    //     }
    //   }
    // },
    'form.password': {
      handler: function (val) {
        if (!val) {
          this.form.requirePwd = false
        } else {
          this.form.requirePwd = true
        }
      }
    },
  },
  computed: {
    ...mapGetters('account', ['username', 'userId']),
  },
  methods: {//一些方法在这里写
    handleSubmit() {
      this.$refs.form.validate(valid => {
        if (valid) {
          let data = {
            topic: this.form.topic != '' ? this.form.topic : (this.username + '发起的立即会议'),
            hostId: this.userId,
            requirePwd: this.form.requirePwd ? 1 : 2,
            password: this.form.password
          }

          InitInstantMeeting(data).then(res => {
            if (res.data.code == 200) {
              let meetingNumber = res.data.data.meetingNumber
              let meetingId = res.data.data.meetingId

              let routerurl = this.$router.resolve({
                path: '/meetingOn', query: { meetingNumber: meetingNumber, meetingId: meetingId }
              });
              window.open(routerurl.href, '_blank');

            } else {
              this.$message.error(res.errMsg)
            }
            this.form.topic = ''//操作完了初始化一下
            this.form.password=''

          }).catch(err => {
            console.log(err)
          })
        } else {
          return false;
        }
      });
    }
  },
}
</script>

<style>
</style>