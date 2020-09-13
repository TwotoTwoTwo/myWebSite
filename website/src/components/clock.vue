<template>
  <div id="clock">
    <p class="ddate">{{ date }}</p>
    <p class="time">{{ time }}</p>
    <p class="text">coding and debug</p>
  </div>
</template>
<script>
export default {
  data () {
    return {
      date: '',
      time: '',
      week: ['SUN', 'MON', 'TUE', 'WED', 'THU', 'FRI', 'SAT']
    }
  },
  methods: {
    zeroPadding (num, digit) {
      var zero = ''
      for (var i = 0; i < digit; i++) {
        zero += '0'
      }
      return (zero + num).slice(-digit)
    },
    updateTime () {
      var cd = new Date()
      let time = this.zeroPadding(cd.getHours(), 2) + ':' + this.zeroPadding(cd.getMinutes(), 2) + ':' + this.zeroPadding(cd.getSeconds(), 2)
      let date = this.zeroPadding(cd.getFullYear(), 4) + '-' + this.zeroPadding(cd.getMonth() + 1, 2) + '-' + this.zeroPadding(cd.getDate(), 2) + ' ' + this.week[cd.getDay()]
      this.$set(this, 'date', date)
      this.$set(this, 'time', time)
      return true
    }
  },
  mounted () {
    setInterval(this.updateTime, 1000)
  }
}
</script>
<style>
#clock {
  font-family: 'Share Tech Mono', monospace;
  color: #0e0d02;
  margin: 0;
  padding-top: 20px;
  text-align: center;
  text-shadow: 0 0 20px #070808, 0 0 20px rgba(10, 175, 230, 0);
}
#clock .time {
  letter-spacing: 0.05em;
  font-size: 250%;
  padding: 5px 0;
}
#clock .ddate {
  letter-spacing: 0.1em;
}
#clock .text {
  letter-spacing: 0.1em;
  padding: 20px 0 0;
}
</style>
