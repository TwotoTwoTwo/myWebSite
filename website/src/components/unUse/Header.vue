<template>
  <div class="m-header">
    <h1 v-html='msg' style="margin-left: 20px"></h1>
    <h3 style="margin-left: 100px">你会看见他，他就在你背后，注视着你—— 炯兄</h3>
  </div>
</template>
<script>
export default {
  name: 'm-header',
  data () {
    return {
      msg: '一生无过&nbsp——&nbsp&nbsp 王四九'
    }
  }
}
</script>
<style>
  .m-header{
    opacity: 0.8;
    text-align: left;
    color: white;
    box-shadow: 0px 0px 10px 1px orange;
    margin-left: 10px;
    margin-right: 10px;
    margin-bottom: 1%;
    padding:10px;
    box-sizing: border-box;
  }
</style>
