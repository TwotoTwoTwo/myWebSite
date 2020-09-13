import Vue from 'vue'
import Router from 'vue-router'

// 主组件
// import topNavbar from '@/components/TopNavbar'
// import sideBar from '@/components/SideBar'
// import header from '@/components/Header'
// import main from '@/components/Main'
// 组件子页面
import editormd from '@/components/page/Editormd'
import homePage from '@/components/page/HomePage'
import aboutPage from '@/components/page/AboutPage'
import buisnessPage from '@/components/page/BusinessPage'
import mdPage from '@/components/page/MdPage'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/homePage',
      name: 'homePage',
      component: homePage
    },
    {
      path: '/editorPage',
      name: 'editorPage',
      component: editormd
    },
    {
      path: '/',
      name: 'aboutPage',
      component: aboutPage
    },
    {
      path: '/businessPage',
      name: 'businessPage',
      component: buisnessPage
    },
    {
      path: '/mdPage',
      name: 'mdPage',
      component: mdPage
    }
  ]
})
