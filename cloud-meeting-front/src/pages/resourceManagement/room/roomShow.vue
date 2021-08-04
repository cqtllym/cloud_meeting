<template>
  <!-- 会议室展示组件 -->
  <a-col :span="8">
    <a-card hoverable style="width: 300px; margin-top: 2%">
      <template slot="actions" class="ant-card-actions">
        <a-tooltip
          v-if="(role == 'founder' || role == 'admin') && !resInfo.status"
          placement="topLeft"
          title="更改名称"
          arrow-point-at-center
        >
          <a-icon key="edit" type="edit" @click="edit" />
        </a-tooltip>
        <a-tooltip
          v-if="
            (role == 'founder' || role == 'admin') &&
            !resInfo.isActive &&
            !resInfo.status
          "
          placement="topLeft"
          title="启用"
          arrow-point-at-center
          @click="change(resInfo.id, true)"
        >
          <a-icon type="check-circle" />
        </a-tooltip>
        <a-tooltip
          v-if="
            (role == 'founder' || role == 'admin') &&
            resInfo.isActive &&
            !resInfo.status
          "
          placement="topLeft"
          title="停用"
          arrow-point-at-center
          @click="change(resInfo.id, false)"
        >
          <a-icon type="stop" />
        </a-tooltip>
        <a-popconfirm
          v-if="(role == 'founder' || role == 'admin') && !resInfo.status"
          title="你要删除这个会议室吗？"
          ok-text="确认"
          cancel-text="取消"
          @confirm="deleteRoom(resInfo.id)"
          @cancel="cancel"
        >
          <a-tooltip placement="topLeft" title="删除" arrow-point-at-center>
            <a-icon type="close-circle" />
          </a-tooltip>
        </a-popconfirm>
        <a-tooltip
          v-if="resInfo.status"
          placement="topLeft"
          title="暂无可进行操作"
          arrow-point-at-center
        >
          <a-icon type="bulb" />
        </a-tooltip>
      </template>
      <a-card-meta :title="resInfo.name"></a-card-meta>
      <a-result
        v-if="resInfo.status"
        title="正在进行会议"
        sub-title="剩余时间："
      >
        <template v-if="resInfo.status" #icon>
          <a-icon type="smile" theme="twoTone" />
        </template>
      </a-result>
      <a-result
        v-if="!resInfo.status && resInfo.isActive"
        status="success"
        title="会议室空闲"
        sub-title="可进行会议活动"
      >
      </a-result>
      <a-result
        v-if="!resInfo.status && !resInfo.isActive"
        status="error"
        title="会议室停用"
        sub-title="管理员可启用"
      >
      </a-result>
      <a-modal
        title="编辑会议室名称"
        :visible="visible"
        @ok="editOk(resInfo.id)"
        @cancel="editCancel"
      >
        <a-form :form="form">
          <a-form-item ref="roomName" label="编辑会议室名称" prop="roomName">
            <a-input
              v-decorator="[
                'roomName',
                {
                  rules: [
                    {
                      required: true,
                      message: '名称不能为空！',
                    },
                  ],
                },
              ]"
            />
          </a-form-item>
        </a-form>
      </a-modal>
    </a-card>
  </a-col>
</template>

<script>
import { mapGetters } from "vuex";
import { changeRoom, editRoom, deleteRoom } from "@/services/room";
import { getRoomReservationList } from "@/services/reservation.js";
export default {
  name: "roomCom",
  props: {
    resInfo: {
      id: "",
      name: "",
      companyId: "",
      departmentId: "",
      status: false,
      isActive: false,
      deleted: false,
    },
  },
  data() {
    return {
      role: "",
      visible: false,
    };
  },
  computed: {
    ...mapGetters("account", ["username", "userId", "companyId", "roles"]),
  },
  beforeCreate() {
    this.form = this.$form.createForm(this, "form");
  },
  created() {
    this.role = this.roles[0].id;
  },
  methods: {
    edit() {
      this.visible = true;
    },
    // 提交更改名字请求
    editOk(id) {
      this.form.validateFieldsAndScroll((err) => {
        if (!err) {
          const name = this.form.getFieldValue("roomName");
          editRoom(id, name).then((res) => {
            const editRes = res.data;
            if (editRes.code == 200) {
              this.$message.success("会议室名称更改成功！");
              this.visible = false;
              this.$refreshPage("/resourceManagement/room");
            }
          });
        }
      });
    },
    editCancel() {
      this.visible = false;
    },
    // 删除会议室
    deleteRoom(id) {
      let data = {
        roomId: id,
      };
      getRoomReservationList(data).then((res) => {
        const getRes = res.data;
        if (getRes.code == 200) {
          const dataList = getRes.data;
          var scheduledEndTimeList = [];
          for (var i = 0; i < dataList.length; i++) {
            scheduledEndTimeList[i] = dataList[i].scheduledEndTime;
          }
          var myDate = new Date();
          var nowTime = myDate.getTime();
          var count = 0;
          for (var j = 0; j < scheduledEndTimeList.length; j++) {
            if (scheduledEndTimeList[j] > nowTime) {
              count++;
            }
          }
          if (count > 0) {
            this.$message.error("该会议室存在已预约会议，无法删除");
          } else {
            deleteRoom(id).then((res) => {
              const deleteRes = res.data;
              if (deleteRes.code == 200) {
                this.$message.success("删除成功！");
                const route = "/resourceManagement/room";
                this.$refreshPage(route);
              }
            });
          }
        }
      });
    },
    change(id, result) {
      if (result == true) {
        changeRoom(id, result).then((res) => {
          const changeRes = res.data;
          if (changeRes.code == 200) {
            this.$message.success("启用会议室成功！");
            const route = "/resourceManagement/room";
            this.$refreshPage(route);
          }
        });
      } else {
        let data = { roomId: id };
        getRoomReservationList(data).then((res) => {
          const getRes = res.data;
          if (getRes.code == 200) {
            const dataList = getRes.data;
            var scheduledEndTimeList = [];
            for (var i = 0; i < dataList.length; i++) {
              scheduledEndTimeList[i] = dataList[i].scheduledEndTime;
            }
            var myDate = new Date();
            var nowTime = myDate.getTime();
            var count = 0;
            for (var j = 0; j < scheduledEndTimeList.length; j++) {
              if (scheduledEndTimeList[j] > nowTime) {
                count++;
              }
            }
            if (count > 0) {
              this.$message.error("该会议室存在已预约会议，无法停用");
            } else {
              changeRoom(id, result).then((res) => {
                const changeRes = res.data;
                if (changeRes.code == 200) {
                  this.$message.success("停用会议室成功！");
                  const route = "/resourceManagement/room";
                  this.$refreshPage(route);
                }
              });
            }
          }
        });
      }
    },
    cancel() {},
  },
};
</script>

<style scoped>
</style>