<template>
  <div id="home" v-if="flag">
    <sidebar></sidebar>
    <maincontent></maincontent>
    <div class="footer">我是页脚</div>
  </div>
</template>
<script>
import axios from 'axios'
import Vue from 'vue'
export default {
  name: 'home',
  data () {
    return {
      msg: '欢迎光临',
      userName: this.$route.query.userName,
      userId: this.$route.query.userId,
      flag: false
    }
  },
  created () {
    if (this.$user === undefined) {
      var loginPathUrl = this.$baseUrl + 'login'
      loginPathUrl = loginPathUrl + '?userId=' + '1'
      axios({
        url: loginPathUrl,
        method: 'get'
      }).then(resp => {
        if (resp.data['userName'] === undefined) alert('请输入正确的通行码')
        else {
          var user = {
            userName: resp.data['userName'],
            userId: resp.data['userId'],
            sex: resp.data['sex'],
            age: resp.data['age'],
            declaration: resp.data['declaration'],
            userPassword: resp.data['userPassword']
          }
          Vue.prototype.$user = user
          this.$router.push({
            path: '/home',
            query: {
            }
          })
          this.flag = true
        }
      })
        .catch(error => {
          alert(error)
        })
    } else this.flag = true
  }
}
</script>
<style>
  #home {
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  width: 100%;
  min-height: 100%;
  height:fit-content;
  position: absolute;
  box-sizing: border-box;
}
  .footer{
    height: 50px;
    line-height: 50px;
    position: absolute;
    bottom: 0px;
    width: 100%;
  }
</style>
