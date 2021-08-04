<template>
    <div class="chatItem">
        <img :style="imgStyle"  :src="chatitem.user.headerimg" alt="">
        <div>
            <slot></slot>
        </div>
    </div>
</template>

<script>
    export default {
        name: "ChatItem",
        props:[
            'chatitem'
        ],
        data(){
            return{
                imgStyle:{
                    width:"60px",
                    height:"60px"
                }
            }
        }
    }
</script>

<style scoped>
    .chatItem{
        display: flex;
    }
</style>
