<template>
  <transition class="fade">
    <button class="hexagon" name='navbutton' v-html="addspace" style="left:0px;top:0px">
    </button>
  </transition>
</template>
<script>
export default {
  name: 'Button',
  props: ['content'],
  show: true,
  data () {
    return {
      show: true
    }
  },
  computed: {
    addspace: function () {
      return '&nbsp&nbsp&nbsp' + this.content + '&nbsp&nbsp&nbsp'
    }
  }
}
</script>
<style>
 .hexagon{
    border-radius: 30px;
    background:linear-gradient(to right,#30cfd0,#330867);
    color: rgb(247, 239, 233);
    opacity: 0.9;
    height:50px;
    border:5px solid rgb(112, 115, 116);
    outline: none;
    position:absolute;
  }

  .fade-enter-active, .fade-leave-active {
  transition: opacity .5s;
}
  .fade-enter, .fade-leave-to /* .fade-leave-active below version 2.1.8 */ {
  opacity: 0;
}
</style>
