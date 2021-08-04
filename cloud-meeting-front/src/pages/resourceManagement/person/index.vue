<template>
  <a-card
    :body-style="{
      padding: '24px 32px',
      textAlign: 'left',
    }"
  >
    <a-row>
      <a-col :span="6">
        部门
        <a-icon
          type="plus-circle"
          @click="showAddModel"
          style="margin-left: 75%"
          v-if="role == 'founder'"
        />
        <a-tree
          :load-data="onLoadDepartment"
          :tree-data="treeData"
          showLine
          blockNode
        >
          <template slot="company" slot-scope="item">
            <span @mouseenter="showTree" @contextmenu.prevent="hide">
              {{ item.title }}
              <a-button-group v-show="treeShow" style="margin-left: 40%">
                <a-tooltip
                  placement="topLeft"
                  title="显示企业内无部门员工"
                  arrow-point-at-center
                >
                  <a-icon type="eye" @click="check(item.key)" />
                </a-tooltip>
              </a-button-group>
            </span>
          </template>
          <template slot="custom" slot-scope="item">
            <span @mouseenter="showTree" @contextmenu.prevent="hide">
              {{ item.title }}
              <a-button-group v-show="treeShow" style="margin-left: 50%">
                <a-tooltip
                  placement="topLeft"
                  title="更改部门名称"
                  arrow-point-at-center
                  v-if="role == 'founder'"
                >
                  <a-icon type="edit" @click="showEdit(item.key)" />
                </a-tooltip>

                <a-popconfirm
                  title="确定删除该部门吗？删除后部门员工将变为无部门状态！"
                  ok-text="确定"
                  cancel-text="取消"
                  @confirm="deleteDepar(item.key)"
                  @cancel="cancel"
                  v-if="role == 'founder'"
                >
                  <a-tooltip
                    placement="topLeft"
                    title="删除部门"
                    arrow-point-at-center
                    v-if="role == 'founder'"
                  >
                    <a-icon type="delete" style="margin-left: 30%" />
                  </a-tooltip>
                </a-popconfirm>
                <a-tooltip
                  placement="topLeft"
                  title="显示部门成员"
                  arrow-point-at-center
                >
                  <a-icon
                    type="eye"
                    @click="check(item.key)"
                    style="margin-left: 30%"
                  />
                </a-tooltip>
              </a-button-group>
            </span>
          </template>
        </a-tree>

        <a-tag
          >鼠标移入名称会出现可进行操作<br />右键点击名称操作按钮会隐藏<br />点击企业名称右侧按钮可查看暂未分配部门的员工<br />点击“用户”可查看企业全部员工<br />管理员不区分部门</a-tag
        >

        <!-- 添加部门 -->
        <a-modal
          v-model="addDepVisible"
          title="添加部门"
          @ok="addDepartment"
          @cancel="cancel"
        >
          <a-form :form="addDepForm">
            <a-form-item
              ref="departmentName"
              label="部门名称"
              prop="departmentName"
            >
              <a-input
                placeholder="请输入部门名称"
                allow-clear
                v-decorator="[
                  'departmentName',
                  {
                    rules: [{ required: true, message: '部门名称不能为空！' }],
                  },
                ]"
              />
            </a-form-item>
          </a-form>
        </a-modal>

        <!-- 修改部门名字 -->
        <a-modal
          v-model="editDepVisible"
          title="修改部门名称"
          @ok="editDepar"
          @cancel="cancel"
        >
          <a-form :form="editDepForm">
            <a-form-item
              ref="departmentEditName"
              label="部门名称"
              prop="departmentEditName"
            >
              <a-input
                placeholder="请输入部门名称"
                allow-clear
                v-decorator="[
                  'departmentEditName',
                  {
                    rules: [{ required: true, message: '部门名称不能为空！' }],
                  },
                ]"
              />
            </a-form-item>
          </a-form>
        </a-modal>
      </a-col>
      <a-col :span="18">
        <a-row>
          <a-tabs default-active-key="1">
            <a-tab-pane key="1">
              <span slot="tab" @click="search">
                <a-icon type="user" />
                用户
              </span>
              <div
                style="margin-bottom: 16px"
                v-if="role == 'founder' || role == 'admin'"
              >
                <a-button
                  type="primary"
                  :disabled="hasSelected"
                  :loading="loading"
                  @click="invite"
                >
                  邀请
                </a-button>

                <!-- 批量解雇员工按钮 -->
                <a-button
                  type="danger"
                  :disabled="!hasSelected"
                  :loading="loading"
                  @click="deleted"
                  style="margin-left: 5%"
                >
                  解雇
                </a-button>

                <!-- 搜索框 -->
                <a-input-search
                  placeholder="请输入员工用户名"
                  style="width: 200px"
                  @search="onSearch"
                  v-model="searchName"
                />
                <span style="margin-left: 8px">
                  <template v-if="hasSelected">
                    {{ `选中 ${selectedRowKeys.length} 条数据` }}
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
                :scroll="{ x: 1500, y: 300 }"
              >
                <div slot="roles" slot-scope="roles">
                  <span v-if="roles[0].id == 'founder'">创始人</span>
                  <span v-if="roles[0].id == 'employee'">员工</span>
                  <span v-if="roles[0].id == 'admin'">管理员</span>
                </div>
                <div slot="action" slot-scope="record">
                  <a
                    v-if="role == 'founder' || role == 'admin'"
                    @click="edit(record.userId)"
                    >修改</a
                  >
                  <a
                    v-if="role == 'founder'"
                    @click="fire(record.userId)"
                    style="margin-left: 7%"
                  >
                    解雇
                  </a>
                </div>
              </a-table>
              <a-modal
                title="更改员工部门"
                :visible="visible"
                :confirm-loading="confirmLoading"
                @ok="handleOk"
                @cancel="handleCancel"
              >
                <a-form :form="form">
                  <a-form-item
                    ref="department"
                    label="选择部门"
                    prop="department"
                  >
                    <a-select
                      placeholder="请选择部门"
                      v-decorator="['department']"
                    >
                      <a-select-option value="1">
                        {{ companyName }}
                      </a-select-option>
                      <a-select-option
                        v-for="item in departmentData"
                        :value="item.id"
                        :key="item.id"
                      >
                        {{ item.name }}
                      </a-select-option>
                    </a-select>
                  </a-form-item>
                </a-form>
              </a-modal>
            </a-tab-pane>

            <!-- 管理员 -->
            <a-tab-pane key="2">
              <span slot="tab">
                <a-icon type="setting" @click="searchAdmin" />
                管理员
              </span>
              <div style="margin-bottom: 16px" v-if="role == 'founder'">
                <a-button type="primary" :loading="loading" @click="addManager">
                  添加
                </a-button>
              </div>
              <a-table
                bordered
                :columns="columns"
                :data-source="dataAdmin"
                :scroll="{ x: 1500, y: 300 }"
              >
                <div slot="roles" slot-scope="roles">
                  <span v-if="roles[0].id == 'founder'">创始人</span>
                  <span v-if="roles[0].id == 'employee'">员工</span>
                  <span v-if="roles[0].id == 'admin'">管理员</span>
                </div>
                <a
                  v-if="role == 'founder'"
                  slot="action"
                  slot-scope="text, record"
                  @click="deleteAdmin(record.userId)"
                  >移除</a
                >
              </a-table>
            </a-tab-pane>
          </a-tabs>
        </a-row>
      </a-col>
    </a-row>
  </a-card>
</template>

<script>
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
  { title: "性别", dataIndex: "sex", key: 1 },
  { title: "部门", dataIndex: "departmentName", key: 2 },
  { title: "手机", dataIndex: "telephone", key: 3 },
  { title: "邮箱", dataIndex: "email", key: 4 },
  {
    title: "操作",
    key: "operation",
    fixed: "right",
    width: 100,
    scopedSlots: { customRender: "action" },
  },
];

import { mapGetters } from "vuex";
import {
  fetchPerson,
  fetchAdmin,
  deleteAdmin,
  searchInformation,
  firePerson,
} from "@/services/company";
import {
  departmentList,
  upadteDepartment,
  findEmployee,
  deleteDerpartment,
  createDepartment,
  editDepartment,
} from "@/services/department";

export default {
  name: "person",
  data() {
    return {
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
      visible: false,
      AdminVisible: false,
      addDepVisible: false,
      editDepVisible: false,
      confirmLoading: false,
      person: [],
      role: "",
      searchName: "",
      departmentData: [],
      userCode: 0,
      treeShow: false,
      editId: 0,
    };
  },
  computed: {
    hasSelected() {
      return this.selectedRowKeys.length > 0;
    },
    ...mapGetters("account", [
      "username",
      "userId",
      "companyId",
      "roles",
      "companyName",
    ]),
  },
  beforeCreate() {
    this.form = this.$form.createForm(this, "form");
    this.addDepForm = this.$form.createForm(this, "addDepForm");
    this.editDepForm = this.$form.createForm(this, "editDepForm");
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
  created() {
    this.role = this.roles[0].id;
    this.fetchPerson();
    this.fetchAdmin();
    this.treeData[0].title = this.companyName;
  },
  methods: {
    // 渲染企业用户
    fetchPerson() {
      fetchPerson(this.companyId).then((res) => {
        this.data = res.data.data;
      });
      this.searchName = "";
    },
    // 渲染管理员
    fetchAdmin() {
      fetchAdmin(this.companyId).then((res) => {
        this.dataAdmin = res.data.data;
      });
    },
    // 邀请加入企业
    invite() {
      this.$router.push({ path: "/resourceManagement/invite" });
    },
    // 添加管理员
    addManager() {
      this.$router.push({ path: "/resourceManagement/manager" });
    },
    // 更改部门
    edit(id) {
      this.visible = true;
      this.userCode = id;
      departmentList(this.companyId).then((res) => {
        const listRes = res.data;
        if (listRes.code == 200) {
          this.departmentData = listRes.data;
        }
      });
    },
    // 提交更改部门请求
    handleOk() {
      var department = this.form.getFieldValue("department");
      if (department == "1") {
        department = null;
      }
      upadteDepartment(this.userCode, department, this.companyId).then(
        (res) => {
          const updateRes = res.data;
          if (updateRes.code == 200) {
            this.visible = false;
            this.$message.success("更改部门成功！");
            this.search();
          } else if (updateRes.code == 802) {
            this.visible = false;
            this.$message.error("企业创始人不可以被分配部门");
          }
        }
      );
    },
    // 取消更改
    handleCancel() {
      this.visible = false;
    },
    // 渲染公司全部用户
    search() {
      this.fetchPerson();
    },
    // 渲染管理员
    searchAdmin() {
      this.fetchAdmin();
    },

    // 根据用户名搜索公司用户
    onSearch(name) {
      if (name != "") {
        searchInformation(name, this.companyId).then((res) => {
          const searchRes = res.data;
          if (searchRes.code == 200) {
            this.data = res.data.data;
            this.searchName = "";
          }
        });
      } else {
        this.search();
      }
    },

    // 批量解雇用户
    deleted() {
      var idList = this.selectedRowKeys.join();
      this.$confirm({
        title: "你确定要解雇这些员工吗？",
        onOk: () => {
          firePerson(this.companyId, idList).then((res) => {
            const fireRes = res.data;
            if (fireRes.code == 200) {
              this.$message.success("解雇成功！");
              this.selectedRowKeys = [];
              this.search();
            } else if (fireRes.code == 601) {
              this.$message.error(fireRes.errMsg);
              this.selectedRowKeys = [];
              this.search();
            }
          });
        },
        onCancel() {},
      });
    },

    // 单行解雇用户
    fire(id) {
      this.$confirm({
        title: "你确定要解雇该员工吗？",
        onOk: () => {
          firePerson(this.companyId, id).then((res) => {
            const fireRes = res.data;
            if (fireRes.code == 200) {
              this.$message.success("解雇成功！");
              this.search();
            } else if (fireRes.code == 601) {
              this.$message.error(fireRes.errMsg);
              this.search();
            }
          });
        },
        onCancel() {},
      });
    },

    // 移除管理员
    deleteAdmin(id) {
      deleteAdmin(id, this.companyId).then((res) => {
        const afterDelete = res.data;
        if (afterDelete.code == 200) {
          this.$message.success("移除成功！");
          this.searchAdmin();
        }
      });
    },
    onSelectChange(selectedRowKeys) {
      this.selectedRowKeys = selectedRowKeys;
    },

    // 树形控件加载部门名称
    onLoadDepartment() {
      return new Promise((resolve) => {
        departmentList(this.companyId).then((res) => {
          const listRes = res.data;
          if (listRes.code == 200) {
            if (listRes.data == null) {
              this.$message.error("该企业还没有部门！");
            } else {
              for (var i = 0; i < listRes.data.length; i++) {
                this.$set(this.treeData[0].children, i, {
                  title: listRes.data[i].name,
                  key: listRes.data[i].id,
                  isLeaf: true,
                  scopedSlots: { title: "custom" },
                });
              }
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
      if (department == 0) {
        department = "";
      }
      findEmployee(department, this.companyId).then((res) => {
        const findRes = res.data;
        if (findRes.code == 200) {
          this.data = findRes.data;
        }
      });
    },
    // 鼠标移入事件
    showTree() {
      this.treeShow = true;
    },
    // 鼠标右击事件
    hide() {
      this.treeShow = false;
    },
    // 显示更改部门名称弹窗显示
    showEdit(key) {
      this.editId = key;
      this.editDepVisible = true;
    },
    // 更改部门名称
    editDepar() {
      this.editDepForm.validateFieldsAndScroll((err) => {
        if (!err) {
          const depName = this.editDepForm.getFieldValue("departmentEditName");
          editDepartment(this.editId, depName).then((res) => {
            const editRes = res.data;
            if (editRes.code == 200) {
              this.$message.success("更改部门名称成功！");
              this.editDepVisible = false;
              this.editDepForm.resetFields();
              const route = "/resourceManagement/person";
              this.$refreshPage(route);
            } else if (editRes.code == 803) {
              this.$message.error("部门已存在");
              this.editDepForm.resetFields();
            }
          });
        }
      });
    },
    // 删除部门
    deleteDepar(key) {
      deleteDerpartment(key).then((res) => {
        const deleteRes = res.data;
        if (deleteRes.code == 200) {
          this.$message.success("删除部门成功！");
          const route = "/resourceManagement/person";
          this.$refreshPage(route);
        }
      });
    },
    cancel() {},

    // 添加部门弹窗出现
    showAddModel() {
      this.addDepVisible = true;
    },
    // 添加部门
    addDepartment() {
      this.addDepForm.validateFieldsAndScroll((err) => {
        if (!err) {
          const depName = this.addDepForm.getFieldValue("departmentName");
          createDepartment(this.companyId, depName).then((res) => {
            const addRes = res.data;
            if (addRes.code == 200) {
              this.$message.success("添加部门成功！");
              this.addDepVisible = false;
              this.addDepForm.resetFields();
              const route = "/resourceManagement/person";
              this.$refreshPage(route);
            } else if (addRes.code == 800) {
              this.$message.error(addRes.errMsg);
              this.addDepForm.resetFields();
            }
          });
        }
      });
    },
  },
};
</script>

<style lang="less" scoped>
@import "./index";
</style>