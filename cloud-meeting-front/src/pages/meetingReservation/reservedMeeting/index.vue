<template>
  <a-card
    :body-style="{
      padding: '24px 32px',
      paddingBottom: '64px',
      textAlign: 'center',
    }"
    :bordered="false"
  >
    <img
      src="../../../assets/img/reservedMeeting.png"
      style="width: 30%; min-width: 150px"
    />
    <a-form-model
      ref="form"
      :model="form"
      :label-col="{ span: 8 }"
      :wrapper-col="{ span: 10 }"
      :selfUpdate="true"
      :rules="rules"
    >
      <a-form-model-item label="会议主题" prop="topic">
        <a-input
          v-model="form.topic"
          :placeholder="'默认为' + username + '预约的会议'"
        >
          <a-icon type="file" style="color: #c7c7c7" slot="suffix" />
        </a-input>
      </a-form-model-item>
      <a-form-model-item label="开始时间" prop="startTime">
        <a-date-picker
          v-model="form.startTime"
          show-time
          type="date"
          placeholder="请选择开始时间"
          style="width: 100%"
          :disabled-date="disabledDate"
          :allow-clear="true"
        />
      </a-form-model-item>
      <a-form-model-item label="会议时长" prop="duration">
        <a-time-picker
          v-model="form.duration"
          format="HH:mm"
          :minute-step="10"
          :disabledHours="getDisabledHours"
          placeholder="请选择会议时长"
          style="width: 100%"
        />
      </a-form-model-item>
      <a-form-model-item label="会议密码" prop="password">
        <a-input v-model="form.password" placeholder="默认无会议密码">
          <a-icon type="eye" style="color: #c7c7c7" slot="suffix" />
        </a-input>
      </a-form-model-item>
    </a-form-model>
    <a-button type="primary" style="margin-right: 10px" @click="handleSubmit"
      >预约会议</a-button
    >
    <a-button @click="resetForm">重置信息</a-button>

    <a-modal :visible="visible" @cancel="visible = !visible" :footer="null">
      <a-result
        status="success"
        :title="'您已成功预约会议 ' + meetingNumber + ' ！'"
        :sub-title="successMsg"
      >
        <template #extra>
          <a-button
            key="check"
            type="link"
            @click="goTo('/meetingManagement/my')"
            size="large"
          >
            前往我的会议查看
          </a-button>
        </template>
      </a-result>
    </a-modal>
  </a-card>
</template>

<script>
import moment from "moment";
import { mapGetters } from "vuex";
import { createCommonReservation } from "@/services/reservation.js";
// import { AddMeeting } from '@/services/confirm_mpwd.js'

export default {
  data() {
    return {
      form: {
        topic: "",
        startTime: "",
        duration: "",
        requiredPassword: false,
        password: "",
        isEmployee: null,
      },
      rules: {
        topic: [
          { max: 20, message: "会议主题不要超过20字", trigger: "change" },
        ],
        startTime: [
          { required: true, message: "请选择开始时间", trigger: "change" },
        ],
        duration: [
          { required: true, message: "请选择会议时长", trigger: "change" },
        ],
        password: [
          {
            pattern: new RegExp(/^[1-9]\d*$/, "g"),
            message: "会议密码必须为数字",
            trigger: "change",
          },
          { max: 6, message: "会议密码必须为4-6位", trigger: "change" },
          { min: 4, message: "会议密码必须为4-6位", trigger: "change" },
        ],
      },
      visible: false,
      meetingId: "",
      meetingNumber: "",
    };
  },
  watch: {
    //侦听属性
    "form.password": {
      handler: function (val) {
        if (!val) {
          this.form.requiredPassword = false;
        } else {
          this.form.requiredPassword = true;
        }
      },
    },
  },
  computed: {
    //计算属性
    ...mapGetters("account", ["username", "userId", "companyId"]),
    successMsg() {
      if (this.form.startTime && this.form.duration) {
        return (
          "会议预计开始时间 " +
          this.form.startTime.toDate().toLocaleString() +
          " , 预计持续" +
          this.form.duration.toDate().getHours() +
          "时" +
          this.form.duration.toDate().getMinutes() +
          "分" +
          (this.form.requiredPassword
            ? ",\n会议密码为" + this.form.password + "。"
            : "。")
        );
      } else {
        return "";
      }
    },
  },
  created() {
    this.form.duration = moment(new Date(2013, 0, 0, 0, 30, 0, 0).getTime());
  },
  methods: {
    moment,
    disabledDate(endValue) {
      let startValue = new Date();
      startValue.setDate(startValue.getDate() - 1);
      if (!endValue || !startValue) {
        return false;
      }
      return startValue.valueOf() >= endValue.valueOf();
    },
    handleSubmit() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          let now = new Date();
          if (this.form.startTime.toDate().valueOf() < now.valueOf()) {
            this.$message.error(
              "预约会议的开始时间小于当前时间，请重新选择时间！"
            );
            this.form.startTime = "";
          } else {
            this.handleCommonReservation();
          }
        } else {
          console.log("表单校验不成功!!");
          return false;
        }
      });
    },
    handleCommonReservation() {
      Date.prototype.clone = function () {
        return new Date(this.valueOf());
      };

      let startTime = this.form.startTime.toDate();
      let endTime = startTime.clone();
      let duration = this.form.duration.toDate();

      endTime.setHours(startTime.getHours() + duration.getHours());
      endTime.setMinutes(startTime.getMinutes() + duration.getMinutes());

      let data = {
        topic: this.form.topic ? this.form.topic : this.username + "预约的会议",
        scheduledStartTime: startTime.getTime(),
        scheduledEndTime: endTime.getTime(),
        hostId: this.userId,
        requiredPassword: this.form.requiredPassword,
        password: this.form.password,
      };
      createCommonReservation(data).then((res) => {
        this.meetingId = res.data.data.meetingId;
        this.meetingNumber = res.data.data.meetingNumber;

        // AddMeeting(res.data.data.meetingNumber, this.form.requiredPassword,this.form.password).then(res => {
        //   if (res.data.code == 200) {
        this.visible = true;
        // }
        // })
      });
    },
    resetForm() {
      this.form.topic = "";
      this.form.startTime = "";
      this.form.duration = moment(new Date(2013, 0, 0, 0, 30, 0, 0).getTime());
      this.form.requiredPassword = false;
      this.form.password = "";
      this.meetingNumber = "";
      this.meetingId = "";
    },
    goTo(path) {
      this.$closePage(this.$route);
      this.$router.push(path);
    },
    handleCancel() {
      // this.resetForm()
      this.visible = false;
    },
    getDisabledHours() {
      let hours = [3, 4, 5, 6, 7, 8, 9, 10, 11, 12];
      return hours;
    },
  },
};
</script>

<style scoped>
</style>