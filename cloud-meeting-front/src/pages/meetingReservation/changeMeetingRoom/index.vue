<template>
  <a-card
    :body-style="{
      padding: '24px 32px',
      paddingBottom: '64px',
      textAlign: 'center',
    }"
    :bordered="false"
  >
    <a-tabs
      default-active-key="1"
      :tabBarStyle="{ width: '100%', textAlign: 'center' }"
    >
      <a-tab-pane key="1" tab="会议室预约情况">
        <room-list />
      </a-tab-pane>
      <a-tab-pane key="2" tab="预约会议室" force-render>
        <a-row>
          <a-col :span="9">
            <a-form-model
              ref="form"
              :model="form"
              :label-col="{ span: 6 }"
              :wrapper-col="{ span: 17 }"
              :selfUpdate="true"
              :rules="rules"
            >
              <a-form-model-item label="会议室会议ID" prop="topic">
                <a-input v-model="roomMeetingId" :disabled="true"></a-input>
              </a-form-model-item>
              <a-form-model-item label="会议主题" prop="topic">
                <a-input
                  v-model="form.topic"
                  :placeholder="'默认为' + username + '预约的会议室会议'"
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
                  placeholder="请选择会议时长"
                  style="width: 100%"
                />
              </a-form-model-item>
              <a-form-model-item label="会议室" prop="roomId">
                <a-select v-model="form.roomId" placeholder="请选择会议室">
                  <a-select-option
                    v-for="(item, index) in roomList"
                    :key="index"
                    :value="item.id"
                    >{{ item.name }}</a-select-option
                  >
                </a-select>
              </a-form-model-item>
            </a-form-model>
            <a-button
              type="primary"
              style="margin-right: 10px"
              @click="handleSubmit"
              >变更会议</a-button
            >
            <a-button @click="resetForm" style="margin-right: 10px">重置信息</a-button>
            <a-popconfirm
              title="您确定要取消变更，这将放弃您所填写的信息?"
              ok-text="确定"
              cancel-text="取消"
              @confirm="confirm"
              @cancel="cancel"
            >
              <a-button>取消变更</a-button>
            </a-popconfirm>
          </a-col>
          <a-col :span="1"></a-col>
          <a-col :span="14">
            <a-row>
              <div
                style="
                  border-radius: 5px;
                  text-align: left;
                  padding: 5px;
                  min-height: 30px;
                  max-height: 65px;
                "
              >
                <span style="font-size: 14px; color: black">与会人：</span>
                <a-tooltip
                  v-if="username.length > 12"
                  :key="userId"
                  :title="username"
                >
                  <a-tag
                    :key="userId"
                    color="rgb(45 183 245 / 82%)"
                    style="margin: 2px; padding: 3px"
                  >
                    <span style="font-size: 14px">
                      {{ `${username.slice(0, 12)}...` }}</span
                    >
                  </a-tag>
                </a-tooltip>
                <a-tag
                  v-else
                  :key="userId"
                  color="rgb(45 183 245 / 82%)"
                  style="margin: 2px; padding: 3px"
                >
                  <span style="font-size: 14px">{{ username }}</span>
                </a-tag>

                <template v-for="(item, index) in participants">
                  <a-tooltip
                    v-if="item.userName.length > 12"
                    :key="index"
                    :title="item.userName"
                  >
                    <a-tag
                      :key="item.userId"
                      :closable="true"
                      @close="() => handleClose(item)"
                      color="rgb(45 183 245 / 82%)"
                      style="margin: 2px; padding: 3px"
                    >
                      <span style="font-size: 14px">
                        {{ `${item.userName.slice(0, 12)}...` }}</span
                      >
                    </a-tag>
                  </a-tooltip>
                  <a-tag
                    v-else
                    :key="item.userId"
                    :closable="true"
                    @close="() => handleClose(item)"
                    color="rgb(45 183 245 / 82%)"
                    style="margin: 2px; padding: 3px"
                  >
                    <span style="font-size: 14px">{{ item.userName }}</span>
                  </a-tag>
                </template>
              </div>
              <hr class="hr" />
            </a-row>
            <a-row>
              <a-col :span="6">
                <a-tree
                  :load-data="onLoadDepartment"
                  :tree-data="treeData"
                  showLine
                  blockNode
                  :defaultExpandAll="true"
                  style="text-align: left"
                >
                  <template slot="company" slot-scope="item">
                    <a-tooltip
                      placement="topLeft"
                      title="显示全部员工"
                      arrow-point-at-center
                    >
                      <span @click="search">
                        {{ item.title }}
                      </span>
                    </a-tooltip>
                  </template>
                  <template slot="custom" slot-scope="item">
                    <a-tooltip
                      placement="topLeft"
                      title="显示部门成员"
                      arrow-point-at-center
                      v-if="item.key != -1"
                    >
                      <span @click="check(item.key)">
                        {{ item.title }}
                      </span>
                    </a-tooltip>
                    <a-tooltip
                      placement="topLeft"
                      title="显示非部门成员"
                      arrow-point-at-center
                      v-else
                    >
                      <span @click="check(item.key)">
                        {{ item.title }}
                      </span>
                    </a-tooltip>
                  </template>
                </a-tree>
              </a-col>
              <a-col :span="18">
                <a-row>
                  <div
                    style="
                      margin-bottom: 8px;
                      margin-top: 8px;
                      margin-left: 8px;
                      text-align: left;
                    "
                  >
                    <a-input-search
                      placeholder="请输入员工用户名"
                      style="width: 250px"
                      @search="onSearch"
                      v-model="searchName"
                    />
                    <span style="margin-left: 8px">
                      <template v-if="hasSelected">
                        {{ `选中 ${selectedRowKeys.length} 个用户` }}
                      </template>
                    </span>
                  </div>
                  <a-table
                    bordered
                    :rowKey="(record, index) => record.userId"
                    :row-selection="{
                      selectedRowKeys: selectedRowKeys,
                      onChange: onSelectChange,
                    }"
                    :columns="columns"
                    :data-source="data"
                    style="margin-left: 8px"
                  >
                    <div slot="roles" slot-scope="roles">
                      <span v-if="roles[0].id == 'founder'">创始人</span>
                      <span v-if="roles[0].id == 'employee'">员工</span>
                      <span v-if="roles[0].id == 'admin'">管理员</span>
                    </div>
                  </a-table>
                </a-row>
              </a-col>
            </a-row>
          </a-col>
        </a-row>
      </a-tab-pane>
    </a-tabs>

    <a-modal :visible="result_visible" :footer="null">
      <a-result
        status="success"
        :title="'变更成功（会议室会议ID为 ' + roomMeetingId + '） ！'"
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
          <span>{{ count }}秒后跳转</span>
        </template>
      </a-result>
    </a-modal>
  </a-card>
</template>

<script>
import moment from 'moment'
import { mapGetters } from 'vuex'
import { avaliableRoom, changeRoomReservation,getRoomReservation} from '@/services/reservation.js'
import { fetchPerson, searchInformation } from "@/services/company";
import { departmentList, findEmployee } from "@/services/department";
import roomList from '@/pages/meetingReservation/meetingRoom/components/roomList.vue'

const columns = [
  {
    title: "用户名",
    width: 150,
    dataIndex: "username",
    key: "username",
    fixed: "left",
  },
  {
    title: "角色",
    key: "roles",
    dataIndex: "roles",
    scopedSlots: { customRender: "roles" },
  },
  { title: "部门", dataIndex: "departmentName", key: 2 },
];


export default {
  components: {
    roomList
  },
  data() {
    return {
      current: 0,
      form: {
        topic: '',
        startTime: '',
        duration: '',
        roomId: undefined,
      },
      rules: {
        topic: [
          { max: 20, message: '会议主题不要超过20字', trigger: 'change' },
        ],
        startTime: [
          { required: true, message: '请选择开始时间', trigger: 'change' },
        ],
        duration: [
          { required: true, message: '请选择会议时长', trigger: 'change' },
        ],
        roomId: [
          { required: true, message: '请选择会议室', trigger: 'change' },
        ],
        password: [
          { pattern: new RegExp(/^[1-9]\d*$/, "g"), message: '会议密码必须为数字', trigger: 'change' },
          { max: 8, message: '会议密码必须为8位以内', trigger: 'change' },
          // { required: true, message: '请填写会议密码', trigger: 'blur' },
        ],
      },
      participants: [],
      roomList: [],
      fetching: false,
      result_visible: false,
      roomMeetingId: '',
      roomName: '',
      data: [],
      dataAdmin: [],
      columns,
      selectedRowKeys: [],
      loading: false,
      treeData: [
        {
          title: "",
          key: 0,
          scopedSlots: { title: "company" },
          children: [],
        },
      ],

      person: [],
      role: "",
      searchName: "",
      departmentData: [],
      userCode: 0,
      treeShow: false,
      editId: 0,
      count: ''
    }
  },
  beforeCreate() {
    this.form = this.$form.createForm(this, "form");
  },
  created() {
    //页面初始化时的操作可以写在这
    // this.form.duration = moment(new Date(2013, 0, 0, 0, 30, 0, 0).getTime());
    this.role = this.roles[0].id;
    this.fetchPerson();
    this.treeData[0].title = this.companyName;

    //页面初始化时的操作可以写在这
    this.roomMeetingId = this.$route.params.roomMeetingId


    if (!this.roomMeetingId) {
      this.$closePage(this.$route)
    }

    let data = {
      id: this.roomMeetingId,
    }
    getRoomReservation(data).then(res => {
      console.log(res)
      this.form.topic = res.data.data.topic
      this.form.roomId = res.data.data.roomId
      this.form.startTime = this.moment(res.data.data.scheduledStartTime)
      this.form.duration = this.moment(res.data.data.scheduledEndTime - res.data.data.scheduledStartTime)
      this.form.hostId = res.data.data.hostId
      // this.participants = res.data.data.participants
      this.participants=[]
      this.selectedRowKeys=[]
      for(let i=0;i<res.data.data.participants.length;i++){
        this.selectedRowKeys.push(res.data.data.participants[i].userId)
        if(res.data.data.participants[i].userId!=this.userId){
          this.participants.push({
            userId:res.data.data.participants[i].userId,
            userName:res.data.data.participants[i].username
          })
        }
      }

    })
  },
  beforeMount() {
    departmentList(this.companyId).then((res) => {
      const listRes = res.data;
      if (listRes.code == 200) {
        if (listRes.data == null) {
          this.treeData = [{ title: this.companyName, key: 0, isLeaf: true }];
        }
      }
    });
  },
  mounted() {
  },
  watch: {//侦听属性
    'form.startTime': {
      handler: function (val) {
        if (this.form.duration&&val!='') {
          this.getRoomList(val, this.form.duration)
        }
      }
    },
    'form.duration': {
      handler: function (val) {
        if (this.form.startTime&&val!='') {
          this.getRoomList(this.form.startTime, val)
        }
      }
    },
  },
  computed: {//计算属性
    ...mapGetters('account', ['username', 'userId', 'companyId', 'roles', 'companyName']),
    hasSelected() {
      return this.selectedRowKeys.length > 0;
    },
    successMsg() {
      if (this.form.startTime && this.form.duration) {
        return '会议室为' + this.roomName + ' ,会议开始时间为：' + this.form.startTime.toDate().toLocaleString('chinese', { hour12: false }) + ' , 预计持续' + this.form.duration.toDate().getHours() + '时' + this.form.duration.toDate().getMinutes() + '分' + (this.form.requirePwd ? (',\n会议密码为' + this.form.password) : '。')
      } else {
        return ''
      }
    }
  },
  methods: {
    moment,
    getRoomList(startTime, duration) {
      //在Date原型上面定义clone，深复制
      Date.prototype.clone = function () {
        return new Date(this.valueOf())
      };

      let endTime = startTime.toDate().clone()

      endTime.setHours(startTime.toDate().getHours() + duration.toDate().getHours())
      endTime.setMinutes(startTime.toDate().getMinutes() + duration.toDate().getMinutes())
      //这里获取会议室列表
      let data = {
        startTime: startTime.toDate().getTime(),
        endTime: endTime.getTime(),
        companyId: this.companyId
      }

      avaliableRoom(data).then(res => {
        this.roomList = res.data.data
      })
    },
    disabledDate(endValue) {
      let startValue = new Date();
      startValue.setDate(startValue.getDate() - 1)
      if (!endValue || !startValue) {
        return false;
      }
      return startValue.valueOf() >= endValue.valueOf();
    },
    handleSubmit() {
      this.$refs.form.validate(valid => {
        if (valid) {
          let now = new Date()
          if (this.form.startTime.toDate().valueOf() < now.valueOf()) {
            this.$message.error('预约会议的开始时间小于当前时间，请重新选择时间！')
            this.form.startTime = ''
          } else {
            this.handleRoomReservation()
          }
        } else {
          console.log('表单校验不成功!!');
          return false;
        }
      });
    },
    handleRoomReservation() {
      Date.prototype.clone = function () {
        return new Date(this.valueOf())
      };

      let startTime = this.form.startTime.toDate()
      let endTime = startTime.clone()
      let duration = this.form.duration.toDate()

      endTime.setHours(startTime.getHours() + duration.getHours())
      endTime.setMinutes(startTime.getMinutes() + duration.getMinutes())

      let participants = ""

      for (let i = 0; i < this.participants.length; i++) {
        participants += (this.participants[i].userId + ",")
      }

      participants += (this.userId)

      let data = {
        id: this.roomMeetingId,
        topic: this.form.topic ? this.form.topic : (this.username + '预约的会议室会议'),
        scheduledStartTime: startTime.getTime(),
        scheduledEndTime: endTime.getTime(),
        roomId: this.form.roomId,
        participants: participants,
      }

      changeRoomReservation(data).then(res => {
        if (res.data.code == 200) {
          this.roomMeetingId = res.data.data.id
          this.roomName = res.data.data.roomName
          this.result_visible = true
          this.fiveSecondsGo()
        } else {
          this.$message.error(res.data.errMsg)
        }

      })
    },
    resetForm() {
      this.form.topic = ''
      this.form.startTime = ''
      this.form.duration = moment(new Date(2013, 0, 0, 0, 30, 0, 0).getTime())
      this.form.roomId = undefined
      this.participants = []
      this.selectedRowKeys=[this.userId]
      this.roomName = ''
      this.fetching = false
      this.search()

    },
    goTo(path) {
      this.$closePage(this.$route)
      this.$router.push(path)
      this.result_visible = false
    },
    handleCancel() {
      this.result_visible = !this.result_visible
      this.resetForm()
    },
    cancel(e){
      console.log(e)
      //do nothing
    },
    confirm(e) {
      console.log(e)
      this.$closePage(this.$route)
    },
    // 渲染部门用户
    fetchPerson() {
      fetchPerson(this.companyId).then((res) => {
        this.data = res.data.data;
        let index1 = (this.selectedRowKeys || []).findIndex((item) => item === this.userId);
        let index2 = (this.data || []).findIndex((item) => item.userId === this.userId)
        if (index2 != -1 && index1 == -1) {
          this.selectedRowKeys.push(this.userId)
        }
      });
      this.searchName = "";
    },
    // 渲染公司全部用户
    search() {
      this.fetchPerson();
    },
    // 根据用户名搜索公司用户
    onSearch(name) {
      if (name != "") {
        searchInformation(name, this.companyId).then((res) => {
          const searchRes = res.data;
          if (searchRes.code == 200) {
            this.data = res.data.data;
            let index1 = (this.selectedRowKeys || []).findIndex((item) => item === this.userId);
            let index2 = (this.data || []).findIndex((item) => item.userId === this.userId)
            if (index2 != -1 && index1 == -1) {
              this.selectedRowKeys.push(this.userId)
            }
            this.searchName = "";
          }
        });
      } else {
        this.search();
      }
    },
    // 树形控件加载部门名称
    onLoadDepartment() {
      return new Promise((resolve) => {
        departmentList(this.companyId).then((res) => {
          const listRes = res.data;
          if (listRes.code == 200) {
            if (listRes.data == null) {
              this.$message.warning("该企业还没有部门！");
            } else {
              for (var i = 0; i < listRes.data.length; i++) {
                this.$set(this.treeData[0].children, i, {
                  title: listRes.data[i].name,
                  key: listRes.data[i].id,
                  isLeaf: true,
                  scopedSlots: { title: "custom" },
                });
              }

              this.$set(this.treeData[0].children, listRes.data.length, {
                title: '非部门成员',
                key: -1,
                isLeaf: true,
                scopedSlots: { title: "custom" },
              });

              this.treeData = [...this.treeData];
            }
          }
        });
        resolve();
      });
    },
    // 查看员工
    check(key) {
      var department = key;
      if (department == -1) {
        department = "";
      }
      findEmployee(department, this.companyId).then((res) => {
        const findRes = res.data;
        if (findRes.code == 200) {
          this.data = findRes.data;
          let index1 = (this.selectedRowKeys || []).findIndex((item) => item === this.userId);
          let index2 = (this.data || []).findIndex((item) => item.userId === this.userId)
          if (index2 != -1 && index1 == -1) {
            this.selectedRowKeys.push(this.userId)
          }
        }
      });
    },
    onSelectChange(selectedRowKeys) {
      let index1 = (selectedRowKeys || []).findIndex((item) => item === this.userId);
      let index2 = (this.data || []).findIndex((item) => item.userId === this.userId)
      if (index2 != -1 && index1 == -1) {
        selectedRowKeys.push(this.userId)
      }

      this.selectedRowKeys = selectedRowKeys;


      for (let i = 0; i < this.data.length; i++) {
        for (let j = 0; j < this.participants.length; j++) {
          if (this.participants[j].userId == this.data[i].userId) {
            this.participants.splice(j, 1)
            continue;
          }
        }
      }

      for (let j = 0; j < selectedRowKeys.length; j++) {
        for (let i = 0; i < this.data.length; i++) {
          if ((selectedRowKeys[j] == this.data[i].userId) && (selectedRowKeys[j] != this.userId)) {
            this.participants.push({
              userId: this.data[i].userId,
              userName: this.data[i].username
            })
          }
        }
      }

    },
    handleClose(removedItem) {
      const participants = this.participants.filter(item => item !== removedItem);
      this.selectedRowKeys = this.selectedRowKeys.filter(item => item !== removedItem.userId)
      this.participants = participants;
    },
    fiveSecondsGo() {
      const TIME_COUNT = 5;
      if (!this.timer) {
        this.count = TIME_COUNT;
        this.show = false;
        this.timer = setInterval(() => {
          if (this.count > 0 && this.count <= TIME_COUNT) {
            this.count--;
          } else {
            this.show = true;
            clearInterval(this.timer);
            this.timer = null;
            //跳转的页面写在此处
            this.result_visible = false
            this.$closePage(this.$route)
            this.$router.push('/meetingManagement/my');
          }
        }, 1000)
      }
    },
  },
}
</script>

<style scoped>
.hr {
  border: none;
  height: 1px;
  background-color: #e5e5e5;
}
</style>

