<template>
  <div class="preview" >
    <!-- <Transition name="preview"> -->
    <div class="preview-content" ref="previewRef">
      <div class="preview-title">
        {{ props.resource.name }}
      </div>
      <div class="preview-body">
        <component
          v-if="url"
          style="width: 100%; height: 100%;"
          :is="viewType"
          :resource="props.resource"
          :maximize="maximize"
          :url="url"
        />
      </div>
    </div>
    <!-- </Transition> -->
    <div class="overlay" v-if="maximize" @click="close"></div>
    
    <div v-if="viewType !== Default" class="maximize-btn">
      <n-button tertiary circle @click="open">
        <template #icon>
          <c-icon name="fa-expand" color="#999" />
        </template>
      </n-button>
    </div>
  </div>
</template>

<script setup>
import { computed, watch, ref, Transition } from "vue";
import Default from "./Default.vue";
import YImage from "./Image.vue";
import YImageM from "./Image-m.vue";
import YText from "./Text.vue";
import YVideo from "./Video.vue";
import YAudio from "./Audio.vue";
import YOffice from "./Office.vue";
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

const previewRef = ref(null);
const maximize = ref(false);
const offsetTop = ref(0);
const offsetLeft = ref(0);
const open = function() {
  const el = previewRef.value;
  const { top, left } = el.getBoundingClientRect(el);
  // el.style.top = `${top}px`;
  // el.style.left = `${left}px`;
  el.classList.toggle("fixed");
  maximize.value = true;
}

const close = function() {
  const el = previewRef.value;
  // el.style.transition = `unset`;
  el.classList.toggle("fixed");
  maximize.value = false;
}

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
  } else if (filetype.value.office.indexOf(type) !== -1) {
    return YOffice;
  } else {
    return Default;
  }
}
</script>

<style scoped lang="scss">
.preview {
  width: 100%;
  height: 100%;
  padding: 10px;
  background-color: #f7f7f7;
  display: flex;
  box-sizing: border-box;
  color: #555;
  font-weight: 700;
  justify-content: center;
  align-items: center;
  position: relative;
  overflow: hidden;

  .preview-content {
    width: 100%;
    height: 100%;
    padding: 0 25px;
    position: relative;
    top: 0;
    left: 0;

    .preview-title {
      height: 30px;
      display: none;
      font-size: 18px;
      font-weight: 700;
      text-align: center;
    }
    .preview-body {
      width: 100%;
      height: 100%;
    }
  }
  .preview-content.fixed {
    position: fixed;
    top: 50% !important;
    left: 50% !important;
    transform: translate(-50%, -50%) !important;
    z-index: 999;
    width: 80vw;
    height: calc(80vh + 30px);
    padding: 10px 20px;
    background-color: #FFF;
    border-radius: 10px;

    .preview-title {
      display: block;
    }
    .preview-body {
      height: calc(100% - 30px);
      overflow-y: auto;
    }
  }


  .overlay {
    position: fixed;
    top: 0;
    left: 0;
    width: 100vw;
    height: 100vh;
    background-color: rgba(0, 0, 0, 0.5);
    z-index: 998;
  }

  .maximize-btn {
    position: absolute;
    bottom: 0;
    right: 0;
    padding: 5px;
  }

}
// .preview-enter-from {
//   top: 0;
// }

// .preview-leave-to {
//   top: 0;
// }

// .preview-enter-from ,
// .preview-leave-to {
//   top: unset;
//   bottom: 0;
//   left: 0;
// }
</style>