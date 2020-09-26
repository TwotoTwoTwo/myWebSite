<template>
  <div class='aboutPage'>
    <div style="width: 100%;height: fit-content;text-align:center;">
      <h2 style="background: gray;color: white;border-radius:5px">{{userName}}的博客</h2>
      <br/>
      <img :src="headerUrl"
    style="margin: auto;width: 80px;height: 80px;
    object-fit: cover;border-radius: 50%;"/>
    </div>
    <br/>
    <div v-if="isMaster" style="width:100%;">
        <p>俗人一个</p>
        <br/>
        <img src='../../assets/wechat.png' style="width:10%"/>
        <img src='../../assets/vertical_line.png' style="width:10%"/>
        <img src='../../assets/qq.png' style="width:10%"/>
        <img src='../../assets/vertical_line.png' style="width:10%"/>
        <a href="https://github.com/TwotoTwoTwo" target="_blank"><img src='../../assets/github.png' style="width:10%"/></a>
        <br/>
        <br/>
        <div style="width:60%;display:inline;text-align:right;">ip访问量：</div>
        <div style="width:20%;display:inline;text-align:left;">{{ipNums}}</div><br/>
        <div style="width:60%;display:inline;text-align:right;">站点访问：</div>
        <div style="width:20%;display:inline;text-align:left;">{{vistorNums}}</div>

    </div>
    <clock></clock>
  </div>
</template>
<script>
import axios from 'axios'
export default {
  name: 'about',
  data () {
    return {
      headerUrl: this.$baseUrl + 'static-resources/header/header.jpg',
      userName: this.$user.userName,
      age: this.$user.age,
      sex: this.$user.sex === '0' ? 'boy' : 'girl',
      ipNums: 0,
      vistorNums: 0,
      isMaster: true
    }
  },
  async activated () {
    var rsp = await axios({
      url: this.$baseUrl + 'ipNums'
    })
    this.ipNums = rsp.data
    rsp = await axios({
      url: this.$baseUrl + 'vistorNums'
    })
    this.vistorNums = rsp.data
  },
  created () {
    var userId = this.$user['userId']
    if (userId === '0') this.isMaster = true
    else this.isMaster = false
  }
}
</script>
<style>
.aboutPage{
  text-align: center;
  color: black;
  font: large;
  padding: 20px;
  background: white;
  width: 200px;
  height: fit-content;
  min-height: 400px;
  border-radius: 10px;
  box-shadow: 5px 5px 10px 5px rgba(17, 13, 252, 0.5);
}
</style>
