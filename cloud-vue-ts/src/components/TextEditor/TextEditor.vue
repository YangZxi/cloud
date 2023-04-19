<template>
  <div
    id="editor"
    :style="{
      height: props.height
    }"
  />
</template>

<script setup lang="ts">
import { nextTick, onMounted, defineProps } from "vue";

const props = defineProps({
  height: {
    type: String,
    required: true
  },
  content: {
    type: String,
    required: false,
    default: ""
  },
  readOnly: {
    type: Boolean,
    required: false,
    default: false
  }
});

let editor = null;
onMounted(() => {
  nextTick(() => {
    init();
  });
});

function init() {
  editor = window.ace.edit("editor");
  editor.setReadOnly(props.readOnly);
  editor.setValue(props.content);
}

function setValue(val) {
  editor.setValue(val);
}

function getValue(): string {
  return editor.getValue();
}

function setReadOnly(flag = false) {
  editor.setReadOnly(flag);
}

defineExpose({
  setValue,
  getValue,
  setReadOnly
});
</script>

<style lang="scss" scoped>
#editor {
  width: 100%;
  margin-top: 30px;
}
</style>
