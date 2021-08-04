<template>
  <a-card
    :body-style="{
      padding: '24px 32px',
      paddingBottom: '64px',
    }"
    :bordered="false"
  >
    <a-tooltip
      v-if="role == 'founder' || role == 'admin'"
      placement="topLeft"
      title="添加会议室"
      arrow-point-at-center
    >
      <a-button type="primary" icon="plus-circle" @click="add" />
    </a-tooltip>
    <a-row :gutter="16">
      <roomCom
        v-for="(item, key) in data"
        :key="key"
        :res-info="item"
      ></roomCom>
    </a-row>
    

    <a-modal
      title="添加会议室"
      :visible="visableRoom"
      @ok="handleAdd"
      @cancel="handleCancel"
    >
      <a-form :form="formRoom">
        <a-form-item ref="roomName" label="输入会议室名字" prop="roomName">
          <a-input
            v-decorator="[
              'roomName',
              {
                rules: [
                  {
                    required: true,
                    message: '名字不能为空！',
                  },
                ],
              },
            ]"
          />
        </a-form-item>
      </a-form>
    </a-modal>
  </a-card>
</template>

<script>
import roomCom from "./roomShow";
import { mapGetters } from "vuex";
import { roomList, createRoom } from "@/services/room";
export default {
  data() {
    return {
      isShow: false,
      visableRoom: false,
      data: [],
      role: "",
    };
  },
  components: {
    roomCom,
  },
  beforeCreate() {
    this.formRoom = this.$form.createForm(this, "formRoom");
  },
  created() {
    this.showList();
    this.role = this.roles[0].id;
  },
  computed: {
    ...mapGetters("account", ["username", "userId", "companyId", "roles"]),
  },
  methods: {
    showList() {
      roomList(this.companyId).then((res) => {
        this.data = res.data.data;
      });
    },
    add() {
      this.visableRoom = true;
    },

    // 提交添加会议室请求
    handleAdd: function () {
      this.formRoom.validateFieldsAndScroll((err) => {
        if (!err) {
          const departmentId = "";
          const roomName = this.formRoom.getFieldValue("roomName");
          createRoom(this.companyId, departmentId, roomName).then((res) => {
            const addRes = res.data;
            if (addRes.code == 200) {
              this.$message.success("新增会议室成功！");
              this.visableRoom = false;
              this.showList();
            }
          });
        }
      });
    },
    // 取消
    handleCancel() {
      this.visableRoom = false;
    },
  },
};
</script>

<style scoped>
</style>