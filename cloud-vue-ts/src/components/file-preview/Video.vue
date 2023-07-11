<template>
  <div class="preview-video flex-center">
    <div id="dplayer"></div>
  </div>
</template>

<script setup>
import { onMounted } from "vue";
import videoBg from '@/assets/video-bg.jpg';
import DPlayer from 'dplayer';

const props = defineProps({
  resource: Object,
  url: String
});

onMounted(() => {
  const dp = new DPlayer({
    container: document.getElementById('dplayer'),
    video: {
      url: props.url,
      theme: "#d8eadc"
    },
  });
})

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

</script>

<style scoped lang="scss">
.preview-video {
  height: 100%;

  #dplayer {
    width: 100%;
    max-width: 70vw;
  }
}



/* @media screen and (max-width: 1400px) {
  .video-box {
    width: 100%;
  }
} */
</style>