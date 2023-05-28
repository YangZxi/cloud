<template>
  <div class="preview">
    <component
      :is="viewType"
      v-if="isQuickView"
      :resource="props.resource"
      :url="url"
    />
    <div v-else-if="viewType !== Default">
      当前文件不支持直接预览，请点击右下角放大按钮查看详情
    </div>
    <div v-else>
      当前文件类型暂不支持在线预览，请下载后查看
    </div>
    
    <div v-if="viewType !== Default" class="maximize-btn">
      <n-button tertiary circle @click="maximize">
        <template #icon>
          <c-icon name="fa-expand" color="#999" />
        </template>
      </n-button>
    </div>

    <n-modal
      v-model:show="dialog.visible"
      class="preview-dialog"
      style="width: 70%;"
      preset="card"
      :title="dialog.title"
      :bordered="false"
    >
      <div class="preview-dialog-content">
        <component
          style="margin: auto;"
          :is="viewType"
          v-if="url"
          :resource="props.resource"
          :url="url"
        />
      </div>
    </n-modal>
  </div>
</template>

<script setup>
import { computed, watch, ref } from "vue";
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
const dialogRaw = {
  visible: false,
  title: ""
}
const dialog = ref(dialogRaw);

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

const isQuickView = computed({
  get: () => {
    let type = props.resource.type;
    if (filetype.value === null) return false;
    if (filetype.value.image.indexOf(type) !== -1) {
      return true;
    }
    return false;
  },
  set: () => { }
});

const maximize = function() {
  dialog.value.visible = true;
  dialog.value.title = props.resource.name;
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
  background-color: #f7f7f7;
  display: flex;
  padding: 10px;
  box-sizing: border-box;
  color: #555;
  font-weight: 700;
  justify-content: center;
  align-items: center;
  position: relative;

  .maximize-btn {
    position: absolute;
    bottom: 0;
    right: 0;
    padding: 5px;
  }
}

.preview-dialog {
  .preview-dialog-content {
    width: 100%;
    min-height: 100px;
    max-height: 80vh;
    overflow-y: auto;
  }
}

</style>