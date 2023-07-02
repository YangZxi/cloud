<template>
  <div>
    <div v-show="props.maximize">
      <TextEditor
        ref="editorRef"
        height="calc(70vh - 20px)"
        content=""
        read-only
      />
      <div class="operateor">
        <n-button size="small" v-if="edit == false" @click="toggleEdit(true)" color="#ff69b4">编辑</n-button>
        <n-button size="small" v-if="edit" @click="toggleEdit(false)">取消</n-button>
        <n-button size="small" v-if="edit" type="primary" @click="saveContent">保存</n-button>
      </div>
    </div>
    <div v-show="!props.maximize" >
      {{ content }}
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref, defineExpose, watch, nextTick } from "vue";
import http from "@/http/XMLHttpRequest";
import TextEditor from "@/components/TextEditor/TextEditor.vue";
import { saveContent as saveContentHttp } from "@/http/Explore";

const props = defineProps({
  resource: Object,
  url: String,
  maximize: Boolean
});

watch(() => props.maximize, (val) => {
  console.log();
  setContent(content.value);
});

const edit = ref(false);
const resource = ref(props.resource);
const editorRef = ref(null);
const content = ref("");

onMounted(() => {
  refreshContent();
});

const refreshContent = function() {
  http.post(props.url).then(({ data }) => {
    setContent(data);
  }).catch(err => {
    console.log(err);
    setContent(err);
  });
};

const setContent = function(text) {
  if (props.maximize) {
    editorRef.value.setValue(text);
  }
  content.value = text;
};

const setReadOnly = function(flag = false) {
  editorRef.value.setReadOnly(flag);
};

const toggleEdit = function(flag) {
  edit.value = flag;
  editorRef.value.setReadOnly(!flag);
}

/**
 * 保存文件内容
 * @param resource
 */
const saveContent = function() {
  if (!resource.value) return;
  return saveContentHttp(resource.value.id, editorRef.value.getValue()).then(() => {
    window.$message.success("保存成功");
    toggleEdit(false);
  }).catch((err) => {
    return Promise.reject(err);
  });
};

defineExpose({
  setReadOnly,
  saveContent
});
</script>

<style scoped lang="scss">
.code-preview {
  width: 100%;
  height: 100%;
}
.operateor {
  text-align: right;

  button {
    margin: 0 5px;
  }
}
</style>
