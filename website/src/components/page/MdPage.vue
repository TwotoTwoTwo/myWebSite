<template>
  <div v-if="isAready===2">
    <div class="mdPage">
      <h1 class="h">{{info[1]}}</h1>
      <br/>
      <label style="color: blue;width:max-content;padding:3px;">作者：{{info[7]}}</label>
      <label style="color: green;width:max-content;padding:3px;">发布于 {{info[3]}}</label>
      <button class='upButton' @click="edit()">编辑</button>
      <br/>
      <div style="line-height:15px;display:inline-block;
      height:15px;font-size:5px;padding:3px;opacity:0.7">
        <img style='height: 15px;display:inline-block;' src='../../assets/book.png'/>
        <div style='height: 15px;display:inline-block;vertical-align: top;'>
        {{words}}个字</div>
        <img src='../../assets/vertical_line.png' style="height:15px"/>
        <img style='height: 15px;display:inline-block;' src='../../assets/time.png'/>
        <div style='height: 15px;display:inline-block;vertical-align: top;'>
        阅读大约需{{Math.round(words/300)}}分钟</div>
        <img src='../../assets/vertical_line.png' style="height:15px"/>
        <img style='height: 15px;display:inline-block;' src='../../assets/eye.png'/>
        <div style='height: 15px;display:inline-block;vertical-align: top;'>
        {{info[6]}} views</div>
        <img src='../../assets/vertical_line.png' style="height:15px"/>
        <img style='height: 15px;display:inline-block;' src='../../assets/category.png'/>
        <div style='height: 15px;display:inline-block;vertical-align: top;'>
        分类为{{info[4]}}</div>
      </div>
      <br/>
      <hr style="opacity: 0.6"/>
      <br/>
      <div v-html="html" class="markdown-body"/>
      <br/>
      <div class='upButton' @click="upAdd" >
        <img style='height: 30px;display:inline-block;' src='../../assets/up.png'/>
        <div style='height: 30px;display:inline-block;vertical-align: top;'>点赞：{{info[5]}}</div>
      </div>
    </div>
    <div id='commentEditArea' style="position: relative">
      <textarea v-model="comment" placeholder="留下你的评论....." class="commentInput"/><br/>
    </div>
    <button class="commentButton"  @click="submit()">发表评论</button>
    <div class="comment" v-for = "(v,n,i) of  commentInfo" :key="i">
      <b>{{v[0]}}:</b>
      {{v[1]}} ——{{v[2]}}
    </div>
  </div>
</template>
<script>
import axios from 'axios'
export default {
  name: 'mdPage',
  data () {
    return {
      isAready: 0,
      isToast: false,
      content: String,
      html: String,
      info: Array,
      articleId: String,
      comment: '', // 输入的评论
      commentInfo: []
    }
  },
  created () {
    this.articleId = this.$route.query.articleId
    let url = this.$baseUrl + 'details?articleId=' + this.articleId
    // 获取文章信息
    axios({
      url: url,
      method: 'get'
    }).then(resp => {
      let c = resp.data
      this.info = c.split('&&', 9)
      //  文章信息包含以下八种
      //  articleId + "&&" +
      //  title + "&&" +
      //  summary + "&&" +
      //  date + "&&" +
      //  category + "&&" +
      //  upNum + "&&" +
      //  readNum+ "&&" +
      //  userId+ "&&" +
      //  content;
      this.html = this.$markdownIt.render(this.info[8])
      this.isAready++
    })
    // 获取评论
    url = this.$baseUrl + 'comments?articleId=' + this.articleId
    axios({
      url: url,
      method: 'get'
    }).then(resp => {
      let data = resp.data
      for (const key in data) {
        if (data.hasOwnProperty(key)) {
          this.commentInfo[key] = data[key].split('&&', 3)
          // userName + "&&" +
          // content + "&&" +
          // date ;
        }
      }
      this.isAready++
    })
  },
  methods: {
    edit () {
      if (this.$user['userId'] !== this.info[7]) this.$toast('container', '无法编辑其他用户的文章')
      else if (this.$user['userPassword'] === null) this.$toast('container', '权限不够，无法编辑')
      else {
        this.$router.push({path: '/editorPage',
          query: {info: this.info}})
      }
    },
    submit () {
      console.log(this.comment.length)
      if (this.comment.length === 0) {
        // 自定义弹框
        this.$toast('commentEditArea', '请输入评论内容')
        return
      }
      if (this.comment.length > 60) {
        // 自定义弹框
        this.$toast('commentEditArea', '评论长度不能超过60个字符')
        return
      }
      let date = new Date()
      // 获取当前日期
      let m = date.getMonth() + 1
      let d = date.getDate()
      let y = date.getFullYear()
      m = m > 9 ? m : '0' + m
      d = d > 9 ? d : '0' + d
      // 格式化年月日
      date = y + '年' + m + '月' + d + '日'
      // 发表评论
      let form = {
        'content': this.comment,
        'userName': this.$user.userName,
        'articleId': this.info[0],
        'date': date
      }
      // 新评论
      let newComment = [this.$user.userName, this.comment, date]
      let url = this.$baseUrl + 'comment'
      axios({
        data: form,
        method: 'post',
        url: url
      }).then(resp => {
        this.commentInfo.splice(0, 0, newComment)
        this.comment = ''
      })
    },
    upAdd () {
      let form = {
        'articleId': this.info[0]
      }
      let url = this.$baseUrl + 'upAdd?articleId=' + this.info[0]
      axios({
        data: form,
        method: 'get',
        url: url
      }).then(resp => {
        let num = Number(this.info[5]) + 1
        this.info.splice(5, 1, num)
      })
    }
  },
  computed: {
    words: function () {
      // info[8] 是文章内容
      return this.info !== undefined ? this.info[8].length : 0
    }
  }
}
</script>
<style>
.upButton{
  padding-left: 5px;
  padding-right: 5px;
  background: rgb(243, 236, 236);
  border: 0px;
  height: 30px;
  font-size: 15px;
  display: inline-block;
  line-height: 30px;
}
.upButton:hover{
  cursor: pointer;
  background: rgb(245, 217, 217);
}
.h{
  word-break: break-all;
  word-wrap: break-word;
}
.comment{
  background: white;
  margin-top: 10px;
  padding: 5px;
  width: max-content;
  max-width: 90%;
  color: black;
  border-radius: 5px;
}
.comment:hover{
  background:  rgb(202, 199, 199);
}
.commentInput{
  width:95%;
  resize: none;
  box-sizing: border-box;
  font-size: 15px;
  padding: 5px;
  border-radius: 5px;
  margin:0px;
  margin-top: 10px;
}
  .commentButton{
    margin-top: 10px;
    padding: 5px;
    border-radius: 10px;
    background: rgb(43, 42, 42);
    color: white;
    border:1px solid rgb(48, 150, 184);
    transition: all .3s ease 0s;
  }
  .commentButton:hover{
    padding: 10px;
    background: rgb(48, 150, 184);
    cursor: pointer;
  }
.mdPage{
  width: 90%;
  min-height: 100%;
  margin-top: 20px;
  padding: 5%;
  padding-bottom: 15px;
  background: rgb(255, 255, 255);
  color: black;
  text-align: left;
}
 .mdPage h2{
  padding: 5px;
  background: rgb(156, 229, 241);
  box-shadow: 0px 3px 5px 2px rgba(173, 235, 144, 0.8);
}
 .mdPage h3{
  padding: 5px;
  background: rgb(166, 238, 137);
  box-shadow: 0px 3px 5px 2px rgb(104, 88, 88,0.8);
}
 .mdPage h4{
  padding: 5px;
  background: rgb(138, 111, 236);
  box-shadow: 0px 3px 5px 2px rgba(150, 141, 141, 0.8);
}
 .mdPage h5{
  padding: 5px;
  background: rgb(248, 170, 0);
  box-shadow: 0px 3px 5px 2px rgba(56, 223, 245, 0.8);
}
 .mdPage pre{
  width: fit-content;
  padding: 5px;
  background: rgba(161, 162, 235, 0.349);
}
</style>
