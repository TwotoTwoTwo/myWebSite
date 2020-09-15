<template>
 <div style="width: 100%;height: 20%;">
  <canvas id='world'/>
  <my-button content="点我有惊喜"/>
  <router-view content="点我没惊喜"/>
  <router-view content="点我给你钱"/>
  <router-view content="点我中彩票"/>
  <router-view content="点我"/>
  <router-view content="广告位招租" top="20px"/>
  </div>
</template>
<script>
export default {
  name: 'topNavbar',
  mounted () {
    var SCREEN_WIDTH = window.innerWidth
    var SCREEN_HEIGHT = window.innerHeight

    var RADIUS = 70

    var RADIUS_SCALE = 1
    var RADIUS_SCALE_MIN = 1
    var RADIUS_SCALE_MAX = 1.5

    var QUANTITY = 25

    var canvas
    var context
    var particles

    var mouseX = SCREEN_WIDTH * 0.5
    var mouseY = SCREEN_HEIGHT * 0.5
    var mouseIsDown = false
    function init () {
      canvas = document.getElementById('world')

      if (canvas && canvas.getContext) {
        context = canvas.getContext('2d')

        // Register event listeners
        window.addEventListener('mousemove', documentMouseMoveHandler, false)
        window.addEventListener('mousedown', documentMouseDownHandler, false)
        window.addEventListener('mouseup', documentMouseUpHandler, false)
        document.addEventListener(
          'touchstart',
          documentTouchStartHandler,
          false
        )
        document.addEventListener('touchmove', documentTouchMoveHandler, false)
        window.addEventListener('resize', windowResizeHandler, false)
        createParticles()
        windowResizeHandler()
        setInterval(loop, 1000 / 60)
      }
    }

    function createParticles () {
      particles = []

      for (var i = 0; i < QUANTITY; i++) {
        var particle = {
          size: 1,
          position: { x: mouseX, y: mouseY },
          offset: { x: 0, y: 0 },
          shift: { x: mouseX, y: mouseY },
          speed: 0.1 + Math.random() * 0.1,
          targetSize: 1,
          fillColor:
            '#' + ((Math.random() * 0x904040 + 0xaaaaaa) | 0).toString(16),
          orbit: RADIUS * 0.5 + RADIUS * 0.5 * Math.random()
        }

        particles.push(particle)
      }
    }

    function documentMouseMoveHandler (event) {
      mouseX = event.clientX - (window.innerWidth - SCREEN_WIDTH) * 0.5
      mouseY = event.clientY - (window.innerHeight - SCREEN_HEIGHT) * 0.5 +
      document.documentElement.scrollTop
      if (mouseY < 0) mouseY = 0
    }

    function documentMouseDownHandler (event) {
      mouseIsDown = true
    }

    function documentMouseUpHandler (event) {
      mouseIsDown = false
    }

    function documentTouchStartHandler (event) {
      if (event.touches.length === 1) {
        event.preventDefault()

        mouseX =
          event.touches[0].pageX - (window.innerWidth - SCREEN_WIDTH) * 0.5
        mouseY =
          event.touches[0].pageY - (window.innerHeight - SCREEN_HEIGHT) * 0.5 +
          document.documentElement.scrollTop
      }
    }

    function documentTouchMoveHandler (event) {
      if (event.touches.length === 1) {
        event.preventDefault()

        mouseX =
          event.touches[0].pageX - (window.innerWidth - SCREEN_WIDTH) * 0.5
        mouseY =
          event.touches[0].pageY - (window.innerHeight - SCREEN_HEIGHT) * 0.5 +
          document.documentElement.scrollTop
      }
    }

    function windowResizeHandler () {
      SCREEN_WIDTH = window.innerWidth
      SCREEN_HEIGHT = window.innerHeight
      console.log(document.documentElement.scrollTop)
      canvas.width = SCREEN_WIDTH
      canvas.height = SCREEN_HEIGHT + document.documentElement.scrollTop
    }

    function loop () {
      windowResizeHandler() // 更新滚动高度
      if (mouseIsDown) {
        RADIUS_SCALE += (RADIUS_SCALE_MAX - RADIUS_SCALE)
      } else {
        RADIUS_SCALE -= (RADIUS_SCALE - RADIUS_SCALE_MIN)
      }

      RADIUS_SCALE = Math.min(RADIUS_SCALE, RADIUS_SCALE_MAX)

      context.fillStyle = 'rgba(22,0,0,0.03)'
      context.fillRect(0, 0, context.canvas.width, context.canvas.height)
      var i = 0
      var len = 0
      for (i = 0, len = particles.length; i < len; i++) {
        var particle = particles[i]

        var lp = { x: particle.position.x, y: particle.position.y }

        // Rotation
        particle.offset.x += particle.speed
        particle.offset.y += particle.speed

        // Follow mouse with some lag
        particle.shift.x += (mouseX - particle.shift.x) * particle.speed
        particle.shift.y += (mouseY - particle.shift.y) * particle.speed

        // Apply position
        particle.position.x =
          particle.shift.x +
          Math.cos(i + particle.offset.x) * (particle.orbit * RADIUS_SCALE)
        particle.position.y =
          particle.shift.y +
          Math.sin(i + particle.offset.y) * (particle.orbit * RADIUS_SCALE)

        // Limit to screen bounds
        particle.position.x = Math.max(
          Math.min(particle.position.x, SCREEN_WIDTH),
          0
        )
        particle.position.y = Math.max(
          particle.position.y,
          0
        )

        particle.size += (particle.targetSize - particle.size) * 0.01

        if (Math.round(particle.size) === Math.round(particle.targetSize)) {
          particle.targetSize = 1 + Math.random() * 2
        }

        context.beginPath()
        context.fillStyle = particle.fillColor
        context.strokeStyle = particle.fillColor
        context.lineWidth = particle.size
        context.moveTo(lp.x, lp.y)
        context.lineTo(particle.position.x, particle.position.y)
        context.stroke()
        context.arc(
          particle.position.x,
          particle.position.y,
          particle.size / 2,
          0,
          Math.PI * 2,
          true
        )
        context.fill()
      }
    }
    init()
  },
  created () {
    // 默认至多10个按钮(组件)
    var xspeed = new Array(10) // 组件的水平速度
    var yspeed = new Array(10) // 组件的垂直速度
    var XrandomNum = new Array(10) // 速度的随机倍数
    var YrandomNum = new Array(10) // 速度的随机倍数
    for (var i = 0; i < xspeed.length; i++) {
      xspeed[i] = 1
      yspeed[i] = 1
      XrandomNum[i] = Math.floor(Math.random() * 10)
      YrandomNum[i] = Math.floor(Math.random() * 10)
    }
    var width = window.innerWidth
    var height = window.innerHeight
    var left = 0 // 左距
    var top = 0 // 上距
    // 绝对布局的移动
    function movebt () {
      var bts = document.getElementsByName('navbutton')
      if (bts !== undefined) {
        bts.forEach((bt, i) => {
          let style = window.getComputedStyle(bt, null)
          left = parseInt(style.left)
          top = parseInt(style.top)
          bt.style.top = top + 1 + 'px'
          if (left > width - parseInt(style.width) - 30) xspeed[i] = -1
          else if (left < 0) xspeed[i] = 1
          if (top > height - parseInt(style.height)) yspeed[i] = -1
          else if (top < 0) yspeed[i] = 1
          bt.style.left = left + xspeed[i] * XrandomNum[i] + 'px'
          bt.style.top = top + yspeed[i] * YrandomNum[i] + 'px'
          // console.log(yspeed[i])
          // console.log(bt.style.top)
        })
      }
    }
    setInterval(movebt, 100)
  }
}

</script>
<style type="text/css">
#navbar {
  width: 100%;
  height: 20%;
}
#world{
  position: absolute;
  left: 0%;
  z-index: 0;
  width: 100%;
}
</style>
