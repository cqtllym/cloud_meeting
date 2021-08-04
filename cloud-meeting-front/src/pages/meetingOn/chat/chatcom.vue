<template>
    <div class="chat">
        <h1 class="user">用户：{{currentuser.username}}</h1>
        <div class="chatlist">
            <chatItem v-for="(item,index) in chatlist" :chatitem="item" :key="'chatlist' + index">
                {{item.chatcontent}}
            </chatItem>
        </div>
        <chatinputcom :sendEvent="sendEvent"></chatinputcom>
    </div>
</template>

<script>
    import Chatinputcom from "./chatinputcom";
    import ChatItem from "./chatitem";
    export default {
        name: "chatcom",
        props:[
            'currentuser'
        ],
        components:{
            Chatinputcom,
            ChatItem
        },
        data(){
            return{
                chatlist:[
                    {
                        user:{
                            username:"小明",
                            headerimg:require('../../../assets/img/1.jpg')
                        },
                        chatcontent:'吃了'
                    }
                ]
            }
        },
        methods:{
            sendEvent:function (value) {
                this.chatlist.push({
                    user:{
                        username:"小明",
                        headerimg:require('../../../assets/img/1.jpg')
                    },
                    chatcontent:value
                })
                console.log(this)
                this.chatlist.push({
                    user:this.currentuser,
                    chatcontent: new Date()
                })
            }
        }
    }
</script>

<style scoped>
    .chat{
        width: 500px;
        height: 700px;
        border: 1px solid palegoldenrod;
        margin: 0px auto;
        box-sizing: border-box;
        position: relative;
    }
</style>
