// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router/Index'
import mavonEditor from 'mavon-editor'
import articlepage from '@/components/page/ArticlePage'
import sidebar from '@/components/Side-Bar'
import main from '@/components/Main'
import toast from '@/components/Toast'
import loginOrRegisterFace from '@/components/LoginOrRegisterFace'
import clock from '@/components/clock'
import topBar from '@/components/TopBar'
import 'mavon-editor/dist/css/index.css'
// js调用自定义提示框，提示框挂载处理
let toastT = function () {
  let id = arguments[0]
  let c = arguments[1]
  let time = arguments[2]
  if (time === undefined) time = 2
  let T = Vue.extend(toast)
  let message = {
    message: c,
    time: time
  }
  let t = new T({
    data: message
  })
  t.vm = t.$mount()
  let parent = document.getElementById(id)
  if (parent !== undefined) parent.appendChild(t.vm.$el)
}
Vue.prototype.$toast = toastT

// 登录和注册的弹框
let LRFace = function (type) {
  let T = Vue.extend(loginOrRegisterFace)
  let data = {
    type: type
  }
  let instance = new T({
    data: data
  })
  instance.vm = instance.$mount()
  document.body.appendChild(instance.vm.$el)
}
Vue.prototype.$loginOrRegisterFace = LRFace
Vue.prototype.$baseUrl = 'http://www.wsjiu.com/Server/'
Vue.prototype.$markdownIt = mavonEditor.markdownIt
Vue.prototype.$category = new Array(1)
Vue.config.productionTip = false
Vue.use(mavonEditor)
Vue.component('clock', clock)
Vue.component('articlepage', articlepage)
Vue.component('sidebar', sidebar)
Vue.component('maincontent', main)
Vue.component('loginOrRegisterFace', loginOrRegisterFace)
Vue.component('topbar', topBar)
/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App></App>'
})
