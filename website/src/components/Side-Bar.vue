<template>
  <div class="sideBar">
    <img :src="headerUrl" style="padding:10px;width: 80px;height: 80px;float:left;object-fit: cover;border-radius: 50%;"/><br/>
    <div style="text-align: left;">
      <p style="color: white">昵称：{{userName}}</p>
      <p style="color: white">性别：{{sex}}</p>
      <p style="color: white">年龄：{{age}}岁</p><br/>
    </div><br/>
    <div>
      <p style="color: rgb(210,210,220)">
        <b>每日(月(年)) 一诗</b><br/><br/>
        鲲行歌<br/>
        我欲上天<br/>
        驾鲲而行<br/>
        高唱一句<br/>
        鸡你太美<br/>
      </p><br/>
    </div>
    <hr style="width:90%;margin-left: 5%;" color="#181c19"/><br/>
    <h2>欢迎各位客官</h2><br/>
    <hr style="width:90%;margin-left: 5%;" color="#181c19"/>
    <clock></clock>
    <hr style="width:90%;margin-left: 5%;" color="#181c19"/>
  </div>
</template>

<script>
export default {
  nmae: 'sideBar',
  data () {
    return {
      headerUrl: this.$baseUrl + 'static-resources/header/header.jpg',
      userName: this.$user.userName,
      age: this.$user.age,
      sex: this.$user.sex === '0' ? 'boy' : 'girl'
    }
  }
}
</script>

<style>
  .sideBar{
    width: 15%;
    border-radius: 20px;
    text-align: center;
    box-shadow: 1px 1px 10px 1px whitesmoke;
    float: left;
    padding-bottom: 100px;
    margin-left: 1%;
  }
</style>
