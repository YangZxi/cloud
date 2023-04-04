<template>
  <div class="preview">
    <component
      :is="viewType"
      v-if="url"
      :resource="props.resource"
      :url="url"
    />
  </div>
</template>

<script setup>
import { computed, watch, ref } from "vue";
import Default from "./Default.vue";
import YImage from "./YImage.vue";
import YImageM from "./YImage-m.vue";
import YText from "./YText.vue";
import YVideo from "./YVideo.vue";
import YAudio from "./YAudio.vue";
import { preview } from "@/http/Explore";
import store from "@/store/temp";

const props = defineProps({
  api: {
    type: Function,
    required: false,
    default: preview
  },
  resource: {
    type: Object,
    required: true
  }
});

const filetype = ref(null);
store().getFiletype().then(res => {
  filetype.value = res;
});

const isMobile = window.sessionStorage.getItem("isMobile");

const url = ref("");

watch(() => props.resource, () => {
  url.value = "";
  getUrl(props.resource.id);
});

const getUrl = async (id) => {
  let u = "";
  await props.api(id).then(url => {
    u = url;
    console.log(url);
  });
  url.value = u;
  return u;
};

getUrl(props.resource.id);

const viewType = computed({
  get: () => {
    let type = props.resource.type;
    let v = getView(type);
    if (v === Default) {
      let dot = props.resource.name.indexOf(".");
      if (dot === -1) return v;
      type = props.resource.name.substring(dot + 1);
      v = getView(type);
    }
    return v;
  },
  set: () => { }
});

function getView(type) {
  if (filetype.value === null) return Default;
  if (filetype.value.image.indexOf(type) !== -1) {
    return isMobile ? YImageM : YImage;
  } else if (filetype.value.text.indexOf(type) !== -1) {
    return YText;
  } else if (filetype.value.video.indexOf(type) !== -1) {
    return YVideo;
  } else if (filetype.value.audio.indexOf(type) !== -1) {
    return YAudio;
  } else {
    return Default;
  }
}

</script>

<style scoped>
.preview {
  height: 380px;
  background-color: #F7F7F7;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 10px;
  box-sizing: border-box;
}
</style>
