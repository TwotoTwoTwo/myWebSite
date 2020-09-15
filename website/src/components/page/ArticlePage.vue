<template>
  <div class="preview" @click="onClick()">
    <p class="date">{{infoArray[3]}}</p>
    <h2 class="title">{{infoArray[1]}}</h2>
    <h3 style="color: blue;width:max-content;">{{isYourArticle?'你的文章':''}}</h3>
    <hr/>
    <img :src="coverPath" class="cover"/>
    <div class="articleContainer">{{infoArray[2]}}</div>
    <label class="posted">posted by {{infoArray[7]}} 号ID</label>
  </div>
</template>
<script>
export default {
  name: 'articlePage',
  props: ['info'],
  data () {
    return {
    }
  },
  computed: {
    infoArray: function () {
      return this.info.split('&&', 8)
      //  articleId + "&&" +
      //  title + "&&" +
      //  summary + "&&" +
      //  date + "&&" +
      //  category + "&&" +
      //  upNum + "&&" +
      //  readNum+"&&" +
      //  userId;
    },
    coverPath: function () {
      return this.$baseUrl + 'getCover?articleId=' + this.infoArray[0] + '&userId=' + this.infoArray[7]
    },
    isYourArticle: function () {
      // 判断是不是自己的文章
      return this.infoArray[7] === this.$user['userId']
    }
  },
  methods: {
    onClick () {
      // 调用父父组件的方法，进行路由,MAin->homePage-> ArticlePage
      // this.$parent.$parent.displayArticle(this.infoArray, this.content)
      this.$router.push({path: '/mdPage', query: {'articleId': this.infoArray[0]}})
    }
  }
}
</script>
<style>
.preview{
  margin: 10px;
  margin-left: 0px;
  padding: 10px;
  width: 100%;
  color: rgb(252, 249, 249);
  display: block;
  border-radius: 10px;
  box-shadow: -5px 5px 10px 1px rgba(93, 230, 211, 0.5);
  overflow: hidden;
  position: relative;
}
.preview:hover{
 opacity: 0.8;
 cursor: pointer;
}
.title{
  width:max-content;
  margin-left: 20px;
  display: inline-block;
}
.title:hover{
  color: rgb(240, 26, 37);
  padding-left: 10px;
}
.date{
 padding: 10px;
 border-radius: 5px;
 position: absolute;
 background: rgb(78, 230, 222);
 right: 5px;
 color: rgb(15, 15, 15);
}
.articleContainer{
  height: 100px;
  padding: 10px;
  overflow-y: hidden;
}
.posted{
  position: absolute;
  right: 0px;
  bottom: 0px;
  margin: 10px;
}
.cover{
  width: 150px;
  height: 150px;
  object-fit: cover;
  float: left;
  opacity: 0.6;
}
img{
  max-width: 100%;
}
</style>
