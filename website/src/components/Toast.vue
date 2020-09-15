<template>
  <div class="outToast" v-if="isShow" :style="{maxHeight: height + 'px'}">
    <div class='toast' v-if="isShow">
      {{message}}
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      message: '',
      time: 2,
      isShow: true,
      height: document.body.clientHeight
    }
  },
  mounted () {
    console.log(this.height)
    setTimeout(() => {
      this.isShow = false
    }, this.time * 1000)
  }
}
</script>
<style>
  .outToast{
    position: absolute;
    width: 100%;
    height: 100%;
    bottom: 0;
  }
  .toast{
    position: absolute;
    left: 40%;
    bottom:50%;
    padding: 5px;
    text-align: center;
    background:rgb(158, 140, 175);
    border-radius: 5px;
    z-index: 1000;
    width: max-content;
    height: fit-content;
    animation:toastAnimation 1s;
-webkit-animation:toastAnimation 1s /*Safari and Chrome*/
  }
  @keyframes toastAnimation{
    from {left:10%;background: red;}
    to {left:40%;background: rgb(158, 140, 175);}
  }

  @-webkit-keyframes toastAnimation /*Safari and Chrome*/{
    from {left:10%;}
    to {left:40%;}
  }
</style>
