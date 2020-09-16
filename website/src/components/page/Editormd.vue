<template>
    <div>
      <div class="editormd">
        <input class="inputBox" placeholder="输入标题" maxlength='60' v-model="title"/>
        <input class="inputBox" placeholder="输入分类(默认随笔)" v-model="category"/><br/>
        <textarea class='summaryInput' v-model='summary' placeHolder='序言.....'></textarea>
        <mavon-editor v-model='content' ref=md @imgAdd="$imgAdd" @change="$change" @imgDel="$imgDel"/>
      </div>
      <button class="submitButton" @click="submit">提交</button>
    </div>
</template>
<script>
import axios from 'axios'

export default {
  name: 'editormd',
  data () {
    return {
      content: '', // 文章内容
      imgs: {}, // 图片
      articleId: undefined, // 文章编号
      isChange: false, // 标记是否修改
      title: undefined,
      date: undefined,
      category: undefined,
      summary: ''
    }
  },
  activated () {
    var info = this.$route.query.info
    if (info !== undefined) {
      this.articleId = info[0]
      this.title = info[1]
      this.date = info[3]
      this.summary = info[2]
      this.category = info[4]
      this.content = info[8]
    } else {
      this.articleId = undefined
      this.title = undefined
      this.date = undefined
      this.summary = ''
      this.category = undefined
      this.content = ''
    }
  },
  methods: {
    $imgAdd (pos, $file) {
      // 将图片转成储存在一个观察者队列
      // file的miniurl就是base64
      this.imgs[pos] = $file.miniurl
    },
    $change (value, render) {
      // 内容经过编辑修改
      this.isChange = true
    },
    $imgDel (pos) {
      // 删除图片的base64
      delete this.imgs[pos[0]]
    },
    upLoadImg () {
      if (this.articleId === undefined) {
        let date = new Date()
        // 获取今天是今年第几天
        let currentYear = new Date()
        currentYear.setMonth(0)
        currentYear.setDate(1)
        // 86400000 = 24 *  * 60 * 1000
        let hasTimestamp = date - currentYear
        // 今年的第几分钟
        let k = Math.ceil(hasTimestamp / 60000)
        k = ('000000' + k).slice(-6)// 不足6位补齐0
        let m = date.getMonth() + 1
        let d = date.getDate()
        let y = date.getFullYear()
        m = m > 9 ? m : '0' + m
        d = d > 9 ? d : '0' + d
        // 格式化年月日
        this.date = y + '年' + m + '月' + d + '日'
        // 当前时间，防止重复,天数*时*分
        // 拼接得到唯一id，userid+年(2020开始)+当前时间(分钟)
        k = ('000' + (y - 2020)).slice(-3) + k // 年份差 不足3位补齐0并拼接上
        let id = '000' + this.$user.userId
        id = id.slice(-3) // userId补齐三位
        // 拼接上得到12位的唯一articleId
        this.articleId = id + k
      }
      var formData = this.imgs
      // id加入到表单中
      formData['articleId'] = this.articleId
      formData['userId'] = this.$user.userId
      axios({
        url: this.$baseUrl + 'imgToUrl',
        data: formData,
        method: 'post'
      }).then(resp => {
        // 记录上传失败的图片
        let errorImg = {}
        // 清空当前图片(已上传)
        this.imgs = {}
        // 获取图片在服务器的url并替换
        let baseUrl = this.$baseUrl
        for (var i in resp.data) {
          if (resp.data[i] === 'no') errorImg[i] = 'no'
          this.$refs.md.$img2Url(i, baseUrl + resp.data[i])
        }
        let arr = Object.keys(errorImg)
        if (arr.length > 0) {
          this.$toast('home', '图片上传错误')
          return false
        }
        // 上传文章，图片已被替换成url(数据更新使用nextTick)
        this.$refs.md.$nextTick(() => {
          // 调用上传文章的方法
          this.upArticle()
        })
      }).catch(error => {
        this.$toast('home', '图片上传出现错误 →' + error)
      })
    },
    upArticle () {
      console.log(this.summary)
      axios({
        url: this.$baseUrl + 'upLoad',
        data: {
          'content': this.content,
          'articleId': this.articleId,
          'title': this.title !== undefined ? this.title : this.date + '-随笔',
          'date': this.date,
          'category': this.category !== undefined ? this.category : '随笔',
          'userId': this.$user.userId,
          'summary': this.summary !== '' ? this.summary : '随笔'
        },
        method: 'post'
      }).then(resp => {
        // 获取返回值确认上传成功
        if (resp.data === 'N') this.$toast('home', '上传失败')
        else {
          this.$toast('home', '上传成功')
          // 标记文章为未修改
          this.isChange = false
        }
      })
        .catch(error => {
          this.$toast('home', '文章上传出现错误 →' + error)
        })
    },
    submit () {
      if (!this.isChange) {
        this.$toast('home', '没有修改，勿重复提交')
        return false// 内容没有更改过或没有内容所以拒绝提交
      }
      // upImg后会调用upArticle
      this.upLoadImg()
    }
  }
}
</script>
<style>
  .editormd{
        width:100%;
        background: white;
         border-radius: 10px;
         height: 100%;
    }
  .submitButton{
    margin-top: 10px;
    padding: 5px;
    border-radius: 10px;
    background: rgb(43, 42, 42);
    color: white;
    border:1px solid rgb(48, 150, 184);
    transition: all .3s ease 0s;
  }
  .submitButton:hover{
    padding: 10px;
    background: rgb(48, 150, 184);
    cursor: pointer;
  }
  .inputBox{
  background:white;
  border: 0px;
  opacity: 0.8;
  border-radius: 5px;
  position: relative;
  top: 50%;
  height: 30px;
  padding: 1%;
  width: 47%;
  font-weight: bold;
  font-size: 100%;
 }
 .summaryInput{
  width:98%;
  resize: none;
  font-size: 15px;
  border: none;
  margin-left: 1%;
 }
</style>
