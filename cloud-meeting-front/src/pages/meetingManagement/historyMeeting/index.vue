<template>
  <a-card
    :body-style="{
      padding: '24px 32px',
      paddingBottom: '64px',
    }"
    :bordered="false"
  >
    <a-tabs default-active-key="1">
      <!-- 立即会议的历史记录 -->
      <a-tab-pane key="1">
        <span slot="tab" @click="getInstantList">立即会议 </span>
        <a-row :gutter="20" style="margin-bottom: 15px; margin-top: 25px">
          <a-col :span="6">
            <a-range-picker :ranges="{ 清空: ['', ''] }" @change="onChange" />
          </a-col>
          <a-col :span="1">
            <a-button @click="getInstantList" type="primary">
              <a-icon type="search" />
            </a-button>
          </a-col>
        </a-row>
        <!-- 表格 -->
        <a-table
          :rowKey="(record, index) => record.meeting_id"
          :columns="columns"
          :data-source="instantData"
          :pagination="{ pageSize: 10 }"
          style="background: white; padding: 10px"
        >
          <span slot="start_time" slot-scope="start_time">
            {{ getLocaleDateTime(start_time) }}
          </span>
          <span slot="end_time" slot-scope="end_time">
            {{ getLocaleDateTime(end_time) }}
          </span>
          <span slot="action" slot-scope="text, record">
            <a
              @click="showInstantAttendee(record.participants, record.meeting_number)"
            >
              查看参会人员
            </a>
            <a-divider type="vertical" />
            <a-button
              type="link"
              @click="
                showInstantRecord(
                  record.type,
                  record.meeting_id,
                  record.meeting_number
                )
              "
            >
              查看会议记录
            </a-button>
            <a-divider type="vertical" />
          </span>
        </a-table>
        <!-- 参会人员列表 -->
        <a-modal
          v-model="attendee_instant_visible"
          :title="attendee_instant_meeting_title"
          @cancel="cancelshowInstantAttendee"
          :footer="null"
        >
          <a-empty v-if="attendee_instant_list.length == 0" />
          <a-table
            :rowKey="(record, index) => record.user_id"
            :columns="col"
            :data-source="attendee_instant_list"
            :pagination="{ pageSize: 10 }"
            style="background: white; padding: 10px"
            v-else
          >
          </a-table>
        </a-modal>

        <!-- 会议记录 -->
        <a-modal
          :visible="record_instant_visible"
          @cancel="cancelshowInstantRecord"
          :title="record_instant_meeting_title"
          :bodyStyle="{ padding: '10px' }"
          :footer="null"
        >
          <a-empty v-if="record_instant_list.length == 0" />
          <div class="chatbox" id="chat-box" v-else>
            <template v-for="(item, index) in record_instant_list">
              <div style="text-align: right" :key="'1' + index"></div>
              <div
                :key="'2' + index"
                class="right"
                v-if="item.userId == userId"
              >
                <span>{{ item.time }} &nbsp; {{ item.userName }}</span>
              </div>
              <div :key="'3' + index" class="left" v-else>
                <span>{{ item.time }} &nbsp; {{ item.userName }}</span>
              </div>
              <Msg
                :msg="toEmoji(item.msg)"
                :send="item.userId == userId"
                :key="'4' + index"
                :username="item.userName"
              />
            </template>
          </div>
        </a-modal>
      </a-tab-pane>

      <a-tab-pane key="2">
        <span slot="tab" @click="getReservationList">预约会议 </span>
        <a-row :gutter="20" style="margin-bottom: 15px; margin-top: 25px">
          <a-col :span="6">
            <a-range-picker :ranges="{ 清空: ['', ''] }" @change="onChange" />
          </a-col>
          <a-col :span="1">
            <a-button @click="getReservationList" type="primary">
              <a-icon type="search" />
            </a-button>
          </a-col>
        </a-row>
        <!-- 表格 -->
        <a-table
          :rowKey="(record, index) => record.meeting_id"
          :columns="columns"
          :data-source="reservationData"
          :pagination="{ pageSize: 10 }"
          style="background: white; padding: 10px"
        >
          <span slot="start_time" slot-scope="start_time">
            {{ getLocaleDateTime(start_time) }}
          </span>
          <span slot="end_time" slot-scope="end_time">
            {{ getLocaleDateTime(end_time) }}
          </span>
          <span slot="action" slot-scope="text, record">
            <a
              @click="
                showReservationAttendee(
                  record.participants,
                  record.meeting_number
                )
              "
            >
              查看参会人员
            </a>

            <a-divider type="vertical" />
            <a-button
              type="link"
              @click="
                showReservationRecord(
                  record.type,
                  record.meeting_id,
                  record.meeting_number
                )
              "
            >
              查看会议记录
            </a-button>
            <a-divider type="vertical" />
          </span>
        </a-table>
        <!-- 参会人员 -->
        <a-modal
          v-model="attendee_reservation_visible"
          :title="attendee_reservation_meeting_title"
          @cancel="cancelshowReservationAttendee"
          :footer="null"
        >
          <a-empty v-if="attendee_reservation_list.length == 0" />
          <a-table
            :rowKey="(record, index) => record.user_id"
            :columns="col"
            :data-source="attendee_reservation_list"
            :pagination="{ pageSize: 10 }"
            style="background: white; padding: 10px"
            v-else
          >
          </a-table>
        </a-modal>
        <!-- 会议记录 -->
        <a-modal
          :visible="record_reservation_visible"
          @cancel="cancelshowReservationRecord"
          :title="record_reservation_meeting_title"
          :bodyStyle="{ padding: '10px' }"
          :footer="null"
        >
          <a-empty v-if="record_reservation_list.length == 0" />
          <div class="chatbox" id="chat-box" v-else>
            <template v-for="(item, index) in record_reservation_list">
              <div style="text-align: right" :key="'1' + index"></div>
              <div
                :key="'2' + index"
                class="right"
                v-if="item.userId == userId"
              >
                <span>{{ item.time }} &nbsp; {{ item.userName }}</span>
              </div>
              <div :key="'3' + index" class="left" v-else>
                <span>{{ item.time }} &nbsp; {{ item.userName }}</span>
              </div>
              <Msg
                :msg="toEmoji(item.msg)"
                :send="item.userId == userId"
                :key="'4' + index"
                :username="item.userName"
              />
            </template>
          </div>
        </a-modal>
      </a-tab-pane>
      <a-tab-pane key="3">
        <span slot="tab" @click="getRoomList">预约会议室 </span>
        <a-row :gutter="20" style="margin-bottom: 15px; margin-top: 25px">
          <a-col :span="6">
            <a-range-picker :ranges="{ 清空: ['', ''] }" @change="onChange" />
          </a-col>
          <a-col :span="1">
            <a-button @click="getRoomList" type="primary">
              <a-icon type="search" />
            </a-button>
          </a-col>
        </a-row>
        <!-- 表格 -->
        <a-table
          :rowKey="(record, index) => record.room_meeting_id"
          :columns="columnsSec"
          :data-source="roomData"
          :pagination="{ pageSize: 10 }"
          style="background: white; padding: 10px"
        >
          <span slot="start_time" slot-scope="start_time">
            {{ getLocaleDateTime(start_time) }}
          </span>
          <span slot="end_time" slot-scope="end_time">
            {{ getLocaleDateTime(end_time) }}
          </span>
          <span slot="action" slot-scope="text, record">
            <a
              @click="
                showRoomAttendee(record.participants, record.room_meeting_id)
              "
            >
              查看参会人员
            </a>

            <a-divider type="vertical" />
            <a-button
              type="link"
              @click="showRoomRecord(record.type, record.room_meeting_id)"
            >
              查看会议记录
            </a-button>
            <a-divider type="vertical" />
          </span>
        </a-table>
        <!-- 参会人员 -->
        <a-modal
          v-model="attendee_room_visible"
          :title="attendee_room_meeting_title"
          @cancel="cancelshowRoomAttendee"
          :footer="null"
        >
          <a-empty v-if="attendee_room_list.length == 0" />
          <a-table
            :rowKey="(record, index) => record.user_id"
            :columns="col"
            :data-source="attendee_room_list"
            :pagination="{ pageSize: 10 }"
            style="background: white; padding: 10px"
            v-else
          >
          </a-table>
        </a-modal>
        <!-- 会议记录 -->
        <a-modal
          :visible="record_room_visible"
          @cancel="cancelshowRoomRecord"
          :title="record_room_meeting_title"
          :bodyStyle="{ padding: '10px' }"
          :footer="null"
        >
          <a-empty v-if="record_room_list.length == 0" />
          <div class="chatbox" id="chat-box" v-else>
            <template v-for="(item, index) in record_room_list">
              <div style="text-align: right" :key="'1' + index"></div>
              <div
                :key="'2' + index"
                class="right"
                v-if="item.userId == userId"
              >
                <span>{{ item.time }} &nbsp; {{ item.userName }}</span>
              </div>
              <div :key="'3' + index" class="left" v-else>
                <span>{{ item.time }} &nbsp; {{ item.userName }}</span>
              </div>
              <Msg
                :msg="toEmoji(item.msg)"
                :send="item.userId == userId"
                :key="'4' + index"
                :username="item.userName"
              />
            </template>
          </div>
        </a-modal>
      </a-tab-pane>
    </a-tabs>
  </a-card>
</template>

<script>
import {
  getHistoryMeetingList,
  getRecord,
  getRoomRecord,
} from "@/services/history.js";
import Msg from "@/pages/meetingOn/chat/Msg.vue";
import moment from "moment";
import { mapGetters } from "vuex";
import Vue from "vue";
import WeixinEmojis from "vue-weixin-emojis";
import "vue-weixin-emojis/dist/vue-weixin-emojis.css";
Vue.use(WeixinEmojis);

export default {
  components: {
    Msg,
  },
  data() {
    return {
      attendee_instant_visible: false,
      attendee_instant_meeting_title: "",
      attendee_instant_list: [],
      record_instant_visible: false,
      record_instant_list: [],
      record_instant_meeting_title: "",

      attendee_reservation_visible: false,
      attendee_reservation_meeting_title: "",
      attendee_reservation_list: [],
      record_reservation_visible: false,
      record_reservation_list: [],
      record_reservation_meeting_title: "",

      attendee_room_visible: false,
      attendee_room_meeting_title: "",
      attendee_room_list: [],
      record_room_visible: false,
      record_room_list: [],
      record_room_meeting_title: "",
      searchName: "",
      col: [
        {
          title: "用户ID",
          dataIndex: "user_id",
        },
        {
          title: "用户名",
          dataIndex: "username",
        },
      ],
      columns: [
        {
          title: "会议ID",
          dataIndex: "meeting_id",
          key: "0",
          scopedSlots: { customRender: "meeting_id" },
        },
        {
          title: "会议号",
          dataIndex: "meeting_number",
          key: "1",
          scopedSlots: { customRender: "meeting_number" },
        },
        {
          title: "会议主题",
          dataIndex: "topic",
          key: "2",
        },
        {
          title: "开始时间",
          dataIndex: "start_time",
          key: "3",
          scopedSlots: { customRender: "start_time" },
          defaultSortOrder: "descend",
          sorter: (a, b) => a.start_time - b.start_time,
        },
        {
          title: "结束时间",
          dataIndex: "end_time",
          key: "4",
          scopedSlots: { customRender: "end_time" },
          defaultSortOrder: "descend",
          sorter: (a, b) => a.end_time - b.end_time,
        },
        {
          title: "操作",
          dataIndex: "action",
          key: "5",
          scopedSlots: { customRender: "action" },
        },
      ],
      columnsSec: [
        {
          title: "会议ID",
          dataIndex: "room_meeting_id",
          key: "0",
          scopedSlots: { customRender: "room_meeting_id" },
        },

        {
          title: "会议室名称",
          dataIndex: "room_name",
          key: "2",
          scopedSlots: { customRender: "room_name" },
        },
        {
          title: "会议主题",
          dataIndex: "topic",
          key: "3",
        },
        {
          title: "开始时间",
          dataIndex: "start_time",
          key: "4",
          scopedSlots: { customRender: "start_time" },
          defaultSortOrder: "descend",
          sorter: (a, b) => a.start_time - b.start_time,
        },
        {
          title: "结束时间",
          dataIndex: "end_time",
          key: "5",
          scopedSlots: { customRender: "end_time" },
          defaultSortOrder: "descend",
          sorter: (a, b) => a.end_time - b.end_time,
        },
        {
          title: "操作",
          dataIndex: "action",
          key: "6",
          scopedSlots: { customRender: "action" },
        },
      ],
      instantData: [],
      reservationData: [],
      roomData: [],
      start_time: "",
      end_time: "",
    };
  },
  created() {
    this.getInstantList();
  },
  computed: {
    ...mapGetters("account", ["userId"]),
  },
  methods: {
    moment,
    getLocaleDateTime(datetime) {
      return new Date(datetime)
        .toLocaleString("chinese", { hour12: false })
        .toString();
    },
    onChange(dates) {
      if (!dates[0] && !dates[1]) {
        this.start_time = "";
        this.end_time = "";
      } else {
        Date.prototype.clone = function () {
          return new Date(this.valueOf());
        };
        let start_time = dates[0].toDate().clone();
        start_time.setHours(0);
        start_time.setMinutes(0);
        start_time.setSeconds(0);

        let end_time = dates[1].toDate().clone();
        end_time.setHours(23);
        end_time.setMinutes(59);
        end_time.setSeconds(59);
        this.start_time = start_time.getTime();
        this.end_time = end_time.getTime();
      }
    },
    getInstantList() {
      this.searchName = "";
      let start_time = 0,
        end_time = 0;
      if (this.start_time && this.end_time) {
        start_time = this.start_time;
        end_time = this.end_time;
      }
      getHistoryMeetingList(start_time, end_time).then((res) => {
        const getRes = res.data.data;
        var dataList = [];
        for (var i = 0; i < getRes.length; i++) {
          if (getRes[i].type == 1) {
            dataList[dataList.length] = getRes[i];
          }
        }
        this.instantData = dataList;
      });
    },
    showInstantRecord(type, meeting_id, meeting_number) {
      this.record_instant_meeting_title =
        "会议记录（会议号：" + meeting_number + "）";
      getRecord(type, meeting_id).then((res) => {
        if (res.data.data != null) {
          let list = JSON.parse(res.data.data.result);
          this.record_instant_list = [];
          for (let i = 0; i < list.length; i++) {
            this.record_instant_list.push(JSON.parse(list[i]));
          }
        } else {
          this.record_instant_list = "";
        }

        this.record_instant_visible = true;
      });
    },
    cancelshowInstantRecord() {
      this.record_instant_visible = false;
    },
    cancelshowInstantAttendee() {
      this.attendee_instant_visible = false;
    },
    showInstantAttendee(participants, meeting_number) {
      this.attendee_instant_meeting_title =
        "参会人（会议号：" + meeting_number + "）";
      this.attendee_instant_list = participants;
      this.attendee_instant_visible = true;
    },
    getReservationList() {
      this.searchName = "";
      let start_time = 0,
        end_time = 0;
      if (this.start_time && this.end_time) {
        start_time = this.start_time;
        end_time = this.end_time;
      }
      getHistoryMeetingList(start_time, end_time).then((res) => {
        const getRes = res.data.data;
        var dataList = [];
        for (var i = 0; i < getRes.length; i++) {
          if (getRes[i].type == 2) {
            dataList[dataList.length] = getRes[i];
          }
        }
        this.reservationData = dataList;
      });
    },
    showReservationRecord(type, meeting_id, meeting_number) {
      this.record_reservation_meeting_title =
        "会议记录（会议号：" + meeting_number + "）";
      getRecord(type, meeting_id).then((res) => {
        if (res.data.data != null) {
          let list = JSON.parse(res.data.data.result);
          this.record_reservation_list = [];
          for (let i = 0; i < list.length; i++) {
            this.record_reservation_list.push(JSON.parse(list[i]));
          }
        } else {
          this.record_reservation_list = "";
        }
        this.record_reservation_visible = true;
      });
    },
    cancelshowReservationRecord() {
      this.record_reservation_visible = false;
    },
    cancelshowReservationAttendee() {
      this.attendee_reservation_visible = false;
    },
    showReservationAttendee(participants, meeting_number) {
      this.attendee_reservation_meeting_title =
        "参会人（会议号：" + meeting_number + "）";
      this.attendee_reservation_list = participants;
      this.attendee_reservation_visible = true;
    },
    getRoomList() {
      this.searchName = "";
      let start_time = 0,
        end_time = 0;
      if (this.start_time && this.end_time) {
        start_time = this.start_time;
        end_time = this.end_time;
      }
      getHistoryMeetingList(start_time, end_time).then((res) => {
        const getRes = res.data.data;
        var dataList = [];
        for (var i = 0; i < getRes.length; i++) {
          if (getRes[i].type == 3) {
            dataList[dataList.length] = getRes[i];
          }
        }
        this.roomData = dataList;
      });
    },
    showRoomRecord(type, room_meeting_id) {
      this.record_room_meeting_title = "会议记录（会议ID：" + room_meeting_id + "）";
      getRoomRecord(type, room_meeting_id).then((res) => {
        if (res.data.data != null) {
          let list = JSON.parse(res.data.data.result);
          this.record_room_list = [];
          for (let i = 0; i < list.length; i++) {
            this.record_room_list.push(JSON.parse(list[i]));
          }
        } else {
          this.record_room_list = "";
        }
        this.record_room_visible = true;
      });
    },
    cancelshowRoomRecord() {
      this.record_room_visible = false;
    },
    cancelshowRoomAttendee() {
      this.attendee_room_visible = false;
    },
    showRoomAttendee(participants, room_meeting_id) {
      this.attendee_room_meeting_title =
        "参会人（会议Id：" + room_meeting_id + "）";
      this.attendee_room_list = participants;
      this.attendee_room_visible = true;
    },
    toEmoji(msg) {
      return this.$string2emoji(msg);
    },
  },
};
</script>

<style scoped>
.chatbox {
  grid-column: 1 / 2;
  grid-row: 2 / 3;
  padding: 8px 10px;
  overflow-y: scroll;
  background-color: #e6e6e6;
}

.chatbox::-webkit-scrollbar-thumb {
  background-color: #f5f5f5;
}
.chatbox:hover::-webkit-scrollbar-thumb {
  background-color: #d2d2d2;
}

.left {
  text-align: left;
}

.right {
  text-align: right;
}
</style>