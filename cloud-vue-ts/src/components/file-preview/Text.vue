<template>
  <TextEditor
    ref="editorRef"
    height="calc(70vh - 40px)"
    content=""
    read-only
  />
</template>

<script setup>
import { onMounted, ref, inject, defineExpose } from "vue";
import http from "@/http/XMLHttpRequest";
import TextEditor from "@/components/TextEditor/TextEditor.vue";
import { saveContent as saveContentHttp } from "@/http/Explore";

const props = defineProps({
  resource: Object,
  url: String
});

const edit = ref(false);
const resource = ref(props.resource);
const editorRef = ref(null);

// watch(() => props.resource, (val) => {
//   resource.value = props.resource;
//   console.log("ref");
//   refreshCode();
// });

onMounted(() => {
  refreshContent();
});

const refreshContent = function() {
  http.post(props.url).then(({ data }) => {
    editorRef.value.setValue(data);
  }).catch(err => {
    editorRef.value.setValue(err.msg);
    console.log(err);
  });
};

const setReadOnly = function() {
  editorRef.value.setReadOnly(false);
};

/**
 * 保存文件内容
 * @param resource
 */
const saveContent = function() {
  if (!resource.value) return;
  return saveContentHttp(resource.value.id, editorRef.value.getValue()).then(() => {
    window.$message.success("保存成功");
  }).catch((err) => {
    return Promise.reject(err);
  });
};

defineExpose({
  setReadOnly,
  saveContent
});
</script>

<style scoped>
.code-preview {
  width: 100%;
  height: 100%;
}
</style>
