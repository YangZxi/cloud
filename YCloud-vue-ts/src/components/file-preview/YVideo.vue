<template>
  <div class="video-box">
    <vue3videoPlay
      v-bind="options"
      :poster="videoBg"
    />
  </div>
</template>

<script setup>
import vue3videoPlay from "vue3-video-play"; // 引入组件
import "vue3-video-play/dist/style.css"; // 引入css
import { reactive } from "vue";
import videoBg from "@/assets/video-bg.jpg";

const props = defineProps({
  resource: Object,
  url: String
});

function getType(type) {
  switch (type) {
    case "mp4":
      return "video/mp4";
    case "avi":
      return "video/webm";
    default:
      return "video/mp4";
  }
}

const options = reactive({
  width: "100%", // 播放器高度
  height: "100%", // 播放器高度
  type: getType(props.resource.type),
  color: "#409eff", // 主题色
  title: props.resource.name, // 视频名称
  src: props.url, // 视频源
  muted: false, // 静音
  webFullScreen: false,
  speedRate: ["0.75", "1.0", "1.5", "2.0"], // 播放倍速
  autoPlay: false, // 自动播放
  loop: false, // 循环播放
  mirror: false, // 镜像画面
  ligthOff: false, // 关灯模式
  volume: 0.5, // 默认音量大小
  control: true, // 是否显示控制
  controlBtns: [
    "audioTrack",
    "quality",
    "speedRate",
    "volume",
    "setting",
    "pip",
    "pageFullScreen",
    "fullScreen"
  ] // 显示所有按钮,
});
</script>

<style scoped>
.video-box {
  height: 100%;
}
</style>