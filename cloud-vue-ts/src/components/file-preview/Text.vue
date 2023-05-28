<template>
  <div>
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
