<template>
  <div v-if = "isAlready" class="homePage" id="homePage">
    <articlepage v-for = "(value, name,i) in data" :key='i' :info='value' ></articlepage>
    <button v-if="page>0" class="RLButton" @click="last()">上一页</button>
    <button v-if="nextPageData!=undefined" class="RLButton" style="float: right" @click="next()">下一页</button>
  </div>
</template>
<script>
import axios from 'axios'
export default {
  name: 'homepage',
  data () {
    return {
      data: undefined,
      nextPageData: undefined,
      isAlready: false,
      category: undefined,
      page: 0
    }
  },
  methods: {
    async getPage () {
      var data
      var urlPath = this.$baseUrl + 'getArticles?page=' + this.page
      if (this.category !== undefined) urlPath += '&category=' + this.category
      var resp = await axios({
        url: urlPath,
        method: 'get'
      })
      // 返回数据为0，已经分页到尾
      if (Object.keys(resp.data).length === 0) data = undefined
      else data = resp.data
      // console.log(data)
      return data
    },
    async next () {
      // 下一页变成当前页，请求下下页作为下页，并恢复页号
      this.page = this.page + 2
      this.data = this.nextPageData
      this.nextPageData = await this.getPage()
      this.page--
      console.log(this.data)
    },
    async last () {
      this.page = this.page - 1
      this.nextPageData = this.data
      this.data = await this.getPage()
    }
  },
  async activated () {
    this.category = this.$category[0]
    // console.log(this.category)
    // 首页
    this.data = await this.getPage()
    this.page++
    this.nextPageData = await this.getPage()
    this.page--
    // console.log(this.data)
    this.isAlready = true
  },
  updated () {
    // 根节点html，滚蛋回顶部
    document.documentElement.scrollTop = 0
  }
}
</script>
<style>
.homePage{
  width:100%;
}
</style>
