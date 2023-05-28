<template>
  <div class="video-box">
    <Player
      style="--vm-player-theme: #e86c8b;height: 100%;"
    >
      <Video
        cross-origin
        :poster="videoBg"
      >
        <source
          :data-src="props.url"
          type="video/mp4"
        >
        <!-- <track
          default
          kind="subtitles"
          src="https://media.vimejs.com/subs/english.vtt"
          srclang="en"
          label="English"
        > -->
      </Video>

      <DefaultUi />
    </Player>
  </div>
</template>

<script setup>
import videoBg from "@/assets/video-bg.jpg";
import "@vime/core/themes/default.css";
import { Player, Video, DefaultUi } from "@vime/vue-next";

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

</script>

<style scoped>
.video-box {
  width: 100%;
}

/* @media screen and (max-width: 1400px) {
  .video-box {
    width: 100%;
  }
} */
</style>
