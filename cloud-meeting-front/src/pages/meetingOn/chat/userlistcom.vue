<template>
    <a-list item-layout="horizontal" :data-source="data" class="item">
        <a-list-item slot="renderItem" slot-scope="item" >
            <a-list-item-meta
                    description=""
            >
                <a slot="title" href="https://www.antdv.com/">{{ item.title }} <hr /></a>


            </a-list-item-meta>
        </a-list-item>
    </a-list>
</template>
<style>
    .item{
        border-inline-color: #0086DB;
        border-inline-style: dashed;
    }
</style>
<script>
    const data = [
        {
            title: 'Ant Design Title 1',
        },
        {
            title: 'Ant Design Title 2',
        },
        {
            title: 'Ant Design Title 3',
        },
        {
            title: 'Ant Design Title 4',
        },
    ];
    export default {
        data() {
            return {
                data,
            };
        },
    };
</script>
<style></style>
