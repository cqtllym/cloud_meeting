<template>
    <!-- <page-layout title="参会人列表"> -->
        <div>
            <div class="show-more-body">
                <div class="show-more-msg-content">
                    <a-card>
                        <div style="font-size:20px;padding:5px;">会议号：{{met_number}}</div>
                        <a-table
                            row-key="user_id"
                            style="margin-bottom: 24px;"
                            :columns="goodsColumns"
                            :dataSource="goodsData"
                            :pagination="false"
                        >
                        </a-table>
                    </a-card>
                 </div>
            </div>
                      
        </div>
    <!-- </page-layout> -->

</template>
<script>

const goodsColumns = [
  {
    title: '用户ID',
    dataIndex: 'user_id',
    key: 'user_id'
  },
  {
    title: '用户名',
    dataIndex: 'username',
    key: 'username'
  },
];
const goodsData = [];
const met_number = 0;
export default {
    data() {
      return {
            goodsColumns,
            goodsData,
            met_number
      }
    },
    created(){
      this.getmsg();
    },
    methods: {
        getmsg() {
            this.goodsData=this.$route.params.the_list; 
            this.met_number=this.$route.params.met_number;
            console.log(this.goodsData)
        },
    }
}
</script>