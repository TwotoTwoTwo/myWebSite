<template>
  <div class="mc" id='mc'>
    <topbar @jump="jump"></topbar>
    <div v-if=isShow style="display:inline-block;width:86%;vertical-align: top;">
      <keep-alive exclude="mdPage"><router-view/></keep-alive>
    </div>
    <div class="navbar">
      <button class="navButton" @click="jump('/homePage')">首页</button>
      <button class="navButton" @click="jump('/editorPage')"  v-if="isLogin">随笔</button>
      <button class="navButton" @click="jump('/')">关于我</button>
      <button class="navButton" @click="jump('/businessPage')">商业互吹联系</button>
    </div>
  </div>
</template>
<script>
export default {
  name: 'MC',
  props: {
    userId: String
  },
  data () {
    return {
      isShow: true,
      isLogin: this.$user.token !== undefined && this.$user.token !== null
    }
  },
  methods: {
    jump (param, category) {
      this.$category[0] = category
      // console.log(param, this.$category)
      if (this.$route.path === param && param !== '/homePage') return false
      if (this.$route.path === param && param === '/homePage') {
        this.isShow = false
        this.$nextTick(() => {
          this.isShow = true
        })
      } else this.$router.push(param)
    },
    jumpp (category) {
      // 标签跳转，绑定标签
      // console.log(category)
      this.$category = category
      // console.log(this.$category)
      // this.$router.go(-1)
      this.jump('/homePage')
    }
  }
}
</script>
<style>
.mc{
  min-height: 100vh;
  width:100%;
  text-align: center;
  color: white;
  display: inline-block;
  padding: 1%;
  padding-bottom: 50px;
  box-sizing: border-box;
}
.navbar{
  opacity: 1;
  width: 12%;
  float: right;
  border-radius: 10px;
  box-shadow: 0px 0px 40px 1px rgba(29, 157, 231, 0.5);
}
.navButton{
  width: 100%;
  height: 60px;
  text-align: center;
  transition: cubic-bezier(0.075, 0.82, 0.165, 1);
  border:0px solid beige;
  margin-right: 10px;
  background: rgb(51, 50, 50,0);
  filter:alpha(0.5);
  color: #fff8;
  border-radius: 5px;
  box-sizing: content-box;
  margin-top: 30px;
}
.navButton:hover{
  background: linear-gradient(left,white,rgb(162, 201, 247));
  color: #000;
  cursor: pointer;
}
</style>
