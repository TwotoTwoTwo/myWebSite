<template>
  <div class='back' v-if="type !== 3">
    <div id='LRframe' v-if="type === 0">
      <div style="text-align:right;width: max-content;margin:auto;">
        ID: <input v-model="userId" class="LRinput"/><br>
        密码: <input v-model="userPassword" class="LRinput"/><br>
      </div>
      <button class="RLButton" @click="login()">登录</button>
      <button class="RLButton" @click="cancle()">取消</button>
    </div>
    <div id='LRframe' v-else-if="type === 1">
      <div style="text-align:right;width: max-content;margin:auto;">
        <b>*</b>名称: <input v-model='userName' maxlength='8' placeholder="起个名字..." class="LRinput"/><br>
        <b>*</b>密码: <input v-model='userPassword' maxlength='8' placeholder="设个密码..." class="LRinput"/><br>
        <div style="text-align: center;width:100%;">
          男<input v-model='sex' type='radio' value="0" />
          女<input v-model='sex' value='1' type='radio'/><br/>
        </div>
        年龄: <input v-model='age' type="text" oninput="value=value.replace(/[^\d]/g,'')" maxlength='4' placeholder="不填就是60岁..." class="LRinput"/><br>
        个性签名: <input v-model='declaration' placeholder="不填就是杀死噶..." class="LRinput"/><br>
      </div>
      <span>*为必填项</span><br/><br/>
      <button class="RLButton" @click="register()">注册</button>
      <button class="RLButton" @click="cancle()">取消</button>
    </div>
    <div id='LRframe' v-else-if="type === 2">
      <b>恭喜您注册成功，你的账号ID是{{userId}}(请不要忘记哦)</b><br/><br/><br/>
      <button class="RLButton" @click="reload()">确认</button>
    </div>
  </div>
</template>

<script>
import Vue from 'vue'
import axios from 'axios'
export default {
  data () {
    return {
      type: 0,
      userName: '',
      userPassword: '',
      userId: '',
      sex: '0',
      age: '60',
      declaration: ''
    }
  },
  methods: {
    login () {
      var loginPathUrl = this.$baseUrl + 'login'
      let form = {
        userId: this.userId,
        userPassword: this.userPassword
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
            // 服务端加密userPassword生成token权限认证字符串
            token: resp.data['userPassword']
          }
          // sessionstorage 防止刷新
          window.sessionStorage.setItem('user', JSON.stringify(user))
          Vue.prototype.$user = user
          this.$toast('LRframe', '登录成功')
          this.cancle()
          location.reload()
        } else this.$toast('LRframe', '密码或ID错误')
      }).catch(error => {
        this.$toast('LRframe', '发生错误=>' + error)
      })
    },
    register () {
      if (this.userName === '' || this.userPassword === '') {
        this.$toast('LRframe', '请填必填项')
        return
      }
      let registerPathUrl = this.$baseUrl + 'register'
      let form = {
        userName: this.userName,
        userPassword: this.userPassword,
        sex: this.sex,
        age: this.age,
        declaration: this.declaration
      }
      axios({
        data: form,
        url: registerPathUrl,
        method: 'post'
      }).then(resp => {
        var user = {
          userName: resp.data['userName'],
          userId: resp.data['userId'],
          sex: resp.data['sex'],
          age: resp.data['age'],
          declaration: resp.data['declaration'],
          // 服务端加密userPassword生成token权限认证字符串
          token: resp.data['userPassword']
        }
        this.userId = resp.data['userId']
        // sessionstorage 防止刷新
        window.sessionStorage.setItem('user', JSON.stringify(user))
        Vue.prototype.$user = user
        this.$set(this, 'type', 2)
      }).catch(error => {
        this.$toast('LRframe', '发生错误=>' + error)
      })
    },
    cancle () {
      document.body.style.overflow = 'scroll'
      this.$set(this, 'type', 3)
    },
    reload () {
      document.body.style.overflow = 'scroll'
      this.$set(this, 'type', 3)
      location.reload()
    }
  }
}
</script>

<style>
  .back{
    position: absolute;
    left: 0px;
    top: 0px;
    width: 100%;
    height: 100%;
    background: #b8c9ecb3;
    text-align: center;
    display: flex;
    align-items:center; /*实现垂直居中*/
  }
  #LRframe{
    position: relative;
    left: 0px;
    right:0px;
    width: 30%;
    min-width: fit-content;
    height: fit-content;
    border-radius: 20px;
    background: rgb(255, 255, 255);
    margin:auto;
    padding-top: 50px;
    padding-bottom: 30px;
    box-sizing: border-box;
    text-align: center;
  }
  .LRinput{
    padding: 5px;
    border: 0px;
    background: whitesmoke;
    margin-bottom: 10px;
    border-radius: 5px;
  }
</style>
