<template>
  <div class="preview-audio">
      <AudioPlayer
        class="audio-player"
        :option="{
          src: props.url,
          title: getTitle,
          coverImage: 'https://img2.baidu.com/it/u=3895119537,2684520677&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500',
        }"
      />
  </div>
</template>

<script setup>
import AudioPlayer from "vue3-audio-player";
import "vue3-audio-player/dist/style.css";
import { computed } from "vue";

const props = defineProps({
  resource: Object,
  url: String
});

const getTitle = computed({
  get: () => {
    let name = props.resource.name;
    if (!name) return "";
    let dot = name.indexOf(".");
    return dot === -1 ? name : name.substr(0, dot);
  }
});

</script>

<style scoped lang="scss">
.preview-audio {
  width: 90%;
  height: 100%;
  margin: auto;
  max-width: 500px;
  display: flex;
  align-items: center;

  .audio-player {
    height: 170px;
    width: 100%;
    padding: 10px 20px;
    background-color: #FFF;
    border-radius: 10px;
    box-sizing: border-box;

  }
}

:deep(.audio__player-play-cont) {
  display: flex;
  justify-content: center;
}

:deep(.audio__player-play-icon) {
  cursor: pointer;
}
</style>
