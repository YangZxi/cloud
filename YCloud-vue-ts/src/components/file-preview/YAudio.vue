<template>
  <div class="audio-box">
    <AudioPlayer
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

<style scoped>
.audio-box {
  background-color: #FFF;
  padding: 10px 20px;
  border-radius: 10px;
  width: 90%;
  max-width: 500px;
}

:deep(.audio__player-play-cont) {
  display: flex;
  justify-content: center;
}

:deep(.audio__player-play-icon) {
  cursor: pointer;
}
</style>
