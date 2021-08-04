<template>
    <div class="send">
        <input @keydown.enter="send" v-model="inputContent" type="text">
        <button @click="send">发送</button>
    </div>
</template>

<script>
    export default {
        name: "Chatinputcom",
        data(){
            return{
                inputContent:""
            }
        },
        //父元素向（子元素传递）
        props:[
            'sendEvent'
        ],
        methods:{
            send:function () {
                this.sendEvent(this.inputContent)
                this.inputContent=""
            }
        }
    }
</script>

<style scoped>
    .send{
        margin: 0px auto;
        position: absolute;
        bottom: 0;
    }
    .send input{
        width: 200px;
        height: 30px;
        border-radius: 10px;
        border: 1px solid #ccc;
        outline: none;
    }
    .send button{
        width: 50px;
        height: 30px;
        background-color: #0086DB;
        border: 0;
        border-radius: 10px;
        color: white;
        font-size: 12px;
        cursor: pointer;
        margin-left: 10px;
    }
</style>
