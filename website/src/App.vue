<template>
  <div id="home" v-if="flag">
    <maincontent></maincontent>
    <div class="footer" ><a target="_blank" href="http://beian.miit.gov.cn/" >粤ICP备20014912号</a></div>
  </div>
</template>

<script>
import Vue from 'vue'
import axios from 'axios'
// import axios from 'axios'
export default {
  name: 'APP',
  data () {
    return {
      userName: '',
      userId: '',
      flag: false,
      isShow: false
    }
  },
  created () {
    let user = window.sessionStorage.getItem('user')
    if (user !== null) {
      user = JSON.parse(user)
      Vue.prototype.$user = user
      this.flag = true
      return
    }
    var loginPathUrl = this.$baseUrl + 'login'
    let form = {
      userId: '0'
    }
    axios({
      data: form,
      url: loginPathUrl,
      method: 'post'
    }).then(resp => {
      if (resp.data['userName'] !== undefined) {
        var user = {
          userName: resp.data['userName'],
          userId: resp.data['userId'],
          sex: resp.data['sex'],
          age: resp.data['age'],
          declaration: resp.data['declaration'],
          token: null
        }
        // sessionstorage 防止刷新
        window.sessionStorage.setItem('user', JSON.stringify(user))
        Vue.prototype.$user = user
      } else {
        this.$toast('container', '服务器发生错误')
      }
      this.flag = true
    }).catch(error => {
      this.$toast('container', '发生错误=>' + error)
    })
  },
  mounted () {
    // 更新title
    if (this.$user['userName'] !== undefined) document.title = this.$user['userName'] + '的星际航行'
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
  left: 0px;
  top: 0px;
}
  .footer{
    height: 50px;
    line-height: 50px;
    position: absolute;
    bottom: 0px;
    width: 100%;
    color: rgb(231, 223, 223);
  }
</style>
