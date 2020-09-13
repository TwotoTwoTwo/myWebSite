<template>
  <div class="loginBox">
    <input class="inputBox" placeholder="请输入你的星球通行码" v-model="inputValue"/>
    <br/>
    <button id="jump" @click="login()">进入新世界</button>
  </div>
</template>
<script>
import axios from 'axios'
import Vue from 'vue'
export default {
  name: 'hello',
  data () {
    return {
      msg: '欢迎来到菜鸟教程！',
      inputValue: ''
    }
  },
  methods: {
    login () {
      var loginPathUrl = this.$baseUrl + 'login'
      loginPathUrl = loginPathUrl + '?userId=' + this.inputValue
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
        }
      })
        .catch(error => {
          alert(error)
        })
    }
  }
}
</script>
<style>
#jump {
  color: whitesmoke;
  margin-top: 5px;
  border:5px solid rgb(112, 115, 116);
  outline: none;
  border-radius: 30px;
  background:linear-gradient(to right,#6ddbdb,#330867);
  padding: 5px;
}
.loginBox{
  text-align: center;
  position: relative;
  top: 40%;
}
.inputBox{
  background:whitesmoke;
  border: 0px;
  opacity: 0.8;
  border-radius: 5px;
  position: relative;
  top: 50%;
  height: 30px;
  padding-left: 5px;
}
</style>
