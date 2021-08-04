<template>
  <div style="text-align: left">
    <div v-for="item in roomList" :key="item.id" class="room-box">
      <a-row style="height: 20%">
        <span style="font-size: 16px; color: black"
          ><span style="font-weight: 500">会议室：</span>{{ item.name }}</span
        >
        <hr class="hr" />
      </a-row>
      <a-row style="height: 62%">
        <svg
          t="1627483351306"
          class="icon"
          viewBox="0 0 1024 1024"
          version="1.1"
          xmlns="http://www.w3.org/2000/svg"
          p-id="1313"
          width="128"
          height="128"
        >
          <path
            d="M344.6 213.2h339.1v225.9H344.6z"
            fill="#B2D6EE"
            p-id="1314"
          ></path>
          <path
            d="M680.9 528.6H343.5c-35.5 0-64.5-28.9-64.5-64.5V194.4c0-35.5 28.9-64.5 64.5-64.5h337.4c35.5 0 64.5 28.9 64.5 64.5v269.7c-0.1 35.6-29 64.5-64.5 64.5zM343.5 161.4c-18.2 0-33 14.8-33 33v269.7c0 18.2 14.8 33 33 33h337.4c18.2 0 33-14.8 33-33V194.4c0-18.2-14.8-33-33-33H343.5z"
            fill="#3B71B7"
            p-id="1315"
          ></path>
          <path
            d="M373.1 594.8c-4.3 0-8.6-1.8-11.7-5.2-5.8-6.5-5.3-16.4 1.2-22.2l73.9-66.2c6.4-5.8 16.4-5.3 22.2 1.2 5.8 6.5 5.3 16.4-1.2 22.2l-73.9 66.2c-3 2.7-6.7 4-10.5 4zM653.4 598c-3.7 0-7.4-1.3-10.4-3.9l-79.3-69.4c-6.5-5.7-7.2-15.7-1.5-22.2 5.7-6.6 15.6-7.2 22.2-1.5l79.3 69.4c6.5 5.7 7.2 15.7 1.5 22.2-3.1 3.6-7.4 5.4-11.8 5.4zM523.4 369.4l-52.6-49.8-61.5 35.9c-7.6 4.4-17.1 1.8-21.5-5.6-4.4-7.5-1.8-17.1 5.7-21.5l81.9-47.8 51.5 48.8 86.5-57.8c7.2-4.8 16.9-2.9 21.8 4.3 4.8 7.2 2.9 17-4.3 21.8l-107.5 71.7zM925.6 894.1H99.8c-11.4 0-22.1-5.5-28.6-14.8-6.6-9.3-8.2-21.2-4.4-31.9l38.5-108.9c4.9-14 18.2-23.4 33.1-23.4H889c15 0 28.4 9.6 33.2 23.8L959 847.8c3.6 10.7 1.8 22.5-4.8 31.7-6.7 9.1-17.3 14.6-28.6 14.6zM138.3 746.5c-1.6 0-2.9 0.9-3.4 2.4L96.3 857.8c-0.5 1.5 0 2.6 0.5 3.3 0.5 0.7 1.4 1.5 3 1.5h825.9c1.5 0 2.5-0.8 2.9-1.5 0.5-0.7 1-1.8 0.5-3.3L892.3 749c-0.5-1.5-1.9-2.5-3.5-2.5H138.3z"
            fill="#3B71B7"
            p-id="1316"
          ></path>
          <path
            d="M346.3 746.5H219.1v-61.6c0-14.6 11.8-26.4 26.4-26.4h74.4c14.6 0 26.4 11.8 26.4 26.4v61.6zM250.6 715h64.3v-25h-64.3v25zM575.7 746.5H448.6v-61.6c0-14.6 11.8-26.4 26.4-26.4h74.4c14.6 0 26.4 11.8 26.4 26.4v61.6zM480 715h64.3v-25H480v25zM806.8 746.5H679.6v-61.6c0-14.6 11.8-26.4 26.4-26.4h74.4c14.6 0 26.4 11.8 26.4 26.4v61.6zM711 715h64.3v-25H711v25z"
            fill="#3B71B7"
            p-id="1317"
          ></path>
        </svg>
      </a-row>

      <a-row style="height: 18%">
        <hr class="hr" />
        <a-button type="link" @click="handleShow(item.id, item.name)">
          <a-icon type="profile" />
          查看预约列表
        </a-button>
      </a-row>
    </div>
    <a-modal
      :visible="visible"
      :footer="null"
      @cancel="handleCancel"
      :title="title"
      style="width:800px"
    >
      <a-table :columns="columns" :data-source="reservationList" :rowKey="(record,index)=>{return index}">
        <span slot="scheduledStartTime" slot-scope="scheduledStartTime">{{
          getLocaleDateTime(scheduledStartTime)
        }}</span>
        <span slot="scheduledEndTime" slot-scope="scheduledEndTime">{{
          getLocaleDateTime(scheduledEndTime)
        }}</span>
      </a-table>
    </a-modal>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import { avaliableRoom, getRoomReservationList } from '@/services/reservation.js'

export default {
  name: 'roomList',
  data() {
    return {
      roomList: [],
      reservationList: [],
      visible: false,
      title: "",
      columns: [
        {
          title: '会议ID',
          dataIndex: 'id',
          key: 1,
          scopedSlots: { customRender: 'id' },
        },
        {
          title: '会议主题',
          dataIndex: 'topic',
          key: 2,
          scopedSlots: { customRender: 'topic' },
        },
        {
          title: '主持人',
          dataIndex: 'hostName',
          key: 3,
          scopedSlots: { customRender: 'hostname' },
        },
        {
          title: '开始时间',
          dataIndex: 'scheduledStartTime',
          key: 4,
          scopedSlots: { customRender: 'scheduledStartTime' },
          defaultSortOrder: 'descend',
          sorter: (a, b) => a.scheduledStartTime - b.scheduledStartTime,
        },
        {
          title: '结束时间',
          dataIndex: 'scheduledEndTime',
          key: 5,
          scopedSlots: { customRender: 'scheduledEndTime' },
          defaultSortOrder: 'descend',
          sorter: (a, b) => a.scheduledEndTime - b.scheduledEndTime,
        }
      ]
    }
  },
  created() {
    this.getRoomList()
  },
  computed: {
    ...mapGetters('account', ['userId', 'companyId', 'companyName'])
  },
  methods: {
    getRoomList() {
      let data = {
        companyId: this.companyId
      }

      avaliableRoom(data).then(res => {
        this.roomList = res.data.data
      })
    },
    handleShow(id, name) {
      let data = {
        roomId: id
      }

      getRoomReservationList(data).then(res => {
        this.reservationList = res.data.data
        this.visible = true
        this.title = "会议室：" + name
      })

    },
    handleCancel() {
      this.visible = false
    },
    getLocaleDateTime(datetime) {
      return new Date(datetime).toLocaleString("chinese", { hour12: false }).toString()
    },
  }

}
</script>


<style scoped>
.room-box {
  margin-left: 20px;
  margin-right: 20px;
  margin-top: 15px;
  margin-bottom: 15px;
  padding: 10px;
  display: inline-block;
  width: 280px;
  height: 280px;
  text-align: center;
  border: 1px solid lightgrey;
  border-radius: 5px;
}

.room-box:hover {
  box-shadow: 0 0 12px lightgray;
}

.hr {
  border: none;
  height: 1px;
  background-color: #e5e5e5;
}
</style>
