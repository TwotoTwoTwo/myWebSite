<template>
  <div class="topBar" >
      <h1 style="float:left;margin-top:20px;background: rgb(43, 42, 42);">{{userName}}</h1>
      <h3 style="float:left;margin-left:2%;margin-top:20px;">{{declaration}}</h3>
      <div class="RLButton" @click="jump(item)"
      v-for="(item,i) in categories" :key="i">
      {{item}}
      </div>
      <br/>
      <br/>
      <button class="RLButton" @click="login()">登录</button>
      <button  class="RLButton" @click="register()">注册</button>
  </div>
</template>

<script>
import axios from 'axios'
export default {
  data () {
    return {
      userName: this.$user['userName'],
      declaration: this.$user['declaration'],
      categories: Array
    }
  },
  created () {
    var path = this.$baseUrl + 'categories'
    axios({
      url: path,
      method: 'get'
    }).then(resp => {
      this.categories = resp.data
    })
  },
  methods: {
    login () {
      // 0是弹出登录界面
      this.$loginOrRegisterFace(0)
      document.body.style.overflow = 'hidden'
    },
    register () {
      // 1是弹出注册界面
      this.$loginOrRegisterFace(1)
      document.body.style.overflow = 'hidden'
    },
    jump (item) {
      // 点击标签,触发父组件方法
      this.$emit('jump', '/homePage', item)
    }
  }
}
</script>

<style>
  .RLButton{
    padding: 5px;
    margin-right: 5px;
    border-radius: 3px;
    background: rgb(43, 42, 42);
    color: white;
    border:1px solid rgb(48, 150, 184);
    transition: all .3s ease 0s;
    display: inline-block;
  }
  .RLButton:hover{
    padding: 10px;
    background: rgb(101, 178, 230);
    cursor: pointer;
  }
  .topBar{
    text-align:right;
    padding-top:5px;
    padding-bottom: 20px;
    margin: auto;
    color: rgb(80, 221, 240);
  }
</style>
